package org.jerry.jorm.descriptor;

import org.jerry.jorm.annotation.TemporalType;

import java.lang.reflect.Method;

/**
 * Created by yong_pliang on 14/10/29.
 */
public class EntityPropertyDescriptor {
    private boolean isId;
    private Class type;
    private String propertyName;
    private String colName;
    private Method readMethod;
    private Method writeMethod;
    private TemporalType temporalType;//日期格式
    private boolean nullable;
    private boolean unique;
    private long length;
    private boolean auto;
    private boolean lob;

    public boolean isId() {
        return isId;
    }

    public void setId(boolean isId) {
        this.isId = isId;
    }

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

    public TemporalType getTemporalType() {
        return temporalType;
    }

    public void setTemporalType(TemporalType temporalType) {
        this.temporalType = temporalType;
    }

    public boolean isNullable() {
        return nullable;
    }

    public void setNullable(boolean nullable) {
        this.nullable = nullable;
    }

    public boolean isUnique() {
        return unique;
    }

    public void setUnique(boolean unique) {
        this.unique = unique;
    }

    public long getLength() {
        return length;
    }

    public void setLength(long length) {
        this.length = length;
    }

    public boolean isAuto() {
        return auto;
    }

    public void setAuto(boolean auto) {
        this.auto = auto;
    }

    public boolean isLob() {
        return lob;
    }

    public void setLob(boolean lob) {
        this.lob = lob;
    }
}
