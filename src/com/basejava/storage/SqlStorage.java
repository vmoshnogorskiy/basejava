package com.basejava.storage;

import com.basejava.exception.NotExistStorageException;
import com.basejava.model.Resume;
import com.basejava.sql.SqlHelper;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class SqlStorage implements Storage {
    public final SqlHelper sqlHelper;
    private static final Logger LOG = Logger.getLogger(SqlStorage.class.getName());

    public SqlStorage(SqlHelper sqlHelper) {
        this.sqlHelper = sqlHelper;
    }

    @Override
    public void clear() {
        sqlHelper.execute(ps -> {
            ps.execute();
            return null;
        }, "Delete from resume");
    }

    @Override
    public void update(Resume r) {
        LOG.info("Update " + r);
        sqlHelper.execute(ps -> {
            ps.setString(1, r.getFullName());
            ps.setString(2, r.getUuid());
            ps.execute();
            return null;
        }, "update resume set full_name = ? where uuid = ?");
    }

    @Override
    public void save(Resume r) {
        LOG.info("Save " + r);
        sqlHelper.execute(ps -> {
            ps.setString(1, r.getUuid());
            ps.setString(2, r.getFullName());
            ps.execute();
            return null;
        }, "insert into resume (uuid, full_name) values (?, ?)");
    }

    @Override
    public Resume get(String uuid) {
        LOG.info("Get " + uuid);
        return sqlHelper.execute(ps -> {
            ps.setString(1, uuid);
            ResultSet rs = ps.executeQuery();
            if (!rs.next()) {
                throw new NotExistStorageException(uuid);
            }
            return new Resume(uuid, rs.getString("full_name"));
        }, "select * from resume r where r.uuid = ?");
    }

    @Override
    public void delete(String uuid) {
        LOG.info("Delete " + uuid);
        sqlHelper.execute((ps) -> {
            ps.setString(1, uuid);
            ps.execute();
            return null;
        }, "delete from resume where uuid = ?");
    }

    @Override
    public List<Resume> getAllSorted() {
        LOG.info("getAllSorted");
        return sqlHelper.execute(ps -> {
            ResultSet rs = ps.executeQuery();
            List<Resume> list = new ArrayList<>();
            Resume r;
            while (rs.next()) {
                r = new Resume(rs.getString("uuid").trim(), rs.getString("full_name"));
                list.add(r);
            }
            return list;
        }, "select * from resume order by full_name ASC, uuid ASC");
    }

    @Override
    public int size() {
        LOG.info("size");
        return sqlHelper.execute(ps -> {
            ResultSet rs = ps.executeQuery();
            rs.next();
            return rs.getInt("count");
        }, "select count(*) as count from resume");
    }
}
