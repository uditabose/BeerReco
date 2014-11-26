package cs9233.project.csvtodb;

import java.sql.Connection;
import java.sql.DriverManager;
import static cs9233.project.csvtodb.Constants.*;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Udita
 */
public class ConnectionUtil {
    
    private Connection connection = null;
    
    private static class Holder {
        private static final ConnectionUtil _instance = new ConnectionUtil();
    }
    
    private ConnectionUtil() {
    }
    
    public static ConnectionUtil getInstance() {
        return Holder._instance;
    }
    
    public Connection getConnection() {
        if (connection == null) {
            try {
                System.out.println("Trying to connect");
                Class.forName("com.mysql.jdbc.Driver").newInstance();
                connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            } catch (SQLException ex) {
                System.err.println(" Cannot create connection : " + ex.getMessage());
            } catch (ClassNotFoundException ex) {
                System.err.println(" Cannot create connection : " + ex.getMessage());
            } catch (InstantiationException ex) {
                System.err.println(" Cannot create connection : " + ex.getMessage());
            } catch (IllegalAccessException ex) {
                System.err.println(" Cannot create connection : " + ex.getMessage());
            }
        }
        return connection;
    }
    
    public void initiateDatabase() {
        Connection aConnection = getConnection();
        try {
            Statement statement = aConnection.createStatement();
            statement.executeQuery("CREATE DATABASE " + DB_NAME);
            statement.executeQuery("use " + DB_NAME);
            statement.executeQuery("CREATE TABLE " + DB_REVIEW_TABLE);
            
        } catch (SQLException ex) {
            System.err.println(" Cannot create database : " + ex.getMessage());
        }
    }
    

}
