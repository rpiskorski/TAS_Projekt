package project.model;


import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name ="Products")

public class Product implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID",nullable = false)
    private int id;

    @Column(name = "Name",nullable = false)
    private String name;

    @Column(name = "Manufacturer_name",nullable = false)
    private String manufacturer_name;

    @Column(name = "Avg_rating",columnDefinition = "double default 0.0")
    private double raiting;

    @Column(name = "Num_of_votes",columnDefinition = "int default 0")
    private int num;

    @ManyToOne
    @JoinColumn(name = "Category_ID",nullable = false)
    private Category cat;


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

    public String getManufacturer_name() {
        return manufacturer_name;
    }

    public void setManufacturer_name(String manufacturer_name) {
        this.manufacturer_name = manufacturer_name;
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
}
