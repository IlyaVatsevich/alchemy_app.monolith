package com.example.alchemy_app.validation.ingredient;

import com.example.alchemy_app.annotation.ingredient.ValidIngredientName;
import lombok.RequiredArgsConstructor;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@RequiredArgsConstructor
public class IngredientNameValidator implements ConstraintValidator<ValidIngredientName,String> {

    private final IngredientValidatorHelper validatorHelper;

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null||value.isBlank()) {
            return true;
        }
        return !validatorHelper.ifIngredientWithSuchNameAlreadyExist(value);
    }
}
