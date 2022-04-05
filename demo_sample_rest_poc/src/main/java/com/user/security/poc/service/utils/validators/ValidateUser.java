package com.user.security.poc.service.utils.validators;

import com.user.security.poc.dto.UserTo;
import com.user.security.poc.exception.SecurityServiceErrorCode;
import com.user.security.poc.exception.SecurityServiceException;
import com.user.security.poc.repository.model.BaseModel;
import com.user.security.poc.repository.model.UserModel;
import org.hibernate.validator.messageinterpolation.ResourceBundleMessageInterpolator;
import org.hibernate.validator.resourceloading.AggregateResourceBundleLocator;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.*;

@Component
public class ValidateUser {
    private ValidateUser() {
        //Private util class
    }

    private static Validator validator = Validation.byDefaultProvider().configure().messageInterpolator(
            new ResourceBundleMessageInterpolator(
                    new AggregateResourceBundleLocator(
                            Arrays.asList("constraint_message")))).buildValidatorFactory().getValidator();

    public static void validateModel(BaseModel baseModel) {
        Set<ConstraintViolation<BaseModel>> violations = validator.validate(baseModel);
        violations.forEach(violation -> {
            //If error code is used to generate error message, then following fields will be populated,
            // Index 0 : Field Name, Index 1 : Provided value, Index 1 : min value. Index 2 : Max value
            String minValue = "0";
            String maxValue = "0";

            if (violation.getConstraintDescriptor() != null && violation.getConstraintDescriptor().getAttributes() != null) {
                Map<String, Object> limitsMap = violation.getConstraintDescriptor().getAttributes();
                minValue = String.valueOf(limitsMap.getOrDefault("min", 0));
                maxValue = String.valueOf(limitsMap.getOrDefault("max", 0));
            }
            String[] errorKeys = new String[]{violation.getPropertyPath().iterator().next().getName(), String.valueOf(violation.getInvalidValue().toString()), minValue, maxValue};
            throw new SecurityServiceException(SecurityServiceErrorCode.findByCode(Integer.valueOf(violation.getMessage())), HttpStatus.BAD_REQUEST.value(), errorKeys, errorKeys);
        });
    }
}
