package org.jerry.jorm.sqlgenerator;

import org.jerry.jorm.descriptor.EntityDescriptionManager;

import javax.sql.DataSource;

/**
 * Created by Jerry.Yong on 2014/11/5.
 */
public abstract class DDLGenerator {
    protected DataSource dataSource;
    protected EntityDescriptionManager entityDescriptionManager;

    public DataSource getDataSource() {
        return dataSource;
    }

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public EntityDescriptionManager getEntityDescriptionManager() {
        return entityDescriptionManager;
    }

    public void setEntityDescriptionManager(EntityDescriptionManager entityDescriptionManager) {
        this.entityDescriptionManager = entityDescriptionManager;
    }

    public DDLGenerator(DataSource dataSource, EntityDescriptionManager entityDescriptionManager) {
        this.dataSource = dataSource;
        this.entityDescriptionManager = entityDescriptionManager;
    }

    public DDLGenerator() {
    }

    public abstract void update();
}
