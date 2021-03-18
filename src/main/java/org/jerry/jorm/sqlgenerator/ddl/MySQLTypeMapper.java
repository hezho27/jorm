package org.jerry.jorm.sqlgenerator.ddl;

import java.util.Date;

/**
 * Created by Jerry.Yong on 2014/11/6.
 */
public class MySQLTypeMapper {

    public static String mapping(Class clazz) {
        if (clazz == String.class || clazz.isEnum()) {
            return "VARCHAR(255)";
        } else if (clazz == Integer.class || clazz == int.class) {
            return "INT";
        } else if (clazz == Long.class || clazz == long.class) {
            return "INT(11)";
        } else if (clazz == Double.class || clazz == double.class) {
            return "DOUBLE";
        } else if (clazz == Float.class || clazz == float.class) {
            return "FLOAT";
        } else if (clazz == Date.class) {
            return "DATETIME";
        } else if (clazz == Boolean.class || clazz == boolean.class) {
            return "BIT";
        } else {
            throw new IllegalArgumentException(clazz + "类型模型字段暂时不支持");
        }
    }

}
