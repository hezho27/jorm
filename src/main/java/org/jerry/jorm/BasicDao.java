package org.jerry.jorm;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * Created by yong_pliang on 14/10/29.
 */
public interface BasicDao<T, ID extends Serializable> {
    T find(ID id);

    void save(T t);

    void update(T t, boolean ignoreNull);

    void update(Map<String, Object> updateProperties, Filter... filters);

    void update(Map<String, Object> updateProperties, Expression expression);

    void delete(ID id);

    void delete(Filter... filters);

    void delete(Expression expression);

    List<T> findList(List<Filter> filters, Order... orders);

    List<T> findList(Expression expression, Order... orders);

    List<T> findList(int first, int count, List<Filter> filters, Order... orders);

    List<T> findList(int first, int count, Expression expression, Order... orders);

    long count(Filter... filters);

    long count(Expression expression);

    T findOne(Filter... filters);

    T findOne(Expression expression);
}
