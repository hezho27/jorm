package org.jerry.jorm.test.entiry;

import org.jerry.jorm.annotation.Entity;
import org.jerry.jorm.annotation.Id;
import org.jerry.jorm.annotation.Table;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by Jerry.Yong on 2014/10/11.
 */
@Entity
@Table(name = "t_message")
public class Message  implements Serializable {
    private String id;
    private String userId;
    private String sayId;
    private Boolean hasRead = false;
    private String message;
    private Date date =new Date();

    @Id
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getSayId() {
        return sayId;
    }

    public void setSayId(String sayId) {
        this.sayId = sayId;
    }

    public Boolean getHasRead() {
        return hasRead;
    }

    public void setHasRead(Boolean hasRead) {
        this.hasRead = hasRead;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
