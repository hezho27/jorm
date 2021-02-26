package org.jerry.jorm;

import java.io.Serializable;
import java.util.Date;


public class Filter implements Serializable,Filterable {

    public enum Operator {
        eq, ne, gt, lt, ge, le, like, in, isNull, isNotNull;

        public static Operator fromString(String value) {
            return valueOf(value.toLowerCase());
        }
    }

    private static final long serialVersionUID = -8712382358441065075L;
    private String property;
    private Operator operator;
    private Object value;
    private Date start;
    private Date end;
    private Boolean ignoreCase = Boolean.valueOf(false);

    public Filter() {
    }

    public Filter(String property, Operator operator, Object value) {
        this.property = property;
        this.operator = operator;
        this.value = value;
    }

    public Filter(String property, Operator operator, Date start, Date end) {
        this.property = property;
        this.operator = operator;
        this.start = start;
        this.end = end;
    }

    public Filter(String property, Operator operator, Object value, boolean ignoreCase) {
        this.property = property;
        this.operator = operator;
        this.value = value;
        this.ignoreCase = Boolean.valueOf(ignoreCase);
    }

    public static Filter eq(String property, Object value) {
        return new Filter(property, Operator.eq, value);
    }

    public static Filter eq(String property, Object value, boolean ignoreCase) {
        return new Filter(property, Operator.eq, value, ignoreCase);
    }

    public static Filter ne(String property, Object value) {
        return new Filter(property, Operator.ne, value);
    }

    public static Filter ne(String property, Object value, boolean ignoreCase) {
        return new Filter(property, Operator.ne, value, ignoreCase);
    }

    public static Filter gt(String property, Object value) {
        return new Filter(property, Operator.gt, value);
    }

    public static Filter lt(String property, Object value) {
        return new Filter(property, Operator.lt, value);
    }

    public static Filter ge(String property, Object value) {
        return new Filter(property, Operator.ge, value);
    }

    public static Filter le(String property, Object value) {
        return new Filter(property, Operator.le, value);
    }

    public static Filter like(String property, Object value) {
        return new Filter(property, Operator.like, value);
    }

    public static Filter in(String property, Object value) {
        return new Filter(property, Operator.in, value);
    }

    public static Filter isNull(String property) {
        return new Filter(property, Operator.isNull, null);
    }

    public static Filter isNotNull(String property) {
        return new Filter(property, Operator.isNotNull, null);
    }


    public Filter ignoreCase() {
        this.ignoreCase = Boolean.valueOf(true);
        return this;
    }

    public String getProperty() {
        return this.property;
    }

    public void setProperty(String property) {
        this.property = property;
    }

    public Operator getOperator() {
        return this.operator;
    }

    public void setOperator(Operator operator) {
        this.operator = operator;
    }

    public Object getValue() {
        return this.value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public Date getStart() {
        return start;
    }

    public void setStart(Date start) {
        this.start = start;
    }

    public Date getEnd() {
        return end;
    }

    public void setEnd(Date end) {
        this.end = end;
    }

    public Boolean getIgnoreCase() {
        return this.ignoreCase;
    }

    public void setIgnoreCase(Boolean ignoreCase) {
        this.ignoreCase = ignoreCase;
    }


    public Expression and(Expression expression) {
        Expression retVal = new Expression();
        retVal.getSubExpressions().add(this);
        retVal.getSubExpressions().add(expression);
        retVal.getLogicOperators().add(Expression.LogicOperator.and);
        return retVal;
    }

    public Expression and(Filter filter) {
        Expression retVal = new Expression();
        retVal.getSubExpressions().add(this);
        retVal.getSubExpressions().add(filter);
        retVal.getLogicOperators().add(Expression.LogicOperator.and);
        return retVal;
    }

    public Expression or(Expression expression) {
        Expression retVal = new Expression();
        retVal.getSubExpressions().add(this);
        retVal.getSubExpressions().add(expression);
        retVal.getLogicOperators().add(Expression.LogicOperator.or);
        return retVal;
    }

    public Expression or(Filter filter) {
        Expression retVal = new Expression();
        retVal.getSubExpressions().add(this);
        retVal.getSubExpressions().add(filter);
        retVal.getLogicOperators().add(Expression.LogicOperator.or);
        return retVal;
    }


}