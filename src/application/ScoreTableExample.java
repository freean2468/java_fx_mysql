package application;

import java.util.ArrayList;
import java.util.Scanner;

import data.Person;
import data.Printable;
import data.ScoreTable;

public class ScoreTableExample {
	public static final int ASCENDING = 1;
	public static final int SORT_SELECT = 2;
	public static final int PRINT = 3;
	public static final int INSERT = 4;
	public static final int DELETE = 5;
	public static final int UPDATE = 6;
	public static final int SEARCH = 7;
	public static final int QUIT = 8;
	
//	public static void main(String[] args) {
//		Scanner sc = new Scanner(System.in);
//		
//		System.out.print("몇 명? ");
//		int input = sc.nextInt();
//		
//		ArrayList<ScoreTable> array = new ArrayList<ScoreTable>(input);
//		
//		for (int i = 0; i < input; ++i) {
//			String birthdate = ScoreTable.generateBirthdate();
//			String name = ScoreTable.generateName();
//			String id = ScoreTable.generateId();
//			String gender = ScoreTable.generateGender();
//			String email = ScoreTable.generateEmail(id);
//			String phone = ScoreTable.generatePhone();
//			
//			ScoreTable st = new ScoreTable(i+1, birthdate, name, id, gender, email, phone, ScoreTable.generateRandomScore(), ScoreTable.generateRandomScore(), ScoreTable.generateRandomScore());
//			
//			if (checkDuplicate(array, st)) { i--; continue; }
//			
//			array.add(st);
//		}
//		
//		boolean flag = true;
//		calcRank(array);
//		
//		while (flag) {
//			System.out.println("1.오름, 2.선택, 3.출력, 4.입력, 5.삭제, 6.수정, 7.검색, 8.종료 : ");
//			input = sc.nextInt();
//			
//			switch(input) {
//			case ASCENDING: printAscending(array); break;
//			case SORT_SELECT: sortSelect(new ArrayList<ScoreTable>(array)); break;
//			case PRINT: printArray(array); break;
//			case INSERT: insert(sc, array); break;
//			case DELETE: delete(sc, array); break;
//			case UPDATE: update(sc, array); break;
//			case SEARCH: search(sc, array); break;
//			case QUIT: flag=false; break;
//			}
//		}
//		
//		System.out.println("goodbye~!");
//		sc.close();
//	}
	
	public static void search(Scanner sc, ArrayList<ScoreTable> array) {
		System.out.println("검색하고 싶은 학생의 학번 입력 : ");
		int no = sc.nextInt();
//		for (ScoreTable elm : array) {
//			if (elm.getNo() == no) {
//				elm.printMember();
//				return ;
//			}
//		}
		System.out.println("존재하지 않는 학번 : " + no);
	}
	
	public static void update(Scanner sc, ArrayList<ScoreTable> array) {
//		ScoreTable st = null;
//		
//u_f:	while (true) {
//			System.out.println("성적을 수정하고 싶은 학생의 학번 입력 : ");
//			int no = sc.nextInt();
////			for (ScoreTable elm : array) {
////				if (elm.getNo() == no) {
////					st = elm;
////					break u_f;
////				}
////			}
//			System.out.println("존재하지 않는 학번 : " + no);
//			return;
//		}
//
//		int kor;
//		int eng;
//		int math;
//		
//		while (true) {
//			System.out.print("국어, 영어, 수학 점수 입력 : ");
//			kor = sc.nextInt();
//			eng = sc.nextInt();
//			math = sc.nextInt();
//			
////			if (kor > Printable.MAX_VALUE || kor < Printable.MIN_VALUE) continue;
////			if (eng > Printable.MAX_VALUE || eng < Printable.MIN_VALUE) continue;
////			if (math > Printable.MAX_VALUE || math < Printable.MIN_VALUE) continue;
//			
//			break;
//		}
//		
//		st.setKor(kor);
//		st.setEng(eng);
//		st.setMath(math);
//		
////		calcRank(array); 
	}
	
	public static void delete(Scanner sc, ArrayList<ScoreTable> array) {
		System.out.print("삭제하고 싶은 학생의 학번 입력 : ");
		int no = sc.nextInt();
		for (ScoreTable st : array) {
//			if (st.getNo() == no) {
//				System.out.println(no + "학번의 학생 삭제.");
//				array.remove(st);
//				return;
//			}
		}
		System.out.println(no + "학번의 학생은 없다.");
	}
	
	public static void insert(Scanner sc, ArrayList<ScoreTable> array) {
		String birthdate = new String();
		String name = new String();
		String id = new String();
		String gender = new String();
		String phone = new String();
		String domain = new String();
		
		String input = new String();
		
		ScoreTable st;
		
		while (true) {
			while (true) {
				System.out.print("birthdate(yyyymmdd) 입력 : ");
				input = sc.next();
				if (input.length() != Person.BIRTHDATE_LENGTH) continue;
				int nInput = Integer.parseInt(input); 
				if (nInput <= 19000101 || nInput >= 20201231) continue;
				birthdate = input;
				break;
			}
			
			while (true) {
				System.out.print("이름(최대 4자) 입력: ");
				input = sc.next();
				if (input.length() > Person.MAX_NAME_LENGTH) continue;
				name = input;
				break;
			}
			
			while (true) {
				System.out.print("id(최대 10자) 입력 : ");
				input = sc.next();
				if (input.length() < Person.MIN_ID_LENGTH || input.length() > Person.MAX_ID_LENGTH) continue;
				id = input;
				break;
			}
			
			while (true) {
				System.out.print("남(1), 여(2) : ");
				int nInput = sc.nextInt();
				if (nInput < 1 || nInput > 3) continue;
				gender = (nInput == 1) ? "남" : "여";
				break;
			}
			
			while (true) {
				System.out.print("phone('-'포함 13자리) 입력 : ");
				input = sc.next();
				if (input.length() != Person.PHONE_LENGTH) continue;
				phone = input;
				break;
			}
			
			while (true) {
				System.out.print("email 도메인 선택(naver:1, google:2, amazon:3) : ");
				int nInput = sc.nextInt();
				if (nInput < 1 || nInput > Person.DOMAINS.length) continue;
				domain = (nInput == 1) ? "@naver.com" : (nInput == 2) ? "@google.com" : "@amazon.com";
				break;
			}
			
			int kor;
			int eng;
			int math;
			
			while (true) {
				System.out.print("국어, 영어, 수학 점수 입력 : ");
				kor = sc.nextInt();
				eng = sc.nextInt();
				math = sc.nextInt();
				
//				if (kor > Printable.MAX_VALUE || kor < Printable.MIN_VALUE) continue;
//				if (eng > Printable.MAX_VALUE || eng < Printable.MIN_VALUE) continue;
//				if (math > Printable.MAX_VALUE || math < Printable.MIN_VALUE) continue;
				
				break;
			}
			
//			st = new ScoreTable(array.size()+1, birthdate, name, id, gender, id+domain, phone, kor, eng, math);
//			if (checkDuplicate(array, st)) continue;
			break;
		}
		
//		array.add(st);
//		calcRank(array); 
	}
	
	public static boolean checkDuplicate(ArrayList<ScoreTable> array, ScoreTable newSt) {
		for (ScoreTable st : array) {
//			if (st.getId().equals(newSt.getId())) return true;
//			if (st.getPhone().equals(newSt.getPhone())) return true;
		}
		
		return false;
	}
	
	public static void printAscending(ArrayList<ScoreTable> array) {
		for (int i = 0; i < array.size(); ++i) {
			for (int j = 0; j < array.size(); ++j) {
				if (array.get(j).getRank() == i+1) {
					array.get(j).printMember();
					break;
				}
			}
		}
	}
	
	public static void sortSelect(ArrayList<ScoreTable> array) {
		for (int i = 0; i < array.size()-1; ++i) {
			for (int j = i+1; j < array.size(); ++j) {
				if (array.get(j).getRank() < array.get(i).getRank()) {
					ScoreTable temp = array.get(i);
					array.set(i, array.get(j));
					array.set(j, temp);
				}
			}
		}
		
		printArray(array);
	}
	
	public static void printArray(ArrayList<ScoreTable> array) {
		for (ScoreTable st : array) {
			st.printMember();
		}
		System.out.println();
	}
}
