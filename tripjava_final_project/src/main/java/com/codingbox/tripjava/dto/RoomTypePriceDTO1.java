package com.codingbox.tripjava.dto;

import com.codingbox.tripjava.entity.RoomTypePrice;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RoomTypePriceDTO1 {
    private String roomType; // 방 타입
    private int maxGuests;   // 최대 숙박 인원
    private int roomPrice;   // 방 가격

    // Constructor for transforming RoomTypePrice to DTO
    public RoomTypePriceDTO1(RoomTypePrice roomTypePrice) {
        if (roomTypePrice == null) {
            throw new IllegalArgumentException("RoomTypePrice cannot be null");
        }

        // Null-safe 변환
        this.roomType = (roomTypePrice.getRoomType() != null) 
                ? roomTypePrice.getRoomType().toString() 
                : "UNKNOWN"; // RoomType이 null인 경우 "UNKNOWN"으로 설정
        this.maxGuests = roomTypePrice.getMaxGuests();
        this.roomPrice = roomTypePrice.getRoomPrice();
    }
}

