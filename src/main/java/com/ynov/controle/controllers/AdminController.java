package com.ynov.controle.controllers;

import com.ynov.controle.models.Admin;
import com.ynov.controle.models.User;
import com.ynov.controle.repositories.RoleRepo;
import com.ynov.controle.models.Role;
import com.ynov.controle.services.AdminServices;
import com.ynov.controle.services.AuthServices;
import com.ynov.controle.services.UserServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

import static com.ynov.controle.models.Role.RoleEnum.ADMIN;
import static com.ynov.controle.models.Role.RoleEnum.USER;

@RestController
@RequestMapping("admin")
@PreAuthorize("hasAuthority('ADMIN')")
public class AdminController {
    @Autowired
    UserServices userServices;
    @Autowired
    AuthServices authServices;
    @Autowired
    RoleRepo roleRepo;
    @Autowired
    AdminServices adminServices;

    private ResponseEntity<?> adminExistResponse (Admin entity){
        Optional<Admin> admin = adminServices.getAdminByEmail(entity.getEmail());
        if(admin.isPresent())
            return new ResponseEntity<>(
                    "This email is already taken",
                    HttpStatus.CONFLICT
            );
        return null;
    }

    private ResponseEntity<?> userExistResponse (String email){
        Optional<User> user = userServices.getUserByEmail(email);
        if(user.isPresent())
            return new ResponseEntity<>(
                    "This email is already taken",
                    HttpStatus.CONFLICT
            );
        return null;
    }

    @PostMapping("signup")
    public ResponseEntity<?> adminRegister(@RequestBody Admin entity){
        ResponseEntity<?> res = adminExistResponse(entity);
        if (res != null)
            return res;
        Optional<Role> role = roleRepo.findByRoleName(ADMIN.name());
        if(role.isEmpty())
            return new ResponseEntity<>(
                    "Internal server error",
                    HttpStatus.INTERNAL_SERVER_ERROR
            );
        return new ResponseEntity<>(
                authServices.registerAdmin(entity, role.get()),
                HttpStatus.CREATED
        );
    }

    @PostMapping("login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> request){
        String pseudo = request.get("email");
        String password = request.get("password");
        Optional<Admin> admin = adminServices.getAdminByEmail(pseudo);
        if (admin.isEmpty())
            return new ResponseEntity(
                    "Admin does not exist",
                    HttpStatus.NOT_FOUND
            );
        String jwt = authServices.loginAdmin(admin.get(), password);
        if (jwt == null)
            return new ResponseEntity<>(
                    "Mot de passe incorrect",
                    HttpStatus.FORBIDDEN
            );
        return new ResponseEntity<>(
                jwt,
                HttpStatus.OK
        );
    }

    @PostMapping("users")
    public ResponseEntity<?> userRegister(@RequestBody Map<String, String> request){
        ResponseEntity<?> res = userExistResponse(request.get("email"));
        if (res != null)
            return res;
        Optional<Role> role = roleRepo.findByRoleName(USER.name());
        if(role.isEmpty())
            return new ResponseEntity<>(
                    "Internal server error",
                    HttpStatus.INTERNAL_SERVER_ERROR
            );
        return new ResponseEntity<>(
                authServices.registerUser(new User
                        (
                            0,
                            request.get("password"),
                            request.get("email"),
                            request.get("fullName"),
                            null,
                            false,
                            null
                        ), role.get()),
                HttpStatus.CREATED
        );
    }

    @DeleteMapping("users/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id){
        Optional<User> check = userServices.getUserById(id);
        if(check.isEmpty())
            return new ResponseEntity(
                    "User does not exist",
                    HttpStatus.NOT_FOUND
            );
        userServices.deleteUserById(id);
        return new ResponseEntity<>(
                HttpStatus.OK
        );
    }

    @PutMapping("users/{id}")
    public ResponseEntity<?> changeUser(@PathVariable Long id, @RequestBody Map<String, String> request){
        Optional<User> entity = userServices.getUserById(id);
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
