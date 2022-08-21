package ru.otus.jdbc.mapper;

import ru.otus.core.repository.DataTemplate;
import ru.otus.core.repository.DataTemplateException;
import ru.otus.core.repository.executor.DbExecutor;
import ru.otus.exception.CustomJdbcException;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * Сохратяет объект в базу, читает объект из базы
 */
public class DataTemplateJdbc<T> implements DataTemplate<T> {

    private final DbExecutor dbExecutor;
    private final EntitySQLMetaData<T> entitySQLMetaData;

    public DataTemplateJdbc(DbExecutor dbExecutor, EntitySQLMetaData<T> entitySQLMetaData) {
        this.dbExecutor = dbExecutor;
        this.entitySQLMetaData = entitySQLMetaData;
    }

    @Override
    public Optional<T> findById(Connection connection, long id) {
        return dbExecutor.executeSelect(connection,
                entitySQLMetaData.getSelectByIdSql(),
                Collections.singletonList(id),
                rs -> {
                    try {
                        if (rs.next()) {
                            T entity = instantiate();
                            setFields(rs, entity, entitySQLMetaData.getMetadata().getAllFields());
                            return entity;
                        }
                        return null;
                    } catch (Exception e) {
                        throw new DataTemplateException(e);
                    }
                });
    }

    @Override
    public List<T> findAll(Connection connection) {
        return dbExecutor.executeSelect(connection,
                entitySQLMetaData.getSelectAllSql(),
                Collections.emptyList(),
                rs -> {
                    List<T> result = new ArrayList<>();
                    try {
                        while (rs.next()) {
                            // вытащить конструктор
                            T entity = instantiate();
                            // проставить поля
                            setFields(rs, entity, entitySQLMetaData.getMetadata().getAllFields());
                            result.add(entity);
                        }
                        return result;
                    } catch (Exception e) {
                        throw new DataTemplateException(e);
                    }
                }).orElseThrow(() -> new CustomJdbcException("What is it? It's nothing here"));
    }

    @Override
    public long insert(Connection connection, T client) {
        try {
            return dbExecutor.executeStatement(connection, entitySQLMetaData.getInsertSql(),
                    getInsertParameters(client));
        } catch (Exception e) {
            throw new DataTemplateException(e);
        }
    }

    @Override
    public void update(Connection connection, T client) {
        try {
            dbExecutor.executeStatement(connection, entitySQLMetaData.getUpdateSql(),
                    getUpdateParameters(client));
        } catch (Exception e) {
            throw new DataTemplateException(e);
        }
    }


    private List<Object> getInsertParameters(T client) {
        List<Object> parameterList = new ArrayList<>();
        for (Field field : entitySQLMetaData.getMetadata().getFieldsWithoutId()) {
            parameterList.add(getFieldValue(client, field));
        }
        return parameterList;
    }

    private void setFieldValue(T entity, Field field, Object value) {
        try {
            field.setAccessible(true);
            field.set(entity, value);
        } catch (IllegalAccessException e) {
            throw new DataTemplateException(e);
        }
    }

    private Object getFieldValue(T entity, Field field) {
        try {
            field.setAccessible(true);
            return field.get(entity);
        } catch (IllegalAccessException e) {
            throw new DataTemplateException(e);
        }
    }


    private List<Object> getUpdateParameters(T client) {
        List<Object> parameterList = new ArrayList<>();
        for (Field field : entitySQLMetaData.getMetadata().getFieldsWithoutId()) {
            parameterList.add(getFieldValue(client, field));
        }
        parameterList.add(getFieldValue(client, entitySQLMetaData.getMetadata().getIdField()));
        return parameterList;
    }

    private void setFields(ResultSet rs, T entity, List<Field> fields) throws SQLException {
        for (Field field : fields) {
            setFieldValue(entity, field, rs.getObject(field.getName()));
        }
    }

    private T instantiate() throws InstantiationException, IllegalAccessException, java.lang.reflect.InvocationTargetException {
        Constructor<T> constructor = entitySQLMetaData.getMetadata().getConstructor();
        return constructor.newInstance();
    }
}
