import React from "react";
import './Detail.css';

function ImageGallery({ imageUrls }) {
  if (!imageUrls || imageUrls.length === 0) {
    return <div>이미지가 없습니다.</div>;
  }

  return (
    <div className="image-gallery">
      {/* 첫 번째 이미지는 크게, 나머지는 동일한 크기 */}
      <img className="main-img1"
        src={imageUrls[0]}
        alt="갤러리 이미지 1"
      />
      
      {/* 나머지 이미지는 동일한 크기 */}
      {imageUrls.slice(1).map((url, index) => (
        <img className="main-imgother"
          key={index}
          src={url}
          alt={`갤러리 이미지 ${index + 2}`} // 첫 번째 이미지 제외하고 번호 조정
        />
      ))}
    </div>
  );
}

export default ImageGallery;
