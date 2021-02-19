package mysql;

import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/** 
 * @apiNote db priperties 파일을 바탕으로 MySQL 최초 설정 담당 클래스
 * 
 *  
 */
public class MySQLUtil {
	public static Connection getConnection() {
		Properties properties = new Properties();
		String path = MySQLUtil.class.getResource("db.properties").getPath();
		path = path.substring(1);
		
		try {
			path = path.replaceAll("/", "\\\\");
			properties.load(new FileReader(path));
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		try {
			// MySQL 드라이버 메소드 영역에 적재 
			Class.forName(properties.getProperty("MYSQL_DRIVER"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		Connection connection = null;
		try {
			connection = DriverManager.getConnection(
					properties.getProperty("URL"), 
					properties.getProperty("userName"),
					properties.getProperty("password"));
			
			if (connection != null) {
				System.out.println("connected to student schema");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return connection;
	}
}
