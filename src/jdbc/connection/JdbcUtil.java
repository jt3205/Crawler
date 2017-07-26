package jdbc.connection;

import java.sql.*;

public class JdbcUtil {
	private static final String url = "jdbc:mysql://?useUnicode=true&characterEncoding=utf8&useSSL=false&serverTimezone=Asia/Seoul";
	private static final String user = "";
	private static final String password = "";
	
	static Connection con;
	
	public static Connection getConnection(){
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			con = DriverManager.getConnection(url, user, password);
		} catch (ClassNotFoundException e) {
			System.out.println("JDBC �겢�옒�뒪媛� �뾾�뒿�땲�떎.");
			e.printStackTrace();
			return null;
		} catch (SQLException e) {
			System.out.println("DB �뿰寃곗떎�뙣");
			e.printStackTrace();
		}
		return con;
	}
}
