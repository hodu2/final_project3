package com.codingbox.review_projectex.repository;

import org.springframework.stereotype.Repository;

import com.codingbox.review_projectex.entity.Payment;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class PaymentRepository {
    private final EntityManager em;

    public void save(Payment payment) {
        em.persist(payment);
    }

    public Payment findById(int paymentId) {
        return em.find(Payment.class, paymentId);
    }
}