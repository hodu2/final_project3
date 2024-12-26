import React, { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import axios from "axios";
import "./main.css";
import { images } from "../../img.js";
import { Link } from 'react-router-dom';
import Snowfall from 'react-snowfall';
import keyimg from "../../img/main-content/keyimage.png";

const Main = () => {
  const navigate = useNavigate(); // React Router의 useNavigate 훅 사용
  const [accommodations, setAccommodations] = useState([]); // 숙소 데이터 상태

  // STS에서 숙소 데이터 가져오기
  useEffect(() => {
    axios
      .get("http://localhost:9090/api/accommodations") // 백엔드 엔드포인트에 맞게 수정
      .then((response) => {
        setAccommodations(response.data.slice(0,12)); // 응답 데이터를 상태에 저장
      });
  }, []);

  // 인기 여행지 데이터
  const popularDestinations = [
      { name: "부산", image: images.busan, link: "/destination/busan" },
      { name: "단양", image: images.danyang, link: "/destination/danyang" },
      { name: "강릉", image: images.gangneung, link: "/destination/gangneung" },
      { name: "가평", image: images.gapyeong, link: "/destination/gapyeong" },
      { name: "경주", image: images.gyeongju, link: "/destination/gyeongju" },
      { name: "인천", image: images.incheon, link: "/destination/incheon" },
      { name: "서울", image: images.seoul, link: "/destination/seoul" },
      { name: "제주", image: images.jeju, link: "/destination/jeju" },
      { name: "전주", image: images.jeonju, link: "/destination/jeonju" },
      { name: "남해", image: images.namhae, link: "/destination/namhae" },
      { name: "포천", image: images.pocheon, link: "/destination/pocheon" },
      { name: "평창", image: images.pyeongchang, link: "/destination/pyeongchang" },
      { name: "속초", image: images.sokcho, link: "/destination/sokcho" },
      { name: "여수", image: images.yeosu, link: "/destination/yeosu" },
    ];

  // 인기 여행지 카드 컴포넌트
  const DestinationCard = ({ destination }) => (
    <div className="location-img">
      <Link to={destination.link}>
        <img src={destination.image} alt={destination.name} />
        <span>{destination.name}</span>
      </Link>
    </div>
  );

  // 숙소 카드 컴포넌트
  const AccommodationCard = ({ accommodation }) => (
    <div
      className="recommendation-img"
      onClick={() => navigate(`/accommodation/${accommodation.accomId}`)} // 클릭 시 상세페이지로 이동
      style={{ cursor: "pointer" }} // 클릭 가능함을 나타내기 위해 포인터 스타일 추가
    >
      <img src={accommodation.representativeImage.split(',')[0].trim()} alt={accommodation.accomName} /><br/>
      <span className="accom-type">{accommodation.type}</span>
      <span className="accom-name">{accommodation.accomName}</span>
      <span className="accom-rating">평점 : {accommodation.averageRating ? accommodation.averageRating.toFixed(1) : "N/A"}</span>
      <span className="accom-price">
        최저가 : {accommodation.cheapestPrice ? accommodation.cheapestPrice.toLocaleString() : "N/A"}
      </span>
    </div>
  );

  return (
    <div className="main-wrap">
    <Snowfall/>
    <img className="keyimg" src="/src/img/main-content/keyimage.png"/>
      <div className="main-content1">
        <h2>국내 인기 여행지</h2>
        <div className="location-container">
          {popularDestinations.map((destination, index) => (
            <DestinationCard key={index} destination={destination} />
          ))}
        </div>
      </div>
      <br/><br/>
      <div className="main-content2">
        <h2>인기 숙소 추천</h2>
        <div className="recommendation-container">
          {accommodations.map((accommodation) => (
            <AccommodationCard key={accommodation.accomId} accommodation={accommodation} />
          ))}
        </div>
      </div>
    </div>
  );
};

export default Main;
