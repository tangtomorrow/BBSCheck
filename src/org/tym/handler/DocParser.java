package org.tym.handler;

import java.util.ArrayList;
import java.util.List;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.tym.Param;
import org.tym.entity.LoginInfo;
import org.tym.entity.Mail;
import org.tym.entity.User;
import org.tym.util.NetUtil;
import org.tym.util.StringUtil;

public class DocParser {
	private DocParser() {
	}
	
	private static DocParser instance = new DocParser();
	
	public static DocParser getInstance() {
		return instance;
	}
	
	/*
	 * Return MailList
	 */
	public static List<Mail> parseMailList(Document doc) {
		List<Mail> mailList = new ArrayList<Mail>();
		
		Elements rows = doc.select("tr");
		for(Element line : rows) {
			Elements links = line.select("a");
			if (links.size() == 0) {
				continue;
			}
			if(links.size() == 2) {
				String authorName = links.get(0).select("a").text();
				//TODO
				//自己的邮件？
/*				if(blockList.contains(authorName)) {
					continue;
				}*/
				Mail mail = new Mail();
				mail.setPoster(new User(authorName, null, links.get(0).select("a").attr("href")));
				mail.setTitle(links.get(1).select("a").text());
				mail.setContentUrl(links.get(1).select("a").attr("href"));			
				mail.setDate(StringUtil.StringToDate(line.select("td").get(4).text()));
				mail.setRead(line.select("img").size() != 0);
				mailList.add(mail);
				
				//System.out.println(mail);
			}

		}
		
		return mailList;
	}
	
	/*
	 * Return MailList
	 */
	public static String parseMailContent(Document doc) {
		String str = null;
		
		String content = doc.select("textarea").get(0).text();		
		content = StringUtil.formatContent(content);		
		content = content.replaceAll("来 源: \\d{1,3}\\.\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}", "");

		
		/*
		 * content:
		 * 来  源: 114.212.86.156

			同学，你好，我和你一样没领奖品，你后来领到了吗？
			--

			※ 来源:．南京大学小百合站 http://bbs.nju.edu.cn [FROM: 114.212.86.156][m
		 */
		//System.out.println(content);
		
		/*
		 * ReplyUrl
		 */
		Elements links = doc.select("a");
		String replyUrl = links.get(links.size() - 3).attr("href");
		
		str = content + "\t" + replyUrl;
		
		return str;
	}
	
	
	/*
	 * Fill Mail Content
	 */
	public static void fillMailContent(LoginInfo loginInfo, Mail mail) {
		String url = Param.AddrBBS + loginInfo.getLoginCode() + "/" + mail.getContentUrl();
		
		//Jsoup
		//String str = DocParser.parseMailContent(NetUtil.getDocByJsoup(loginInfo, url));
		
		//HttpClient
		String str = DocParser.parseMailContent(NetUtil.getDocByHttpClient(loginInfo, url));
		
		String[] strs = str.split("\t");
		mail.setContent(strs[0]);
		mail.setReplyUrl(strs[1]);
		
		System.out.println(mail);
	}
}
