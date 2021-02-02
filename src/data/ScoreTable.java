package data;

import java.util.ArrayList;

public class ScoreTable implements Printable {
	public static final int MAX_VALUE = 100;
	public static final int MIN_VALUE = 0;
	
	enum Subjects {
		KOR, ENG, MATH, LAST
	}
	public static final int AVG_FLOAT_POINT_PLACE_EXP = 2;
	
	private ArrayList<Integer> scores;
	private int rank;
	
	public ScoreTable() {
		this(0, 0, 0);
	}
	
	public ScoreTable(int kor, int eng, int math) {
		scores = new ArrayList<Integer>(Subjects.LAST.ordinal());
		for (int i = 0; i < Subjects.LAST.ordinal(); ++i) {
			scores.add(0);
		}
		setKor(kor);
		setEng(eng);
		setMath(math);
	}
	
	@Override
	public String toString() {
		return "rank:" + getRank() + ", Kor:" + getKor() + ", Eng:" + getEng() + ", Math:"
				+ getMath() + ", Total:" + getTotal() + ", Avg:" + getAvg() + ", grade:" + getGrade(); 
	}
	
	@Override
	public void printMember() {
		System.out.println(this.toString());
	}
	
	public void setKor(int kor) {
		if (ScoreTable.MAX_VALUE < kor || kor < ScoreTable.MIN_VALUE) {
			System.out.println("범위 초과");
			scores.set(Subjects.KOR.ordinal(), (int)(Math.random()*(ScoreTable.MAX_VALUE+1)));
		} else {
			scores.set(Subjects.KOR.ordinal(), kor);
		}
	}
	
	public int getKor() {
		return scores.get(Subjects.KOR.ordinal());
	}
	
	public void setEng(int eng) {
		if (ScoreTable.MAX_VALUE < eng || eng < ScoreTable.MIN_VALUE) {
			System.out.println("범위 초과");
			scores.set(Subjects.ENG.ordinal(), (int)(Math.random()*(ScoreTable.MAX_VALUE+1)));
		} else {
			scores.set(Subjects.ENG.ordinal(), eng);
		}
	}
	
	public int getEng() {
		return scores.get(Subjects.ENG.ordinal());
	}
	
	public void setMath(int math) {
		if (ScoreTable.MAX_VALUE < math || math < ScoreTable.MIN_VALUE) {
			System.out.println("범위 초과");
			scores.set(Subjects.MATH.ordinal(), (int)(Math.random()*(ScoreTable.MAX_VALUE+1)));
		} else {
			scores.set(Subjects.MATH.ordinal(), math);
		}
	}
	
	public int getMath() {
		return scores.get(Subjects.MATH.ordinal());
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
