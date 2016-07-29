package com.yimei.boot.config;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedTypes;

import java.sql.*;
import java.time.LocalDateTime;

/**
 * Created by joe on 11/6/14.
 */
@MappedTypes(LocalDateTime.class)
public class LocalDateTimeTypeHandler extends BaseTypeHandler<LocalDateTime> {
    @Override
    public void setNonNullParameter(PreparedStatement preparedStatement, int i, LocalDateTime localDateTime, JdbcType jdbcType) throws SQLException {
        preparedStatement.setTimestamp(i, Timestamp.valueOf(localDateTime));
    }

    @Override
    public LocalDateTime getNullableResult(ResultSet resultSet, String columnName) throws SQLException {
        Timestamp dateTime = resultSet.getTimestamp(columnName);
        if(dateTime!=null)
            return dateTime.toLocalDateTime();
        return null;
    }

    @Override
    public LocalDateTime getNullableResult(ResultSet resultSet, int columnIndex) throws SQLException {
        Timestamp dateTime = resultSet.getTimestamp(columnIndex);
        if(dateTime!=null)
            return dateTime.toLocalDateTime();
        return null;
    }

    @Override
    public LocalDateTime getNullableResult(CallableStatement callableStatement, int columnIndex) throws SQLException {
        Timestamp dateTime = callableStatement.getTimestamp(columnIndex);
        if(dateTime!=null)
            return dateTime.toLocalDateTime();
        return null;
    }
}
