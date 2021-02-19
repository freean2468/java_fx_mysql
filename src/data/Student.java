package data;

/** 
 * @apiNote Student 구조를 담당할 클래스, value는 StudentVO에게 일임.
 * 
 *  
 */
public class Student extends Person {
	private ScoreTable scoreTable;
	private int courseId;
	
	public Student() {
		this(null, null, 0, null, null, null, null, null);
	}
	
	/*
	 * public Student(String birthdate, String name, String id, String gender, 
			String email, String phone, ScoreTable scoreTable, int courseId) {
	 */
	public Student(String id, String name, int courseId, String birthdate, String gender, 
			String email, String phone, ScoreTable scoreTable) {
		super(id, name, birthdate, gender, email, phone);
		this.courseId = courseId;
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
		// assume all student objects are equal at the hashCode comparison stage.
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
	
	public int getCourseId() { return courseId; }
	public void setCourseId(int courseId) { this.courseId = courseId; }
}
