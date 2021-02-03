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
		// assume all student objects are equal at hashCode compare stage.
		return 0;
	}
	
	@Override
	public boolean equals(Object obj) {
		boolean flag = false;
		if (obj instanceof Student) {
			Student arg = (Student)obj;
			if (super.getId().equals(arg.getId()) 
					|| super.getPhone().equals(arg.getPhone())) 
				flag = true;
		}
		return flag;
	}
	
	public ScoreTable getScoreTable() { return scoreTable; }
	public void setScoreTable(ScoreTable st) { scoreTable = st; }
}
