package project.model;


import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;
import java.util.Set;

@Entity
@Table(name="Users")
//@JsonIdentityInfo(generator=ObjectIdGenerators.PropertyGenerator.class, property="id")
public class User implements Serializable {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="Id",nullable = false)
    private int id;

    @Column(name="name",nullable = false,unique = true)
    private String name;

    @Column(name="password",nullable = false)
    private String password;

    @Column(name="enabled",columnDefinition = "boolean default true")
    private boolean enabled;

    @ManyToOne
    @JoinColumn(name = "role",nullable=false)
    private Role role;

    @OneToMany(mappedBy = "userP")
//    @JsonIgnore
    private List<ProductUser> usersP;


    @OneToMany(mappedBy = "userS")
//    @JsonIgnore
    private List<ServUser> usersS;

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

    public List<ProductUser> getUsersP() {
        return usersP;
    }

    public void setUsersP(List<ProductUser> usersP) {
        this.usersP = usersP;
    }

    public List<ServUser> getUsersS() {
        return usersS;
    }

    public void setUsersS(List<ServUser> usersS) {
        this.usersS = usersS;
    }
}
