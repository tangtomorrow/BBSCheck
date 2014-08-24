package org.tym.entity;

import java.util.Date;

public class Mail {
	/*
	 * 信件列表获取的内容
	 */
	private User poster;	//发信人
	private String title;	//标题
	private String contentUrl;	//信件地址
	private Date date;	//发信时间
	private boolean isRead;	//是否已读
	
	//访问信件地址后获取的内容
	private String content = "";	//信件内容
	private String replyUrl = "";
		
	public User getPoster() {
		return poster;
	}
	public void setPoster(User poster) {
		this.poster = poster;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getContentUrl() {
		return contentUrl;
	}
	public void setContentUrl(String contentUrl) {
		this.contentUrl = contentUrl;
	}
	public boolean isRead() {
		return isRead;
	}
	public void setRead(boolean isRead) {
		this.isRead = isRead;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public String getReplyUrl() {
		return replyUrl;
	}
	public void setReplyUrl(String replyUrl) {
		this.replyUrl = replyUrl;
	}
	
	@Override
	public String toString() {
		StringBuffer str = new StringBuffer();;
		
		str.append(poster.getId());
		str.append("\t");
		str.append(title);
		str.append("\t");
		str.append(contentUrl);
		str.append("\t");
		str.append(date);
		str.append("\t");
		str.append(isRead);
		str.append("\t");
		str.append(content);
		str.append("\t");
		str.append(replyUrl);
		
		return str.toString();
	}
}
