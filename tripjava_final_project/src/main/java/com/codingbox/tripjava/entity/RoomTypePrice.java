package com.codingbox.tripjava.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import com.codingbox.tripjava.enums.RoomType;
import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name = "ROOM_TYPE_PRICE")
@Getter
@Setter
@ToString
@IdClass(RoomTypePriceId.class) // 복합 키 클래스 지정
public class RoomTypePrice {

    @Id
    @Enumerated(EnumType.STRING)
    @Column(name = "room_type")
    private RoomType roomType;

    @Id
    @Column(name = "accom_id", nullable = false)
    private Integer accomId;

    @Column(name = "max_guests", nullable = false)
    private int maxGuests;

    @Column(name = "room_price", nullable = false)
    private int roomPrice;

    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "accom_id", referencedColumnName = "accom_id", insertable = false, updatable = false)
    private Accommodation accommodation;

    public RoomTypePrice() {}

    public RoomTypePrice(Accommodation accommodation, RoomType roomType, int roomPrice, int maxGuests) {
        this.accommodation = accommodation;
        this.roomType = roomType;
        this.accomId = accommodation.getAccomId(); // accomodation에서 accomId 가져오기
        this.roomPrice = roomPrice;
        this.maxGuests = maxGuests;
    }
}
