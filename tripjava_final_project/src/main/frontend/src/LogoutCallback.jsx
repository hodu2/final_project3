import { useEffect } from "react";
import { useNavigate } from "react-router-dom";

export default function LogoutCallback() {
  const navigate = useNavigate();

  useEffect(() => {
    // 로그아웃 후 HomePage로 리디렉션
    navigate("/");
  }, [navigate]);

  return null; // 화면에 아무것도 렌더링하지 않음
}
