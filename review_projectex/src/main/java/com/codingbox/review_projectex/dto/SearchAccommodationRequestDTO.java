package com.codingbox.review_projectex.dto;

import com.codingbox.review_projectex.enums.AccommodationType;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SearchAccommodationRequestDTO {

    private String accomName;
    private String accomAddress;
    private String checkIn;
    private String checkOut;
    private Integer guests;
    private AccommodationType type;
    private Integer minPrice;
    private Integer maxPrice;
}
