package com.example.alchemy_app.service;

import com.example.alchemy_app.entity.Ingredient;
import com.example.alchemy_app.entity.User;
import com.example.alchemy_app.extension.GMailCustomExtension;
import com.example.alchemy_app.generator.IngredientGeneratorUtil;
import com.example.alchemy_app.generator.UserGeneratorUtil;
import com.example.alchemy_app.repository.IngredientRepository;
import com.example.alchemy_app.repository.UserRepository;
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

import java.lang.reflect.Field;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
class UserIngredientServiceTest {

    @Autowired
    private UserIngredientService userIngredientService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private IngredientRepository ingredientRepository;
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
    void testAddNewBasicIngredientToUsersShouldSave() {
        Ingredient newIngredient = ingredientRepository.save(IngredientGeneratorUtil.createValidIngredient());
        Assertions.assertDoesNotThrow(()->userIngredientService.addNewBasicIngredientToUsers(newIngredient));
    }

    @Test
    void testInitializeUserIngredientShouldSave() {
        User newUser = userRepository.save(UserGeneratorUtil.createValidUser());
        Assertions.assertDoesNotThrow(()->userIngredientService.initializeUserIngredient(newUser));
    }

}
