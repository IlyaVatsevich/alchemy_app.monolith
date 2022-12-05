package com.example.alchemy_app.validation.ingredient;

import com.example.alchemy_app.annotation.user.ValidEnoughGoldToBuy;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.List;

public class BuyIngredientValidator implements ConstraintValidator<ValidEnoughGoldToBuy, List<Long>> {

    @Override
    public boolean isValid(List<Long> value, ConstraintValidatorContext context) {
        //TODO after security add
        return false;
    }
}
