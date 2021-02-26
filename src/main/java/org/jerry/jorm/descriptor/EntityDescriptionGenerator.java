package org.jerry.jorm.descriptor;


import org.jerry.jorm.NameConverter;
import org.jerry.jorm.annotation.*;
import org.jerry.jorm.util.ClassUtils;

import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by yong_pliang on 14/10/29.
 */
public class EntityDescriptionGenerator {


    public static Map<String, EntityDescriptor> buildEntityDescriptions(Set<Class<?>> classes) {
        Map<String, EntityDescriptor> entityDescriptors = new HashMap<String, EntityDescriptor>();
        for (Class<?> aClass : classes) {
            Entity entity = aClass.getAnnotation(Entity.class);
            if (entity == null) {
                continue;
            }
            EntityDescriptor entityDescriptor = new EntityDescriptor();
            entityDescriptor.setClazz(aClass);
            Table table = aClass.getAnnotation(Table.class);
            if (table != null) {
                entityDescriptor.setTableName(table.name());
            } else {
                entityDescriptor.setTableName(aClass.getSimpleName());
            }

            buildEntityPropertyDescriptor(entityDescriptor);
            entityDescriptors.put(aClass.getName(), entityDescriptor);
        }

        return entityDescriptors;

    }

    private static void buildEntityPropertyDescriptor(EntityDescriptor entityDescriptor) {
        Class<?> calzz = entityDescriptor.getClazz();
        try {
            PropertyDescriptor[] propertyDescriptors = Introspector.getBeanInfo(calzz).getPropertyDescriptors();
            if (propertyDescriptors == null) {
                return;
            }
            for (PropertyDescriptor propertyDescriptor : propertyDescriptors) {
                if (!ClassUtils.isBaseType(propertyDescriptor.getPropertyType())) {
                    continue;
                }

                Method readMethod = propertyDescriptor.getReadMethod();
                Transient t = readMethod.getAnnotation(Transient.class);
                if (t != null) {
                    continue;
                }
                EntityPropertyDescriptor descriptor = new EntityPropertyDescriptor();
                descriptor.setPropertyName(propertyDescriptor.getName());
                descriptor.setType(propertyDescriptor.getPropertyType());
                Column column = readMethod.getAnnotation(Column.class);
                if (column == null) {
                    String sqlName = NameConverter.javaName2sqlName(propertyDescriptor.getName());
                    descriptor.setColName(sqlName);
                } else {
                    descriptor.setColName(column.name());
                }
                Id id = readMethod.getAnnotation(Id.class);
                if (id != null) {
                    entityDescriptor.setIdDescriptor(descriptor);
                }
                descriptor.setWriteMethod(propertyDescriptor.getWriteMethod());
                descriptor.setReadMethod(propertyDescriptor.getReadMethod());
                entityDescriptor.addEntityPropertyDescriptor(descriptor);
            }
        } catch (IntrospectionException e) {
            e.printStackTrace();
        }


    }

}