package com.example.alchemy_app.annotation.user;


import com.example.alchemy_app.validation.user.UserLoginExistValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.TYPE, ElementType.FIELD, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = UserLoginExistValidator.class)
public @interface ValidExistUserLogin {

    String message() default "User with such login already exist.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
