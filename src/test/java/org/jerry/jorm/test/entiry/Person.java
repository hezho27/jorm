package org.jerry.jorm.test.entiry;

import org.jerry.jorm.annotation.Entity;
import org.jerry.jorm.annotation.Id;
import org.jerry.jorm.annotation.Table;

/**
 * Created by yong_pliang on 14/10/31.
 */
@Entity
@Table(name = "t_person")
public class Person {
    public enum Gender {
        female, male
    }

    private String id;
    private Gender gender = Gender.female;
    private String name;
    private Integer age;
    private String address;

    @Id
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

}