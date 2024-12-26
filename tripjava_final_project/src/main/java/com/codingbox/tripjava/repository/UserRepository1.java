package com.codingbox.tripjava.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.codingbox.tripjava.entity.User;

public interface UserRepository1 extends JpaRepository<User, Integer> {
    // 추가 쿼리 메서드를 정의할 수 있음 (예: 사용자 이메일로 조회)
	User findByUsername(String username);
}
