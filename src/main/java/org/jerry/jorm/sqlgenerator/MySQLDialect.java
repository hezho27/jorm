package org.jerry.jorm.sqlgenerator;

import org.jerry.jorm.sqlgenerator.ddl.DDLGenerator;
import org.jerry.jorm.sqlgenerator.ddl.MySQLDDLGenerator;
import org.jerry.jorm.sqlgenerator.sql.MySQLGenerator;
import org.jerry.jorm.sqlgenerator.sql.SQLGenerator;

/**
 * Created by Jerry.Yong on 2014/11/5.
 */
public class MySQLDialect extends Dialect {

    @Override
    public SQLGenerator getSqlGenerator() {
        return new MySQLGenerator();
    }

    @Override
    public DDLGenerator getDdlGenerator() {
        return new MySQLDDLGenerator();
    }
}
