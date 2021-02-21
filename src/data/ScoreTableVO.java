package data;

import java.util.ArrayList;

/** 
 * @apiNote 학생 테이블이 가지고 있는 성적 계산을 담당하려 했던 클래스인데 
 * 			이 모든 일을 DB에서 처리하도록 바꿈.
 * 
 *  
 */
public class ScoreTableVO implements Printable {
	public static final int MAX_VALUE = 100;
	public static final int MIN_VALUE = 0;
	
	public static final int AVG_FLOAT_POINT_PLACE_EXP = 2;
	
	private int c;
	private int java;
	private int android;
	private int web;
	private int total;
	private double avg;
	private String grade;
	
	public ScoreTableVO() {
		this(0, 0, 0, 0, 0, 0.0, "F");
	}
	
	public ScoreTableVO(int c, int java, int android, int web) {
		setC(c);
		setJava(java);
		setAndroid(android);
		setWeb(web);
	}
	
	public ScoreTableVO(int c, int java, int android, int web, int total, double avg, String grade) {
		setC(c);
		setJava(java);
		setAndroid(android);
		setWeb(web);
		this.total = total;
		this.avg = avg;
		this.grade = grade;
	}
	
	@Override
	public String toString() {
		return "C:" + getC() + ", Java:" + getJava() + ", Android:"
				+ getAndroid() + ", Web:" + getWeb() + ", Total:" + getTotal() + ", Avg:" + getAvg() + ", grade:" + getGrade(); 
	}
	
	@Override
	public void printMember() {
		System.out.println(this.toString());
	}
	
	public void setC(int val) {
		if (ScoreTableVO.MAX_VALUE < val || val < ScoreTableVO.MIN_VALUE) {
			System.out.println("범위 초과");
			this.c = (int)(Math.random()*(ScoreTableVO.MAX_VALUE+1));
		} else {
			this.c = val;
		}
	}
	
	public int getC() { return this.c; }
	
	public void setJava(int val) {
		if (ScoreTableVO.MAX_VALUE < val || val < ScoreTableVO.MIN_VALUE) {
			System.out.println("범위 초과");
			this.java = (int)(Math.random()*(ScoreTableVO.MAX_VALUE+1));
		} else {
			this.java= val;
		}
	}
	
	public int getJava() { return this.java; }
	
	public void setWeb(int val) {
		if (ScoreTableVO.MAX_VALUE < val || val < ScoreTableVO.MIN_VALUE) {
			System.out.println("범위 초과");
			this.web = (int)(Math.random()*(ScoreTableVO.MAX_VALUE+1));
		} else {
			this.web = val;
		}
	}
	
	public int getWeb() { return this.web; }
	
	public void setAndroid(int val) {
		if (ScoreTableVO.MAX_VALUE < val || val < ScoreTableVO.MIN_VALUE) {
			System.out.println("범위 초과");
			this.android = (int)(Math.random()*(ScoreTableVO.MAX_VALUE+1));
		} else {
			this.android = val;
		}
	}
	
	public int getAndroid() { return this.android; }
	
	public int getTotal() { return this.total; }
	
	public double getAvg() { return this.avg; }
	
	public String getGrade() { return this.grade; }
	
	public static int generateRandomScore() {
		return (int)(Math.random()*101);
	}
}
