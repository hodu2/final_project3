package com.codingbox.tripjava.dto;

import com.codingbox.tripjava.dto.ReviewDTO;
import com.codingbox.tripjava.entity.Accommodation;
import com.codingbox.tripjava.entity.AccommodationAmenity;
import com.codingbox.tripjava.entity.AccommodationImage;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Accommodation 데이터를 외부로 전달하기 위한 DTO 클래스
 */
@Getter
@Setter
public class AccommodationDTO {

    private Integer accomId;
    private String accomName;
    private String type; // 문자열로 사용 (Enum 매핑 대신)
    private String description;
    private String address;
    private double latitude;
    private double longitude;
    private String accomTel;
    private String avaDatesStart;
    private String avaDatesEnd;
    private List<String> amenities; // 편의시설 정보
    private List<String> images; // 이미지 경로
    private List<ReviewDTO> reviews; // 리뷰 리스트
    private List<RoomTypePriceDTO2> roomTypePrices; // RoomTypePriceDTO 리스트 추가

    // 기본 생성자
    public AccommodationDTO() {}

    /**
     * Accommodation 엔티티를 DTO로 변환하는 생성자
     */
    public AccommodationDTO(Accommodation accommodation) {
        this.accomId = accommodation.getAccomId();
        this.accomName = accommodation.getAccomName();
        this.type = accommodation.getType().name(); // Enum을 문자열로 변환
        this.description = accommodation.getDescription();
        this.address = accommodation.getAddress();
        this.latitude = accommodation.getLatitude();
        this.longitude = accommodation.getLongitude();
        this.accomTel = accommodation.getAccomTel();

        // 날짜를 문자열로 변환 (필요에 따라 포맷 설정 가능)
        this.avaDatesStart = accommodation.getAvaDatesStart() != null
                ? accommodation.getAvaDatesStart().toString()
                : null;
        this.avaDatesEnd = accommodation.getAvaDatesEnd() != null
                ? accommodation.getAvaDatesEnd().toString()
                : null;

        // 편의시설 리스트를 이름(String)으로 변환
        this.amenities = accommodation.getAmenities() != null
                ? accommodation.getAmenities().stream()
                .map(AccommodationAmenity::getAmenityName) // 편의시설 이름 가져오기
                .collect(Collectors.toList())
                : null;

        // 이미지 리스트를 경로(String)로 변환
        this.images = accommodation.getImages() != null
                ? accommodation.getImages().stream()
                .map(AccommodationImage::getImagePath) // 이미지 경로 가져오기
                .collect(Collectors.toList())
                : null;

        // 리뷰 엔티티를 DTO로 변환
        this.reviews = accommodation.getReviews() != null
                ? accommodation.getReviews().stream()
                .map(ReviewDTO::new) // ReviewDTO 변환 생성자 호출
                .collect(Collectors.toList())
                : null;

        // RoomTypePriceDTO 리스트를 변환하여 추가
        this.roomTypePrices = accommodation.getRoomTypePrices() != null
                ? accommodation.getRoomTypePrices().stream()
                .map(RoomTypePriceDTO2::new) // RoomTypePriceDTO 변환 생성자 호출
                .collect(Collectors.toList())
                : null;
    }

    public AccommodationDTO(Integer accomId, String accomName, String type, String description, 
                            String address, double latitude, double longitude, String accomTel, 
                            String avaDatesStart, String avaDatesEnd, List<String> amenities, 
                            List<String> images, List<ReviewDTO> reviews, List<RoomTypePriceDTO2> roomTypePrices) {
        this.accomId = accomId;
        this.accomName = accomName;
        this.type = type;
        this.description = description;
        this.address = address;
        this.latitude = latitude;
        this.longitude = longitude;
        this.accomTel = accomTel;
        this.avaDatesStart = avaDatesStart;
        this.avaDatesEnd = avaDatesEnd;
        this.amenities = amenities;
        this.images = images;
        this.reviews = reviews;
        this.roomTypePrices = roomTypePrices; // roomTypePrices 필드 초기화
    }
}
