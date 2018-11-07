package project.model;


import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name="Users")
public class User implements Serializable {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="Id",nullable = false)
    private int id;

    @Column(name="name",nullable = false,unique = true)
    private String name;

    @Column(name="password",nullable = false)
    private String password;

    @Column(name="enabled",nullable = false,columnDefinition = "boolean default true")
    private boolean enabled;

    @ManyToOne
    @JoinColumn(name = "role",nullable=false)
    private Role role;

    public User(){

    }

    public User(User user){
        this.enabled=user.isEnabled();
        this.id = user.getId();
        this.name = user.getName();
        this.password = user.getPassword();
        this.role = user.getRole();

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
}
