package com.ispc.gymapp.model;

import java.time.LocalDate;
import java.util.Date;
import java.util.UUID;

public class User {
    private String name;
    private String lastname;
    private String mail;
    private String password;
    private Integer age;
    private Double weight;
    private Double weightGoal;
    private Double height;
    private Role role;
    private Double IMC;
    private Date createdAt;
    private Character genre;

    public User() {
    }

    public User(String name, String lastname, String mail, String password, Integer age, Double weight, Double weightGoal, Double height, Role role, Double IMC, Date createdAt, Character genre) {
        this.name = name;
        this.lastname = lastname;
        this.mail = mail;
        this.password = password;
        this.age = age;
        this.weight = weight;
        this.weightGoal = weightGoal;
        this.height = height;
        this.role = role;
        this.IMC = IMC;
        this.createdAt = createdAt;
        this.genre = genre;
    }

    public User(String name, String mail, String password, Double weight, Double weightGoal, Date createdAt,Role role) {
        this.name = name;
        this.mail = mail;
        this.password = password;
        this.weight = weight;
        this.weightGoal = weightGoal;
        this.createdAt = createdAt;
        this.role = role;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    public Double getWeightGoal() {
        return weightGoal;
    }

    public void setWeightGoal(Double weightGoal) {
        this.weightGoal = weightGoal;
    }

    public Double getHeight() {
        return height;
    }

    public void setHeight(Double height) {
        this.height = height;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public Double getIMC() {
        return IMC;
    }

    public void setIMC(Double IMC) {
        this.IMC = IMC;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Character getGenre() {
        return genre;
    }

    public void setGenre(Character genre) {
        this.genre = genre;
    }

    @Override
    public String toString() {
        return "User{" +
                ", name='" + name + '\'' +
                ", lastname='" + lastname + '\'' +
                ", mail='" + mail + '\'' +
                ", password='" + password + '\'' +
                ", age=" + age +
                ", weight=" + weight +
                ", weightGoal=" + weightGoal +
                ", height=" + height +
                ", role=" + role +
                ", IMC=" + IMC +
                ", createdAt=" + createdAt +
                ", genre=" + genre +
                '}';
    }
}
