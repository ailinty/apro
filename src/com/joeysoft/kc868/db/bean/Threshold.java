package com.joeysoft.kc868.db.bean;

import java.util.HashMap;
import java.util.Map;

public class Threshold {
	private int thresholdId;
	
	private String thresholdType;
	private int threshold;
	
	public Threshold(){
		
	}
	
	public Threshold(int thresholdId, String thresholdType, int threshold){
		this.thresholdId = thresholdId;
		this.thresholdType = thresholdType;
		this.threshold = threshold;
	}

	public Map<String, Object> fillMap(){
		Map<String, Object> htParam = new HashMap<String, Object>();
		htParam.put("THRESHOLD_TYPE", this.thresholdType);
		htParam.put("THRESHOLD", this.threshold);
		
		return htParam;
	}

	public int getThresholdId() {
		return thresholdId;
	}

	public void setThresholdId(int thresholdId) {
		this.thresholdId = thresholdId;
	}

	public String getThresholdType() {
		return thresholdType;
	}

	public void setThresholdType(String thresholdType) {
		this.thresholdType = thresholdType;
	}

	public int getThreshold() {
		return threshold;
	}

	public void setThreshold(int threshold) {
		this.threshold = threshold;
	}
	
}
