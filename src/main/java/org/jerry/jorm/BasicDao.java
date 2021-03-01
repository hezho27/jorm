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

    /**
     * @param page    第几页,从1开始
     * @param rows    每页记录数
     * @param filters 过滤条件
     * @param sidx    排序字段
     * @param sord    排序值  asc desc
     * @return
     */
    GridJson page(Integer page, Integer rows, List<Filter> filters, String sidx, String sord);

    /**
     * @param page       第几页,从1开始
     * @param rows       每页记录数
     * @param expression 过滤表达式
     * @param sidx       排序字段
     * @param sord       排序值  asc desc
     * @return
     */
    GridJson page(Integer page, Integer rows, Expression expression, String sidx, String sord);
}
