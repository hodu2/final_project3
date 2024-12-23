package com.codingbox.review_projectex.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.codingbox.review_projectex.entity.Reservation;
import com.codingbox.review_projectex.enums.ReservationStatus;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class ReservationRepository1 {

    private final EntityManager em;

    public void save(Reservation reservation) {
        em.persist(reservation);
    }

    public Reservation findByResvId(Integer resvId) {
        return em.find(Reservation.class, resvId);
    }

    // 모든 예약 조회
    public List<Reservation> findByUserId(Integer userId) {
        return em.createQuery("SELECT r FROM Reservation r WHERE r.user.id = :userId", Reservation.class)
                .setParameter("userId", userId)
                .getResultList();
    }

    // 취소된 예약 조회
    public List<Reservation> findCancelledReservationsByUserId(Integer userId) {
        return em.createQuery("SELECT r FROM Reservation r WHERE r.user.id = :userId AND r.resvStatus = :status", Reservation.class)
                .setParameter("userId", userId)
                .setParameter("status", ReservationStatus.CANCEL)
                .getResultList();
    }
}