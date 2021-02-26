package org.jerry.jorm;

import org.jerry.jorm.descriptor.EntityDescriptionManager;
import org.jerry.jorm.sqlgenerator.Dialect;
import org.jerry.jorm.sqlgenerator.ddl.DDLGenerator;
import org.jerry.jorm.sqlgenerator.sql.SQLGenerator;

import javax.sql.DataSource;
import java.util.Set;

/**
 * Created by yong_pliang on 14/10/29.
 */
public class Config {
    private String entityPackages;
    private DataSource dataSource;
    private Dialect detail;
    private DDLGenerator ddlGenerator;
    private SQLGenerator sqlGenerator;
    private Set<Class<?>> entityClasses;
    private EntityDescriptionManager entityDescriptionManager;
    private boolean showSql = false;

    public Config() {

    }


    public void init() {
        entityClasses = EntityScanner.scan(entityPackages.replace(" ", "").split(","));
        entityDescriptionManager = new EntityDescriptionManager(entityClasses);
        ddlGenerator.setDataSource(dataSource);
        ddlGenerator.setEntityDescriptionManager(entityDescriptionManager);
        sqlGenerator.setShowSql(showSql);
        ddlGenerator.update();


    }

    public String getEntityPackages() {
        return entityPackages;
    }

    public void setEntityPackages(String entityPackages) {
        this.entityPackages = entityPackages;
    }

    public DataSource getDataSource() {
        return dataSource;
    }

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public SQLGenerator getSqlGenerator() {
        return sqlGenerator;
    }

    public void setSqlGenerator(SQLGenerator sqlGenerator) {
        this.sqlGenerator = sqlGenerator;
    }

    public EntityDescriptionManager getEntityDescriptionManager() {
        return entityDescriptionManager;
    }

    public void setEntityDescriptionManager(EntityDescriptionManager entityDescriptionManager) {
        this.entityDescriptionManager = entityDescriptionManager;
    }

    public Dialect getDetail() {
        return detail;
    }

    public void setDetail(Dialect detail) {
        this.ddlGenerator = detail.getDdlGenerator();
        this.sqlGenerator = detail.getSqlGenerator();
        this.detail = detail;
    }

    public DDLGenerator getDdlGenerator() {
        return ddlGenerator;
    }

    public void setDdlGenerator(DDLGenerator ddlGenerator) {
        this.ddlGenerator = ddlGenerator;
    }

    public boolean isShowSql() {
        return showSql;
    }

    public void setShowSql(boolean showSql) {
        this.showSql = showSql;
    }
}

