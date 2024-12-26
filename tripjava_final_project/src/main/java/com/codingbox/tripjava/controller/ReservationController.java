package com.codingbox.tripjava.controller;

import com.codingbox.tripjava.dto.CancelReservationListDTO;
import com.codingbox.tripjava.dto.ReservationFormDTO;
import com.codingbox.tripjava.dto.ReservationListDTO;
import com.codingbox.tripjava.service.ReservationService;

import lombok.RequiredArgsConstructor;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/reservation")
public class ReservationController {
	private static final Logger logger = LoggerFactory.getLogger(ReservationController.class);
	private final ReservationService reservationService;

	/**
	 * 예약 생성
	 */
	@PostMapping("/create")
	public ResponseEntity<String> createReservation(@RequestBody ReservationFormDTO request) {
	    logger.info("예약 생성 요청 받음: {}", request);
	    try {
	        // 각 필드 개별 검증
	        if (request.getUser_id() == null) {
	            logger.warn("user_id가 누락되었습니다.");
	            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("user_id가 누락되었습니다.");
	        }
	        if (request.getAccom_id() == null) {
	            logger.warn("accom_id가 누락되었습니다.");
	            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("accom_id가 누락되었습니다.");
	        }
	        if (request.getRoomType() == null) {
	            logger.warn("roomType이 누락되었습니다.");
	            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("roomType이 누락되었습니다.");
	        }
	        if (request.getCheckInDate() == null) {
	            logger.warn("checkInDate가 누락되었습니다.");
	            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("checkInDate가 누락되었습니다.");
	        }
	        if (request.getCheckOutDate() == null) {
	            logger.warn("checkOutDate가 누락되었습니다.");
	            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("checkOutDate가 누락되었습니다.");
	        }

	        // int 타입 필드에 대한 추가 검증
	        if (request.getRoomPrice() <= 0) {
	            logger.warn("유효하지 않은 가격: {}", request.getRoomPrice());
	            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("유효하지 않은 가격입니다.");
	        }
	        if (request.getNumber_guests() <= 0) {
	            logger.warn("유효하지 않은 게스트 수: {}", request.getNumber_guests());
	            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("유효하지 않은 게스트 수입니다.");
	        }

	        // 서비스 계층 호출
	        reservationService.createReservation(request);
	        logger.info("예약 생성 성공: {}", request);
	        return ResponseEntity.ok("예약이 성공적으로 생성되었습니다.");
	    } catch (IllegalArgumentException e) {
	        logger.error("예약 생성 실패 (잘못된 인자): {}", e.getMessage());
	        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
	    } catch (Exception e) {
	        logger.error("예약 생성 중 예외 발생", e);
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("예약 생성 중 오류가 발생했습니다: " + e.getMessage());
	    }
	}

	/**
	 * 모든 예약 조회
	 */
	@GetMapping("/search/{userId}")
	public ResponseEntity<?> getUserReservations(@PathVariable("userId") Integer userId) {
		try {
			List<ReservationListDTO> reservations = reservationService.getReservationsByUserId(userId);
			return ResponseEntity.ok(reservations); // DTO 반환
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("예약 내역을 조회하는 중 오류가 발생했습니다.");
		}
	}

	// 예약 취소
	@PostMapping("/{resvId}/cancel")
	public ResponseEntity<String> cancelReservation(@PathVariable Integer resvId) {
		try {
			reservationService.cancelReservation(resvId);
			return ResponseEntity.ok("예약이 성공적으로 취소되었습니다.");
		} catch (IllegalArgumentException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("예약 취소 중 오류가 발생했습니다.");
		}
	}

	// 취소된 예약 조회
	@GetMapping("/cancelled/{userId}")
	public ResponseEntity<?> getCancelledReservations(@PathVariable Integer userId) {
		try {
			List<CancelReservationListDTO> cancelledReservations = reservationService
					.getCancelledReservationsByUserId(userId);
			return ResponseEntity.ok(cancelledReservations);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("취소된 예약 내역을 조회하는 중 오류가 발생했습니다.");
		}
	}
}