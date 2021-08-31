package queries;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import classes.User;

public class UserQueries {

    private static final String URL = "jdbc:derby:schooldb;create=true";

    private Connection connection; // manages connection
    private PreparedStatement selectAllUsers;
    private PreparedStatement insertNewUser;
    private PreparedStatement getUserById;

    // constructor
    public UserQueries() {
	try {
	    System.out.println("init");
	    connection = DriverManager.getConnection(URL);

	    createTable();

	    // create query that selects all entries in Books
	    selectAllUsers = connection.prepareStatement("SELECT * FROM Users");

	    // create query that selects a book with a specific name
	    getUserById = connection.prepareStatement("SELECT * FROM Users WHERE ID = ? ");

	    // create insert that adds a new entry into the database
	    insertNewUser = connection
		    .prepareStatement("INSERT INTO Users " + "(USER_ROLE_ID, NAME) " + "VALUES (?, ?)");
	} catch (SQLException sqlException) {
	    sqlException.printStackTrace();
	    System.exit(1);
	}
    } // end PersonQueries constructor

    public User getUserById(int id) {
	ArrayList<User> results = null;
	ResultSet resultSet = null;

	try {
	    // executeQuery returns ResultSet containing matching entries
	    getUserById.setInt(1, id);
	    resultSet = getUserById.executeQuery();
	    results = new ArrayList<User>();

	    while (resultSet.next()) {
		results.add(new User(resultSet.getInt("ID"), resultSet.getInt("USER_ROLE_ID"),
			resultSet.getString("Name")));
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

	return results.get(0);
    }

    // select all of the addresses in the database
    public ArrayList<User> getAllUsers() {
	ArrayList<User> results = null;
	ResultSet resultSet = null;

	try {
	    // executeQuery returns ResultSet containing matching entries
	    resultSet = selectAllUsers.executeQuery();
	    results = new ArrayList<User>();

	    while (resultSet.next()) {
		results.add(new User(resultSet.getInt("ID"), resultSet.getInt("USER_ROLE_ID"),
			resultSet.getString("Name")));
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

    // add an entry
    public int addUser(String Name, int role) {
	int result = 0;

	// set parameters, then execute insertNewPerson
	try {
	    insertNewUser.setInt(1, role);
	    insertNewUser.setString(2, Name);

	    // insert the new entry; returns # of rows updated
	    result = insertNewUser.executeUpdate();
	} catch (SQLException sqlException) {
	    sqlException.printStackTrace();
	    close();
	}

	return result;
    }

    public void createTable() throws SQLException {
	try {
	    Statement statement = connection.createStatement();
	    statement.executeUpdate("CREATE TABLE Users(" + " ID INT NOT NULL GENERATED ALWAYS AS IDENTITY "
		    + " , USER_ROLE_ID INT NOT NULL " + " , NAME VARCHAR(50) NOT NULL "
		    + " , CONSTRAINT user_pk PRIMARY KEY (id) "
		    + " , CONSTRAINT user_fk  FOREIGN KEY (USER_ROLE_ID) REFERENCES UserRole" + ")");

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
