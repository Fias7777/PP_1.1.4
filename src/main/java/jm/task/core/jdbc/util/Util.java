package jm.task.core.jdbc.util;


import jm.task.core.jdbc.model.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class Util {
    private static final String DB_USERNAME = "root";
    private static final String DB_PASSWORD = "root";
    private static final String  DB_URL= "jdbc:mysql://localhost:3306/customer";
    private static final String DB_DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final SessionFactory session = getSession();

    public static Connection getConnection() {
        Connection connection = null;
        try {
            Class.forName(DB_DRIVER);
            connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
            System.out.println("CONNECTION OK");
        } catch (SQLException | ClassNotFoundException e)  {
            System.out.println("CONNECTION ERROR");
            throw new RuntimeException(e);
        }
        return connection;
    }
    private static SessionFactory getSession() {
        Configuration configuration = new Configuration();
        Properties setting = new Properties();
        setting.put(Environment.DRIVER, DB_DRIVER);
        setting.put(Environment.URL, DB_URL);
        setting.put(Environment.USER, DB_USERNAME);
        setting.put(Environment.DIALECT, "org.hibernate.dialect.MySQL8Dialect");
        setting.put(Environment.SHOW_SQL, "true");
        setting.put(Environment.CURRENT_SESSION_CONTEXT_CLASS, "thread");
        setting.put(Environment.HBM2DDL_AUTO, "create-drop");
        configuration.setProperties(setting);

        configuration.addAnnotatedClass(User.class);

        StandardServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                .applySettings(configuration.getProperties())
                .build();
        return configuration.buildSessionFactory(serviceRegistry);
    }
    public static Session getHibernateSession() {
        return session.openSession();
    }
}
