package banghoadon;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class ConnectionManager {
	private static String  url = "jdbc:sqlserver://MSI:1433;"
			+"DatabaseName=QLHD;"
			+"encrypt=true;"
			+"trustServerCertificate=true";
    private static String driverName = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
    private static String username = "sa";
    private static Connection connection;
    private static String password = "qazwsxL9@";
    Statement statement = null;

    public static Connection getConnection() {
        try {
            Class.forName(driverName);
            try {
                connection = DriverManager.getConnection(url, username, password);
                System.out.println("Connection Succeeded");
            }
            catch(SQLException e) {
                System.out.println("Connection Failed");
            }    
        }
        catch (ClassNotFoundException ex){
            System.out.println("Driver Not Found");
        }
        return connection;  
    }
}
