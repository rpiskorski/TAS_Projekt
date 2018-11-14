package project.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name="ServUser")
public class ServUser implements Serializable {

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
    @JoinColumn(name="service_id" ,nullable = false)
    @JsonIgnore
    private Serv service;

    @ManyToOne
    @JoinColumn(name="user_id" ,nullable = false)
    @JsonIgnore
    private User userS;

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

    public Serv getService() {
        return service;
    }

    public void setService(Serv service) {
        this.service = service;
    }

    public User getUserS() {
        return userS;
    }

    public void setUserS(User userS) {
        this.userS = userS;
    }


    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }
}
