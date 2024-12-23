package com.codingbox.review_projectex.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.sql.Timestamp;
import java.time.LocalDateTime;

/**
 * PAYMENT_CANCELLATION 엔티티 클래스
 * PAYMENT_CANCELLATION 테이블과 매핑되는 클래스입니다.
 */
@Entity
@Getter
@Setter
@ToString
@Table(name = "PAYMENT_CANCELLATION")
public class PaymentCancellation {

    /**
     * 결제 ID (기본키, 외래키)
     * PAYMENT 테이블의 payment_id를 참조합니다.
     */
    @Id
    @Column(name = "payment_id")
    private Integer paymentId;

    /**
     * 취소 일시
     * 결제 취소된 일시를 나타냅니다.
     */
    @Column(name = "cancellation_date", nullable = false)
    private Timestamp cancellationDate;

    /**
     * 결제 정보 (외래 키, PAYMENT 엔티티와 연결)
     */
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "payment_id", referencedColumnName = "payment_id", insertable = false, updatable = false)
    private Payment payment;

    // 기본 생성자는 롬복에 의해 자동 생성됩니다.
    // PaymentCancellation() 생성자도 롬복이 기본 생성자를 제공합니다.
    
    /**
     * 결제 취소 정보 초기화하는 생성자
     */
	public PaymentCancellation(Integer paymentId, java.sql.Timestamp currentTimestamp, Payment payment) {
		this.paymentId = paymentId;
		this.cancellationDate = currentTimestamp;
		this.payment = payment;
	}
}



