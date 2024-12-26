import React, { useState } from "react";
import Input from "../component/Input";
import axios from "axios";
import { Link, useNavigate } from "react-router-dom";
import "./loginform.css"

export default function LoginForm({ checkLoginStatus  }) {
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [errorMsg, setErrorMsg] = useState("");
  const [formErrors, setFormErrors] = useState({
    email: "",
    password: "",
  });
  const [isButtonDisabled, setIsButtonDisabled] = useState(true);
  const navigate = useNavigate();

  // 이메일과 비밀번호 필드 블러 시 유효성 검사
  function handleBlur(field) {
    let errors = { ...formErrors };

    switch (field) {
      case "email":
        if (!email) {
          errors.email = "이메일을 입력해주세요.";
        } else if (!/\S+@\S+\.\S+/.test(email)) {
          errors.email = "※올바른 이메일 형식을 입력해주세요.※";
        } else {
          errors.email = ""; // 이메일 유효성 검사 성공
        }
        break;
      case "password":
        if (!password) {
          errors.password = "비밀번호를 입력해주세요.";
        } else if (password.length < 8) {
          errors.password = "※비밀번호는 최소 8자 이상이어야 합니다.※";
        } else {
          errors.password = ""; // 비밀번호 유효성 검사 성공
        }
        break;
    }
    setFormErrors(errors);

    // 모든 필드가 유효한지 체크하여 버튼 활성화/비활성화
    validateForm(errors);
  }

  // 폼 유효성 검사 함수
  function validateForm(errors) {
    if (errors && typeof errors === "object") {
      const hasErrors = Object.values(errors).some((error) => error !== "");
      setIsButtonDisabled(hasErrors); // 오류가 하나라도 있으면 버튼 비활성화
    }
  }

  const handleSubmit = async (e) => {
    e.preventDefault(); // 기본 폼 제출 동작 방지

    try {
      // axios를 사용해 로그인 데이터 전송
      const response = await axios.post("/api/login", { email, password });
      {
        withCredentials: true;
      } // 세션 쿠키 유지
      // 성공 처리
      if (response.status === 200) {
        alert("로그인 성공!");
        checkLoginStatus ();
        navigate("/"); // 홈 화면으로 리다이렉트
      }
    } catch (error) {
      // 로그인 실패
      alert("로그인 실패!");
      if (error.response && error.response.data) {
        setErrorMsg(error.response.data.msg); // 서버에서 반환된 오류 메시지 표시
        console.log("서버에서 반환된 오류 메시지 : " + errorMsg);
      } else {
        setErrorMsg("로그인 중 오류가 발생했습니다. 다시 시도해주세요.");
        console.log("리액트 오류 메시지 : " + errorMsg);
      }
    }
  };

  return (
    <div className="login-wrap">
    <div className="login">
      <h1>이메일로 시작하기</h1>
      <br/>
      <form onSubmit={handleSubmit} className="login-form">
        <Input
          label="이메일"
          type="email"
          placeholder="abc@tripjava.co.kr"
          name="email"
          id="email"
          value={email}
          onChange={(e) => setEmail(e.target.value)}
          onBlur={() => handleBlur("email")}
        />
        <span className="valid_text">{formErrors.email}</span>
        <br />
        <Input
          label="비밀번호"
          type="password"
          placeholder="비밀번호를 입력하세요."
          name="password"
          id="password"
          value={password}
          onChange={(e) => setPassword(e.target.value)}
          onBlur={() => handleBlur("password")}
        />
        <span className="valid_text">{formErrors.password}</span>
        <br />
        <button
          type="submit"
          disabled={isButtonDisabled}
          style={{
            backgroundColor: isButtonDisabled ? "#ccc" : "#4169e1",
            cursor: isButtonDisabled ? "not-allowed" : "pointer",
          }}
        >
          로그인
        </button>
      </form>
      <br/>
      <div className="regitpage">
        <div className="caption">계정이 없으신가요?</div>
        <Link to="/login/email/regit">
          <div className="text-button">이메일로 회원가입</div>
        </Link>
      </div>
    </div>
    </div>
  );
}
