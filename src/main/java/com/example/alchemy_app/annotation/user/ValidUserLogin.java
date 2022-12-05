package com.example.alchemy_app.annotation.user;

import com.example.alchemy_app.validation.user.UserLoginValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.TYPE, ElementType.FIELD, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = UserLoginValidator.class)
public @interface ValidUserLogin {

    String message() default "Invalid login.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
