package com.codingbox.tripjava.dto;

import lombok.Getter;
import lombok.Setter;

import java.sql.Date;

import com.codingbox.tripjava.enums.RoomType;

@Getter
@Setter
public class ReservationFormDTO {
    private Integer user_id;
    private Integer accom_id;
    private RoomType roomType;
    private int number_guests;
    private int roomPrice;
    private Date checkInDate;
    private Date checkOutDate;
}