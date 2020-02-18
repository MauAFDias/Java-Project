package data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionDB {
	
	public static Connection conn()  throws SQLException 
	{
		Connection conn =null;
		
		String username = "bank";
		String password = "bank123";
		String service = "localhost";
		String url = "jdbc:oracle:thin:";
		
		try {
			conn = DriverManager.getConnection(url+username+"/"+password+"@"+service);
	    System.out.println("\nConnection established...\n");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return conn;
	}

}
