package com.example.alchemy_app.annotation.ingredient;

import com.example.alchemy_app.validation.ingredient.IngredientsExistValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.TYPE, ElementType.FIELD, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = IngredientsExistValidator.class)
public @interface ValidIfIngredientsExist {

    String message() default "Ingredients not exist.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
