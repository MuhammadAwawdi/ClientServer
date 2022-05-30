package org.example.entities;

import javax.persistence.*;

@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@Entity
public class User {
    @Id
    @Column(name = "id", nullable = false)
    protected Long id;
    protected String username;
    protected String password;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
        this.password = password;
    }
}
