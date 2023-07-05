package com.example.cardiocare;

/**
 * This class represents a user profile.
 *
 * @author George
 */
public class UserProfile {
    public String name, email, number;

    /**
     * Creates a new user profile object.
     */
    public UserProfile() {
    }

    /**
     * Creates a new user profile object with the specified name, email address, and phone number.
     *
     * @param name   The user's name.
     * @param email  The user's email address.
     * @param number The user's phone number.
     */
    public UserProfile(String name, String email, String number) {
        this.name = name;
        this.email = email;
        this.number = number;
    }

}
