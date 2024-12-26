package com.codingbox.tripjava.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.codingbox.tripjava.entity.Wishlist;
import com.codingbox.tripjava.entity.WishlistId;

public interface WishlistRepository extends JpaRepository<Wishlist, WishlistId> {
    boolean existsByUserIdAndAccomId(int userId, int accomId);

    void deleteByUserIdAndAccomId(int userId, int accomId);
}
