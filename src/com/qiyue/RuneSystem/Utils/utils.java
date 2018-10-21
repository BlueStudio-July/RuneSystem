package com.qiyue.RuneSystem.Utils;


public class utils {

	public static boolean isNumber(String s){
		try{
			int a = Integer.parseInt(s);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
		
		
		
	}
}
