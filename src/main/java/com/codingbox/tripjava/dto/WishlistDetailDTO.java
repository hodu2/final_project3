package com.codingbox.tripjava.dto;

import com.codingbox.tripjava.enums.AccommodationType;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WishlistDetailDTO {
	private Integer accomId;
    private String accomName; // 숙소 이름
    private AccommodationType accomType; // 숙소 유형
    private Double averageRating; // 평점
    private int cheapestPrice; // 최저가
    private String imageUrl; // 숙소 대표 이미지

    public WishlistDetailDTO(Integer accomId, String accomName, AccommodationType accomType, Double averageRating, int cheapestPrice, String imageUrl) {
        this.accomId = accomId;
    	this.accomName = accomName;
        this.accomType = accomType;
        this.averageRating = averageRating;
        this.cheapestPrice = cheapestPrice;
        this.imageUrl = imageUrl;
    }

}