package com.codingbox.tripjava.controller;

import com.codingbox.tripjava.dto.ReviewDTO;
import com.codingbox.tripjava.entity.Accommodation;
import com.codingbox.tripjava.entity.Review;
import com.codingbox.tripjava.entity.ReviewCreateRequest;
import com.codingbox.tripjava.entity.User;
import com.codingbox.tripjava.repository.AccommodationRepository;
import com.codingbox.tripjava.repository.ReservationRepository;
import com.codingbox.tripjava.service.ReviewService;
import com.codingbox.tripjava.service.UserService1;
import com.codingbox.tripjava.session.SessionConst;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpSession;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/reviews")
public class ReviewController {

    private final ReviewService reviewService;
    private final UserService1 userService1;
    private final AccommodationRepository accommodationRepository;
    private final ReservationRepository reservationRepository;

    @Autowired
    public ReviewController(ReviewService reviewService, UserService1 userService1, AccommodationRepository accommodationRepository, ReservationRepository reservationRepository) {
        this.reviewService = reviewService;
        this.userService1 = userService1;
        this.accommodationRepository = accommodationRepository;
        this.reservationRepository = reservationRepository;
    }

    // 리뷰 전체 목록 조회
    @GetMapping
    public List<ReviewDTO> getAllReviews() {
        List<Review> reviews = reviewService.getAllReviews();
        return reviews.stream().map(ReviewDTO::new).toList();
    }

    // 특정 평점 기준으로 리뷰 조회
    @GetMapping("/rating/{rating}")
    public List<Review> getReviewsByRating(@PathVariable Double rating) {
        return reviewService.getReviewsByRating(rating);
    }

    // 특정 숙소에 대한 리뷰 조회
    @GetMapping("/accommodation/{accomId}")
    public List<ReviewDTO> getReviewsByAccommodation(@PathVariable Integer accomId) {
        List<Review> reviews = reviewService.getReviewsByAccommodation(accomId);
        return reviews.stream().map(ReviewDTO::new).toList();
    }

    // 리뷰 생성
    @PostMapping
    public ResponseEntity<?> createReview(@RequestBody ReviewCreateRequest request, HttpSession session) {
        // 세션에서 로그인 사용자 정보 가져오기
        User user = (User) session.getAttribute(SessionConst.LOGIN_MEMBER);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("로그인되지 않은 사용자입니다.");
        }

        // Review 객체 생성 및 데이터 설정
        Review review = new Review();
        review.setUser(user); // 세션에서 가져온 user 객체 사용
        Accommodation accommodation = accommodationRepository.findById(request.getAccomId())
            .orElseGet(() -> {
                Accommodation newAccommodation = new Accommodation();
                newAccommodation.setAccomId(request.getAccomId());
                accommodationRepository.save(newAccommodation); // 신규 Accommodation 저장
                return newAccommodation;
            });
        review.setAccommodation(accommodation); // Accommodation 객체를 Review에 설정
        review.setRating(request.getRating());
        review.setReviewText(request.getReviewText());
        review.setReviewDate(new Date());

        // 이미지가 제공되지 않으면 null 대신 빈 문자열로 설정
        String reviewImagePath = request.getReviewImagePath();
        if (reviewImagePath == null || reviewImagePath.trim().isEmpty() || reviewImagePath.equals("default-image.png")) {
            reviewImagePath = null; // null로 설정
        }
        review.setReviewImagePath(reviewImagePath);

        // 예약 정보가 있다면, 예약 정보를 리뷰에 추가
        if (request.getReservationId() != null) {
            reservationRepository.findById((Integer) request.getReservationId()).ifPresent(reservation -> {
                review.setReservation(reservation);
            });
        }

        // 리뷰 저장
        Review savedReview = reviewService.addReview(review);
        return ResponseEntity.ok(new ReviewDTO(savedReview));
    }

    @GetMapping("/{reviewId}")
    public ResponseEntity<?> getReviewById(@PathVariable Integer reviewId) {
        Review review = reviewService.getReviewById(reviewId);
        if (review != null) {
            return ResponseEntity.ok(review);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // 리뷰 수정
    @PutMapping("/{reviewId}")
    public ResponseEntity<?> updateReview(@PathVariable Integer reviewId, @RequestBody ReviewDTO reviewDTO) {
        reviewDTO.setReviewId(reviewId); // 수정할 리뷰의 ID 설정
        reviewService.updateReview(reviewId, reviewDTO);
        return ResponseEntity.ok("리뷰가 수정되었습니다.");
    }

    // 리뷰 삭제
    @DeleteMapping("/{reviewId}")
    public String deleteReview(@PathVariable Integer reviewId) {
        reviewService.deleteReview(reviewId);
        return "리뷰가 성공적으로 삭제되었습니다.";
    }
}
