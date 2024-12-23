package com.codingbox.review_projectex.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReviewCreateRequest {
	 @JsonProperty("accommodationId")
    private Integer accomId;   // 숙소 ID
    private Double rating;     // 평점
    private String reviewText; // 리뷰 내용
    @JsonProperty("userId")
    private Integer userId;  // userId를 String에서 Integer로 변경
    @JsonProperty("resvId")
    private Integer resvId;
    private String reviewImagePath; // 이미지 경로
    // 기본 생성자
    public ReviewCreateRequest() {}

    public Object getReservationId() {
        // TODO Auto-generated method stub
        return null;
    }
    public void setReviewImagePath(String reviewImagePath) {
        this.reviewImagePath = reviewImagePath;
    }
}
