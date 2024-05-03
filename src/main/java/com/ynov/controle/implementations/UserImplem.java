package com.ynov.controle.implementations;

import com.ynov.controle.models.Role;
import com.ynov.controle.models.User;
import com.ynov.controle.repositories.UserRepo;
import com.ynov.controle.services.UserServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserImplem implements UserServices {
    @Autowired
    UserRepo userRepo;

    @Override
    public User createUser(User entity) {
        return userRepo.save(entity);
    }

    @Override
    public Optional<User> getUserByEmail(String pseudo) {
        return userRepo.findByEmail(pseudo);
    }

    @Override
    public Optional<User> getUserById(Long id) {
        return userRepo.findById(id);
    }

    @Override
    public void deleteUserById(Long id) {
        userRepo.deleteById(id);
    }

    @Override
    public void addRoleToUser(User user, Role role) {
        user.addRole(role);
        userRepo.save(user);
    }
}
