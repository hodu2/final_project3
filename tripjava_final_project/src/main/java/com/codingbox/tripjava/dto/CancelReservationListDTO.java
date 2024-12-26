package com.codingbox.tripjava.dto;

import java.sql.Date;

import com.codingbox.tripjava.enums.ReservationStatus;
import com.codingbox.tripjava.enums.RoomType;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CancelReservationListDTO {
	private Integer paymentId;
	private Integer resvId;
	private String username;
	private String accomName;
	private RoomType roomType;
	private int numberGuests;
	private Date checkInDate;
	private Date checkOutDate;
	private ReservationStatus resvStatus;
	
	public CancelReservationListDTO(Integer paymentId, Integer resvId, String username, String accomName,
			RoomType roomType, int numberGuests, Date checkInDate, Date checkOutDate, ReservationStatus resvStatus) {
		this.paymentId = paymentId;
		this.resvId = resvId;
		this.username = username;
		this.accomName = accomName;
		this.roomType = roomType;
		this.numberGuests = numberGuests;
		this.checkInDate = checkInDate;
		this.checkOutDate = checkOutDate;
		this.resvStatus = resvStatus;
	}

	
}

