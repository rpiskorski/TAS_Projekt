package project.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Calendar;
import java.util.TimeZone;

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
    @JsonIgnoreProperties({"name","owner_name","localization","raiting","num","cat","servUsers"})
    private Serv service;

    @ManyToOne
    @JoinColumn(name="user_id" ,nullable = false)
    @JsonIgnoreProperties({"id","password","enabled","role","usersP","usersS",""})
    private User userS;


    public ServUser(){
        this.timestamp = Calendar.getInstance(TimeZone.getTimeZone("Europe/Warsaw")).getTimeInMillis();
    }

    public ServUser(ServUser servUser){
        this.timestamp = Calendar.getInstance(TimeZone.getTimeZone("Europe/Warsaw")).getTimeInMillis();
        this.comment = servUser.getComment();
        this.rating = servUser.getRating();
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