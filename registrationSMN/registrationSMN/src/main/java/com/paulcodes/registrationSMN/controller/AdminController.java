package com.paulcodes.registrationSMN.controller;

import com.paulcodes.registrationSMN.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.paulcodes.registrationSMN.model.User;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private UserService userService;

    // GET /admin/user/{id}
    @GetMapping("/user/{id}")
    public ResponseEntity<User> getUserById(@PathVariable int id) {
        User user = userService.getUserById(id);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        return ResponseEntity.ok(user);
    }


    // DELETE /admin/user/delete/{id}
    @DeleteMapping("/user/delete/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable int id) {
        userService.deleteUserById(id);
        return ResponseEntity.ok("User deleted successfully.");
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userService.getAllUsers(); // Fetch all users from the service layer
        return ResponseEntity.ok(users); // Return the list of users with a 200 OK status
    }

    //get email
    //COULD USE "/users/{email} because If your controller has other mappings (e.g., "/register", "/login"), Spring might confuse those routes with the "/{email}" mapping, causing ambiguous behavior.
    @GetMapping("/users/{email}")
    public ResponseEntity<User> getUserByEmail(@PathVariable("email") String email) {
        User user = userService.findUserByEmail(email); // Assuming UserService has a method to find user by ID
        if (user != null) {
            return ResponseEntity.ok(user); // Return the user if found
        } else {
            return ResponseEntity.notFound().build(); // Return 404 if user not found
        }
    }
}

