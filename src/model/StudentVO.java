package model;

import data.Student;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

/** 
 * @apiNote JavaFX UI tableColumn에 값 바인딩을 위해 값만을 가지고 있을 StudentVO 클래스.
 * 			실제 Student 구조는 Student 클래스에서 담당
 * 
 *  
 */ 
public class StudentVO {
	private SimpleStringProperty birthDate;
	private SimpleStringProperty name;
	private SimpleStringProperty id;
	private SimpleStringProperty gender;
	private SimpleStringProperty email;
	private SimpleStringProperty phone;
	private SimpleIntegerProperty courseId;
	private SimpleIntegerProperty c;
	private SimpleIntegerProperty java;
	private SimpleIntegerProperty android;
	private SimpleIntegerProperty web;
	private SimpleIntegerProperty total;
	private SimpleDoubleProperty avg;
	private SimpleStringProperty grade;
	
	public StudentVO(Student s) {
		this.birthDate = new SimpleStringProperty(s.getBirthdate());
		this.name = new SimpleStringProperty(s.getName());
		this.id = new SimpleStringProperty(s.getId());
		this.courseId = new SimpleIntegerProperty(s.getCourseId());
		this.gender = new SimpleStringProperty(s.getGender());
		this.email = new SimpleStringProperty(s.getEmail());
		this.phone = new SimpleStringProperty(s.getPhone());
		this.grade = new SimpleStringProperty(s.getScoreTable().getGrade());
		this.c = new SimpleIntegerProperty(s.getScoreTable().getC());
		this.java = new SimpleIntegerProperty(s.getScoreTable().getJava());
		this.android = new SimpleIntegerProperty(s.getScoreTable().getAndroid());
		this.web = new SimpleIntegerProperty(s.getScoreTable().getWeb());
		this.total = new SimpleIntegerProperty(s.getScoreTable().getTotal());
		this.avg = new SimpleDoubleProperty(s.getScoreTable().getAvg());
	}
	
	public String getBirthDate() { return birthDate.get(); }
	public void setBirthDate(String birthdate) { this.birthDate.set(birthdate); }  
	public String getName() { return name.get(); }
	public void setName(String name) { this.name.set(name); }
	public String getId() { return id.get(); }
	public String getGender() { return gender.get(); }
	public void setGender(String gender) { this.gender.set(gender); }
	public String getEmail() { return email.get(); }
	public void setEmail(String email) { this.email.set(email); }
	public String getPhone() { return phone.get(); }
	public void setPhone(String phone) { this.phone.set(phone); }
	public String getGrade() { return grade.get(); }
	public void setGrade(String grade) { this.grade.set(grade); }
	

	public int getCourseId() { return courseId.get(); }
	public int getC() { return c.get(); }
	public void setC(int val) { this.c.set(val); }
	public int getJava() { return java.get(); }
	public void setJava(int val) { this.java.set(val); }
	public int getAndroid() { return android.get(); }
	public void setAndroid(int val) { this.android.set(val); }
	public int getWeb() { return web.get(); }
	public void setWeb(int val) { this.web.set(val); }
	public int getTotal() { return total.get(); }
	public void setTotal(int total) { this.total.set(total); }
	public double getAvg() { return avg.get(); }
	public void setAvg(double avg) { this.avg.set(avg); }
}
