package com.codingbox.tripjava.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.ToString;

import java.io.Serializable;
import java.util.Objects;

@ToString
@Embeddable
public class AccommodationAmenityId implements Serializable {
    
    @Column(name = "accom_id")
    private Integer accomId; // Integer로 변경
    
    @Column(name = "amenity_id")
    private Integer amenityId; // Integer로 변경
    
    // 기본 생성자
    public AccommodationAmenityId() {}

    // 필드 생성자
    public AccommodationAmenityId(Integer accomId, Integer amenityId) {
        this.accomId = accomId;
        this.amenityId = amenityId;
    }

    // Getter & Setter
    public Integer getAccomId() {
        return accomId;
    }

    public void setAccomId(Integer accomId) {
        this.accomId = accomId;
    }

    public Integer getAmenityId() {
        return amenityId;
    }

    public void setAmenityId(Integer amenityId) {
        this.amenityId = amenityId;
    }

    // equals() & hashCode()
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AccommodationAmenityId that = (AccommodationAmenityId) o;
        return Objects.equals(accomId, that.accomId) &&
               Objects.equals(amenityId, that.amenityId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(accomId, amenityId);
    }
}
