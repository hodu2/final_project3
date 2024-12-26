package com.codingbox.tripjava.repository;

import com.codingbox.tripjava.dto.AccommodationRecommendationDTO;
import com.codingbox.tripjava.dto.LocationDTO;
import com.codingbox.tripjava.dto.SearchAccommodationRequestDTO;
import com.codingbox.tripjava.dto.SearchResultDTO;
import com.codingbox.tripjava.entity.Accommodation;
import com.codingbox.tripjava.entity.AccommodationAmenity;
import com.codingbox.tripjava.entity.QAccommodation;
import com.codingbox.tripjava.entity.QAccommodationAmenity;
import com.codingbox.tripjava.entity.QAccommodationImage;
import com.codingbox.tripjava.entity.QReview;
import com.codingbox.tripjava.entity.QRoomTypePrice;
import com.codingbox.tripjava.entity.RoomTypePrice;
import com.codingbox.tripjava.enums.AccommodationType;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;

@Repository
public class AccommodationRepository2 {

    private final JPAQueryFactory queryFactory;

    public AccommodationRepository2(JPAQueryFactory queryFactory) {
        this.queryFactory = queryFactory;
    }

    // 메인 페이지 추천 숙소 가져오기
    public List<AccommodationRecommendationDTO> findTop12Accommodations() {
        QAccommodation accommodation = QAccommodation.accommodation;
        QRoomTypePrice roomTypePrice = QRoomTypePrice.roomTypePrice;
        QAccommodationImage accommodationImage = QAccommodationImage.accommodationImage;
        QReview review = QReview.review;

        return queryFactory
                .select(Projections.constructor(AccommodationRecommendationDTO.class,
                        accommodation.accomId.as("accomId"),
                        accommodation.accomName.as("accomName"),
                        accommodation.type.as("type"),
                        roomTypePrice.roomPrice.min().as("cheapestPrice"), // 최저 가격
                        accommodationImage.accomImagePath.min().as("representativeImage"), // 대표 이미지
                        review.rating.avg().as("averageRating") // 평균 평점
                ))
                .from(accommodation)
                .leftJoin(accommodation.roomTypePrices, roomTypePrice)
                .leftJoin(accommodation.images, accommodationImage)
                .leftJoin(accommodation.reviews, review)
                .where(accommodation.accomId.between(1, 12)) // Top 12 조건
                .groupBy(
                        accommodation.accomId,
                        accommodation.accomName,
                        accommodation.type
                )
                .fetch();
    }

    
    
    public List<Accommodation> searchAccommodations(SearchAccommodationRequestDTO request) {
    	if ((request.getAccomName() == null || request.getAccomName().isEmpty()) &&
	        (request.getAccomAddress() == null || request.getAccomAddress().isEmpty())) {
	        throw new IllegalArgumentException("검색 조건이 필요합니다.");
		}
        QAccommodation accommodation = QAccommodation.accommodation;
        QRoomTypePrice roomTypePrice = QRoomTypePrice.roomTypePrice;

        return queryFactory.selectFrom(accommodation)
                .leftJoin(accommodation.roomTypePrices, roomTypePrice)
                .where(
                        locationContains(request.getAccomName(), request.getAccomAddress()),
                        typeMatches(accommodation, request.getType()),                     // 숙소 유형 필터
                        guestsLeq(roomTypePrice, request.getGuests()),                     // 최대 인원 필터
                        availableDates(accommodation, request.getCheckIn(), request.getCheckOut()), // 날짜 필터
                        priceRangeMatches(roomTypePrice, request.getMinPrice(), request.getMaxPrice()) // 가격 필터
                )
                .distinct()
                .fetch();
    }
    
    public List<SearchResultDTO> searchAccommodationsWithImage(SearchAccommodationRequestDTO request) {
        QAccommodation accommodation = QAccommodation.accommodation;
        QRoomTypePrice roomTypePrice = QRoomTypePrice.roomTypePrice;
        QAccommodationImage accommodationImage = QAccommodationImage.accommodationImage;
        QReview review = QReview.review;

        return queryFactory
            .select(Projections.constructor(SearchResultDTO.class,
            	accommodation.accomId.as("accomId"),
                accommodation.accomName.as("accomName"),
                accommodation.type.as("type"),
                accommodation.address.as("address"),
                accommodation.avaDatesStart.as("avaDatesStart"),
                accommodation.avaDatesEnd.as("avaDatesEnd"),
                roomTypePrice.roomPrice.min().as("cheapestPrice"),
                accommodationImage.accomImagePath.min().as("representativeImage"),
                review.rating.avg().as("averageRating") // 리뷰 평점 평균 추가
            ))
            .from(accommodation)
            .leftJoin(accommodation.roomTypePrices, roomTypePrice)
            .leftJoin(accommodation.images, accommodationImage)
            .leftJoin(accommodation.reviews, review) // 숙소와 리뷰 조인
            .where(
                locationContains(request.getAccomName(), request.getAccomAddress()),
                typeMatches(accommodation, request.getType()),
                guestsLeq(roomTypePrice, request.getGuests()),
                availableDates(accommodation, request.getCheckIn(), request.getCheckOut()),
                priceRangeMatches(roomTypePrice, request.getMinPrice(), request.getMaxPrice())
            )
            .groupBy(
                accommodation.accomId,
                accommodation.accomName,
                accommodation.type,
                accommodation.address,
                accommodation.avaDatesStart,
                accommodation.avaDatesEnd,
                review.accommodation.accomId // 리뷰의 숙소 ID 추가
            )
            .fetch();
    }

    private BooleanExpression typeMatches(QAccommodation accommodation, AccommodationType type) {
        if (type == null) {
            return null; // 필터 조건이 없으면 건너뜀
        }
        return accommodation.type.eq(type);
    }

    private BooleanExpression priceRangeMatches(QRoomTypePrice roomTypePrice, Integer minPrice, Integer maxPrice) {
        if (minPrice == null && maxPrice == null) {
            return null; // 필터 조건이 없으면 건너뜀
        }
        if (minPrice != null && maxPrice != null) {
            return roomTypePrice.roomPrice.between(minPrice, maxPrice); // 범위 조건
        } else if (minPrice != null) {
            return roomTypePrice.roomPrice.goe(minPrice); // 최소 가격만 적용
        } else {
            return roomTypePrice.roomPrice.loe(maxPrice); // 최대 가격만 적용
        }
    }

    

    // 전체 숙소 조회하는 코드 추가본
    public List<Accommodation> findAllAccommodationsWithDetails() {
        QAccommodation accommodation = QAccommodation.accommodation;

        return queryFactory.selectFrom(accommodation)
                .fetch();
    }

    public List<RoomTypePrice> findRoomTypePricesByAccommodationId(int accomId) {
        QRoomTypePrice roomTypePrice = QRoomTypePrice.roomTypePrice;

        return queryFactory.selectFrom(roomTypePrice)
                .where(roomTypePrice.accommodation.accomId.eq(accomId))
                .fetch();
    }

    public List<AccommodationAmenity> findAmenitiesByAccommodationId(int accomId) {
        QAccommodationAmenity accommodationAmenity = QAccommodationAmenity.accommodationAmenity;

        return queryFactory.selectFrom(accommodationAmenity)
                .where(accommodationAmenity.accommodation.accomId.eq(accomId))
                .fetch();
    }

    private BooleanExpression locationContains(String accomName, String accomAddress) {
        QAccommodation accommodation = QAccommodation.accommodation;

        // 숙소 타입 매핑 (한국어 타입을 Enum으로 변환)
        AccommodationType typeFromName = getAccommodationTypeFromName(accomName);

        BooleanExpression typeCondition = typeFromName != null ? accommodation.type.eq(typeFromName) : null;
        BooleanExpression nameCondition = accomName != null ? accommodation.accomName.contains(accomName) : null;
        BooleanExpression addressCondition = accomAddress != null ? accommodation.address.contains(accomAddress) : null;

        // 조건 결합
        BooleanExpression combinedCondition = null;

        if (typeCondition != null) {
            combinedCondition = typeCondition;
        }
        if (nameCondition != null) {
            combinedCondition = combinedCondition != null ? combinedCondition.or(nameCondition) : nameCondition;
        }
        if (addressCondition != null) {
            combinedCondition = combinedCondition != null ? combinedCondition.or(addressCondition) : addressCondition;
        }

        return combinedCondition;
    }

    // 숙소 타입 이름에서 AccommodationType Enum으로 변환
    private AccommodationType getAccommodationTypeFromName(String accomName) {
        if (accomName == null) {
            return null;
        }

        // 이름에서 숙소 타입을 추출
        if (accomName.contains("호텔")) {
            return AccommodationType.HOTEL;
        } else if (accomName.contains("펜션")) {
            return AccommodationType.PENSION;
        } else if (accomName.contains("모텔")) {
            return AccommodationType.MOTEL;
        } else if (accomName.contains("리조트")) {
            return AccommodationType.RESORT;
        } else if (accomName.contains("게스트하우스")) {
            return AccommodationType.GUESTHOUSE;
        }

        return null; // 매칭되지 않는 경우
    }


    private BooleanExpression guestsLeq(QRoomTypePrice roomTypePrice, Integer guests) {
        if (guests == null) {
            return null;
        }
        return roomTypePrice.maxGuests.goe(guests);
    }

    private BooleanExpression availableDates(QAccommodation accommodation, String checkIn, String checkOut) {
        if (checkIn == null || checkOut == null) return null;

        try {
            LocalDate startDate = LocalDate.parse(checkIn);
            LocalDate endDate = LocalDate.parse(checkOut);

            return accommodation.avaDatesStart.loe(java.sql.Date.valueOf(startDate))
                    .and(accommodation.avaDatesEnd.goe(java.sql.Date.valueOf(endDate)));
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("Invalid date format. Expected format: yyyy-MM-dd");
        }
    }
    
    public Accommodation findById(int id) {
        QAccommodation accommodation = QAccommodation.accommodation;
        return queryFactory.selectFrom(accommodation)
                .where(accommodation.accomId.eq(id))
                .fetchOne();
    }

}
