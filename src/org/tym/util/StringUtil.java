package org.tym.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 对字符串进行各种处理的工具类
 * @author njutym
 *
 */
public class StringUtil {
	/**
	 * 
	 * 日期与字符串的相互转换
	 */
	
	/*
	 * 转换一个String
	 * 成为Date
	 */
	public static Date StringToDate(String str) {
		Date date = null;
		
		Pattern p = Pattern.compile("^([A-Za-z]{3})\\s([0-9]{1,2})\\s([0-9]{2}):([0-9]{2})$");
		Matcher m = p.matcher(str);
		if (m.find()) {			
			Calendar calendar = Calendar.getInstance();
			calendar.set(Calendar.MONTH, StrToMonth(m.group(1)) - 1);
			calendar.set(Calendar.DAY_OF_MONTH, Integer.parseInt(m.group(2)));
			calendar.set(Calendar.HOUR_OF_DAY, Integer.parseInt(m.group(3)));
			calendar.set(Calendar.MINUTE, Integer.parseInt(m.group(4)));
			
			date = calendar.getTime();
		}
		
		return date;
	}
	
	/*
	 * Jan -> 1
	 * Feb -> 2
	 * Mar -> 3
	 * Apr -> 4
	 * May -> 5
	 * Jun -> 6
	 * Jul -> 7
	 * Aug -> 8
	 * Sep -> 9
	 * Oct -> 10
	 * Nov -> 11
	 * Dec -> 12
	 */
	public static int StrToMonth(String str) {
		int month = 0;
		
		switch(str) {
		case "Jan":
			month = 1;
			break;
		case "Feb":
			month = 2;
			break;
		case "Mar":
			month = 3;
			break;
		case "Apr":
			month = 4;
			break;
		case "May":
			month = 5;
			break;
		case "Jun":
			month = 6;
			break;
		case "Jul":
			month = 7;
			break;
		case "Aug":
			month = 8;
			break;
		case "Sep":
			month = 9;
			break;
		case "Oct":
			month = 10;
			break;
		case "Nov":
			month = 11;
			break;
		case "Dec":
			month = 12;
			break;
		default:
			month = 0;
		}
		
		return month;
	}
	
	/*
	 * 转换一个Date
	 * 成为String
	 */
	public static String DateToString(Date date) {
		String str = null;
		
		SimpleDateFormat format = new SimpleDateFormat("MM dd HH:mm");
		String strTemp = format.format(date);
		str = MonthToStr(strTemp.substring(0, 2)) + strTemp.substring(2);
		
		return str;
	}
	
	/*
	 * 反转
	 */
	public static String MonthToStr(String month) {
		String str = null;
		
		switch(month) {
		case "01":
			str = "Jan";
			break;
		case "02":
			str = "Feb";
			break;
		case "03":
			str = "Mar";
			break;
		case "04":
			str = "Apr";
			break;
		case "05":
			str = "May";
		case "06":
			str = "Jun";
			break;
		case "07":
			str = "Jul";
			break;
		case "08":
			str = "Aug";
			break;
		case "09":
			str = "Sep";
			break;
		case "10":
			str = "Oct";
			break;
		case "11":
			str = "Nov";
			break;
		case "12":
			str = "Dec";
			break;
		default:
			str = "TYM";
		}
		
		return str;
	}
	
	/**
	 * 其他非日期的字符串处理函数
	 */
	
	/*
	 * 转换一个String
	 * 成为Date
	 */
	public static String getBMString(String str) {
		String bmstr = null;
		
		Pattern p = Pattern.compile(".*\\[讨论区: (.+)\\] 版主\\[(.*)\\]\\s文章.+ ", Pattern.CASE_INSENSITIVE);
		Matcher m = p.matcher(str);
		if (m.find()) {			
			bmstr= m.group(2);
		}
		
		return bmstr;
	}
	
	/*
	 * 拆分cookie
	 */
	public static String[] cookieSplit(String cookie) {
		String[] strs = cookie.split(";");
		
		if (strs.length != 3) {
			return null;
		}
		strs[0] = strs[0].substring(strs[0].indexOf('=') + 1);
		strs[1] = strs[1].substring(strs[1].indexOf('=') + 1);
		strs[2] = strs[2].substring(strs[2].indexOf('=') + 1);
		
		return strs;
	}
	
	/*
	 * Format Content
	 */
	public static final String formatContent(String content) {
		String result = content.substring(content.indexOf("发信站: 南京大学小百合站 (") + 41);
		result = result.indexOf("-- ") > 0 ? result.substring(0,result.indexOf("-- ")) : result;
		result = result.replace("http://bbs.nju.edu.cn/file", "<br/><img src='http://bbs.nju.edu.cn/file");
		if(result.indexOf("[") >= 0) {
			result = result.replace("[:s]", "<img src='emotion_s'/>");
			result = result.replace("[:O]", "<img src='emotion_o'/>");
			result = result.replace("[:|]", "<img src='emotion_v'/>");
			result = result.replace("[:$]", "<img src='emotion_d'/>");
			result = result.replace("[:X]", "<img src='emotion_x'/>");
			result = result.replace("[:'(]", "<img src='emotion_q'/>");
			result = result.replace("[:@]", "<img src='emotion_a'/>");
			result = result.replace("[:-|]", "<img src='emotion_h'/>");
			result = result.replace("[:P]", "<img src='emotion_p'/>");
			result = result.replace("[:D]", "<img src='emotion_e'/>");
			result = result.replace("[:)]", "<img src='emotion_b'/>");
			result = result.replace("[:(]", "<img src='emotion_c'/>");
			result = result.replace("[:Q]", "<img src='emotion_f'/>");
			result = result.replace("[:T]", "<img src='emotion_g'/>");
			result = result.replace("[;P]", "<img src='emotion_i'/>");
			result = result.replace("[;-D]", "<img src='emotion_j'/>");
			result = result.replace("[:!]", "<img src='emotion_k'/>");
			result = result.replace("[:L]", "<img src='emotion_l'/>");
			result = result.replace("[:?]", "<img src='emotion_m'/>");
			result = result.replace("[:U]", "<img src='emotion_n'/>");
			result = result.replace("[:K]", "<img src='emotion_r'/>");
			result = result.replace("[:C-]", "<img src='emotion_t'/>");
			result = result.replace("[;X]", "<img src='emotion_u'/>");
			result = result.replace("[:H]", "<img src='emotion_w'/>");
			result = result.replace("[;bye]", "<img src='emotion_y'/>");
			result = result.replace("[;cool]", "<img src='emotion_z'/>");
			//[:-b][:-8][;PT][:hx][;K][:E][:-(][;hx][:-v][;xx]
			result = result.replace("[:-b]", "<img src='emotion_0'/>");
			result = result.replace("[:-8]", "<img src='emotion_1'/>");
			result = result.replace("[;PT]", "<img src='emotion_2'/>");
			result = result.replace("[:hx]", "<img src='emotion_3'/>");
			result = result.replace("[;K]", "<img src='emotion_4'/>");
			result = result.replace("[:E]", "<img src='emotion_5'/>");
			result = result.replace("[:-(]", "<img src='emotion_6'/>");
			result = result.replace("[;hx]", "<img src='emotion_7'/>");
			result = result.replace("[:-v]", "<img src='emotion_8'/>");
			result = result.replace("[;xx]", "<img src='emotion_9'/>");
		}
		result = result.replace("[uid]", "<uid>");
		result = result.replace("[/uid]", "</uid>");
		result = result.replace("jpg", "jpg'/><br/>");
		result = result.replace("JPG", "JPG'/><br/>");
		result = result.replace("gif", "gif'/><br/>");
		result = result.replace("GIF", "GIF'/><br/>");
		result = result.replace("png", "png'/><br/>");
		result = result.replace("PNG", "PNG'/><br/>");
		result = result.replace("jpeg", "jpeg'/><br/>");
		result = result.replace("JPEG", "JPEG'/><br/>");
		result = result.replaceAll("\\[(1;.*?|37;1|32|33)m", "");
		return result;
	}
}
