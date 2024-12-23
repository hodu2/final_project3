package com.codingbox.tripjava.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.codingbox.tripjava.entity.User;
import com.codingbox.tripjava.session.SessionConst;

import java.util.Map;

@RestController
@RequestMapping("/api/session")
public class SessionController {

    @PostMapping("/save")
    public ResponseEntity<String> saveSearchDataToSession(
            @RequestBody Map<String, Object> searchData,
            HttpSession session) {
        session.setAttribute("searchData", searchData);
        System.out.println("세션에 저장된 데이터: " + searchData); // 저장된 데이터 출력
        return ResponseEntity.ok("검색 데이터가 세션에 저장되었습니다.");
    }

    @GetMapping("/retrieve")
    public ResponseEntity<Map<String, Object>> getSearchDataFromSession(HttpSession session) {
        @SuppressWarnings("unchecked")
        Map<String, Object> searchData = (Map<String, Object>) session.getAttribute("searchData");
        if (searchData == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(searchData);
    }
    @GetMapping("/user")
    public ResponseEntity<User> getLoggedInUser(HttpSession session) {
        User user = (User) session.getAttribute(SessionConst.LOGIN_MEMBER);
        if (user == null) {
            return ResponseEntity.status(401).body(null);  // 로그인되지 않으면 401 반환
        }
        return ResponseEntity.ok(user);  // 로그인된 사용자 정보 반환
    }
}
