package com.codingbox.review_projectex.user.repository;

import java.util.List;

import com.codingbox.review_projectex.entity.User;

public interface UserRepositoryImpl {
	// 로그인 ID로 사용자 조회
    public User findByLoginId(String loginId);

    // 로그인 ID 존재 여부 확인
    public boolean existsByLoginId(String loginId);

    // 이름에 특정 문자열이 포함된 사용자 조회
    public List<User> findByNameContaining(String name);

    // 로그인 ID와 비밀번호로 사용자 조회
    public User findByLoginIdAndPassword(String loginId, String password);
}
