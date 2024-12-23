import React from "react";
import { NavLink, Outlet } from "react-router-dom";
import "./MyPage.css";

export default function MyPage() {
  return (
    <div className="mypage-wrap">
      {/* <h2>마이페이지</h2> */}
      <main className="profile-layout">
        {/* 좌측 매뉴 */}
        <nav className="profile-layout_navigation">
          <ul>
            <li>
              {/* 내 정보 관리 */}
              <NavLink 
                to="profile-info"
                className={({ isActive }) => (isActive ? "menu-item active" : "menu-item")}>
                내 정보 관리
              </NavLink>
            </li>
            <li>
              {/* 예약내역 */}
              <NavLink 
                to="reservation-history" 
                className={({ isActive }) => (isActive ? "menu-item active" : "menu-item")}>
                예약 내역
              </NavLink>
            </li>
            <li>
              {/* 찜하기 */}
              <NavLink 
                to="wishlist" 
                className={({ isActive }) => (isActive ? "menu-item active" : "menu-item")}>
                위시리스트
              </NavLink>
            </li>
          </ul>
        </nav>
        {/* 컨텐츠 영역 */}
        <section className="profile-layout_content">
          <Outlet />
        </section>
      </main>
    </div>
  );
}
