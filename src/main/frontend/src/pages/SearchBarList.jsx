import React, { useState } from "react";
import MiniSearchBar from "../SearchBar/MiniSearchBar";
import SearchBar from "../SearchBar/SearchBar";
import "./SearchBarList.css";

function SearchBarList({ location, dates, guests }) {
  const [isExpanded, setIsExpanded] = useState(false);

  const handleToggleSearchBar = () => {
    setIsExpanded(!isExpanded);
  };

  return (
    <div className="search-bar-list">
      {/* 상단 미니 검색바 */}
      <MiniSearchBar
        location={location}
        dates={dates}
        guests={guests}
        onToggle={handleToggleSearchBar}
      />

      {/* 확장된 검색바 */}
      {isExpanded && (
        <div className="expanded-search-bar">
          <SearchBar
            initialLocation={location}
            initialDates={dates}
            initialGuests={guests}
          />
        </div>
      )}

      {/* 여기에 기존 숙소 리스트 화면 컴포넌트 추가 */}
      <div className="accommodation-list">
        {/* 숙소 리스트 내용 */}
      </div>
    </div>
  );
}

export default SearchBarList;
