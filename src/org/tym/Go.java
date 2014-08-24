package org.tym;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.tym.entity.Board;
import org.tym.entity.Board.BoardMode;
import org.tym.entity.Article;
import org.tym.entity.LoginInfo;
import org.tym.entity.Mail;
import org.tym.entity.Section;
import org.tym.entity.User;
import org.tym.handler.BoardParser;
import org.tym.handler.DocParser;
import org.tym.util.LoginUtil;
import org.tym.util.DocUtil;
import org.tym.util.NetUtil;
import org.tym.util.StringUtil;
import org.tym.widget.BoardChooser;

public class Go {

	public static void main(String[] args) {
		//Test Board
/*		Board bd = new Board("TaiZhou");
		BoardParser.getInstance().parseBoard(bd, BoardMode.CommonMode);
		for (Article sa : BoardParser.getInstance().getArticles()) {
			System.out.println(sa);
		}
		System.out.println(bd.getManagers());*/
		
		//Test XMLDocUtil
		//XMLDocUtil.getSections();
		//XMLDocUtil.getBoardsBySection(XMLDocUtil.getSections().get(0));
		
		//Test logout
		//LoginUtil.logout(loginInfo);
		
		//Test BoardChooser
		/*
		JFrame j = new JFrame();
		j.setSize(300,300);
		j.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		j.setLayout(null);	
		j.setContentPane(new BoardChooser());
		j.setVisible(true);
		*/
		
		//Test Mail
		User user = new User("supergod");
		LoginInfo loginInfo = LoginUtil.login(user.getId(), "tangyaming");
		//System.out.println(loginInfo.getLoginCode());
		//System.out.println(loginInfo.getLoginCookie());
		
		String urlMail = Param.AddrBBS + loginInfo.getLoginCode() + "/" + "bbsmail";
		List<Mail> list =  DocParser.parseMailList(NetUtil.getDocByHttpClient(loginInfo, urlMail));
		//List<Mail> list =  DocParser.parseMailList(NetUtil.getDocByJsoup(loginInfo, urlMail));		
		DocParser.fillMailContent(loginInfo, list.get(0));
	}

}
