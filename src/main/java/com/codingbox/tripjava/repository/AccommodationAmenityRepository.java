package com.codingbox.tripjava.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.codingbox.tripjava.entity.AccommodationAmenity;
import com.codingbox.tripjava.entity.AccommodationAmenityId;

public interface AccommodationAmenityRepository extends JpaRepository<AccommodationAmenity,  AccommodationAmenityId> {

}
