package com.github.ravendcode.hello;

import io.github.cdimascio.dotenv.Dotenv;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Config {
//    private static final Config INSTANCE = new Config();
    private Dotenv env;
    private Connection db;

    private static class ConfigHolder {
        private static final Config HOLDER_INSTANCE = new Config();
    }

    private Config() {
        env = Dotenv.load();
        initDb(env);
    }

    public static Config getInstance() {
//        return INSTANCE;
        return ConfigHolder.HOLDER_INSTANCE;
    }

    public Dotenv getEnv() {
        return env;
    }

    public Connection getDb() {
        return db;
    }

    private void initDb(Dotenv env) {
        try {
            String url = String.format(
                    "jdbc:%s://%s:%s/%s",
                    env.get("DB_CONNECTION"),
                    env.get("DB_HOST"),
                    env.get("DB_PORT"),
                    env.get("DB_DATABASE"));

            db = DriverManager.getConnection(
                    url,
                    env.get("DB_USERNAME"),
                    env.get("DB_PASSWORD"));
            if (db == null) {
                System.err.println("Нет соединения с БД!");
                System.exit(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
