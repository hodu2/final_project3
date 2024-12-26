import { loadTossPayments } from "@tosspayments/tosspayments-sdk";
import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import axios from "axios";
import styles from "./Payment.module.css";

// 토스페이 키
const clientKey = "test_gck_docs_Ovk5rk1EwkEbP0W43n07xlzm";
const customerKey = generateRandomString();

// 요일 변환 기능
const weekdays = ["일", "월", "화", "수", "목", "금", "토"];
function getDateWithWeekday(dateString) {
  const date = new Date(dateString);
  const weekday = weekdays[date.getDay()];
  return `${dateString} (${weekday})`;
}

export default function PaymentCheckoutPage() {
  const navigate = useNavigate();
  const [userData, setUserData] = useState({ username: "", phone: "" });
  const [numberOfNights, setNumberOfNights] = useState(0);
  const [totalAmount, setTotalAmount] = useState(0);
  const [amount, setAmount] = useState({ currency: "KRW", value: 50000 });
  const [ready, setReady] = useState(false);
  const [widgets, setWidgets] = useState(null);

  const userId = sessionStorage.getItem("userId");
  const checkIn = new Date(sessionStorage.getItem("checkIn"));
  const checkOut = new Date(sessionStorage.getItem("checkOut"));
  const roomPrice = sessionStorage.getItem("roomPrice");

  useEffect(() => {
    const nights = Math.ceil((checkOut - checkIn) / (1000 * 60 * 60 * 24));
    setNumberOfNights(nights);

    const total = roomPrice * nights;
    setTotalAmount(total);
    setAmount({ currency: "KRW", value: total });

    if (userId) {
      axios
        .get(`http://localhost:9090/api/${userId}`, {
          withCredentials: true,
        })
        .then((response) => {
          if (response.status === 200) {
            const user = response.data.user;
            setUserData({
              username: user.username || user.name,
              phone: user.phone,
            });
          } else {
            console.error("사용자 정보를 가져오는데 실패했습니다.");
          }
        })
        .catch((error) => {
          console.error("Error fetching user data:", error);
          if (error.response && error.response.status === 401) {
            navigate("/login");
          }
        });
    }
  }, [navigate]);

  useEffect(() => {
    async function fetchPaymentWidgets() {
      try {
        const tossPayments = await loadTossPayments(clientKey);
        const widgets = tossPayments.widgets({ customerKey });
        setWidgets(widgets);
      } catch (error) {
        console.error("Error fetching payment widget:", error);
      }
    }
    fetchPaymentWidgets();
  }, []);

  useEffect(() => {
    async function renderPaymentWidgets() {
      if (widgets == null) {
        return;
      }
      await widgets.setAmount(amount);
      await Promise.all([
        widgets.renderPaymentMethods({
          selector: "#payment-method",
          variantKey: "DEFAULT",
        }),
      ]);
      setReady(true);
    }
    renderPaymentWidgets();
  }, [widgets, amount]);

  const handlePayment = async () => {
    try {
      await widgets.requestPayment({
        orderId: generateRandomString(),
        orderName: sessionStorage.getItem("accomName"),
        customerEmail: "example@example.com",
        customerName: userData.username,
      });
      const reservationData = {
        user_id: parseInt(sessionStorage.getItem("userId")),
        accom_id: parseInt(sessionStorage.getItem("accomId")),
        roomType: sessionStorage.getItem("roomType"),
        roomPrice: parseInt(sessionStorage.getItem("roomPrice")),
        number_guests: parseInt(sessionStorage.getItem("guests")),
        checkInDate: sessionStorage.getItem("checkIn"),
        checkOutDate: sessionStorage.getItem("checkOut"),
      };
      console.log("Reservation Data:", reservationData);
      await axios.post(
        "http://localhost:9090/api/reservation/create",
        reservationData
      );
      navigate("/payment/success");
    } catch (error) {
      navigate("/payment/fail");
    }
  };

  return (
    <>
      <main>
        <div className={styles.headline}>
          <h1>예약 확인 및 결제</h1>
        </div>
        <div className={styles.section_double}>
          <div className={styles.section_double_left}>
            <div className={styles.section_resv_check}>
              <div className={styles.title} id="user-title">
                예약자 정보
              </div>
              <div className={styles.box_user} id="user_name">
                <label>
                  <span>예약자 이름</span>
                  <div className={styles.label}>
                    <strong>{userData.username} 님</strong>
                  </div>
                </label>
              </div>
              <div className={styles.box_user} id="user_phone">
                <label>
                  <span>휴대폰 번호</span>
                  <div className={styles.label}>
                    <strong>{userData.phone}</strong>
                  </div>
                </label>
              </div>
            </div>
            <div className={styles.section_payment}>
            <div className={styles.divider}></div>
              <div className={styles.title} id="payment-title">
                결제 수단
              </div>
              <div className="wrapper">
                <div className="box_section">
                  <div id="payment-method" />
                </div>
              </div>
            </div>
          </div>
          <div className={styles.section_double_right}>
            <section className={styles.section_sticky}>
              <div className={styles.section_accom_info}>
                <div className={styles.title} id={styles.accom_title}>
                  {sessionStorage.getItem("accomName")}
                </div>
                <img
                  src={sessionStorage.getItem("image")?.split(",")[0].trim()}
                  className={styles.accomImg}
                  alt="숙소 이미지"
                />
                <div>
                  <table>
                    <tbody>
                      <tr className="accom_room_type">
                        <td className={styles.paytd}>객실</td>
                        <td>{sessionStorage.getItem("roomType")}</td>
                      </tr>
                      <tr className="accom_date">
                        <td className={styles.paytd}>숙박 일정</td>
                        <td>
                          {getDateWithWeekday(
                            sessionStorage.getItem("checkIn")
                          )}{" "}
                          &nbsp; ~ &nbsp;
                          {getDateWithWeekday(
                            sessionStorage.getItem("checkOut")
                          )}{" "}
                           ({numberOfNights}박)
                        </td>
                      </tr>
                      <tr className="accom_max_person">
                        <td className={styles.paytd}>숙박인원</td>
                        <td>
                          {sessionStorage.getItem("guests")}인 / 최대{" "}
                          {sessionStorage.getItem("maxGuests")}인
                        </td>
                      </tr>
                    </tbody>
                  </table>
                </div>
              </div>
              <div className={styles.section_amount}>
                <div className={styles.section_amount_price}>
                  <div className={styles.title} id="amount-title">
                    결제 정보
                  </div>
                  <div>
                    <ul className={styles.section_amount_content}>
                      <li>
                        <span className="accom_price_with_day">
                          객실 가격 ({numberOfNights}박)
                        </span>
                        <strong className="price">
                          {parseInt(
                            sessionStorage.getItem("roomPrice")
                          ).toLocaleString()}
                          원
                        </strong>
                      </li>
                    </ul>
                    <div className={styles.divider}></div>
                    <ul className={styles.section_amount_content}>
                      <li className={styles.total_price_item}>
                        <span>총 결제 금액</span>
                        <strong className={styles.total_price}>
                          {totalAmount.toLocaleString()} 원
                        </strong>
                      </li>
                    </ul>
                  </div>
                  <div className={styles.section_button}>
                    <button
                      className={styles.button_payment}
                      disabled={!ready}
                      onClick={handlePayment}
                    >
                      결제하기
                    </button>
                  </div>
                </div>
              </div>
            </section>
          </div>
        </div>
      </main>
    </>
  );
}

function generateRandomString() {
  return window.btoa(Math.random().toString()).slice(0, 20);
}
