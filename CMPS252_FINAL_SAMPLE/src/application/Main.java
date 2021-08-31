package application;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import queries.UserQueries;
import queries.UserRoleQueries;
import javafx.scene.Parent;
import javafx.scene.Scene;


public class Main extends Application {
    public static UserRoleQueries userRoleQueries = new UserRoleQueries();
    public static UserQueries userQueries = new UserQueries();

    @Override
    public void start(Stage primaryStage) {
	try {
	    
	    Parent root = FXMLLoader.load(getClass().getResource("../fx/Home.fxml"));

	    Scene scene = new Scene(root, 1000, 800);
	    scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
	    primaryStage.setTitle("School");
	    primaryStage.setScene(scene);
	    primaryStage.show();

	} catch (Exception e) {
	    e.printStackTrace();
	}
    }
    
    @Override
    public void stop() {

	userRoleQueries.close();
	userQueries.close();

    }

    public static void main(String[] args) {
	launch(args);
    }
}
