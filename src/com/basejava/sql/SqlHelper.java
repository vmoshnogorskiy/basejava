package com.basejava.sql;

import com.basejava.exception.StorageException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SqlHelper {
    public final ConnectionFactory connectionFactory;

    public SqlHelper(String dbURL, String dbUser, String dbPassword) {
        connectionFactory = () -> DriverManager.getConnection(dbURL, dbUser, dbPassword);
    }

    public <T> T execute(SqlQueryFrame <T> sqlQueryFrame, String sqlCode) {
        try (Connection conn = connectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sqlCode)) {
            return sqlQueryFrame.execute(ps);
        } catch (SQLException e) {
            throw new StorageException(e);
        }
    }
}
