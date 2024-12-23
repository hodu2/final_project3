package com.codingbox.tripjava.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.codingbox.tripjava.entity.Accommodation;

public interface AccommodationRepository extends JpaRepository<Accommodation, Integer> {
    // 특정 지역이나 숙소 이름으로 조회하는 메서드를 추가 가능
	
	 Optional<Accommodation> findById(Integer accomId);
}
