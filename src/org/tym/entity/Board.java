package org.tym.entity;

import java.util.List;

public class Board {	
	private int id;
	private int sectionID;	//所属区
	private String name;	//Name
	private String cname;	//ChineseName
	private String category;	//类别
	private List<User> managers;	//版主们
	
	public Board(String name) {
		this.name = name;
	}
	
	public Board(int id, int sectionID, String name, String cname, String category) {
		this.id = id;
		this.sectionID = sectionID;
		this.name = name;
		this.cname = cname;
		this.category = category;
	}
	
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getSectionID() {
		return sectionID;
	}

	public void setSectionID(int sectionID) {
		this.sectionID = sectionID;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCname() {
		return cname;
	}

	public void setCname(String cname) {
		this.cname = cname;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public List<User> getManagers() {
		return managers;
	}

	public void setManagers(List<User> managers) {
		this.managers = managers;
	}
	
	@Override
	public String toString() {
		return this.name + "(" + this.cname + ")";
	}
	
	/*
	 * Enum BoardMode
	 */
	public enum BoardMode{
		CommonMode(),
		TitleMode();
	}
}
