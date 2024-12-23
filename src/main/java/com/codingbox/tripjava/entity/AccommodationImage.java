package com.codingbox.tripjava.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "ACCOMMODATION_IMAGE")
@Getter @Setter
@ToString
public class AccommodationImage {

   @Id
   @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "accom_image_id_seq")
   @SequenceGenerator(name = "accom_image_id_seq", sequenceName = "accom_image_id_seq", allocationSize = 1)
   @Column(name = "accom_image_id")
   private int accomImageId;
   
   @Column(name = "accom_image_path")
   private String accomImagePath;
   
   @ManyToOne
   @JoinColumn(name = "accom_id")
   private Accommodation accommodation;
   
   public String getImagePath() {
       return getAccomImagePath();
   }
   // 기본 생성자 추가
   public AccommodationImage() {
   }

   // 숙소 이미지 초기화 생성자
   public AccommodationImage(int accomImageId, String accomImagePath, Accommodation accommodation) {
      this.accomImageId = accomImageId;
      this.accomImagePath = accomImagePath;
      this.accommodation = accommodation;
   }
}
