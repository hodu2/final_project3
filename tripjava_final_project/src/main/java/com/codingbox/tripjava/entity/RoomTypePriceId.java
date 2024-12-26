package com.codingbox.tripjava.entity;

import java.io.Serializable;
import java.util.Objects;

import com.codingbox.tripjava.enums.RoomType;


/**
 * ROOM_TYPE_PRICE의 복합 기본 키 클래스
 * acco_id와 room_type을 결합하여 하나의 기본 키로 사용합니다.
 */
public class RoomTypePriceId implements Serializable {

    private RoomType roomType; // 룸 타입
    private Integer accomId;   // 숙소 ID (Integer)

    public RoomTypePriceId() {
    }

    // 생성자: roomType과 accomId를 초기화
    public RoomTypePriceId(RoomType roomType, Integer accomId) {
        this.roomType = roomType;
        this.accomId = accomId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RoomTypePriceId that = (RoomTypePriceId) o;
        return Objects.equals(accomId, that.accomId) && 
               Objects.equals(roomType, that.roomType);
    }

    @Override
    public int hashCode() {
        return Objects.hash(roomType, accomId);
    }
}

