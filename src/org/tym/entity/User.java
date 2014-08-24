package org.tym.entity;

public class User {
	private String id;
	private String userUrl;
	private String name;
	
	public User(String id) {
		this.id = id;
	}
	
	/*
	public User(String id, String name) {
		this.id = id;
		this.name = name;
	}
	*/
	
	public User(String id, String name, String userUrl) {
		this.id = id;
		this.name = name;
		this.userUrl = userUrl;
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getUserUrl() {
		return userUrl;
	}

	public void setUserUrl(String userUrl) {
		this.userUrl = userUrl;
	}
	
	@Override
	public String toString() {
		return this.getId();
	}
}
