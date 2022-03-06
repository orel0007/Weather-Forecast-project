package main.repo;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;

/**
 * User entity:
 *  hold user date: id,userName,TimeStamp
 */

@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @NotBlank(message = "Name is mandatory")
    private String userName;
    private long timeStamp;
    public User() {}

    public User(String userName) { this.userName = userName; }

    public void setId(long id) { this.id = id; }

    public long getId() { return id; }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserName() { return this.userName; }

    public void setTime(){this.timeStamp = System.currentTimeMillis();}

    public long getTime(){return this.timeStamp;}

    @Override
    public String toString() {
        return "User{" + "id=" + id + ", userName=" + userName + '}';
    }
}

