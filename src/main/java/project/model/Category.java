package project.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

@Entity
@Table(name = "Category")

public class Category implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name ="Name")
    private String name;

    @OneToMany(mappedBy = "cat")
    @JsonIgnore
    private Set<Product> products;

    @OneToMany(mappedBy = "cat")
    @JsonIgnore
    private Set<Serv> services;


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

    public Set<Product> getProducts() {
        return products;
    }

    public void setProducts(Set<Product> products) {
        this.products = products;
    }

    public Set<Serv> getServices() {
        return services;
    }

    public void setServices(Set<Serv> services) {
        this.services = services;
    }
}
