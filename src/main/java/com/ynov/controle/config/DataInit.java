package com.ynov.controle.config;

import com.ynov.controle.models.Role;
import com.ynov.controle.models.User;
import com.ynov.controle.repositories.RoleRepo;
import com.ynov.controle.repositories.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataInit implements CommandLineRunner {
    @Autowired
    RoleRepo roleRepo;

    @Override
    public void run(String... args) throws Exception {
        for (Role.RoleEnum roleEnum : Role.RoleEnum.values())   {
            roleRepo.save(new Role(null, roleEnum.name()));
        }
    }
}