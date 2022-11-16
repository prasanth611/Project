package com.example.springbootcrud.model;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "name")
    private String name;

    @Column
    private short is_active = 1;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    @Column
    private String role;

    @Column
    private Date created_at = new Date();

    @Column
    private Date last_updated_at = new Date();

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public short getIs_active() {
        return is_active;
    }

    public void setIs_active(short is_active) {
        this.is_active = is_active;
    }

    public Date getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Date created_at) {
        this.created_at = created_at;
    }

    public Date getLast_updated_at() {
        return last_updated_at;
    }

    public void setLast_updated_at(Date last_updated_at) {
        this.last_updated_at = last_updated_at;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", is_active=" + is_active +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", role='" + role + '\'' +
                ", created_at=" + created_at +
                ", last_updated_at=" + last_updated_at +
                '}';
    }
}

