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
@Table(name = "t_nice")
public class Nice  implements Serializable {
    private String id;
    private String userId;
    private String sayId;
    private Date operateTime = new Date();

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

    public Date getOperateTime() {
        return operateTime;
    }

    public void setOperateTime(Date operateTime) {
        this.operateTime = operateTime;
    }
}
