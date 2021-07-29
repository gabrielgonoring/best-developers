package com.gabriel.gonoring.borges.bdb.validation.annotation;

import com.gabriel.gonoring.borges.bdb.validation.constraint.IsCurrentUserIdConstraint;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Constraint(validatedBy = IsCurrentUserIdConstraint.class)
public @interface IsCurrentUserId {
    String message() default "This is not current user id";
    Class<? extends Payload>[] payload() default {};
    Class<?>[] groups() default {};
}
