import './LocationInput.css';


function LocationInput({ value, onChange }) {
  return (
    <div className="location-input">
      <span className="search-icon">🔍</span> {/* 검색 아이콘 */}
      <input
        type="text"
        placeholder="여행지나 숙소를 검색해보세요."
        value={value}
        onChange={(e) => onChange(e.target.value)}
      />
    </div>
  );
}

export default LocationInput;
