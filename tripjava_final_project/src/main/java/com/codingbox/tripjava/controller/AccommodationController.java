package com.codingbox.tripjava.controller;

import com.codingbox.tripjava.dto.AccommodationDTO;
import com.codingbox.tripjava.dto.AccommodationWithReviewsDTO;
import com.codingbox.tripjava.dto.ReviewDTO;
import com.codingbox.tripjava.entity.Accommodation;
import com.codingbox.tripjava.entity.Review;
import com.codingbox.tripjava.repository.AccommodationRepository;
import com.codingbox.tripjava.service.AccommodationService;
import com.codingbox.tripjava.service.ReviewService;
import com.codingbox.tripjava.service.WishlistService;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/accommodation")
public class AccommodationController {

    private final ReviewService reviewService;
    private final AccommodationRepository accommodationRepository;

    @Autowired
    public AccommodationController(ReviewService reviewService, AccommodationRepository accommodationRepository, WishlistService wishlistService) {
        this.reviewService = reviewService;
        this.accommodationRepository = accommodationRepository;
    }

    // 숙소 정보, 리뷰, 위시리스트 상태 조회
    @GetMapping("/{accomId}")
    public AccommodationWithReviewsDTO getAccommodationWithReviews(@PathVariable Integer accomId, @RequestParam(required = false) Integer userId) {
        // 숙소 정보 조회
        Accommodation accommodation = accommodationRepository.findById(accomId)
                .orElseThrow(() -> new IllegalArgumentException("유효하지 않은 숙소 ID"));

        // 숙소에 해당하는 리뷰 조회
        List<Review> reviews = reviewService.getReviewsByAccommodation(accomId);

        // AccommodationDTO로 변환
        AccommodationDTO accommodationDTO = new AccommodationDTO(accommodation);  // 숙소 정보 DTO로 변환

        // ReviewDTO 리스트로 변환
        List<ReviewDTO> reviewDTOs = reviews.stream()
                .map(review -> new ReviewDTO(review))  // Review 객체를 ReviewDTO로 변환
                .collect(Collectors.toList());

        // 위시리스트에 포함된 숙소 여부 확인

        // AccommodationWithReviewsDTO 객체로 결과 반환
        return new AccommodationWithReviewsDTO(accommodationDTO, reviewDTOs, accommodationDTO.getRoomTypePrices());
    }
}
