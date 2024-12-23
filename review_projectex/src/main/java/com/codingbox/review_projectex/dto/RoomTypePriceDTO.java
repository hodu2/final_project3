package com.codingbox.review_projectex.dto;

import com.codingbox.review_projectex.entity.RoomTypePrice;
import com.codingbox.review_projectex.enums.RoomType;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RoomTypePriceDTO {
    private RoomType roomType;  // 방 타입
    private Integer accomId;    // 숙소 ID
    private Integer maxGuests;  // 최대 인원
    private Integer roomPrice;  // 가격

    // 기본 생성자
    public RoomTypePriceDTO() {}

    // 엔티티에서 DTO로 변환하는 생성자
    public RoomTypePriceDTO(RoomTypePrice roomTypePrice) {
        this.roomType = roomTypePrice.getRoomType();  // RoomType
        this.accomId = roomTypePrice.getAccomId();    // accomId
        this.maxGuests = roomTypePrice.getMaxGuests();
        this.roomPrice = roomTypePrice.getRoomPrice();
    }

    // 추가 생성자 (모든 필드 포함)
    public RoomTypePriceDTO(RoomType roomType, Integer accomId, Integer maxGuests, Integer roomPrice) {
        this.roomType = roomType;
        this.accomId = accomId;
        this.maxGuests = maxGuests;
        this.roomPrice = roomPrice;
    }
}
