//Handles HTTP requests and responses, interacting with the frontend, works with service, service works with repository, 
package com.paulcodes.registrationSMN.controller;

import com.paulcodes.registrationSMN.dto.UserRegistrationDto;
import com.paulcodes.registrationSMN.service.EmailService;
import com.paulcodes.registrationSMN.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@CrossOrigin //you’ll encounter CORS issues when the frontend tries to make requests to the backend. The @CrossOrigin annotation allows the backend to explicitly permit such requests.
public class RegistrationController {
    @Autowired
    private UserService userService;

    @Autowired
    private EmailService emailService;


    //make sure you make paths ambiguous to avoid confusion and errors in postman
    //ex: /{id} and /{email] both take in an input and have same path ("/{input}") so postman doesn't know which function to use

    //POST /register: Registers a new user by accepting registration data.
    @PostMapping("/register")
    //The UserRegistrationDto allows us to define validation rules
    public ResponseEntity<String> registerUser(@RequestBody UserRegistrationDto userDto) {
        //logger.info("Received registration request for email: {}", userDto.getEmail());
        try {
            userService.registerUser(userDto);

            return ResponseEntity.ok("Registration almost done! Please check your email to verify your account.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


}
