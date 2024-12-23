package com.codingbox.review_projectex.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.codingbox.review_projectex.entity.Accommodation;
import com.codingbox.review_projectex.entity.AccommodationImage;

public interface AccommodationImageRepository extends JpaRepository<AccommodationImage, Integer> {
	 Optional<Accommodation> findByAccommodation_AccomId(Integer accomId);
}
