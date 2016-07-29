package com.yimei.boot.config;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedTypes;

import java.sql.*;
import java.time.LocalDate;

/**
 * Created by joe on 11/6/14.
 */
@MappedTypes(LocalDate.class)
public class LocalDateTypeHandler extends BaseTypeHandler<LocalDate> {

    @Override
    public void setNonNullParameter(PreparedStatement preparedStatement, int i, LocalDate localDate, JdbcType jdbcType) throws SQLException {
        preparedStatement.setDate(i, Date.valueOf(localDate));
    }

    @Override
    public LocalDate getNullableResult(ResultSet resultSet, String columnName) throws SQLException {
        Date date = resultSet.getDate(columnName);
        if(date!=null)
            return date.toLocalDate();
        return null;
    }

    @Override
    public LocalDate getNullableResult(ResultSet resultSet, int columnIndex) throws SQLException {
        Date date = resultSet.getDate(columnIndex);
        if(date!=null)
            return date.toLocalDate();
        return null;
    }

    @Override
    public LocalDate getNullableResult(CallableStatement callableStatement, int columnIndex) throws SQLException {
        Date date = callableStatement.getDate(columnIndex);
        if(date!=null)
            return date.toLocalDate();
        return null;
    }
}
