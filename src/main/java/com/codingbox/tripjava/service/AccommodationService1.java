package com.codingbox.tripjava.service;

import com.codingbox.tripjava.dto.AccommodationRecommendationDTO;
import com.codingbox.tripjava.dto.SearchAccommodationRequestDTO;
import com.codingbox.tripjava.dto.SearchResultDTO;
import com.codingbox.tripjava.entity.Accommodation;
import com.codingbox.tripjava.repository.AccommodationRepository2;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccommodationService1 {

    private final AccommodationRepository2 accommodationRepository;

    public AccommodationService1(AccommodationRepository2 accommodationRepository) {
        this.accommodationRepository = accommodationRepository;
    }

    public List<Accommodation> searchAccommodations(SearchAccommodationRequestDTO request) {
    	List<Accommodation> test = accommodationRepository.searchAccommodations(request); 
    	System.out.println(test);
        return test;
    }
    
    public List<Accommodation> getAllAccommodationsWithDetails() {
        return accommodationRepository.findAllAccommodationsWithDetails();
    }
    
    public List<SearchResultDTO> searchAccommodationsWithImage(SearchAccommodationRequestDTO request) {
        return accommodationRepository.searchAccommodationsWithImage(request);
    }
    
    // id 조회 (지도)
    public Accommodation findById(int id) {
        return accommodationRepository.findById(id);
    }

    // 추천 숙소 12개 가져오기
    public List<AccommodationRecommendationDTO> getTop12Accommodations() {
        return accommodationRepository.findTop12Accommodations();
    }
    
}
