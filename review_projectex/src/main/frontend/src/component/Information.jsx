import React, { useState, useEffect } from "react";
import axios from "axios";
import "./Detail.css";
import { useNavigate } from "react-router-dom";

export default function Information({ accommodation, userId }) {
  const navigate = useNavigate();
  // 데이터 유효성 검증
  if (!accommodation?.accommodation) {
    console.error("Invalid accommodation data:", accommodation);
    return <div>Loading...</div>;
  }

  // 중첩된 accommodation 객체 구조 해체
  const {
    accomId,
    accomName,
    address,
    description,
    accomTel,
    avaDatesStart,
    avaDatesEnd,
  } = accommodation.accommodation;

  const [isFavorited, setIsFavorited] = useState(false);

  useEffect(() => {
    const checkFavoriteStatus = async () => {
      try {
        const response = await axios.get(
          `http://localhost:9090/api/wishlist/${userId}/${accomId}`, // userId 사용
          { withCredentials: true }
        );
        setIsFavorited(response.data);
      } catch (error) {
        console.error("찜 상태 확인 실패:", error);
      }
    };

    if (accomId && userId) { // userId로 조건 확인
      checkFavoriteStatus();
    }
  }, [accomId, userId]); // userId로 의존성 설정

  const handleFavoriteClick = async () => {
    if (!userId) { // userId 확인
      alert("로그인이 필요합니다.");
      navigate("/login");
      return;
    }
    console.log("userId:", userId, "accomId:", accomId);
    try {
      if (isFavorited) {
        await axios.delete(
          `http://localhost:9090/api/wishlist/${userId}/${accomId}`, // userId 사용
          { withCredentials: true }
        );
      } else {
        await axios.post(
          `http://localhost:9090/api/wishlist`,
          { accomId },
          { withCredentials: true }
        );
      }
      setIsFavorited(!isFavorited);
    } catch (error) {
      console.error("찜 상태 변경 실패:", error);
    }
  };

  return (
    <div className="information">
      <h1 className="accomname">{accomName}</h1>
      <div className="favorite-section">
          <button className="favorite-button" onClick={handleFavoriteClick}>
            {isFavorited ? "❤️" : "🤍"}
          </button>
      </div>
      <div className="detail-container">
        <br />
        <p>{address}</p>
        <h3 id="explain">숙소 설명</h3>
        <p>{description}</p>
        <h3>숙소 전화번호</h3>
        <p>{accomTel}</p>
      </div>
    </div>
  );
}
