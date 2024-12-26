package com.codingbox.tripjava.entity;

import java.io.Serializable;
import java.util.Objects;

public class WishlistId implements Serializable {

    private Integer userId;
    private Integer accomId;

    // 기본 생성자
    public WishlistId() {}

    public WishlistId(Integer userId, Integer accomId) {
        this.userId = userId;
        this.accomId = accomId;
    }

    // equals()와 hashCode() 메서드 오버라이드
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WishlistId that = (WishlistId) o;
        return Objects.equals(userId, that.userId) && Objects.equals(accomId, that.accomId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, accomId);
    }
}
