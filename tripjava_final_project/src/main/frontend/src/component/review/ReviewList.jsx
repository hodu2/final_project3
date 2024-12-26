import React from "react";
import { useNavigate } from "react-router-dom";
import "./Review.css";

export default function Reviews() {
  const navigate = useNavigate();

  const reviews = [
    {
      username: "사용자 이름",
      rating: "★★★★★",
      images: [
        "http://tong.visitkorea.or.kr/cms/resource/45/2705645_image2_1.jpg",
        "http://tong.visitkorea.or.kr/cms/resource/45/2705645_image2_1.jpg",
      ],
      text: `Contrary to popular belief, Lorem Ipsum is not simply random text. It has roots in a
      piece of classical Latin literature from 45 BC, making it over 2000 years old.`,
    },
    {
      username: "사용자 이름",
      rating: "★★★★★",
      images: [
        "http://tong.visitkorea.or.kr/cms/resource/45/2705645_image2_1.jpg",
        "http://tong.visitkorea.or.kr/cms/resource/45/2705645_image2_1.jpg",
      ],
      text: `Contrary to popular belief, Lorem Ipsum is not simply random text. It has roots in a
      piece of classical Latin literature from 45 BC, making it over 2000 years old.`,
    },
  ];
  
  const handReviewWrite = () => {
    navigate("/review-write");
  };

  return (
    <div className="review">
      <h3 id="review">리뷰</h3>
      <div className="coment">
        <button  onClick={handReviewWrite}>작성하기 ✏️</button>
      </div>
      {reviews.map((review, index) => (
        <div className="coment" key={index}>
          <p>{review.username}</p>
          <p>{review.rating}</p>
          <div>
            {review.images.map((image, idx) => (
              <img key={idx} src={image} alt={`리뷰 이미지 ${idx + 1}`} />
            ))}
          </div>
          <p>{review.text}</p>
        </div>
      ))}
    </div>
  );
}
