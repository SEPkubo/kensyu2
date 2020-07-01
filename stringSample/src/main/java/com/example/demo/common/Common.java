package com.example.demo.common;

import java.io.UnsupportedEncodingException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

	public class Common {

		public static String getErr(String name,String address,String tel) {

			String ERRMSG_NAME01 = "名前は全角20文字以内で入力してください";
			String ERRMSG_NAME02 = "名前は必須項目です";
			String ERRMSG_ADDRESS01 = "住所は全角40文字以内で入力してください";
			String ERRMSG_ADDRESS02 = "住所は必須項目です";
			String ERRMSG_TEL01 = "電話番号は「000-0000-0000」の形式で入力してください";

			String returnVal = "";
			Pattern pattern = Pattern.compile("^\\d{3}-\\d{4}-\\d{4}$");		// 電話番号の型判定
			Matcher matcher = pattern.matcher(tel);

			try {
				if(name.getBytes("Shift-JIS").length > 40) {
					returnVal = ERRMSG_NAME01;
				} else if (name.getBytes("Shift-JIS").length == 0) {
					returnVal = ERRMSG_NAME02;
				} else if (address.getBytes("Shift-JIS").length > 80) {
					returnVal = ERRMSG_ADDRESS01;
				} else if (address.getBytes("Shift-JIS").length == 0) {
					returnVal = ERRMSG_ADDRESS02;
				} else if (tel.getBytes("Shift-JIS").length > 0 && matcher.find() == false) {
					returnVal = ERRMSG_TEL01;
				}



			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			return returnVal;

		}
	}

