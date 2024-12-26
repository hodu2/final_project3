package com.codingbox.tripjava.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.codingbox.tripjava.entity.Reservation;

public interface ReservationRepository extends JpaRepository<Reservation, Integer> {
    // 추가 쿼리 메서드를 정의할 수 있음 (예: 특정 사용자 ID로 예약 조회)
}
