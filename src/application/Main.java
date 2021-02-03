/*
 * purpose : javafx - java - MySQL co-work project
 * 개발자 :송훈일 (Neil)
 * 개발일자 : Feb 3, 2021
 * 실행 영상 : 
 * 주석에 관해 : 에디터에 한글로 쓰는게 불편해서 영어로 쓴 부분도 있습니다.
 * TODO : Connector/J가 정상적으로 작동하는 수준으로 구현되었는데 이후 DB Indexing, primary key & foreign key 활용까지 더 발전시켜보자.
 */
package application;
	
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class Main extends Application {
	public static RootController rootController = null;
	
	@Override
	public void init() {
		System.out.println(Thread.currentThread().getName() + ": init()");
	}
	
	@Override
	public void start(Stage primaryStage) {
		try {
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../resources/root.fxml"));
			Parent root = fxmlLoader.load();
			rootController = (RootController) fxmlLoader.getController();
			Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("../resources/application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.setTitle("JavaFX-Java-MySQL co-work project");
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void stop() {
		System.out.println(Thread.currentThread().getName() + ": stop()");
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
