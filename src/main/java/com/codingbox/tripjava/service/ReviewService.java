package com.codingbox.tripjava.service;

import com.codingbox.tripjava.dto.ReviewDTO;
import com.codingbox.tripjava.entity.Review;
import com.codingbox.tripjava.repository.AccommodationRepository;
import com.codingbox.tripjava.repository.ReservationRepository;
import com.codingbox.tripjava.repository.ReviewRepository;
import com.codingbox.tripjava.repository.UserRepository1;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final UserRepository1 userRepository1;
    private final ReservationRepository reservationRepository;
    private final AccommodationRepository accommodationRepository;

    @Autowired
    public ReviewService(ReviewRepository reviewRepository,
                         UserRepository1 userRepository1,
                         ReservationRepository reservationRepository,
                         AccommodationRepository accommodationRepository) {
        this.reviewRepository = reviewRepository;
        this.userRepository1 = userRepository1;
        this.reservationRepository = reservationRepository;
        this.accommodationRepository = accommodationRepository;
    }

    // 리뷰 추가
    public Review addReview(Review review) {
        // 유저 ID 검증 및 설정
        if (review.getUser() != null) {
            review.setUser(userRepository1.findById((Integer) review.getUserId())
                    .orElseThrow(() -> new IllegalArgumentException("유효하지 않은 사용자 ID")));
        }

        // 예약 ID 검증 및 설정
        if (review.getReservationId() != null) {
            review.setReservation(reservationRepository.findById(review.getReservationId())
                    .orElseThrow(() -> new IllegalArgumentException("유효하지 않은 예약 ID")));
        }

        // 숙소 ID 검증 및 설정
        if (review.getAccommodation() != null && review.getAccommodation().getAccomId() != null) {
            review.setAccommodation(accommodationRepository.findById(review.getAccommodation().getAccomId())
                    .orElseThrow(() -> new IllegalArgumentException("유효하지 않은 숙소 ID")));
        }

        // 작성 날짜 설정
        review.setReviewDate(new Date());

       

        // 리뷰 저장
        return reviewRepository.save(review);
    }

    // 리뷰 수정
    public Review updateReview(Integer reviewId, ReviewDTO reviewDTO) {
        Review existingReview = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new IllegalArgumentException("리뷰 ID가 유효하지 않습니다: " + reviewId));

        if (reviewDTO.getRating() != null) {
            existingReview.setRating(reviewDTO.getRating());
        }

        if (reviewDTO.getReviewText() != null) {
            existingReview.setReviewText(reviewDTO.getReviewText());
        }

        return reviewRepository.save(existingReview);
    }

    // 리뷰 삭제
    public void deleteReview(Integer reviewId) {
        if (!reviewRepository.existsById(reviewId)) {
            throw new IllegalArgumentException("삭제할 리뷰가 존재하지 않습니다: " + reviewId);
        }

        reviewRepository.deleteById(reviewId);
    }

    // 전체 리뷰 조회
    public List<Review> getAllReviews() {
        return reviewRepository.findAll();
    }

    // 특정 평점 기준으로 리뷰 조회
    public List<Review> getReviewsByRating(Double rating) {
        return reviewRepository.findByRating(rating);
    }

    // 특정 숙소에 대한 리뷰 조회
    public List<Review> getReviewsByAccommodation(Integer accomId) {
        return reviewRepository.findByAccommodation_AccomId(accomId);
    }

	public Review getReviewById(Integer reviewId) {
		return reviewRepository.findById(reviewId).orElse(null);
	}
}
