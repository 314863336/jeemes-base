/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.huitai.common.utils;

import org.springframework.util.StringUtils;

import javax.sql.rowset.serial.SerialClob;
import javax.sql.rowset.serial.SerialException;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.sql.Clob;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 字符串工具类, 继承org.apache.commons.lang3.StringUtils类
 * 
 * @author ThinkGem
 * @version 2018-1-6
 */
public class StringUtil extends org.apache.commons.lang3.StringUtils {

	private static final char SEPARATOR = '_';
	private static final String CHARSET_NAME = "UTF-8";

	/**
	 * 转换为字节数组
	 * 
	 * @param str
	 * @return
	 */
	public static byte[] getBytes(String str) {
		if (str != null) {
			try {
				return str.getBytes(CHARSET_NAME);
			} catch (UnsupportedEncodingException e) {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 转换为字节数组
	 * 
	 * @param str
	 * @return
	 */
	public static String toString(byte[] bytes) {
		try {
			return new String(bytes, CHARSET_NAME);
		} catch (UnsupportedEncodingException e) {
			return EMPTY;
		}
	}

	/**
	 * 是否包含字符串
	 * 
	 * @param str  验证字符串
	 * @param strs 字符串组
	 * @return 包含返回true
	 */
	public static boolean inString(String str, String... strs) {
		if (str != null && strs != null) {
			for (String s : strs) {
				if (str.equals(trim(s))) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * 是否包含字符串
	 * 
	 * @param str  验证字符串
	 * @param strs 字符串组
	 * @return 包含返回true
	 */
	public static boolean inStringIgnoreCase(String str, String... strs) {
		if (str != null && strs != null) {
			for (String s : strs) {
				if (str.equalsIgnoreCase(trim(s))) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * 使用正则表达式来判断字符串中是否包含字母 比如判断 字符“CBS001”中是否包含CBS 调用方法是【judgeContainsStr(“CBS ”)】
	 * 
	 * @param str1 正则
	 * @param str  待检验的字符串
	 * @return 返回是否包含 true: 包含字母 ;false 不包含字母
	 */
	public static boolean judgeContainsStr(String str1, String str) {
		String regex = ".*[" + str1 + "]+.*";
		Matcher m = Pattern.compile(regex).matcher(str);
		return m.matches();
	}

	/**
	 * 去除左右空格（包含中文空格）
	 * 
	 * @param str
	 */
	public static String trim2(final String str) {
		return str == null ? null : str.replaceAll("^[\\s|　| ]*|[\\s|　| ]*$", "");
	}

	/**
	 * 替换掉HTML标签方法
	 */
	public static String stripHtml(String html) {
		if (isBlank(html)) {
			return "";
		}
		// html.replaceAll("\\&[a-zA-Z]{0,9};", "").replaceAll("<[^>]*>", "");
		String regEx = "<.+?>";
		Pattern p = Pattern.compile(regEx);
		Matcher m = p.matcher(html);
		String s = m.replaceAll("");
		return s;
	}

	/*****
	 * html中的特殊字符，java后台接收会显示转义字符。例如 ’ 会被显示成&#39；因此需要对特殊字符转义。
	 * <p>
	 * 特殊字符转义 </P
	 * 
	 * @param str
	 * @return
	 */
	public static String htmlReplace(String str) {
		str = str.replace("&ldquo;", "“");
		str = str.replace("&rdquo;", "”");
		str = str.replace("&nbsp;", " ");
		str = str.replace("&", "&amp;");
		str = str.replace("&#39;", "'");
		str = str.replace("&rsquo;", "’");
		str = str.replace("&mdash;", "—");
		str = str.replace("&ndash;", "–");
		return str;
	}

	/**
	 * 替换为手机识别的HTML，去掉样式及属性，保留回车。
	 * 
	 * @param html
	 * @return
	 */
	public static String toMobileHtml(String html) {
		if (html == null) {
			return "";
		}
		return html.replaceAll("<([a-z]+?)\\s+?.*?>", "<$1>");
	}


	// 缩略字符串替换Html正则表达式预编译
	private static Pattern p1 = Pattern.compile("<([a-zA-Z]+)[^<>]*>");

	/**
	 * 缩略字符串（适应于与HTML标签的）
	 * 
	 * @param param  目标字符串
	 * @param length 截取长度
	 * @return
	 */
	public static String htmlAbbr(String param, int length) {
		if (param == null) {
			return "";
		}
		StringBuffer result = new StringBuffer();
		int n = 0;
		char temp;
		boolean isCode = false; // 是不是HTML代码
		boolean isHTML = false; // 是不是HTML特殊字符,如&nbsp;
		for (int i = 0; i < param.length(); i++) {
			temp = param.charAt(i);
			if (temp == '<') {
				isCode = true;
			} else if (temp == '&') {
				isHTML = true;
			} else if (temp == '>' && isCode) {
				n = n - 1;
				isCode = false;
			} else if (temp == ';' && isHTML) {
				isHTML = false;
			}
			try {
				if (!isCode && !isHTML) {
					n += String.valueOf(temp).getBytes("GBK").length;
				}
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}

			if (n <= length - 3) {
				result.append(temp);
			} else {
				result.append("...");
				break;
			}
		}
		// 取出截取字符串中的HTML标记
		String tempResult = result.toString().replaceAll("(>)[^<>]*(<?)", "$1$2");
		// 去掉不需要结素标记的HTML标记
		tempResult = tempResult.replaceAll("</?(AREA|BASE|BASEFONT|BODY|BR|COL|COLGROUP|DD|DT|FRAME|HEAD|HR|"
				+ "HTML|IMG|INPUT|ISINDEX|LI|LINK|META|OPTION|P|PARAM|TBODY|TD|TFOOT|TH|THEAD|TR|area|base|"
				+ "basefont|body|br|col|colgroup|dd|dt|frame|head|hr|html|img|input|isindex|li|link|meta|"
				+ "option|p|param|tbody|td|tfoot|th|thead|tr)[^<>]*/?>", "");
		// 去掉成对的HTML标记
		tempResult = tempResult.replaceAll("<([a-zA-Z]+)[^<>]*>(.*?)</\\1>", "$2");
		// 用正则表达式取出标记
		Matcher m = p1.matcher(tempResult);
		List<String> endHTML = new ArrayList<>();
		while (m.find()) {
			endHTML.add(m.group(1));
		}
		// 补全不成对的HTML标记
		for (int i = endHTML.size() - 1; i >= 0; i--) {
			result.append("</");
			result.append(endHTML.get(i));
			result.append(">");
		}
		return result.toString();
	}

	/**
	 * 首字母大写
	 */
	public static String cap(String str) {
		return capitalize(str);
	}

	/**
	 * 首字母小写
	 */
	public static String uncap(String str) {
		return uncapitalize(str);
	}

	/**
	 * 驼峰命名法工具
	 * 
	 * @return camelCase("hello_world") == "helloWorld" capCamelCase("hello_world")
	 *         == "HelloWorld" uncamelCase("helloWorld") = "hello_world"
	 */
	public static String camelCase(String s) {
		if (s == null) {
			return null;
		}
		s = s.toLowerCase();
		StringBuilder sb = new StringBuilder(s.length());
		boolean upperCase = false;
		for (int i = 0; i < s.length(); i++) {
			char c = s.charAt(i);

			if (c == SEPARATOR) {
				upperCase = i != 1; // 不允许第二个字符是大写
			} else if (upperCase) {
				sb.append(Character.toUpperCase(c));
				upperCase = false;
			} else {
				sb.append(c);
			}
		}
		return sb.toString();
	}

	/**
	 * 驼峰命名法工具
	 * 
	 * @return camelCase("hello_world") == "helloWorld" capCamelCase("hello_world")
	 *         == "HelloWorld" uncamelCase("helloWorld") = "hello_world"
	 */
	public static String capCamelCase(String s) {
		if (s == null) {
			return null;
		}
		s = camelCase(s);
		return s.substring(0, 1).toUpperCase() + s.substring(1);
	}

	/**
	 * 驼峰命名法工具
	 * 
	 * @return camelCase("hello_world") == "helloWorld" capCamelCase("hello_world")
	 *         == "HelloWorld" uncamelCase("helloWorld") = "hello_world"
	 */
	public static String uncamelCase(String s) {
		if (s == null) {
			return null;
		}
		StringBuilder sb = new StringBuilder();
		boolean upperCase = false;
		for (int i = 0; i < s.length(); i++) {
			char c = s.charAt(i);
			boolean nextUpperCase = true;
			if (i < (s.length() - 1)) {
				nextUpperCase = Character.isUpperCase(s.charAt(i + 1));
			}
			if ((i > 0) && Character.isUpperCase(c)) {
				if (!upperCase || !nextUpperCase) {
					sb.append(SEPARATOR);
				}
				upperCase = true;
			} else {
				upperCase = false;
			}
			sb.append(Character.toLowerCase(c));
		}
		return sb.toString();
	}

	/**
	 * 转换为JS获取对象值，生成三目运算返回结果
	 * 
	 * @param objectString 对象串 例如：row.user.id
	 *                     返回：!row?'':!row.user?'':!row.user.id?'':row.user.id
	 */
	public static String jsGetVal(String objectString) {
		StringBuilder result = new StringBuilder();
		StringBuilder val = new StringBuilder();
		String[] vals = split(objectString, ".");
		for (int i = 0; i < vals.length; i++) {
			val.append("." + vals[i]);
			result.append("!" + (val.substring(1)) + "?'':");
		}
		result.append(val.substring(1));
		return result.toString();
	}

	/**
	 * 获取随机字符串
	 * 
	 * @param count
	 * @return
	 */
	public static String getRandomStr(int count) {
		char[] codeSeq = { 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R',
				'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9' };
		Random random = new Random();
		StringBuilder s = new StringBuilder();
		for (int i = 0; i < count; i++) {
			String r = String.valueOf(codeSeq[random.nextInt(codeSeq.length)]);
			s.append(r);
		}
		return s.toString();
	}

	/**
	 * 获取随机数字
	 * 
	 * @param count
	 * @return
	 */
	public static String getRandomNum(int count) {
		char[] codeSeq = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9' };
		Random random = new Random();
		StringBuilder s = new StringBuilder();
		for (int i = 0; i < count; i++) {
			String r = String.valueOf(codeSeq[random.nextInt(codeSeq.length)]);
			s.append(r);
		}
		return s.toString();
	}

	/**
	 * 获取树节点名字
	 * 
	 * @param isShowCode 是否显示编码<br>
	 *                   true or 1：显示在左侧：(code)name<br>
	 *                   2：显示在右侧：name(code)<br>
	 *                   false or null：不显示编码：name
	 * @param code       编码
	 * @param name       名称
	 * @return
	 */
	public static String getTreeNodeName(String isShowCode, String code, String name) {
		if ("true".equals(isShowCode) || "1".equals(isShowCode)) {
			return "(" + code + ") " + StringUtil.replace(name, " ", "");
		} else if ("2".equals(isShowCode)) {
			return StringUtil.replace(name, " ", "") + " (" + code + ")";
		} else {
			return StringUtil.replace(name, " ", "");
		}
	}

	/*
	 * 中文转unicode编码
	 */
	public static String gbEncoding(final String gbString) {
		char[] utfBytes = gbString.toCharArray();
		String unicodeBytes = "";
		for (int i = 0; i < utfBytes.length; i++) {
			String hexB = Integer.toHexString(utfBytes[i]);
			if (hexB.length() <= 2) {
				hexB = "00" + hexB;
			}
			unicodeBytes = unicodeBytes + "\\u" + hexB;
		}
		return unicodeBytes;
	}

	/*
	 * unicode编码转中文
	 ***
	 */
	public static String decodeUnicode(final String dataStr) {
		int start = 0;
		int end = 0;
		final StringBuffer buffer = new StringBuffer();
		while (start > -1) {
			end = dataStr.indexOf("\\u", start + 2);
			String charStr = "";
			if (end == -1) {
				charStr = dataStr.substring(start + 2, dataStr.length());
			} else {
				charStr = dataStr.substring(start + 2, end);
			}
			char letter = (char) Integer.parseInt(charStr, 16); // 16进制parse整形字符串。
			buffer.append(new Character(letter).toString());
			start = end;
		}
		return buffer.toString();
	}

	/**
	 * 判断字符串中是否包含中文
	 * 
	 * @param str 待校验字符串
	 * @return 是否为中文
	 * @warn 不能校验是否为中文标点符号
	 */
	public static boolean isContainChinese(String str) {
		Pattern p = Pattern.compile("[\u4e00-\u9fa5]");
		Matcher m = p.matcher(str);
		if (m.find()) {
			return true;
		}
		return false;
	}

	/***
	 * clob类型转成String
	 * 
	 * @param clob
	 * @return
	 */
	public static String clobtoString(Clob clob) {
		int i = 0;
		if (clob != null) {
			InputStream input;
			try {
				input = clob.getAsciiStream();
				int len = (int) clob.length();
				byte by[] = new byte[len];
				while (-1 != (i = input.read(by, 0, by.length))) {
					input.read(by, 0, i);
				}
				return new String(by, "utf-8");
			} catch (SQLException | IOException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	/****
	 * 字符串转成clob
	 * 
	 * @param str
	 * @return
	 */
	public static Clob stringToClob(String str) {
		if (isEmpty(str)) {
			return null;
		}
		Clob clob = null;
		try {
			clob = new SerialClob(str.toCharArray());
		} catch (SerialException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return clob;
	}

	public static boolean isEmpty(Object obj) {
		return StringUtils.isEmpty(obj);
	}

}
