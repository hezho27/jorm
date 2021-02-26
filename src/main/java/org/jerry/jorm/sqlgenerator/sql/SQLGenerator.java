package org.jerry.jorm.sqlgenerator.sql;

import org.jerry.jorm.Expression;
import org.jerry.jorm.Filter;
import org.jerry.jorm.Order;
import org.jerry.jorm.descriptor.EntityDescriptor;

import java.util.List;
import java.util.Map;

/**
 * Created by yong_pliang on 14/10/29.
 */
public interface SQLGenerator {

    public String generateSelectByIdSql(EntityDescriptor entityDescriptor);

    public String generateInsertSql(EntityDescriptor entityDescriptor, Object o);

    public String generateUpdateSql(EntityDescriptor entityDescriptor, Object o, boolean ignoreNull);

    String generateUpdateSql(EntityDescriptor entityDescriptor, Map<String, Object> updateProperties, Filter... filters);

    String generateUpdateSql(EntityDescriptor entityDescriptor, Map<String, Object> updateProperties, Expression expression, List<Filter> filters);

    public String generateDeleteByIdSql(EntityDescriptor entityDescriptor);

    String generateDeleteSql(EntityDescriptor entityDescriptor, Filter... filters);

    String generateDeleteSql(EntityDescriptor entityDescriptor, Expression expression, List<Filter> filters);

    String generateSelectSql(EntityDescriptor entityDescriptor, int first, int count, List<Filter> filters, Order... orders);

    String generateSelectSql(EntityDescriptor entityDescriptor, int first, int count, Expression expression, List<Filter> filters, Order... orders);

    String generateSelectCountSql(EntityDescriptor entityDescriptor, Filter... filters);

    String generateSelectCountSql(EntityDescriptor entityDescriptor, Expression expression, List<Filter> filters);

    boolean isShowSql();

    void setShowSql(boolean showSql);
}
