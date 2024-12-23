package com.codingbox.review_projectex.user.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.codingbox.review_projectex.entity.User;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class UserRepository {
	
	private final EntityManager em;	
	
	// 회원 저장
	public User save(User user) {
		em.persist(user);
		return user;
	}
	
	// 단건 조회
	public User findOne(Integer userId) {
		return em.find(User.class, userId);
	}
	
	// 로그인 ID로 회원 조회
    public User findByLoginId(String loginId) {
    	List<User> users = em.createQuery("SELECT u FROM User u WHERE u.email = :loginId", User.class)
                .setParameter("loginId", loginId)
                .getResultList();
    	if (users.isEmpty()) {
    		return null;  // 사용자 없음
    	} else if (users.size() > 1) {
    		throw new IllegalStateException("중복된 사용자 정보가 발견되었습니다.");
    	}
    	return users.get(0);  // 유일한 사용자 반환
    }
    
    // 회원정보 삭제
	public void delete(User userId) {
		em.remove(userId);
	}    
    
    // 회원 전체 조회 (관리자 권한임)
//	public List<User> findAll(String longId) {
//		return em.createQuery("SELECT u FROM User u", User.class);
//
//	}	
	
    

}
