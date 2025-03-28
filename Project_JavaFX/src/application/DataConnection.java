package application;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DataConnection {
    public static Connection getConnection() throws SQLException {
    	// Chuỗi kết nối SQL Server
        String url = "jdbc:sqlserver://LAPTOP-VPMJ8CML\\NKHANGG;databaseName=Northwind;integratedSecurity=true;TrustServerCertificate=True;Trusted_Connection=true";
        return DriverManager.getConnection(url);
    }

}

