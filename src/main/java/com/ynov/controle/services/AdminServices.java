package com.ynov.controle.services;

import com.ynov.controle.models.Admin;
import com.ynov.controle.models.Role;
import com.ynov.controle.models.User;

import java.util.Optional;

public interface AdminServices {
    Optional<Admin> getAdminByEmail(String email);

    void addRoleToAdmin(Admin entity, Role admin);

    Admin createAdmin(Admin entity);
}
