import { Link, useSearchParams, useNavigate } from "react-router-dom";
import { useEffect } from "react";
import styles from "./Payment.module.css";

export default function FailPage({ user }) {
  const [searchParams] = useSearchParams();
  const navigate = useNavigate();

  useEffect(() => {
    if (!user) {
      navigate("/login");
    }
  }, [user, navigate]);

  return (
    <div className={styles.failWrap}>
    <div className={styles.box_section} id={styles.box_after}>
      <img
        width="100px"
        src="https://static.toss.im/lotties/error-spot-no-loop-space-apng.png"
        alt="에러 이미지"
      />
      <h2 className={styles.title_after}>
        결제를 실패했어요.
        <br />
        <br />
        결제를 다시 시도해주세요.
      </h2>

      {/* <div className="p-grid typography--p" style={{ marginTop: "50px" }}>
        <div className="p-grid-col text--left">
          <b>에러메시지</b>
        </div>
        <div className="p-grid-col text--right" id="message">{`${searchParams.get("message")}`}</div>
      </div>
      <div className="p-grid typography--p" style={{ marginTop: "10px" }}>
        <div className="p-grid-col text--left">
          <b>에러코드</b>
        </div>
        <div className="p-grid-col text--right" id="code">{`${searchParams.get("code")}`}</div>
      </div> */}

      <div className={styles.button_container}>
        <Link to="/">
          <button id="homeButton" className={styles.button}>
            홈으로
          </button>
        </Link>
      </div>
    </div>
    </div>
  );
}
