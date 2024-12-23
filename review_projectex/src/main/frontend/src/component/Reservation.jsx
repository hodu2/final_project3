import React, { useState, useEffect } from "react";
import { useNavigate } from "react-router-dom";
import "./Detail.css";

export default function Reservation({ accommodation }) {
  const [selectedRoom, setSelectedRoom] = useState("");
  const [sortedRooms, setSortedRooms] = useState([]);
  const [checkIn, setCheckIn] = useState("");
  const [checkOut, setCheckOut] = useState("");
  const [guests, setGuests] = useState(0);
  const navigate = useNavigate();

  useEffect(() => {
    // 세션 스토리지에서 체크인, 체크아웃 날짜 가져오기
    const storedCheckIn = sessionStorage.getItem("checkIn");
    const storedCheckOut = sessionStorage.getItem("checkOut");
    const storedGuests = sessionStorage.getItem("guests");

    if (storedCheckIn) setCheckIn(storedCheckIn);
    if (storedCheckOut) setCheckOut(storedCheckOut);
    if (storedGuests) setGuests(parseInt(storedGuests, 10));

    if (accommodation.accommodation.roomTypePrices) {
      const sorted = [...accommodation.accommodation.roomTypePrices]
        .sort((a, b) => a.roomPrice - b.roomPrice)
        .filter((room) => room.maxGuests >= guests);
      setSortedRooms(sorted);
    }
  }, [accommodation, guests]);

  const handleRoomChange = (e) => {
    setSelectedRoom(e.target.value);
  };

  const handleReservation = () => {
    if (!selectedRoom) {
      alert("객실을 선택해주세요.");
      return;
    }

    // 객실 종류 가격 오름차순 정렬
    const selectedRoomData = sortedRooms.find(
      (room) => room.roomType === selectedRoom
    );

    // 세션 스토리지에 예약 정보 저장
    sessionStorage.setItem("accomName", accommodation.accommodation.accomName);
    sessionStorage.setItem("checkInDate", sessionStorage.getItem("checkIn"));
    sessionStorage.setItem("checkOutDate", sessionStorage.getItem("checkOut"));
    sessionStorage.setItem("roomType", selectedRoomData.roomType);
    sessionStorage.setItem("roomPrice", selectedRoomData.roomPrice);
    sessionStorage.setItem("maxGuests", selectedRoomData.maxGuests);
    sessionStorage.setItem("numberGuests", sessionStorage.getItem("guests"));

    // PaymentCheckoutPage로 이동
    navigate("/payment/checkout");
  };

  return (
    <div className="reservation">
      <h2>예약 정보</h2>
      <table>
        <tbody>
          <tr>
            <td className="in">
              체크인
              <br />
              {checkIn}
            </td>
            <td className="out">
              체크아웃
              <br />
              {checkOut}
            </td>
          </tr>
          <tr>
            <td colSpan="2">
              <select onChange={handleRoomChange} value={selectedRoom}>
                <option value="">객실을 선택하세요</option>
                {sortedRooms.map((room, index) => (
                  <option key={index} value={room.roomType}>
                    {room.roomType === "NONE" ? "" : `${room.roomType} - `}
                    {room.roomPrice ? room.roomPrice.toLocaleString() : ""}원
                    (최대 {room.maxGuests}명)
                  </option>
                ))}
              </select>
            </td>
          </tr>
        </tbody>
      </table>
      <br />
      <button className="confirm" onClick={handleReservation}>
        예약하기
      </button>
    </div>
  );
}
