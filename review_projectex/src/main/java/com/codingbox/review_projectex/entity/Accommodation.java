package com.codingbox.review_projectex.entity;

import java.util.List;

import com.codingbox.review_projectex.enums.AccommodationType;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

/**
 * ACCOMMODATION 엔티티 클래스
 * ACCOMMODATION 테이블과 매핑되는 클래스입니다.
 */
@Entity
@Getter
@Setter
@Table(name = "ACCOMMODATION")
public class Accommodation {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "accom_id_seq")
    @SequenceGenerator(name = "accom_id_seq", sequenceName = "accom_id_seq", allocationSize = 1)
    @Column(name = "accom_id")
    private Integer accomId;

    @Column(name = "accom_name", nullable = false)
    private String accomName;

    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    private AccommodationType type;

    @Column(name = "description")
    private String description;

    @Column(name = "address")
    private String address;

    @Column(name = "latitude", nullable = false, columnDefinition = "NUMBER(9,6)")
    private double latitude;

    @Column(name = "longitude", nullable = false, columnDefinition = "NUMBER(9,6)")
    private double longitude;

    @Column(name = "accom_tel")
    private String accomTel;
    
    @Column(name = "ava_dates_start")
    private java.sql.Date avaDatesStart;

    @Column(name = "ava_dates_end")
    private java.sql.Date avaDatesEnd;

    @JsonIgnore
    @OneToMany(mappedBy = "accommodation", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<RoomTypePrice> roomTypePrices;

    @JsonManagedReference
    @OneToMany(mappedBy = "accommodation", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<AccommodationAmenity> amenities;

    @JsonManagedReference
    @OneToMany(mappedBy = "accommodation")
    private List<AccommodationImage> images;

    @JsonManagedReference
    @OneToMany(mappedBy = "accommodation", fetch = FetchType.LAZY)
    private List<Review> reviews;

    public Accommodation() {
    }

    public Accommodation(String accomName, AccommodationType type, String description, String address,
                         double latitude, double longitude, java.sql.Date avaDatesStart, java.sql.Date avaDatesEnd) {
        this.accomName = accomName;
        this.type = type;
        this.description = description;
        this.address = address;
        this.latitude = latitude;
        this.longitude = longitude;
        this.avaDatesStart = avaDatesStart;
        this.avaDatesEnd = avaDatesEnd;
    }
}

