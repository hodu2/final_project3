import { useEffect, useState } from "react";
import { Link, useNavigate } from "react-router-dom";
import styles from "./Payment.module.css";

export default function PaymentSuccessPage({ user }) {
  const navigate = useNavigate();

  useEffect(() => {
    if (!user) {
      navigate("/login");
    }
  }, [user, navigate]);

  return (
    <div className={styles.box_section} id={styles.box_after}>
      <img
        width="100px"
        src="https://static.toss.im/illusts/check-blue-spot-ending-frame.png"
        alt="성공 이미지"
      />
      <h2 className={styles.title_after}>
        {user?.username}님, 예약이 확정되었습니다.
        <br />
        <br />
        감사합니다.
      </h2>

      <div className={styles.button_container}>
        <Link to="/mypage/reservation-history">
          <button id="reservationButton" className={styles.button}>
            예약 내역
          </button>
        </Link>
        <Link to="/">
          <button id="homeButton" className={styles.button}>
            홈으로
          </button>
        </Link>
      </div>
    </div>
  );
}
