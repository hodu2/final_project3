package com.codingbox.tripjava.repository;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import com.codingbox.tripjava.entity.PaymentCancellation;

@Repository
@RequiredArgsConstructor
public class PaymentCancellationRepository {
    private final EntityManager em;

    public void save(PaymentCancellation cancellation) {
        em.persist(cancellation);
    }
}