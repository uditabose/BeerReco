package cs9233.project.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author Udita
 */
public class ConnectionUtil {
    
    private Connection tempRdsConnection = null;
    private Connection rdsConnection = null;
    
    private static class Holder {
        private static final ConnectionUtil _instance = new ConnectionUtil();
    }
    
    private ConnectionUtil() {
    }
    
    public static ConnectionUtil getInstance() {
        return Holder._instance;
    }
    
    public Connection getTempRdsConnection() {
        if (tempRdsConnection == null) {
            try {
                System.out.println("Trying to connect");
                Class.forName("com.mysql.jdbc.Driver").newInstance();
                tempRdsConnection = DriverManager.getConnection(Constants.TEMP_RDS.DB_URL, 
                        Constants.TEMP_RDS.DB_USER, Constants.TEMP_RDS.DB_PASSWORD);
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
        return tempRdsConnection;
    }
    
    public Connection getFinalRdsConnection() {
        if (rdsConnection == null) {
            try {
                System.out.println("Trying to connect");
                Class.forName("com.mysql.jdbc.Driver").newInstance();
                rdsConnection = DriverManager.getConnection(Constants.FINAL_RDS.DB_URL, 
                        Constants.FINAL_RDS.DB_USER, Constants.FINAL_RDS.DB_PASSWORD);
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
        return rdsConnection;
    }
    

}
