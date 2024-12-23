package com.codingbox.tripjava.dto;

public class WishlistDTO {
    private int userId; // 사용자 ID
    private int accomId; // 숙소 ID

    public WishlistDTO() {}

    public WishlistDTO(int userId, int accomId) {
        this.userId = userId;
        this.accomId = accomId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getAccomId() {
        return accomId;
    }

    public void setAccomId(int accomId) {
        this.accomId = accomId;
    }
}
