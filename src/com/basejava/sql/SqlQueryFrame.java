package com.basejava.sql;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public interface SqlQueryFrame {
    Object execute(PreparedStatement ps) throws SQLException;
}
