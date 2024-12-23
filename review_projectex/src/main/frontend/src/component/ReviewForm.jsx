import React, { useEffect, useState } from "react";
import axios from "axios";
import "./ReviewList.css";

function ReviewForm({ accommodationId, editingReview, setEditingReview, fetchReviews }) {
  const [reviewText, setReviewText] = useState(""); // 리뷰 내용
  const [rating, setRating] = useState(5); // 평점
  const [reviewDate, setReviewDate] = useState(""); // 작성 날짜
  const [reviewImagePath, setReviewImagePath] = useState(null); // 이미지 경로
  const [userId, setUserId] = useState(null); // 로그인된 사용자 ID

  // 디버깅: editingReview 값 확인
  useEffect(() => {
    console.log("editingReview:", editingReview); // editingReview 값 출력
    if (editingReview) {
      setReviewText(editingReview.reviewText);
      setRating(editingReview.rating);
      setReviewDate(editingReview.reviewDate);
      // reviewImagePath가 "default-image.png"일 경우 null로 처리
      setReviewImagePath(editingReview.reviewImagePath === "default-image.png" ? null : editingReview.reviewImagePath);
    }

    // 로그인된 사용자 정보 가져오기
    const fetchUser = async () => {
      try {
        const response = await axios.get("http://localhost:9090/api/session/user",{ withCredentials: true });
        if (response.data) {
          setUserId(response.data.userId); // 사용자 ID 설정
        } else {
          alert("로그인되어 있지 않습니다.");
        }
      } catch (error) {
        console.error("로그인 정보 조회 실패:", error);
      }
    };

    fetchUser();
  }, [editingReview]);

  const handleSubmit = async (e) => {
    e.preventDefault();

    if (!userId) {
      alert("로그인 후 리뷰를 작성할 수 있습니다.");
      return;
    }

    // 디버깅: reviewText, rating, reviewImagePath 값 확인
    console.log("reviewText:", reviewText); // reviewText 값 출력
    console.log("rating:", rating); // rating 값 출력
    console.log("reviewImagePath:", reviewImagePath); // reviewImagePath 값 출력

    // reviewImagePath가 null이거나 "default-image.png"일 경우 null로 처리
    const finalImagePath = (reviewImagePath && reviewImagePath !== "default-image.png")
      ? reviewImagePath
      : null;

    const requestData = {
      reviewText: reviewText,
      rating: rating,
      accommodationId: accommodationId,
      userId: userId, // 세션에서 가져온 유저 ID 사용
      reviewImagePath: finalImagePath // reviewImagePath가 없으면 null
    };

    // 디버깅: requestData 값 확인
    console.log("requestData:", requestData); // 보내는 데이터 출력

    try {
      if (editingReview) {
        await axios.put(
          `http://localhost:9090/api/reviews/${editingReview.reviewId}`,
          requestData,
          { withCredentials: true } 
        );
        alert("리뷰가 수정되었습니다.");
      } else {
        await axios.post(
          "http://localhost:9090/api/reviews",
          requestData,
          { withCredentials: true } 
        );
        alert("리뷰가 작성되었습니다.");
      }

      // 폼 초기화
      setReviewText("");
      setRating(5);
      setReviewImagePath(null); // 이미지 경로 초기화
      setEditingReview(null);
      fetchReviews();

      // 리뷰 작성 후 상세 페이지로 이동 (새로 고침 없이)
      window.location.href = `/accommodation/${accommodationId}`; // 상세 페이지로 이동
    } catch (error) {
      console.error("리뷰 저장 실패:", error);
    }
  };

  return (
    <div className="review-form">
      <h2>{editingReview ? "리뷰 수정" : "리뷰 작성"}</h2>
      <form onSubmit={handleSubmit}>
        <div className="review-write"> 
          <label>리뷰 내용 : </label><br/>
          <textarea 
            value={reviewText}
            onChange={(e) => setReviewText(e.target.value)}
            required
          />
        </div>
        <div className="review-rating">
          <label>평점 : </label>
          <select value={rating.toFixed(1)} onChange={(e) => setRating(parseFloat(e.target.value))}>
            {[...Array(11)].map((_, index) => {
              const value = (index / 2).toFixed(1);
              return (
                <option key={value} value={value}>
                  {value}
                </option>
              );
            })}
          </select>
        </div>

        {reviewDate && (
          <div className="write-date">
            <p> 작성 날짜 : {new Date(reviewDate).toLocaleString()}</p>
          </div>
        )}
        <div className="write-btn">
        <button type="submit">{editingReview ? "수정" : "작성"}</button>
        </div>
      </form>
    </div>
  );
}

export default ReviewForm;
