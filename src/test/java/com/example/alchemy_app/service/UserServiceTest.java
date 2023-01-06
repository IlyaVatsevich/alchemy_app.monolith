package com.example.alchemy_app.service;

import com.example.alchemy_app.dto.UserRegDto;
import com.example.alchemy_app.extension.GMailCustomExtension;
import com.example.alchemy_app.generator.UserDtoGeneratorUtil;
import com.example.alchemy_app.repository.IngredientRepository;
import com.example.alchemy_app.service.impl.UserIngredientServiceImpl;
import com.icegreen.greenmail.junit5.GreenMailExtension;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.util.AopTestUtils;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.ValidationException;
import java.lang.reflect.Field;


@SpringBootTest
@ActiveProfiles("test")
@Transactional
class UserServiceTest {

    @Autowired
    private UserService userService;
    @Autowired
    private IngredientRepository ingredientRepository;
    @Autowired
    private UserIngredientService userIngredientService;

    @RegisterExtension
    static GreenMailExtension greenMailExtension = new GMailCustomExtension().withConfiguration();

    @BeforeEach
    void setup() throws Exception {
        UserIngredientServiceImpl targetObject = AopTestUtils.getTargetObject(userIngredientService);
        Field basicIngredients = targetObject.getClass().getDeclaredField("basicIngredients");
        basicIngredients.setAccessible(true);
        basicIngredients.set(targetObject,ingredientRepository.getAllBasicIngredients());
    }

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
