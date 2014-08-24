package org.tym;

public class Param {	
	public final static String UserAgent = "Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/36.0.1985.125 Safari/537.36";
	
	public final static String AddrBBS = "http://bbs.nju.edu.cn/";
	
	/*
	 * No Login
	 */
	public final static String AddrTopTen = AddrBBS + "bbstop10";
	
	public final static String AddrBlogAll = AddrBBS + "blogall";
	
	public final static String SectionPrefix = AddrBBS + "bbsboa?sec=";
	
	public final static String BoardPrefix = "board=";
	
	//一般模式
	public final static String AddrBoardPrefix = AddrBBS + "bbsdoc?" + BoardPrefix;
	public final static String AddrArticlePrefix = AddrBBS + "bbstcon?";
	
	//主题模式
	public final static String AddrBoardTPrefix = AddrBBS + "bbstdoc?" + BoardPrefix;
	public final static String AddrArticleTPrefix = AddrBBS + "bbstcon?";
	
	/*
	 * After Login
	 * http://bbs.nju.edu.cn/vd33456/bbslogin?type=2&id=xxx&pw=xxx
	 */
	

}
