package com.example.alchemy_app.validation.user_ingredient;

import com.example.alchemy_app.annotation.user_ingredient.ValidIfIngredientExistInUserIngredient;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class IngredientExistInUserIngredientValidator implements
        ConstraintValidator<ValidIfIngredientExistInUserIngredient,Long> {

    @Override
    public boolean isValid(Long value, ConstraintValidatorContext context) {
        //TODO after security add
        return false;
    }
}
