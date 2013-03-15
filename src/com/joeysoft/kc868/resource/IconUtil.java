package com.joeysoft.kc868.resource;

public class IconUtil {
	
	public static String getImageOn(String image){
		int pos = image.indexOf(".");
		if(pos > 0){
			return image.substring(0, pos) + "_on" + image.substring(pos);
		}else{
			return image;
		}
		
	}
	
	public static String getImageUp(String image){
		int pos = image.indexOf(".");
		if(pos > 0){
			return image.substring(0, pos) + "_up" + image.substring(pos);
		}else{
			return image;
		}
	}
	
	public static String getImage32(String image){
		int pos = image.indexOf(".");
		if(pos > 0){
			return image.substring(0, pos) + "_32" + image.substring(pos);
		}else{
			return image;
		}
	}
}
