package org.jerry.jorm.sqlgenerator.sqlserver;

import org.jerry.jorm.sqlgenerator.DDLGenerator;
import org.jerry.jorm.sqlgenerator.Dialect;
import org.jerry.jorm.sqlgenerator.SQLGenerator;

/**
 * Created by Jerry.Yong on 2014/11/5.
 */
public class SQLServerDialect extends Dialect {

    @Override
    public SQLGenerator getSqlGenerator() {
        return new SQLServerDMLGenerator();
    }

    @Override
    public DDLGenerator getDdlGenerator() {
        return new SQLServerDDLGenerator();
    }
}
