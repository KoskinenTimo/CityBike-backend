package org.citybike.dto.constraint;

import org.citybike.dto.StationRequest;
import org.citybike.repository.StationRepository;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;


public class UniqueIdentifierValidator implements ConstraintValidator<UniqueIdentifier, Long> {

    @Autowired
    private StationRepository stationRepository;

    @Override
    public void initialize(UniqueIdentifier constraintAnnotation) {
        constraintAnnotation.message();
    }

    @Override
    public boolean isValid(Long identifier, ConstraintValidatorContext context) {
        if (stationRepository != null && stationRepository.findByIdentifier(identifier) != null) {
            return false;
        }
        return true;
    }
}