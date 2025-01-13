package com.example.shoppingmodule.service;

import com.example.shoppingmodule.entity.User;
import com.example.shoppingmodule.exceptions.UserNotFoundException;
import com.example.shoppingmodule.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    // Create a new user
    public User createUser(User user) {
        // Check for duplicate email
        if (userRepository.findByEmail(user.getEmail())!=null) {
            throw new RuntimeException("Email already exists: " + user.getEmail());
        }
        return userRepository.save(user);
    }

    // Get a user by ID
    public User getUserById(Long id) {
        User user = userRepository.findById(id).orElse(null);
        if (user == null) {
            throw new UserNotFoundException("User not found with id: " + id);
        }
        return user;
    }

    // Get all users
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    // Update a user
    public User updateUser(Long id, User updatedUser) {
        // 1. Check if the ID is invalid (User not found)
        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("Invalid ID: User with ID " + id + " does not exist."));

        return userRepository.findById(id)
                .map(user -> {
                    user.setName(updatedUser.getName());
                    user.setEmail(updatedUser.getEmail());
                    user.setPassword(updatedUser.getPassword());
                    return userRepository.save(user);
                })
                .orElseThrow(() -> new RuntimeException("Invalid Info"));
    }

    // Delete a user
    public void deleteUser(Long id) {
        if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
        } else {
            throw new RuntimeException("User not found with id " + id);
        }
    }
}
