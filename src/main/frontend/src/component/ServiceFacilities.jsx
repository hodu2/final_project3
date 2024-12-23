import React from "react";
import { images } from "../img.js"; // 이미지 파일 import
import "./Detail.css";

export default function ServiceFacilities({ amenities }) {
  const amenityIcons = {
    TV: images.tv,
    BATH: images.bath,
    INTERNET: images.internet,
    REFRIGERATOR: images.refrigerator,
    DRYER: images.dryer,
    AIRCONDITIONER: images.airconditioner,
    SOFA: images.sofa,
    COOK: images.cook,
    TABLE: images.table,
    BED: images.bed,
    FITNESS: images.fitness,
    SPA: images.spa,
    RESTAURANT: images.restaurant,
    PARKING: images.parking,
    POOL: images.pool,
    BARBEQUE: images.barbeque,
    LAUNDRY: images.laundry,
    ELEVATOR: images.elevator,
    PETFRIENDLY: images.petfriendly,
    BREAKFAST: images.restaurant, 
    NOSMOKING: images.nosmoking,
    OCEANVIEW: images.pool,
    CITYVIEW: images.tv,
    MOUNTAINVIEW: images.spa,
  };

  const defaultIcon = images.defaultIcon || images.tv;

  return (
    <div className="service">
      <br/>
      <h2 id="service">편의 시설</h2>
      <div className="service-list">
        {amenities.map((amenity, index) => {
          const icon = amenityIcons[amenity] || defaultIcon;
          return (
            <div key={index} className="service-item">
              <img src={icon} alt={amenity}/>
              {amenity}
            </div>
          );
        })}
      </div>
    </div>
  );
}
