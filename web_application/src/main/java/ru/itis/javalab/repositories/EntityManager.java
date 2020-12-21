package ru.itis.javalab.repositories;

import javax.sql.DataSource;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.sql.ResultSet;
import java.sql.SQLException;

public class EntityManager {

    private DataSource dataSource;
    private SimpleJdbcTemplate simpleJdbcTemplate;

    public EntityManager(DataSource dataSource) {
        this.dataSource = dataSource;
        this.simpleJdbcTemplate = new SimpleJdbcTemplate(dataSource);
    }

    // createTable("account", User.class);
    public <T> void createTable(String tableName, Class<T> entityClass) {
        StringBuilder sql = new StringBuilder("create table " + tableName + "( ");
        Field [] fields = entityClass.getDeclaredFields();
        for (Field field : fields) {
            if(!field.isAccessible()){
                field.setAccessible(true);
            }
        }
        for (Field field : fields) {
            String fieldType = field.getType().getSimpleName();
            switch (fieldType) {
                case ("String"):
                    sql.append(field.getName()).append(" ");
                    sql.append("varchar(255), ");
                    break;
                case ("Long"):
                    sql.append(field.getName()).append(" ");
                    sql.append("bigint, ");
                    break;
                case ("Boolean"):
                    sql.append(field.getName()).append(" ");
                    sql.append("boolean, ");
                    break;
                case ("Integer"):
                    sql.append(field.getName()).append(" ");
                    sql.append("integer, ");
                    break;
                default:
                    throw new IllegalStateException();
            }
        }
        sql.append(");");
        this.simpleJdbcTemplate.voidQuery(sql.toString());
    }

    public void save(String tableName, Object entity) {
        Class<?> classOfEntity = entity.getClass();
        StringBuilder sql = new StringBuilder("insert into " + tableName + " values (");
        Field [] fields = classOfEntity.getFields();
        for (Field field : fields) {
            if(!field.isAccessible()){
                field.setAccessible(true);
            }
        }
        for (Field field : fields) {
            String fieldType = field.getType().getSimpleName();
            switch (fieldType) {
                case ("String"):
                    try {
                        sql.append("'").append(field.get(entity)).append("',");
                    } catch (IllegalAccessException e) {
                        throw new IllegalStateException(e);
                    }
                case ("Long"):
                    try {
                        sql.append(field.get(entity)).append(",");
                    } catch (IllegalAccessException e) {
                        throw new IllegalStateException(e);
                    }
                case ("Boolean"):
                    try {
                        sql.append("'").append(field.get(entity)).append("',");
                    } catch (IllegalAccessException e) {
                        throw new IllegalStateException(e);
                    }
                case ("Integer"):
                    try {
                        sql.append(field.get(entity)).append(",");
                    } catch (IllegalAccessException e) {
                        throw new IllegalStateException(e);
                    }
            }
        }
        sql.append(");");
        this.simpleJdbcTemplate.voidQuery(sql.toString());
    }

    // User user = entityManager.findById("account", User.class, Long.class, 10L);
    public <T, ID> T findById(String tableName, Class<T> resultType, Class<ID> idType, ID idValue) {
        StringBuilder sql = new StringBuilder("select ");
        Field [] fields = resultType.getDeclaredFields();
        for (Field field : fields) {
            sql.append(field.getName());
        }
        sql.append(" from ").append(tableName).append(" where id = ").append(idValue.toString());
        ResultSet resultSet;
        try {
            resultSet = this.dataSource.getConnection().prepareStatement(sql.toString()).executeQuery();
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }

        T entity;
        try {
            entity = resultType.getConstructor().newInstance();
            for (Field field : fields) {
                String name = field.getName();
                String value = resultSet.getString(name);
                field.set(entity, field.getType().getConstructor(String.class).newInstance(value));
            }
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException | SQLException e) {
            throw new IllegalStateException(e);
        }
        return entity;
    }

}
