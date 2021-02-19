package mysql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;

import data.ScoreTable;
import data.Student;

/** 
 * @apiNote MySQL와 연결 - 쿼리 - 연결 종료 를 담당할 클래스
 * 
 *  
 */
public class MySQLQueries {
	
	public static final String _TABLE = "student_tbl";
	
	/**
	 * @apiNote MySQL create table 호출 함수
	 * @param name 생성하려는 table 이름
	 * @return 성공 true 실패 false
	 */
	public static boolean createTable(String name) {
		boolean flag = false;
		Connection connection = MySQLUtil.getConnection();
		String createTableQuery = "create table " + name + "("
				+ "`id` varchar(10) NOT NULL,"
				+ "`name` varchar(10) not null,"
				+ "`course_id` int not null,"
				+ "`birthdate` varchar(10) not null,"
				+ "`gender` varchar(1) not null,"
				+ "`email` varchar(40) not null,"
				+ "`phone` varchar(20) not null,"
				+ "`c` int not null,"
				+ "`java` int not null,"
				+ "`android` int not null,"
				+ "`web` int not null,"
				+ "constraint `pk_id` PRIMARY KEY (`id`),"
				+ "constraint `fk_course_id` foreign key(`course_id`) references `course_tbl`(`id`) on delete cascade on update cascade"
				+ ") ENGINE=InnoDB DEFAULT CHARSET=utf8";
		String dropTableQuery = "drop table if exists student";
		
		PreparedStatement preparedStatement = null;
		
		try {
			preparedStatement = connection.prepareStatement(dropTableQuery);
			preparedStatement.executeUpdate();
			preparedStatement.close();
			
			preparedStatement = connection.prepareStatement(createTableQuery);
			int count = preparedStatement.executeUpdate();
			if (count == 0) {
				System.out.println("Create table " + name + " completed.");
				flag = true;
			} else {
				System.out.println("Create table " + name + " failed.");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (preparedStatement != null) preparedStatement.close();
				if (connection != null) connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return flag;
	}
	
	/**
	 * @apiNote student_tbl에 insert 쿼리 함수
	 * @param s 삽입하려는 Student 객체
	 * @return 성공 true 실패 false
	 */
	public static boolean insertStudent(Student s) {
		boolean flag = false;
		Connection connection = MySQLUtil.getConnection();
		String insertQuery = "insert into " 	
			+ _TABLE
			+ "(`id`, `name`, `course_id`, `birthdate`, `gender`, `email`, `phone`, `c`, `java`, `android`, `web`) "
			+ " value(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
		
		PreparedStatement preparedStatement = null;
		try {
			preparedStatement = connection.prepareStatement(insertQuery);
			preparedStatement.setString(1, s.getId());
			preparedStatement.setString(2, s.getName());
			preparedStatement.setInt(3, s.getCourseId());
			preparedStatement.setString(4, s.getBirthdate());
			preparedStatement.setString(5, s.getGender());
			preparedStatement.setString(6, s.getEmail());
			preparedStatement.setString(7, s.getPhone());
			preparedStatement.setInt(8, s.getScoreTable().getC());
			preparedStatement.setInt(9, s.getScoreTable().getJava());
			preparedStatement.setInt(10, s.getScoreTable().getAndroid());
			preparedStatement.setInt(11, s.getScoreTable().getWeb());
			
			int count = preparedStatement.executeUpdate();
			if (count == 1) {
				System.out.println("Insert Completed");
				flag = true;
			} else {
				System.out.println("Insert Failed");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (preparedStatement != null) preparedStatement.close();
				if (connection != null) connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return flag;
	}
	
	/**
	 * @apiNote student_tbl에 update 쿼리 함수
	 * @param s 갱신하려는 Student 객체
	 * @return 성공 true 실패 false
	 */
	public static boolean updateStudentWhereId(Student s) {
		boolean flag = false;
		Connection connection = MySQLUtil.getConnection();
		String updateQuery = "update " + _TABLE + " set name=?, course_id=?, birthdate=?, gender=?, email=?, phone=?,  "
				+ "c=?, java=?, android=?, web=? where id = ?";
		
		PreparedStatement preparedStatement = null;
		try {
			preparedStatement = connection.prepareStatement(updateQuery);
			preparedStatement.setString(1, s.getName());
			preparedStatement.setInt(2, s.getCourseId());
			preparedStatement.setString(3, s.getBirthdate());
			preparedStatement.setString(4, s.getGender());
			preparedStatement.setString(5, s.getEmail());
			preparedStatement.setString(6, s.getPhone());
			preparedStatement.setInt(7, s.getScoreTable().getC());
			preparedStatement.setInt(8, s.getScoreTable().getJava());
			preparedStatement.setInt(9, s.getScoreTable().getAndroid());
			preparedStatement.setInt(10, s.getScoreTable().getWeb());
			preparedStatement.setString(11, s.getId());
			
			int count = preparedStatement.executeUpdate();
			if (count == 1) {
				System.out.println("Update Completed");
				flag = true;
			} else {
				System.out.println("Update Failed");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (preparedStatement != null) preparedStatement.close();
				if (connection != null) connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return flag;
	}

	/**
	 * @apiNote student_tbl 검색 함수
	 * 			아래 파라미터 중 원하는 조건의 파라미터만 넘겨주면 된다. 원하지 않는 값들은 타입의 디폴트값으로 설정한다.
	 * @param set
	 * @param id
	 * @param name
	 * @param birthdate
	 * @param gender
	 * @param email
	 * @param phone
	 * @return 성공 true (검색 결과가 없어도 true) 실패 false
	 */
	public static boolean selectStudent(HashSet<Student> set, String id, int courseId, String name,
								String birthdate, String gender, String email, String phone) {
		boolean flag = false;
		Connection connection = MySQLUtil.getConnection();
				
		String query = "select * from " + _TABLE + " where ";
		
		query += "id LIKE '";
		query +=  (id.length() != 0) ? id + "'": "%'";		
		query +=  (courseId != 0) ? "AND course_id = " + courseId: "";
		query += " AND name LIKE '";
		query += (name.length() != 0) ? name + "'" : "%'";
		query += " AND birthdate LIKE '";
		query += (birthdate.length() != 0) ? birthdate + "'": "%'";
		query += " AND gender LIKE '";
		query += (gender != null) ? gender + "'" : "%'";
		query += " AND email LIKE '";
		query += (email != null) ? email + "'" : "%'";
		query += " AND phone LIKE '";
		query += (phone.length() != 0) ? phone + "';" : "%';";		
		
		PreparedStatement preparedStatement = null;
		ResultSet resSet = null;
		
		try {
			preparedStatement = connection.prepareStatement(query);
			
			resSet = preparedStatement.executeQuery();
			
			set.clear();
			while(resSet.next()) {
				set.add(new Student(
					resSet.getString(1),
					resSet.getString(2),
					Integer.parseInt(resSet.getString(3)),
					resSet.getString(4),
					resSet.getString(5),
					resSet.getString(6),
					resSet.getString(7),
					new ScoreTable(resSet.getInt(8),resSet.getInt(9),resSet.getInt(10),resSet.getInt(11))
				));
			}
			
			flag = true;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (preparedStatement != null) preparedStatement.close();
				if (connection != null) connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return flag;
	}
	
	/**
	 * @apiNote 모든 student_tbl 레코드 select
	 * @param set
	 * @return
	 */
	public static boolean selectStudent(HashSet<Student> set) {

		boolean flag = false;
		Connection connection = MySQLUtil.getConnection();
		String selectQuery = "select * from " + _TABLE;
		
		PreparedStatement preparedStatement = null;
		ResultSet resSet = null;
		
		try {
			preparedStatement = connection.prepareStatement(selectQuery);
			
			resSet = preparedStatement.executeQuery();
			
			set.clear();
			while(resSet.next()) {
				set.add(new Student(
					resSet.getString(1),
					resSet.getString(2),
					Integer.parseInt(resSet.getString(3)),
					resSet.getString(4),
					resSet.getString(5),
					resSet.getString(6),
					resSet.getString(7),
					new ScoreTable(resSet.getInt(8),resSet.getInt(9),resSet.getInt(10),resSet.getInt(11))
				));
			}
			
			flag = true;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (preparedStatement != null) preparedStatement.close();
				if (connection != null) connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return flag;
	}
	
	/**
	 * @apiNote 모든 student_tbl 레코드 정렬해서 select
	 * @param list
	 * @return
	 */
	public static boolean selectStudentOrderById(ArrayList<Student> list) {
		boolean flag = false;
		Connection connection = MySQLUtil.getConnection();
		String selectQuery = "select * from " + _TABLE + " order by id";
		
		PreparedStatement preparedStatement = null;
		ResultSet resSet = null;
		
		try {
			preparedStatement = connection.prepareStatement(selectQuery);
			
			resSet = preparedStatement.executeQuery();
			
			list.clear();
			while(resSet.next()) {
				list.add(new Student(
					resSet.getString(1),
					resSet.getString(2),
					Integer.parseInt(resSet.getString(3)),
					resSet.getString(4),
					resSet.getString(5),
					resSet.getString(6),
					resSet.getString(7),
					new ScoreTable(resSet.getInt(8),resSet.getInt(9),resSet.getInt(10),resSet.getInt(11))
				));
			}
			
			flag = true;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (preparedStatement != null) preparedStatement.close();
				if (connection != null) connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return flag;
	}
	
	/**
	 * @apiNote student_tbl에서 Student의 id를 기준으로 delete 쿼리
	 * @param id
	 * @return 성공 true 실패 false 
	 */
	public static boolean deleteStudentById(String id) {
		boolean flag = false;
		Connection connection = MySQLUtil.getConnection();
		
		String deleteQuery = "delete from " + _TABLE + " where id = ?";
		PreparedStatement preparedStatement = null;
		
		try {
			preparedStatement = connection.prepareStatement(deleteQuery);
			preparedStatement.setString(1, id);
			int count = preparedStatement.executeUpdate();
			
			if (count == 1) {
				System.out.println("Delete Completed");
				flag = true;
			} else {
				System.out.println("Delete Failed");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (preparedStatement != null) preparedStatement.close();
				if (connection != null) connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return flag;
	}
}
