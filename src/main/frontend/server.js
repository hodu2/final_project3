const express = require("express");
const cors = require("cors");
const app = express();
const port = 5173;

// CORS 설정
app.use(cors({ origin: "http://localhost:5173" })); // Vite 클라이언트 도메인 허용
app.use(express.static("public"));
app.use(express.json());

const widgetSecretKey = "test_gsk_docs_OaPz8L5KdmQXkzRz3y47BMw6";

const encryptedWidgetSecretKey = "Basic " + Buffer.from(widgetSecretKey + ":").toString("base64");

// 결제 승인
app.post("/confirm/payment", function (req, res) {
  const { paymentKey, orderId, amount } = req.body;

  // 결제 승인 API를 호출
  fetch("https://api.tosspayments.com/v1/payments/confirm", {
    method: "POST",
    headers: {
      Authorization: encryptedWidgetSecretKey,
      "Content-Type": "application/json",
    },
    body: JSON.stringify({
      orderId: orderId,
      amount: amount,
      paymentKey: paymentKey,
    }),
  }).then(async function (response) {
    const result = await response.json();
    console.log(result);

    if (!response.ok) {
      res.status(response.status).json(result);

      return;
    }

    res.status(response.status).json(result);
  });
});

app.listen(port, () => console.log(`http://localhost:${port} 으로 앱이 실행되었습니다.`));
