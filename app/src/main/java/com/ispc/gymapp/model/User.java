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
    private Integer height;
    private Role role;
    private Double IMC;
    private Date createdAt;
    private String genre;

    public User() {
    }

    public User(String name, String lastname, String mail, String password, Integer age, Double weight, Double weightGoal, Integer height, Role role, Double IMC, Date createdAt, String genre) {
        this(name, mail, password,height, weight, weightGoal,createdAt,role);
        this.lastname = lastname;
        this.age = age;
        this.genre = genre;
    }

    public User(String name, String mail, String password,Integer height, Double weight, Double weightGoal, Date createdAt,Role role) {
        this(weight,height);
        this.name = name;
        this.mail = mail;
        this.password = password;
        this.weightGoal = weightGoal;
        this.createdAt = createdAt;
        this.role = role;

    }
    public User(Double weight,Integer height) {
        this.weight = weight;
        this.height = height;
    }

    public User(String mail, String password,Role role){
        this.mail= mail;
        this.password = password;
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

    public Integer getHeight() {
        return height;
    }

    public void setHeight(Integer height) {
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

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public Double calculateIMC(Integer height,Double weight){
        if(height!=null&&weight!=null){
        Double heightMeters =  height / 100d;
        return (weight / (heightMeters*heightMeters));
        }
        return 0.0;
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
