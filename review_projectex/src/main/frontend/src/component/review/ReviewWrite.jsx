import React, { useState, useEffect } from "react";
import axios from "axios";
import "./Review.css";

  function ReviewWrite({ accommodationId, editingReview, setEditingReview }) {
    const [content, setContent] = useState("");
    const [rating, setRating] = useState(5);
    const [file, setFile] = useState(null);
    const [reviewDate, setReviewDate] = useState("");
    const [reviewImagePath, setReviewImagePath] = useState("");
  
    useEffect(() => {
      if (editingReview) {
        setContent(editingReview.reviewText);
        setRating(editingReview.rating);
        setReviewDate(editingReview.reviewDate); // 리뷰 작성 날짜 추가
        setReviewImagePath(editingReview.reviewImagePath); // 이미지 경로 추가
      }
    }, [editingReview]);
  
    const handleSubmit = async (e) => {
      e.preventDefault();
  
      const formData = new FormData();
      formData.append("content", content);
      formData.append("rating", rating);
      formData.append("accommodationId", accommodationId); // 숙소 ID 추가
  
      if (file) {
        formData.append("file", file);
      }
  
      try {
        if (editingReview) {
          // 수정 요청
          await axios.put(
            `http://localhost:9090/api/reviews/${editingReview.reviewId}`,
            formData
          );
          alert("리뷰가 수정되었습니다.");
        } else {
          // 새로운 리뷰 생성
          await axios.post("http://localhost:9090/api/reviews", formData, {
            headers: { "Content-Type": "multipart/form-data" },
          });
          alert("리뷰가 작성되었습니다.");
        }
  
        // 폼 초기화
        setContent("");
        setRating(5);
        setFile(null);
        setEditingReview(null); // 수정 종료 후 초기화
      } catch (error) {
        console.error("리뷰 저장 실패:", error);
      }
    };

  return (
    <div className="review-write">
      <h2>{editingReview ? "리뷰 수정" : "리뷰 작성"}</h2>
      <form onSubmit={handleSubmit}>
        <div className="review-rating">
          <p>숙소는 만족하셨나요?</p>
          <label>평점 : </label>
          <select
            value={rating}
            onChange={(e) => setRating(Number(e.target.value))}
          >
            <option value={1}>⭐</option>
            <option value={2}>⭐⭐</option>
            <option value={3}>⭐⭐⭐</option>
            <option value={4}>⭐⭐⭐⭐</option>
            <option value={5}>⭐⭐⭐⭐⭐</option>
          </select>
        </div>
        <hr/>
        
        <div className="coment">
          <p>숙소의 어떤 점이 좋았나요?</p>
          <label>리뷰 내용 :</label>
          <br/>
          <textarea
            value={content}
            onChange={(e) => setContent(e.target.value)}
            required
          />
        </div>
        <br/><br/>
        <div>
          <label>숙소 사진 업로드 : </label>
          <input
            placeholder="📷 사진 첨부하기"
            type="file"
            accept="image/*"
            onChange={(e) => setFile(e.target.files[0])}
          />
        </div>
        {/* 이미지와 날짜 표시 부분 */}
        {reviewImagePath && (
          <div>
            <h4>기존 사진:</h4>
            {/* 이미지가 여러 개일 경우 처리 */}
            {reviewImagePath.split(",").map((imgUrl, index) => (
              <img key={index} src={imgUrl} alt={`Review Image ${index + 1}`} />
            ))}
          </div>
        )}

        {reviewDate && (
          <div>
            <h4>작성 날짜:</h4>
            <p>{new Date(reviewDate).toLocaleString()}</p>
          </div>
        )}
        <br/>
        <div className="write-button">
          <button className="submit" type="submit">{editingReview ? "수정" : "작성"}</button>
        </div>
      </form>
    </div>
  );
}

export default ReviewWrite;