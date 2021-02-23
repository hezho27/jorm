package org.jerry.jorm;

import org.jerry.jorm.descriptor.EntityDescriptionManager;
import org.jerry.jorm.descriptor.EntityDescriptor;
import org.jerry.jorm.rowmapper.DefaultRowMapper;
import org.jerry.jorm.sqlgenerator.sql.SQLGenerator;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcDaoSupport;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;

import javax.annotation.Resource;
import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Jerry.Yong on 2014/10/11.
 */

public abstract class BasicDaoImpl<T, ID extends Serializable> extends NamedParameterJdbcDaoSupport implements BasicDao<T, ID> {

    protected Class entityClass;
    protected SQLGenerator sqlGenerator;
    protected EntityDescriptor entityDescriptor;

    @Resource
    public void setConfig(Config config) {
        setDataSource(config.getDataSource());
        sqlGenerator = config.getSqlGenerator();
        EntityDescriptionManager entityDescriptionManager = config.getEntityDescriptionManager();
        entityDescriptor = entityDescriptionManager.getEntityDescriptor(entityClass);
    }

    @SuppressWarnings("unchecked")
    public BasicDaoImpl() {
        Type type = getClass().getGenericSuperclass();
        Type[] parameterizedType = ((ParameterizedType) type).getActualTypeArguments();
        entityClass = (Class<T>) parameterizedType[0];
    }


    @Override
    public T find(ID id) {
        String sql = sqlGenerator.generateSelectByIdSql(entityDescriptor);
        String idPropertyName = entityDescriptor.getIdDescriptor().getPropertyName();
        JMapSqlParameterSource params = new JMapSqlParameterSource();
        params.addValue(idPropertyName, id);
        List<T> results = getNamedParameterJdbcTemplate().query(sql, params, new DefaultRowMapper<T>(entityDescriptor));
        return results.size() > 0 ? results.get(0) : null;
    }


    @Override
    public void save(T t) {
        String sql = sqlGenerator.generateInsertSql(entityDescriptor, t);
        SqlParameterSource params = new JBeanPropertySqlParameterSource(t);
        getNamedParameterJdbcTemplate().update(sql, params);

    }

    @Override
    public void update(T t, boolean ignoreNull) {
        String sql = sqlGenerator.generateUpdateSql(entityDescriptor, t, ignoreNull);
        SqlParameterSource params = new JBeanPropertySqlParameterSource(t);
        getNamedParameterJdbcTemplate().update(sql, params);
    }


    @Override
    public void update(Map<String, Object> updateProperties, Filter... filters) {
        String sql = sqlGenerator.generateUpdateSql(entityDescriptor, updateProperties, filters);
        JMapSqlParameterSource params = filters2Map(filters);
        params.addValues(updateProperties);
        getNamedParameterJdbcTemplate().update(sql, params);
    }

    @Override
    public void delete(ID id) {
        String sql = sqlGenerator.generateDeleteByIdSql(entityDescriptor);
        String idPropertyName = entityDescriptor.getIdDescriptor().getPropertyName();
        Map params = new HashMap();
        params.put(idPropertyName, id);
        getNamedParameterJdbcTemplate().update(sql, params);
    }

    @Override
    public void delete(Filter... filters) {
        String sql = sqlGenerator.generateDeleteSql(entityDescriptor, filters);
        JMapSqlParameterSource params = filters2Map(filters);
        getNamedParameterJdbcTemplate().update(sql, params);

    }

    @Override
    public List<T> findList(List<Filter> filters, Order... orders) {
        return findList(-1, -1, filters, orders);
    }

    @Override
    public List<T> findList(int first, int count, List<Filter> filters, Order... orders) {
        String sql = sqlGenerator.generateSelectSql(entityDescriptor, first, count, filters, orders);
        JMapSqlParameterSource params = filters2Map(filters == null ? null : filters.toArray(new Filter[]{}));
        return getNamedParameterJdbcTemplate().query(sql, params, new DefaultRowMapper<T>(entityDescriptor));
    }

    @Override
    public long count(Filter... filters) {
        String sql = sqlGenerator.generateSelectCountSql(entityDescriptor, filters);
        JMapSqlParameterSource params = filters2Map(filters);
        return getNamedParameterJdbcTemplate().queryForLong(sql, params);

    }

    protected static JMapSqlParameterSource filters2Map(Filter... filters) {
        JMapSqlParameterSource params = new JMapSqlParameterSource();
        if (filters == null) {
            return params;
        }
        for (int i = 0; i < filters.length; i++) {
            Filter filter = filters[i];
            String propertyName = filter.getProperty();
            if (filter.getOperator() == Filter.Operator.in) {
                Collection collection = (Collection) filter.getValue();
                int j = 0;
                for (Object o : collection) {
                    params.addValue(propertyName + "_filter_in_jerry_" + j++, o);
                }

                continue;
            }
            params.addValue(propertyName + "_filter_jerry_" + i, filter.getValue());
        }

        return params;
    }


    public Class getEntityClass() {
        return entityClass;
    }

    public void setEntityClass(Class entityClass) {
        this.entityClass = entityClass;
    }

    public SQLGenerator getSqlGenerator() {
        return sqlGenerator;
    }

    public void setSqlGenerator(SQLGenerator sqlGenerator) {
        this.sqlGenerator = sqlGenerator;
    }

    public EntityDescriptor getEntityDescriptor() {
        return entityDescriptor;
    }

    public void setEntityDescriptor(EntityDescriptor entityDescriptor) {
        this.entityDescriptor = entityDescriptor;
    }
}