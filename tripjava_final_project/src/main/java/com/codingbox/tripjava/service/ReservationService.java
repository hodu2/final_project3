package com.codingbox.tripjava.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.codingbox.tripjava.dto.*;
import com.codingbox.tripjava.entity.Accommodation;
import com.codingbox.tripjava.entity.Payment;
import com.codingbox.tripjava.entity.PaymentCancellation;
import com.codingbox.tripjava.entity.Reservation;
import com.codingbox.tripjava.entity.RoomTypePrice;
import com.codingbox.tripjava.entity.User;
import com.codingbox.tripjava.enums.ReservationStatus;
import com.codingbox.tripjava.enums.RoomType;
import com.codingbox.tripjava.repository.*;

import lombok.RequiredArgsConstructor;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class ReservationService {

	private final ReservationRepository1 reservationRepository;
	private final PaymentRepository paymentRepository;
	private final PaymentCancellationRepository paymentCancellationRepository;
	private final AccommodationRepository1 accommodationRepository;
	private final UserwishRepository userRepository;

	/**
	 * 예약 생성 메서드
	 */
	public void createReservation(ReservationFormDTO request) {
		// RoomType enum 변환 부분 제거
		RoomType roomType = request.getRoomType(); // 이미 RoomType 객체임

		// 예약 생성 로직...
		User user = userRepository.findById(request.getUser_id())
				.orElseThrow(() -> new IllegalArgumentException("유효하지 않은 사용자 ID입니다."));

		Accommodation accommodation = accommodationRepository.findById(request.getAccom_id());
		if (accommodation == null) {
			throw new IllegalArgumentException("유효하지 않은 숙소 ID입니다.");
		}

		RoomTypePrice roomTypePrice = accommodation.getRoomTypePrices().stream()
				.filter(rtp -> rtp.getRoomType().equals(roomType)).findFirst()
				.orElseThrow(() -> new IllegalArgumentException("선택된 객실 정보가 없습니다."));

		Reservation reservation = new Reservation(user, roomTypePrice, request.getNumber_guests(),
				request.getRoomPrice(), request.getCheckInDate(), request.getCheckOutDate(), ReservationStatus.BOOKED);

		reservationRepository.save(reservation);

		// 결제 생성
		createPayment(reservation);
	}

	/**
	 * 결제 생성 메서드
	 */
	private void createPayment(Reservation reservation) {
		// 체크인 날짜와 체크아웃 날짜의 차이 계산 (몇 박인지)
		long daysBetween = (reservation.getCheckOutDate().getTime() - reservation.getCheckInDate().getTime())
				/ (1000 * 60 * 60 * 24);

		// 결제 금액 계산: room_price * 숙박일수
		int amount = (int) (reservation.getRoomPrice() * daysBetween);

		// 결제 엔티티 생성
		Payment payment = new Payment(reservation, // 예약 정보 연결 (1:1 관계)
				amount, Timestamp.valueOf(LocalDateTime.now()) // 현재 시간으로 결제 날짜 설정
		);

		// 결제 저장
		paymentRepository.save(payment);

		// 예약에 결제 정보 연결 후 저장
		reservation.setPayment(payment);
		reservationRepository.save(reservation);
	}

	/**
	 * 모든 예약 조회 메서드
	 */
	public List<ReservationListDTO> getReservationsByUserId(Integer userId) {
		// 해당 사용자의 예약 리스트 조회
		List<Reservation> reservations = reservationRepository.findByUserId(userId);

		// Reservation 엔티티를 ReservationList DTO로 변환하여 반환
		return reservations.stream()
				.map(reservation -> new ReservationListDTO(reservation.getResvId(), reservation.getUser().getUsername(),
						reservation.getRoomTypePrice().getAccommodation().getAccomName(),
						reservation.getRoomTypePrice().getRoomType(),
						reservation.getPayment() != null ? reservation.getPayment().getAmount() : 0,
						reservation.getNumberGuests(), new java.sql.Date(reservation.getCheckInDate().getTime()),
						new java.sql.Date(reservation.getCheckOutDate().getTime()), reservation.getResvStatus()))
				.collect(Collectors.toList());
	}

	/**
	 * 예약 취소 메서드
	 *
	 * @param resvId 예약 ID
	 */
	public void cancelReservation(Integer resvId) {
		Reservation reservation = reservationRepository.findByResvId(resvId);
		if (reservation == null) {
			throw new IllegalArgumentException("예약을 찾을 수 없습니다.");
		}

		if (reservation.getResvStatus() == ReservationStatus.CANCEL) {
			throw new IllegalStateException("이미 취소된 예약입니다.");
		}

		Payment payment = reservation.getPayment();
		if (payment == null) {
			throw new IllegalArgumentException("해당 예약에 대한 결제가 존재하지 않습니다.");
		}

		// PaymentCancellation 생성 및 저장
		Timestamp currentTimestamp = Timestamp.valueOf(LocalDateTime.now());
		PaymentCancellation cancellation = new PaymentCancellation(payment.getPaymentId(), currentTimestamp, payment);
		paymentCancellationRepository.save(cancellation);

		// 예약 상태 변경 및 저장
		reservation.setResvStatus(ReservationStatus.CANCEL);
		reservationRepository.save(reservation);
	}

	/**
	 * 취소된 예약 조회 메서드
	 */
	public List<CancelReservationListDTO> getCancelledReservationsByUserId(Integer userId) {
		List<Reservation> reservations = reservationRepository.findCancelledReservationsByUserId(userId);

		return reservations.stream()
				.map(reservation -> new CancelReservationListDTO(reservation.getPayment().getPaymentId(),
						reservation.getResvId(), reservation.getUser().getUsername(),
						reservation.getRoomTypePrice().getAccommodation().getAccomName(),
						reservation.getRoomTypePrice().getRoomType(), reservation.getNumberGuests(),
						new java.sql.Date(reservation.getCheckInDate().getTime()),
						new java.sql.Date(reservation.getCheckOutDate().getTime()), reservation.getResvStatus()))
				.collect(Collectors.toList());
	}

}