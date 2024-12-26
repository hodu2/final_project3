import React from "react";

function HomePage({ user }) {
  
  return (
    <>
      <main>
        {user && <h2>환영합니다! {user.username} 님</h2>}
        {!user && <h2>메인페이지</h2>}
      </main>
    </>
  );
}

export default HomePage;
