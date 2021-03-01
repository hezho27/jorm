package org.jerry.jorm.test.entiry;

import org.jerry.jorm.annotation.Id;

import java.util.Date;

/**
 * Created by yong_pliang on 14/11/24.
 */
public class BaseEntity {
    private String id;
    private Date createTime;
    private Date updateTime;

    @Id
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}
