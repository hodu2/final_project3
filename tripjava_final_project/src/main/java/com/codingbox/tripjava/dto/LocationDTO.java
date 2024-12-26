package com.codingbox.tripjava.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LocationDTO {
    private Double latitude;		// 위도
    private Double longitude;		// 경도
}