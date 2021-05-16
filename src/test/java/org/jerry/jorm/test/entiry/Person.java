package org.jerry.jorm.test.entiry;

import org.jerry.jorm.annotation.*;

import java.util.Date;

/**
 * Created by yong_pliang on 14/10/31.
 */
@Entity
@Table(name = "t_person")
public class Person extends BaseEntity {
    public enum Gender {
        female, male
    }

    private Gender gender = Gender.female;
    private String name;
    private Integer age;
    private String address;
    private Date birthday;



    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    @Column(length = 40)
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

    @Lob
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Temporal(TemporalType.DATE)
    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    @Override
    public String toString() {
        return "Person{" +
                "gender=" + gender +
                ", name='" + name + '\'' +
                ", age=" + age +
                ", address='" + address + '\'' +
                ", birthday=" + birthday +
                '}';
    }
}
