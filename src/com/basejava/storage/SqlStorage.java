package com.basejava.storage;

import com.basejava.exception.NotExistStorageException;
import com.basejava.model.ContactType;
import com.basejava.model.Resume;
import com.basejava.sql.SqlHelper;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

public class SqlStorage implements Storage {
    public final SqlHelper sqlHelper;
    private static final Logger LOG = Logger.getLogger(SqlStorage.class.getName());

    public SqlStorage(String dbURL, String dbUser, String dbPassword) {

        sqlHelper = new SqlHelper(() -> DriverManager.getConnection(dbURL, dbUser, dbPassword));
    }

    @Override
    public void clear() {
        sqlHelper.execute("DELETE FROM resume", ps -> {
            ps.execute();
            return null;
        });
    }

    @Override
    public void update(Resume r) {
        LOG.info("Update " + r);
        sqlHelper.transactionalExecute(conn -> {
            resumeUpsert("UPDATE resume SET full_name = ? WHERE uuid = ?", r, "UPDATE", conn);
            computeBatchTransaction("UPDATE contact SET value = ? WHERE resume_uuid = ? AND type = ?", r, conn);
            return null;
        });
    }

    @Override
    public void save(Resume r) {
        LOG.info("Save " + r);
        sqlHelper.transactionalExecute(conn -> {
            resumeUpsert("INSERT INTO resume (full_name, uuid) VALUES (?, ?)", r, "INSERT", conn);
            computeBatchTransaction("INSERT INTO contact (value, resume_uuid, type) VALUES(?, ?, ?)", r, conn);
            return null;
        });
    }

    @Override
    public void delete(String uuid) {
        LOG.info("Delete " + uuid);
        sqlHelper.execute("DELETE FROM resume WHERE uuid = ?", (ps) -> {
            ps.setString(1, uuid);
            if (ps.executeUpdate() == 0) {
                throw new NotExistStorageException(uuid);
            }
            return null;
        });
    }

    @Override
    public Resume get(String uuid) {
        LOG.info("Get " + uuid);
        return sqlHelper.execute("" +
                "    SELECT * FROM resume r " +
                " LEFT JOIN contact c " +
                "        ON r.uuid = c.resume_uuid" +
                "     WHERE r.uuid = ? ", ps -> {
            ps.setString(1, uuid);
            ResultSet rs = ps.executeQuery();
            if (!rs.next()) {
                throw new NotExistStorageException(uuid);
            }
            Resume r = new Resume(uuid, rs.getString("full_name"));
            do {
                String value = rs.getString("value");
                ContactType type = ContactType.valueOf(rs.getString("type"));
                r.setContact(type, value);
            } while (rs.next());
            return r;
        });
    }

    @Override
    public List<Resume> getAllSorted() {
        LOG.info("getAllSorted");
        return sqlHelper.execute("" +
                "SELECT * FROM resume r " +
                "LEFT JOIN contact c " +
                "       ON r.uuid = c.resume_uuid " +
                "ORDER BY full_name ASC, uuid ASC", ps -> {
            ResultSet rs = ps.executeQuery();
            List<Resume> list = new ArrayList<>();
            if (!rs.next()) {
                return list;
            }
            Resume r = new Resume(rs.getString("uuid"), rs.getString("full_name"));
            String resumeUuid = rs.getString("uuid");
            do {
                if (!resumeUuid.equals(rs.getString("uuid"))) {
                    list.add(r);
                    resumeUuid = rs.getString("uuid");
                    r = new Resume(resumeUuid, rs.getString("full_name"));
                }
                ContactType type = ContactType.valueOf(rs.getString("type"));
                r.setContact(type, rs.getString("value"));
            } while (rs.next());
            list.add(r);
            return list;
        });
    }

    @Override
    public int size() {
        LOG.info("size");
        return sqlHelper.execute("SELECT count(*) AS count FROM resume", ps -> {
            ResultSet rs = ps.executeQuery();
            rs.next();
            return rs.getInt("count");
        });
    }

    private void computeBatchTransaction(String sqlCode, Resume r, Connection conn) throws SQLException {
        try (PreparedStatement ps = conn.prepareStatement(sqlCode)) {
            for (Map.Entry<ContactType, String> e : r.getContacts().entrySet()) {
                ps.setString(1, e.getValue());
                ps.setString(2, r.getUuid());
                ps.setString(3, e.getKey().name());
                ps.addBatch();
            }
            ps.executeBatch();
        }
    }

    private void resumeUpsert(String sqlCode, Resume r, String operation, Connection conn) throws SQLException {
        try (PreparedStatement ps = conn.prepareStatement(sqlCode)) {
            ps.setString(1, r.getFullName());
            ps.setString(2, r.getUuid());
            if ("INSERT".equals(operation)) {
                ps.execute();
            }
            if ("UPDATE".equals(operation)) {
                if (ps.executeUpdate() == 0) {
                    throw new NotExistStorageException(r.getUuid());
                }
            }
        }
    }
}
