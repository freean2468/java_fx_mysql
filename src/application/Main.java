/*
 * purpose : javafx - java - MySQL co-work project
 * 개발자 :송훈일 (Neil)
 * 실행 영상 : 
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
