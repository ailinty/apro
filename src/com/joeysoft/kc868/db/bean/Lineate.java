package com.joeysoft.kc868.db.bean;

import java.util.HashMap;
import java.util.Map;

/**
 * 有线电
 * @author JOEY
 *
 */
public class Lineate {
	private int lineateId;
	
	private int roomId;
	
	private String lineateName;
	
	private String lineateType;
	
	private String lineateUD;
	
	private String lineateGL;
	
	private String lineateV;
	
	private String roomName;
	private int floor;
	private String floorName;
	
	public Lineate(){
		
	}
	
	public Lineate(int lineateId, int roomId, String lineateName, String lineateType, String lineateUD, String lineateGL, String lineateV){
		this.lineateId = lineateId;
		this.roomId = roomId;
		this.lineateName = lineateName;
		this.lineateType = lineateType;
		this.lineateUD = lineateUD;
		this.lineateGL = lineateGL;
		this.lineateV = lineateV;
	}

	public Map<String, Object> fillMap(){
		Map<String, Object> htParam = new HashMap<String, Object>();
		htParam.put("LINEATE_ID", lineateId);
		htParam.put("ROOM_ID", roomId);
		htParam.put("LINEATE_NAME", this.lineateName);
		htParam.put("LINEATE_TYPE", this.lineateType);
		htParam.put("LINEATE_UD", this.lineateUD);
		htParam.put("LINEATE_GL", this.lineateGL);
		htParam.put("LINEATE_V", this.lineateV);
		
		return htParam;
	}
	
	public int getLineateId() {
		return lineateId;
	}

	public void setLineateId(int lineateId) {
		this.lineateId = lineateId;
	}

	public String getLineateName() {
		return lineateName;
	}

	public void setLineateName(String lineateName) {
		this.lineateName = lineateName;
	}

	public String getLineateType() {
		return lineateType;
	}

	public void setLineateType(String lineateType) {
		this.lineateType = lineateType;
	}

	public String getLineateUD() {
		return lineateUD;
	}

	public void setLineateUD(String lineateUD) {
		this.lineateUD = lineateUD;
	}

	public String getLineateGL() {
		return lineateGL;
	}

	public void setLineateGL(String lineateGL) {
		this.lineateGL = lineateGL;
	}

	public String getLineateV() {
		return lineateV;
	}

	public void setLineateV(String lineateV) {
		this.lineateV = lineateV;
	}

	public int getRoomId() {
		return roomId;
	}

	public void setRoomId(int roomId) {
		this.roomId = roomId;
	}

	public String getRoomName() {
		return roomName;
	}

	public void setRoomName(String roomName) {
		this.roomName = roomName;
	}

	public int getFloor() {
		return floor;
	}

	public void setFloor(int floor) {
		this.floor = floor;
	}

	public String getFloorName() {
		return floorName;
	}

	public void setFloorName(String floorName) {
		this.floorName = floorName;
	}
	
	
}
