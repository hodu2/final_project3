package com.codingbox.tripjava.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

/**
 * PAYMENT 엔티티 클래스
 * PAYMENT 테이블과 매핑되는 클래스입니다.
 */
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name = "PAYMENT")
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "payment_id_seq")
    @SequenceGenerator(name = "payment_id_seq", sequenceName = "payment_id_seq", allocationSize = 1)
    @Column(name = "payment_id", nullable = false)
    private int paymentId;

    /**
     * 예약 ID (외래 키)
     */
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "resv_id", referencedColumnName = "resv_id", nullable = false)
    private Reservation reservation;

    @Column(name = "amount", nullable = false)
    private int amount;


    @Column(name = "payment_date", nullable = false)
    private Timestamp paymentDate;

    // 기본 생성자
    public Payment() {
    }

    // 전체 필드 초기화 생성자
    public Payment(Reservation reservation, int amount, Timestamp paymentDate) {
        this.reservation = reservation;
        this.amount = amount;
        this.paymentDate = paymentDate;
    }
}

