package classes;

public class User {

    private int id;
    private int userroleId;
    private String name;

    public User(int id, int userroleId, String name) {
	setId(id);
	setUserroleId(userroleId);
	setName(name);
    }

    public int getId() {
	return id;
    }

    public void setId(int id) {
	this.id = id;
    }

    public int getUserroleId() {
	return userroleId;
    }

    public void setUserroleId(int userroleId) {
	this.userroleId = userroleId;
    }

    public String getName() {
	return name;
    }

    public void setName(String name) {
	this.name = name;
    }



}
