import "./Footer.css";
import React from "react";


function Footer() {
  return (
    <footer className="footer-wrap">
    <div className="footer-content">
      {/* About Section */}
      <div className="footer-section-about">
        <h2>About Us</h2>
        <p>
          저희 트립 자바는 전국 다양한 숙박 옵션을 제공합니다.<br/>
          최고의 서비스와 잊지 못할 여행 경험을 위해 항상 최선을 다하겠습니다.
        </p>
      </div>

      {/* Contact Section */}
      <div className="footer-section-contact">
        <h2>Contact Us</h2>
        <p>Email: tripjava@support.com</p>
        <p>Phone: +82 1544-9970</p>
        <p>Address: 서울특별시 강남구 테헤란로14길 6</p>
      </div>
    </div>

    {/* Footer Bottom */}
    <div className="footer-bottom">
      <p>&copy; 2024 TRIPJAVA. All Rights Reserved.</p>
    </div>
  </footer>
);
}

export default Footer;
