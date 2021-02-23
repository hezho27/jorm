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

    void delete(ID id);

    void delete(Filter... filters);

    List<T> findList(List<Filter> filters, Order... orders);

    List<T> findList(int first, int count, List<Filter> filters, Order... orders);

    long count(Filter... filters);
}
