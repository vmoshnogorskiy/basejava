package com.basejava.storage;

import com.basejava.exception.NotExistStorageException;
import com.basejava.exception.StorageException;
import com.basejava.model.Resume;
import com.basejava.sql.ConnectionFactory;
import com.basejava.sql.SqlQueryFrame;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class SqlStorage implements Storage {
    public final ConnectionFactory connectionFactory;
    private static final Logger LOG = Logger.getLogger(SqlStorage.class.getName());

    public SqlStorage(String dbURL, String dbUser, String dbPassword) {
        connectionFactory = () -> DriverManager.getConnection(dbURL, dbUser, dbPassword);
    }

    @Override
    public void clear() {
        transactionExecute(new SqlQueryFrame() {
            @Override
            public Object execute(PreparedStatement ps) throws SQLException {
                ps.execute();
                return null;
            }
        }, "Delete from resume");
    }

    @Override
    public void update(Resume r) {
        LOG.info("Update " + r);
        transactionExecute(new SqlQueryFrame() {
            @Override
            public Object execute(PreparedStatement ps) throws SQLException {
                ps.setString(1, r.getFullName());
                ps.setString(2, r.getUuid());
                ps.execute();
                return null;
            }
        }, "update resume set full_name = ? where uuid = ?");
    }

    @Override
    public void save(Resume r) {
        LOG.info("Save " + r);
        transactionExecute(new SqlQueryFrame() {
            @Override
            public Object execute(PreparedStatement ps) throws SQLException {
                ps.setString(1, r.getUuid());
                ps.setString(2, r.getFullName());
                ps.execute();
                return null;
            }
        }, "insert into resume (uuid, full_name) values (?, ?)");
    }

    @Override
    public Resume get(String uuid) {
        LOG.info("Get " + uuid);
        return (Resume) transactionExecute(new SqlQueryFrame() {
            @Override
            public Object execute(PreparedStatement ps) throws SQLException {
                ps.setString(1, uuid);
                ResultSet rs = ps.executeQuery();
                if (!rs.next()) {
                    throw new NotExistStorageException(uuid);
                }
                Resume r = new Resume(uuid, rs.getString("full_name"));
                return r;
            }
        }, "select * from resume r where r.uuid = ?");
    }

    @Override
    public void delete(String uuid) {
        LOG.info("Delete " + uuid);
        transactionExecute(new SqlQueryFrame() {
            @Override
            public Object execute(PreparedStatement ps) throws SQLException {
                ps.setString(1, uuid);
                ps.execute();
                return null;
            }
        }, "delete from resume where uuid = ?");
    }

    @Override
    public List<Resume> getAllSorted() {
        LOG.info("getAllSorted");
        return (List<Resume>) transactionExecute(new SqlQueryFrame() {
            @Override
            public Object execute(PreparedStatement ps) throws SQLException {
                ResultSet rs = ps.executeQuery();
                List<Resume> list = new ArrayList<>();
                Resume r;
                while (rs.next()) {
                    r = new Resume(rs.getString("uuid").trim(), rs.getString("full_name"));
                    list.add(r);
                }
                return list;
            }
        }, "select * from resume order by full_name ASC, uuid ASC");
    }

    @Override
    public int size() {
        LOG.info("size");
        return (int) transactionExecute(new SqlQueryFrame() {
            @Override
            public Object execute(PreparedStatement ps) throws SQLException {
                ResultSet rs = ps.executeQuery();
                rs.next();
                return rs.getInt("count");
            }
        }, "select count(*) as count from resume");
    }

    private Object transactionExecute(SqlQueryFrame sqlQueryFrame, String sqlCode) {
        try (Connection conn = connectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sqlCode)) {
            return sqlQueryFrame.execute(ps);
        } catch (SQLException e) {
            throw new StorageException(e);
        }
    }
}
