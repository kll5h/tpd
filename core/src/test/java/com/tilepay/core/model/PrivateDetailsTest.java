package com.tilepay.core.model;

import org.junit.BeforeClass;
import org.junit.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.assertEquals;

public class PrivateDetailsTest {

    private static Validator validator;

    @BeforeClass
    public static void beforeClass() {
        validator = Validation.buildDefaultValidatorFactory().getValidator();
    }

    @Test
    public void validate() {
        PrivateDetails privateDetails = new PrivateDetails();
        privateDetails.setEmail("123");

        List<ConstraintViolation<Object>> constraintViolations = validate(privateDetails, 1);
        assertConstraintViolation(constraintViolations.get(0), "email", "not a well-formed email address");
    }

    private List<ConstraintViolation<Object>> validate(Object object, int size) {
        Set<ConstraintViolation<Object>> constraintViolations = validator.validate(object);
        assertEquals(size, constraintViolations.size());
        ArrayList<ConstraintViolation<Object>> list = new ArrayList<>(constraintViolations);
        Collections.sort(list, (o1, o2) -> o1.getPropertyPath().toString().compareTo(o2.getPropertyPath().toString()));
        return list;
    }

    private void assertConstraintViolation(ConstraintViolation<Object> constraintViolation, String path, String message) {
        assertEquals(path, constraintViolation.getPropertyPath().toString());
        assertEquals(message, constraintViolation.getMessage());
    }

}