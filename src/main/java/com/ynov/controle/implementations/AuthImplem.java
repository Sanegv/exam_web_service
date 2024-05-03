package com.ynov.controle.implementations;

import com.ynov.controle.models.Admin;
import com.ynov.controle.models.Role;
import com.ynov.controle.models.User;
import com.ynov.controle.security.JwtService;
import com.ynov.controle.services.AdminServices;
import com.ynov.controle.services.AuthServices;
import com.ynov.controle.services.UserServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthImplem implements AuthServices {
    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    UserServices userServices;
    @Autowired
    JwtService jwtService;
    @Autowired
    AdminServices adminServices;

    @Override
    public String loginUser(User user, String password) {
        if(bCryptPasswordEncoder.matches(password, user.getPassword()))
            return jwtService.generateToken(user);
        return null;
    }

    @Override
    public String loginAdmin(Admin admin, String password) {
        if(bCryptPasswordEncoder.matches(password, admin.getPassword()))
            return jwtService.generateToken(admin);
        return null;
    }

    @Override
    public boolean validate(User user) {
        user.setValidated(true);
        userServices.createUser(user);
        return user.isValidated();
    }

    @Override
    public User registerUser(User entity, Role user) {
        String passwordEncoded = bCryptPasswordEncoder.encode(entity.getPassword());
        entity.setPassword(passwordEncoded);
        userServices.addRoleToUser(entity, user);
        return userServices.createUser(entity);
    }

    @Override
    public Admin registerAdmin(Admin entity, Role admin) {
        String passwordEncoded = bCryptPasswordEncoder.encode(entity.getPassword());
        entity.setPassword(passwordEncoded);
        adminServices.addRoleToAdmin(entity, admin);
        return adminServices.createAdmin(entity);
    }
}
