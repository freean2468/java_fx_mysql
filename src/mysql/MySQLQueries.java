package mysql;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;
import java.util.HashSet;

import data.ScoreTableVO;
import data.Student;

/** 
 * @apiNote MySQL와 연결 - 쿼리 - 연결 종료 를 담당할 클래스
 * 
 *  
 */
public class MySQLQueries {
	/**
	 * `mirae_institution_db`.`student_tbl` 테이블 필드 binding을 위한 상수 선언
	 */
	public static final int ID_COLUMN = 1;
	public static final int NAME_COLUMN = 2;
	public static final int COURSE_ID_COLUMN = 3;
	public static final int BIRTHDATE_COLUMN = 4;
	public static final int GENDER_COLUMN = 5;
	public static final int EMAIL_COLUMN = 6;
	public static final int PHONE_COLUMN = 7;
	public static final int C_COLUMN = 8;
	public static final int JAVA_COLUMN = 9;
	public static final int ANDROID_COLUMN = 10;
	public static final int WEB_COLUMN = 11;
	public static final int TOTAL_COLUMN = 12;
	public static final int AVG_COLUMN = 13;
	public static final int GRADE_COLUMN = 14;
	
	public static final String _TABLE = MySQLUtil.getInstance().getTableName();
	
	/**
	 * @apiNote MySQL create table 호출 함수
	 * @param name 생성하려는 table 이름
	 * @return 성공 true 실패 false
	 */
	public static boolean createTable(String name) {
		boolean flag = false;
		Connection connection = MySQLUtil.getInstance().getConnection();
		String createTableQuery = "create table " + name + "("
				+ "`id` varchar(10) NOT NULL,"
				+ "`name` varchar(10) not null,"
				+ "`course_id` bigint not null,"
				+ "`birthdate` varchar(10) not null,"
				+ "`gender` varchar(1) not null,"
				+ "`email` varchar(40) not null,"
				+ "`phone` varchar(20) not null,"
				+ "`c` int not null,"
				+ "`java` int not null,"
				+ "`android` int not null,"
				+ "`web` int not null,"
				+ "`total` int not null,"
				+ "`avg` double not null,"
				+ "`grade` varchar(1) not null,"
				+ "constraint `pk_id` PRIMARY KEY (`id`),"
				+ "constraint `fk_course_id` foreign key(`course_id`) references `course_tbl`(`id`) on delete cascade on update cascade"
				+ ") ENGINE=InnoDB DEFAULT CHARSET=utf8";
		
		PreparedStatement preparedStatement = null;
		
		try {
			preparedStatement = connection.prepareStatement(createTableQuery);
			int count = preparedStatement.executeUpdate();
			if (count == 0) {
				System.out.println("Create table " + name + " completed.");
				flag = true;
			} else {
				System.out.println("Create table " + name + " failed.");
			}
		} catch (SQLException e) {
			if (!e.getMessage().equals("Table '" + _TABLE + "' already exists")) {
				e.printStackTrace();
			}
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
	 * @apiNote 미리 생성한 `proc_insert_then_calc` 프로시져 호출 함수. 
	 * @param s 삽입하려는 Student 객체
	 * @return 성공 true 실패 false
	 */
	public static Student insertStudent(Student s) {
		Connection connection = MySQLUtil.getInstance().getConnection();;
		String procCall = "{call `proc_insert_then_calc`(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)}";
		
		CallableStatement callableStatement = null;
		Student resStudent = null;
		
		try {
			callableStatement = connection.prepareCall(procCall);
			callableStatement.setString(ID_COLUMN, s.getId());
			callableStatement.setString(NAME_COLUMN, s.getName());
			callableStatement.setInt(COURSE_ID_COLUMN, s.getCourseId());
			callableStatement.setString(BIRTHDATE_COLUMN, s.getBirthdate());
			callableStatement.setString(GENDER_COLUMN, s.getGender());
			callableStatement.setString(EMAIL_COLUMN, s.getEmail());
			callableStatement.setString(PHONE_COLUMN, s.getPhone());
			callableStatement.setInt(C_COLUMN, s.getScoreTable().getC());
			callableStatement.setInt(JAVA_COLUMN, s.getScoreTable().getJava());
			callableStatement.setInt(ANDROID_COLUMN, s.getScoreTable().getAndroid());
			callableStatement.setInt(WEB_COLUMN, s.getScoreTable().getWeb());
			
			boolean res = callableStatement.execute();
			if (res) {
				System.out.println("call `proc_insert_then_calc` Completed");
				
				ResultSet resSet = callableStatement.getResultSet();
				
				resSet.next();
				
				resStudent = new Student(
								resSet.getString(ID_COLUMN),
								resSet.getString(NAME_COLUMN),
								Integer.parseInt(resSet.getString(COURSE_ID_COLUMN)),
								resSet.getString(BIRTHDATE_COLUMN),
								resSet.getString(GENDER_COLUMN),
								resSet.getString(EMAIL_COLUMN),
								resSet.getString(PHONE_COLUMN),
								new ScoreTableVO(resSet.getInt(C_COLUMN),resSet.getInt(JAVA_COLUMN)
												, resSet.getInt(ANDROID_COLUMN),resSet.getInt(WEB_COLUMN)
												, resSet.getInt(TOTAL_COLUMN), resSet.getDouble(AVG_COLUMN)
												, resSet.getString(GRADE_COLUMN))
							); 
			} else {
				System.out.println("call `proc_insert_then_calc` Failed");
			}
		} catch (SQLIntegrityConstraintViolationException cve) {
			System.out.println("제약 조건 위반 : " + cve.getMessage());
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (callableStatement != null) callableStatement.close();
				if (connection != null) connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return resStudent;
	}
	
	/**
	 * @apiNote student_tbl에 update 쿼리 함수
	 * @param s 갱신하려는 Student 객체
	 * @return 성공 true 실패 false
	 */
	public static Student updateStudentWhereId(Student s) {
		Connection connection = MySQLUtil.getInstance().getConnection();
		String procCall = "{call `proc_update_then_calc`(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)}";
		
		CallableStatement callableStatement = null;
		Student resStudent = null;
		
		try {
			callableStatement = connection.prepareCall(procCall);
			callableStatement.setString(ID_COLUMN, s.getId());
			callableStatement.setString(NAME_COLUMN, s.getName());
			callableStatement.setInt(COURSE_ID_COLUMN, s.getCourseId());
			callableStatement.setString(BIRTHDATE_COLUMN, s.getBirthdate());
			callableStatement.setString(GENDER_COLUMN, s.getGender());
			callableStatement.setString(EMAIL_COLUMN, s.getEmail());
			callableStatement.setString(PHONE_COLUMN, s.getPhone());
			callableStatement.setInt(C_COLUMN, s.getScoreTable().getC());
			callableStatement.setInt(JAVA_COLUMN, s.getScoreTable().getJava());
			callableStatement.setInt(ANDROID_COLUMN, s.getScoreTable().getAndroid());
			callableStatement.setInt(WEB_COLUMN, s.getScoreTable().getWeb());
			
			boolean res = callableStatement.execute();
			if (res) {
				System.out.println("call `proc_update_then_calc` Completed");
				
				ResultSet resSet = callableStatement.getResultSet();
				
				resSet.next();
				
				resStudent = new Student(
								resSet.getString(ID_COLUMN),
								resSet.getString(NAME_COLUMN),
								Integer.parseInt(resSet.getString(COURSE_ID_COLUMN)),
								resSet.getString(BIRTHDATE_COLUMN),
								resSet.getString(GENDER_COLUMN),
								resSet.getString(EMAIL_COLUMN),
								resSet.getString(PHONE_COLUMN),
								new ScoreTableVO(resSet.getInt(C_COLUMN),resSet.getInt(JAVA_COLUMN)
												, resSet.getInt(ANDROID_COLUMN),resSet.getInt(WEB_COLUMN)
												, resSet.getInt(TOTAL_COLUMN), resSet.getDouble(AVG_COLUMN)
												, resSet.getString(GRADE_COLUMN))
							); 
			} else {
				System.out.println("call `proc_update_then_calc` Failed");
			}
		} catch (SQLIntegrityConstraintViolationException cve) {
			System.out.println("제약 조건 위반 : " + cve.getMessage());
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (callableStatement != null) callableStatement.close();
				if (connection != null) connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return resStudent;
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
		Connection connection = MySQLUtil.getInstance().getConnection();
				
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
					resSet.getString(ID_COLUMN),
					resSet.getString(NAME_COLUMN),
					Integer.parseInt(resSet.getString(COURSE_ID_COLUMN)),
					resSet.getString(BIRTHDATE_COLUMN),
					resSet.getString(GENDER_COLUMN),
					resSet.getString(EMAIL_COLUMN),
					resSet.getString(PHONE_COLUMN),
					new ScoreTableVO(resSet.getInt(C_COLUMN),resSet.getInt(JAVA_COLUMN)
									, resSet.getInt(ANDROID_COLUMN),resSet.getInt(WEB_COLUMN)
									, resSet.getInt(TOTAL_COLUMN), resSet.getDouble(AVG_COLUMN)
									, resSet.getString(GRADE_COLUMN))
				));
			}
			
			System.out.println("selectStudent completed");
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
		Connection connection = MySQLUtil.getInstance().getConnection();
		String selectQuery = "select * from " + _TABLE;
		
		PreparedStatement preparedStatement = null;
		ResultSet resSet = null;
		
		try {
			preparedStatement = connection.prepareStatement(selectQuery);
			
			resSet = preparedStatement.executeQuery();
			
			set.clear();
			while(resSet.next()) {
				set.add(new Student(
					resSet.getString(ID_COLUMN),
					resSet.getString(NAME_COLUMN),
					Integer.parseInt(resSet.getString(COURSE_ID_COLUMN)),
					resSet.getString(BIRTHDATE_COLUMN),
					resSet.getString(GENDER_COLUMN),
					resSet.getString(EMAIL_COLUMN),
					resSet.getString(PHONE_COLUMN),
					new ScoreTableVO(resSet.getInt(C_COLUMN),resSet.getInt(JAVA_COLUMN)
									, resSet.getInt(ANDROID_COLUMN),resSet.getInt(WEB_COLUMN)
									, resSet.getInt(TOTAL_COLUMN), resSet.getDouble(AVG_COLUMN)
									, resSet.getString(GRADE_COLUMN))
				));
			}
			
			System.out.println("selectStudent completed");
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
		Connection connection = MySQLUtil.getInstance().getConnection();
		String selectQuery = "select * from " + _TABLE + " order by id";
		
		PreparedStatement preparedStatement = null;
		ResultSet resSet = null;
		
		try {
			preparedStatement = connection.prepareStatement(selectQuery);
			
			resSet = preparedStatement.executeQuery();
			
			list.clear();
			while(resSet.next()) {
				list.add(new Student(
					resSet.getString(ID_COLUMN),
					resSet.getString(NAME_COLUMN),
					Integer.parseInt(resSet.getString(COURSE_ID_COLUMN)),
					resSet.getString(BIRTHDATE_COLUMN),
					resSet.getString(GENDER_COLUMN),
					resSet.getString(EMAIL_COLUMN),
					resSet.getString(PHONE_COLUMN),
					new ScoreTableVO(resSet.getInt(C_COLUMN),resSet.getInt(JAVA_COLUMN)
									, resSet.getInt(ANDROID_COLUMN),resSet.getInt(WEB_COLUMN)
									, resSet.getInt(TOTAL_COLUMN), resSet.getDouble(AVG_COLUMN)
									, resSet.getString(GRADE_COLUMN))
				));
			}
			
			System.out.println("selectStudent orderby completed");
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
		Connection connection = MySQLUtil.getInstance().getConnection();
		
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
