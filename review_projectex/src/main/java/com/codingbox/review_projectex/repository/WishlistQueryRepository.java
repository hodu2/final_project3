package com.codingbox.review_projectex.repository;

import com.codingbox.review_projectex.dto.WishlistDetailDTO;
import com.codingbox.review_projectex.dto.QAccommodation;
import com.codingbox.review_projectex.dto.QAccommodationImage;
import com.codingbox.review_projectex.dto.QReview;
import com.codingbox.review_projectex.dto.QRoomTypePrice;
import com.codingbox.review_projectex.dto.QWishlist;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class WishlistQueryRepository {
    private final JPAQueryFactory queryFactory;

    public WishlistQueryRepository(JPAQueryFactory queryFactory) {
        this.queryFactory = queryFactory;
    }

    public List<WishlistDetailDTO> findWishlistDetailsByUserId(int userId) {
        QWishlist wishlist = QWishlist.wishlist;
        QRoomTypePrice roomTypePrice = QRoomTypePrice.roomTypePrice;
        QAccommodation accommodation = QAccommodation.accommodation;
        QAccommodationImage accommodationImage = QAccommodationImage.accommodationImage;
        QReview review = QReview.review;

        return queryFactory
                .select(Projections.constructor(
                        WishlistDetailDTO.class,
                        accommodation.accomId,
                        accommodation.accomName,
                        accommodation.type, // Enum to String
                        review.rating.avg().as("averageRating"),
                        roomTypePrice.roomPrice.min().as("cheapestPrice"), // 최저 가격
                        accommodationImage.accomImagePath.min().as("imageUrl")
                ))
                .from(wishlist)
                .join(wishlist.accommodation, accommodation)
                .leftJoin(accommodation.roomTypePrices, roomTypePrice)
                .leftJoin(accommodation.images, accommodationImage)
                .leftJoin(accommodation.reviews, review)
                .where(wishlist.userId.eq(userId))
                .groupBy(accommodation.accomId,
                        accommodation.accomName,
                        accommodation.type)
                .fetch();
    }
}
