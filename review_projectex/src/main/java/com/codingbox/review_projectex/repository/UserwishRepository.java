package com.codingbox.review_projectex.repository;

import org.springframework.stereotype.Repository;

import com.codingbox.review_projectex.entity.User;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class UserwishRepository {

    private final EntityManager em;

    // 사용자 조회
    public Optional<User> findById(int userId) {
        User user = em.find(User.class, userId);
        return Optional.ofNullable(user);
    }
}
