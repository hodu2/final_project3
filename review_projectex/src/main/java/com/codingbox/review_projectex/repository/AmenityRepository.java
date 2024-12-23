package com.codingbox.review_projectex.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.codingbox.review_projectex.entity.Accommodation;
import com.codingbox.review_projectex.entity.Amenity;

public interface AmenityRepository extends JpaRepository<Amenity, Integer> {
	 
}
