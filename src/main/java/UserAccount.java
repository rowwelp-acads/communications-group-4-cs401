package main.java;

import java.io.Serializable;

public class UserAccount implements Serializable{
	private static final long serialVersionUID = 1L;
	private String id;
	
	public UserAccount() {
		
	}
	
	public String getID() {
		return id;
	}
}
