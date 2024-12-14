package Models;

public class User {

	private String userName;
	private String password;
	private String userType;

	public User(String userName, String password, String userType) {

		this.userName = userName;
		this.password = password;
		this.userType = userType;

	}

	public String getUserName() {
		return userName;
	}

	public String getPassword() {
		return password;
	}

	public String getUserType() {
		return userType;
	}

}
