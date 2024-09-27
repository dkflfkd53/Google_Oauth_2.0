package com.example.google_oauth_2.auth.service;

import com.example.google_oauth_2.auth.api.GoogleOAuthApi;
import com.example.google_oauth_2.auth.api.GoogleResourceApi;
import com.example.google_oauth_2.auth.dto.GoogleResourceDto;
import com.example.google_oauth_2.auth.dto.GoogleTokenDto;
import com.example.google_oauth_2.user.domain.User;
import com.example.google_oauth_2.user.domain.UserRepository;
import com.example.google_oauth_2.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class OAuth2Service {
    private final Environment env;
    private final UserRepository userRepository;
    private final UserService userService;
    private final GoogleOAuthApi googleOAuthApi;
    private final GoogleResourceApi googleResourceApi;

    public String devSocialLogin(String code, String registration) {
        String accessToken = getAccessToken(code, registration);
        GoogleResourceDto resource = getUserResource(accessToken, registration);

        if (checkSignUp(resource.email())){
            return "로그인에 성공했습니다.";
        } else {
            socialSignUp(resource.email(), resource.id(), resource.picture(), resource.nickname());
            return "회원가입 및 로그인에 성공했습니다.";
        }
    }

    public void socialSignUp(String email, String password, String picture, String nickname) {
        /* 프로필 및 맴버 저장 */
        try {
            userService.createUser(email, password, picture, nickname);

        } catch (Exception e) {
            /* 중복된 이메일 값이 삽입되려고 할 때 발생하는 예외 처리,unique = true 때문에 발생하는 에러 */
        }
    }

    public Boolean checkSignUp(String email){
        User user = userRepository.findByEmail(email);
        return user != null;
    }

    /* oauth 서버에서 access_token 받아온다 */
    private String getAccessToken(String authorizationCode, String registration) {
        String clientId = env.getProperty("oauth2." + registration + ".client-id");
        String clientSecret = env.getProperty("oauth2." + registration + ".client-secret");
        String redirectUri = env.getProperty("oauth2." + registration + ".redirect-uri");

        GoogleTokenDto tokenDto = googleOAuthApi.googleGetToken(authorizationCode, clientId, clientSecret, redirectUri, "authorization_code");

        return tokenDto.accessToken();
    }

    /* 리다이렉트 URL을 통해서 리소스 가져온다 */
    private GoogleResourceDto getUserResource(String accessToken, String registration) {

        return googleResourceApi.googleGetResource("Bearer " + accessToken);
    }
}
