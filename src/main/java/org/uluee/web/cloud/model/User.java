package org.uluee.web.cloud.model;

public class User {

	private String username;
	private String password;
	private String addId;
	private String sessionId;
	
	public String getUsername() {
		return username;
	}
	public User setUsername(String username) {
		this.username = username;
		return this;
	}
	public String getPassword() {
		return password;
	}
	public User setPassword(String password) {
		this.password = password;
		return this;
	}
	public String getAddId() {
		return addId;
	}
	public User setAddId(String addId) {
		this.addId = addId;
		return this;
	}
	public String getSessionId() {
		return sessionId;
	}
	public User setSessionId(String sessionId) {
		this.sessionId = sessionId;
		return this;
	}
	
	
}
