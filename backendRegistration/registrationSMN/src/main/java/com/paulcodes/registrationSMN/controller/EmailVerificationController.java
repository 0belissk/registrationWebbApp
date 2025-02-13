package com.paulcodes.registrationSMN.controller;

import com.paulcodes.registrationSMN.model.User;
import com.paulcodes.registrationSMN.repository.UserRepository;
import com.paulcodes.registrationSMN.service.EmailService;
import com.paulcodes.registrationSMN.service.UserServiceInterface;
import com.paulcodes.registrationSMN.util.TokenUtil;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/verify")
public class EmailVerificationController {



    @Autowired
    private UserServiceInterface userService;


    @Autowired
    private UserRepository userRepository;

    /**
     * Verifies the email using the provided token.
     *
     * @param token The verification token sent to the user's email.
     * @return Response indicating success or failure of verification.
     */

    //the user clicks a link that sends them to the front end firebase url, and in the front end logic for that front end screen route "/verify",
    //it used the backend url with the endpoint in its fetch, but now the backend local host is not running so when the user clicks the url it dies as there is not backend logic to handle it
    //the solution to this is to redeploy the front end with the url with the ec2 instance into the fetch logic
    @GetMapping
    //@RequestParam("token") creates GET /verify?token={token}, @RequestParam("token") maps the query parameter token from the URL to the String token method argument
    public ResponseEntity<String> verifyEmail(@RequestParam("token") String token, @RequestHeader(value = "Origin", required = false) String origin) {
        System.out.println("Received token: " + token); // Log the token
        System.out.println("Origin: " + origin);

        if (token == null || token.isEmpty()) {
            return ResponseEntity.badRequest().body("Token is missing or invalid.");
        }

        try {
            // Validate the token and extract user details
            System.out.println("Validating token...");
            Claims claims = TokenUtil.validateToken(token);
            String email = claims.getSubject();
            String firstName = claims.get("firstName", String.class);
            String lastName = claims.get("lastName", String.class);

            // Check if the user already exists (shouldn't happen if properly implemented)
            if (userService.isEmailAlreadyInUse(email)) {
                return ResponseEntity.ok("Email already verified.");
            }

            // Create and save the user in the database
            User user = new User();
            user.setEmail(email);
            user.setFirstName(firstName);
            user.setLastName(lastName);
            user.setEnabled(true);

            try {
                userRepository.save(user); // Attempt to save the user
            } catch (DataIntegrityViolationException e) {
                System.err.println("Data integrity violation: " + e.getMessage());
                return ResponseEntity.status(HttpStatus.CONFLICT).body("Email already verified. Please log in.");
            }

            return ResponseEntity.ok("Email verified and user registered successfully!");

        } catch (Exception e) {
            System.err.println("Unexpected error occurred: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred.");
        }
    }

    /**
     * Resends the verification email to the user.
     *
     * @param email The email address of the user.
     * @return Response indicating success or failure of resending the email.
     */
    @PostMapping("/resend")
    //@RequestParam("email") makes POST /verify/resend?email={email}
    public ResponseEntity<String> resendVerificationEmail(@RequestParam("email") String email) {
        try {

            return ResponseEntity.ok("Verification email resent successfully!");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
