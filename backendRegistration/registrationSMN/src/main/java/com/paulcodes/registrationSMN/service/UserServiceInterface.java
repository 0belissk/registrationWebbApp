package com.paulcodes.registrationSMN.service;

import com.paulcodes.registrationSMN.dto.UserRegistrationDto;
import com.paulcodes.registrationSMN.model.User;

import java.util.List;

public interface UserServiceInterface {
    //Registers a new User
    void registerUser(UserRegistrationDto userDto);

    //Checks if an email is already registered
    boolean isEmailAlreadyInUse(String email);

    //find all users
    List<User> getAllUsers(); // Method to get all users

    //find user by id
    User findUserById(int id);

    //find user by email
    User findUserByEmail(String email);

    User saveUser(User user);
    //Sends a verification email after registration
    //void sendVerificationEmail(User user);

    //Verifies the token received from an email link
    //boolean verifyUserToken(String token);

    User getUserById(int id);

    void deleteUserById(int id);
}
