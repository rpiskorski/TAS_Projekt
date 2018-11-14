package project.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;
import java.util.Set;

@Entity
@Table(name="Services")
public class Serv implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID",nullable = false)
    private int id;

    @Column(name = "Name",nullable = false)
    private String name;

    @Column(name = "Owner_name",nullable = false)
    private String owner_name;

    @Column(name = "Localization",nullable = false)
    private String localization;

    @Column(name = "Avg_rating",columnDefinition = "double default 0.0")
    private double raiting;

    @Column(name = "Num_of_votes",columnDefinition = "int default 0")
    private int num;

    @ManyToOne
    @JoinColumn(name = "Category_ID",nullable = false)
    private Category cat;

    @OneToMany(mappedBy = "service")
//    @JsonIgnore
    private List<ServUser> servUsers;


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

    public String getOwner_name() {
        return owner_name;
    }

    public void setOwner_name(String owner_name) {
        this.owner_name = owner_name;
    }

    public String getLocalization() {
        return localization;
    }

    public void setLocalization(String localization) {
        this.localization = localization;
    }

    public double getRaiting() {
        return raiting;
    }

    public void setRaiting(double raiting) {
        this.raiting = raiting;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public Category getCat() {
        return cat;
    }

    public void setCat(Category cat) {
        this.cat = cat;
    }

    public List<ServUser> getServUsers() {
        return servUsers;
    }

    public void setServUsers(List<ServUser> servUsers) {
        this.servUsers = servUsers;
    }
}

