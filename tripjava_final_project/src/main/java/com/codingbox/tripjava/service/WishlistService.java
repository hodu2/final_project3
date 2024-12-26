package com.codingbox.tripjava.service;

import com.codingbox.tripjava.dto.WishlistDetailDTO;
import com.codingbox.tripjava.entity.Wishlist;
import com.codingbox.tripjava.repository.WishlistQueryRepository;
import com.codingbox.tripjava.repository.WishlistRepository;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional
public class WishlistService {
    private final WishlistRepository wishlistRepository;

    public WishlistService(WishlistRepository wishlistRepository, WishlistQueryRepository wishlistQueryRepository) {
        this.wishlistRepository = wishlistRepository;
        this.wishlistQueryRepository = wishlistQueryRepository;
    }

    public boolean isWishlisted(int userId, int accomId) {
        return wishlistRepository.existsByUserIdAndAccomId(userId, accomId);
    }

    public void addWishlist(int userId, int accomId) {
        if (!isWishlisted(userId, accomId)) {
            Wishlist wishlist = new Wishlist(userId, accomId);
            wishlistRepository.save(wishlist);
        }
    }

    public void removeWishlist(int userId, int accomId) {
        wishlistRepository.deleteByUserIdAndAccomId(userId, accomId);
    }
    
    private final WishlistQueryRepository wishlistQueryRepository;

    public List<WishlistDetailDTO> getWishlistDetails(int userId) {
        return wishlistQueryRepository.findWishlistDetailsByUserId(userId);
    }
}
