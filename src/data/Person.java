package data;

public class Person implements Printable {
	public static final int MAX_ID_LENGTH = 10;
	public static final int MIN_ID_LENGTH = 3;
	public static final int BIRTHDATE_LENGTH = 8;
	public static final int MAX_NAME_LENGTH = 4;
	public static final int PHONE_LENGTH = 13;
	
	private String birthdate;
	private String name;
	private String id;
	private String gender;
	private String email;
	private String phone;
	
	public Person() {
		this(null, null, null, null, null, null);
	}
	
	public Person(String id, String name, String birthdate, String gender, String email, String phone) {
		this.birthdate = birthdate;
		this.name = name;
		this.id = id;
		this.gender = gender;
		this.email = email;
		this.phone = phone;
	}
	
	public String getId() { return id; }
	
	public String getBirthdate() { return birthdate; }
	public void setBirthdate(String birthdate) { this.birthdate = birthdate; }
	public String getName() { return name; }
	public void setName(String name) { this.name = name; }
	public String getGender() { return gender; }
	public void setGender(String gender) { this.gender = gender; }
	public String getEmail() { return email; }
	public void setEmail(String email) { this.email = email; }
	public String getPhone() { return phone; }
	public void setPhone(String phone) { this.phone = phone; }
	
	@Override
	public String toString() {
		return "birthdate:" + birthdate + ", name:" + name + ", id:" + id
				+ ", gender:" + gender + ", email:" + email + ", phone:" + phone;
	}
	
	@Override
	public void printMember() {
		System.out.println(this.toString());
	}
	
	public static String generateBirthdate() {
		String year = String.valueOf((int)(Math.random()*20) + 2000);
		int nMonth = (int)(Math.random()*12)+1;
		int nDate = (int)(Math.random()*31) + 1;
		String sMonth = new String();
		String sDate = new String();
		
		sMonth = (nMonth < 10) ? sMonth = "0"+String.valueOf(nMonth) : String.valueOf(nMonth);
		sDate = (nDate < 10) ? sDate = "0"+String.valueOf(nDate) : String.valueOf(nDate);
		
		return year + sMonth + sDate;
	}
	
	public static String[] LAST_NAMES = {"김", "송", "허", "신", "박"};
	public static String[] FIRST_NAMES = {"훈", "일", "재", "준", "성", "철", "수", "연", "민", "지", "선", "영", "도", "양", "원"};
	
	public static String generateName() {
		return LAST_NAMES[(int)(Math.random()*LAST_NAMES.length)] + FIRST_NAMES[(int)(Math.random()*FIRST_NAMES.length)] + FIRST_NAMES[(int)(Math.random()*FIRST_NAMES.length)];
	}
	
	// 최대 10자리 
	public static String generateId() {
		int places = (int)(Math.random()*7) + 4;
		String id = new String();
		
		for (int i = 0; i < places; ++i) {
			id += (i%((int)(Math.random()*2)+1)) == 0 ? getRandomAlphabet() : String.valueOf((int)(Math.random()*10));
		}
		
		return id;
	}
	
	public static char getRandomAlphabet() {
		return (char)(((int)(Math.random()*(int)('z'-'a')))+(int)'a');
	}
	
	public static String generateGender() {
		return (1%((int)(Math.random()*2)+1)) == 0 ? "남" : "여";
	}
	
	public static String[] DOMAINS = {"naver.com", "google.com", "amazon.com"};
	
	public static String generateEmail(String id) {
		return id+"@"+DOMAINS[(int)(Math.random()*DOMAINS.length)];
	}
	
	public static String generatePhone() {
		String phone = "010-";
		
		for (int i = 0; i < 4; ++i) {
			phone += String.valueOf((int)(Math.random()*10));
		}
		
		phone+="-";
		
		for (int i = 0; i < 4; ++i) {
			phone += String.valueOf((int)(Math.random()*10));
		}
		
		return phone;
	}
}
