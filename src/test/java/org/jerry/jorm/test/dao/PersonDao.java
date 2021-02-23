package org.jerry.jorm.test.dao;

import org.jerry.jorm.BasicDaoImpl;
import org.jerry.jorm.test.entiry.Person;
import org.springframework.stereotype.Repository;

/**
 * Created by yong_pliang on 14/10/31.
 */
@Repository
public class PersonDao extends BasicDaoImpl<Person,String> {
}
