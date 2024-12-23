import React from "react";
import kakaoImg from "../assets/kakao_login_medium_wide.png";
import emailImg from "../assets/icon-envelope.png";
import { useNavigate } from "react-router-dom";
import "./loginpage.css";

export default function LoginPage() {
  const navigation = useNavigate();
  function handleLogin() {
    navigation("/login/email");
  }

  function handleKakaoLogin() {
    window.location.href = "/api/auth/kakao";
  }

  return (
    <main className="container">
      {/* 로그인영역 */}
      <div className="login">
        <div className="login_logo">
          <img src="/src/img/logo.png" alt="Logo" />
        </div>
        {/* 로그인 타이틀 */}
        <div className="login_title">
          <span className="caption">로그인/회원가입</span>
          <span className="strikethrough"></span>
        </div>
        {/* 로그인 버튼 */}
        <div className="login_btn_group">
          {/* 소셜 로그인 */}
          <button onClick={handleKakaoLogin}>
            <img src={kakaoImg} />
            카카오로 시작하기
          </button>
          <button onClick={handleLogin}>
            <img src={emailImg} className="email-btn"/>
            이메일로 시작하기
          </button>
        </div>
      </div>
    </main>
  );
}
