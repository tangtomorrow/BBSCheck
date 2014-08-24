package org.tym.util;

import java.io.IOException;
import java.util.Random;

import org.jsoup.Jsoup;
import org.tym.Param;
import org.tym.entity.LoginInfo;

/**
 * 登录和注销的操作
 * @author njutym
 *
 */
public class LoginUtil {
	/*
	 * Login
	 * get LoginInfo After Login
	 */
	public static LoginInfo login(String id, String password) {
		return login(id, password, 3);
	}
	
	public static final LoginInfo login(String id, String password, int tryTimes) {
		LoginInfo loginInfo = null;
		
		if(tryTimes <= 0) {
			return null;
		}
		String loginCode = String.valueOf(new Random().nextInt(99999)%(90000) + 10000);
		String url = Param.AddrBBS + "vd" + loginCode + "/bbslogin?type=2&id=" + id + "&pw=" + password;
		try {
			String doc = Jsoup.connect(url).get().toString();
			//System.out.println(doc);
			if (doc.indexOf("setCookie") < 0) {
				return login(id, password, tryTimes - 1);
			} else {
				loginInfo = new LoginInfo();
				String loginString = doc.substring(doc.indexOf("setCookie"));
				//System.out.println(loginString);
				loginString = loginString.substring(11, loginString.indexOf(")") - 1) + "+vd" + loginCode;
				//System.out.println(loginString);
				String[] tmpString =  loginString.split("\\+");
				//System.out.println(tmpString[0] + "\t" + tmpString[1] + "\t" + tmpString[2]);
				String _U_KEY = String.valueOf(Integer.parseInt(tmpString[1])-2);
				String[] loginTmp = tmpString[0].split("N");
				String _U_UID = loginTmp[1];
				String _U_NUM = "" + String.valueOf(Integer.parseInt(loginTmp[0]) + 2);
				loginInfo.setLoginCookie("_U_KEY=" + _U_KEY + "; " + "_U_UID=" + _U_UID + "; " + "_U_NUM=" + _U_NUM + ";");
				//System.out.println(loginInfo.getLoginCookie());
				loginInfo.setLoginCode(tmpString[2]);
				loginInfo.setUsername(id);
				loginInfo.setPassword(password);
			}
		} catch (IOException e) {
			return login(id, password, tryTimes - 1);
		}
		
		return loginInfo;
	}
	
	/*
	 * Logout
	 */
	public static final boolean logout(LoginInfo loginInfo) {
		boolean flag = false;
		
		//TODO
		String url = Param.AddrBBS + loginInfo.getLoginCode() + "/bbslogout";
		NetUtil.getDocByHttpClient(loginInfo, url);
		
		return flag;
	}
}
