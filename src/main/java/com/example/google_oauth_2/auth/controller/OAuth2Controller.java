package com.example.google_oauth_2.auth.controller;

import com.example.google_oauth_2.auth.service.OAuth2Service;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class OAuth2Controller {
    private final OAuth2Service oAuth2Service;
    @GetMapping("/dev/login/oauth/{registration}")
    public String devSocialLogin(@RequestParam String code, @PathVariable String registration) {
        return oAuth2Service.devSocialLogin(code, registration);
    }
}
