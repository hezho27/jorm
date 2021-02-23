package org.jerry.jorm.rowmapper;


import org.jerry.jorm.descriptor.EntityDescriptor;
import org.jerry.jorm.descriptor.EntityPropertyDescriptor;
import org.springframework.jdbc.core.RowMapper;

import java.lang.reflect.InvocationTargetException;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;

/**
 * Created by yong_pliang on 14/10/29.
 */
public class DefaultRowMapper<T> implements RowMapper<T> {
    private Class<T> entityClass;
    private EntityDescriptor entityDescriptor;

    @Override
    public T mapRow(ResultSet rs, int rowNum) throws SQLException {
        try {
            T t = entityClass.newInstance();
            ResultSetMetaData metaData = rs.getMetaData();
            int count = metaData.getColumnCount();
            for (int i = 1; i <= count; i++) {
                String columnName = metaData.getColumnLabel(i);
                EntityPropertyDescriptor descriptor = entityDescriptor.getEntityPropertyDescriptorByColumnName(columnName);
                Class clazz = descriptor.getType();
                Object value = null;
                if (clazz == String.class) {
                    value = rs.getString(columnName);
                } else if (clazz == Long.class) {
                    value = rs.getLong(columnName);
                } else if (clazz == Date.class) {
                    Timestamp timestamp = rs.getTimestamp(columnName);
                    if (timestamp != null) {
                        value = new Date(timestamp.getTime());
                    }
                } else if (clazz == Double.class) {
                    value = rs.getDouble(columnName);
                } else if (clazz == Boolean.class) {
                    value = rs.getBoolean(columnName);
                } else if (clazz == Integer.class) {
                    value = rs.getInt(columnName);
                } else if (clazz == Float.class) {
                    value = rs.getFloat(columnName);
                } else if (clazz == Short.class) {
                    value = rs.getShort(columnName);
                } else if (clazz.isEnum()) {
                    value = rs.getString(columnName);
                    if (value != null) {
                        value = Enum.valueOf(clazz, (String) value);
                    }

                }
                descriptor.getWriteMethod().invoke(t, value);
            }

            return t;
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return null;
    }

    public DefaultRowMapper(EntityDescriptor entityDescriptor) {
        this.entityDescriptor = entityDescriptor;
        this.entityClass = entityDescriptor.getClazz();
    }

}
