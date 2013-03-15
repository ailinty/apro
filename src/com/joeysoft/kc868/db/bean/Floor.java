package com.joeysoft.kc868.db.bean;

import java.util.HashMap;
import java.util.Map;

public class Floor {
	private Integer floor;
	
	private String floorName;
	
	public Floor(){
		
	}
	
	public Floor(String floorName){
		this.floorName = floorName;
	}
	
	public Floor(Integer floor, String floorName){
		this.floor = floor;
		this.floorName = floorName;
	}

	public Map<String, Object> fillMap(){
		Map<String, Object> htParam = new HashMap<String, Object>();
		htParam.put("FLOOR", this.floor);
		htParam.put("FLOOR_NAME", this.floorName);
		
		return htParam;
	}
	
	public Integer getFloor() {
		return floor;
	}

	public void setFloor(Integer floor) {
		this.floor = floor;
	}

	public String getFloorName() {
		return floorName;
	}

	public void setFloorName(String floorName) {
		this.floorName = floorName;
	}
	
}
