package com.ynov.controle.controllers;

import com.ynov.controle.models.Article;
import com.ynov.controle.models.User;
import com.ynov.controle.security.JwtService;
import com.ynov.controle.services.ArticlesServices;
import com.ynov.controle.services.UserServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("api")
public class ArticleController {
    @Autowired
    ArticlesServices articlesServices;
    @Autowired
    UserServices userServices;
    @Autowired
    JwtService jwtService;

    @GetMapping("products")
    public ResponseEntity<?> getAllArticles(String token){
        Optional<User> entity = userServices.getUserByEmail(jwtService.extractUsername(token));
        if(entity.isEmpty())
            return new ResponseEntity(
                    "User does not exist",
                    HttpStatus.NOT_FOUND
            );
        User user = entity.get();
        return new ResponseEntity<>(
                user.getStock(),
                HttpStatus.OK
        );
    }

    @GetMapping("products/{id}")
    public ResponseEntity<?> getArticle(String token, @PathVariable Long id){
        Optional<User> entity = userServices.getUserByEmail(jwtService.extractUsername(token));
        if(entity.isEmpty())
            return new ResponseEntity(
                    "User does not exist",
                    HttpStatus.NOT_FOUND
            );
        User user = entity.get();
        Article result = null;
        for(Article article : user.getStock()){
            if(article.getId() == id) {
                result = article;
                break;
            }
        }
        if(result == null){
            return new ResponseEntity<>(
                    "No product with provided id",
                    HttpStatus.NOT_FOUND
            );
        }
        return new ResponseEntity<>(
                result,
                HttpStatus.OK
        );
    }

    @PostMapping("products")
    public ResponseEntity<?> createArticle(@RequestBody Article article, String token){
        Optional<User> entity = userServices.getUserByEmail(jwtService.extractUsername(token));
        if(entity.isEmpty())
            return new ResponseEntity(
                    "User does not exist",
                    HttpStatus.NOT_FOUND
            );
        User user = entity.get();
        user.getStock().add(article);
        User response = userServices.createUser(user);
        return new ResponseEntity<>(
                response,
                HttpStatus.OK
        );
    }
}
