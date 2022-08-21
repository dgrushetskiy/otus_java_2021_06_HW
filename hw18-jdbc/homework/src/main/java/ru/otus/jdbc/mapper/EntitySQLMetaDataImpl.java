package ru.otus.jdbc.mapper;

import java.lang.reflect.Field;
import java.util.Collections;
import java.util.stream.Collectors;

public class EntitySQLMetaDataImpl<T> implements EntitySQLMetaData<T> {

    private final EntityClassMetaData<T> entityClassMetaData;

    public EntitySQLMetaDataImpl(EntityClassMetaData<T> entityClassMetaData) {
        this.entityClassMetaData = entityClassMetaData;
    }

    @Override
    public String getSelectAllSql() {
        return "select * from " + entityClassMetaData.getName();
    }

    @Override
    public String getSelectByIdSql() {
        return "select * from " + entityClassMetaData.getName() + " where " + entityClassMetaData.getIdField().getName() + " = ?";
    }

    @Override
    public String getInsertSql() {
        return "insert into "
                + entityClassMetaData.getName()
                + entityClassMetaData.getFieldsWithoutId().stream().map(Field::getName).collect(Collectors.joining(",", " (", ") values "))
                + Collections.nCopies(entityClassMetaData.getFieldsWithoutId().size(), "?").stream().collect(Collectors.joining(",", "(", ")"));
    }

    @Override
    public String getUpdateSql() {
        return "update "
                + entityClassMetaData.getName()
                + entityClassMetaData.getFieldsWithoutId().stream().map(f -> " set " + f.getName() + " = ?").collect(Collectors.joining(","))
                + " where id = ?";
    }

    @Override
    public EntityClassMetaData<T> getMetadata() {
        return this.entityClassMetaData;
    }
}
