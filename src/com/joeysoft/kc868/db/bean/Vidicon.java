package com.joeysoft.kc868.db.bean;

import java.util.HashMap;
import java.util.Map;

public class Vidicon {
	
	private int deviceId;
	private int vidiconId;
	private String vidiconUrl;
	private String vidiconPort;
	private String vidiconUser;
	private String vidiconPwd;
	
	private int roomId;
	
	public Vidicon(){
		
	}
	
	public Vidicon(int vidiconId, int deviceId, String vidiconUrl,
			String vidiconPort, String vidiconUser, String vidiconPwd){
		this.vidiconId = vidiconId;
		this.deviceId = deviceId;
		this.vidiconUrl = vidiconUrl;
		this.vidiconPort = vidiconPort;
		this.vidiconUser = vidiconUser;
		this.vidiconPwd = vidiconPwd;
	}
	
	public Map<String, Object> fillMap(){
		Map<String, Object> htParam = new HashMap<String, Object>();
		htParam.put("DEVICE_ID", this.deviceId);
		htParam.put("VIDICON_URL", this.vidiconUrl);
		htParam.put("VIDICON_PORT", this.vidiconPort);
		htParam.put("VIDICON_USER", this.vidiconUser);
		htParam.put("VIDICON_PWD", this.vidiconPwd);
		
		return htParam;
	}
	
	public int getDeviceId() {
		return deviceId;
	}
	public void setDeviceId(int deviceId) {
		this.deviceId = deviceId;
	}
	public int getVidiconId() {
		return vidiconId;
	}
	public void setVidiconId(int vidiconId) {
		this.vidiconId = vidiconId;
	}
	public String getVidiconUrl() {
		return vidiconUrl;
	}
	public void setVidiconUrl(String vidiconUrl) {
		this.vidiconUrl = vidiconUrl;
	}
	public String getVidiconPort() {
		return vidiconPort;
	}
	public void setVidiconPort(String vidiconPort) {
		this.vidiconPort = vidiconPort;
	}
	public String getVidiconUser() {
		return vidiconUser;
	}
	public void setVidiconUser(String vidiconUser) {
		this.vidiconUser = vidiconUser;
	}
	public String getVidiconPwd() {
		return vidiconPwd;
	}
	public void setVidiconPwd(String vidiconPwd) {
		this.vidiconPwd = vidiconPwd;
	}

	public int getRoomId() {
		return roomId;
	}

	public void setRoomId(int roomId) {
		this.roomId = roomId;
	}
	
}
