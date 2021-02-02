package application;

import java.net.URL;
import java.util.HashSet;
import java.util.Iterator;
import java.util.ResourceBundle;

import data.Person;
import data.ScoreTable;
import data.Student;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
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
		
		ObservableList<StudentModelForJavaFX> tableList = FXCollections.observableArrayList();
		
		Iterator<Student> itr = set.iterator();
		while (itr.hasNext()) {
			tableList.add(new StudentModelForJavaFX(itr.next()));
		}
		
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
		
		tableView.setItems(tableList);
	}
	
	@FXML
	public void handleInsert() {
		System.out.println("Insert");
	}
	
	@FXML
	public void handleUpdate() {
		System.out.println("Update");
	}
	
	@FXML
	public void handleSearch() {
		System.out.println("Search");
	}
	
	@FXML
	public void handleSort() {
		System.out.println("Sort");
	}
	
	@FXML
	public void handleDelete() {
		System.out.println("Delete");
	}
	
	@FXML
	public void handleQuit() {
		System.out.println("Quit");
	}
}
