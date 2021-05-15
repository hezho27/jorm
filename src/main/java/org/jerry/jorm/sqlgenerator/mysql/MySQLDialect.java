package org.jerry.jorm.sqlgenerator.mysql;

import org.jerry.jorm.sqlgenerator.DDLGenerator;
import org.jerry.jorm.sqlgenerator.Dialect;
import org.jerry.jorm.sqlgenerator.SQLGenerator;

/**
 * Created by Jerry.Yong on 2014/11/5.
 */
public class MySQLDialect extends Dialect {

    @Override
    public SQLGenerator getSqlGenerator() {
        return new MySQLDMLGenerator();
    }

    @Override
    public DDLGenerator getDdlGenerator() {
        return new MySQLDDLGenerator();
    }
}
