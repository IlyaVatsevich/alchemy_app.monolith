package com.example.alchemy_app.annotation.ingredient;


import com.example.alchemy_app.validation.ingredient.IngredientNameValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.TYPE, ElementType.FIELD, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = IngredientNameValidator.class)
public @interface ValidIngredientName {

    String message() default "Ingredient with such name already exists.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
