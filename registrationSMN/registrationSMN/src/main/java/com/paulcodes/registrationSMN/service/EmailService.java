package com.paulcodes.registrationSMN.service;

import com.paulcodes.registrationSMN.dto.UserRegistrationDto;
import com.paulcodes.registrationSMN.util.TokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;


@Service
public class EmailService implements EmailServiceInterface {

    @Autowired
    private JavaMailSender mailSender; // Autowires JavaMailSender for sending emails

    // Front-end URL for verification
    private final String appUrl = "https://registration-802d1.web.app";

    @Override
    public void sendVerificationEmail(UserRegistrationDto userDto) {
        // Create the verification URL
        String token = TokenUtil.generateVerificationToken(userDto);
        String verificationUrl = appUrl + "/front-verify?token=" + token;

        // Build the email message with clickable text
        String emailContent = "<p>Dear " + userDto.getFirstName() + ",</p>"
                + "<p>Thank you for registering. Please click the link below to verify your account:</p>"
                + "<p><a href=\"" + verificationUrl + "\">Click here to verify your email address</a></p>"
                + "<p>If you did not register, please ignore this email.</p>"
                + "<p>Best regards,<br>The Team</p>";

        // Create a MIME email message for HTML content
        MimeMessage message = mailSender.createMimeMessage();

        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
            helper.setTo(userDto.getEmail()); // Recipient's email address
            helper.setSubject("Verify Your Account"); // Subject of the email
            helper.setText(emailContent, true); // `true` specifies that this is an HTML email

            // Send the email
            mailSender.send(message);
        } catch (MessagingException | MailException e) {
            System.err.println("Failed to send email: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
