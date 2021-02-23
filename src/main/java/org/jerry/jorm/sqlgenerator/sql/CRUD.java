package org.jerry.jorm.sqlgenerator.sql;

/**
 * Created by yong_pliang on 14/10/29.
 */
public class CRUD {
    private Class<?> clazz;
    private String selectByIdSql;
    private String selectAllSql;
    private String deleteByIdSql;


    public String getSelectByIdSql() {
        return selectByIdSql;
    }

    public void setSelectByIdSql(String selectByIdSql) {
        this.selectByIdSql = selectByIdSql;
    }

    public String getSelectAllSql() {
        return selectAllSql;
    }

    public void setSelectAllSql(String selectAllSql) {
        this.selectAllSql = selectAllSql;
    }

    public String getDeleteByIdSql() {
        return deleteByIdSql;
    }


    public void setDeleteByIdSql(String deleteByIdSql) {
        this.deleteByIdSql = deleteByIdSql;
    }




    public CRUD(Class<?> clazz) {
        this.clazz = clazz;
    }
}
