package model;

import data.Student;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class StudentModelForJavaFX {
	private SimpleStringProperty birthDate;
	private SimpleStringProperty name;
	private SimpleStringProperty id;
	private SimpleStringProperty gender;
	private SimpleStringProperty email;
	private SimpleStringProperty phone;
	private SimpleIntegerProperty kor;
	private SimpleIntegerProperty eng;
	private SimpleIntegerProperty math;
	private SimpleIntegerProperty total;
	private SimpleDoubleProperty avg;
	private SimpleStringProperty grade;
	
	public StudentModelForJavaFX(Student s) {
		this.birthDate = new SimpleStringProperty(s.getBirthdate());
		this.name = new SimpleStringProperty(s.getName());
		this.id = new SimpleStringProperty(s.getId());
		this.gender = new SimpleStringProperty(s.getGender());
		this.email = new SimpleStringProperty(s.getEmail());
		this.phone = new SimpleStringProperty(s.getPhone());
		this.grade = new SimpleStringProperty(s.getScoreTable().getGrade());
		this.kor = new SimpleIntegerProperty(s.getScoreTable().getKor());
		this.eng = new SimpleIntegerProperty(s.getScoreTable().getEng());
		this.math = new SimpleIntegerProperty(s.getScoreTable().getMath());
		this.total = new SimpleIntegerProperty(s.getScoreTable().getTotal());
		this.avg = new SimpleDoubleProperty(s.getScoreTable().getAvg());
	}
	
	public String getBirthDate() { return birthDate.get(); }
	public String getName() { return name.get(); }
	public String getId() { return id.get(); }
	public String getGender() { return gender.get(); }
	public String getEmail() { return email.get(); }
	public String getPhone() { return phone.get(); }
	public String getGrade() { return grade.get(); }
	public int getKor() { return kor.get(); }
	public int getEng() { return eng.get(); }
	public int getMath() { return math.get(); }
	public int getTotal() { return total.get(); }
	public double getAvg() { return avg.get(); }
}
