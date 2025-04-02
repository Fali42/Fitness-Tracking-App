package com.FitnessTrackingApp.services;

import java.util.Date;
import java.util.regex.Pattern;

public class Users {
    private String username;
    private String password;
    private String name;
    private Date birthday;
    private String gender;
    private double height;
    private double weight;
    private String fitnessGoals;

    public Users(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public Users(){
        this.username = "";
        this.password = "";
    }

    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        if (!isValidPassword(password)) {
            System.out.println("Invalid password. Password requirement: " +
                    "8-16 characters, at least 1 uppercase, at least 1 lowercase");
            throw new IllegalArgumentException("Invalid password.");
        }
        this.password = password;
    }
    public String getName(String name){
        return name;
    }
    public void setName(String name){
        this.name = name;
    }
    public Date getBirthday() {
        return birthday;
    }
    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }
    public String getGender() {
        return gender;
    }
    public void setGender(String gender) {
        this.gender = gender;
    }
    public double getHeight() {
        return height;
    }
    public void setHeight(double height) {
        this.height = height;
    }
    public double getWeight() {
        return weight;
    }
    public void setWeight(double weight) {
        this.weight = weight;
    }
    public String getFitnessGoals() {
        return fitnessGoals;
    }
    public void setFitnessGoals(String fitnessGoals) {
        this.fitnessGoals = fitnessGoals;
    }

    private boolean isValidPassword(String password) {
        if (password.length() < 8 || password.length() > 16) {
            return false;
        }

        boolean hasUppercase = Pattern.compile("[A-Z]").matcher(password).find();
        boolean hasLowercase = Pattern.compile("[a-z]").matcher(password).find();

        return hasUppercase && hasLowercase;
    }

    @Override
    public String toString() {
        return "Users [username=" + username + ", password=" + password + "]";
    }
}