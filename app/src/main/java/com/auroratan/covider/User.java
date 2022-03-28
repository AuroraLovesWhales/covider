package com.auroratan.covider;

public class User {
    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private boolean isInstructor;

    public User() {

    }

    public User(String email, String password, String firstName, String lastName, boolean isInstructor) {
        this.email = email;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.isInstructor = isInstructor;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public boolean getIsInstructor() {
        return isInstructor;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setInstructor(boolean instructor) {
        isInstructor = instructor;
    }
}
