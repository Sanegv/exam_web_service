package com.ynov.controle.modelsTests;

import com.ynov.controle.models.User;
import jakarta.validation.ConstraintViolation;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class UserTests {
    @Autowired
    private LocalValidatorFactoryBean validator;

    @Test
    public void shouldThrowConstraintsValidationWithSmallPassword(){
//        User user = new User(0, "132", "user@gmail.com", null);
//        Set<ConstraintViolation<User>> violations = validator.validate(user);
//        assertFalse(violations.isEmpty());
//
//        boolean found = false;
//        for (ConstraintViolation<User> violation : violations)
//            if (violation.getMessage().equals("Password should be at least 8 characters long")) {
//                found = true;
//                break;
//            }
//        assertTrue(found);
//    }
//
//    @Test
//    public void shouldThrowConstraintsValidationWithWrongEmail(){
//        User user = new User(0, "123456789", "admi", null);
//        Set<ConstraintViolation<User>> violations = validator.validate(user);
//        assertFalse(violations.isEmpty());
//
//        boolean found = false;
//        for (ConstraintViolation<User> violation : violations)
//            if (violation.getMessage().equals("Not a valid email address")) {
//                found = true;
//                break;
//            }
//        assertTrue(found);
//    }
//
//    @Test
//    public void shouldSucceedWithValidParameters(){
//        String email = "user@test.com";
//        String password = "123456789";
//        User user = new User(0,email, password, null);
//
//        Set<ConstraintViolation<User>> violations = validator.validate(user);
//        assertTrue(violations.isEmpty());
//
//        assertEquals(email, user.getEmail());
//        assertEquals(password, user.getPassword());
    }
}
