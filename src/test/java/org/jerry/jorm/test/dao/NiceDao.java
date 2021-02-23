package org.jerry.jorm.test.dao;

import org.jerry.jorm.BasicDaoImpl;
import org.jerry.jorm.test.entiry.Nice;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCallback;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Jerry.Yong on 2014/11/3.
 */
@Repository
public class NiceDao extends BasicDaoImpl<Nice, String> {



}
