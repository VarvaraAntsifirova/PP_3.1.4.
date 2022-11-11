package ru.kata.spring.boot_security.demo.model;

import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;

@Entity
@Table(name="roles")
public class Role implements GrantedAuthority {

    @Id
    @Column(name="id")
    private int id;

    @Column(name="role")
    private String role;

    public Role() {
    }

    public Role(String role) {
        this.role = role;
    }

    @Override
    public String getAuthority() {
        return role;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return role;
    }
/*    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }*/
}
