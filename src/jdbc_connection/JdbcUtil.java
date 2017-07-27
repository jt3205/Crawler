package jdbc_connection;

import java.sql.*;

public class JdbcUtil {
	public static Connection getConnection() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException ex) {
			System.out.println("JDBC 클래스가 없습니다.");
			return null;
		}
		String connectionString = "jdbc:mysql:///?useUnicode=true&characterEncoding=utf8&useSSL=false&serverTimezone=Asia/Seoul";
		String dbUser = "";
		String dbPass = "";
		Connection conn = null;
		try {
			conn = DriverManager.getConnection(connectionString, dbUser, dbPass);
		} catch (Exception e) {
			System.out.println("DB 연결 실패");
		}
		return conn;
	}
}
