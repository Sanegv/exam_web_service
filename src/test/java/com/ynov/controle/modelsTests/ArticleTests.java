package com.ynov.controle.modelsTests;

import com.ynov.controle.models.Article;
import jakarta.validation.ConstraintViolation;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class ArticleTests {
    @Autowired
    private LocalValidatorFactoryBean validator;

    @Test
    public void shouldThrowConstraintsValidationWithNegativePrice(){
        Article article = new Article(null, "name", "description", -1.5, 1, null);

        Set<ConstraintViolation<Article>> violations = validator.validate(article);
        assertFalse(violations.isEmpty());

        boolean found = false;
        for (ConstraintViolation<Article> violation : violations)
            if (violation.getMessage().equals("Price cannot be below 0!")) {
                found = true;
                break;
            }
        assertTrue(found);
    }

    @Test
    public void shouldThrowConstraintsValidationWithNegativeQuantity(){
        Article article = new Article(null, "name", "description", 5.0, -1, null);

        Set<ConstraintViolation<Article>> violations = validator.validate(article);
        assertFalse(violations.isEmpty());

        boolean found = false;
        for (ConstraintViolation<Article> violation : violations)
            if (violation.getMessage().equals("Quantity cannot be below 0!")) {
                found = true;
                break;
            }
        assertTrue(found);
    }

    @Test
    public void shouldThrowConstraintsValidationWithSmallName(){
        Article article = new Article(null, "n", "description", 5.0, 1, null);

        Set<ConstraintViolation<Article>> violations = validator.validate(article);
        assertFalse(violations.isEmpty());

        boolean found = false;
        for (ConstraintViolation<Article> violation : violations)
            if (violation.getMessage().equals("Title must be at least 3 characters long!")) {
                found = true;
                break;
            }
        assertTrue(found);
    }

    @Test
    public void shouldThrowConstraintsValidationWithSmallDescription(){
        Article article = new Article(null, "name", "d", 5.0, 1, null);

        Set<ConstraintViolation<Article>> violations = validator.validate(article);
        assertFalse(violations.isEmpty());

        boolean found = false;
        for (ConstraintViolation<Article> violation : violations)
            if (violation.getMessage().equals("Description must be at least 10 characters long!")) {
                found = true;
                break;
            }
        assertTrue(found);
    }

    @Test
    public void shouldSucceedWithValidParameters(){
        String name = "name";
        String description = "description";
        double price = 5.0;
        int quantity = 1;
        Article article = new Article(null, name , description, price, quantity, null);

        Set<ConstraintViolation<Article>> violations = validator.validate(article);
        assertTrue(violations.isEmpty());

        assertEquals(name, article.getName());
        assertEquals(description, article.getDescription());
        assertEquals(price, article.getPrice());
        assertEquals(quantity, article.getQuantity());
    }
}
