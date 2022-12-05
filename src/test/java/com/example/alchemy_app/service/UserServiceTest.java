package com.example.alchemy_app.service;

import com.example.alchemy_app.dto.UserRegDto;
import com.example.alchemy_app.extension.GMailCustomExtension;
import com.example.alchemy_app.generator.UserDtoGeneratorUtil;
import com.icegreen.greenmail.junit5.GreenMailExtension;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.ValidationException;


@SpringBootTest
@ActiveProfiles("postgres")
@Transactional
class UserServiceTest {

    @Autowired
    private UserService userService;

    @RegisterExtension
    static GreenMailExtension greenMailExtension = new GMailCustomExtension().withConfiguration();

    @Test
    void testRegistrationShouldSaveNewUser() {
        UserRegDto validUserForReg = UserDtoGeneratorUtil.createValidUserForReg();
        Assertions.assertDoesNotThrow(()->userService.registration(validUserForReg));
    }

    @Test
    void testRegistrationShouldThrowUserWithInvalidMail() {
        UserRegDto userWithInvalidMail = UserDtoGeneratorUtil.createUserWithInvalidMail();
        Assertions.assertThrows(ValidationException.class,
                ()->userService.registration(userWithInvalidMail));
    }

    @Test
    void testRegistrationShouldThrowUserWithInvalidPassword() {
        UserRegDto userWithInvalidPassword = UserDtoGeneratorUtil.createUserWithInvalidPassword();
        Assertions.assertThrows(ValidationException.class,
                ()->userService.registration(userWithInvalidPassword));
    }

}
