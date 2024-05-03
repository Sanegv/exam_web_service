package com.ynov.controle.modelsTests;

import com.ynov.controle.models.Admin;
import jakarta.validation.ConstraintViolation;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class AdminTests {
    @Autowired
    private LocalValidatorFactoryBean validator;

    @Test
    public void shouldThrowConstraintsValidationWithSmallPassword(){
        Admin admin = new Admin(0, "132", "admin@gmail.com", null);
        Set<ConstraintViolation<Admin>> violations = validator.validate(admin);
        assertFalse(violations.isEmpty());

        boolean found = false;
        for (ConstraintViolation<Admin> violation : violations)
            if (violation.getMessage().equals("Password should be at least 8 characters long")) {
                found = true;
                break;
            }
        assertTrue(found);
    }

    @Test
    public void shouldThrowConstraintsValidationWithWrongEmail(){
        Admin admin = new Admin(0, "123456789", "admi", null);
        Set<ConstraintViolation<Admin>> violations = validator.validate(admin);
        assertFalse(violations.isEmpty());

        boolean found = false;
        for (ConstraintViolation<Admin> violation : violations)
            if (violation.getMessage().equals("Not a valid email address")) {
                found = true;
                break;
            }
        assertTrue(found);
    }

    @Test
    public void shouldSucceedWithValidParameters(){
        String email = "admin@test.com";
        String password = "123456789";
        Admin admin = new Admin(0,password, email, null);

        Set<ConstraintViolation<Admin>> violations = validator.validate(admin);
        assertTrue(violations.isEmpty());

        assertEquals(email, admin.getEmail());
        assertEquals(password, admin.getPassword());
    }
}

