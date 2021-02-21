package mysql;

import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/** 
 * @apiNote db priperties 파일을 바탕으로 MySQL 최초 설정 담당 클래스
 * 			싱글톤 
 *  
 */
public class MySQLUtil {
	private static String OS = System.getProperty("os.name").toLowerCase();
	private static Properties properties = new Properties();
	private static MySQLUtil mySqlUtil = new MySQLUtil();
	
	public static boolean isWindows() { return (OS.indexOf("win") >= 0); }
    public static boolean isMac() { return (OS.indexOf("mac") >= 0); }
	
	private MySQLUtil() {
		String path = MySQLUtil.class.getResource("db.properties").getPath();
		
		try {
			if (isWindows()) {
				System.out.println("os : windows");
				path = path.substring(1);
				path = path.replaceAll("/", "\\\\");
				System.out.println("path for windows : " + path);
			} else if (isMac()) {
				System.out.println("os : mac");
				System.out.println("path for mac : " + path);
			}
			
			
			properties.load(new FileReader(path));
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			// MySQL 드라이버 메소드 영역에 적재
			if (isWindows()) {
				Class.forName(properties.getProperty("MYSQL_DRIVER_FOR_WINDOWS"));
			} else if (isMac()) {
				Class.forName(properties.getProperty("MYSQL_DRIVER_FOR_MAC"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static MySQLUtil getInstance() { return mySqlUtil; }
	
	public Connection getConnection() {
		Connection connection = null;
		try {
			connection = DriverManager.getConnection(
					properties.getProperty("URL"), 
					properties.getProperty("userName"),
					properties.getProperty("password"));
			
			if (connection != null) {
				System.out.println("connected to schema");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return connection;
	}
	
	public String getDbName() { return properties.getProperty("db"); }
	public String getTableName() { return properties.getProperty("table"); }
}
