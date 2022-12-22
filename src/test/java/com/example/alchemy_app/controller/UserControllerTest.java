package com.example.alchemy_app.controller;


import com.example.alchemy_app.dto.TokenRequest;
import com.example.alchemy_app.dto.UserRegDto;
import com.example.alchemy_app.entity.RefreshToken;
import com.example.alchemy_app.entity.User;
import com.example.alchemy_app.extension.GMailCustomExtension;
import com.example.alchemy_app.generator.UserDtoGeneratorUtil;
import com.example.alchemy_app.repository.RefreshTokenRepository;
import com.example.alchemy_app.repository.UserRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.icegreen.greenmail.junit5.GreenMailExtension;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Transactional
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @RegisterExtension
    static GreenMailExtension greenMailExtension = new GMailCustomExtension().withConfiguration();

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RefreshTokenRepository refreshTokenRepository;

    private final String userDetailsName = "admin_1";

    @Test
    void testRegistrationShouldRegisterUser() throws Exception {
        UserRegDto validUserForReg = UserDtoGeneratorUtil.createValidUserForReg();
        mockMvc.perform(post("/api/v1/user/registration").
                         contentType(MediaType.APPLICATION_JSON).
                         content(objectToJson(validUserForReg))).
                 andExpect(status().isCreated());
    }

    @Test
    void testRegistrationShouldThrowCauseOfInvalidMail() throws Exception {
        UserRegDto userWithInvalidMail = UserDtoGeneratorUtil.createUserWithInvalidMail();
        mockMvc.perform(post("/api/v1/user/registration").
                        contentType(MediaType.APPLICATION_JSON).
                        content(objectToJson(userWithInvalidMail))).
                andExpect(status().isBadRequest());
    }

    @Test
    void testRegistrationShouldThrowCauseOfInvalidPassword() throws Exception {
        UserRegDto userWithInvalidPassword = UserDtoGeneratorUtil.createUserWithInvalidPassword();
        mockMvc.perform(post("/api/v1/user/registration").
                        contentType(MediaType.APPLICATION_JSON).
                        content(objectToJson(userWithInvalidPassword))).
                andExpect(status().isBadRequest());
    }

    @Test
    @WithUserDetails(userDetailsName)
    void testRegistrationShouldThrowCauseOfUserAlreadyLogged() throws Exception {
        mockMvc.perform(post("/api/v1/user/registration").
                        contentType(MediaType.APPLICATION_JSON).
                        content(objectToJson(UserDtoGeneratorUtil.createValidLogUser()))).
                andExpect(status().isForbidden());
    }

    @Test
    void testUserLoginShouldLoginUser() throws Exception {
        mockMvc.perform(post("/api/v1/user/login").
                        contentType(MediaType.APPLICATION_JSON).
                        content(objectToJson(UserDtoGeneratorUtil.createValidLogUser()))).
                andExpect(status().isOk());
    }

    @Test
    void testUserLoginShouldThrowCauseOfUserWithSuchLoginNotExist() throws Exception {
        mockMvc.perform(post("/api/v1/user/login").
                        contentType(MediaType.APPLICATION_JSON).
                        content(objectToJson(UserDtoGeneratorUtil.createNotExistingLogUser()))).
                andExpect(status().isBadRequest());
    }

    @Test
    void testUserLoginShouldThrowCauseOfUserWithInvalidPassword() throws Exception {
        mockMvc.perform(post("/api/v1/user/login").
                        contentType(MediaType.APPLICATION_JSON).
                        content(objectToJson(UserDtoGeneratorUtil.createExistingUserWithInvalidPassword()))).
                andExpect(status().isBadRequest());
    }

    @Test
    @WithUserDetails(userDetailsName)
    void testUserLoginShouldThrowCauseUserAlreadyLogged() throws Exception {
        mockMvc.perform(post("/api/v1/user/login").
                        contentType(MediaType.APPLICATION_JSON).
                        content(objectToJson(UserDtoGeneratorUtil.createValidLogUser()))).
                andExpect(status().isForbidden());
    }

    @Test
    @WithUserDetails(userDetailsName)
    void testGetUsersByMaxGoldShouldReturnUsers() throws Exception {
        mockMvc.perform(get("/api/v1/user/high_score/gold").
                        content(objectToJson(Pageable.unpaged()))).
                andExpect(status().isOk());
    }

    @Test
    void testGetUserByMaxGoldShouldThrowCauseOfAnonymousUser() throws Exception {
        mockMvc.perform(get("/api/v1/user/high_score/gold").
                        content(objectToJson(Pageable.unpaged()))).
                andExpect(status().isUnauthorized());
    }

    @Test
    @WithUserDetails(userDetailsName)
    void testGetUserIngredientShouldReturnUserIngredient() throws Exception {
        mockMvc.perform(get("/api/v1/user/user_ingredient").
                        content(objectToJson(Pageable.unpaged()))).
                andExpect(status().isOk());
    }

    @Test
    void testGetUserIngredientShouldThrowCauseOfAnonymousUser() throws Exception {
        mockMvc.perform(get("/api/v1/user/user_ingredient").
                        content(objectToJson(Pageable.unpaged()))).
                andExpect(status().isUnauthorized());
    }

    @Test
    void testGetNewAccessTokenByRefreshTokenShouldReturnNewAccessToken() throws Exception {
        User user = userRepository.findById(500L).get();
        RefreshToken refreshToken = RefreshToken.builder().
                withToken(UUID.randomUUID().toString()).
                withUser(user).
                withExpiryDate(LocalDateTime.now().plus(1000000, ChronoUnit.MINUTES)).
                build();
        TokenRequest tokenRequest = TokenRequest.builder().withRefreshToken(refreshToken.getToken()).build();
        refreshTokenRepository.save(refreshToken);
        mockMvc.perform(post("/api/v1/user/refresh_token").
                        contentType(MediaType.APPLICATION_JSON).
                        content(objectToJson(tokenRequest))).
                andExpect(status().isCreated());
    }

    @Test
    void testGetNewAccessTokenByRefreshTokenShouldThrowCauseTokenIsExpired() throws Exception {
        User user = userRepository.findById(500L).get();
        RefreshToken refreshToken = RefreshToken.builder().
                withToken(UUID.randomUUID().toString()).
                withUser(user).
                withExpiryDate(LocalDateTime.now()).
                build();
        TokenRequest tokenRequest = TokenRequest.builder().withRefreshToken(refreshToken.getToken()).build();
        refreshTokenRepository.save(refreshToken);
        mockMvc.perform(post("/api/v1/user/refresh_token").
                        contentType(MediaType.APPLICATION_JSON).
                        content(objectToJson(tokenRequest))).
                andExpect(status().isBadRequest());
    }

    @Test
    void testGetNewAccessTokenByRefreshTokenShouldThrowCauseTokenIsInvalidFormat() throws Exception {
        TokenRequest tokenRequest = TokenRequest.builder().withRefreshToken("123sdaer123asadf").build();
        mockMvc.perform(post("/api/v1/user/refresh_token").
                        contentType(MediaType.APPLICATION_JSON).
                        content(objectToJson(tokenRequest))).
                andExpect(status().isBadRequest());
    }

    private String objectToJson(Object object) throws JsonProcessingException {
        return objectMapper.writeValueAsString(object);
    }

}
