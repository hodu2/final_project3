package com.codingbox.tripjava.controller;

import java.lang.System.Logger;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.codingbox.tripjava.dto.LoginFormDTO;
import com.codingbox.tripjava.dto.MemberFormDTO;
import com.codingbox.tripjava.dto.UserResponseDTO;
import com.codingbox.tripjava.entity.User;
import com.codingbox.tripjava.session.SessionConst;
import com.codingbox.tripjava.user.service.KakaoService;
import com.codingbox.tripjava.user.service.UserService;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

//@Slf4j
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class UserController {
	
	private final UserService userService;
	private final KakaoService kakaoService;
	
	// 회원조회
	@GetMapping("/{userId}")
    public ResponseEntity<?> getUserInfo(@PathVariable Integer userId) {
        try {
            // UserService를 통해 사용자 정보를 가져옵니다.
            User user = userService.findById(userId);
            if (user == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("사용자를 찾을 수 없습니다.");
            }
            return ResponseEntity.ok().body(Map.of("user", user));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("사용자 정보를 불러오는 중 오류가 발생했습니다.");
        }
    }
	
	// 회원 정보 수정
    @PostMapping("/{userId}/update")
    public ResponseEntity<?> updateUser(@PathVariable Integer userId, @RequestBody User user) {
        try {
            // 서비스 계층에서 사용자 정보를 업데이트
            User updatedUser = userService.updateUser(userId, user);
            return ResponseEntity.ok(updatedUser); // 수정된 사용자 정보 반환
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("회원 정보 수정 실패");
        }
    }

    // 소셜 로그안시, 전화변호 등록 
    @PostMapping("/{userId}/register-phone")
    public ResponseEntity<?> registerPhone(
            @PathVariable Integer userId, @RequestBody MemberFormDTO form) {
        try {
            // 유효성 검사: 전화번호 형식 확인
            if (form.getPhone() == null || !form.getPhone().matches("^\\d{10,11}$")) {
                return ResponseEntity.badRequest().body("전화번호는 10~11자리 숫자만 가능합니다.");
            }

            // 유저 서비스로 전화번호 저장 요청
            userService.registerPhone(userId, form.getPhone());

            return ResponseEntity.ok("전화번호 등록이 완료되었습니다.");
        } catch (Exception e) {
            e.printStackTrace(); // 서버 오류 로그 출력
            return ResponseEntity.status(500).body("서버 오류로 전화번호 등록에 실패했습니다.");
        }
    }

	// 회원 탈퇴
    @PostMapping("/{userId}/delete/")
    public ResponseEntity<?> deleteUser(@PathVariable Integer userId, HttpServletRequest request, HttpServletResponse response) {
        try {
        	userService.deleteUser(userId, request, response);
            return ResponseEntity.ok("회원 탈퇴 완료");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("회원 탈퇴 실패");
        }
	 }
	
	// 로그인 (세션) 처리
	@PostMapping("/login")
	public ResponseEntity<String> login(
	        @RequestBody LoginFormDTO loginForm,
	        HttpServletRequest request) {

	    // UserService에서 인증 처리
	    User user = userService.login(loginForm.getEmail(), loginForm.getPassword());
	    if (user == null) {
	        // 로그인 실패 시
	        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
	                .body("아이디 또는 비밀번호가 잘못되었습니다.");
	    }

	    // 로그인 성공 시
	    HttpSession session = request.getSession();  // 세션 생성 또는 가져오기
	    session.setAttribute(SessionConst.LOGIN_MEMBER, user);  // 세션에 사용자 정보 저장
	    System.out.println("세션에 저장된 User ID: " + user.getUserId()); // 로그 확인
//	    log.info("세션 저장 완료: {  } ", user.getEmail());
	    return ResponseEntity.ok("로그인 성공");
	}
	
	// 세션
	@GetMapping("/session")
    public ResponseEntity<?> checkSession(HttpSession session) {
        User user = (User) session.getAttribute(SessionConst.LOGIN_MEMBER);  // 세션에서 사용자 정보 가져오기
        String accessToken = (String) session.getAttribute("kakaoAccessToken");

        if (user != null) {
            if (accessToken != null) {
                return ResponseEntity.ok(new UserResponseDTO(user, accessToken));  // 카카오 로그인 상태
            } else {
                return ResponseEntity.ok(new UserResponseDTO(user, null));  // 일반 로그인 상태
            }
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("로그인되지 않은 사용자입니다.");
        }
    }
	

	// 일반 로그인 로그아웃
    @PostMapping("/logout")
    public ResponseEntity<String> logout(HttpServletRequest request) {
        try {
            HttpSession session = request.getSession(false);
            if (session != null) {
                session.invalidate();  // 세션 무효화
            }
            return ResponseEntity.ok("일반 로그아웃 성공");

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                 .body("로그아웃 처리 중 서버 오류 발생: " + e.getMessage());
        }
    }

    // 소셣 로그인시, 로그아웃
	@PostMapping("/kakao-logout")
    public ResponseEntity<String> kakaoLogout(HttpServletRequest request, HttpServletResponse response, @RequestParam("accessToken") String accessToken) {
        try {
            // 카카오 로그아웃 API 호출 (카카오 서버에서 로그아웃을 처리)
            kakaoService.logoutFromKakao(accessToken);

            // 세션 무효화
            HttpSession session = request.getSession(false);
            if (session != null) {
                session.invalidate();  // 세션 무효화
            }

            // 쿠키 제거 (예시로 "accessToken" 쿠키 제거)
            Cookie cookie = new Cookie("accessToken", ""); // 쿠키 이름 "accessToken"
            cookie.setMaxAge(0);  // 쿠키 만료 설정
            cookie.setPath("/");  // 전체 도메인에서 쿠키 접근 가능하도록 설정
            cookie.setHttpOnly(true); // 클라이언트 스크립트에서 접근할 수 없게 설정 (보안 향상)
            cookie.setSecure(true);  // HTTPS 연결에서만 쿠키 전송
            response.addCookie(cookie);  // 쿠키 삭제를 위한 응답에 추가

            return ResponseEntity.ok("http://localhost:5173/logout-callback");

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                 .body("카카오 로그아웃 처리 중 오류 발생: " + e.getMessage());
        }
    }
    
	// 소셜로그인 회원정보 탈퇴 요청시, 카카오 연결끊기	
	@PostMapping("/{userId}/kakao-unlink")
	public ResponseEntity<String> kakaoUnlink(@PathVariable Integer userId, 
                                                HttpServletRequest request, 
                                                HttpServletResponse response, 
                                                @RequestParam("accessToken") String accessToken) {
	    try {
	        // 카카오 연결 끊기 요청
	        kakaoService.unlinkFromKakao(accessToken);

	        // 데이터베이스에서 회원 제거
            userService.deleteUser(userId, request, response);

	        // 리디렉션 URL을 프론트엔드로 반환
	        return ResponseEntity.ok("http://localhost:5173/");
	    } catch (Exception e) {
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
	                             .body("카카오 연결 끊기 처리 중 오류 발생: " + e.getMessage());
	    }
	}

	// 회원가입
	@PostMapping("/regit")
	public ResponseEntity<String> save(@RequestBody MemberFormDTO member) {
        try {
            // 회원가입 서비스 호출
            userService.join(member);
            return ResponseEntity.ok().body("회원가입 성공!");
        } catch (Exception e) {
            return ResponseEntity.status(400).body("회원가입 실패: " + e.getMessage());
        }
    }

	
}
