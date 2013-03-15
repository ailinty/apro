package com.joeysoft.kc868.db.bean;

import java.util.HashMap;
import java.util.Map;

public class SetRestart {
	private int id;
	private String reWeek;
	private String reHour;
	private String reMinute;
	private String reSecond;
	
	public SetRestart(){
		
	}
			
	public SetRestart(int id, String reWeek, String reHour, 
			String reMinute, String reSecond){
		this.id = id;
		this.reWeek = reWeek;
		this.reHour = reHour;
		this.reMinute = reMinute;
		this.reSecond = reSecond;
	}

	public Map<String, Object> fillMap(){
		Map<String, Object> htParam = new HashMap<String, Object>();
		htParam.put("RE_WEEK", this.reWeek);
		htParam.put("RE_HOUR", this.reHour);
		htParam.put("RE_MINUTE", this.reMinute);
		htParam.put("RE_SECOND", this.reSecond);
		
		return htParam;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getReWeek() {
		return reWeek;
	}

	public void setReWeek(String reWeek) {
		this.reWeek = reWeek;
	}

	public String getReHour() {
		return reHour;
	}

	public void setReHour(String reHour) {
		this.reHour = reHour;
	}

	public String getReMinute() {
		return reMinute;
	}

	public void setReMinute(String reMinute) {
		this.reMinute = reMinute;
	}

	public String getReSecond() {
		return reSecond;
	}

	public void setReSecond(String reSecond) {
		this.reSecond = reSecond;
	}
}
