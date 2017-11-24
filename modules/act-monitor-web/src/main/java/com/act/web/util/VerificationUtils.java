package com.act.web.util;

import java.util.regex.Pattern;

/**
 * @Description: 数据校验类，提供基础数据校验方法
 * @author xujian
 * @date 2017-5-8 上午10:18:18
 * @version V1.0
 */
public class VerificationUtils {
	/**
	 * 姓名格式校验
	 * @param name  需要校验的姓名
	 * @return msg	返回姓名校验信息:
	 * 		  		0 - 校验合格;
	 * 				1 - 姓名为空;
	 * 				2 - 姓名过长或者过短;
	 * 				
	 */
	public static int validatorName(String name) {
		if (checkStrEmpty(name)) {
			return 1;
		}
		if (name.length() <2 || name.length() >16) {
			return 2;
		}
		
		return 0;
	}
	
	/**
	 * 检查给定字符串是否都是中文
	 * @param str
	 * @return 全部为中文,返回true,否则返回false
	 */
	public static boolean checkIsAllChiniese (String str) {
		if (checkStrEmpty(str)) {
			return false;
		}
		String reg = "[\u4e00-\u9fa5]+";
		return Pattern.matches(reg, str);
	}
	
	/**
	 * 检测给定在吃饭是否都是英文
	 * @param str
	 * @return 全部为英文,返回true;否则返回false
	 */
	public static boolean checkIsAllEnglish (String str) {
		if (checkStrEmpty(str)) {
			return false;
		}
		String reg = "[a-zA-Z]+";
		return Pattern.matches(reg, str);
		
	}
	
	
	/**
	 * 字符串是否有重复字符串
	 * 
	 * @param length 同一字符串连续出现次数,请输入大于或等于2的数
	 * @param str 需要校验的字符串
	 * @return 判断str中是否有字符串连续出现length次,有则返回true,没有则返回false. 如：str =
	 *         "13647985545" ;length = 2 ,str中"55"连续出现2次，返回true
	 */
	public static boolean checkStrIsRepeat(int length, String str) {
		if (checkStrEmpty(str)) {
			return false;
		}
		if (length < 2) {
			return true;
		}
		if (length > str.length()) {
			return false;
		}
		for (int i = 0; i <= ( str.length() - length ); i++) {
			String temp = String.valueOf(str.charAt(i));
			if (!checkStrEmpty(temp) && str.indexOf(assembleSameStr(length, temp)) != -1) {
				return true;
			}
		}
		return false;
		
	}

	/**
	 * 把指定字符串str连续拼接times次
	 * @param times 拼接次数
	 * @param str 拼接的字符串
	 * @return 返回拼接后的字符串 ,如：times = 2, str = "a", 则返回"aa"
	 */
	public static String assembleSameStr (int times ,String str) {
		if (checkStrEmpty(str)) {
			return null;
		}
		if (times < 2) {
			return str;
		}
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < times; i++) {
			sb.append(str);
		}
		return sb.toString();
	}
	

	/**
	 * 判断字符串是否为null
	 * 
	 * @param str
	 *            需要判断的字符串
	 * @return 不为null、""、"null",返回false;否则返回true
	 */
	public static boolean checkStrEmpty(String str) {
		return !(str != null && !"".equals(str.trim()) && !"null".equals(str
				.trim()));
	}

	
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String str = "adjfaj fl";
		System.out.println(checkIsAllEnglish(str));
		
//		System.out.println(validatorName("湖北武汉市"));
		
//		System.out.println(checkStrIsRepeat(4, "11121dadffaderddds"));
		
//		System.out.println("ssssaa".charAt(2));
		
//		System.out.println(assembleSameStr(1, "aaa"));
		
	}

}
