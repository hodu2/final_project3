import React from "react";
import { Link } from "react-router-dom";
import "./Header.css";
import { useNavigate } from "react-router-dom";
import axios from "axios";
import SearchBar from "../SearchBar/SearchBar.jsx"

export default function Header({ user, setUser, token, setToken}) {
  const navigate = useNavigate();

  function handleMypage() {
    navigate("/mypage");
  }

  function handleLogin() {
    navigate("/login");
  }

  // **추가된 브라우저 데이터 삭제 함수**
  const clearBrowserData = () => {
    // 세션 스토리지 초기화
    sessionStorage.clear();

    // 로컬 스토리지 초기화
    localStorage.clear();

    // 쿠키 초기화
    document.cookie.split(";").forEach((cookie) => {
      const eqPos = cookie.indexOf("=");
      const name = eqPos > -1 ? cookie.substr(0, eqPos) : cookie;
      document.cookie = `${name}=;expires=Thu, 01 Jan 1970 00:00:00 GMT;path=/`;
    });
  };

  const handleLogout = async () => {
    try {

      let logoutUrl = '/api/logout';  // 기본 로그아웃 URL (일반 로그인)
    
      if (token) {
        logoutUrl = '/api/kakao-logout';  // 카카오 로그인 상태일 경우
      } 

      // 로그아웃 요청
      const response = await axios.post(
        logoutUrl,
        null,
        {
          params: token ? { accessToken: token } : {}, // token이 있을 때만 accessToken 전달
          withCredentials: true, // 세션 정보 포함
        }
      );

      if (response.status === 200) {
        const logoutRedirectUrl = response.data; // 백엔드에서 반환된 로그아웃 리디렉션 URL
        console.log('카카오 연결 끊기 및 로그아웃 성공');
        setUser(null);
        setToken(null); // 상태 초기화
        alert('로그아웃 성공');

        // **브라우저 데이터 삭제 실행**
        clearBrowserData();

        if (token) {
          const YOUR_CLIENT_ID = "6f14e0deeef1d4d349512266f3dd47fc";
          const kakaoLogoutUrl = `https://kauth.kakao.com/oauth/logout?client_id=${YOUR_CLIENT_ID}&logout_redirect_uri=${encodeURIComponent(
            logoutRedirectUrl || '/'
          )}`;
          window.location.href = kakaoLogoutUrl; // 카카오 로그아웃 후 리디렉션
          navigate("/");
        } else {
          navigate("/"); // 일반 로그아웃 후 메인 페이지로 리디렉션
        }

      }
    } catch (error) {
      console.error('로그아웃 실패:', error.message);
      alert('로그아웃 처리 중 문제가 발생했습니다.');
    }
  };

  return (
    <div className="header">
      <Link to="/">
        <img className="logo" src="/src/img/logo.png" alt="Logo" />
      </Link>
      <SearchBar />
      <ol className="member">
        {!user ? (
          <li onClick={handleLogin}>로그인/회원가입</li>
        ) : (
          <>
            <li onClick={handleLogout}>로그아웃</li>
            <li onClick={handleMypage}>마이페이지</li>
          </>
        )}
      </ol>
    </div>
  );
}
