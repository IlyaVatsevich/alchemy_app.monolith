package com.example.alchemy_app.validation.user;

import com.example.alchemy_app.annotation.user.ValidUserPassword;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class UserPasswordValidator implements ConstraintValidator<ValidUserPassword,String> {

    private static final String PASS_PATTERN = "[\\p{L}0-9~\"():;<>@\\[\\]!#$%&'*+-/=?^_`{|}]+";

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (null == value || value.isBlank()) {
            return true;
        }
        return upperCaseEnglishAlphaCharIsPresent(value)
                && lowerCaseEnglishAlphaCharIsPresent(value)
                && digitIsPresent(value)
                && value.matches(PASS_PATTERN);
    }


    private boolean upperCaseEnglishAlphaCharIsPresent(String password) {
        return password.codePoints().
                filter(Character::isUpperCase).
                mapToObj(Character.UnicodeBlock::of).anyMatch(c->c.equals(Character.UnicodeBlock.BASIC_LATIN));
    }

    private boolean lowerCaseEnglishAlphaCharIsPresent(String password) {
        return password.codePoints().
                filter(Character::isLowerCase).
                mapToObj(Character.UnicodeBlock::of).anyMatch(c->c.equals(Character.UnicodeBlock.BASIC_LATIN));
    }

    private boolean digitIsPresent(String password) {
        return password.codePoints().anyMatch(Character::isDigit);
    }


}
