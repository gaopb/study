package com.eenet.androidbase.utils;

import java.text.DecimalFormat;

/**
 * 字符串工具类
 *
 * Created by yao on 16/6/24.
 */
public class StringUtils {

	/**
	 * 浮点型模式转换，当为整数时不显示小数点后数字
	 * @param f
	 * @return
	 */
	public static String formatFloat(float f){
		String _rst = "0";
		if(f%((int)f)>0){
			_rst = ""+f;
		}else{
			_rst = ""+(int)f;
		}
		return _rst;
	}

	/**
	 * 文件大小单位转换
	 * @param size
	 * @return
	 */
	public static String sizeChange(long size){
		DecimalFormat df = new DecimalFormat("#");
		String changeSize = "";
		if (size < 1024) {
			changeSize = df.format((double) size) + "B";
		} else if (size < 1048576) {
			changeSize = df.format((double) size / 1024) + "K";
		} else if (size < 1073741824) {
			changeSize = df.format((double) size / 1048576) + "M";
		} else {
			changeSize = df.format((double) size / 1073741824) + "G";
		}
		return changeSize;
	}

	/**
	 *<b>截取指定字节长度的字符串，不能返回半个汉字</b>
	 * 例如：
	 * 如果网页最多能显示17个汉字，那么 length 则为 34
	 * StringTool.getSubString(str, 34);
	 *
	 * @param str
	 * @param length
	 * @return
	 */
	public static String cutString(String str, int length) {
		int count = 0;
		int offset = 0;
		char[] c = str.toCharArray();
		for (int i = 0; i < c.length; i++) {
			if (c[i] > 256) {
				offset = 2;
				count += 2;
			} else {
				offset = 1;
				count++;
			}
			if (count == length) {
				return str.substring(0, i + 1);
			}
			if ((count == length + 1 && offset == 2)) {
				return str.substring(0, i);
			}
		}
		return str;
	}


}
