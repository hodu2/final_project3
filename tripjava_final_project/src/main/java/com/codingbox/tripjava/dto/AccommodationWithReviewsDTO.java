package com.codingbox.tripjava.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Getter
@Setter
public class AccommodationWithReviewsDTO {

    private AccommodationDTO accommodation;
    
    @JsonIgnore
    private List<ReviewDTO> reviews;  // 리뷰 리스트
    @JsonIgnore
    private List<RoomTypePriceDTO2> roomTypePrices;  // RoomTypePrice 정보 추가

    // 기본 생성자
    public AccommodationWithReviewsDTO() {}

    // AccommodationDTO, ReviewDTO, RoomTypePriceDTO, isInWishlist를 모두 포함하는 생성자
    public AccommodationWithReviewsDTO(AccommodationDTO accommodation, List<ReviewDTO> reviews, List<RoomTypePriceDTO2> roomTypePrices) {
        this.accommodation = accommodation;
        this.reviews = reviews;
        this.roomTypePrices = roomTypePrices;  // RoomTypePriceDTO 리스트 추가
    }
}
