package org.jerry.jorm.test;

import org.jerry.jorm.Filter;
import org.jerry.jorm.test.dao.NiceDao;
import org.jerry.jorm.test.entiry.Nice;
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
 * Created by Jerry.Yong on 2014/11/3.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext.xml")
@TransactionConfiguration(defaultRollback = false)
@Transactional
public class NiceDaoTester {
    @Resource
    private NiceDao niceDao;

    @Test
    public void testFind(){
        Nice nice = new Nice();
        nice.setSayId("446dd43e29a84ff78f3af4552ff4cf09");
        nice.setUserId("testUser3");
        List<Filter> filters = new ArrayList<Filter>();
        filters.add(Filter.eq("userId", nice.getUserId()));
        filters.add(Filter.eq("sayId", nice.getSayId()));
        List oldNice = niceDao.findList(filters);
    }

    @Test
    public void testddl(){

    }

}
