package ru.itis.homework;

import javax.sql.DataSource;

public class EntityManager {
    private DataSource dataSource;

    public EntityManager(DataSource dataSource) {
        this.dataSource = dataSource;
    }



    // createTable("account", User.class);
    // сгенерировать CREATE TABLE на основе класса
    // create table account ( id integer, firstName varchar(255), ...))
    public <T> void createTable(String tableName, Class<T> entityClass) {
        StringBuilder request = new StringBuilder("CREATE TABLE " + tableName + " (");
        Field[] fields = entityClass.getDeclaredFields();

        for (Field field : fields) {
            field.setAccessible(true);
        }


        for (int i = 0; i < fields.length; i++) {
            String sqlTypeName = "";

            switch (fields[i].getType().getSimpleName()) {

                case "int":
                    sqlTypeName = "INTEGER";
                    break;

                case "double":
                    sqlTypeName = "DOUBLE";
                    break;

                case "long":
                    sqlTypeName = "BIGINT";
                    break;


                case "String":
                    sqlTypeName = "VARCHAR";
                    break;

                case "boolean":
                    sqlTypeName = "BIT";
                    break;

                case "byte[]":
                    sqlTypeName = "LONGVARBINARY";
                    break;

                case "Date":
                    sqlTypeName = "DATE";
                    break;

                case "Time":
                    sqlTypeName = "TIME";
                    break;

                case "Timestamp":
                    sqlTypeName = "TIMESTAMP";
                    break;
            }

            request.append(fields[i].getName()).append(" ").append(sqlTypeName);

            if(i = fields.length - 1){
                request.append(");");
            }else{
                request.append(", ");
            }
        }

        try {
            this.dataSource.getConnection().prepareStatement(String.valueOf(request)).executeUpdate();
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }

    // сканируем его поля
    // сканируем значения этих полей
    // генерируем insert into
    public void save(String tableName, Object entity) {
        Class<?> classOfEntity = entity.getClass();

        StringBuilder request = new StringBuilder("INSERT INTO " + tableName + " (");
        Field[] fields = classOfEntity.getDeclaredFields();

        for (Field field : fields) {
            field.setAccessible(true);
        }


        for (int i = 0; i < fields.length; i++) {
            request.append(fields[i].getName());
            if(i = fields.length - 1){
                request.append(") VALUES (");
            }else{
                request.append(", ");
            }
        }
        for (int i = 0; i < fields.length; i++) {
            try {
                String value;
                if (String.class.equals(fields[i].getType())) {
                    value = "'" + fields[i].get(entity) + "'";
                } else {
                    value = String.valueOf(fields[i].get(entity));
                }
                request.append(sqlStyle);
            } catch (IllegalAccessException e) {
                throw new IllegalStateException(e);
            }

            if(i = fields.length - 1){
                request.append(");");
            }else{
                request.append(", ");
            }
        }

        try {
            dataSource.getConnection().prepareStatement(String.valueOf(request)).executeUpdate();
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }

    // User user = entityManager.findById("account", User.class, Long.class, 10L);
    // сгенеририровать select
    public <T, ID> T findById(String tableName, Class<T> resultType, Class<ID> idType, ID idValue) {
        Field[] fields = resultType.getDeclaredFields();

        for (Field field : fields) {
            field.setAccessible(true);
        }

        String request = "select * from " + tableName + " " + "where id = " + idValue + ";";
        ResultSet resultSet;

        try {
            resultSet = this.dataSource.getConnection().prepareStatement(String.valueOf(request)).executeUpdate();

        } catch (SQLException e) {
            throw new IllegalArgumentException(e);
        }
        T entity;
        try {
            entity = resultType.getConstructor().newInstance();
            for (Field field : fields) {
                String name = field.getName();
                field.set(entity, resultSet.getString(name));
            }
        } catch (IllegalAccessException | InstantiationException | SQLException | NoSuchMethodException | InvocationTargetException e) {
            throw new IllegalArgumentException(e);
        }

        return entity;

    }
}
