package com.example.alchemy_app.generator;

import com.example.alchemy_app.dto.UserLogDto;
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

    public static UserLogDto createValidLogUser() {
        return UserLogDto.
                builder().
                withLogin("user_1").
                withPassword("P@ssword1").
                build();
    }

    public static UserLogDto createNotExistingLogUser() {
        return UserLogDto.
                builder().
                withLogin(SecondaryGeneratorUtil.generateRndStr()).
                withPassword("P@ssword1").
                build();
    }

    public static UserLogDto createExistingUserWithInvalidPassword() {
        return UserLogDto.
                builder().
                withLogin("user_1").
                withPassword("P@ssword1!").
                build();
    }

}
