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
import model.StudentModelForJavaFX;
import mysql.MySQLQueries;

// can't be declared as a singleton
public class RootController implements Initializable {
	public static final int ID_COLUMN = 0;
	public static final int NAME_COLUMN = 1;
	public static final int BIRTHDATE_COLUMN = 2;
	public static final int GENDER_COLUMN = 3;
	public static final int EMAIL_COLUMN = 4;
	public static final int PHONE_COLUMN = 5;
	public static final int KOR_COLUMN = 6;
	public static final int ENG_COLUMN = 7;
	public static final int MATH_COLUMN = 8;
	public static final int TOTAL_COLUMN = 9;
	public static final int AVG_COLUMN = 10;
	public static final int GRADE_COLUMN = 11;
	
	public static final int INITIAL_NUM = 5;
	
	private HashSet<Student> set = new HashSet<>();
	
	@FXML private TableView<StudentModelForJavaFX> tableView;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		System.out.println(Thread.currentThread().getName() + "'s calling initialize");
		
		// If there is no student table
		if (MySQLQueries.createTable("student")) {
			// then create objects
			while (set.size() < INITIAL_NUM) {
				String id = Person.generateId();
				
				set.add(new Student(
					Person.generateBirthdate(),
					Person.generateName(),
					id,
					Person.generateGender(),
					Person.generateEmail(id),
					Person.generatePhone(),
					new ScoreTable(ScoreTable.generateRandomScore(), 
							ScoreTable.generateRandomScore(), ScoreTable.generateRandomScore())
				));
			}
			
			System.out.println("insert into student");
			// then insert them into student schema
			Iterator<Student> itr = set.iterator();
			while (itr.hasNext()) {
				MySQLQueries.insertStudent(itr.next());
			}
		} else {
			// else then select * from student schema
			System.out.println("select * from student");
			MySQLQueries.selectStudent(set);
		}
		
		this.refreshTable();
		
		@SuppressWarnings("unchecked")
		TableColumn<StudentModelForJavaFX, String> tcId = (TableColumn<StudentModelForJavaFX, String>) tableView.getColumns().get(ID_COLUMN);
		tcId.setCellValueFactory(new PropertyValueFactory<>("id"));
		tcId.setStyle("-fx-alignment: CENTER;");
		
		@SuppressWarnings("unchecked")
		TableColumn<StudentModelForJavaFX, String> tcBirthDate = (TableColumn<StudentModelForJavaFX, String>) tableView.getColumns().get(BIRTHDATE_COLUMN);
		tcBirthDate.setCellValueFactory(new PropertyValueFactory<>("birthDate"));
		tcBirthDate.setStyle("-fx-alignment: CENTER;");
		
		@SuppressWarnings("unchecked")
		TableColumn<StudentModelForJavaFX, String> tcName = (TableColumn<StudentModelForJavaFX, String>)tableView.getColumns().get(NAME_COLUMN);
		tcName.setCellValueFactory(new PropertyValueFactory<>("name"));
		tcName.setStyle("-fx-alignment: CENTER;");
		
		@SuppressWarnings("unchecked")
		TableColumn<StudentModelForJavaFX, String> tcGender = (TableColumn<StudentModelForJavaFX, String>)tableView.getColumns().get(GENDER_COLUMN);
		tcGender.setCellValueFactory(new PropertyValueFactory<>("gender"));
		tcGender.setStyle("-fx-alignment: CENTER;");
		
		@SuppressWarnings("unchecked")
		TableColumn<StudentModelForJavaFX, String> tcEmail = (TableColumn<StudentModelForJavaFX, String>)tableView.getColumns().get(EMAIL_COLUMN);
		tcEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
		tcEmail.setStyle("-fx-alignment: CENTER;");
		
		@SuppressWarnings("unchecked")
		TableColumn<StudentModelForJavaFX, String> tcPhone = (TableColumn<StudentModelForJavaFX, String>)tableView.getColumns().get(PHONE_COLUMN);
		tcPhone.setCellValueFactory(new PropertyValueFactory<>("phone"));
		tcPhone.setStyle("-fx-alignment: CENTER;");
		
		@SuppressWarnings("unchecked")
		TableColumn<StudentModelForJavaFX, Integer> tcKor = (TableColumn<StudentModelForJavaFX, Integer>)tableView.getColumns().get(KOR_COLUMN);
		tcKor.setCellValueFactory(new PropertyValueFactory<>("kor"));
		tcKor.setStyle("-fx-alignment: CENTER;");
		
		@SuppressWarnings("unchecked")
		TableColumn<StudentModelForJavaFX, Integer> tcEng = (TableColumn<StudentModelForJavaFX, Integer>)tableView.getColumns().get(ENG_COLUMN);
		tcEng.setCellValueFactory(new PropertyValueFactory<>("eng"));
		tcEng.setStyle("-fx-alignment: CENTER;");
		
		@SuppressWarnings("unchecked")
		TableColumn<StudentModelForJavaFX, Integer> tcMath = (TableColumn<StudentModelForJavaFX, Integer>)tableView.getColumns().get(MATH_COLUMN);
		tcMath.setCellValueFactory(new PropertyValueFactory<>("math"));
		tcMath.setStyle("-fx-alignment: CENTER;");
		
		@SuppressWarnings("unchecked")
		TableColumn<StudentModelForJavaFX, Integer> tcTotal = (TableColumn<StudentModelForJavaFX, Integer>)tableView.getColumns().get(TOTAL_COLUMN);
		tcTotal.setCellValueFactory(new PropertyValueFactory<>("total"));
		tcTotal.setStyle("-fx-alignment: CENTER;");
		
		@SuppressWarnings("unchecked")
		TableColumn<StudentModelForJavaFX, Double> tcAvg = (TableColumn<StudentModelForJavaFX, Double>)tableView.getColumns().get(AVG_COLUMN);
		tcAvg.setCellValueFactory(new PropertyValueFactory<>("avg"));
		tcAvg.setStyle("-fx-alignment: CENTER;");
		
		@SuppressWarnings("unchecked")
		TableColumn<StudentModelForJavaFX, String> tcGrade = (TableColumn<StudentModelForJavaFX, String>)tableView.getColumns().get(GRADE_COLUMN);
		tcGrade.setCellValueFactory(new PropertyValueFactory<>("grade"));
		tcGrade.setStyle("-fx-alignment: CENTER;");
	}
	
	@FXML
	public void handleInsert() {
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
					TextField name = (TextField)parent.lookup("#name");
					TextField birthdate = (TextField)parent.lookup("#birthdate");
					@SuppressWarnings("unchecked")
					ComboBox<String> email = (ComboBox<String>)parent.lookup("#email");
					TextField phone = (TextField)parent.lookup("#phone");
					@SuppressWarnings("unchecked")
					ComboBox<String> gender = (ComboBox<String>)parent.lookup("#gender");
					TextField kor = (TextField)parent.lookup("#kor");
					TextField eng = (TextField)parent.lookup("#eng");
					TextField math = (TextField)parent.lookup("#math");
					
					Student student = new Student(
						birthdate.getText(),
						name.getText(),
						id.getText(),
						gender.getValue(),
						id.getText()+email.getValue(),
						phone.getText(),
						new ScoreTable(Integer.parseInt(kor.getText()), 
								Integer.parseInt(eng.getText()), 
								Integer.parseInt(math.getText()))
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
	
	@FXML
	public void handleUpdate() {
		boolean flag = true;
		Stage dialog = new Stage(StageStyle.UTILITY);
		dialog.initModality(Modality.WINDOW_MODAL);
		dialog.initOwner(tableView.getScene().getWindow());
		dialog.setTitle("Update");
		StudentModelForJavaFX m = tableView.getSelectionModel().getSelectedItem();

		try {
			Parent parent = FXMLLoader.load(getClass().getResource("../resources/form.fxml"));			
			Button insertConfirmButton = (Button)parent.lookup("#confirm");

			TextField id = (TextField)parent.lookup("#id");
			TextField name = (TextField)parent.lookup("#name");
			TextField birthdate = (TextField)parent.lookup("#birthdate");
			@SuppressWarnings("unchecked")
			ComboBox<String> email = (ComboBox<String>)parent.lookup("#email");
			TextField phone = (TextField)parent.lookup("#phone");
			@SuppressWarnings("unchecked")
			ComboBox<String> gender = (ComboBox<String>)parent.lookup("#gender");
			TextField kor = (TextField)parent.lookup("#kor");
			TextField eng = (TextField)parent.lookup("#eng");
			TextField math = (TextField)parent.lookup("#math");
			
			id.setText(m.getId());
			id.setDisable(true);
			
			name.setText(m.getName());
			birthdate.setText(m.getBirthDate());
			email.setValue("@"+m.getEmail().split("@")[1]);
			phone.setText(m.getPhone());
			gender.setValue(m.getGender());
			kor.setText(String.valueOf(m.getKor()));
			math.setText(String.valueOf(m.getEng()));
			eng.setText(String.valueOf(m.getMath()));
			
			insertConfirmButton.setOnAction(e->{
				try {
					Student student = new Student(
						birthdate.getText(),
						name.getText(),
						id.getText(),
						gender.getValue(),
						id.getText()+email.getValue(),
						phone.getText(),
						new ScoreTable(Integer.parseInt(kor.getText()), 
								Integer.parseInt(eng.getText()), 
								Integer.parseInt(math.getText()))
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
			
			Button insertCancelButton = (Button)parent.lookup("#cancel");
			insertCancelButton.setOnAction(e->dialog.close());
			
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
	
	@FXML
	public void handleSearch() {
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
				TextField birthdate = (TextField)parent.lookup("#birthdate");
				@SuppressWarnings("unchecked")
				ComboBox<String> email = (ComboBox<String>)parent.lookup("#email");
				TextField phone = (TextField)parent.lookup("#phone");
				@SuppressWarnings("unchecked")
				ComboBox<String> gender = (ComboBox<String>)parent.lookup("#gender");
				TextField kor = (TextField)parent.lookup("#kor");
				kor.setDisable(true);
				TextField eng = (TextField)parent.lookup("#eng");
				eng.setDisable(true);
				TextField math = (TextField)parent.lookup("#math");
				math.setDisable(true);
				
				if (id.getText().equals("") &&
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
				
				boolean res = updateAfterSelect(id.getText(), name.getText(),
						birthdate.getText(), email.getValue(), phone.getText(),
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
	
	@FXML
	public void handleSort() {
		ArrayList<Student> list = new ArrayList<>();
		MySQLQueries.selectStudentOrderById(list);
		ObservableList<StudentModelForJavaFX> tableList = FXCollections.observableArrayList();
		
		for (Student s : list) {
			tableList.add(new StudentModelForJavaFX(s));
		}
		
		tableView.setItems(tableList);
		tableView.refresh();
	}
	
	@FXML
	public void handleDelete() {
		boolean flag = true;
		Stage dialog = new Stage(StageStyle.UTILITY);
		dialog.initModality(Modality.WINDOW_MODAL);
		dialog.initOwner(tableView.getScene().getWindow());
		dialog.setTitle("Delete");
		StudentModelForJavaFX m = tableView.getSelectionModel().getSelectedItem();

		try {
			Parent parent = FXMLLoader.load(getClass().getResource("../resources/form.fxml"));			
			Button insertConfirmButton = (Button)parent.lookup("#confirm");

			TextField id = (TextField)parent.lookup("#id");
			TextField name = (TextField)parent.lookup("#name");
			TextField birthdate = (TextField)parent.lookup("#birthdate");
			@SuppressWarnings("unchecked")
			ComboBox<String> email = (ComboBox<String>)parent.lookup("#email");
			TextField phone = (TextField)parent.lookup("#phone");
			@SuppressWarnings("unchecked")
			ComboBox<String> gender = (ComboBox<String>)parent.lookup("#gender");
			TextField kor = (TextField)parent.lookup("#kor");
			TextField eng = (TextField)parent.lookup("#eng");
			TextField math = (TextField)parent.lookup("#math");
			
			id.setText(m.getId()); id.setDisable(true);			
			name.setText(m.getName()); name.setDisable(true);
			birthdate.setText(m.getBirthDate()); birthdate.setDisable(true);
			email.setValue("@"+m.getEmail().split("@")[1]); email.setDisable(true);
			phone.setText(m.getPhone()); phone.setDisable(true);
			gender.setValue(m.getGender()); gender.setDisable(true);
			kor.setText(String.valueOf(m.getKor())); kor.setDisable(true);
			math.setText(String.valueOf(m.getEng())); math.setDisable(true);
			eng.setText(String.valueOf(m.getMath())); eng.setDisable(true);
			
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
	
	@FXML
	public void handleQuit() {
		System.out.println("Quit");
		Stage stage = (Stage)tableView.getScene().getWindow();
		stage.close();
	}
	
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
	
	public boolean addIntoSet(Student s) {
		boolean res = set.add(s);
		if (res == true) {
			ObservableList<StudentModelForJavaFX> tableList = tableView.getItems();
			tableList.add(new StudentModelForJavaFX(s));
			tableView.setItems(tableList);
			MySQLQueries.insertStudent(s);
		}
		return res;
	}
	
	public boolean removeSetWhereId(String id) {
		boolean res = false;
		Iterator<Student> itr = set.iterator();
		while(itr.hasNext()) {
			Student item = itr.next();
			if (id.equals(item.getId())) {
				itr.remove();
				MySQLQueries.deleteStudentById(id);
				res = true;
				ObservableList<StudentModelForJavaFX> tableList = tableView.getItems();
				Iterator<StudentModelForJavaFX> mItr = tableList.iterator();
								
				while (mItr.hasNext()) {
					StudentModelForJavaFX m = mItr.next();
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
	
	public boolean updateSetWhereId(Student s) {
		boolean res = false;
		Iterator<Student> itr = set.iterator();
		while(itr.hasNext()) {
			Student item = itr.next();
			if (s.getId().equals(item.getId())) {
				item.setBirthdate(s.getBirthdate());
				item.setEmail(s.getEmail());
				item.setName(s.getName());
				item.setGender(s.getGender());
				item.setPhone(s.getPhone());
				item.setScoreTable(s.getScoreTable());
				MySQLQueries.updateStudentWhereId(s);
				res = true;
				ObservableList<StudentModelForJavaFX> tableList = tableView.getItems();
				Iterator<StudentModelForJavaFX> mItr = tableList.iterator();
								
				while (mItr.hasNext()) {
					StudentModelForJavaFX m = mItr.next();
					if (s.getId().equals(m.getId())) {
						m.setBirthDate(s.getBirthdate());
						m.setEmail(s.getEmail());
						m.setGender(s.getGender());
						m.setGrade(s.getGender());
						m.setName(s.getName());
						m.setPhone(s.getPhone());
						m.setKor(s.getScoreTable().getKor());
						m.setEng(s.getScoreTable().getEng());
						m.setMath(s.getScoreTable().getMath());
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
	
	public boolean updateAfterSelect(String id, String name, String birthdate,
			String email, String phone, String gender) {
		boolean res = false;
		
		res = MySQLQueries.selectStudent(set, id, name, birthdate, gender, email, phone);
		
		Iterator<Student> itr = set.iterator();
		ObservableList<StudentModelForJavaFX> tableList = tableView.getItems();
		tableList.clear();
		
		while(itr.hasNext()) {
			Student item = itr.next();
			tableList.add(new StudentModelForJavaFX(item));
		}

		tableView.setItems(tableList);
		tableView.refresh();
		return res;
	}
	
	public void refreshTable() {
		ObservableList<StudentModelForJavaFX> tableList = FXCollections.observableArrayList();
		
		Iterator<Student> itr = set.iterator();
		while (itr.hasNext()) {
			tableList.add(new StudentModelForJavaFX(itr.next()));
		}
		
		tableView.setItems(tableList);
		tableView.refresh();
	}
}
