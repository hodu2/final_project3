import React, { useState, useEffect } from "react";
import { useLocation } from "react-router-dom";
import AccommodationCard from "../AccommodationList/AccommodationCard";
import SearchBar from "../SearchBar/SearchBar";
import Slider from "rc-slider";
import "rc-slider/assets/index.css";
import "../AccommodationList/AccommodationList.css";
import axios from "axios";

function AccommodationList() {
  const location = useLocation();
  const { accomName, accomAddress, checkIn, checkOut, guests } =
    location.state || {};

  const [accommodations, setAccommodations] = useState([]);
  const [sortOption, setSortOption] = useState("추천순");
  const [isLoading, setIsLoading] = useState(true);
  const [error, setError] = useState(null);
  const [isExpanded, setIsExpanded] = useState(false);

  const [filterType, setFilterType] = useState("전체");
  const [priceRange, setPriceRange] = useState([0, 200000]);

  const handlePriceRangeChange = (values) => {
    setPriceRange(values); // 가격 범위 상태 업데이트
  };

  const handleSearch = async (searchParams) => {
    try {
      setIsLoading(true);
      console.log("검색 조건:", { ...searchParams, filterType, priceRange });

      const type =
        filterType !== "전체"
          ? {
              모텔: "MOTEL",
              호텔: "HOTEL",
              리조트: "RESORT",
              펜션: "PENSION",
              게스트하우스: "GUESTHOUSE",
            }[filterType]
          : null;

      const response = await axios.post(
        "http://localhost:9090/api/accommodations/search",
        {
          ...searchParams,
          type,
          minPrice: priceRange[0],
          maxPrice: priceRange[1],
        }
      );

      const data = response.data
        .filter(item => item.accomName.includes("부산") || item.address.includes("부산"))
        .map((item) => ({
          id: item.accomId,
          name: item.accomName,
          type: item.type,
          address: item.address,
          price: item.cheapestPrice,
          imageUrl: item.representativeImage
            ? item.representativeImage.split(",")[0]
            : null,
          rating: item.averageRating || "평점 없음",
        }));

      setAccommodations(data);
      setIsExpanded(false);
      setIsLoading(false);
    } catch (err) {
      console.error("API 요청 실패:", err);
      setError("숙소 정보를 불러오는 데 실패했습니다.");
      setIsLoading(false);
    }
  };

  const handleSortChange = (option) => {
    setSortOption(option);
    const sortedData = [...accommodations];
    switch (option) {
      case "평점높은순":
        sortedData.sort((a, b) => b.rating - a.rating);
        break;
      case "낮은가격순":
        sortedData.sort((a, b) => a.price - b.price);
        break;
      case "높은가격순":
        sortedData.sort((a, b) => b.price - a.price);
        break;
      default:
        break;
    }
    setAccommodations(sortedData);
  };

  const handleTypeFilterChange = (type) => {
    setFilterType(type);
  };

  useEffect(() => {
    handleSearch({ accomName, accomAddress, checkIn, checkOut, guests });
  }, [accomName, accomAddress, checkIn, checkOut, guests, filterType, priceRange]);

  if (isLoading) return <p>로딩 중...</p>;
  if (error) return <p>{error}</p>;

  return (
    <div className="accommodation-list-container">
      <div className="filter-container">
        <h3>숙소 유형</h3>
        <div className="filter-options">
          {["전체", "모텔", "호텔", "리조트", "펜션", "게스트하우스"].map((type) => (
            <label key={type}>
              <input
                type="radio"
                name="filterType"
                value={type}
                checked={filterType === type}
                onChange={() => handleTypeFilterChange(type)}
              />
              {type}<br/>
            </label>
          ))}
        </div>
        <h3>가격 (1박 기준)</h3>
        <Slider
          range
          min={0}
          max={200000}
          step={30000}
          value={priceRange}
          onChange={handlePriceRangeChange}
        />
        <p>
          {priceRange[0].toLocaleString()}원 ~ {priceRange[1].toLocaleString()}원
        </p>
      </div>

      <div className="main-content">
        {isExpanded && (
          <div className="expanded-search-bar">
            <SearchBar
              onSearch={handleSearch}
              onClose={() => setIsExpanded(false)}
            />
          </div>
        )}
        <br />
        <div className="sort-filter">
          <label htmlFor="sort">정렬 : </label>
          <select
            id="sort"
            value={sortOption}
            onChange={(e) => handleSortChange(e.target.value)}
          >
            <option value="추천순">추천순</option>
            <option value="평점높은순">평점 높은 순</option>
            <option value="낮은가격순">낮은 가격 순</option>
            <option value="높은가격순">높은 가격 순</option>
          </select>
        </div>
        <br />
        <br />
        <div className="accommodation-grid">
          {accommodations.map((item) => (
            <AccommodationCard key={item.id} data={item} />
          ))}
        </div>
      </div>
    </div>
  );
}

export default AccommodationList;