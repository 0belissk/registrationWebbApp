package com.paulcodes.registrationSMN.util;

import java.util.UUID;

import com.paulcodes.registrationSMN.dto.UserRegistrationDto;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;
import java.util.Date;

public class TokenUtil {
    //Dynamically generates a secret key for signing JWTs using the HMAC SHA-256 (HS256) algorithm.
    //HS256 is a symmetric algorithm, meaning the same key is used for both signing and verifying the JWT
    //The server uses this key to verify that a received JWT was created by the server and has not been modified.
    private static final SecretKey SECRET_KEY = Keys.secretKeyFor(SignatureAlgorithm.HS256);

    public static String generateVerificationToken(UserRegistrationDto userDto) {
        // Define the expiration time for the token in milliseconds (24 hours in this case)
        long expirationTime = 1000 * 60 * 60 * 24; // 24 hours

        // Build and return a JSON Web Token (JWT)
        return Jwts.builder()
                // Set the subject of the token (usually a unique identifier like the user's email)
                .setSubject(userDto.getEmail())
                // Add custom claims (key-value pairs) to the token
                // These claims store additional user information
                .claim("firstName", userDto.getFirstName()) // Store the user's first name
                .claim("lastName", userDto.getLastName()) // Store the user's last name
                .setExpiration(new Date(System.currentTimeMillis() + expirationTime))

                // Sign the token using a secret key (for security and integrity)
                // SECRET_KEY is a cryptographic key used to sign and later validate the token
                .signWith(SECRET_KEY)
                // Compact the token into a URL-safe string format
                .compact();
    }

    public static Claims validateToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(SECRET_KEY)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    //creates random unique token
    public static String generateToken() {
        return UUID.randomUUID().toString();
    }
}
