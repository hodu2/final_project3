package com.codingbox.tripjava.user.service;

import java.util.Map;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.codingbox.tripjava.entity.User;


@Service
public class KakaoService {

    // 상수값 (카카오 디벨로퍼스에서 발급받은 값)
    private static final String USER_INFO_URL = "https://kapi.kakao.com/v2/user/me";
    private static final String USER_KAKAO_REST_API_KEY = "6f14e0deeef1d4d349512266f3dd47fc";
    private static final String USER_REDIRECT_URI = "http://localhost:9090/auth/kakao/callback";
    private static final RestTemplate restTemplate = new RestTemplate();
        
    
        /**
         * 1. 카카오 서버에서 Access Token 요청
         */
        public String getAccessToken(String code) {
            String tokenUrl = "https://kauth.kakao.com/oauth/token";
            
            MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
            params.add("grant_type", "authorization_code");
            params.add("client_id", USER_KAKAO_REST_API_KEY);  // 카카오 개발자에서 발급받은 REST API Key
            params.add("redirect_uri", USER_REDIRECT_URI);
            params.add("code", code);
    
            HttpHeaders headers = new HttpHeaders();
            headers.add("Content-Type", "application/x-www-form-urlencoded");
    
            HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(params, headers);
            try {
                // 토큰 요청
                ResponseEntity<Map> response = restTemplate.exchange(tokenUrl, HttpMethod.POST, entity, Map.class);
                Map<String, String> responseBody = response.getBody();

                // null 체크 추가
                if (responseBody == null || !responseBody.containsKey("access_token")) {
                    throw new IllegalStateException("Access token을 얻는 데 실패했습니다.");
                }
                return responseBody.get("access_token");  // 액세스 토큰 반환
            } catch (Exception e) {
                throw new RuntimeException("카카오 액세스 토큰 요청 실패: " + e.getMessage(), e);
            }
        }
    
        /**
         * 2. Access Token을 이용해 사용자 정보 요청
         */
        public User getUserInfo(String token) {
            HttpHeaders headers = new HttpHeaders();
            headers.add("Authorization", "Bearer " + token);
            
            HttpEntity<String> entity = new HttpEntity<>(headers);
            
            try {
                ResponseEntity<Map> response = restTemplate.exchange(USER_INFO_URL, HttpMethod.GET, entity, Map.class);
                Map<String, Object> userInfo = response.getBody();
                Long socialId = (Long) userInfo.get("id"); // 고유 사용자 ID (socialId)

                // 사용자 정보 파싱
                Map<String, Object> kakaoAccount = (Map<String, Object>) userInfo.get("kakao_account");
                Map<String, Object> profile = (Map<String, Object>) kakaoAccount.get("profile");
                
                String email = (String) kakaoAccount.get("email");
                String username = (String) profile.get("nickname");

                if (socialId == null || email == null || username == null) {
                    throw new IllegalArgumentException("카카오 사용자 정보가 올바르지 않습니다. (email: " + email + ", nickname: " + username + ")");
                }

                User kakaoUser = new User();
                kakaoUser.setSocialId(String.valueOf(socialId)); // socialId는 문자열로 변환
                kakaoUser.setEmail(email);
                kakaoUser.setUsername(username);
                
                return kakaoUser;
            } catch (Exception e) {
                throw new RuntimeException("카카오 사용자 정보 요청 실패: " + e.getMessage(), e);
            }
        }

        /**
         * 카카오 로그아웃 처리
         */
        public void logoutFromKakao(String accessToken) {
            String logoutUrl = "https://kapi.kakao.com/v1/user/logout";

            HttpHeaders headers = new HttpHeaders();
            headers.add("Authorization", "Bearer " + accessToken);

            HttpEntity<Void> entity = new HttpEntity<>(headers);

            try {
                ResponseEntity<Map> response = restTemplate.exchange(logoutUrl, HttpMethod.POST, entity, Map.class);

                if (!response.getStatusCode().is2xxSuccessful()) {
                    throw new IllegalArgumentException("카카오 로그아웃 실패: 응답 코드 " + response.getStatusCode());
                }

                Map<String, Object> responseBody = response.getBody();
                if (responseBody != null) {
                    System.out.println("카카오 로그아웃 성공. 사용자 ID: " + responseBody.get("id"));
                }

            } catch (HttpClientErrorException e) {
                throw new IllegalArgumentException("카카오 로그아웃 실패: " + e.getResponseBodyAsString(), e);
            } catch (Exception e) {
                throw new RuntimeException("카카오 로그아웃 처리 중 예기치 못한 오류 발생", e);
            }
        }
        // 카카오 연결해제
		public void unlinkFromKakao(String accessToken) {
			RestTemplate restTemplate = new RestTemplate();
		    HttpHeaders headers = new HttpHeaders();
		    headers.set("Authorization", "Bearer " + accessToken);

		    HttpEntity<String> entity = new HttpEntity<>(headers);

		    String unlinkUrl = "https://kapi.kakao.com/v1/user/unlink";
		    restTemplate.exchange(unlinkUrl, HttpMethod.POST, entity, String.class);
		}


}
