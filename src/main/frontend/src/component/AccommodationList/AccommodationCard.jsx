import React from "react";
import { Link, useNavigate } from "react-router-dom"; // React Router를 사용한 링크 설정
import "./AccommodationCard.css";

function AccommodationCard({ data }) {
  console.log("AccommodationCard Data:", data); // 데이터 확인
  const { id, name, type, address, rating, price, imageUrl } = data;
  const navigate = useNavigate();


  const handleAccommodationDetail = () => {
    navigate(`/accommodation/${id}`);
  }

  return (
    <div className="accommodation-card" onClick={handleAccommodationDetail}>
      <img 
        src={imageUrl} 
        alt={`${name}`} 
        className="accommodation-image" 
      />

      <div className="accommodation-details">
        <h3>{name}</h3>
        <p>{type}</p>
        <p>{address}</p>
        <p>{rating}</p>
        <p className="price">{price.toLocaleString()}원</p>
      </div>
    </div>
  );
}

export default AccommodationCard;
