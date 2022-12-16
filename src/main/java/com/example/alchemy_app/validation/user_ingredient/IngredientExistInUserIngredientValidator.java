package com.example.alchemy_app.validation.user_ingredient;

import com.example.alchemy_app.annotation.user_ingredient.ValidIfIngredientExistInUserIngredient;
import com.example.alchemy_app.repository.UserIngredientRepository;
import com.example.alchemy_app.security.UserHolder;
import lombok.RequiredArgsConstructor;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@RequiredArgsConstructor
public class IngredientExistInUserIngredientValidator implements
        ConstraintValidator<ValidIfIngredientExistInUserIngredient, Long> {

    private final UserIngredientRepository userIngredientRepository;

    private final UserHolder userHolder;

    @Override
    public boolean isValid(Long value, ConstraintValidatorContext context) {
        return userIngredientRepository.existsUserIngredientByIngredientIdAndUserAndCountGreaterThan(value,userHolder.getUser());
    }
}
