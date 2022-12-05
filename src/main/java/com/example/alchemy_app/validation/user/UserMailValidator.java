package com.example.alchemy_app.validation.user;

import com.example.alchemy_app.annotation.user.ValidUserMail;
import com.example.alchemy_app.repository.UserRepository;
import lombok.RequiredArgsConstructor;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@RequiredArgsConstructor
public class UserMailValidator implements ConstraintValidator<ValidUserMail,String> {
    private final UserRepository userRepository;
    private static final String MAIL_PATTERN = "^[A-Za-z][a-z.-_]+@[A-Za-z0-9.-]+[.][A-Za-z]+$";

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (null == value || value.isBlank()) {
            return true;
        }
        return !userRepository.existsByMail(value) && value.matches(MAIL_PATTERN);
    }
}
