package com.codingbox.tripjava.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.codingbox.tripjava.entity.User;
import com.codingbox.tripjava.repository.UserRepository1;

@Service
public class UserService1 {
	 private final UserRepository1 userRepository;  // UserRepository는 DB에서 User 정보를 가져오는 역할을 합니다.

	    @Autowired
	    public UserService1(UserRepository1 userRepository) {
	        this.userRepository = userRepository;
	    }

	    public User getUserByUsername(String username) {
	        return userRepository.findByUsername(username);  // DB에서 username으로 User 객체 조회
	    }
}
