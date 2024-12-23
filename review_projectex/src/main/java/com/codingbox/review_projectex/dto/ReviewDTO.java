package com.codingbox.review_projectex.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

import com.codingbox.review_projectex.entity.Review;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class ReviewDTO {
    private Integer reviewId; // 리뷰 ID
    @JsonProperty("userId")  // JSON의 "userId" 필드를 해당 필드로 매핑
    private Integer userId;
    private String username; // 작성자 이름 추가
    @JsonProperty("accomId")  // JSON에서 "accomId" 필드를 이 필드에 매핑
    private Integer accomId; // 숙소 ID
    private Integer resvId; // 예약 ID 추가 필요
    private Double rating; // 평점
    private String reviewText; // 리뷰 내용
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")  // 날짜 형식 지정
    private Date reviewDate; // 작성 날짜
    private String reviewImagePath; // 이미지 경로

    // 기본 생성자
    public ReviewDTO() {}

    // 엔티티에서 DTO로 변환하는 생성자
    public ReviewDTO(Review review) {
        this.reviewId = review.getReviewId();
        this.userId = review.getUser().getUserId(); // User 객체에서 userId 추출
        this.username = review.getUser().getUsername(); // User 객체에서 username 추출
        this.resvId = review.getReservation() != null ? review.getReservation().getResvId() : null; // 예약 정보가 없을 경우 null 처리 // Reservation 객체에서 resvId 추출
        this.accomId = review.getAccommodation().getAccomId(); // Accommodation 객체에서 accomId 추출
        this.rating = review.getRating();
        this.reviewText = review.getReviewText();
        this.reviewDate = review.getReviewDate();
        this.reviewImagePath = review.getReviewImagePath();
    }

    // 추가 생성자 (모든 필드 포함)
    public ReviewDTO(Integer reviewId, Integer userId, String username, Integer accomId, Double rating, String reviewText, Date reviewDate, String reviewImagePath) {
        this.reviewId = reviewId;
        this.userId = userId;
        this.username = username; // 작성자 이름 설정
        this.accomId = accomId;
        this.rating = rating;
        this.reviewText = reviewText;
        this.reviewDate = reviewDate;
        this.reviewImagePath = reviewImagePath;
    }
}
