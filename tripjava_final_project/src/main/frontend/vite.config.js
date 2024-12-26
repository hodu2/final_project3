import { defineConfig } from 'vite'
import react from '@vitejs/plugin-react'

// https://vite.dev/config/
export default defineConfig({
  plugins: [react()],
  server: {
    proxy: {
      "/api": {
        target: "http://localhost:9090", // 백엔드 서버 주소
        changeOrigin: true, // 쿠키를 허용
      },
      '/api/kakao': {
        target: 'https://kauth.kakao.com', // 카카오 API 서버로 프록시
        changeOrigin: true,
        rewrite: (path) => path.replace(/^\/api\/kakao/, ''), // '/api/kakao' 제거
      },
    },
  },
});

