package com.joeysoft.kc868.db.bean;

import java.util.HashMap;
import java.util.Map;

/**
 * 按键类
 * @author JOEY
 *
 */
public class Key {
	private int keyId;
	
	private String keyName;
	
	private String keyIcon;
	
	private int keyHeight;
	
	private int keyWidth;
	
	// 固定按键编号，以此来表示
	private String keyCode;
	
	public Key(){
		
	}
	
	public Key(int keyId, String keyName, String keyIcon, int keyHeight, int keyWidth){
		this.keyId = keyId;
		this.keyName = keyName;
		this.keyIcon = keyIcon;
		this.keyHeight = keyHeight;
		this.keyWidth = keyWidth;
	}
	
	public Map<String, Object> fillMap(){
		Map<String, Object> htParam = new HashMap<String, Object>();
		htParam.put("KEY_NAME", this.keyName);
		htParam.put("KEY_ICON", this.keyIcon);
		htParam.put("KEY_HEIGHT", this.keyHeight);
		htParam.put("KEY_WIDTH", this.keyWidth);
		
		return htParam;
	}

	public int getKeyId() {
		return keyId;
	}

	public void setKeyId(int keyId) {
		this.keyId = keyId;
	}

	public String getKeyName() {
		return keyName;
	}

	public void setKeyName(String keyName) {
		this.keyName = keyName;
	}

	public String getKeyIcon() {
		return keyIcon;
	}

	public void setKeyIcon(String keyIcon) {
		this.keyIcon = keyIcon;
	}

	public int getKeyHeight() {
		return keyHeight;
	}

	public void setKeyHeight(int keyHeight) {
		this.keyHeight = keyHeight;
	}

	public int getKeyWidth() {
		return keyWidth;
	}

	public void setKeyWidth(int keyWidth) {
		this.keyWidth = keyWidth;
	}

	public String getKeyCode() {
		return keyCode;
	}

	public void setKeyCode(String keyCode) {
		this.keyCode = keyCode;
	}
	
}
