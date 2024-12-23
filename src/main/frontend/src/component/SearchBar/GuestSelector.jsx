import './GuestSelector.css';

function GuestSelector({ value, onChange }) {
  return (
    <div className="guest-selector">
      {/* 인원 타이틀 */}
      <p className="guest-title">인원</p>
      
      {/* 인원 체크 기능 */}
      <div className="guest-control">
        <button onClick={() => onChange(value > 1 ? value - 1 : 1)}>-</button>
        <span>{value}</span>
        <button 
          onClick={() => value < 10 && onChange(value + 1)} 
          className={value >= 10 ? 'disabled' : ''}
          disabled={value >= 10}
        >
          +
        </button>      </div>
      {/* 안내 문구 */}
      <p className="guest-instruction">※ 유아 및 아동도<br/> 인원수에 포함해주세요.</p>
    </div>
  );
}

export default GuestSelector;
