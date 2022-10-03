package jm.task.core.jdbc.util;

import java.sql.*;

public class Util {
    private static final String URL_db = "jdbc:mysql://localhost:3306/users";
    private static final String login_db = "root";
    private static final String password_db = "1234";
    private static final String driver_db = "com.mysql.cj.jdbc.Driver";

    private static Util INSTANCE;

    private Util() {
    }

    public static Util getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new Util();
        }
        return INSTANCE;
    }

    public Connection getConnection() {
        Connection connection = null;
        try {
            Class.forName(driver_db);
            connection = DriverManager.getConnection(URL_db, login_db, password_db);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }
}