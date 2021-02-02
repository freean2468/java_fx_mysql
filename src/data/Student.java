package data;

import java.util.Objects;

public class Student extends Person {
	private ScoreTable scoreTable;
	
	public Student() {
		this(null, null, null, null, null, null, null);
	}
	
	public Student(String birthdate, String name, String id, String gender, String email, String phone, ScoreTable scoreTable) {
		super(birthdate, name, id, gender, email, phone);
		this.scoreTable = scoreTable;
	}
	
	@Override
	public String toString() {
		return super.toString() + ", " + this.scoreTable.toString();
	}
	
	@Override
	public void printMember() {
		System.out.println(this.toString());
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(super.getId(), super.getPhone());
	}
	
	@Override
	public boolean equals(Object obj) {
		boolean flag = false;
		if (obj instanceof Student) {
			Student arg = (Student)obj;
			if (this.getId().equals(arg.getId()) || this.getPhone().equals(arg.getPhone())) {
				flag = true;
			}
		}
		return flag;
	}
	
	public ScoreTable getScoreTable() { return scoreTable; } 
}
