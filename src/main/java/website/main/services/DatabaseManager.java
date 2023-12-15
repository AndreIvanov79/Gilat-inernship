package website.main.services;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseManager {
	
	private static final String URL = "jdbc:postgresql://localhost:5436/gg";
    private static final String USER = "postgres";
    private static final String PASSWORD = "postgres";
    
    private static Connection connection;
    
    private DatabaseManager() {}
    
    public static synchronized Connection getConnection() {
    	//check the status of the connection
        try {
			if (connection == null || connection.isValid(0)) {
			    try {
			        // Load the JDBC driver
			        Class.forName("org.postgresql.Driver");
			        // Create the connection
			        connection = DriverManager.getConnection(URL, USER, PASSWORD);
			    } catch (ClassNotFoundException | SQLException e) {
			        e.printStackTrace();
			        // Handle the exception as needed
			    }
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        return connection;
    }
    
    

}
