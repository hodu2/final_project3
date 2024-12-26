package com.codingbox.tripjava.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "ACCOMMODATION_AMENITY")
public class AccommodationAmenity {

    @EmbeddedId
    private AccommodationAmenityId id;  // 복합키 사용

    @ManyToOne
    @MapsId("accomId")  // 복합 키에서 accomId를 매핑
    @JoinColumn(name = "accom_id", referencedColumnName = "accom_id", nullable = false)
    private Accommodation accommodation;

    @ManyToOne
    @MapsId("amenityId")  // 복합 키에서 amenityId를 매핑
    @JoinColumn(name = "amenity_id", referencedColumnName = "amenity_id", nullable = false)
    private Amenity amenity;

    // Amenity의 이름 반환
    public String getAmenityName() {
        return this.amenity != null ? this.amenity.getName() : null;
    }

    // 기본 생성자
    public AccommodationAmenity() {}

    // 인자 생성자
    public AccommodationAmenity(Accommodation accommodation, Amenity amenity) {
        this.accommodation = accommodation;
        this.amenity = amenity;
        this.id = new AccommodationAmenityId(accommodation.getAccomId(), amenity.getAmenityId());
    }
}
