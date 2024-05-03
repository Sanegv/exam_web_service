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

import java.util.HashMap;
import java.util.Map;
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
                HttpStatus.CREATED
        );
    }

    @PutMapping("products/{id}")
    public ResponseEntity<?> changeArticle(String token, @PathVariable Long id){
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
        user.getStock().add(result);
        User response = userServices.createUser(user);
        return new ResponseEntity<>(
                response,
                HttpStatus.CREATED
        );
    }

    @DeleteMapping("products/{id}")
    public ResponseEntity<?> deleteArticle(String token, @PathVariable Long id){
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
        user.getStock().remove(result);
        articlesServices.deleteArticleById(id);
        return new ResponseEntity<>(
                HttpStatus.OK
        );
    }

    @PostMapping("stock/entry")
    public ResponseEntity<?> fillStock(String token, @RequestBody Map<String, Integer> request){
        Optional<User> entity = userServices.getUserByEmail(jwtService.extractUsername(token));
        if(entity.isEmpty())
            return new ResponseEntity(
                    "User does not exist",
                    HttpStatus.NOT_FOUND
            );

        if((!request.containsKey("id")) ||(!request.containsKey("quantity")))return new ResponseEntity(
                "Body should contain a field id and a field quantity",
                HttpStatus.BAD_REQUEST
        );

        User user = entity.get();
        Article result = null;
        for(Article article : user.getStock()){
            if(article.getId() == (long)request.get("id")) {
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
        result.setQuantity(result.getQuantity() + (request.get("quantity")));
        return new ResponseEntity<>(
                userServices.createUser(user),
                HttpStatus.OK
        );
    }

    @PostMapping("stock/exit")
    public ResponseEntity<?> emptyStock(String token, @RequestBody Map<String, Integer> request){
        Optional<User> entity = userServices.getUserByEmail(jwtService.extractUsername(token));
        if(entity.isEmpty())
            return new ResponseEntity(
                    "User does not exist",
                    HttpStatus.NOT_FOUND
            );

        if((!request.containsKey("id")) ||(!request.containsKey("quantity")))return new ResponseEntity(
                "Body should contain a field id and a field quantity",
                HttpStatus.BAD_REQUEST
        );

        User user = entity.get();
        Article result = null;
        for(Article article : user.getStock()){
            if(article.getId() == (long)request.get("id")) {
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
        int newQuantity = result.getQuantity() - (request.get("quantity"));
        if(newQuantity < 0)
            return new ResponseEntity<>(
                    "There isn't enough stock to perform this action",
                    HttpStatus.BAD_REQUEST
            );
        result.setQuantity(newQuantity);
        return new ResponseEntity<>(
                userServices.createUser(user),
                HttpStatus.OK
        );
    }

    @GetMapping("reports/inventory")
    public ResponseEntity<?> showInventory(String token){
        Optional<User> entity = userServices.getUserByEmail(jwtService.extractUsername(token));
        if(entity.isEmpty())
            return new ResponseEntity(
                    "User does not exist",
                    HttpStatus.NOT_FOUND
            );
        Map<String, Integer> inventory = new HashMap<>();
        for(Article article : entity.get().getStock()){
            inventory.put(article.getName(), article.getQuantity());
        }
        return new ResponseEntity<>(
                inventory,
                HttpStatus.OK
        );
    }
}
