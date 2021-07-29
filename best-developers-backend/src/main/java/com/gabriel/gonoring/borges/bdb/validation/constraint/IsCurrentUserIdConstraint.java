package com.gabriel.gonoring.borges.bdb.validation.constraint;

import com.gabriel.gonoring.borges.bdb.service.user.CurrentUserService;
import com.gabriel.gonoring.borges.bdb.validation.annotation.IsCurrentUserId;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.UUID;

public class IsCurrentUserIdConstraint implements ConstraintValidator<IsCurrentUserId, UUID> {

    private CurrentUserService currentUserService;

    @Autowired
    public IsCurrentUserIdConstraint(CurrentUserService currentUserService) {
        this.currentUserService = currentUserService;
    }

    @Override
    public boolean isValid(UUID userId, ConstraintValidatorContext constraintValidatorContext) {

        if (!currentUserService.getCurrentUserId().equals(userId)){
            return false;
        }

        return true;
    }
}
