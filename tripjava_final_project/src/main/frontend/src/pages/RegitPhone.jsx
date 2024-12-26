import React, { useState, useEffect } from "react";
import { useNavigate } from "react-router-dom";
import axios from "axios";
import Input from "../component/Input";
import style from "./RegitPhone.module.css";

function RegitPhone({ user }) {
  const [phone, setPhone] = useState("");
  const [error, setError] = useState("");
  const [isButtonDisabled, setIsButtonDisabled] = useState(true);
  const navigate = useNavigate();

  // 입력값 검증
  function handleBlur() {
    if (!phone) {
      setError("휴대폰 번호를 입력해주세요.");
    } else if (!/^\d{10,11}$/.test(phone)) {
      setError("휴대폰 번호는 10~11자리 숫자만 가능합니다.");
    } else {
      setError("");
    }
    validateForm();
  }

  // 폼 유효성 검증
  function validateForm() {
    setIsButtonDisabled(!/^\d{10,11}$/.test(phone));
  }

  // 전화번호 입력 변화 감지
  useEffect(() => {
    validateForm();
  }, [phone]);

  const handleSubmit = async (e) => {
    e.preventDefault();
    setError("");

    if (!/^\d{10,11}$/.test(phone)) {
      setError("전화번호는 숫자만 입력해 주세요 (10~11자리).");
      return;
    }

    try {
      if (!user || !user.userId) {
        setError("로그인이 필요합니다.");
        return;
      }

      await axios.post(
        `/api/${user.userId}/register-phone`, // 요청 경로
        { phone }, // 요청 body
        { withCredentials: true }
      );
      alert("회원가입이 완료되었습니다!");
      navigate("/");
    } catch (err) {
      console.error("전화번호 등록 실패:", err);
      setError("전화번호 등록에 실패했습니다. 다시 시도해주세요.");
    }
    
  };

  return (
    <div className={style.phoneWrap}>
    <div className={style.container}>
      <h1 className={style.header}>휴대폰 입력</h1>
      <p className={style.headerP}>원활한 서비스 제공을 위해 전화번호를 입력해 주세요</p>
      <form className={style.phoneForm} onSubmit={handleSubmit}>
        <Input className={style.phoneInput}
          label="휴대폰 번호"
          type="text"
          name="phone"
          id="phone"
          value={phone}
          placeholder="숫자만 입력 (10~11자리)"
          onChange={(e) => setPhone(e.target.value)}
          onBlur={handleBlur}
          required
        />
        <span className={style.valid_text}>{error}</span>
        <div className={style.regit}>
          <button className={style.phoneButton}
            type="submit"
            disabled={isButtonDisabled}
            style={{
              backgroundColor: isButtonDisabled ? "#ccc" : "#4169e1",
              cursor: isButtonDisabled ? "not-allowed" : "pointer",
            }}
          >
            회원 가입 완료
          </button>
        </div>
      </form>
    </div>
    </div>
  );
}

export default RegitPhone;
