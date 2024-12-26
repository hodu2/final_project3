import React, { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom"; // React Router 훅 추가
import styles from "./Wishlist.module.css";
import axios from "axios";

const Wishlist = React.memo(({ user }) => {
  const [wishlistItems, setWishlistItems] = useState([]);
  const [hoveredItemId, setHoveredItemId] = useState(null);
  const navigate = useNavigate(); // 페이지 이동 함수

  const userId = user?.id; // user 객체에서 id 추출

  // 위시리스트 불러오기
  useEffect(() => {
    console.log("userId 확인:", userId); // userId 확인 로그
    if (userId) {
      const fetchWishlist = async () => {
        try {
          const response = await axios.get(
            `http://localhost:9090/api/wishlist/${userId}/details`,
            { withCredentials: true }
          );
          // console.log("서버 응답 데이터:", response.data); // 서버 응답 데이터 로그
          setWishlistItems(response.data);
        } catch (error) {
          console.error("위시리스트 로드 실패:", error);
        }
      };
      fetchWishlist();
    } else {
      console.error("User ID가 유효하지 않습니다.");
    }
  }, [userId]);

  // 항목 삭제 함수
  const handleDelete = async (id) => {
    try {
      await axios.delete(`/api/wishlist/${userId}/${id}`, {
        withCredentials: true,
      });
      const updatedItems = wishlistItems.filter((item) => item.accomId !== id);
      setWishlistItems(updatedItems);
    } catch (error) {
      console.error("위시리스트 항목 삭제 실패:", error);
    }
  };

  // 항목 클릭 시 상세 페이지로 이동
  const handleItemClick = (accomId) => {
    navigate(`/accommodation/${accomId}`); // 상세 페이지 경로로 이동
  };

  return (
    <div className={styles.wishWrap}>
      <h1 className={styles.wishTitle}>위시리스트</h1>
      <div className={styles.wishContainer}>
        <div className={styles.wishlistItems}>
          {/* 위시리스트가 비어있을 때 */}
          {wishlistItems.length === 0 ? (
            <p className={styles.emptyMessage}>위시리스트에 항목이 없습니다.</p>
          ) : (
            wishlistItems.map((item, index) => {
              // console.log(`item 확인 (Index ${index}):`, item); // 각 item 로그
              return (
                <div
                  key={item.accomId || `wishlist-item-${index}`} // accomId 없으면 index 사용
                  className={styles.wishlistItem}
                  onClick={() => handleItemClick(item.accomId)} // 클릭 시 상세 페이지 이동
                  onMouseEnter={() => setHoveredItemId(item.accomId)}
                  onMouseLeave={() => setHoveredItemId(null)}
                >
                  <img
                    src={item.imageUrl?.split(",")[0]?.trim() || "/default-image.png"}
                    alt={item.accomName || "No Name"}
                  />
                  <h3>{item.accomName}</h3>
                  <p>
                    {item.accomType} | 평점: {item.averageRating || "N/A"}
                  </p>
                  <p>
                    최저가:{" "}
                    {item.cheapestPrice
                      ? `${item.cheapestPrice.toLocaleString()}원`
                      : "정보 없음"}
                  </p>
                  {hoveredItemId === item.accomId && (
                    <button
                      className={styles.deleteButton}
                      onClick={(e) => {
                        e.stopPropagation(); // 클릭 이벤트 전파 방지
                        handleDelete(item.accomId);
                      }}
                    >
                      <span className={styles.deleteIcon}>X</span>
                    </button>
                  )}
                </div>
              );
            })
          )}
        </div>
      </div>
    </div>
  );
});
export default Wishlist;