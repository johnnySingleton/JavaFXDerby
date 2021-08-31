package fx;

import java.io.IOException;

import application.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;

import javafx.stage.Stage;
import queries.UserQueries;
import queries.UserRoleQueries;

public class HomeController {
    
    private UserRoleQueries userRoleQueries = Main.userRoleQueries;
    
    
    @FXML
    public void goToUsers(ActionEvent event) throws IOException {
	  Node node = (Node) event.getSource();
	    Stage stage = (Stage) node.getScene().getWindow();

	    FXMLLoader loader = new FXMLLoader();

	    loader.setLocation(HomeController.class.getResource("Users.fxml"));
	    Parent root = loader.load();
	    
	    UsersController controller = loader.getController();
	    System.out.println(1);
	    System.out.println(userRoleQueries.getAllRoles());
	    controller.setUserRoles(userRoleQueries.getAllRoles());
	    

	    Scene scene = new Scene(root, 1000, 800);
	    stage.setScene(scene);
	    stage.setResizable(false);
	    stage.show();


    }

}
