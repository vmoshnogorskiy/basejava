package com.basejava.sql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SqlHelper {
    public final ConnectionFactory connectionFactory;

    public SqlHelper(ConnectionFactory connectionFactory) {
        this.connectionFactory = connectionFactory;
    }

    public <T> T execute(String sqlCode, SqlQueryFrame<T> sqlQueryFrame) {
        try (Connection conn = connectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sqlCode)) {
            return sqlQueryFrame.execute(ps);
        } catch (SQLException e) {
            throw ExceptionConverter.convert(e);
        }
    }
}
