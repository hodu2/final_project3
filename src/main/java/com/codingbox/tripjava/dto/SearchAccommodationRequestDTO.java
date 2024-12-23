package com.codingbox.tripjava.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

import com.codingbox.tripjava.enums.AccommodationType;

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
