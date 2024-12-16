package com.paulcodes.registrationSMN.service;

import com.paulcodes.registrationSMN.dto.UserRegistrationDto;
import com.paulcodes.registrationSMN.model.User;

import com.paulcodes.registrationSMN.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service //used to mark a class as a service, essentially indicating that it encapsulates business logic and functionality that can be injected and used by other parts of your application
public class UserService implements UserServiceInterface {

    // Inject the UserRepository dependency to handle database operations for User entities
    @Autowired
    private UserRepository userRepository;


    @Autowired //Autowired instructs Spring to provide an instance of that class (a Spring-managed bean) into your class
    //EmailService is likely a Spring component (e.g., annotated with @Service, @Component, or @Bean) that you want to use in another class
    private EmailService emailService;


    // Inject the EmailService dependency to handle email operations
    //@Autowired
    //private EmailService emailService;
    @Override
    public User saveUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public void registerUser(UserRegistrationDto userDto) {
        // Check if the email is already in use by another user (only one registration per email)
        if (isEmailAlreadyInUse(userDto.getEmail())) {
            // If email is already in use, throw an exception with a relevant message
            throw new IllegalArgumentException("Email is already in use");
        }

        emailService.sendVerificationEmail(userDto);

    }

    /**
     * Checks if the given email is already registered in the system.
     *
     * @param email Email to check
     * @return true if the email is already in use, false otherwise
     */
    @Override
    public boolean isEmailAlreadyInUse(String email) {
        // Check if the email exists in the database
        return userRepository.findByEmail(email).isPresent();
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }


    @Override
    public User findUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("User not found with email: " + email));
    }

    @Override
    public User findUserById(int id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("User not found with ID: " + id));
    }

    @Override
    public User getUserById(int id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
    }

    @Override
    public void deleteUserById(int id) {
        userRepository.deleteById(id);
    }


}
