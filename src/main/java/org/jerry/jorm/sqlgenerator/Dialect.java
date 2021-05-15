package org.jerry.jorm.sqlgenerator;

/**
 * Created by Jerry.Yong on 2014/11/5.
 */
public abstract class Dialect {

    abstract public SQLGenerator getSqlGenerator();
    abstract public DDLGenerator getDdlGenerator();
}
