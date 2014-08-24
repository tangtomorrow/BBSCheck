package org.tym.handler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.tym.Param;
import org.tym.entity.Board;
import org.tym.entity.Board.BoardMode;
import org.tym.entity.Article;
import org.tym.entity.User;
import org.tym.util.StringUtil;

public class BoardParser {
	private List<Article> articles;
	
	private BoardParser() {
		articles = new ArrayList<Article>();
	}
	
	private static BoardParser instance = new BoardParser();
	
	public static BoardParser getInstance() {
		return instance;
	}
	
	public void parseBoard(Board board, BoardMode mode) {
		String addr = this.getBoardAddr(board, mode);
				
		try {		
			Document doc = Jsoup.connect(addr).get();
			//System.out.println(doc);
			this.setBoardManagers(doc, board, mode);
			this.fillArticles(doc, mode);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/*
	 * 根据Board和BoardMode
	 * 获取Board地址
	 */
	private String getBoardAddr(Board board, BoardMode mode) {
		String addr = null;
		
		if (mode == BoardMode.CommonMode) {
			addr = Param.AddrBoardPrefix + board.getName();
		}
		else if (mode == BoardMode.TitleMode){
			addr = Param.AddrBoardTPrefix + board.getName();
		}
		
		return addr;
	}
	
	/*
	 * 根据Board和BoardMode
	 * 设置Board的BoardInfo
	 * 主要是版主列表
	 */
	private void setBoardManagers(Document doc, Board board, BoardMode mode) {	
		List<User> bmlist = new ArrayList<User>();
		
		if (mode == BoardMode.CommonMode) {			
			Elements bm = doc.select("table").get(1).select("a[href~=(bbsqry\\?userid=)(.*)]");
			for (Element b : bm) {
				bmlist.add(new User(b.text()));
			}			
		}
		else if (mode == BoardMode.TitleMode){
			String[] strs = StringUtil.getBMString(doc.select("center").text()).split("\\s"); 
			for (String str : strs) {
				bmlist.add(new User(str));
			}
		}
		board.setManagers(bmlist);
	}
	
	/*
	 * 根据Doc、Board和BoardMode
	 * 填充articles列表
	 */
	private void fillArticles(Document doc, BoardMode mode) {
		Element table = doc.select("table").get(this.getTableIndexOfArticles(mode));
		Elements trs = table.select("tr");			
		int index = 1;
		Article article = null;
		while(index < trs.size()) {
			Element tr = trs.get(index);
			article = new Article();
			
			//ID
			if (tr.select("td").get(0).children().size() == 0) {				
				article.setId(Integer.parseInt(tr.select("td").get(0).text()));
			}
			else {
				article.setId(Article.DefaultID);
			}
			
			//State
			if (tr.select("td").get(1).select("nobr").size() != 0) {					
				article.setState("置顶");		
			}
			else {
				article.setState("");
			}
			
			//User				
			article.setUser(new User(tr.select("td").get(2).select("a").get(0).text()));
			
			//Date		
			if (mode == BoardMode.TitleMode) {
				article.setDate(StringUtil.StringToDate(tr.select("td").get(this.getTdIndexOfDate(mode)).text()));				
			}
			else if (mode == BoardMode.CommonMode) {
				article.setDate(StringUtil.StringToDate(tr.select("td").get(this.getTdIndexOfDate(mode)).select("nobr").text()));
			}
			
			//Title
			article.setTitle(tr.select("td").get(this.getTdIndexOfDate(mode) + 1).select("a").get(0).text());
			
			//Address
			article.setAddr(tr.select("td").get(this.getTdIndexOfDate(mode) + 1).select("a").get(0).attr("href"));
			
			//Popularity&Reply
			if (tr.select("td").get(this.getTdIndexOfDate(mode) + 2).select("font").size() > 1)
			{
				article.setReply(Integer.parseInt(tr.select("td").get(this.getTdIndexOfDate(mode) + 2).select("font").get(0).text()));
				article.setPopularity(Integer.parseInt(tr.select("td").get(this.getTdIndexOfDate(mode) + 2).select("font").get(1).text()));	
			}
			else {
				article.setPopularity(Integer.parseInt(tr.select("td").get(this.getTdIndexOfDate(mode) + 2).select("font").get(0).text()));	
				article.setReply(Article.DefaultReply);							
			}
		
			articles.add(article);
			
			index++;
		}		
	}
	
	/*
	 * 根据BoardMode
	 * 获取文章所在Table的Index
	 */
	private int getTableIndexOfArticles(BoardMode mode) {	
		int index = 0;
		if (mode == BoardMode.CommonMode) {			
			index = 3;
		}
		else if (mode == BoardMode.TitleMode){
			index = 0;
		}
		return index;
	}
	
	/*
	 * 根据BoardMode
	 * 获取文章所在td的Index
	 */
	private int getTdIndexOfDate(BoardMode mode) {	
		int index = 0;
		if (mode == BoardMode.CommonMode) {			
			index = 4;
		}
		else if (mode == BoardMode.TitleMode){
			index = 3;
		}
		return index;
	}

	public List<Article> getArticles() {
		return articles;
	}
}
