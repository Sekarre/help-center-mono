package com.sekarre.chatdemo.validators;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = AtLeastOneFieldNotEmptyValidator.class)
@Target({ ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface AtLeastOneFieldNotEmpty {

    String message() default "Fields values don't match!";

    String[] fields();

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}