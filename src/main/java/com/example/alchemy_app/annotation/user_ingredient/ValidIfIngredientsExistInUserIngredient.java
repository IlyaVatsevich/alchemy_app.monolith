package com.example.alchemy_app.annotation.user_ingredient;

import com.example.alchemy_app.validation.user_ingredient.IngredientsExistInUserIngredientValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.TYPE, ElementType.FIELD, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = IngredientsExistInUserIngredientValidator.class)
public @interface ValidIfIngredientsExistInUserIngredient {

    String message() default "You have no such ingredient's in your bag.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
