package com.codingbox.tripjava.service;

import com.codingbox.tripjava.entity.User;
import com.codingbox.tripjava.repository.UserwishRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class UserwishService {

    private final UserwishRepository userRepository;

    public UserwishService(UserwishRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Optional<User> getUserById(int id) {
        return userRepository.findById(id);
    }
}
