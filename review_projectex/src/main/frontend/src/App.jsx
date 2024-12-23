import React, { useState, useEffect, useRef } from "react";
import {
  createBrowserRouter,
  RouterProvider,
  Navigate,
} from "react-router-dom";
import axios from "axios";

// 페이지 컴포넌트
import HomePage from "./pages/HomePage.jsx";
import LoginPage from "./pages/LoginPage.jsx";
import LoginForm from "./pages/LoginForm.jsx";
import RegitForm from "./pages/RegitForm.jsx";
import MainLayout from "./pages/MainLayout.jsx";
import LogoutCallback from "./LogoutCallback.jsx";
import RegitPhone from "./pages/RegitPhone.jsx"
import MyPage from "./pages/MyPage.jsx";
import ProfileInfo from "./pages/mypage/ProfileInfo.jsx";
import ReservationHistory from "./pages/mypage/ReservationHistory.jsx";
import Wishlist from "./pages/mypage/Wishlist.jsx";
import AccommodationDetailPage from "./component/AccommodationDetailPage.jsx";
import PaymentCheckoutPage from "./pages/payment/PaymentCheckout.jsx";
import PaymentSuccessPage from "./pages/payment/PaymentSuccess.jsx";
import PaymentFailPage from "./pages/payment/PaymentFail.jsx";
import AccommodationList from "./component/AccommodationList/AccommodationList.jsx";
import MainPage from "./component/main/Main.jsx";
import Jeju from './component/PopularDestinations/jeju.jsx';
import Busan from './component/PopularDestinations/busan.jsx';

function App() {
  const [user, setUser] = useState(null); // 사용자 정보
  const [token, setToken] = useState(null); // 카카오 토큰
  const [loading, setLoading] = useState(true); // 로딩 상태
  const userRef = useRef(null); // 즉시 업데이트용 Ref
  const tokenRef = useRef(null); // 즉시 업데이트용 Ref

  // 세션 상태 확인
  const checkLoginStatus = async () => {
    try {
      const response = await axios.get("http://localhost:9090/api/session", {
        withCredentials: true,
      });

      if (response.status === 200) {
        const { user: fetchedUser, accessToken } = response.data;
        const userWithId = { ...fetchedUser, id: fetchedUser.userId };

        userRef.current = fetchedUser;
        tokenRef.current = accessToken || null;
        setUser(userWithId);
        // setUser(fetchedUser);
        setToken(accessToken || null);
      } else {
        userRef.current = null;
        tokenRef.current = null;
        setUser(null);
        setToken(null);
      }
    } catch (error) {
      console.error("세션 확인 실패:", error.message);
      userRef.current = null;
      tokenRef.current = null;
      setUser(null);
      setToken(null);
    } finally {
      setLoading(false); // 로딩 완료
    }
  };

  useEffect(() => {
    checkLoginStatus();
  }, []);

  // 로딩 중 화면 표시
  if (loading) {
    return <div>로딩 중...</div>;
  }

  const router = createBrowserRouter([
    {
      path: "/",
      element: (
        <MainLayout
          user={user}
          setUser={setUser}
          token={token}
          setToken={setToken}
        />
      ),
      children: [
        { path: "/", element: <MainPage /> }, // 기본 리디렉션
        // { path: "/home", element: <HomePage user={user} setUser={setUser} /> }, 
        // HomePage 는 메인페이지로 리디렉션하기 위한 테스트용도 삭제 해도 됨
        { path: "/logout-callback", element: <LogoutCallback /> },
        { path: "/login", element: <LoginPage /> },
        {
          path: "/login/email",
          element: <LoginForm checkLoginStatus={checkLoginStatus} />,
        },
        { path: "/login/email/regit", element: <RegitForm /> },
        { path: "/login/regitPhone", element: <RegitPhone user={user} /> },
        {
          path: "/mypage",
          element: <MyPage />,
          children: [
            {
              index: true, // 기본 경로 설정
              element: <Navigate to="profile-info" replace />,
            },
            {
              path: "profile-info",
              element: (
                <ProfileInfo
                  user={user}
                  setUser={setUser}
                  token={token}
                  setToken={setToken}
                />
              ),
            },
            {
              path: "reservation-history",
              element: <ReservationHistory user={user} />,
            },
            { path: "wishlist", element: <Wishlist user={user} /> },
          ],
        },
        {
          path: "/accommodations/search",
          element: <AccommodationList />
        },
        {
          path: "/accommodation/:accomId",
          element: <AccommodationDetailPage userId={user?.id} />,
        },
        {
          path: "/destination/jeju", element: <Jeju />,
        },
        {
          path: "/destination/busan", element: <Busan />,
        },
        {
          path: "/payment/checkout",
          element: <PaymentCheckoutPage user={user} />,
        },
        {
          path: "/payment/success",
          element: <PaymentSuccessPage user={user} />,
        },
        { path: "/payment/fail", element: <PaymentFailPage user={user} /> },
      ],
    },
  ]);

  return <RouterProvider router={router} />;
}

export default App;
