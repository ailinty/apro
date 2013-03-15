package com.joeysoft.kc868.db.bean;

import java.util.HashMap;
import java.util.Map;

public class Alarm {
	private int alarmId;
	private int alarmWeek;
	private String alarmHour;
	private String alarmMinute;
	private String alarmSecond;
	private String alarmStatus;
	
	public Alarm(){
		
	}
			
	public Alarm(int alarmId, int alarmWeek, String alarmHour, 
			String alarmMinute, String alarmSecond, String alarmStatus){
		this.alarmId = alarmId;
		this.alarmWeek = alarmWeek;
		this.alarmHour = alarmHour;
		this.alarmMinute = alarmMinute;
		this.alarmSecond = alarmSecond;
		this.alarmStatus = alarmStatus;
	}

	public Map<String, Object> fillMap(){
		Map<String, Object> htParam = new HashMap<String, Object>();
		htParam.put("ALARM_ID", this.alarmId);
		htParam.put("ALARM_WEEK", this.alarmWeek);
		htParam.put("ALARM_HOUR", this.alarmHour);
		htParam.put("ALARM_MINUTE", this.alarmMinute);
		htParam.put("ALARM_SECOND", this.alarmSecond);
		htParam.put("ALARM_STATUS", this.alarmStatus);
		
		return htParam;
	}
	
	public int getAlarmId() {
		return alarmId;
	}

	public void setAlarmId(int alarmId) {
		this.alarmId = alarmId;
	}

	public int getAlarmWeek() {
		return alarmWeek;
	}

	public void setAlarmWeek(int alarmWeek) {
		this.alarmWeek = alarmWeek;
	}

	public String getAlarmHour() {
		return alarmHour;
	}

	public void setAlarmHour(String alarmHour) {
		this.alarmHour = alarmHour;
	}

	public String getAlarmMinute() {
		return alarmMinute;
	}

	public void setAlarmMinute(String alarmMinute) {
		this.alarmMinute = alarmMinute;
	}

	public String getAlarmSecond() {
		return alarmSecond;
	}

	public void setAlarmSecond(String alarmSecond) {
		this.alarmSecond = alarmSecond;
	}

	public String getAlarmStatus() {
		return alarmStatus;
	}

	public void setAlarmStatus(String alarmStatus) {
		this.alarmStatus = alarmStatus;
	}
}
