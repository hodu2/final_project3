package com.codingbox.tripjava.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.codingbox.tripjava.entity.Accommodation;
import com.codingbox.tripjava.entity.AccommodationImage;

public interface AccommodationImageRepository extends JpaRepository<AccommodationImage, Integer> {
	 Optional<Accommodation> findByAccommodation_AccomId(Integer accomId);
}
