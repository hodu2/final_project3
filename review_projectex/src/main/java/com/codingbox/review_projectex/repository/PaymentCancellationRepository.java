package com.codingbox.review_projectex.repository;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import com.codingbox.review_projectex.entity.PaymentCancellation;

@Repository
@RequiredArgsConstructor
public class PaymentCancellationRepository {
    private final EntityManager em;

    public void save(PaymentCancellation cancellation) {
        em.persist(cancellation);
    }
}