package com.example.google_oauth_2.user.controller;

import com.example.google_oauth_2.user.dto.UserRequest;
import com.example.google_oauth_2.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping("/user")
    public void createUser(@RequestBody UserRequest request) {
        userService.createUser(request.email(), request.password(), request.picture(), request.nickname());
    }
}
