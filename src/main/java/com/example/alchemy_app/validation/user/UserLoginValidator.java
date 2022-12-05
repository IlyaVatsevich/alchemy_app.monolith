package com.example.alchemy_app.validation.user;

import com.example.alchemy_app.annotation.user.ValidUserLogin;
import com.example.alchemy_app.repository.UserRepository;
import lombok.RequiredArgsConstructor;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@RequiredArgsConstructor
public class UserLoginValidator implements ConstraintValidator<ValidUserLogin,String> {

    private final UserRepository userRepository;

    private static final String LOGIN_PATTERN = "[\\p{L}0-9~\"():;<>@\\[\\]!#$%&'*+-/=?^_`{|}]+";

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (null== value || value.isBlank()) {
            return true;
        }
        return !userRepository.existsByLogin(value) && value.matches(LOGIN_PATTERN);
    }
}
