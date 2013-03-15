package com.joeysoft.kc868.db.bean;

import java.util.HashMap;
import java.util.Map;

public class SmsOut {
	private String smsId;
	private String smsContent;
	private String smsName;
	private Integer smsIndex;
	
	public SmsOut(){
		
	}
	
	public SmsOut(String smsId, String smsContent, String smsName) {
		super();
		this.smsId = smsId;
		this.smsContent = smsContent;
		this.smsName = smsName;
	}
	
	public SmsOut(String smsId, String smsContent, String smsName, Integer smsIndex) {
		super();
		this.smsId = smsId;
		this.smsContent = smsContent;
		this.smsName = smsName;
		this.smsIndex = smsIndex;
	}
	
	public Map<String, Object> fillMap(){
		Map<String, Object> htParam = new HashMap<String, Object>();
		htParam.put("SMS_ID", this.smsId);
		htParam.put("SMS_CONTENT", this.smsContent);
		htParam.put("SMS_NAME", this.smsName);
		htParam.put("SMS_INDEX", this.smsIndex);
		
		return htParam;
	}
	
	public String getSmsId() {
		return smsId;
	}
	public void setSmsId(String smsId) {
		this.smsId = smsId;
	}
	public String getSmsContent() {
		return smsContent;
	}
	public void setSmsContent(String smsContent) {
		this.smsContent = smsContent;
	}
	public String getSmsName() {
		return smsName;
	}
	public void setSmsName(String smsName) {
		this.smsName = smsName;
	}

	public Integer getSmsIndex() {
		return smsIndex;
	}

	public void setSmsIndex(Integer smsIndex) {
		this.smsIndex = smsIndex;
	}
	
}
