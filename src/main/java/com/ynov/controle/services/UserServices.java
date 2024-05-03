package com.ynov.controle.services;

import com.ynov.controle.models.Role;
import com.ynov.controle.models.User;

import java.util.Optional;

public interface UserServices {
    public void addRoleToUser(User user, Role role);
    public User createUser(User entity);

    Optional<User> getUserByEmail(String pseudo);
    Optional<User> getUserById(Long id);
    public void deleteUserById(Long id);
}
