package com.act.web.test;

import java.util.List;

import com.act.web.util.SqlUtil;

public class Test {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
			List<String> list = SqlUtil.getDateList(5);
			List<String> list1 = SqlUtil.getExistTable(list,"monsys_eu_device_stat_66666");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//SqlUtil.getDateList(5);
	}

}
