package com.example.marketingcosmetics.models;

import java.io.Serializable;

public class User implements Serializable {
    private int id;
    private String username;
    private String fullname;
    private String email;
    private String phone;
    private String role;
    private double point;

    // Constructor, Getter và Setter
    public User(int id, String username, String fullname, String email, String phone, String role, double point) {
        this.id = id;
        this.username = username;
        this.fullname = fullname;
        this.email = email;
        this.phone = phone;
        this.role = role;
        this.point = point;
    }

    public int getId() { return id; }
    public String getFullname() { return fullname; }
    public void setFullname(String fullname) { this.fullname = fullname; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }
    public double getPoint() { return point; }
}