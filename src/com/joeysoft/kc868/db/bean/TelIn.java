package com.joeysoft.kc868.db.bean;

import java.util.HashMap;
import java.util.Map;

public class TelIn {
	private String telId;
	private String telPhone;
	private String telName;
	
	private String countryCode;
	
	private Integer telIndex;
	
	public TelIn(){
		
	}
	
	public TelIn(String telId, String telPhone, String telName, String countryCode) {
		super();
		this.telId = telId;
		this.telPhone = telPhone;
		this.telName = telName;
		this.countryCode = countryCode;
	}
	
	public TelIn(String telId, String telPhone, String telName, String countryCode, Integer telIndex) {
		super();
		this.telId = telId;
		this.telPhone = telPhone;
		this.telName = telName;
		this.countryCode = countryCode;
		this.telIndex = telIndex;
	}
	
	public Map<String, Object> fillMap(){
		Map<String, Object> htParam = new HashMap<String, Object>();
		htParam.put("TEL_ID", this.telId);
		htParam.put("TEL_PHONE", this.telPhone);
		htParam.put("TEL_NAME", this.telName);
		htParam.put("COUNTRY_CODE", this.countryCode);
		htParam.put("TEL_INDEX", this.telIndex);
		
		return htParam;
	}

	public String getTelId() {
		return telId;
	}

	public void setTelId(String telId) {
		this.telId = telId;
	}

	public String getTelPhone() {
		return telPhone;
	}

	public void setTelPhone(String telPhone) {
		this.telPhone = telPhone;
	}

	public String getTelName() {
		return telName;
	}

	public void setTelName(String telName) {
		this.telName = telName;
	}

	public String getCountryCode() {
		return countryCode;
	}

	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}

	public Integer getTelIndex() {
		return telIndex;
	}

	public void setTelIndex(Integer telIndex) {
		this.telIndex = telIndex;
	}
	
	
}
