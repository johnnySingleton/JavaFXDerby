package fx;

import java.io.IOException;
import java.util.ArrayList;

import application.Main;
import classes.User;
import classes.UserRole;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import queries.UserQueries;

public class UsersController {
    
    
    private UserQueries userQueries = Main.userQueries;
    
    private ArrayList<UserRole> userRoles;
    
    
    
    public ArrayList<UserRole> getUserRoles() {
        return userRoles;
    }


    public void setUserRoles(ArrayList<UserRole> userRoles) {
        this.userRoles = userRoles;
        role.setItems(FXCollections.observableArrayList(userRoles));
    }

    @FXML
    private ChoiceBox<UserRole> role;
    
    @FXML
    private TextField name;

 

    @FXML
    private TableView<User> table;

    @FXML
    private TableColumn<User, Integer> idTable;

    @FXML
    private TableColumn<User, String> nameTable;

    @FXML
    private TableColumn<User, String> roleTable;
    
    @FXML
    public void initialize() {
	idTable.setCellValueFactory(new PropertyValueFactory<User, Integer>("id"));
	nameTable.setCellValueFactory(new PropertyValueFactory<User, String>("name"));
	roleTable.setCellValueFactory(values -> new ReadOnlyStringWrapper(values.getValue().getUserroleId()==1 ?"Student":"Teacher"));
	table.setItems(FXCollections.observableArrayList(userQueries.getAllUsers()));
	

    }
    
    
    @FXML
    public void goBack(ActionEvent event) throws IOException {


	Node node = (Node) event.getSource();
	Stage stage = (Stage) node.getScene().getWindow();

	FXMLLoader loader = new FXMLLoader();

	loader.setLocation(UsersController.class.getResource("Home.fxml"));
	Parent root = loader.load();


	Scene scene = new Scene(root, 1000, 800);
	stage.setScene(scene);
	stage.setResizable(false);
	stage.show();
    }

    @FXML
    public void add(ActionEvent event) throws IOException {

	userQueries.addUser(name.getText(),role.getSelectionModel().getSelectedItem().getId());
	table.setItems(FXCollections.observableArrayList(userQueries.getAllUsers()));

    }



}
