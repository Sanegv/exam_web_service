package com.ynov.controle.implementations;

import com.ynov.controle.models.Admin;
import com.ynov.controle.models.Role;
import com.ynov.controle.models.User;
import com.ynov.controle.repositories.AdminRepo;
import com.ynov.controle.services.AdminServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AdminImplem implements AdminServices {
    @Autowired
    AdminRepo adminRepo;
    @Override
    public Optional<Admin> getAdminByEmail(String email) {
        return adminRepo.findByEmail(email);
    }

    @Override
    public void addRoleToAdmin(Admin entity, Role admin) {
        entity.addRole(admin);
        adminRepo.save(entity);
    }

    @Override
    public Admin createAdmin(Admin entity) {
        return adminRepo.save(entity);
    }
}
