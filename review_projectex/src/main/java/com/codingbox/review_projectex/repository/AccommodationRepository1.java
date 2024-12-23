package com.codingbox.review_projectex.repository;

import org.springframework.stereotype.Repository;
import com.codingbox.review_projectex.entity.Accommodation;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class AccommodationRepository1 {
	private final EntityManager em;

	public Accommodation findById(Integer accomId) {
        return em.find(Accommodation.class, accomId);
    }
}
