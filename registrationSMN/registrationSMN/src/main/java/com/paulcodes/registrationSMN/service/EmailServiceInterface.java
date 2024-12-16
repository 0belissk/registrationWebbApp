package com.paulcodes.registrationSMN.service;

import com.paulcodes.registrationSMN.dto.UserRegistrationDto;

public interface EmailServiceInterface {

    /**
     * Sends a verification email to the given user with the provided userDto data.
     */
    void sendVerificationEmail(UserRegistrationDto userDto);


}
