package jm.task.core.jdbc.util;

import jm.task.core.jdbc.model.User;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import org.hibernate.service.ServiceRegistry;

import java.sql.*;
import java.util.Properties;

public class Util {
    private static final String URL_db = "jdbc:mysql://localhost:3306/users";
    private static final String login_db = "root";
    private static final String password_db = "1234";
    private static final String driver_db = "com.mysql.cj.jdbc.Driver";
    private static Util INSTANCE;

    private static SessionFactory sessionFactory;

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

    public SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            try {
                Configuration configuration = new Configuration();
                Properties setting = new Properties();
                setting.put(Environment.DRIVER, driver_db);
                setting.put(Environment.URL, URL_db);
                setting.put(Environment.USER, login_db);
                setting.put(Environment.PASS, password_db);
                setting.put(Environment.DIALECT, "org.hibernate.dialect.MySQL8Dialect");

                setting.put(Environment.SHOW_SQL, true);

                configuration.setProperties(setting);
                configuration.addAnnotatedClass(User.class);

                ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder().applySettings(configuration
                        .getProperties()).build();

                sessionFactory = configuration.buildSessionFactory(serviceRegistry);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return sessionFactory;
    }
}