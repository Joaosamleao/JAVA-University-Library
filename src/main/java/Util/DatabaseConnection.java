package Util;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DatabaseConnection {   

    public static Connection getConnection() throws SQLException, IOException {

        Properties props = new Properties();

        try (FileInputStream fis = new FileInputStream("config.properties")) {
            props.load(fis);
        }

        String url = props.getProperty("db.url");
        String user = props.getProperty("db.user");
        String password = props.getProperty("db.password");

        if (url == null || user == null || password == null) {
            throw new SQLException("ERROR: Database credentials not found in config.properties");
        }

        return DriverManager.getConnection(url, user, password);
    }

}