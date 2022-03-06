package main.repo;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;

/**
 * User entity:
 *  hold massage date: id,msg,userName
 */

@Entity
public class Massage {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @NotBlank(message = "Massage is mandatory not empty")
    private String msg;
    private String userName;

    public Massage() {}

    public Massage(String massage) {
        this.msg = massage;
        this.userName = userName;
    }

    public void setMsg(String massage) {
        this.msg = massage;
    }

    public String getMsg() {
        return msg;
    }

    public void setUserName(String userName) {this.userName = userName;}

    public String getUserName() { return userName; }

    @Override
    public String toString() {
        return "Massage{" + "id=" + id + ", massage=" + msg + '}';
    }
}
