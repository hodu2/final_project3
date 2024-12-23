package com.codingbox.tripjava.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.codingbox.tripjava.entity.Accommodation;
import com.codingbox.tripjava.entity.Review;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Integer> {

    // 평점 기준으로 리뷰 조회 (정확한 평점)
    List<Review> findByRating(Double rating);

    // 특정 숙소에 대한 리뷰 조회
    List<Review> findByAccommodation_AccomId(Integer accomId);

    // 특정 예약에 대한 리뷰 조회
    List<Review> findByReservation_ResvId(Long resvId);

    // 평점 범위 기준으로 리뷰 조회
    List<Review> findByRatingBetween(Double minRating, Double maxRating);  // 추가
    
    List<Review> findByAccommodation(Accommodation accommodation);
}

