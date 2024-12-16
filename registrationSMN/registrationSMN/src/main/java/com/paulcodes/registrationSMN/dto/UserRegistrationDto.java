package com.paulcodes.registrationSMN.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

//using DTO provides a structured way to handle registration data separately from your entity, enforcing validation rules and keeping your User entity clean from unnecessary validation logic.

// @Getter generates getter methods for all fields in the class, allowing access to private variables
@Getter
// @Setter generates setter methods for all fields in the class, enabling modification of private variables
@Setter
// @NoArgsConstructor generates a no-argument constructor, allowing creation of an object without setting initial field values
@NoArgsConstructor
// @AllArgsConstructor generates a constructor with arguments for all fields, allowing initialization of all variables upon object creation
@AllArgsConstructor
public class UserRegistrationDto {
    //In the UserRegistrationDto, you’ll only need to include the fields that the user is expected to provide during registration

    //Custom Validation:
    //If you need to enforce specific formats (e.g., email ending with @university.edu), you can create a custom validation annotation.

    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
    //[a-zA-Z0-9._%+-]: A character class that allows:
    //Letters (a-z, A-Z)
    //Digits (0-9)
    //Special characters (., _, %, +, and -)
    @Pattern(regexp = "^[a-zA-Z0-9._%+-]+@uic\\.edu$", message = "Email must be a UIC email (e.g., name@uic.edu)")
    private String email;

    @NotBlank(message = "First name is required")
    private String firstName;

    @NotBlank(message = "Last name is required")
    private String lastName;

}
