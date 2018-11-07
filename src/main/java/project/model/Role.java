package project.model;


import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

@Entity
@Table(name="Roles")
public class Role implements Serializable {

//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Column(name = "ID",nullable = false)
//    private int id;

    @Id
    @Column(name="name",nullable = false)
    private String name;

    @OneToMany(mappedBy = "role")
    @JsonIgnore
    private Set<User> user;

//    public Role(){
//        this.id=2;
//        this.role_name = "USER";
//    }


//    public int getId() {
//        return id;
//    }
//
//    public void setId(int id) {
//        this.id = id;
//    }

    public String getRolename() {
        return name;
    }

    public void setRolename(String name) {
        this.name = name;
    }
}
