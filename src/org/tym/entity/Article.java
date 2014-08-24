package org.tym.entity;

import java.util.Date;

import org.tym.util.StringUtil;

public class Article {
	private int id;
	private String state;
	private User user;
	private Date date;
	private String title;
	private String addr;
	private int popularity;	//人气
	private int reply;	//回复人数
	
	public final static int DefaultID = -1;
	public final static int DefaultReply = -1;
	
	public Article() {
	}
	
	public Article(int id, User user, Date date, String title, int popularity) {
		this.setId(id);
		this.setState("");
		this.setUser(user);
		this.setDate(date);
		this.setTitle(title);
		this.setPopularity(popularity);	
	}
	
	public Article(int id, String state, User user, Date date, String title, int popularity) {
		this.setId(id);
		this.setState(state);
		this.setUser(user);
		this.setDate(date);
		this.setTitle(title);
		this.setPopularity(popularity);
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getAddr() {
		return addr;
	}
	public void setAddr(String addr) {
		this.addr = addr;
	}	
	public int getPopularity() {
		return popularity;
	}
	public void setPopularity(int popularity) {
		this.popularity = popularity;
	}
	public int getReply() {
		return reply;
	}

	public void setReply(int reply) {
		this.reply = reply;
	}

	@Override
	public String toString() {
		String s = null;
		
		if (this.getReply() == DefaultReply) {
			if (this.getId() == DefaultID) {
				s = "Hot" + "\t" + this.getState() + "\t" + StringUtil.DateToString(this.getDate()) + "\t" + this.getUser() + "\t" + this.getTitle() + "\t" + this.getPopularity();							
			}
			else {
				s = this.getId() + "\t" + this.getState() + "\t" + StringUtil.DateToString(this.getDate()) + "\t" + this.getUser() + "\t" + this.getTitle() + "\t" + this.getPopularity();							
			}
		}
		else {
			s = this.getId() + "\t" + this.getState() + "\t" + StringUtil.DateToString(this.getDate()) + "\t" + this.getUser() + "\t" + this.getTitle() + "\t" + this.getReply() + "/" + this.getPopularity();			
		}
		
		return s;
	}
}
