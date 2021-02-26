package org.jerry.jorm.descriptor;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yong_pliang on 14/10/29.
 */
public class EntityDescriptor {
    private Class clazz;
    private String tableName;
    private List<EntityPropertyDescriptor> entityPropertyDescriptors = new ArrayList<EntityPropertyDescriptor>();
    private EntityPropertyDescriptor idDescriptor;


    public Class getClazz() {
        return clazz;
    }

    public void setClazz(Class clazz) {
        this.clazz = clazz;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public List<EntityPropertyDescriptor> getEntityPropertyDescriptors() {
        return entityPropertyDescriptors;
    }

    public void setEntityPropertyDescriptors(List<EntityPropertyDescriptor> entityPropertyDescriptors) {
        this.entityPropertyDescriptors = entityPropertyDescriptors;
    }

    public void addEntityPropertyDescriptor(EntityPropertyDescriptor entityPropertyDescriptor) {
        this.entityPropertyDescriptors.add(entityPropertyDescriptor);
    }

    public EntityPropertyDescriptor getIdDescriptor() {
        return idDescriptor;
    }

    public void setIdDescriptor(EntityPropertyDescriptor idDescriptor) {
        this.idDescriptor = idDescriptor;
    }

    public EntityPropertyDescriptor getEntityPropertyDescriptorByColumnName(String columnName) {
        for (EntityPropertyDescriptor entityPropertyDescriptor : entityPropertyDescriptors) {
            if (entityPropertyDescriptor.getColName().equalsIgnoreCase(columnName)) {
                return entityPropertyDescriptor;
            }
        }
        return null;
    }

    public EntityPropertyDescriptor getEntityPropertyDescriptorByPropertyName(String propertyName) {
        for (EntityPropertyDescriptor entityPropertyDescriptor : entityPropertyDescriptors) {
            if (entityPropertyDescriptor.getPropertyName().equals(propertyName)) {
                return entityPropertyDescriptor;
            }
        }
        return null;

    }


}
