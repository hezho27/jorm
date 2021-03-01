package org.jerry.jorm.test;

import org.jerry.jorm.Expression;
import org.jerry.jorm.Filter;
import org.jerry.jorm.GridJson;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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


    /**
     * 根据id查找
     */
    @Test
    public void test2() {
        Person person = personDao.find("1");
        System.out.println(person);
    }

    /**
     * 根据字段过滤,关于Filter的更多用法请阅读Filter类
     */
    @Test
    public void test3() {
        List<Filter> filters = new ArrayList<Filter>();
        filters.add(Filter.eq("gender", Person.Gender.female));//过滤性别为女
        filters.add(Filter.ge("age", 50));//过滤年龄大于等于50
        List<Person> persons = personDao.findList(filters, Order.asc("age"));//过滤查询,并按age排序
        System.out.println(persons);
    }

    /**
     * 查询满足条件的数量
     */
    @Test
    public void test4() {
        List<Filter> filters = new ArrayList<Filter>();
        filters.add(Filter.eq("gender", Person.Gender.female));//过滤性别为女
        filters.add(Filter.ge("age", 50));//过滤年龄大于等于50
        long count = personDao.count(filters.toArray(new Filter[]{}));
        System.out.println(count);
    }

    /**
     * 查询所有,并按id排序
     */
    @Test
    public void test5() {
        List<Person> persons = personDao.findList((Expression) null, Order.asc("id"));
        System.out.println(persons);
    }


    /**
     * 修改person记录
     */
    @Test
    public void test6() {
        Person person = personDao.find("1");
        person.setGender(Person.Gender.male);
        personDao.update(person, true);//第二个参数表示是否忽略person中的null字段

    }

    /**
     * 根据条件修改person的部分字段
     */

    @Test
    public void test07() {
        Map set = new HashMap();
        set.put("address", "上海");
        set.put("age", 15);
        personDao.update(set, Filter.eq("id", "1"));
    }

    /**
     * 分页查询
     */

    @Test
    public void test08() {
        GridJson gridJson = personDao.page(1, 2, (List<Filter>) null, "id", "asc");
        System.out.println(gridJson);
    }

    /**
     * 查询单个结果,若果结果数大于1,则跑出NotOneResultException异常
     */
    @Test
    public void test09() {
        Person person = personDao.findOne(Filter.eq("id", "1"));
        System.out.println(person);
    }

    /**
     * Expression表达式的使用
     */
    @Test
    public void test10() {
        // 对于Filter的集合,即List<Filter>而言,list中的所有filter之间都是and关系,
        // 如果我们具有or或则not关系时,就应该使用Expression表达式,
        // 所有用List<Filter>的参数api都有对应的Expression表达式api,
        // 现在我们要构建一个过滤表达式:gender=female and (age<20 or age>50) and name!='姓名5'
        Expression expression = Filter.eq("gender", Person.Gender.female)
                .and(Filter.lt("age", 20).or(Filter.lt("age", 50)).parentheses()) //parentheses()方法为给当前表达式加括号
                .and(Filter.ne("name", "姓名5"));
        List<Person> persons = personDao.findList(expression);
        System.out.println(persons);
    }


}
