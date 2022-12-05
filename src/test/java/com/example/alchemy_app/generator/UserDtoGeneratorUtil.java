package com.example.alchemy_app.generator;

import com.example.alchemy_app.dto.UserRegDto;

public class UserDtoGeneratorUtil {

    private UserDtoGeneratorUtil() {}

    public static UserRegDto createValidUserForReg() {
        return UserRegDto.builder().
                withLogin(SecondaryGeneratorUtil.generateRndStr()).
                withPassword("P@ssword1!").
                withMail(SecondaryGeneratorUtil.generateRndStr() +
                        SecondaryGeneratorUtil.generatePositiveInteger() +
                        "@yopmail.com").
                build();
    }

    public static UserRegDto createUserWithInvalidMail() {
        return UserRegDto.builder().
                withLogin(SecondaryGeneratorUtil.generateRndStr()).
                withPassword("P@ssword1!").
                withMail(SecondaryGeneratorUtil.generateRndStr()).
                build();
    }

    public static UserRegDto createUserWithInvalidPassword() {
        return UserRegDto.builder().
                withLogin(SecondaryGeneratorUtil.generateRndStr()).
                withPassword("P@ssword!").
                withMail(SecondaryGeneratorUtil.generateRndStr()).
                build();
    }

}
