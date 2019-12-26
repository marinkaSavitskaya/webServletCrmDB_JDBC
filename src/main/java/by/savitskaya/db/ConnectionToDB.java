package by.savitskaya.db;

import by.savitskaya.dao.ClientDao;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * Created by teacher on 24.12.2019.
 */
public class ConnectionToDB {


    private static String jdbcUrl;
    private static String login;
    private static String passwd;
    private static Connection connection = null;

    public static Connection getConnection(){
        System.out.println("Testing connection to PostgreSQL JDBC");

        if (connection != null){
            return connection;
        }

        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println("PostgreSQL JDBC Driver is not found. Include it in your library path ");
            e.printStackTrace();
        }

        System.out.println("PostgreSQL JDBC Driver successfully connected");
        try {
            InputStream is = ConnectionToDB.class.getResourceAsStream("/db.properties");
            Properties props = new Properties();
            props.load(is);
            jdbcUrl = props.getProperty("db.url");
            login = props.getProperty("db.username");
            passwd = props.getProperty("db.password");
            connection = DriverManager
                    .getConnection(jdbcUrl, login, passwd);



        } catch (SQLException e) {
            System.out.println("Connection Failed");
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return connection;
    }
}
