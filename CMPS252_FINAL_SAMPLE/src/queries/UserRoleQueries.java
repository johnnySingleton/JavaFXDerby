package queries;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import classes.UserRole;

public class UserRoleQueries {

    private static final String URL = "jdbc:derby:schooldb;create=true";

    private Connection connection; // manages connection
    private PreparedStatement insertNewRole;
    private PreparedStatement selectAllRoles;

    // constructor
    public UserRoleQueries() {
	try {
	    System.out.println("init");
	    connection = DriverManager.getConnection(URL);
	    createTable();
	    
	    insertNewRole = connection.prepareStatement("INSERT INTO UserRole " + "(ROLE) " + "VALUES (?)");

	    // create query that selects all entries in Roles
	    selectAllRoles = connection.prepareStatement("SELECT * FROM UserRole");

	} catch (SQLException sqlException) {
	    sqlException.printStackTrace();
	    System.exit(1);
	}
    } // end PersonQueries constructor

    // add an entry
    public int addRole(String Role) {
	int result = 0;

	// set parameters, then execute insertNewPerson
	try {
	    insertNewRole.setString(1, Role);

	    // insert the new entry; returns # of rows updated
	    result = insertNewRole.executeUpdate();
	} catch (SQLException sqlException) {
	    sqlException.printStackTrace();
	    close();
	}

	return result;
    }
    
    
    // select all of the addresses in the database
    public ArrayList<UserRole> getAllRoles() {
	ArrayList<UserRole> results = null;
	ResultSet resultSet = null;

	try {
	    // executeQuery returns ResultSet containing matching entries
	    resultSet = selectAllRoles.executeQuery();
	    results = new ArrayList<UserRole>();

	    while (resultSet.next()) {
		results.add(new UserRole(resultSet.getInt("ID"), resultSet.getString("ROLE")));
	    }
	} catch (SQLException sqlException) {
	    sqlException.printStackTrace();
	} finally {
	    try {
		resultSet.close();
	    } catch (SQLException sqlException) {
		sqlException.printStackTrace();
		close();
	    }
	}

	return results;
    }

    public void createTable() throws SQLException {
	try {
	    Statement statement = connection.createStatement();
	    statement.executeUpdate("CREATE TABLE UserRole("
		    + " ID INT NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1) "
		    + " , ROLE VARCHAR(50) NOT NULL " + ", CONSTRAINT primary_key PRIMARY KEY (id)" + ")");

	    insertNewRole = connection.prepareStatement("INSERT INTO UserRole " + "(ROLE) " + "VALUES (?)");

	    // create query that selects all entries in Roles
	    selectAllRoles = connection.prepareStatement("SELECT * FROM UserRole");

	    addRole("Student");
	    addRole("Teacher");
	    
	} catch (SQLException se) {
	    if (!se.getSQLState().equals("X0Y32"))
		throw se;
	}
    }
    
    

    // close the database connection
    public void close() {
	try {
	    connection.close();
	} catch (SQLException sqlException) {
	    sqlException.printStackTrace();
	}
    }

}
