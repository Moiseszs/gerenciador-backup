package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnector {

	private static final String MYSQL_DRIVER = "org.mariadb.jdbc.Driver";
	private static final String MSSQL_DRIVER = "net.sourceforge.jtds.jdbc.Driver";
	
	private static final String MSSQL_URL = "jdbc:jtds:sqlserver://localhost:1433/db_app";
	
	private static final String MYSQL_URL = "jdbc:mariadb://localhost:3306/db_app";
	private static final String MYSQL_USER = "root";
	
	public static Connection connectMYSQL() throws ClassNotFoundException, SQLException {
		Class.forName(MYSQL_DRIVER);
		return DriverManager.getConnection(MYSQL_URL, MYSQL_USER, "");
	}
	
	public static Connection connectMSSQL() throws ClassNotFoundException, SQLException {
		Class.forName(MSSQL_DRIVER);
		return DriverManager.getConnection(MSSQL_URL);
	}
	
	
}
