package com.joeysoft.kc868.common;

import java.io.UnsupportedEncodingException;

import org.apache.commons.lang.StringUtils;

public class StringUtil {
	
	public static String substring(String origin, int begin, int end) {
		return substring(origin, begin, end, "", "");
	}
	
	/**
	 * 截取一段字符的长度,不区分中英文,如果数字不正好，则少取一个字符位
	 * 
	 * @param origin
	 *            原始字符串
	 * @param begin
	 *            截取开始位置(一个汉字长度按2算的)
	 * @param end
	 *            截取结束位置(一个汉字长度按2算的)
	 * @param appendStr
	 *            待添加字符串
	 * @param encoding
	 *            编码格式,默认UTF8
	 * @return 返回的字符串
	 */
	public static String substring(String origin, int begin, int end,
			String appendStr, String encoding) {
		if (origin == null || origin.equals("")) {
			return appendStr;
		}
		if (begin < 0) {
			begin = 0;
		}
		if (end < 0) {
			return "";
		}
		if (begin > end) {
			return "";
		}
		if (begin == end) {
			return "";
		}
		if (begin > length(origin)) {
			return "";
		}
		if (end > length(origin)) {
			end = length(origin) - 1;
		}
		if (StringUtils.isBlank(encoding) || StringUtils.isEmpty(encoding)) {
			encoding = "UTF8";
		}
		int len = end - begin;
		byte[] strByte = new byte[len];
		try {
			System.arraycopy(origin.getBytes(encoding), begin, strByte, 0, len);
			int count = 0;
			for (int i = 0; i < len; i++) {
				int value = (int) strByte[i];
				if (value < 0) {
					count++;
				}
			}
			if (count % 2 != 0) {
				len = (len == 1) ? ++len : --len;
			}
			return new String(strByte, 0, len, encoding) + appendStr;
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException(e);
		} catch (StringIndexOutOfBoundsException ex) {
			return appendStr;
		} catch (Exception ex) {
			return "";
		}
	}

	/**
	 * 判断一个字符是Ascill字符还是其它字符（如汉，日，韩文字符）
	 * 
	 * @param c
	 *            需要判断的字符
	 * @return 返回true,Ascill字符
	 */
	public static boolean isLetter(char c) {
		int k = 0x80;
		return c / k == 0 ? true : false;
	}

	/**
	 * 得到一个字符串的长度,显示的长度,一个汉字或日韩文长度为2,英文字符长度为1
	 * 
	 * @param s
	 *            需要得到长度的字符串
	 * @return i得到的字符串长度
	 */
	public static int length(String s) {
		if (s == null) {
			return 0;
		}
		char[] c = s.toCharArray();
		int len = 0;
		for (int i = 0; i < c.length; i++) {
			len++;
			// 如果为汉，日，韩，则多加一位
			if (!isLetter(c[i])) {
				len++;
			}
		}
		return len;
	}
	
	/**
	 * 判断是否有非Ascill字符
	 * @return
	 */
	public static boolean hasNotAscill(String s){
		if (s == null) {
			return false;
		}
		char[] c = s.toCharArray();
		for (int i = 0; i < c.length; i++) {
			// 如果为汉，日，韩，则多加一位
			if (!isLetter(c[i])) {
				return true;
			}
		}
		return false;
	}

}
