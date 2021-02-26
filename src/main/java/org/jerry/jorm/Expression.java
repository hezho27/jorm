package org.jerry.jorm;

import java.util.ArrayList;
import java.util.List;

/**
 * 查询表达式
 * Created by Jerry.Yong on 2014/11/21.
 */
public class Expression implements Filterable {

    public enum LogicOperator {
        not, and, or;

        public static LogicOperator fromString(String value) {
            return valueOf(value.toLowerCase());
        }
    }

    private List<Filterable> subExpressions = new ArrayList<Filterable>(); //子表达式集合
    private List<LogicOperator> logicOperators = new ArrayList<LogicOperator>();//运算符
    private boolean parentheses = false;//表达式是否已加括号
    private boolean not = false;//是否not


    public Expression and(Expression expression) {
        Expression retVal;
        if (parentheses) {//表明当前表达式已经加括号
            retVal = new Expression();
            retVal.subExpressions.add(this);
        } else {
            retVal = this;
        }
        if (retVal.subExpressions.size() > 0) {
            retVal.logicOperators.add(LogicOperator.and);
        }
        retVal.subExpressions.add(expression);
        return retVal;
    }

    public Expression and(Filter filter) {
        Expression retVal;
        if (parentheses) {//表明当前表达式已经加括号
            retVal = new Expression();
            retVal.subExpressions.add(this);
        } else {
            retVal = this;
        }
        if (retVal.subExpressions.size() > 0) {
            retVal.logicOperators.add(LogicOperator.and);
        }
        retVal.subExpressions.add(filter);
        return retVal;
    }

    public Expression or(Expression expression) {
        Expression retVal;
        if (parentheses) {//表明当前表达式已经加括号
            retVal = new Expression();
            retVal.subExpressions.add(this);
        } else {
            retVal = this;
        }
        if (retVal.subExpressions.size() > 0) {
            retVal.logicOperators.add(LogicOperator.or);
        }
        retVal.subExpressions.add(expression);
        return retVal;
    }

    public Expression or(Filter filter) {
        Expression retVal;
        if (parentheses) {//表明当前表达式已经加括号
            retVal = new Expression();
            retVal.subExpressions.add(this);
        } else {
            retVal = this;
        }
        if (retVal.subExpressions.size() > 0) {
            retVal.logicOperators.add(LogicOperator.or);
        }
        retVal.subExpressions.add(filter);
        return retVal;
    }

    public Expression not() {
        parentheses = true;
        this.not = !not;
        return this;
    }

    /**
     * 给表达式加括号
     *
     * @return
     */
    public Expression parentheses() {
        parentheses = true;
        return this;
    }

    public List<Filterable> getSubExpressions() {
        return subExpressions;
    }

    public List<LogicOperator> getLogicOperators() {
        return logicOperators;
    }

    public boolean isParentheses() {
        return parentheses;
    }

    public boolean isNot() {
        return not;
    }

    public Expression() {
    }


    public static void main(String[] args) {


        Filter filter1 = Filter.eq("a", "");
        Filter filter2 = Filter.eq("b", "");
        Filter filter3 = Filter.eq("c", "");
        Filter filter4 = Filter.eq("d", "");
//        //构成 where a=:a and ( b=:b or b=:b) and c=:c 语句
//        Expression expression = filter1.and(filter2.or(filter3).parentheses()).and(filter4);
        // a =:a and b =:b and c =:c and d =:d
        Expression expression = filter1.and(filter2).and(filter3).and(filter4);
//        Expression expression = new Expression();
        StringBuffer retVal = new StringBuffer();
        List<Filter> filters = new ArrayList<Filter>();
        r(expression, retVal, filters);
        System.out.println(retVal);
        System.out.println(filters);


    }


    public static void r(Filterable filterable, StringBuffer retVal, List<Filter> filters) {
        if (filterable instanceof Filter) {
            Filter filter = (Filter) filterable;
            retVal.append(" " + filter.getProperty() + " =:" + filter.getProperty() + " ");
            if (filters != null) {
                filters.add(filter);
            }

        } else {
            Expression expression = (Expression) filterable;
            List<Filterable> filterableList = expression.getSubExpressions();
            int size = expression.getSubExpressions().size();
            if (size <= 0) {
                return;
            }
            if (expression.not) {
                retVal.append("!");
            }
            boolean parentheses = expression.parentheses;
            if (parentheses) {
                retVal.append(" (");
            }
            List<LogicOperator> logicOperatorList = expression.getLogicOperators();
            for (int i = 0; i < size; i++) {
                r(filterableList.get(i), retVal, filters);
                if (i < logicOperatorList.size()) {
                    retVal.append(logicOperatorList.get(i));
                }

            }
            if (parentheses) {
                retVal.append(") ");
            }
        }


    }


}
