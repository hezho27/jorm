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

    @Test
    public void test1() {
        Person person = new Person();
        person.setId("1");
        person.setGender(Person.Gender.female);
        person.setName("zhangsan");
        person.setAge(12);
        personDao.save(person);
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
        Person person = personDao.findOne(Filter.eq("id", "1"));
        System.out.println(person);
    }


}
