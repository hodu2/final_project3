import React from "react";
import "./Detail.css";

export default function Menu() {
  return (
    <nav>
      <ol className="menu">
        <li><a href="#explain">숙소 설명</a></li>
        <li><a href="#service">서비스 및 부대시설</a></li>
        <li><a href="#location">위치</a></li>
        <li><a href="#review">리뷰</a></li>
      </ol>
    </nav>
  );
}
