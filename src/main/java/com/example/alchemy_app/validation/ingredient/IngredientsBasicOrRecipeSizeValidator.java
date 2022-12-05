package com.example.alchemy_app.validation.ingredient;

import com.example.alchemy_app.annotation.ingredient.ValidIngredientsBasicOrRecipeSize;
import lombok.RequiredArgsConstructor;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.List;

@RequiredArgsConstructor
public class IngredientsBasicOrRecipeSizeValidator implements ConstraintValidator<ValidIngredientsBasicOrRecipeSize, List<Long>> {

    private final IngredientValidatorHelper validatorHelper;

    @Override
    public boolean isValid(List<Long> value, ConstraintValidatorContext context) {
        if (value == null || value.isEmpty()) {
            return true;
        }
        return validatorHelper.checkIngredientsSize(value);
    }
}
