package org.jerry.jorm.test.entiry;


import org.jerry.jorm.annotation.Entity;
import org.jerry.jorm.annotation.Id;
import org.jerry.jorm.annotation.Table;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "t_say")
public class Say implements Serializable {
    public enum Type {
        work, life
    }

    private String id;
    private String userId;
    private Date createTime;
    private Date lastCommentDatetime;
    private String content;
    private Type type = Type.work;
    private Boolean delf = false;


    private List<List<Comment>> comments = new ArrayList<List<Comment>>();
    private List<String> niceUserIds = new ArrayList<String>();


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

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public List<List<Comment>> getComments() {
        return comments;
    }

    public void setComments(List<List<Comment>> comments) {
        this.comments = comments;
    }

    public List<String> getNiceUserIds() {
        return niceUserIds;
    }

    public void setNiceUserIds(List<String> niceUserIds) {
        this.niceUserIds = niceUserIds;
    }

    public Date getLastCommentDatetime() {
        return lastCommentDatetime;
    }

    public void setLastCommentDatetime(Date lastCommentDatetime) {
        this.lastCommentDatetime = lastCommentDatetime;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public Boolean getDelf() {
        return delf;
    }

    public void setDelf(Boolean delf) {
        this.delf = delf;
    }

    public void addNice(Nice nice) {
        this.niceUserIds.add(nice.getUserId());
    }

    public void addComment(List<Comment> comments) {
        this.comments.add(comments);
    }

}
