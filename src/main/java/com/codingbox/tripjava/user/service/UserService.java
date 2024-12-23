package com.codingbox.tripjava.user.service;

import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.codingbox.tripjava.dto.LoginFormDTO;
import com.codingbox.tripjava.dto.MemberFormDTO;
import com.codingbox.tripjava.entity.User;
import com.codingbox.tripjava.user.repository.UserRepository;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserService {
	
	private final UserRepository userRepository;
	
	// 로그인 기능
	public User login(String loginId, String password) {
		// 아이디, 비밀번호 체크
        User user = userRepository.findByLoginId(loginId);
        if (user == null || !user.getPassword().equals(password)) {
            throw new IllegalArgumentException("로그인 정보가 일치하지 않습니다.");
        }
        return user;
    }
	
	// 회원 가입 일반
	@Transactional
	public Integer join(MemberFormDTO memberForm) {
		User user = new User();
		user.setUsername(memberForm.getUsername());
        user.setEmail(memberForm.getEmail());
        user.setPassword(memberForm.getPassword());
        user.setPhone(memberForm.getPhone());
		userRepository.save(user);
		return user.getUserId();
	}

	// 로그인 아이디로 회원조회
	public User findOne(String loginId) {
		return userRepository.findByLoginId(loginId);
	}
	
	// 카카오 사용자 정보를 처리.기존 회원 검사 후 저장하거나 기존 회원 반환.
	@Transactional
	public User processKakaoUser(User kakaoUser) {
	    if (kakaoUser.getEmail() == null || kakaoUser.getUsername() == null) {
	        throw new IllegalArgumentException("사용자 정보가 누락되었습니다. (email: " + kakaoUser.getEmail() + ", username: " + kakaoUser.getUsername() + ")");
	    }

	    // 1. 기존 회원 검사
	    User existingUser = userRepository.findByLoginId(kakaoUser.getEmail());
	    if (existingUser != null) {
	        return existingUser;
	    }

	    // 2. 신규 회원 생성 및 저장
	    User newUser = new User();
	    newUser.setSocialProvider("kakao");
	    newUser.setSocialId(kakaoUser.getSocialId());
	    newUser.setUsername(kakaoUser.getUsername());
	    newUser.setEmail(kakaoUser.getEmail());
	    userRepository.save(newUser);
		return newUser;
	}

	// 회원 조회
	public User findById(Integer userId) {
		return userRepository.findOne(userId);
	}
	
	// 회원 수정
	@Transactional
	public User updateUser(Integer userId, User user) throws Exception {
		User existingUser = userRepository.findOne(userId);
		
		// 기존 사용자 정보 갱신
        existingUser.setUsername(user.getUsername());
        existingUser.setPhone(user.getPhone());

        return userRepository.save(existingUser);
	}
	
	// 회원 탈퇴
	@Transactional
	public void deleteUser(Integer userId, HttpServletRequest request, HttpServletResponse response) throws Exception {
        // 사용자 삭제
        User deleteUser = userRepository.findOne(userId);
        userRepository.delete(deleteUser);
        
        // 세션 종료
        HttpSession session = request.getSession(false); // 세션이 있으면 가져옴
        if (session != null) {
            session.invalidate(); // 세션 만료
        }

        // 인증 쿠키 삭제
        Cookie authCookie = new Cookie("auth_token", null);  // 쿠키 이름에 맞춰 설정
        authCookie.setMaxAge(0);  // 쿠키 만료 시간을 0으로 설정
        authCookie.setPath("/");  // 쿠키의 경로 설정
        response.addCookie(authCookie);  // 응답에 쿠키 추가
    }
	
	// 소셯로그인 진행시, 신규 유저인지 확인
	public boolean isNewUser(Integer userId) {
		System.out.println("userId : " + userId);
		User user = userRepository.findOne(userId);
		if (user.getPhone() == null) {
			return true;
		}
		return false;
	}

	// 소셜로그인시, 전화번호 등록 로직
	@Transactional
	public void registerPhone(Integer userId, String phone) {
		User user = userRepository.findOne(userId);
		user.setPhone(phone);
	    userRepository.save(user); // 전화번호 저장
	}
}
