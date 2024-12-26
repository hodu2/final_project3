package com.codingbox.tripjava.entity;

import java.util.List;

import com.codingbox.tripjava.enums.AmenityType;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Entity
@Table(name = "AMENITY")
public class Amenity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "amenity_id_seq")
    @SequenceGenerator(name = "amenity_id_seq", sequenceName = "amenity_id_seq", allocationSize = 1) // 시퀀스 이름 소문자로 수정
    @Column(name = "amenity_id")
    private Integer amenityId;

    @Enumerated(EnumType.STRING)
    @Column(name = "amenity_type")
    private AmenityType amenityType;


    @Column(name = "amenity_name", nullable = false)
    private String amenityName;

    @OneToMany(mappedBy = "amenity")
    private List<AccommodationAmenity> accommodations;

    // 기본 생성자 추가
    public Amenity() {
    }

 
    // amenityName을 반환하는 메서드 추가
    public String getName() {
        return this.amenityName;
    }
}
