package data;

import java.util.ArrayList;

/** 
 * @apiNote 학생 테이블이 가지고 있는 성적 계산을 담당하려 했던 클래스인데 
 * 			이 모든 일을 DB에서 처리하면 된다 걸 알았으므로 필요가 없어졌다.
 * 
 * 첫 개발 일자 : 2021_02_03
 * last_updated : 2021_02_19
 *  
 */
public class ScoreTable implements Printable {
	public static final int MAX_VALUE = 100;
	public static final int MIN_VALUE = 0;
	
	enum Subjects {
		C, JAVA, ANDROID, WEB, LAST
	}
	public static final int AVG_FLOAT_POINT_PLACE_EXP = 2;
	
	private ArrayList<Integer> scores;
	private int rank;
	
	public ScoreTable() {
		this(0, 0, 0, 0);
	}
	
	public ScoreTable(int c, int java, int android, int web) {
		scores = new ArrayList<Integer>(Subjects.LAST.ordinal());
		for (int i = 0; i < Subjects.LAST.ordinal(); ++i) {
			scores.add(0);
		}
		
		setC(c);
		setJava(java);
		setAndroid(android);
		setWeb(web);
	}
	
	@Override
	public String toString() {
		return "rank:" + getRank() + ", C:" + getC() + ", Java:" + getJava() + ", Android:"
				+ getAndroid() + ", Web:" + getWeb() + ", Total:" + getTotal() + ", Avg:" + getAvg() + ", grade:" + getGrade(); 
	}
	
	@Override
	public void printMember() {
		System.out.println(this.toString());
	}
	
	public void setC(int val) {
		if (ScoreTable.MAX_VALUE < val || val < ScoreTable.MIN_VALUE) {
			System.out.println("범위 초과");
			scores.set(Subjects.C.ordinal(), (int)(Math.random()*(ScoreTable.MAX_VALUE+1)));
		} else {
			scores.set(Subjects.C.ordinal(), val);
		}
	}
	
	public int getC() {
		return scores.get(Subjects.C.ordinal());
	}
	
	public void setJava(int val) {
		if (ScoreTable.MAX_VALUE < val || val < ScoreTable.MIN_VALUE) {
			System.out.println("범위 초과");
			scores.set(Subjects.JAVA.ordinal(), (int)(Math.random()*(ScoreTable.MAX_VALUE+1)));
		} else {
			scores.set(Subjects.JAVA.ordinal(), val);
		}
	}
	
	public int getJava() {
		return scores.get(Subjects.JAVA.ordinal());
	}
	
	public void setWeb(int val) {
		if (ScoreTable.MAX_VALUE < val || val < ScoreTable.MIN_VALUE) {
			System.out.println("범위 초과");
			scores.set(Subjects.WEB.ordinal(), (int)(Math.random()*(ScoreTable.MAX_VALUE+1)));
		} else {
			scores.set(Subjects.WEB.ordinal(), val);
		}
	}
	
	public int getWeb() {
		return scores.get(Subjects.WEB.ordinal());
	}
	
	public void setAndroid(int val) {
		if (ScoreTable.MAX_VALUE < val || val < ScoreTable.MIN_VALUE) {
			System.out.println("범위 초과");
			scores.set(Subjects.ANDROID.ordinal(), (int)(Math.random()*(ScoreTable.MAX_VALUE+1)));
		} else {
			scores.set(Subjects.ANDROID.ordinal(), val);
		}
	}
	
	public int getAndroid() {
		return scores.get(Subjects.ANDROID.ordinal());
	}
	
	public void setRank(int rank) {
		this.rank = rank;
	}
	
	public int getRank() {
		return rank;
	}
	
	public int getTotal() {
		int sum = 0;
		
		for (Integer score : scores) {
			sum += score;
		}
		
		return sum;
	}
	
	public double getAvg() {
		int total = getTotal();
		double avgFloatPointPlace = Math.pow(10, AVG_FLOAT_POINT_PLACE_EXP);
		
		return Math.round(avgFloatPointPlace*total/scores.size())/avgFloatPointPlace;
	}
	
	public String getGrade() {
		String grade = new String();
		
		switch((int)(getAvg()/10)) {
		case 10: case 9: grade = "A"; break;
		case 8: case 7: grade = "B"; break;
		case 6: case 5: grade = "C"; break;
		case 4: case 3: grade = "D"; break;
		default: grade = "F"; break;
		}
		
		return grade;
	}
	
	public static int generateRandomScore() {
		return (int)(Math.random()*101);
	}
	
	public static void calcRank(ArrayList<ScoreTable> array) {
		for (int i = 0; i < array.size(); ++i) {
			int count = 1;
			for (int j = 0; j < array.size(); ++j) {
				if (array.get(i).getTotal() < array.get(j).getTotal()) count++;
			}
			array.get(i).setRank(count);
		}
	}
}
