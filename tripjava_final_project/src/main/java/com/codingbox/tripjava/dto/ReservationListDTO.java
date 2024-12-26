package com.codingbox.tripjava.dto;

import java.sql.Date;

import com.codingbox.tripjava.enums.ReservationStatus;
import com.codingbox.tripjava.enums.RoomType;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReservationListDTO {
	private Integer resvId;
	private String username;
	private String accomName;
	private RoomType roomType;
	private int amount;
	private int numberGuests;
	private Date checkInDate;
	private Date checkOutDate;
	private ReservationStatus resvStatus;

	public ReservationListDTO(Integer resvId, String username, String accomName, RoomType roomType, int amount,
			int numberGuests, Date checkInDate, Date checkOutDate, ReservationStatus resvStatus) {
		this.resvId = resvId;
		this.username = username;
		this.accomName = accomName;
		this.roomType = roomType;
		this.amount = amount;
		this.numberGuests = numberGuests;
		this.checkInDate = checkInDate;
		this.checkOutDate = checkOutDate;
		this.resvStatus = resvStatus;
	}
}
