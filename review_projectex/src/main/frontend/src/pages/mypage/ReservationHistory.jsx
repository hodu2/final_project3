// ReservationHistory.jsx
import React, { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import styles from "./ReservationHistory.module.css";
import axios from "axios";

const ReservationHistory = React.memo(({ user }) => {
  const [bookedReservations, setBookedReservations] = useState([]);
  const [cancelledReservations, setCancelledReservations] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);
  const [showConfirmModal, setShowConfirmModal] = useState(false);
  const [selectedReservation, setSelectedReservation] = useState(null);

  const navigate = useNavigate();
  // 숙박 일수 계산
  function calculateNights(checkInDate, checkOutDate) {
    const startDate = new Date(parseInt(checkInDate));
    const endDate = new Date(parseInt(checkOutDate));
    return Math.ceil((endDate - startDate) / (1000 * 60 * 60 * 24));
  }

  const userId = user?.id;

  function formatDate(dateString) {
    const date = new Date(parseInt(dateString));
    return date.toLocaleDateString("ko-KR", {
      year: "numeric",
      month: "2-digit",
      day: "2-digit",
    });
  }

  useEffect(() => {
    if (userId) {
      fetchReservations(userId);
    } else {
      console.error("User ID가 유효하지 않습니다.");
      setLoading(false);
    }
  }, [userId]);

  const fetchReservations = (userId) => {
    Promise.all([
      axios.get(`http://localhost:9090/api/reservation/search/${userId}`, {
        withCredentials: true,
      }),
      axios.get(`http://localhost:9090/api/reservation/cancelled/${userId}`, {
        withCredentials: true,
      }),
    ])
      .then(([ongoingRes, cancelledRes]) => {
        const bookedReservations = ongoingRes.data.filter(
          (reservation) => reservation.resvStatus === "BOOKED"
        );
        setBookedReservations(bookedReservations);
        setCancelledReservations(cancelledRes.data);
      })
      .catch((error) => {
        setError("예약 데이터를 불러오는 중 문제가 발생했습니다.");
      })
      .finally(() => {
        setLoading(false);
      });
  };

  const handleCancelClick = (reservation) => {
    if (reservation && reservation.resvId) {
      setSelectedReservation(reservation);
      setShowConfirmModal(true);
    }
  };

  const handleConfirmCancel = () => {
    if (selectedReservation && selectedReservation.resvId) {
      axios
        .post(
          `http://localhost:9090/api/reservation/${selectedReservation.resvId}/cancel`,
          {},
          { withCredentials: true }
        )
        .then(() => {
          fetchReservations(userId);
          setShowConfirmModal(false);
        })
        .catch((error) => {
          setError("예약 취소 중 문제가 발생했습니다.");
        });
    }
  };

  if (loading) return <p>로딩 중...</p>;
  if (error) return <p>{error}</p>;

  return (
    <div className={styles.historyWrap}>
      <h1 className={styles.historyContainer}>예약 내역</h1>
      <div className={styles.content}>
        <section className={styles.section}>
          <h2 className={styles.subtitle}>진행 중인 예약</h2>
          {bookedReservations.length > 0 ? (
            <ul className={styles.list}>
              {bookedReservations.map((reservationData) => (
                <li
                  key={reservationData.resvId}
                  className={styles.listItem}
                >
                  <div className={styles.itemHeader}>
                    <span className={styles.dateInfo}>
                      {formatDate(reservationData.checkInDate)} ~{" "}
                      {formatDate(reservationData.checkOutDate)} (
                      {calculateNights(
                        reservationData.checkInDate,
                        reservationData.checkOutDate
                      )}
                      박)
                    </span>
                    <button
                      className={styles.cancelButton}
                      onClick={(e) => {
                        e.stopPropagation();
                        handleCancelClick(reservationData);
                      }}
                    >
                      예약 취소
                    </button>
                  </div>
                  <div className={styles.itemContent}>
                    <div className={styles.accommodationName}>
                      {reservationData.accomName}
                    </div>
                    <div className={styles.roomInfo}>
                      {reservationData.roomType} |{" "}
                      {reservationData.numberGuests}인
                    </div>
                    <div className={styles.priceInfo}>
                      전체 숙박 가격: {Number(reservationData.amount).toLocaleString()}원
                      
                    </div>
                  </div>
                </li>
              ))}
            </ul>
          ) : (
            <p className={styles.emptyMessage}>
              현재 진행 중인 예약이 없습니다.
            </p>
          )}
        </section>

        <section className={styles.section}>
          <h2 className={styles.subtitle}>취소된 예약</h2>
          {cancelledReservations.length > 0 ? (
            <ul className={styles.list}>
              {cancelledReservations.map((reservationData) => (
                <li
                  key={reservationData.resvId}
                  className={`${styles.listItem} ${styles.canceledItem}`}
                >
                  <div className={styles.itemHeader}>
                    <span className={styles.dateInfo}>
                      {formatDate(reservationData.checkInDate)} ~{" "}
                      {formatDate(reservationData.checkOutDate)} (
                      {calculateNights(
                        reservationData.checkInDate,
                        reservationData.checkOutDate
                      )}
                      박)
                    </span>
                  </div>
                  <div className={styles.itemContent}>
                    <div className={styles.accommodationName}>
                      {reservationData.accomName}
                    </div>
                    <div className={styles.roomInfo}>
                      {reservationData.roomType} |{" "}
                      {reservationData.numberGuests}인
                    </div>
                  </div>
                </li>
              ))}
            </ul>
          ) : (
            <p className={styles.emptyMessage}>취소된 예약이 없습니다.</p>
          )}
        </section>
      </div>

      {showConfirmModal && (
        <div className={styles.modal}>
          <div className={styles.modalContent}>
            <p>예약을 취소하시겠습니까?</p>
            <div className={styles.modalButtons}>
              <button onClick={handleConfirmCancel}>예</button>
              <button onClick={() => setShowConfirmModal(false)}>아니오</button>
            </div>
          </div>
        </div>
      )}
    </div>
  );
});

export default ReservationHistory;
