package com.joeysoft.kc868.db.bean;

import java.util.HashMap;
import java.util.Map;

public class Device {
	private int roomId;
	
	private int deviceId;
	
	private String deviceName;
	
	private String deviceType;
	
	private String deviceIcon;
	
	private String deviceRmk;
	
	private int floor;
	private String floorName;
	
	private String roomName;
	
	
	// 灯光、窗帘、幕布中和
	private String freqType;
	
	private String codeType;
	
	private String resType;
	
	private int addrCode;
	
	private int openDataCode;
	private int closeDataCode;	
	
	
	private int vidiconId;
	private String vidiconUrl = "";
	private String vidiconPort = "";
	private String vidiconUser = "";
	private String vidiconPwd = "";

	public Device(){
		
	}
			
	public Device(int deviceId, int roomId, String deviceName, String deviceType, String deviceIcon, String deviceRmk){
		this.deviceId = deviceId;
		this.roomId = roomId;
		this.deviceName = deviceName;
		this.deviceType = deviceType;
		this.deviceIcon = deviceIcon;
		this.deviceRmk = deviceRmk;
	}
	
	public Map<String, Object> fillMap(){
		Map<String, Object> htParam = new HashMap<String, Object>();
		htParam.put("DEVICE_ID", this.deviceId);
		htParam.put("ROOM_ID", this.roomId);
		htParam.put("DEVICE_NAME", this.deviceName);
		htParam.put("DEVICE_TYPE", this.deviceType);
		htParam.put("DEVICE_ICON", this.deviceIcon);
		htParam.put("DEVICE_RMK", this.deviceRmk);
		
		return htParam;
	}
	
	public int getRoomId() {
		return roomId;
	}

	public void setRoomId(int roomId) {
		this.roomId = roomId;
	}

	public int getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(int deviceId) {
		this.deviceId = deviceId;
	}

	public String getDeviceName() {
		return deviceName;
	}

	public void setDeviceName(String deviceName) {
		this.deviceName = deviceName;
	}

	public String getDeviceType() {
		return deviceType;
	}

	public void setDeviceType(String deviceType) {
		this.deviceType = deviceType;
	}

	public String getDeviceRmk() {
		return deviceRmk;
	}

	public void setDeviceRmk(String deviceRmk) {
		this.deviceRmk = deviceRmk;
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

	public String getDeviceIcon() {
		return deviceIcon;
	}

	public void setDeviceIcon(String deviceIcon) {
		this.deviceIcon = deviceIcon;
	}

	public String getFreqType() {
		return freqType;
	}

	public void setFreqType(String freqType) {
		this.freqType = freqType;
	}

	public String getCodeType() {
		return codeType;
	}

	public void setCodeType(String codeType) {
		this.codeType = codeType;
	}

	public String getResType() {
		return resType;
	}

	public void setResType(String resType) {
		this.resType = resType;
	}

	public int getAddrCode() {
		return addrCode;
	}

	public void setAddrCode(int addrCode) {
		this.addrCode = addrCode;
	}

	public int getOpenDataCode() {
		return openDataCode;
	}

	public void setOpenDataCode(int openDataCode) {
		this.openDataCode = openDataCode;
	}

	public int getCloseDataCode() {
		return closeDataCode;
	}

	public void setCloseDataCode(int closeDataCode) {
		this.closeDataCode = closeDataCode;
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

	public int getVidiconId() {
		return vidiconId;
	}

	public void setVidiconId(int vidiconId) {
		this.vidiconId = vidiconId;
	}
}
