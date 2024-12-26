package com.codingbox.tripjava.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.codingbox.tripjava.entity.Accommodation;
import com.codingbox.tripjava.entity.Amenity;

public interface AmenityRepository extends JpaRepository<Amenity, Integer> {
	 
}
