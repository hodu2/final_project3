import React, { useEffect, useState } from "react";
import styles from "./ProfileInfo.module.css";
import axios from "axios";
import { useNavigate } from "react-router-dom";

export default function ProfileInfo({ user, setUser, token, setToken }) {
  const [userInfo, setUserInfo] = useState(null);
  const [isEditing, setIsEditing] = useState(false);
  const [formValues, setFormValues] = useState({});
  const [errorMsg, setErrorMsg] = useState("");
  const navigate = useNavigate();

  useEffect(() => {
    const fetchUserInfo = async () => {
      try {
        // 백엔드 API 호출
        const response = await axios.get(`/api/${user.userId}`);
        setUserInfo(response.data.user); // 백엔드 데이터 구조에 맞게 설정
        setFormValues(response.data.user);
      } catch (error) {
        console.error("사용자 정보 로드 실패:", error);
        setErrorMsg("사용자 정보를 불러오는 중 오류가 발생했습니다.");
      }
    };

    if (user) {
      fetchUserInfo();
    }
  }, [user]);
  
  // 입력 변경 핸들러
  const handleInputChange = (e) => {
    const { name, value } = e.target;
    setFormValues((prev) => ({
      ...prev,
      [name]: value,
    }));
  };

  // 수정 상태 토글 및 저장
  const handleUpdateAccount = async () => {
    if (isEditing) {
      // 저장 로직
      try {
        const response = await axios.post(
          `/api/${user.userId}/update`,
          formValues
        );
        if (response.status === 200) {
          alert("회원정보가 성공적으로 수정되었습니다.");
          setUserInfo(formValues); // 저장 후 상태 업데이트
          setIsEditing(false); // 수정 상태 종료
        }
      } catch (error) {
        console.error("회원정보 수정 실패:", error);
        alert("회원정보 수정 중 오류가 발생했습니다. 다시 시도해주세요.");
      }
    } else {
      setIsEditing(true); // 수정 상태 시작
    }
  };

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

    // 세션을 강제로 종료
    window.sessionStorage.clear(); // 세션 저장소 클리어
    window.localStorage.clear(); // 로컬 저장소 클리어
  };

  // 회원탈퇴 함수
  const handleDeleteAccount = async () => {
    const deleteAccountUrl = token
      ? `/api/${user.userId}/kakao-unlink?accessToken=${token}` // 소셜 로그인 유저의 경우
      : `/api/${user.userId}/delete/`; // 일반 로그인 유저의 경우
    
    if (window.confirm("정말로 탈퇴하시겠습니까?")) {
      try {
        const response = await axios.post(
          deleteAccountUrl,
          token ? { accessToken: token } : null
        );

        if (response.status === 200) {
          setUser(null);
          setToken(null);
          alert("회원탈퇴가 완료되었습니다.");
          clearBrowserData();
          // 탈퇴 후 메인 페이지로 이동
          navigate("/", { replace: true }); // replace: true로 세션을 새로 시작하도록 설정
        }
      } catch (error) {
        console.error("회원탈퇴 실패:", error);
        alert("회원탈퇴 중 오류가 발생했습니다. 다시 시도해주세요.");
      }
    }
  };

  // 전화번호 포맷팅 함수
  function formatPhoneNumber (num) {
    return num.replace(/(\d{3})(\d{3,4})(\d{4})/, "$1-$2-$3");
  };

  if (errorMsg) {
    return <p>{errorMsg}</p>;
  }

  if (!userInfo) {
    return <p>사용자 정보를 불러오는 중...</p>;
  }

  return (
    <div className={styles.mypageWrap}>
      <h1 className={styles.mypageHeader}>내 정보 관리</h1>
      <div className={styles.mypageContainer}>
        <div className={styles.fieldGroup}>
          <label className={styles.maypageLabel}>이메일</label>
          <input 
            name="email"
            value={formValues.email}
            onChange={handleInputChange}
            readOnly
            className={styles.readOnlyInput}
          />
        </div>
        <div className={styles.fieldGroup}>
          <label className={styles.label}>이름</label><br/>
          <input
            name="username"
            value={formValues.username}
            onChange={handleInputChange}
            readOnly={!isEditing}
            className={isEditing ? styles.mypageInput : styles.readOnlyInput}
          />
        </div>
        <div>
          <label className={styles.label}>휴대폰번호</label><br/>
          <input
            name="phone"
            value={isEditing ? formValues.phone : formatPhoneNumber(formValues.phone)}
            onChange={handleInputChange}
            readOnly={!isEditing}
            className={isEditing ? styles.mypageInput : styles.readOnlyInput}
          />
        </div>
        <button className={styles.button} onClick={handleUpdateAccount}>
          {isEditing ? "저장" : "수정"}
        </button>
        {isEditing && (
          <button
            className={styles.button}
            onClick={() => setIsEditing(false)}
          >
            취소
          </button>
        )}
      </div>
      <div className={styles.inlineMessage}>
          <span>더 이상 트립자바를 이용하기 원하지 않으십니까? </span>
          <button className={styles.linkButton} onClick={handleDeleteAccount}>
            회원탈퇴
          </button>
      </div>
    </div>
  );
}
