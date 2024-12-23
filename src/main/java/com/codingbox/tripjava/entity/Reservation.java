package com.codingbox.tripjava.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

import com.codingbox.tripjava.enums.ReservationStatus;

/**
 * RESERVATION 엔티티 클래스
 * RESERVATION 테이블과 매핑되는 클래스입니다.
 */
@Entity
@Getter
@Setter
@Table(name = "RESERVATION")
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "resv_id_seq")
    @SequenceGenerator(name = "resv_id_seq", sequenceName = "resv_id_seq", allocationSize = 1)
    @Column(name = "resv_id")
    private Integer resvId;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumns({
        @JoinColumn(name = "accom_id", referencedColumnName = "accom_id", nullable = false),
        @JoinColumn(name = "room_type", referencedColumnName = "room_type", nullable = false)
    })
    private RoomTypePrice roomTypePrice;

    @Column(name = "number_guests", nullable = false)
    private int numberGuests;
    
    @Column(name = "room_price", nullable = false)
    private int roomPrice;

    @Column(name = "check_in_date", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date checkInDate;

    @Column(name = "check_out_date", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date checkOutDate;

    @Column(name = "resv_status", nullable = false)
    @Enumerated(EnumType.STRING) // ENUM을 문자열로 저장
    private ReservationStatus resvStatus = ReservationStatus.BOOKED;

    public Reservation() {}
    
    @OneToOne(mappedBy = "reservation", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private Payment payment;


    public Reservation(User user, RoomTypePrice roomTypePrice, int numberGuests, int roomPrice, Date checkInDate, Date checkOutDate, ReservationStatus resvStatus) {
        this.user = user;
        this.roomTypePrice = roomTypePrice;
        this.numberGuests = numberGuests;
        this.roomPrice = roomPrice;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
        this.resvStatus = resvStatus;
	}

	@Override
	public String toString() {
		return "Reservation [resvId=" + resvId + ", user=" + user + ", roomTypePrice=" + roomTypePrice
				+ ", numberGuests=" + numberGuests + ", roomPrice=" + roomPrice + ", checkInDate=" + checkInDate
				+ ", checkOutDate=" + checkOutDate + ", resvStatus=" + resvStatus + ", payment=" + payment + "]";
	}

}