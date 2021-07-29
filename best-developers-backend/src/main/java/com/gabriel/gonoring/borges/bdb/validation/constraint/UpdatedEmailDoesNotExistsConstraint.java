package com.gabriel.gonoring.borges.bdb.validation.constraint;

import com.gabriel.gonoring.borges.bdb.dto.user.UpdatedUserDTO;
import com.gabriel.gonoring.borges.bdb.repository.UserPORepository;
import com.gabriel.gonoring.borges.bdb.util.Utils;
import com.gabriel.gonoring.borges.bdb.validation.annotation.EmailDoesNotExists;
import com.gabriel.gonoring.borges.bdb.validation.annotation.UpdatedEmailDoesNotExist;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class UpdatedEmailDoesNotExistsConstraint implements ConstraintValidator<UpdatedEmailDoesNotExist, UpdatedUserDTO> {

    private UserPORepository userPORepository;

    @Autowired
    public UpdatedEmailDoesNotExistsConstraint(UserPORepository userPORepository) {
        this.userPORepository = userPORepository;
    }

    @Override
    public boolean isValid(UpdatedUserDTO user, ConstraintValidatorContext constraintValidatorContext) {

        if (Utils.isStringNullOrBlack(user.getEmail()))
            return true;

        if (userPORepository.existsByIdAndEmail(user.getId(), user.getEmail()))
            return true;

        if (userPORepository.existsByEmail(user.getEmail()))
            return false;

        return true;
    }
}
