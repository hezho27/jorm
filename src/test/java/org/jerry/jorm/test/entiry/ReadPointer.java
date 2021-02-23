package org.jerry.jorm.test.entiry;

import org.jerry.jorm.annotation.Entity;
import org.jerry.jorm.annotation.Id;
import org.jerry.jorm.annotation.Table;

/**
 * Created by Jerry.Yong on 2014/10/30.
 */
@Entity
@Table(name = "t_read_pointer")
public class ReadPointer {
    public enum ResourceType{
        say
    }
    private String id;
    private String userId;
    private String pointer;
    private ResourceType type = ResourceType.say;

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

    public String getPointer() {
        return pointer;
    }

    public void setPointer(String pointer) {
        this.pointer = pointer;
    }

    public ResourceType getType() {
        return type;
    }

    public void setType(ResourceType type) {
        this.type = type;
    }

    public ReadPointer() {
    }

    public ReadPointer(String userId, String pointer, ResourceType type) {
        this.userId = userId;
        this.pointer = pointer;
        this.type = type;
    }

}
