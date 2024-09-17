package com.example.demo.Controller;

import com.example.demo.Model.User;
import com.example.demo.Service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/user")
public class UserController {


    private final UserService userService;

    @GetMapping("/get-all")// ADMIN
    public ResponseEntity getAllUsers() {
        List<User> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }


    @DeleteMapping("/delete/{userId}")// ADMIN
    public ResponseEntity deleteUser(@PathVariable Integer userId) {
        userService.deleteUser(userId);
        return ResponseEntity.status(200).body("User deleted successfully");
    }


    @GetMapping("/get-user/{userId}") // ADMIN
    public ResponseEntity getUser(@PathVariable int userId) {
        return ResponseEntity.status(200).body(userService.getUserById(userId));
    }


    @GetMapping("/logout")// USER
    public ResponseEntity logout() {
        return ResponseEntity.status(200).body(" logout");
    }

}
