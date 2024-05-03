package com.ynov.controle.controllers;

import com.ynov.controle.models.User;
import com.ynov.controle.repositories.RoleRepo;
import com.ynov.controle.security.JwtService;
import com.ynov.controle.services.AuthServices;
import com.ynov.controle.services.UserServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("users")
public class UserController {
    @Autowired
    UserServices userServices;
    @Autowired
    AuthServices authServices;
    @Autowired
    RoleRepo roleRepo;
    @Autowired
    JwtService jwtService;

    @PostMapping("login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> request){
        String pseudo = request.get("email");
        String password = request.get("password");
        Optional<User> user = userServices.getUserByEmail(pseudo);
        if (user.isEmpty())
            return new ResponseEntity(
                    "User does not exist",
                    HttpStatus.NOT_FOUND
            );
        String jwt = authServices.loginUser(user.get(), password);
        if (jwt == null)
            return new ResponseEntity<>(
                    "Incorrect password",
                    HttpStatus.FORBIDDEN
            );
        return new ResponseEntity<>(
                jwt,
                HttpStatus.OK
        );
    }

    @PostMapping("validate")
    public ResponseEntity<?> validate(@RequestBody Map<String, String> request){
        String pseudo = request.get("email");
        String password = request.get("password");
        Optional<User> user = userServices.getUserByEmail(pseudo);
        if (user.isEmpty())
            return new ResponseEntity(
                    "User n'existe pas",
                    HttpStatus.NOT_FOUND
            );
        String jwt = authServices.loginUser(user.get(), password);
        if (jwt == null)
            return new ResponseEntity<>(
                    "Mot de passe incorrect",
                    HttpStatus.FORBIDDEN
            );
        if(!authServices.validate(user.get()))
            return new ResponseEntity<>(
                    "Internal server error",
                    HttpStatus.INTERNAL_SERVER_ERROR
            );
        return new ResponseEntity<>(
                "Account validated",
                HttpStatus.OK
        );
    }

    @PutMapping("users")
    public ResponseEntity<?> changeUser(String token, @RequestBody Map<String, String> request){
        Optional<User> entity = userServices.getUserByEmail(jwtService.extractUsername(token));
        if(entity.isEmpty())
            return new ResponseEntity(
                    "User does not exist",
                    HttpStatus.NOT_FOUND
            );
        User user = entity.get();
        if(!request.get("email").isEmpty()){
            user.setEmail(request.get("email"));
        }
        if(!request.get("password").isEmpty()){
            user.setPassword(request.get("password"));
        }
        return new ResponseEntity<>(
                userServices.createUser(user),
                HttpStatus.OK
        );
    }
}
