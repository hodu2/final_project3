package com.codingbox.tripjava.dto;


import com.codingbox.tripjava.enums.AccommodationType;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AccommodationRecommendationDTO {
    private Integer accomId;
    private String accomName;
    private AccommodationType type;
    private String representativeImage;
    private Double averageRating;
    private int cheapestPrice;

    public AccommodationRecommendationDTO(Integer accomId, String accomName, AccommodationType type, 
    		int cheapestPrice, String representativeImage, Double averageRating) {
	this.accomId = accomId;
	this.accomName = accomName;
	this.type = type;
	this.cheapestPrice = cheapestPrice;
	this.representativeImage = representativeImage;
	this.averageRating = averageRating;
	}
}
