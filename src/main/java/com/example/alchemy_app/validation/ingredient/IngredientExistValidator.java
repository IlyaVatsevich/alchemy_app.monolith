package com.example.alchemy_app.validation.ingredient;

import com.example.alchemy_app.annotation.ingredient.ValidIfIngredientExist;
import lombok.RequiredArgsConstructor;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@RequiredArgsConstructor
public class IngredientExistValidator implements ConstraintValidator<ValidIfIngredientExist,Long> {

    private final IngredientValidatorHelper validatorHelper;

    @Override
    public boolean isValid(Long value, ConstraintValidatorContext context) {
        if (value == null) {
            return true;
        }
        return validatorHelper.ifIngredientExist(value);
    }
}
