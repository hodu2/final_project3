import React, { useEffect, useState } from "react";
import axios from "axios";
import ReviewForm from "./ReviewForm"; // 리뷰 폼 (수정용)
import ReviewHeader from "./ReviewHeader"; // 리뷰 헤더 (평균 평점 등 표시)
import "./ReviewList.css";

function ReviewList({ accomId }) {
  const [reviews, setReviews] = useState([]);
  const [rating, setRating] = useState(0);
  const [reviewCount, setReviewCount] = useState(0);
  const [editingReview, setEditingReview] = useState(null); // 수정할 리뷰 상태
  const [sortOption, setSortOption] = useState("newest"); // 기본 정렬: 최신순

  useEffect(() => {
    fetchReviews();
  }, [accomId]);

  // 리뷰 데이터 가져오기
  const fetchReviews = () => {
    axios
      .get(`http://localhost:9090/api/reviews/accommodation/${accomId}`)
      .then((response) => {
        if (Array.isArray(response.data)) {
          setReviews(response.data);
          calculateReviewStats(response.data);
        } else {
          console.error("리뷰 데이터가 배열이 아닙니다:", response.data);
        }
      })
      .catch((error) => console.error("리뷰 데이터 불러오기 실패:", error));
  };

  // 리뷰 통계 계산 (평균 평점, 리뷰 개수)
  const calculateReviewStats = (reviews) => {
    const totalReviews = reviews.length;
    const totalRating = reviews.reduce((acc, review) => acc + review.rating, 0);
    const avgRating =
      totalReviews > 0 ? (totalRating / totalReviews).toFixed(1) : 0;

    setReviewCount(totalReviews);
    setRating(avgRating);
  };

  // 정렬 옵션 변경
  const handleSortChange = (event) => {
    setSortOption(event.target.value);
  };

  // 정렬 함수 (최신순, 별점 높은순)
  const sortedReviews = () => {
    let sorted = [...reviews];
    if (sortOption === "newest") {
      sorted.sort((a, b) => new Date(b.reviewDate) - new Date(a.reviewDate)); // 최신순
    } else if (sortOption === "highestRating") {
      sorted.sort((a, b) => b.rating - a.rating); // 별점 높은순
    }
    return sorted;
  };

  // 리뷰 삭제
  const handleDelete = (reviewId) => {
    if (window.confirm("리뷰를 삭제하시겠습니까?")) {
      axios
        .delete(`http://localhost:9090/api/reviews/${reviewId}`)
        .then(() => {
          fetchReviews();
          alert("리뷰가 삭제되었습니다.");
          setEditingReview(null); // 삭제 후 편집 상태 초기화
        })
        .catch((error) => console.error("리뷰 삭제 실패:", error));
    }
  };

  // 리뷰 수정
  const handleEdit = (review) => {
    setEditingReview(review); // 수정할 리뷰 설정
  };

  return (
    <div className="review-list">
      {/* 헤더 */}
      <ReviewHeader rating={rating} reviewCount={reviewCount} />

      {/* 정렬 선택 드롭다운 */}
      <div className="drop-down">
        <select value={sortOption} onChange={handleSortChange}>
          <option value="newest">최신순</option>
          <option value="highestRating">별점 높은순</option>
        </select>
      </div>
      <br/><br/>
      <div className="review-list">
      {/* 리뷰 목록 표시 */}
      <ul>
        {sortedReviews().map((review) => (
          <li key={review.reviewId} style={{ marginBottom: "20px" }}>
            <div style={{ display: "flex", justifyContent: "space-between" }}>
              <div className="review-margin">
              <div>
                <p>🙎‍♀️ <strong>{review.username}</strong>&nbsp;&nbsp;
                평점 : {review.rating.toFixed(1)}<br/>작성 날짜 : {new Date(review.reviewDate).toLocaleDateString()}</p> {/* 작성자 이름 출력 */}
                <br/>

                {/* 리뷰에 이미지가 있을 경우 */}
                {review.reviewImagePath && review.reviewImagePath.trim() !== "" && (
                  <div>
                    {review.reviewImagePath.split(",").map((imgUrl, index) => (
                      <img
                        className="review-img"
                        key={index}
                        src={imgUrl}
                        alt={`Review Image ${index + 1}`}
                      />
                    ))}
                  </div>
                )}
                <br/>
                <p className="review-text">{review.reviewText}</p>
              </div>
              
            
            <br/>
            <div className="review-button">
              <button
                style={{ marginRight: "10px" }}
                onClick={() => handleEdit(review)}
              >
                수정
              </button>
              <button onClick={() => handleDelete(review.reviewId)}>삭제</button>
            </div>
            </div>
            </div>
            <br/><br/>

            {/* 수정 폼은 해당 리뷰 아래에 나타나도록 */}
            {editingReview && editingReview.reviewId === review.reviewId && (
              <div style={{ marginTop: "20px" }}>
                <ReviewForm
                  accommodationId={accomId}
                  fetchReviews={() => fetchReviews(accomId)}
                  editingReview={editingReview}
                  setEditingReview={setEditingReview}
                />
              </div>
            )}
          </li>
        ))}
      </ul>
    </div>
    </div>
  );
}

export default ReviewList;
