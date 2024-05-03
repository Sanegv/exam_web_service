package com.ynov.controle.services;

import com.ynov.controle.models.Admin;
import com.ynov.controle.models.Role;
import com.ynov.controle.models.User;

public interface AuthServices {
    String loginUser(User user, String password);
    String loginAdmin(Admin admin, String password);
    boolean validate(User user);
    User registerUser(User entity, Role user);
    Admin registerAdmin(Admin entity, Role admin);
}
