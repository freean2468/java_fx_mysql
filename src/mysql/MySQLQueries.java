package mysql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;

import data.ScoreTable;
import data.Student;

public class MySQLQueries {
	public static boolean createTable(String name) {
		boolean flag = false;
		Connection connection = MySQLUtil.getConnection();
		String createQuery = "create table " + name + "("
				+ "id varchar(10),"
				+ "name varchar(10),"
				+ "birthdate varchar(10),"
				+ "gender varchar(1),"
				+ "email varchar(40),"
				+ "phone varchar(20),"
				+ "kor int,"
				+ "eng int,"
				+ "math int"
				+ ")";
		
		PreparedStatement preparedStatement = null;
		
		try {
			preparedStatement = connection.prepareStatement(createQuery);
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
	
	public static boolean insertStudent(Student s) {
		boolean flag = false;
		Connection connection = MySQLUtil.getConnection();
		String insertQuery = "insert into student values(?, ?, ?, ?, ?, ?, ?, ?, ?)";
		
		PreparedStatement preparedStatement = null;
		try {
			preparedStatement = connection.prepareStatement(insertQuery);
			preparedStatement.setString(1, s.getId());
			preparedStatement.setString(2, s.getName());
			preparedStatement.setString(3, s.getBirthdate());
			preparedStatement.setString(4, s.getGender());
			preparedStatement.setString(5, s.getEmail());
			preparedStatement.setString(6, s.getPhone());
			preparedStatement.setInt(7, s.getScoreTable().getKor());
			preparedStatement.setInt(8, s.getScoreTable().getEng());
			preparedStatement.setInt(9, s.getScoreTable().getMath());
			
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
	
	public static boolean selectStudent(HashSet<Student> set) {
		boolean flag = false;
		Connection connection = MySQLUtil.getConnection();
		String insertQuery = "select * from student";
		
		PreparedStatement preparedStatement = null;
		ResultSet resSet = null;
		
		try {
			preparedStatement = connection.prepareStatement(insertQuery);
			
			resSet = preparedStatement.executeQuery();
			
			while(resSet.next()) {
				set.add(new Student(
					resSet.getString(3),
					resSet.getString(2),
					resSet.getString(1),
					resSet.getString(4),
					resSet.getString(5),
					resSet.getString(6),
					new ScoreTable(resSet.getInt(7),resSet.getInt(8),resSet.getInt(9))
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
	
	public static boolean deleteStudentById(String id) {
		boolean flag = false;
		Connection connection = MySQLUtil.getConnection();
		
		String deleteQuery = "delete from student where id = ?";
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
