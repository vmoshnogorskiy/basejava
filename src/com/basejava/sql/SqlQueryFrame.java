package com.basejava.sql;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public interface SqlQueryFrame<T> {
    T execute(PreparedStatement ps) throws SQLException;
}
