package org.jerry.jorm.sqlgenerator.sql;

import org.jerry.jorm.Filter;
import org.jerry.jorm.Order;
import org.jerry.jorm.descriptor.EntityDescriptor;
import org.jerry.jorm.descriptor.EntityPropertyDescriptor;
import org.apache.commons.lang.StringUtils;

import java.lang.reflect.InvocationTargetException;
import java.util.*;

/**
 * Created by yong_pliang on 14/10/29.
 */
public class MySQLGenerator implements SQLGenerator {

    private Map<String, CRUD> crudMap = new HashMap<String, CRUD>();
    private boolean showSql = false;


    @Override
    public String generateSelectByIdSql(EntityDescriptor entityDescriptor) {
        String clazzName = entityDescriptor.getClazz().getName();
        CRUD crud = crudMap.get(clazzName);
        if (crud == null) {
            crud = new CRUD(entityDescriptor.getClazz());
            crudMap.put(clazzName, crud);
        }

        String selectByIdSql = crud.getSelectByIdSql();
        if (StringUtils.isNotEmpty(selectByIdSql)) {
            showSql(selectByIdSql);
            return selectByIdSql;
        }

        StringBuffer selectSql = new StringBuffer();
        List<EntityPropertyDescriptor> propertyDescriptors = entityDescriptor.getEntityPropertyDescriptors();
        selectSql.append("select");
        for (EntityPropertyDescriptor propertyDescriptor : propertyDescriptors) {
            selectSql.append(" " + propertyDescriptor.getColName() + ",");
        }
        selectSql.deleteCharAt(selectSql.length() - 1);
        selectSql.append(" from ");
        selectSql.append(entityDescriptor.getTableName());
        selectSql.append(" where ");
        EntityPropertyDescriptor idPropertyDescriptor = entityDescriptor.getIdDescriptor();
        selectSql.append(idPropertyDescriptor.getColName() + " = :" + idPropertyDescriptor.getPropertyName());
        selectByIdSql = selectSql.toString();
        crud.setSelectByIdSql(selectByIdSql);
        showSql(selectByIdSql);
        return selectByIdSql;
    }

    @Override
    public String generateInsertSql(EntityDescriptor entityDescriptor, Object o) {
        try {
            List<EntityPropertyDescriptor> propertyDescriptors = entityDescriptor.getEntityPropertyDescriptors();
            StringBuffer insertSql = new StringBuffer();
            insertSql.append("insert into ");
            insertSql.append(entityDescriptor.getTableName());
            StringBuffer temp1 = new StringBuffer("( ");
            StringBuffer temp2 = new StringBuffer("( ");
            for (EntityPropertyDescriptor propertyDescriptor : propertyDescriptors) {
                Object value = propertyDescriptor.getReadMethod().invoke(o);
                if (value == null) {
                    continue;
                }
                temp1.append(" " + propertyDescriptor.getColName() + ",");
                temp2.append(" :" + propertyDescriptor.getPropertyName() + ",");
            }
            temp1.deleteCharAt(temp1.length() - 1).append(" )");
            temp2.deleteCharAt(temp2.length() - 1).append(" )");
            insertSql.append(temp1).append(" values ").append(temp2);
            showSql(insertSql);
            return insertSql.toString();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return null;
    }


    @Override
    public String generateUpdateSql(EntityDescriptor entityDescriptor, Object o, boolean ignoreNull) {
        try {
            List<EntityPropertyDescriptor> propertyDescriptors = entityDescriptor.getEntityPropertyDescriptors();
            StringBuffer updateSql = new StringBuffer();
            updateSql.append("update ");
            updateSql.append(entityDescriptor.getTableName());
            updateSql.append(" set ");
            for (EntityPropertyDescriptor propertyDescriptor : propertyDescriptors) {
                if (propertyDescriptor.getColName().equalsIgnoreCase(entityDescriptor.getIdDescriptor().getColName())) {
                    continue;
                }
                Object value = propertyDescriptor.getReadMethod().invoke(o);
                if (ignoreNull && value == null) {
                    continue;
                }
                updateSql.append(propertyDescriptor.getColName() + " = :" + propertyDescriptor.getPropertyName() + ",");
            }
            updateSql.deleteCharAt(updateSql.length() - 1);
            updateSql.append(" where ");
            EntityPropertyDescriptor idPropertyDescriptor = entityDescriptor.getIdDescriptor();
            updateSql.append(idPropertyDescriptor.getColName() + " = :" + idPropertyDescriptor.getPropertyName());
            showSql(updateSql);
            return updateSql.toString();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return null;
    }


    @Override
    public String generateUpdateSql(EntityDescriptor entityDescriptor, Map<String, Object> updateProperties, Filter... filters) {
        StringBuffer updateSql = new StringBuffer();
        updateSql.append("update ");
        updateSql.append(entityDescriptor.getTableName());
        updateSql.append(" set ");
        for (Map.Entry<String, Object> entry : updateProperties.entrySet()) {
            String propertyName = entry.getKey();
            EntityPropertyDescriptor descriptor = entityDescriptor.getEntityPropertyDescriptorByPropertyName(propertyName);
            updateSql.append(descriptor.getColName() + " =:" + propertyName + ",");

        }
        updateSql.deleteCharAt(updateSql.length() - 1);
        if (filters != null && filters.length > 0) {
            updateSql.append(" where ");
            String filterSql = buildFilterSql(entityDescriptor, filters);
            updateSql.append(filterSql);
        }
        showSql(updateSql);
        return updateSql.toString();

    }


    @Override
    public String generateDeleteByIdSql(EntityDescriptor entityDescriptor) {
        String clazzName = entityDescriptor.getClazz().getName();
        CRUD crud = crudMap.get(clazzName);
        if (crud == null) {
            crud = new CRUD(entityDescriptor.getClazz());
            crudMap.put(clazzName, crud);
        }

        String deleteByIdSql = crud.getDeleteByIdSql();
        if (StringUtils.isNotEmpty(deleteByIdSql)) {
            showSql(deleteByIdSql);
            return deleteByIdSql;
        }

        StringBuffer deleteSql = new StringBuffer();
        deleteSql.append("delete from ");
        deleteSql.append(entityDescriptor.getTableName());
        deleteSql.append(" where ");
        EntityPropertyDescriptor idPropertyDescriptor = entityDescriptor.getIdDescriptor();
        deleteSql.append(idPropertyDescriptor.getColName() + " = :" + idPropertyDescriptor.getPropertyName());
        deleteByIdSql = deleteSql.toString();
        crud.setSelectByIdSql(deleteByIdSql);
        showSql(deleteByIdSql);
        return deleteByIdSql;
    }


    @Override
    public String generateDeleteSql(EntityDescriptor entityDescriptor, Filter... filters) {
        StringBuffer deleteSql = new StringBuffer();
        deleteSql.append("delete from ");
        deleteSql.append(entityDescriptor.getTableName());
        if (filters == null || filters.length == 0) {
            return deleteSql.toString();
        }
        deleteSql.append(" where ");
        String filterSql = buildFilterSql(entityDescriptor, filters);
        deleteSql.append(filterSql);
        showSql(deleteSql);
        return deleteSql.toString();
    }


    @Override
    public String generateSelectSql(EntityDescriptor entityDescriptor, int first, int count, List<Filter> filters, Order... orders) {
        StringBuffer selectSql = new StringBuffer();
        List<EntityPropertyDescriptor> propertyDescriptors = entityDescriptor.getEntityPropertyDescriptors();
        selectSql.append("select ");
        for (EntityPropertyDescriptor propertyDescriptor : propertyDescriptors) {
            selectSql.append(" " + propertyDescriptor.getColName() + ",");
        }
        selectSql.deleteCharAt(selectSql.length() - 1);
        selectSql.append(" from ");
        selectSql.append(entityDescriptor.getTableName());

        if (filters != null && filters.size() > 0) {
            selectSql.append(" where ");
            selectSql.append(" " + buildFilterSql(entityDescriptor, filters.toArray(new Filter[]{})) + " ");
        }

        if (orders != null && orders.length > 0) {
            selectSql.append(" " + buildOrderSql(entityDescriptor, orders) + " ");
        }

        if (first > -1 && count > -1) {
            selectSql.append(" limit " + first + ", " + count);
        }

        showSql(selectSql);
        return selectSql.toString();

    }





    @Override
    public String generateSelectCountSql(EntityDescriptor entityDescriptor, Filter... filters) {
        StringBuffer selectSql = new StringBuffer();
        selectSql.append("select count(1) from ");
        selectSql.append(entityDescriptor.getTableName());
        if (filters != null && filters.length > 0) {
            selectSql.append(" where ");
            selectSql.append(" " + buildFilterSql(entityDescriptor, filters) + " ");
        }
        showSql(selectSql);
        return selectSql.toString();
    }


    private static String buildFilterSql(EntityDescriptor entityDescriptor, Filter... filters) {
        if (filters == null || filters.length == 0) {
            return "";
        }
        StringBuffer filterSql = new StringBuffer();
        for (int i = 0; i < filters.length; i++) {
            Filter filter = filters[i];
            String propertyName = filter.getProperty();
            String columnName = entityDescriptor.getEntityPropertyDescriptorByPropertyName(propertyName).getColName();
            Filter.Operator operator = filter.getOperator();
            switch (operator) {
                case eq:
                    filterSql.append(" " + columnName + " = :" + propertyName + "_filter_jerry_" + i);
                    break;
                case ne:
                    filterSql.append(" " + columnName + " != :" + propertyName + "_filter_jerry_" + i);
                    break;
                case ge:
                    filterSql.append(" " + columnName + " >= :" + propertyName + "_filter_jerry_" + i);
                    break;
                case gt:
                    filterSql.append(" " + columnName + " > :" + propertyName + "_filter_jerry_" + i);
                    break;
                case le:
                    filterSql.append(" " + columnName + " <= :" + propertyName + "_filter_jerry_" + i);
                    break;
                case lt:
                    filterSql.append(" " + columnName + " < :" + propertyName + "_filter_jerry_" + i);
                    break;
                case like:
                    filterSql.append(" " + columnName + " like :" + propertyName + "_filter_jerry_" + i);
                    break;
                case in:
                    Collection collection = (Collection) filter.getValue();
                    filterSql.append(" " + columnName + " in ( ");
                    for (int j = 0; j < collection.size(); j++) {
                        filterSql.append(" :" + propertyName + "_filter_in_jerry_" + j + ",");
                    }
                    filterSql.deleteCharAt(filterSql.length() - 1);
                    filterSql.append(" ) ");
                    break;
                case isNull:
                    filterSql.append(" " + columnName + " is null ");
                    break;
                case isNotNull:
                    filterSql.append(" " + columnName + " is not null ");
                    break;

            }

            filterSql.append(" and");

        }
        int length = filterSql.length();
        filterSql.delete(length - 3, length);
        return filterSql.toString();
    }

    private static String buildOrderSql(EntityDescriptor entityDescriptor, Order... orders) {
        if (orders == null || orders.length == 0) {
            return "";
        }

        StringBuffer orderSql = new StringBuffer();
        orderSql.append(" order by ");
        for (Order order : orders) {
            String columnName = entityDescriptor.getEntityPropertyDescriptorByPropertyName(order.getProperty()).getColName();
            orderSql.append(" " + columnName + " " + order.getDirection().name() + ",");
        }
        orderSql.deleteCharAt(orderSql.length() - 1);
        return orderSql.toString();
    }

    public void showSql(CharSequence sql) {
        if (showSql) {
            System.out.println(sql);
        }

    }

    @Override
    public boolean isShowSql() {
        return showSql;
    }

    @Override
    public void setShowSql(boolean showSql) {
        this.showSql = showSql;
    }

    public MySQLGenerator() {
    }


}
