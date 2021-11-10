package jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
	private final String url = "jdbc:oracle:thin:@localhost:1521:xe";

	public DBConnection() {
		try {
			Class.forName("oracle.jdbc.OracleDriver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	public Connection getConnection() {
		Connection con = null;
		try {
			con = DriverManager.getConnection(url, "project", "oracle1234");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return con;
	}
}