import React, { useState } from "react";
import { useNavigate } from "react-router-dom";
import LocationInput from "./LocationInput";
import DatePicker from "./DatePicker";
import GuestSelector from "./GuestSelector";
import axios from "axios";
import "./SearchBar.css";

function SearchBar() {
  const [isDateOpen, setIsDateOpen] = useState(false);
  const [isGuestOpen, setIsGuestOpen] = useState(false);
  const [dates, setDates] = useState({ checkIn: "", checkOut: "" }); // 날짜 상태
  const [guests, setGuests] = useState(2); // 기본 인원 수
  const [location, setLocation] = useState(""); // 검색 위치
  const navigate = useNavigate();

  // 세션에 검색 데이터 저장 함수
  const saveSearchDataToSession = async (searchData) => {
    try {
      await axios.post("http://localhost:9090/api/session/save", searchData, {
        withCredentials: true, // 세션 쿠키 포함
      });
      console.log("검색 데이터가 세션에 저장되었습니다.");
    } catch (error) {
      console.error("세션에 검색 데이터를 저장하는 데 실패했습니다:", error);
    }
  };

  const handleSearch = async () => {
    // 예약 가능한 날짜 범위 설정 (12/24~12/31)
    const availableStart = new Date("2024-12-24");
    const availableEnd = new Date("2024-12-31");

    const checkInDate = new Date(dates.checkIn);
    const checkOutDate = new Date(dates.checkOut);

    // 날짜 범위 및 체크인/체크아웃 검증
    if (checkInDate >= checkOutDate) {
      alert("날짜 선택은 최소 1박 이상이어야 합니다.");
      return;
    }
    if (checkInDate < availableStart || checkOutDate > availableEnd) {
      alert("해당 조건에 만족하는 숙소가 없습니다.");
      return;
    }

    // 검색 데이터 생성
    const searchData = {
      accomName: location,
      accomAddress: location,
      checkIn: dates.checkIn,
      checkOut: dates.checkOut,
      guests: guests,
    };

    try {
      // 세션에 데이터 저장 요청
      await saveSearchDataToSession(searchData);

      // 인원수를 세션 스토리지에 저장
      sessionStorage.setItem("guests", guests);

      console.log("검색 요청 데이터:", searchData);

      // 스프링 부트 POST 요청
      const response = await axios.post(
        "http://localhost:9090/api/accommodations/search",
        searchData
      );

      if (response.data.length === 0) {
        alert("해당 조건에 만족하는 숙소가 없습니다.");
        return;
      }

      console.log("검색 응답 데이터:", response.data);

      // 검색 결과를 AccommodationList 페이지로 전달
      navigate("/accommodations/search", {
        state: searchData,
      });
    } catch (error) {
      console.error("검색 요청 실패:", error);
      alert("검색 결과를 불러오는 데 실패했습니다.");
    }
  };

  const toggleDate = () => {
    setIsDateOpen(!isDateOpen);
    setIsGuestOpen(false);
  };

  const toggleGuest = () => {
    setIsGuestOpen(!isGuestOpen);
    setIsDateOpen(false);
  };

  const handleDateChange = (value) => {
    const formatDate = (date) =>
      `${date.getFullYear()}-${String(date.getMonth() + 1).padStart(
        2,
        "0"
      )}-${String(date.getDate()).padStart(2, "0")}`;

    const start = formatDate(value.startDate);
    const end = formatDate(value.endDate);

    setDates({ checkIn: start, checkOut: end });
    sessionStorage.setItem("checkIn", start);
    sessionStorage.setItem("checkOut", end);
    setIsDateOpen(false);
  };

  const handleGuestChange = (newValue) => {
    setGuests(newValue);
    sessionStorage.setItem("guests", newValue);
  };

  return (
    <div className="search-bar">
      <LocationInput value={location} onChange={setLocation} />
      <div className="date-picker-wrapper">
        <button className="date-button" onClick={toggleDate}>
          {dates.checkIn && dates.checkOut
            ? `${dates.checkIn} ~ ${dates.checkOut}`
            : "날짜 선택"}
        </button>
        {isDateOpen && (
          <DatePicker
            value={{
              startDate: dates.checkIn ? new Date(dates.checkIn) : new Date(),
              endDate: dates.checkOut ? new Date(dates.checkOut) : new Date(),
            }}
            onChange={handleDateChange}
          />
        )}
      </div>
      <div className="guest-selector-wrapper">
        <button className="guest-button" onClick={toggleGuest}>
          인원 {guests}
        </button>
        {isGuestOpen && (
          <GuestSelector value={guests} onChange={handleGuestChange} />
        )}
      </div>
      <button className="search-button" onClick={handleSearch}>
        검색
      </button>
    </div>
  );
}

export default SearchBar;
