package com.codingbox.review_projectex.repository;

import com.codingbox.review_projectex.entity.Accommodation;
import com.codingbox.review_projectex.entity.RoomTypePrice;
import com.codingbox.review_projectex.entity.RoomTypePriceId;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RoomTypePriceRepository extends JpaRepository<RoomTypePrice, RoomTypePriceId> {

    // Accommodation 객체의 accomId를 기준으로 RoomTypePrice 목록을 조회
    List<RoomTypePrice> findByAccommodation(Accommodation accommodation);  // 수정된 부분
}
