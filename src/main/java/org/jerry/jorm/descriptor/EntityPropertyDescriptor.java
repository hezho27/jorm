package org.jerry.jorm.descriptor;

import java.lang.reflect.Method;

/**
 * Created by yong_pliang on 14/10/29.
 */
public class EntityPropertyDescriptor {
    private Class type;
    private String propertyName;
    private String colName;
    private Method readMethod;
    private Method writeMethod;

    public Class getType() {
        return type;
    }

    public void setType(Class type) {
        this.type = type;
    }

    public String getPropertyName() {
        return propertyName;
    }

    public void setPropertyName(String propertyName) {
        this.propertyName = propertyName;
    }

    public String getColName() {
        return colName;
    }

    public void setColName(String colName) {
        this.colName = colName;
    }

    public Method getReadMethod() {
        return readMethod;
    }

    public void setReadMethod(Method readMethod) {
        this.readMethod = readMethod;
    }

    public Method getWriteMethod() {
        return writeMethod;
    }

    public void setWriteMethod(Method writeMethod) {
        this.writeMethod = writeMethod;
    }
}
