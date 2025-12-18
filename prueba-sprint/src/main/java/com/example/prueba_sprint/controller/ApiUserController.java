package com.example.prueba_sprint.controller;

import com.example.prueba_sprint.dto.AuthRequest;
import com.example.prueba_sprint.dto.UserDTO;
import com.example.prueba_sprint.entity.User;
import com.example.prueba_sprint.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
public class ApiUserController {

    @Autowired
    private UserService userService;

    // Map entity -> DTO
    private UserDTO toDto(User u) {
        return new UserDTO(u.getId(), u.getName(), u.getUsername(), u.getEmail(), u.getAvatarUrl());
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody User user) {
        try {
            User created = userService.registerUser(user);
            return ResponseEntity.status(HttpStatus.CREATED).body(toDto(created));
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("error: " + ex.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("error: " + e.getMessage());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthRequest req) {
        Optional<User> userOpt = userService.authenticateUser(req.getEmail(), req.getPassword());
        if (userOpt.isPresent()) {
            // TODO: Empezar con JWT o similar; por ahora devolvemos el DTO
            return ResponseEntity.ok(toDto(userOpt.get()));
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getUser(@PathVariable Long id) {
        Optional<User> userOpt = userService.getUserById(id);
        if (userOpt.isPresent()) {
            return ResponseEntity.ok(toDto(userOpt.get()));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateUser(@PathVariable Long id, @RequestBody User user) {
        Optional<User> updated = userService.updateUser(id, user);
        if (updated.isPresent()) {
            return ResponseEntity.ok(toDto(updated.get()));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found or not updated");
    }

}
