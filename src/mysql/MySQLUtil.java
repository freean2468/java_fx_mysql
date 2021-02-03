package mysql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MySQLUtil {
	public static final String MYSQL_DRIVER = "com.mysql.cj.jdbc.Driver";
	
	// URL of schema which we will use
	public static final String URL ="jdbc:mysql://localhost/student";
	
	public static Connection getConnection() {
		try {
			// MySQL 드라이버 메소드 영역에 적
			Class.forName(MYSQL_DRIVER);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		Connection connection = null;
		try {
			// DB 정보는 숨겨야 한다.
			// TODO : Properties를 이용해 보안을 강화할 수 있다.
			connection = DriverManager.getConnection(URL, "root", "12345678");
			
			if (connection != null) {
				System.out.println("connected to student schema");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return connection;
	}
}
