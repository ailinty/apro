package com.joeysoft.kc868.db.util;

/**
 * 固定按键帮助类
 * @author JOEY
 *
 */
public class KeyCodeUtil {
	
	/**
	 * 按设备类型返回固定按键编号keyCode
	 * @param deviceType
	 * @return
	 */
	public static String[] getKeyCode(String deviceType){
		if("DD".equals(deviceType)){
			return new String[]{"DD_ON", "DD_OFF"};
		}
		return null;
	}
}
