/**   
 * @Title: TranscodeUtil.java 
 * @Package com.act.web.util 
 * @Description: (字符串的转码，可以处理字符串到二进制字符、16进制字符、unicode字符、base64字符之间的转换 ) 
 * @author   fmj
 * @date 2017-2-10 下午12:46:50 
 * @version V1.0   
 */
package com.act.web.util;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

import org.apache.commons.codec.digest.DigestUtils;

public class TranscodeUtil {
	
	/**
	 * base64字符集 0..63
	 */
	 static private char[] alphabet =  
		        "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/="  
		        .toCharArray();  
	
	/**
	 * 初始化base64字符集表
	 */
	static private byte[] codes = new byte[256];
	
	static {
		for (int i = 0; i < 256; i++)
			codes[i] = -1;
		for (int i = 'A'; i <= 'Z'; i++)
			codes[i] = (byte) (i - 'A');
		for (int i = 'a'; i <= 'z'; i++)
			codes[i] = (byte) (26 + i - 'a');
		for (int i = '0'; i <= '9'; i++)
			codes[i] = (byte) (52 + i - '0');
		codes['+'] = 62;
		codes['/'] = 63;
	} 

	/**
	 * 将base64码转换成字节数组
	 */
	public static byte[] base64StrToByteArray(String base64Str) {
		char[] dataArr = new char[base64Str.length()];
		base64Str.getChars(0, base64Str.length(), dataArr, 0);
		return decode(dataArr);
	}

	
	private static byte[] decode(char[] data) {
		int len = ((data.length + 3) / 4) * 3;
		if (data.length > 0 && data[data.length - 1] == '=')--len;
		if (data.length > 1 && data[data.length - 2] == '=')--len;
		byte[] out = new byte[len];
		int shift = 0;
		int accum = 0;
		int index = 0;
		for (int ix = 0; ix < data.length; ix++) {
			int value = codes[data[ix] & 0xFF];
			if (value >= 0) {
				accum <<= 6;
				shift += 6;
				accum |= value;
				if (shift >= 8) {
					shift -= 8;
					out[index++] = (byte) ((accum >> shift) & 0xff);
				}
			}
		}
		if (index != out.length)
			throw new Error("miscalculated data length!");
		return out;
	}

	public static String byteArrayToBase64Str(byte[] byteArray) {
		return new String(encode(byteArray));
	}

	/**
	 * 将一个字节数组转换成base64的字符数组
	 */
	private static char[] encode(byte[] data) {
		char[] out = new char[((data.length + 2) / 3) * 4];
		for (int i = 0, index = 0; i < data.length; i += 3, index += 4) {
			boolean quad = false;
			boolean trip = false;
			int val = (0xFF & (int) data[i]);
			val <<= 8;
			if ((i + 1) < data.length) {
				val |= (0xFF & (int) data[i + 1]);
				trip = true;
			}
			val <<= 8;
			if ((i + 2) < data.length) {
				val |= (0xFF & (int) data[i + 2]);
				quad = true;
			}
			out[index + 3] = alphabet[(quad ? (val & 0x3F) : 64)];
			val >>= 6;
			out[index + 2] = alphabet[(trip ? (val & 0x3F) : 64)];
			val >>= 6;
			out[index + 1] = alphabet[val & 0x3F];
			val >>= 6;
			out[index + 0] = alphabet[val & 0x3F];
		}
		return out;
	}
	
	/**
	 * md5加密方法
	 * 
	 * @param str
	 * @return
	 * @throws NoSuchAlgorithmException
	 * @throws UnsupportedEncodingException
	 */
	public static String encoderByMd5(String str) throws NoSuchAlgorithmException,
			UnsupportedEncodingException {
		String md5Str = DigestUtils.md5Hex(str);
		return md5Str;
	}

}
