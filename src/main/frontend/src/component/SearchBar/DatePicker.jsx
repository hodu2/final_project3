import { useState } from "react";
import { DateRange } from "react-date-range";
import "react-date-range/dist/styles.css";
import "react-date-range/dist/theme/default.css";
import { ko } from "date-fns/locale"; // 한국 시간대 로케일

function DatePicker({ value, onChange }) {
  const [selection, setSelection] = useState({
    startDate: value.startDate || new Date(),
    endDate: value.endDate || new Date(),
    key: "selection",
  });

  const handleSelect = (ranges) => {
    const startDate = ranges.selection.startDate;
    const endDate = ranges.selection.endDate;

    // 체크아웃 날짜가 설정되었을 때만 부모 컴포넌트에 값을 전달
    if (startDate && endDate && startDate !== endDate) {
      onChange({
        startDate,
        endDate,
      });
    }

    // 선택한 값을 로컬 상태에 업데이트
    setSelection({
      startDate,
      endDate,
      key: "selection",
    });
  };

  return (
    <div className="date-picker">
      <DateRange
        ranges={[selection]}
        onChange={handleSelect}
        locale={ko} // 한국 시간대 설정
        editableDateInputs={true} /* 날짜 직접 입력 허용 */
        moveRangeOnFirstSelection={false} /* 첫 클릭 시 달력이 닫히지 않도록 설정 */
        retainEndDateOnFirstSelection={true} /* 체크인 선택 후 체크아웃 유지 */
        minDate={new Date()} /* 오늘 날짜 이후만 선택 가능 */
      />
    </div>
  );
}

export default DatePicker;
