package com.realraghavgupta.foodies;

/**
 * Created by Collins on 14/03/2018.
 */

public class User {
    String firstName, lastName, email, password;

    public User() {
    }

    public User(String firstName, String lastName, String email, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;

    }

    public String getFirstName() {
        return this.firstName;
    }

    public String getLastName() {
        return this.lastName;
    }

    public String getEmail() {
        return this.email;

    }

    public String getPassword() {
        return this.password;

    }

}
