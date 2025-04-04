package com.FitnessTrackingApp.services;


import java.util.regex.Pattern;

public class ThisUser {
    private static ThisUser instance;
    private String username;
    private String password;

    public ThisUser() {
	this.username = null;
	this.password = null;
    }
    
    public ThisUser(String username, String password) {
	this.username = username;
	this.password = password;
    }

    public static ThisUser getInstance() {
        if (instance == null) {
            instance = new ThisUser();
        }
        return instance;
    }

    public String getCurrentUsername() {
        return username;
    }
    public void setCurrentUsername(String username) {
        this.username = username;
    }
    public String getCurrentPassword() {
        return password;
    }
    public void setCurrentPassword(String password) {
        if(password == null || password.isEmpty()) {
            throw new IllegalArgumentException("Password cannot be null or empty");
        }
        if(isValidPassword(password)){
            this.password = password;
        } else {
            System.out.println("Invalid password. Password requirement: " +
                    "8-16 characters, at least 1 uppercase, at least 1 lowercase");
            throw new IllegalArgumentException("Invalid password");
        }
    }
    public String clearCurrentUser(){
        return this.username = null;
    }

    private boolean isValidPassword(String password) {
        if (password.length() < 8 || password.length() > 16) {
            return false;
        }

        boolean hasUppercase = Pattern.compile("[A-Z]").matcher(password).find();
        boolean hasLowercase = Pattern.compile("[a-z]").matcher(password).find();

        return hasUppercase && hasLowercase;
    }

}