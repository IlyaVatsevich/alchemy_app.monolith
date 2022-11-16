package com.example.alchemy_app.generator;

import com.example.alchemy_app.entity.User;
import com.example.alchemy_app.enums.UserRole;

import java.util.Set;


public class UserGeneratorUtil {

    private UserGeneratorUtil(){}

    public static User createValidUser() {
        return User.builder().
                withUserRole(Set.of(UserRole.USER)).
                withPassword("P@ssword1!".toCharArray()).
                withIsActive(true).
                withMail(SecondaryGeneratorUtil.generateRndStr() +
                        SecondaryGeneratorUtil.generatePositiveInteger() +
                        "@yopmail.com").
                withLogin(SecondaryGeneratorUtil.generateRndStr() +
                        SecondaryGeneratorUtil.generatePositiveInteger()).
                withGold(SecondaryGeneratorUtil.generatePositiveInteger()).
                build();
    }

    public static User createUserWithInvalidMail() {
        User invalidUser = createValidUser();
        invalidUser.setMail(SecondaryGeneratorUtil.generateRndStr());
        return invalidUser;
    }

    public static User createUserWithNullProperty() {
        User userWithNullProperty = createValidUser();
        userWithNullProperty.setLogin(null);
        return userWithNullProperty;
    }



}
