package com.codingbox.tripjava.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Getter
@Setter
@Table(name = "REVIEW")
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "review_id_seq")
    @SequenceGenerator(name = "review_id_seq", sequenceName = "review_id_seq", allocationSize = 1)
    @Column(name = "review_id")
    private Integer reviewId;
    
    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "user_id", nullable = false)
    private User user;
    
    @JsonIgnore 
    @ManyToOne
    @JoinColumn(name = "resv_id", referencedColumnName = "resv_id", nullable = true)
    private Reservation reservation;
    
    @JsonIgnore 
    @ManyToOne
    @JoinColumn(name = "accom_id", referencedColumnName = "accom_id", nullable = false)
    private Accommodation accommodation;

    @Column(name = "rating", nullable = false)
    private Double rating;

    @Column(name = "review_text", length = 1000)
    private String reviewText;

    @Column(name = "review_date", nullable = false)
    @Temporal(TemporalType.DATE)
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date reviewDate;

    @Column(name = "review_image_path")
    private String reviewImagePath;

    // 기본 생성자
    public Review() {}

    // 생성자 추가 (리뷰 이미지 경로 포함)
    public Review(User user, Reservation reservation, Accommodation accommodation, Double rating, String reviewText, Date reviewDate, String reviewImagePath) {
        this.user = user;
        this.reservation = reservation;
        this.accommodation = accommodation;
        this.rating = rating;
        this.reviewText = reviewText;
        this.reviewDate = reviewDate;
        this.reviewImagePath = reviewImagePath;
    }

    // content 메서드 수정
    public void setContent(String content) {
        this.reviewText = content;
    }

    // accommodationId 설정 메서드 (Integer 타입으로 수정)
    public void setAccommodationId(Integer accomId) {
        if (this.accommodation == null) {
            this.accommodation = new Accommodation();  // Accommodation 객체가 없으면 생성
        }
        this.accommodation.setAccomId(accomId);  // accommodation 객체에 accomId를 설정
    }

    // accommodationId 조회 메서드 (accommodation 객체의 accomId 반환)
    public Integer getAccommodationId() {
        if (this.accommodation != null) {
            return this.accommodation.getAccomId();  // accommodation 객체에서 accomId 반환
        }
        return null;
    }

    // ImagePath 설정 메서드 (이미지 경로를 설정)
    public void setImagePath(String imagePath) {
        this.reviewImagePath = imagePath;  // reviewImagePath에 이미지 경로를 설정
    }

    // getUserId() 메서드 추가: Review 객체 내 User 객체의 userId 반환
    public Integer getUserId() {
        if (this.user != null) {
            return this.user.getUserId();  // User 객체의 userId 반환
        }
        return null;
    }

    public Integer getReservationId() {
        // TODO Auto-generated method stub
        return null;
    }
    public String getUsername() {
        return this.user.getUsername();  // User 객체의 username을 반환
    }


  
}
