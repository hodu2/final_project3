import React, { useEffect, useRef, useState } from "react";

function Location({ latitude, longitude }) {
  const mapContainer = useRef(null); // 지도를 담을 div를 참조
  const [mapLoaded, setMapLoaded] = useState(false); // 지도 API 로드 상태 관리

  // 네이버 지도 API가 로드되었는지 확인하는 useEffect
  useEffect(() => {
    const checkNaverMap = () => {
      if (window.naver) {
        console.log("네이버 지도 API가 로드되었습니다.");
        setMapLoaded(true); // 네이버 지도 API가 로드되면 mapLoaded를 true로 설정
      } else {
        console.log("네이버 지도 API 로드 대기 중...");
        setTimeout(checkNaverMap, 100); // 100ms마다 체크
      }
    };
    checkNaverMap();
  }, []); // 컴포넌트가 처음 렌더링될 때 한번만 실행

  // 지도와 마커를 생성하는 useEffect
  useEffect(() => {
    if (mapLoaded && latitude && longitude) {
      console.log("지도와 마커를 생성합니다.");
      const mapOptions = {
        center: new window.naver.maps.LatLng(longitude, latitude), // 위도, 경도 설정
        zoom: 12, // 기본 줌 레벨
      };

      const map = new window.naver.maps.Map(mapContainer.current, mapOptions); // 지도 생성

      new window.naver.maps.Marker({
        position: new window.naver.maps.LatLng(longitude, latitude), // 마커 위치 설정
        map: map, // 지도 위에 마커 표시
      });

      console.log("지도와 마커 생성 완료!");

      // 지도 리사이즈 처리 (윈도우 크기 변경 시)
      window.addEventListener("resize", () => {
        naver.maps.Event.trigger(map, "resize"); // 지도 리사이즈 이벤트 트리거
      });
    }
  }, [mapLoaded, latitude, longitude]); // mapLoaded, latitude, longitude가 변경될 때마다 실행

  return (
    <div className="location-section">
      <h2 id="location">위치</h2>
      <div
        ref={mapContainer}
        style={{ width: "100%", height: "500px", marginTop: "20px" }}
      ></div>
      <br />
    </div>
  );
}

export default Location;
