package com.paulcodes.registrationSMN.model;

import jakarta.persistence.*;

@Entity // Entity annotation lets Spring applications interact with databases easily and efficiently
public class User {

    // All variables are columns in the table
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String email;
    private String firstName;
    private String lastName;




    private boolean enabled;

    // Getters and Setters

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }


    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    
    /**
     * Activates the user by enabling their account after verification.
     */
    public void activateUser() {
        this.enabled = true;
    }
}
