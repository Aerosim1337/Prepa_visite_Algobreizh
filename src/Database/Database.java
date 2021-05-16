package Database;

import java.util.Properties;
import java.sql.*;

public class Database extends Exception {
    static Connection connection;
    static Statement stmt;

    public static ResultSet execute(String requete) {
        ResultSet result;
        try {
            //String url = "jdbc:mysql://192.168.150.45/algobreizh";
            String url = "jdbc:mysql://localhost/algobreizh";
            Properties info = new Properties();
            //info.put("user", "user"); info.put("password", "password");
            info.put("user", "root");
            info.put("password", "");

            connection = DriverManager.getConnection(url, info); //set connection to database
            stmt = connection.createStatement(); //create a statement
            result = stmt.executeQuery(requete); //execute the query
        } catch (Exception e) {
            result = null;
        }
        return result;
    }

    //only for non select query
    public static void execute_update(String requete) throws SQLException {
        //String url = "jdbc:mysql://192.168.150.45/algobreizh";
        String url = "jdbc:mysql://localhost/algobreizh";
        Properties info = new Properties();
        info.put("user", "root");
        info.put("password", "");
        //info.put("user", "user"); info.put("password", "password");
        connection = DriverManager.getConnection(url, info); //set connection to database
        stmt = connection.createStatement(); //create a statement
        stmt.executeUpdate(requete); //execute the query
    }
}