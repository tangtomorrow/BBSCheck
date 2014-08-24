package org.tym.util;

import java.io.IOException;

import org.apache.http.HttpHeaders;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.tym.Param;
import org.tym.entity.LoginInfo;

/**
 * 提供了两种登录后再访问并返回页面的方法
 * @author njutym
 * @param	loginInfo 登录后获得的登录信息，包含了cookie
 * @param	url 访问的页面地址
 */
public class NetUtil {
	/*
	 * 通过HttpClient
	 * 获取网页数据
	 */
	public static Document getDocByHttpClient(LoginInfo loginInfo, String url) {
		String result = null;
		CloseableHttpClient client = HttpClientBuilder.create().build(); 
		HttpPost post = new HttpPost(url);
		post.addHeader("Cookie", loginInfo.getLoginCookie());
		post.addHeader(HttpHeaders.USER_AGENT, Param.UserAgent);
		CloseableHttpResponse response = null;
		try {
			response = client.execute(post);
			if (response.getStatusLine().getStatusCode() == 200) {
				result = EntityUtils.toString(response.getEntity());
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		finally{
			if (response != null) {
				try {
					response.close();
				} catch (IOException e) {
					e.printStackTrace();
				}	
			}			  
		}
		
		return Jsoup.parse(result);
	}
	
	/*
	 * 通过Jsoup
	 * 获取网页数据
	 */
	public static Document getDocByJsoup(LoginInfo loginInfo, String url) {
		Document doc = null;		
		
		String[] strs = StringUtil.cookieSplit(loginInfo.getLoginCookie());
		try {
			doc = Jsoup.connect(url).
					cookie("_U_KEY", strs[0]).
					cookie("_U_UID", strs[1]).
					cookie("_U_NUM", strs[2]).
					userAgent(Param.UserAgent).
					post();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return doc;
	}
}
