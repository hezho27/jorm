package org.jerry.jorm;

import org.jerry.jorm.descriptor.EntityDescriptionManager;
import org.jerry.jorm.descriptor.EntityDescriptor;
import org.jerry.jorm.descriptor.EntityPropertyDescriptor;
import org.jerry.jorm.exception.NotOneResultException;
import org.jerry.jorm.rowmapper.DefaultRowMapper;
import org.jerry.jorm.sqlgenerator.SQLGenerator;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcDaoSupport;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import javax.annotation.Resource;
import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.*;

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
        EntityPropertyDescriptor idDescriptor = entityDescriptor.getIdDescriptor();
        if (idDescriptor.isAuto()) {
            KeyHolder keyHolder = new GeneratedKeyHolder();
            getNamedParameterJdbcTemplate().update(sql, params, keyHolder);
            String id = keyHolder.getKey().longValue()+"";
            try {
                Object oid = idDescriptor.getType().getConstructor(String.class).newInstance(id);
                idDescriptor.getWriteMethod().invoke(t, oid);
            } catch (Exception e) {
                e.printStackTrace();
            }

        } else {
            getNamedParameterJdbcTemplate().update(sql, params);
        }

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
    public void update(Map<String, Object> updateProperties, Expression expression) {
        List<Filter> filters = new ArrayList<Filter>();
        String sql = sqlGenerator.generateUpdateSql(entityDescriptor, updateProperties, expression, filters);
        JMapSqlParameterSource params = filters2Map(filters.toArray(new Filter[]{}));
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
    public void delete(Expression expression) {
        List<Filter> filters = new ArrayList<Filter>();
        String sql = sqlGenerator.generateDeleteSql(entityDescriptor, expression, filters);
        JMapSqlParameterSource params = filters2Map(filters.toArray(new Filter[]{}));
        getNamedParameterJdbcTemplate().update(sql, params);
    }


    @Override
    public List<T> findList(List<Filter> filters, Order... orders) {
        return findList(-1, -1, filters, orders);
    }

    @Override
    public List<T> findList(Expression expression, Order... orders) {
        return findList(-1, -1, expression, orders);
    }


    @Override
    public List<T> findList(int first, int count, List<Filter> filters, Order... orders) {
        String sql = sqlGenerator.generateSelectSql(entityDescriptor, first, count, filters, orders);
        JMapSqlParameterSource params = filters2Map(filters == null ? null : filters.toArray(new Filter[]{}));
        return getNamedParameterJdbcTemplate().query(sql, params, new DefaultRowMapper<T>(entityDescriptor));
    }

    @Override
    public List<T> findList(int first, int count, Expression expression, Order... orders) {
        List<Filter> filters = new ArrayList<Filter>();
        String sql = sqlGenerator.generateSelectSql(entityDescriptor, first, count, expression, filters, orders);
        JMapSqlParameterSource params = filters2Map(filters == null ? null : filters.toArray(new Filter[]{}));
        return getNamedParameterJdbcTemplate().query(sql, params, new DefaultRowMapper<T>(entityDescriptor));
    }

    @Override
    public long count(Filter... filters) {
        String sql = sqlGenerator.generateSelectCountSql(entityDescriptor, filters);
        JMapSqlParameterSource params = filters2Map(filters);
        return getNamedParameterJdbcTemplate().queryForLong(sql, params);
    }

    @Override
    public long count(Expression expression) {
        List<Filter> filters = new ArrayList<Filter>();
        String sql = sqlGenerator.generateSelectCountSql(entityDescriptor, expression, filters);
        JMapSqlParameterSource params = filters2Map(filters.toArray(new Filter[]{}));
        return getNamedParameterJdbcTemplate().queryForLong(sql, params);
    }

    @Override
    public T findOne(Filter... filters) {
        List<T> results = findList(Arrays.asList(filters));
        if (results.size() > 1) {
            throw new NotOneResultException();
        }
        return results.size() > 0 ? results.get(0) : null;
    }

    @Override
    public T findOne(Expression expression) {
        List<T> results = findList(expression);
        if (results.size() > 1) {
            throw new NotOneResultException();
        }
        return results.size() > 0 ? results.get(0) : null;
    }


    @Override
    public T findFirst(List<Filter> filters, Order... orders) {
        List<T> list = findList(0, 1, filters, orders);
        return list.size() > 0 ? list.get(0) : null;
    }

    @Override
    public T findFirst(Expression expression, Order... orders) {
        List<T> list = findList(0, 1, expression, orders);
        return list.size() > 0 ? list.get(0) : null;
    }

    @Override
    public GridJson page(Integer page, Integer rows, List<Filter> filters, String sidx, String sord) {
        int first = (page - 1) * rows;
        List<T> data = findList(first, rows, filters, new Order(sidx, Order.Direction.fromString(sord)));
        long count = count(filters == null ? null : filters.toArray(new Filter[]{}));
        return new GridJson(page, count, rows, data);
    }

    @Override
    public GridJson page(Integer page, Integer rows, Expression expression, String sidx, String sord) {
        int first = (page - 1) * rows;
        List<T> data = findList(first, rows, expression, new Order(sidx, Order.Direction.fromString(sord)));
        long count = count(expression);
        return new GridJson(page, count, rows, data);
    }

    @Override
    public GridJson page(Integer page, Integer rows, List<Filter> filters, Order... orders) {
        int first = (page - 1) * rows;
        List<T> data = findList(first, rows, filters, orders);
        long count = count(filters == null ? null : filters.toArray(new Filter[]{}));
        return new GridJson(page, count, rows, data);
    }

    @Override
    public GridJson page(Integer page, Integer rows, Expression expression, Order... orders) {
        int first = (page - 1) * rows;
        List<T> data = findList(first, rows, expression, orders);
        long count = count(expression);
        return new GridJson(page, count, rows, data);
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