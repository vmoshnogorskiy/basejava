package com.basejava;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;

public class Config {
    protected static final File PROPS = new File("./config/resumes.properties");
    private static final Config INSTANCE = new Config();
    private Properties properties = new Properties();
    private File storageDir;
    private File dbUrl;
    private File dbUser;
    private File dbPassword;

    private Config() {
        try (InputStream is = new FileInputStream(PROPS)) {
            properties.load(is);
            storageDir = new File(properties.getProperty("storage.dir"));
            dbUrl = new File(properties.getProperty("db.url"));
            dbUser = new File(properties.getProperty("db.user"));
            dbPassword = new File(properties.getProperty("db.password"));
        } catch (Exception e) {
            throw new IllegalStateException("Invalid config file " + PROPS.getAbsolutePath());
        }
    }

    public static Config getInstance() {
        return INSTANCE;
    }

    public File getStorageDir() {
        return storageDir;
    }

    public File getDbUrl() {
        return dbUrl;
    }

    public File getDbUser() {
        return dbUser;
    }

    public File getDbPassword() {
        return dbPassword;
    }
}