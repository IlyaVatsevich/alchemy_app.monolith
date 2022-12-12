package com.example.alchemy_app.validation.user;

import com.example.alchemy_app.annotation.user.ValidExistUserMail;
import com.example.alchemy_app.repository.UserRepository;
import lombok.RequiredArgsConstructor;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@RequiredArgsConstructor
public class UserExistMailValidator implements ConstraintValidator<ValidExistUserMail,String> {

    private final UserRepository userRepository;

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (null == value || value.isBlank()) {
            return true;
        }
        return !userRepository.existsByMail(value);
    }
}
