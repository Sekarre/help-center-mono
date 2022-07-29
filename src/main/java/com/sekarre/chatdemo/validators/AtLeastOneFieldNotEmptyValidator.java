package com.sekarre.chatdemo.validators;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Objects;

@Slf4j
public class AtLeastOneFieldNotEmptyValidator implements ConstraintValidator<AtLeastOneFieldNotEmpty, Object> {

    private String[] fields;

    @Override
    public void initialize(AtLeastOneFieldNotEmpty constraintAnnotation) {
        this.fields = constraintAnnotation.fields();
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        boolean isValid = true;
        for (String fieldToCheck : fields) {
            Object fieldToCheckValue = new BeanWrapperImpl(value).getPropertyValue(fieldToCheck);
            if (Objects.isNull(fieldToCheckValue) || StringUtils.isEmpty(fieldToCheckValue.toString())) {
                isValid = false;
            }
        }
        return isValid;
    }
}