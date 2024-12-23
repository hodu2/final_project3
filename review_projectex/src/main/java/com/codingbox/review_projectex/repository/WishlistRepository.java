package com.codingbox.review_projectex.repository;

import com.codingbox.review_projectex.entity.Wishlist;
import com.codingbox.review_projectex.entity.WishlistId;

import org.springframework.data.jpa.repository.JpaRepository;

public interface WishlistRepository extends JpaRepository<Wishlist, WishlistId> {
    boolean existsByUserIdAndAccomId(int userId, int accomId);

    void deleteByUserIdAndAccomId(int userId, int accomId);
}
