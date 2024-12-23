package com.codingbox.review_projectex.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.codingbox.review_projectex.entity.AccommodationAmenity;
import com.codingbox.review_projectex.entity.AccommodationAmenityId;

public interface AccommodationAmenityRepository extends JpaRepository<AccommodationAmenity,  AccommodationAmenityId> {

}
