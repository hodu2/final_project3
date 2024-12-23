import "./ReviewList.css";

function ReviewHeader({ rating, reviewCount }) {
    return (
      <div className="review-header">
        <h2 id="review">리뷰</h2>
        {/* 평균 평점은 왼쪽 정렬 */}
        <div className="average-rating-container">
          <div className="css-yeouz0">
            <h2 className="css-4wprqg">평균 평점&nbsp;
              <svg
              width="30"
              height="30"
              viewBox="0 0 20 20"
              fill="none"
              xmlns="http://www.w3.org/2000/svg"
              className="css-olperf"
            >
              <path
                d="M14.229 12.1l.853 3.91c.06.502-.069.896-.386 1.18-.434.39-.958.352-1.294.21-.209-.114-1.343-.813-3.404-2.095-2.061 1.282-3.196 1.981-3.404 2.096-.336.14-.86.18-1.295-.211-.317-.284-.445-.678-.386-1.18l.853-3.91-.004-.003.001-.003c-1.817-1.651-2.806-2.56-2.965-2.724-.247-.267-.403-.786-.215-1.309.188-.523.671-.746.954-.78.19-.021 1.472-.146 3.848-.373H7.38c.965-2.358 1.49-3.621 1.576-3.791.128-.255.54-.617 1.042-.617s.864.332 1.018.573c.1.198.634 1.477 1.6 3.835 2.375.227 3.658.352 3.847.374.283.033.766.256.954.78.188.522.032 1.04-.215 1.308-.16.165-1.15 1.074-2.969 2.727l-.004.003z"
                fill="current"
              ></path>
            </svg>
            {rating}</h2>
          </div>
          <span className="css-1m0qn1y">
            {reviewCount}명 평가 ・ {reviewCount}개 리뷰
          </span>
        </div>
        <br/>
      </div>
    );
  }
  
  export default ReviewHeader;
  