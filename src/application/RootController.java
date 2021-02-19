package application;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.ResourceBundle;

import data.Person;
import data.ScoreTable;
import data.Student;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import model.StudentVO;
import mysql.MySQLQueries;

/** 
 * @apiNote JavaFX UI 컨트롤을 담당
 * 			싱글톤으로 선언될 수 없다.
 * 
 * last_updated : 2021_02_19
 *  
 */
public class RootController implements Initializable {
	/**
	 * `mirae_institution_db`.`student_tbl` 테이블 필드 상수 선언
	 */
	public static final int ID_COLUMN = 0;
	public static final int NAME_COLUMN = 1;
	public static final int COURSE_ID_COLUMN = 2;
	public static final int BIRTHDATE_COLUMN = 3;
	public static final int GENDER_COLUMN = 4;
	public static final int EMAIL_COLUMN = 5;
	public static final int PHONE_COLUMN = 6;
	public static final int C_COLUMN = 7;
	public static final int JAVA_COLUMN = 8;
	public static final int ANDROID_COLUMN = 9;
	public static final int WEB_COLUMN = 10;
	public static final int TOTAL_COLUMN = 11;
	public static final int AVG_COLUMN = 12;
	public static final int GRADE_COLUMN = 13;
	
	public static final int INITIAL_NUM = 5;
	
	// DB transaction 결과물을 담을 Set
	private HashSet<Student> set = new HashSet<>();
	
	@FXML private TableView<StudentVO> tableView;
	
	/**
	 * @apiNote FX UI를 담당하는 JavaFX Application Thread가 호출하는 초기화 함수
	 */
	@Override public void initialize(URL location, ResourceBundle resources) {
		System.out.println(Thread.currentThread().getName() + "'s calling initialize");
		

		if (MySQLQueries.createTable("student_tbl")) {
			//
			// 테이블이 비어있다면 자동으로 생성해주는 부분.
			//
//			while (set.size() < INITIAL_NUM) {
//				String id = Person.generateId();
//				
//				set.add(new Student(
//					Person.generateBirthdate(),
//					Person.generateName(),
//					id,
//					Person.generateGender(),
//					Person.generateEmail(id),
//					Person.generatePhone(),
//					new ScoreTable(ScoreTable.generateRandomScore(), 
//							ScoreTable.generateRandomScore(), ScoreTable.generateRandomScore())
//				));
//			}
//			
//			System.out.println("insert into student");
//			
//			Iterator<Student> itr = set.iterator();
//			while (itr.hasNext()) {
//				MySQLQueries.insertStudent(itr.next());
//			}
		} else {
			//
			// 테이블이 비어있지 않다면 그냥 모두 가져온다
			//
			System.out.println("select * from student_tbl");
			MySQLQueries.selectStudent(set);
		}
		
		this.refreshTable();
		
		this.studentTblBinding();
	}
	
	@FXML public void handleInsert() {
		boolean flag = true;
		Stage dialog = new Stage(StageStyle.UTILITY);
		dialog.initModality(Modality.WINDOW_MODAL);
		dialog.initOwner(tableView.getScene().getWindow());
		dialog.setTitle("Insert");
		
		try {
			Parent parent = FXMLLoader.load(getClass().getResource("../resources/form.fxml"));			
			Button insertConfirmButton = (Button)parent.lookup("#confirm");
			
			insertConfirmButton.setOnAction(e->{
				try {
					TextField id = (TextField)parent.lookup("#id");
					TextField courseId = (TextField)parent.lookup("#courseId");
					TextField name = (TextField)parent.lookup("#name");
					TextField birthdate = (TextField)parent.lookup("#birthdate");
					@SuppressWarnings("unchecked")
					ComboBox<String> email = (ComboBox<String>)parent.lookup("#email");
					TextField phone = (TextField)parent.lookup("#phone");
					@SuppressWarnings("unchecked")
					ComboBox<String> gender = (ComboBox<String>)parent.lookup("#gender");
					TextField c = (TextField)parent.lookup("#c");
					TextField java = (TextField)parent.lookup("#java");
					TextField android = (TextField)parent.lookup("#android");
					TextField web = (TextField)parent.lookup("#web");
										
					Student student = new Student(
						id.getText(),
						name.getText(),
						Integer.parseInt(courseId.getText()),
						birthdate.getText(),
						gender.getValue(),
						id.getText()+email.getValue(),
						phone.getText(),
						new ScoreTable(Integer.parseInt(c.getText()), 
								Integer.parseInt(java.getText()), 
								Integer.parseInt(android.getText()),
								Integer.parseInt(web.getText()))
					);		
					
					boolean res = addIntoSet(student);
					if (res == false) {
						showMessage(dialog, "중복입니다.");	
					} else {
						dialog.close();
					}
				} catch (NumberFormatException nfe) {
					showMessage(dialog, "올바르지 않은 입력");
				} 
			});
			
			Button insertCancelButton = (Button)parent.lookup("#cancel");
			insertCancelButton.setOnAction(e->dialog.close());
			
			Scene scene = new Scene(parent);
			dialog.setScene(scene);
			dialog.setResizable(false);
			dialog.show();
		} catch (IOException e) {
			e.printStackTrace();
		} 
	}
	
	@FXML public void handleUpdate() {
		boolean flag = true;
		Stage dialog = new Stage(StageStyle.UTILITY);
		dialog.initModality(Modality.WINDOW_MODAL);
		dialog.initOwner(tableView.getScene().getWindow());
		dialog.setTitle("Update");
		StudentVO m = tableView.getSelectionModel().getSelectedItem();

		try {
			Parent parent = FXMLLoader.load(getClass().getResource("../resources/form.fxml"));			
			Button confirm = (Button)parent.lookup("#confirm");

			TextField id = (TextField)parent.lookup("#id");
			TextField courseId = (TextField)parent.lookup("#courseId");
			TextField name = (TextField)parent.lookup("#name");
			TextField birthdate = (TextField)parent.lookup("#birthdate");
			@SuppressWarnings("unchecked")
			ComboBox<String> email = (ComboBox<String>)parent.lookup("#email");
			TextField phone = (TextField)parent.lookup("#phone");
			@SuppressWarnings("unchecked")
			ComboBox<String> gender = (ComboBox<String>)parent.lookup("#gender");
			TextField c = (TextField)parent.lookup("#c");
			TextField java = (TextField)parent.lookup("#java");
			TextField android = (TextField)parent.lookup("#android");
			TextField web = (TextField)parent.lookup("#web");
			
			id.setText(m.getId());
			id.setDisable(true);
			courseId.setText(String.valueOf(m.getCourseId()));
			
			name.setText(m.getName());
			birthdate.setText(m.getBirthDate());
			email.setValue("@"+m.getEmail().split("@")[1]);
			phone.setText(m.getPhone());
			gender.setValue(m.getGender());
			c.setText(String.valueOf(m.getC()));
			java.setText(String.valueOf(m.getJava()));
			android.setText(String.valueOf(m.getAndroid()));
			web.setText(String.valueOf(m.getWeb()));
			
			confirm.setOnAction(e->{
				try {
					Student student = new Student(
						id.getText(),
						name.getText(),
						Integer.parseInt(courseId.getText()),
						birthdate.getText(),
						gender.getValue(),
						id.getText()+email.getValue(),
						phone.getText(),
						new ScoreTable(Integer.parseInt(c.getText()), 
								Integer.parseInt(java.getText()), 
								Integer.parseInt(android.getText()),
								Integer.parseInt(web.getText()))
					);		
					
					boolean res = updateSetWhereId(student);
					if (res == false) {
						showMessage(dialog, "update 오류");	
					} else {
						dialog.close();
					}
				} catch (NumberFormatException nfe) {
					showMessage(dialog, "올바르지 않은 입력");
				} 
			});
			
			Button cancel = (Button)parent.lookup("#cancel");
			cancel.setOnAction(e->dialog.close());
			
			Scene scene = new Scene(parent);
			dialog.setScene(scene);
			dialog.setResizable(false);
			dialog.show();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (NullPointerException npe) {
			this.showMessage(dialog, "수정하고자 하는 학생 선택");
		}
	}
	
	@FXML public void handleSearch() {
		boolean flag = true;
		Stage dialog = new Stage(StageStyle.UTILITY);
		dialog.initModality(Modality.WINDOW_MODAL);
		dialog.initOwner(tableView.getScene().getWindow());
		dialog.setTitle("Search");
		
		try {
			Parent parent = FXMLLoader.load(getClass().getResource("../resources/form.fxml"));			
			Button insertConfirmButton = (Button)parent.lookup("#confirm");
			
			insertConfirmButton.setOnAction(e->{
				TextField id = (TextField)parent.lookup("#id");
				TextField name = (TextField)parent.lookup("#name");
				TextField courseId = (TextField)parent.lookup("#courseId");
				TextField birthdate = (TextField)parent.lookup("#birthdate");
				@SuppressWarnings("unchecked")
				ComboBox<String> email = (ComboBox<String>)parent.lookup("#email");
				TextField phone = (TextField)parent.lookup("#phone");
				@SuppressWarnings("unchecked")
				ComboBox<String> gender = (ComboBox<String>)parent.lookup("#gender");
				TextField c = (TextField)parent.lookup("#c");
				c.setDisable(true);
				TextField java = (TextField)parent.lookup("#java");
				java.setDisable(true);
				TextField android = (TextField)parent.lookup("#android");
				android.setDisable(true);
				TextField web = (TextField)parent.lookup("#web");
				web.setDisable(true);
				
				// 아무 내용도 입력하지 않으면 select *
				if (id.getText().equals("") &&
					courseId.getText().equals("") &&
					name.getText().equals("") &&
					birthdate.getText().equals("") &&
					email.getValue() == null &&
					phone.getText().equals("") &&
					gender.getValue() == null) {
					MySQLQueries.selectStudent(set);
					this.refreshTable();
					dialog.close();
					return;
				}
				
				int nCourseId = 0;
				
				try {
					nCourseId = Integer.parseInt(courseId.getText());
				} catch (NumberFormatException nfe) {
					nCourseId = 0;					
				}
				
				boolean res = updateAfterSelect(id.getText(), nCourseId,
						name.getText(),	birthdate.getText(), email.getValue(), phone.getText(),
						gender.getValue());
				if (res == false) {
					showMessage(dialog, "검색 결과 없음.");	
				} else {
					dialog.close();
				}
			});
			
			Button insertCancelButton = (Button)parent.lookup("#cancel");
			insertCancelButton.setOnAction(e->dialog.close());
			
			Scene scene = new Scene(parent);
			dialog.setScene(scene);
			dialog.setResizable(false);
			dialog.show();
		} catch (IOException e) {
			e.printStackTrace();
		} 
	}
	
	@FXML public void handleSort() {
		ArrayList<Student> list = new ArrayList<>();
		MySQLQueries.selectStudentOrderById(list);
		ObservableList<StudentVO> tableList = FXCollections.observableArrayList();
		
		for (Student s : list) {
			tableList.add(new StudentVO(s));
		}
		
		tableView.setItems(tableList);
		tableView.refresh();
	}
	
	@FXML public void handleDelete() {
		boolean flag = true;
		Stage dialog = new Stage(StageStyle.UTILITY);
		dialog.initModality(Modality.WINDOW_MODAL);
		dialog.initOwner(tableView.getScene().getWindow());
		dialog.setTitle("Delete");
		StudentVO m = tableView.getSelectionModel().getSelectedItem();

		try {
			Parent parent = FXMLLoader.load(getClass().getResource("../resources/form.fxml"));			
			Button insertConfirmButton = (Button)parent.lookup("#confirm");

			TextField id = (TextField)parent.lookup("#id");
			TextField name = (TextField)parent.lookup("#name");
			TextField courseId = (TextField)parent.lookup("#courseId");
			TextField birthdate = (TextField)parent.lookup("#birthdate");
			@SuppressWarnings("unchecked")
			ComboBox<String> email = (ComboBox<String>)parent.lookup("#email");
			TextField phone = (TextField)parent.lookup("#phone");
			@SuppressWarnings("unchecked")
			ComboBox<String> gender = (ComboBox<String>)parent.lookup("#gender");
			TextField c = (TextField)parent.lookup("#c");
			TextField java = (TextField)parent.lookup("#java");
			TextField android = (TextField)parent.lookup("#android");
			TextField web = (TextField)parent.lookup("#web");
			
			id.setText(m.getId()); id.setDisable(true);			
			name.setText(m.getName()); name.setDisable(true);
			courseId.setText(String.valueOf(m.getCourseId())); courseId.setDisable(true);
			birthdate.setText(m.getBirthDate()); birthdate.setDisable(true);
			email.setValue("@"+m.getEmail().split("@")[1]); email.setDisable(true);
			phone.setText(m.getPhone()); phone.setDisable(true);
			gender.setValue(m.getGender()); gender.setDisable(true);
			c.setText(String.valueOf(m.getC())); c.setDisable(true);
			java.setText(String.valueOf(m.getJava())); java.setDisable(true);
			android.setText(String.valueOf(m.getAndroid())); android.setDisable(true);
			web.setText(String.valueOf(m.getWeb())); web.setDisable(true);
			
			insertConfirmButton.setOnAction(e->{
				try {					
					boolean res = removeSetWhereId(id.getText());
					if (res == false) {
						showMessage(dialog, "delete 오류");	
					} else {
						dialog.close();
					}
				} catch (NumberFormatException nfe) {
					showMessage(dialog, "올바르지 않은 입력");
				} 
			});
			
			Button insertCancelButton = (Button)parent.lookup("#cancel");
			insertCancelButton.setOnAction(e->dialog.close());
			
			Scene scene = new Scene(parent);
			dialog.setScene(scene);
			dialog.setResizable(false);
			dialog.show();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (NullPointerException npe) {
			this.showMessage(dialog, "삭제하고자 하는 학생 선택");
		}
	}
	
	@FXML public void handleQuit() {
		System.out.println("Quit");
		Stage stage = (Stage)tableView.getScene().getWindow();
		stage.close();
	}
	
	/**
	 * @apiNote 상황에 따른 메세지를 팝업창으로 전달 
	 * @param Stage : 현재 메인 화면 
	 * @param message : 전달할 메세지
	 */ 
	public void showMessage(Stage dialog, String message) {
		Stage messageDialog = new Stage(StageStyle.UTILITY);
		messageDialog.initModality(Modality.WINDOW_MODAL);
		messageDialog.initOwner(dialog);
		messageDialog.setTitle("메세지");
		
		try {
			Parent messageParent = FXMLLoader.load(getClass().getResource("../resources/messageDialog.fxml"));
			Label messageLabel = (Label)messageParent.lookup("#message");
			messageLabel.setText(message);
			Button messageButton = (Button)messageParent.lookup("#confirm");
			messageButton.setOnAction(messageEvent->messageDialog.close());
			Scene scene = new Scene(messageParent);
			messageDialog.setScene(scene);
			messageDialog.setResizable(false);
			messageDialog.show();
		} catch (IOException messageException) {
			messageException.printStackTrace();
		}				
	}
	
	/**
	 * @apiNote DB transaction 결과물을 가지고 있는 Set은 그 데이터가 수정될 때
				다음과 같은 작업을 병행해줘야 하기 때문에 따로 함수들을 만듬.
				1. DB transaction
				2. set data 반영
				3. UI data 모델 반영  
	 * @param s : set에 넣으려는 학생 객체
	 * @return 정상적으로 넣었으면 true, 이미 존재하면 false 반환
	 */
	public boolean addIntoSet(Student s) {
		boolean res = set.add(s);
		if (res == true) {
			ObservableList<StudentVO> tableList = tableView.getItems();
			tableList.add(new StudentVO(s));
			tableView.setItems(tableList);
			MySQLQueries.insertStudent(s);
		}
		return res;
	}
	
	/**
	 * @apiNote student의 id를 기준으로 set에서 삭제, db에서도 삭제, ui에서도 삭제
	 * @param id 삭제하려는 student의 id
	 * @return 삭제했으면 true 실패면 false
	 */
	public boolean removeSetWhereId(String id) {
		boolean res = false;
		Iterator<Student> itr = set.iterator();
		while(itr.hasNext()) {
			Student item = itr.next();
			if (id.equals(item.getId())) {
				itr.remove();
				MySQLQueries.deleteStudentById(id);
				res = true;
				ObservableList<StudentVO> tableList = tableView.getItems();
				Iterator<StudentVO> mItr = tableList.iterator();
								
				while (mItr.hasNext()) {
					StudentVO m = mItr.next();
					if (id.equals(m.getId())) {
						mItr.remove();
						break;
					}
				}
				tableView.setItems(tableList);
				tableView.refresh();
				break;
			}
		}
		return res;
	}
	
	/** 
	 * @apiNote 새로운 Student 객체를 받아 set 안 기존에 있던 객체에 덮어 씌운다.
	 * 			마찬가지로 DB와 UI에도 반영
	 * @param s 새로운 Student 객체
	 * @return 성공 true 실패 false
	 */
	public boolean updateSetWhereId(Student s) {
		boolean res = false;
		Iterator<Student> itr = set.iterator();
		while(itr.hasNext()) {
			Student item = itr.next();
			if (s.getId().equals(item.getId())) {
				item.setBirthdate(s.getBirthdate());
				item.setCourseId(s.getCourseId());
				item.setEmail(s.getEmail());
				item.setName(s.getName());
				item.setGender(s.getGender());
				item.setPhone(s.getPhone());
				item.setScoreTable(s.getScoreTable());
				MySQLQueries.updateStudentWhereId(s);
				res = true;
				ObservableList<StudentVO> tableList = tableView.getItems();
				Iterator<StudentVO> mItr = tableList.iterator();
								
				while (mItr.hasNext()) {
					StudentVO m = mItr.next();
					if (s.getId().equals(m.getId())) {
						m.setBirthDate(s.getBirthdate());
						m.setEmail(s.getEmail());
						m.setGender(s.getGender());
						m.setGrade(s.getGender());
						m.setName(s.getName());
						m.setPhone(s.getPhone());
						m.setC(s.getScoreTable().getC());
						m.setJava(s.getScoreTable().getJava());
						m.setAndroid(s.getScoreTable().getAndroid());
						m.setWeb(s.getScoreTable().getWeb());
						m.setTotal(s.getScoreTable().getTotal());
						m.setAvg(s.getScoreTable().getAvg());
					}
				}
				tableView.setItems(tableList);
				tableView.refresh();
				break;
			}
		}
		return res;
	}
	
	/**
	 * @apiNote 필드 중 아무 값이나 선택해 입력하면 해당 필드를 가진 튜플을 검색해주는 함수
	 * 			set, DB, UI 모두 반영
	 * @param id
	 * @param courseId
	 * @param name
	 * @param birthdate
	 * @param email
	 * @param phone
	 * @param gender
	 * @return
	 */
	public boolean updateAfterSelect(String id, int courseId, String name, String birthdate,
			String email, String phone, String gender) {
		boolean res = false;
		
		res = MySQLQueries.selectStudent(set, id, courseId, name, birthdate, gender, email, phone);
		
		Iterator<Student> itr = set.iterator();
		ObservableList<StudentVO> tableList = tableView.getItems();
		tableList.clear();
		
		while(itr.hasNext()) {
			Student item = itr.next();
			tableList.add(new StudentVO(item));
		}

		tableView.setItems(tableList);
		tableView.refresh();
		return res;
	}
	
	/**
	 * @apiNote
	 * 쓸데없이 시간을 많이 잡아먹게 만든 부분,
	 * 당연히 합리적인 논리의 전개로 tableColumn 데이터가 바뀌면 바로바로 tableView에 반영이 될 줄 알았는데 그렇지가 않아서
	 * 직접 refresh()를 호출해줘야 한다;
	 */
	public void refreshTable() {
		ObservableList<StudentVO> tableList = FXCollections.observableArrayList();
		
		Iterator<Student> itr = set.iterator();
		while (itr.hasNext()) {
			tableList.add(new StudentVO(itr.next()));
		}
		
		tableView.setItems(tableList);
		tableView.refresh();
	}
	
	/**
	 * @apiNote db student_tbl <-> java VO binding
	 * 			각 테이블 필드를 Factory 함수를 통해 생성되도록 각 Student field와 바인딩.
	 * 			이를 위해 Student 클래스를 모델링한 StudentVO 클래스를 만듬.
	 */
	private void studentTblBinding() {
		@SuppressWarnings("unchecked")
		TableColumn<StudentVO, String> tcId = (TableColumn<StudentVO, String>) tableView.getColumns().get(ID_COLUMN);
		tcId.setCellValueFactory(new PropertyValueFactory<>("id"));
		tcId.setStyle("-fx-alignment: CENTER;");
		
		@SuppressWarnings("unchecked")
		TableColumn<StudentVO, String> tcBirthDate = (TableColumn<StudentVO, String>) tableView.getColumns().get(BIRTHDATE_COLUMN);
		tcBirthDate.setCellValueFactory(new PropertyValueFactory<>("birthDate"));
		tcBirthDate.setStyle("-fx-alignment: CENTER;");
		
		@SuppressWarnings("unchecked")
		TableColumn<StudentVO, String> tcName = (TableColumn<StudentVO, String>)tableView.getColumns().get(NAME_COLUMN);
		tcName.setCellValueFactory(new PropertyValueFactory<>("name"));
		tcName.setStyle("-fx-alignment: CENTER;");
		
		@SuppressWarnings("unchecked")
		TableColumn<StudentVO, Integer> tcCourseId = (TableColumn<StudentVO, Integer>)tableView.getColumns().get(COURSE_ID_COLUMN);
		tcCourseId.setCellValueFactory(new PropertyValueFactory<>("courseId"));
		tcCourseId.setStyle("-fx-alignment: CENTER;");
		
		@SuppressWarnings("unchecked")
		TableColumn<StudentVO, String> tcGender = (TableColumn<StudentVO, String>)tableView.getColumns().get(GENDER_COLUMN);
		tcGender.setCellValueFactory(new PropertyValueFactory<>("gender"));
		tcGender.setStyle("-fx-alignment: CENTER;");
		
		@SuppressWarnings("unchecked")
		TableColumn<StudentVO, String> tcEmail = (TableColumn<StudentVO, String>)tableView.getColumns().get(EMAIL_COLUMN);
		tcEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
		tcEmail.setStyle("-fx-alignment: CENTER;");
		
		@SuppressWarnings("unchecked")
		TableColumn<StudentVO, String> tcPhone = (TableColumn<StudentVO, String>)tableView.getColumns().get(PHONE_COLUMN);
		tcPhone.setCellValueFactory(new PropertyValueFactory<>("phone"));
		tcPhone.setStyle("-fx-alignment: CENTER;");
		
		@SuppressWarnings("unchecked")
		TableColumn<StudentVO, Integer> tcC = (TableColumn<StudentVO, Integer>)tableView.getColumns().get(C_COLUMN);
		tcC.setCellValueFactory(new PropertyValueFactory<>("c"));
		tcC.setStyle("-fx-alignment: CENTER;");
		
		@SuppressWarnings("unchecked")
		TableColumn<StudentVO, Integer> tcJava = (TableColumn<StudentVO, Integer>)tableView.getColumns().get(JAVA_COLUMN);
		tcJava.setCellValueFactory(new PropertyValueFactory<>("java"));
		tcJava.setStyle("-fx-alignment: CENTER;");
		
		@SuppressWarnings("unchecked")
		TableColumn<StudentVO, Integer> tcAndroid = (TableColumn<StudentVO, Integer>)tableView.getColumns().get(ANDROID_COLUMN);
		tcAndroid.setCellValueFactory(new PropertyValueFactory<>("android"));
		tcAndroid.setStyle("-fx-alignment: CENTER;");
		
		@SuppressWarnings("unchecked")
		TableColumn<StudentVO, Integer> tcWeb = (TableColumn<StudentVO, Integer>)tableView.getColumns().get(WEB_COLUMN);
		tcWeb.setCellValueFactory(new PropertyValueFactory<>("web"));
		tcWeb.setStyle("-fx-alignment: CENTER;");
		
		@SuppressWarnings("unchecked")
		TableColumn<StudentVO, Integer> tcTotal = (TableColumn<StudentVO, Integer>)tableView.getColumns().get(TOTAL_COLUMN);
		tcTotal.setCellValueFactory(new PropertyValueFactory<>("total"));
		tcTotal.setStyle("-fx-alignment: CENTER;");
		
		@SuppressWarnings("unchecked")
		TableColumn<StudentVO, Double> tcAvg = (TableColumn<StudentVO, Double>)tableView.getColumns().get(AVG_COLUMN);
		tcAvg.setCellValueFactory(new PropertyValueFactory<>("avg"));
		tcAvg.setStyle("-fx-alignment: CENTER;");
		
		@SuppressWarnings("unchecked")
		TableColumn<StudentVO, String> tcGrade = (TableColumn<StudentVO, String>)tableView.getColumns().get(GRADE_COLUMN);
		tcGrade.setCellValueFactory(new PropertyValueFactory<>("grade"));
		tcGrade.setStyle("-fx-alignment: CENTER;");
	}
}
