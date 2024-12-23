package com.codingbox.tripjava.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.codingbox.tripjava.entity.Accommodation;
import com.codingbox.tripjava.entity.RoomTypePrice;
import com.codingbox.tripjava.entity.RoomTypePriceId;

import java.util.List;

public interface RoomTypePriceRepository extends JpaRepository<RoomTypePrice, RoomTypePriceId> {

    // Accommodation 객체의 accomId를 기준으로 RoomTypePrice 목록을 조회
    List<RoomTypePrice> findByAccommodation(Accommodation accommodation);  // 수정된 부분
}
