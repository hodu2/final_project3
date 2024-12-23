package com.codingbox.review_projectex.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "WISHLIST")
@IdClass(WishlistId.class) // @IdClass를 사용해 두 필드를 합친 식별자 클래스를 지정
public class Wishlist {

    // 사용자 ID (기본키의 일부)
    @Id
    @Column(name = "user_id")
    private Integer userId;

    // 숙소 ID (기본키의 일부)
    @Id
    @Column(name = "accom_id")
    private Integer accomId;

    // 숙소와의 관계 (FK)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "accom_id", referencedColumnName = "accom_id", insertable = false, updatable = false)
    private Accommodation accommodation;

    // 기본 생성자
    public Wishlist() {
    }

    // 생성자
    public Wishlist(Integer userId, Integer accomId) {
        this.userId = userId;
        this.accomId = accomId;
    }
}
