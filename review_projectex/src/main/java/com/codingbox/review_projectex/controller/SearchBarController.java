package com.codingbox.review_projectex.controller;

import com.codingbox.review_projectex.dto.AccommodationRecommendationDTO;
import com.codingbox.review_projectex.dto.AccommodationResponseDTO;
import com.codingbox.review_projectex.dto.LocationDTO;
import com.codingbox.review_projectex.dto.SearchAccommodationRequestDTO;
import com.codingbox.review_projectex.dto.SearchResultDTO;
import com.codingbox.review_projectex.entity.Accommodation;
import com.codingbox.review_projectex.enums.AccommodationType;
import com.codingbox.review_projectex.service.AccommodationService;
import com.codingbox.review_projectex.service.AccommodationService1;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/accommodations")
public class SearchBarController {

    private final AccommodationService1 accommodationService;

    public SearchBarController(AccommodationService1 accommodationService) {
        this.accommodationService = accommodationService;
    }

    @GetMapping("/all")
    public List<Accommodation> searchAccommodations(
            @RequestParam(value = "accomname", required = false) String accomName,
            @RequestParam(value = "accomaddress", required = false) String accomAddress,
            @RequestParam(value = "checkIn", required = false) String checkIn,
            @RequestParam(value = "checkOut", required = false) String checkOut,
            @RequestParam(value = "guests", required = false) Integer guests, 
    		@RequestParam(value = "type", required = false) AccommodationType type,
    		@RequestParam(value = "minPrice", required = false) Integer minPrice,
    		@RequestParam(value = "maxPrice", required = false) Integer maxPrice) {
    		
        SearchAccommodationRequestDTO request = new SearchAccommodationRequestDTO(
                accomName,
                accomAddress,
                checkIn,
                checkOut,
                guests,
                type,
                minPrice,
                maxPrice
        );

        return accommodationService.searchAccommodations(request);
    }
    
    // 숙소 검색 필터링 
    @PostMapping("/search")
    public ResponseEntity<List<SearchResultDTO>> searchAccommodations(@RequestBody SearchAccommodationRequestDTO request) {
    	System.out.println("검색 조건: " + request);
    	List<SearchResultDTO> results = accommodationService.searchAccommodationsWithImage(request);
        System.out.println("검색 결과: " + results);
        return ResponseEntity.ok(results);
    }
    
//    // 숙소 상세페이지
//    @GetMapping("/{id}/location")
//    public ResponseEntity<LocationDTO> getAccommodationLocation(@PathVariable int id) {
//        Accommodation accommodation = accommodationService.findById(id);
//        if (accommodation == null) {
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
//        }
//        LocationDTO location = new LocationDTO(accommodation.getLatitude(), accommodation.getLongitude());
//        return ResponseEntity.ok(location);
//    }

    
//    @GetMapping("/all")
//    public List<AccommodationResponseDTO> getAllAccommodations() {
//        List<Accommodation> accommodations = accommodationService.getAllAccommodationsWithDetails();
//        return accommodations.stream()
//                .map(AccommodationResponseDTO::new)
//                .collect(Collectors.toList());
//    }
    
    // 추천 숙소 가져오는 컨트롤러
    @GetMapping()
    public List<AccommodationRecommendationDTO> getTop12Accommodations() {
        return accommodationService.getTop12Accommodations();
    }
   
}
