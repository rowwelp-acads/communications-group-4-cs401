package main.java;
// TODO: Write JUnit tests!
public class UserAccount {
	// Attributes.
	private static int num = 0;
	private String username;
	private String userId;
	private String email;
	private String password;
	
	// Constructor.
	public UserAccount(String userId, String username, String email, String password) {
		this.userId = username + Integer.toString(num + 1);
		this.username = username;
        this.email = email;
        this.password = password;
	}
	
  // Getters and Setters.
  public String getUserId() {
      return userId;
  }

  public void setUserId(String userId) {
      this.userId = userId;
  }

  public String getUsername() {
      return username;
  }

  public void setUsername(String username) {
      this.username = username;
  }

  public String getEmail() {
      return email;
  }

  public void setEmail(String email) {
      this.email = email;
  }

  public String getPassword() {
      return password;
  }

  public void setPassword(String password) {
      this.password = password;
  }

  // Methods
  public boolean login(String username, String password) {
      return this.username.equals(username) && this.password.equals(password);
  }

  public void logout() {
      System.out.println("User " + username + " has logged out.");
  }

  @Override
  public String toString() {
	  return "UserAccount [userId=" + userId + ",username=" + username + ",email=" + email + "]";
  }
}
