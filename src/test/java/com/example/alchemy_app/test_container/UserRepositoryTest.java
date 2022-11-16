package com.example.alchemy_app.test_container;

import com.example.alchemy_app.entity.User;
import com.example.alchemy_app.generator.UserGeneratorUtil;
import com.example.alchemy_app.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.ActiveProfiles;


@DataJpaTest
@ActiveProfiles("postgres")
class UserRepositoryTest {

    @Autowired
    private UserRepository repository;

    @Test
    void testSaveUserShouldSaveValidUser() {
        User validUser = UserGeneratorUtil.createValidUser();
        Assertions.assertDoesNotThrow(()->repository.saveAndFlush(validUser));
    }

    @Test
    void testSaveUserShouldThrowUserWithNullProperty() {
        User userWithNullProperty = UserGeneratorUtil.createUserWithNullProperty();
        Assertions.assertThrows(DataIntegrityViolationException.class,
                ()->repository.saveAndFlush(userWithNullProperty));
    }

    @Test
    void testSaveUserShouldThrowUserWithInvalidMail() {
        User userWithInvalidMail = UserGeneratorUtil.createUserWithInvalidMail();
        Assertions.assertThrows(DataIntegrityViolationException.class,
                ()->repository.saveAndFlush(userWithInvalidMail));
    }

}
