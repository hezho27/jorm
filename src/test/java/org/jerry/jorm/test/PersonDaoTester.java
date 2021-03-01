package org.jerry.jorm.test;

import org.jerry.jorm.Expression;
import org.jerry.jorm.Filter;
import org.jerry.jorm.Order;
import org.jerry.jorm.test.dao.PersonDao;
import org.jerry.jorm.test.entiry.Person;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by yong_pliang on 14/10/31.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext.xml")
@TransactionConfiguration(defaultRollback = false)
@Transactional
public class PersonDaoTester {
    @Resource
    private PersonDao personDao;

    /**
     * 初始化测试数据
     */
    @Test
    public void test1() {
        Person person1 = new Person();
        person1.setId("1");
        person1.setGender(Person.Gender.female);
        person1.setName("姓名1");
        person1.setAge(10);
        personDao.save(person1);

        Person person2 = new Person();
        person2.setId("2");
        person2.setGender(Person.Gender.male);
        person2.setName("姓名2");
        person2.setAge(10);
        personDao.save(person2);

        Person person3 = new Person();
        person3.setId("3");
        person3.setGender(Person.Gender.female);
        person3.setName("姓名3");
        person3.setAge(20);
        personDao.save(person3);

        Person person4 = new Person();
        person4.setId("4");
        person4.setGender(Person.Gender.male);
        person4.setName("姓名4");
        person4.setAge(40);
        personDao.save(person4);

        Person person5 = new Person();
        person5.setId("5");
        person5.setGender(Person.Gender.female);
        person5.setName("姓名5");
        person5.setAge(50);
        personDao.save(person5);

        Person person6 = new Person();
        person6.setId("6");
        person6.setGender(Person.Gender.male);
        person6.setName("姓名6");
        person6.setAge(60);
        personDao.save(person6);

        Person person7 = new Person();
        person7.setId("7");
        person7.setGender(Person.Gender.female);
        person7.setName("姓名7");
        person7.setAge(70);
        personDao.save(person7);

        Person person8 = new Person();
        person8.setId("8");
        person8.setGender(Person.Gender.male);
        person8.setName("姓名8");
        person8.setAge(80);
        personDao.save(person8);


        Person person9 = new Person();
        person9.setId("9");
        person9.setGender(Person.Gender.female);
        person9.setName("姓名9");
        person9.setAge(90);
        personDao.save(person9);
    }


    @Test
    public void test2() {
        Person person = personDao.find("1");
        System.out.println(person);
    }

    @Test
    public void test3() {
        List<Filter> filters = new ArrayList<Filter>();
        filters.add(Filter.eq("name", "zhangsan"));
        List<Person> persons = personDao.findList(filters);
        System.out.println(persons);
    }

    @Test
    public void test4() {
        List<Person> persons = personDao.findList((Expression) null, Order.asc("id"));
        System.out.println(persons);
    }

    @Test
    public void test5() {
        List<Filter> filters = new ArrayList<Filter>();
        filters.add(Filter.eq("name", "zhangsan"));
        long count = personDao.count(filters.toArray(new Filter[]{}));
        System.out.println(count);
    }


    @Test
    public void test6() {

        Person person = personDao.find("1");
        person.setGender(Person.Gender.male);
        personDao.update(person, true);

    }

    @Test
    public void test07() {
        Person person = personDao.findOne(Filter.eq("id", "1").or(Filter.eq("id", "3")));
        System.out.println(person);
    }


}
