import React, { useState, useEffect } from "react";
import Input from "../component/Input.jsx";
import { useNavigate } from "react-router-dom";
import axios from "axios";
import "./loginform.css";

export default function RegitForm() {
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [confirmPassword, setConfirmPassword] = useState("");
  const [username, setUsername] = useState("");
  const [phone, setPhone] = useState("");
  const [errorMsg, setErrorMsg] = useState("");
  const [formErrors, setFormErrors] = useState({
    email: "",
    password: "",
    confirmPassword: "",
    username: "",
    phone: "",
  });
  const [isButtonDisabled, setIsButtonDisabled] = useState(true);
  const navigate = useNavigate();

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
          errors.password = "※비밀번호를 입력해주세요.※";
        } else if (password.length < 8) {
          errors.password = "※비밀번호는 최소 8자 이상이어야 합니다.※";
        } else {
          errors.password = ""; // 비밀번호 유효성 검사 성공
        }
        break;
      case "confirmPassword":
        if (!confirmPassword) {
          errors.confirmPassword = "비밀번호 확인을 입력해주세요.";
        } else if (confirmPassword !== password) {
          errors.confirmPassword = "※비밀번호가 일치하지 않습니다.※";
        } else {
          errors.confirmPassword = ""; // 비밀번호 확인 일치
        }
        break;
      case "username":
        if (!username) {
          errors.username = "※이름을 입력해주세요.※";
        } else {
          errors.username = ""; // 이름 유효성 검사 성공
        }
        break;
      case "phone":
        if (!phone) {
          errors.phone = "휴대폰 번호를 입력해주세요.";
        } else if (!/^\d{10,11}$/.test(phone)) {
          errors.phone = "※휴대폰 번호는 10~11자리 숫자만 가능합니다.※";
        } else {
          errors.phone = "";
        }
        break;
    }

    setFormErrors(errors);
    validateForm(errors);
  }

  function validateForm(errors) {
    // errors가 객체인 경우에만 values 호출
    if (errors && typeof errors === "object") {
      const hasErrors = Object.values(errors).some((error) => error !== "");
      setIsButtonDisabled(hasErrors); // 오류가 하나라도 있으면 버튼 비활성화
    }
  }

  const handleSubmit = async (e) => {
    e.preventDefault(); // 기본 폼 제출 동작 방지

    try {
      // axios를 사용해 로그인 데이터 전송
      const response = await axios.post("/api/regit", {
        email,
        password,
        username,
        phone,
      });

      // 성공 처리
      if (response.status === 200) {
        alert("회원가입 성공!");
        navigate("/login/email");
        console.log("Response:", response.data);
      }
    } catch (error) {
      // 로그인 실패
      alert("회원가입 실패!");
      if (error.response && error.response.data) {
        setErrorMsg(error.response.data.msg); // 서버에서 반환된 오류 메시지 표시
        console.log("서버에서 반환된 오류 메시지 : " + errorMsg);
      } else {
        console.log(error + ", " + error.response);
        setErrorMsg("회원가입중 오류가 발생했습니다. 다시 시도해주세요.");
        console.log("리액트 오류 메시지 : " + errorMsg);
      }
    }
  };

  useEffect(() => {
    // 입력값이 변경될 때마다 폼 유효성 검사를 다시 수행
    validateForm();
  }, [email, password, confirmPassword, username]);

  return (
    <div className="regit-wrap">
      <div className="regit">
        <h1>필수 정보 입력</h1>
        <p>가입을 위해 필수 정보를 입력해 주세요.</p>
        <form onSubmit={handleSubmit} className="regit-form">
          <Input
            label="이메일 : "
            type="email"
            name="email"
            id="email"
            value={email}
            placeholder="abc@tripjava.co.kr"
            onChange={(e) => setEmail(e.target.value)}
            onBlur={() => handleBlur("email")}
          />
          <span className="valid_text">{formErrors.email}</span>
          <br />
          <Input
            label="비밀번호 : "
            type="password"
            name="password"
            id="password"
            value={password}
            placeholder="최소 8자 이상 입력해주세요."
            onChange={(e) => setPassword(e.target.value)}
            onBlur={() => handleBlur("password")}
          />
          <span className="valid_text">{formErrors.password}</span>
          <br />
          <Input
            label="비밀번호 확인 : "
            type="password"
            name="confirmPassword"
            id="confirmPassword"
            value={confirmPassword}
            placeholder="위 비밀번호와 동일하게 입력해주세요."
            onChange={(e) => setConfirmPassword(e.target.value)}
            onBlur={() => handleBlur("confirmPassword")}
          />
          <span className="valid_text">{formErrors.confirmPassword}</span>
          <br />
          <Input
            label="이름 : "
            type="text"
            name="username"
            id="username"
            value={username}
            placeholder="이름을 입력해주세요."
            onChange={(e) => setUsername(e.target.value)}
            onBlur={() => handleBlur("username")}
          />
          <span className="valid_text">{formErrors.username}</span>
          <br/>
          <Input
            label="휴대폰 번호 : "
            type="text"
            name="phone"
            id="phone"
            value={phone}
            placeholder="숫자만 입력 (10~11자리)"
            onChange={(e) => setPhone(e.target.value)}
            onBlur={() => handleBlur("phone")}
          />
          <span className="valid_text">{formErrors.phone}</span>
          <br />
          <div>
            <button className="complete"
              type="submit"
              disabled={isButtonDisabled}
              style={{
                backgroundColor: isButtonDisabled ? "#ccc" : "#4169e1",
                cursor: isButtonDisabled ? "not-allowed" : "pointer",
              }}
            >
              회원가입
            </button>
          </div>
        </form>
      </div>
    </div>
  );
}
