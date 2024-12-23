package com.codingbox.tripjava.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.codingbox.tripjava.dto.RoomTypePriceDTO;
import com.codingbox.tripjava.entity.Accommodation;
import com.codingbox.tripjava.entity.RoomTypePrice;
import com.codingbox.tripjava.repository.AccommodationRepository;
import com.codingbox.tripjava.repository.RoomTypePriceRepository;

@Service
public class RoomTypePriceService {

    private final RoomTypePriceRepository roomTypePriceRepository;
    private final AccommodationRepository accommodationRepository;

    // 생성자 주입: RoomTypePriceRepository와 AccommodationRepository를 받음
    @Autowired
    public RoomTypePriceService(RoomTypePriceRepository roomTypePriceRepository, 
                                AccommodationRepository accommodationRepository) {
        this.roomTypePriceRepository = roomTypePriceRepository;
        this.accommodationRepository = accommodationRepository;
    }

    // accomId로 RoomTypePrice 조회
    public List<RoomTypePriceDTO> getRoomTypePricesByAccommodation(Integer accomId) {
        // accomId로 Accommodation 객체 조회
        Accommodation accommodation = accommodationRepository.findById(accomId)
                .orElseThrow(() -> new RuntimeException("Accommodation not found"));

        // Accommodation 객체를 사용하여 RoomTypePrice 목록 조회
        List<RoomTypePrice> roomTypePrices = roomTypePriceRepository.findByAccommodation(accommodation);

        // RoomTypePrice 목록을 RoomTypePriceDTO로 변환하여 반환
        return roomTypePrices.stream()
                .map(RoomTypePriceDTO::new) // RoomTypePrice 객체를 RoomTypePriceDTO로 변환
                .collect(Collectors.toList());
    }
}
