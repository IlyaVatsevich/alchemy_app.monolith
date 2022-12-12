package com.example.alchemy_app.validation.user;

import com.example.alchemy_app.annotation.user.ValidUserLogin;
import lombok.RequiredArgsConstructor;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@RequiredArgsConstructor
public class UserLoginValidator implements ConstraintValidator<ValidUserLogin,String> {

    private static final String LOGIN_PATTERN = "[\\p{L}0-9~\"():;<>@\\[\\]!#$%&'*+-/=?^_`{|}]+";

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (null== value || value.isBlank()) {
            return true;
        }
        return value.matches(LOGIN_PATTERN);
    }
}
