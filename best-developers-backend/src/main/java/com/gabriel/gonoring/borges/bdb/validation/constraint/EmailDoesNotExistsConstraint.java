package com.gabriel.gonoring.borges.bdb.validation.constraint;

import com.gabriel.gonoring.borges.bdb.repository.UserPORepository;
import com.gabriel.gonoring.borges.bdb.validation.annotation.EmailDoesNotExists;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class EmailDoesNotExistsConstraint implements ConstraintValidator<EmailDoesNotExists, String> {

    private UserPORepository userPORepository;

    @Autowired
    public EmailDoesNotExistsConstraint(UserPORepository userPORepository) {
        this.userPORepository = userPORepository;
    }

    @Override
    public boolean isValid(String email, ConstraintValidatorContext constraintValidatorContext) {

        if (userPORepository.existsByEmail(email))
            return false;

        return true;
    }
}
