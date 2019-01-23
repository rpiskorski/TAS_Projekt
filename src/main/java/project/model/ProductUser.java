package project.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.TimeZone;

@Entity
@Table(name="ProductUser")
//@JsonIdentityInfo(generator=ObjectIdGenerators.PropertyGenerator.class, property="id")
public class ProductUser implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID",nullable = false)
    private int id;

    @Column(name="rating",columnDefinition = "int default 3")
    private int rating;

    @Column(name="comment")
    private String comment;

    @Column(name="date")
    private long timestamp;

    @ManyToOne
    @JoinColumn(name="product_id" ,nullable = false)
//    @JsonIgnore
    @JsonIgnoreProperties({"name","manufacturer_name","raiting","num","cat"})
    private Product product;

    @ManyToOne
    @JoinColumn(name="user_id" ,nullable = false)
 //   @JsonIgnore
    @JsonIgnoreProperties({"id","password","enabled","role","usersP","usersS"})
    private User userP;

    public ProductUser(){
        this.timestamp = Calendar.getInstance(TimeZone.getTimeZone("Europe/Warsaw")).getTimeInMillis();
    }

    public ProductUser(ProductUser pu){
        this.timestamp = Calendar.getInstance(TimeZone.getTimeZone("Europe/Warsaw")).getTimeInMillis();
        this.rating = pu.getRating();
        this.comment = pu.getComment();
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public User getUserP() {
        return userP;
    }

    public void setUserP(User userP) {
        this.userP = userP;
    }


    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }
}
