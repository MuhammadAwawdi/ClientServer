package org.example.entities;

import net.bytebuddy.dynamic.loading.InjectionClassLoader;

import javax.persistence.*;
import java.io.Serializable;

@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@Entity
@Table
public class User implements Serializable {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;
    protected String username;
    protected String password;

    public User() {};
    public User(String username, String password){
        this.username = username;
        this.password=password;
    }

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
