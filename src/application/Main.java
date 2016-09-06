package application;
	
import javafx.application.Application;

import javafx.stage.Stage;
import javafx.scene.Scene;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import application.db.database;


public class Main extends Application {
	
	private static database db;
	
	@Override
	public void start(Stage primaryStage) throws Exception {
			
			
			db = new database();
			Parent root = FXMLLoader.load(getClass().getResource("GUI.fxml"));
			Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setTitle("ToDo List");
			primaryStage.setScene(scene);
			primaryStage.show();
			
			String workingDir = System.getProperty("user.dir");
			System.out.println("Current working directory : " + workingDir);
			
			primaryStage.setOnCloseRequest(event -> {
			    stageIsClosing(primaryStage);
			});
			
		
	}

	
	public static void main(String[] args) {
		launch(args);
	}
	
	public static database getDatabase(){
		return db;
	}
	
	public static void stageIsClosing(Stage primaryStage){
		    System.out.println("Stage is closing");
		    db.setClose();
		    System.out.println("db closed and saved?");
	}


}
