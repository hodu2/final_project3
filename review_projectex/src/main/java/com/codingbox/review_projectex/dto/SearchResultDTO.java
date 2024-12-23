package com.codingbox.review_projectex.dto;

import java.sql.Date;
import java.time.LocalDate;

import com.codingbox.review_projectex.enums.AccommodationType;
import com.querydsl.core.annotations.QueryProjection;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class SearchResultDTO {
	private int accomId;					// 숙소 아이디 추가
    private String accomName;      			// 숙소 이름
    private AccommodationType type; 		// 숙소 유형
    private String address;        			// 상세 주소
    private java.sql.Date avaDatesStart;  			// 예약 가능 시작 날짜
    private java.sql.Date avaDatesEnd;    			// 예약 가능 종료 날짜
    private int cheapestPrice;     			// 최소 가격
    private String representativeImage;		// 대표 이미지 경로
    private Double averageRating; // 평점 추가
    
    public String getAvaDatesStart() {
        return avaDatesStart != null ? avaDatesStart.toString() : null;
    }

    public String getAvaDatesEnd() {
        return avaDatesEnd != null ? avaDatesEnd.toString() : null;
    }
    
    @QueryProjection
    public SearchResultDTO(int accomId, String accomName, AccommodationType type, String address, 
    		Date avaDatesStart, Date avaDatesEnd, int cheapestPrice, String representativeImage, Double averageRating) {
        this.accomId = accomId;
    	this.accomName = accomName;
        this.type = type;
        this.address = address;
        this.avaDatesStart = avaDatesStart;
        this.avaDatesEnd = avaDatesEnd;
        this.cheapestPrice = cheapestPrice;
        this.representativeImage = representativeImage;
        this.averageRating = averageRating;
    }
}
