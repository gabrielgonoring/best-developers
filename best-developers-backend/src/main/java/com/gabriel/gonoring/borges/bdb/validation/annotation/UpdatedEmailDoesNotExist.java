package com.gabriel.gonoring.borges.bdb.validation.annotation;

import com.gabriel.gonoring.borges.bdb.validation.constraint.EmailDoesNotExistsConstraint;
import com.gabriel.gonoring.borges.bdb.validation.constraint.UpdatedEmailDoesNotExistsConstraint;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Constraint(validatedBy = UpdatedEmailDoesNotExistsConstraint.class)
public @interface UpdatedEmailDoesNotExist {

    String message() default "This email already exist";
    Class<? extends Payload>[] payload() default {};
    Class<?>[] groups() default {};
}
