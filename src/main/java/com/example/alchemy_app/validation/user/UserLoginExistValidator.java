package com.example.alchemy_app.validation.user;

import com.example.alchemy_app.annotation.user.ValidExistUserLogin;
import com.example.alchemy_app.repository.UserRepository;
import lombok.RequiredArgsConstructor;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@RequiredArgsConstructor
public class UserLoginExistValidator implements ConstraintValidator<ValidExistUserLogin,String> {

    private final UserRepository userRepository;
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (null == value || value.isBlank()) {
            return true;
        }
        return !userRepository.existsByLogin(value);
    }
}
