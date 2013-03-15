package com.joeysoft.kc868.db.bean;

import java.util.HashMap;
import java.util.Map;

/**
 * 喇叭
 * @author JOEY
 *
 */
public class Bugle {
	private int bugleId;
	
	private String bugleName;
	
	private String bugleOnName;
	
	private String bugleOffName;
	
	private String bugleStatus;
	
	public Bugle(String bugleName, String bugleOnName, String bugleOffName, String bugleStatus){
		this.bugleName = bugleName;
		this.bugleOnName = bugleOnName;
		this.bugleOffName = bugleOffName;
		this.bugleStatus = bugleStatus;
	}
	
	public Map<String, Object> fillMap(){
		Map<String, Object> htParam = new HashMap<String, Object>();
		htParam.put("BUGLE_ID", null);
		htParam.put("BUGLE_NAME", this.bugleName);
		htParam.put("BUGLE_ON_NAME", this.bugleOnName);
		htParam.put("BUGLE_OFF_NAME", this.bugleOffName);
		htParam.put("BUGLE_STATUS", this.bugleStatus);
		
		return htParam;
	}
	
	public int getBugleId() {
		return bugleId;
	}
	public void setBugleId(int bugleId) {
		this.bugleId = bugleId;
	}
	public String getBugleName() {
		return bugleName;
	}
	public void setBugleName(String bugleName) {
		this.bugleName = bugleName;
	}
	public String getBugleOnName() {
		return bugleOnName;
	}
	public void setBugleOnName(String bugleOnName) {
		this.bugleOnName = bugleOnName;
	}
	public String getBugleOffName() {
		return bugleOffName;
	}
	public void setBugleOffName(String bugleOffName) {
		this.bugleOffName = bugleOffName;
	}
	public String getBugleStatus() {
		return bugleStatus;
	}
	public void setBugleStatus(String bugleStatus) {
		this.bugleStatus = bugleStatus;
	}
}
