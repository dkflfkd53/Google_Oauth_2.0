package com.example.google_oauth_2.user.service;

import com.example.google_oauth_2.user.domain.User;
import com.example.google_oauth_2.user.domain.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    @Transactional
    public void createUser(String email, String password, String picture, String nickname) {
        userRepository.save(new User(null, email, password, picture, nickname));
    }
}
