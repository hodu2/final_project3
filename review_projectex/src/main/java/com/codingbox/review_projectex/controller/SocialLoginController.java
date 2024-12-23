package com.codingbox.review_projectex.controller;

import java.io.IOException;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.codingbox.review_projectex.session.SessionConst;
import com.codingbox.review_projectex.dto.LoginFormDTO;
import com.codingbox.review_projectex.entity.User;
import com.codingbox.review_projectex.user.service.KakaoService;
import com.codingbox.review_projectex.user.service.UserService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class SocialLoginController {
	
	private final KakaoService kakaoService;
	private final UserService userService;
	
    @GetMapping("/api/auth/kakao")
    public void kakaoLoginRedirect(HttpServletResponse response) throws IOException {
        String clientId = "6f14e0deeef1d4d349512266f3dd47fc";
        String redirectUri = "http://localhost:9090/auth/kakao/callback";
        String kakaoAuthUrl = "https://kauth.kakao.com/oauth/authorize"
            + "?response_type=code"
            + "&client_id=" + clientId
            + "&redirect_uri=" + redirectUri;

        response.sendRedirect(kakaoAuthUrl);
    }

    @GetMapping("/auth/kakao/callback")
    public void kakaoLoginCallback(@RequestParam("code") String code, HttpSession session,
    		HttpServletResponse response) throws IOException {
        System.out.println(code);
        // 카카오 서버로부터 받은 인가 코드로 토큰 요청
        String token = kakaoService.getAccessToken(code);
        System.out.println("token : " + token);
        // 사용자 정보 요청 및 세션 생성
        User kakaoUser = kakaoService.getUserInfo(token);
        System.out.println("kakaoUser : " + kakaoUser);
        // 사용자 정보 처리 및 DB 저장
        User savedUser = userService.processKakaoUser(kakaoUser);
        session.setAttribute(SessionConst.LOGIN_MEMBER, savedUser);
        session.setAttribute("kakaoAccessToken", token); // 액세스 토큰

     	// 리액트 페이지로 리다이렉트
         if (userService.isNewUser(savedUser.getUserId())) {
            response.sendRedirect("http://localhost:5173/login/regitPhone"); // 신규 사용자
        } else {
            response.sendRedirect("http://localhost:5173/"); // 기존 사용자
        }
    }
}
	

