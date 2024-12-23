import React, { useEffect, useState } from "react";
import axios from "axios";
import { useParams } from "react-router-dom";
import Menu from "./Menu.jsx";
import Information from "./Information.jsx";
import Reservation from "./Reservation.jsx";
import ServiceFacilities from "./ServiceFacilities.jsx";
import ReviewList from "./ReviewList.jsx";
import ReviewForm from "./ReviewForm";
import Location from "./Location.jsx";
import ImageGallery from "./ImageGallery.jsx";
import "./Detail.css";

function AccommodationDetailPage({ userId }) {
  const { accomId } = useParams();
  const [accommodation, setAccommodation] = useState(null);
  const [reviews, setReviews] = useState([]);
  const [editingReview, setEditingReview] = useState(null); // 수정 중인 리뷰 상태

  useEffect(() => {
    if (!accomId) return;

    // 유저 정보 세션 저장
    if (userId) sessionStorage.setItem("userId", userId);

    // 숙소 정보 가져오기
    const accomUrl = userId
      ? `http://localhost:9090/api/accommodation/${accomId}?userId=${userId}`
      : `http://localhost:9090/api/accommodation/${accomId}`;
    axios
      .get(accomUrl)
      .then((response) => {
        setAccommodation(response.data);

        // 세션에 숙소 아이디와 이미지 저장
        sessionStorage.setItem("accomId", response.data.accommodation.accomId);
        sessionStorage.setItem("image", response.data.accommodation.images);
      })
      .catch((error) => console.log(error));

    // 리뷰 데이터 가져오기
    fetchReviews();
  }, [accomId, userId]);

  // 리뷰 목록 새로고침
  const fetchReviews = () => {
    axios
      .get(`http://localhost:9090/api/reviews/${accomId}`)
      .then((response) => setReviews(response.data))
      .catch((error) => console.log(error));
  };

  if (!accommodation) {
    return <div>로딩 중...</div>;
  }

  const imageUrls = accommodation.accommodation.images[0]
    ? accommodation.accommodation.images[0].split(",")
    : [];
  const rating = accommodation.accommodation.rating || 0;
  const reviewCount = accommodation.accommodation.reviewCount || 0;

  return (
    <div className="accommodation-detail-page">
      <Menu className="menu" />
      
      <ImageGallery imageUrls={imageUrls} />

      <div className="info-wrap">
        <Information accommodation={accommodation} userId={userId} />
        <Reservation accommodation={accommodation} />
      </div>

      <ServiceFacilities amenities={accommodation.accommodation.amenities} />

      <Location
        latitude={accommodation.accommodation.latitude}
        longitude={accommodation.accommodation.longitude}
      />

      {/* 리뷰 목록 */}
      <ReviewList reviews={reviews} accomId={accomId} />

      {/* 리뷰 작성 폼 */}
      <ReviewForm
        accommodationId={accomId}
        editingReview={editingReview}
        setEditingReview={setEditingReview}
        fetchReviews={fetchReviews}
      />
    </div>
  );
}

export default AccommodationDetailPage;
