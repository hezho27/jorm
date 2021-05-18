package org.jerry.jorm.sqlgenerator.sqlserver;

import org.jerry.jorm.descriptor.EntityDescriptionManager;
import org.jerry.jorm.descriptor.EntityDescriptor;
import org.jerry.jorm.descriptor.EntityPropertyDescriptor;
import org.jerry.jorm.sqlgenerator.DDLGenerator;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Jerry.Yong on 2014/11/5.
 */
public class SQLServerDDLGenerator extends DDLGenerator {
    List<String> ddls = new ArrayList<String>();

    @Override
    public void update() {
        Connection connection = null;
        try {
            connection = dataSource.getConnection();
            DatabaseMetaData databaseMetaData = connection.getMetaData();
            String[] types = {"TABLE"};
            Map<String, EntityDescriptor> descriptorMap = entityDescriptionManager.getEntityDescriptorMap();
            for (Map.Entry<String, EntityDescriptor> entry : descriptorMap.entrySet()) {
                String tableName = entry.getValue().getTableName();
                String sql = "SELECT TOP 0 * FROM  [" + tableName+"]";
                ResultSet tableResultSet = databaseMetaData.getTables(null, null, tableName, types);
                EntityDescriptor entityDescriptor = entry.getValue();
                List<EntityPropertyDescriptor> entityPropertyDescriptors = entityDescriptor.getEntityPropertyDescriptors();
                StringBuffer ddl = new StringBuffer();
                if (tableResultSet.next()) {
                    //update  只实现了新增字段部分
                    PreparedStatement statement = connection.prepareStatement(sql);
                    ResultSetMetaData metaData = statement.executeQuery().getMetaData();
                    int count = metaData.getColumnCount();
                    List<String> dbColnmns = new ArrayList<String>();
                    for (int i = 1; i <= count; i++) {
                        String columnName = metaData.getColumnName(i);
                        dbColnmns.add(columnName);
                    }


                    for (EntityPropertyDescriptor entityPropertyDescriptor : entityPropertyDescriptors) {
                        String colName = entityPropertyDescriptor.getColName();
                        Class clazz = entityPropertyDescriptor.getType();
                        if (!dbColnmns.contains(colName)) {
                            ddl.append("  [" + colName + "] " + SQLServerTypeMapper.mapping(clazz, entityPropertyDescriptor.getLength(), entityPropertyDescriptor.isLob(), entityPropertyDescriptor.getTemporalType()));
                            if (!entityPropertyDescriptor.isNullable()) {
                                ddl.append(" NOT NULL ");
                            }

                            if (entityPropertyDescriptor.isUnique()) {
                                ddl.append(" UNIQUE ");
                            }

                            ddl.append(" ,");
                        }
                    }

                    if (ddl.length() > 0) {
                        ddls.add("alter table [" + tableName + "] add " + ddl.deleteCharAt(ddl.length() - 1));
                    }

                } else { //创建表
                    ddl.append("create table [" + tableName + "] (");
                    for (EntityPropertyDescriptor entityPropertyDescriptor : entityPropertyDescriptors) {
                        String colName = entityPropertyDescriptor.getColName();
                        Class clazz = entityPropertyDescriptor.getType();
                        ddl.append(" [" + colName + "] " + SQLServerTypeMapper.mapping(clazz, entityPropertyDescriptor.getLength(), entityPropertyDescriptor.isLob(), entityPropertyDescriptor.getTemporalType()));
                        if (!entityPropertyDescriptor.isNullable()) {
                            ddl.append(" NOT NULL ");
                        }

                        if (entityPropertyDescriptor.isId() && entityPropertyDescriptor.isAuto()) {
                            ddl.append(" IDENTITY(1, 1)  ");
                        }

                        if (entityPropertyDescriptor.isUnique()) {
                            ddl.append(" UNIQUE ");
                        }

                        ddl.append(" , ");
                    }
                    ddl.append(" PRIMARY KEY ([" + entityDescriptor.getIdDescriptor().getColName() + "]) )");
                    ddls.add(ddl.toString());
                }
            }

            for (String ddl : ddls) {
                PreparedStatement statement = connection.prepareStatement(ddl);
                statement.execute();
                System.out.println(ddl);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }

        }

    }


    public SQLServerDDLGenerator() {

    }

    public SQLServerDDLGenerator(DataSource dataSource, EntityDescriptionManager entityDescriptionManager) {
        super(dataSource, entityDescriptionManager);
    }
}
