package com.joeysoft.kc868.db.bean;

import java.util.HashMap;
import java.util.Map;

public class TsfSensor {
	private String sensorId;
	private int transferId;
	private String sensorName;
	private String resType;
	private int dataCode;
	private String sensorStudy;
	
	private String freqType;
	
	private String codeType;
	
	private int addrCode;
	
	public TsfSensor(){
		
	}
			
	public TsfSensor(String sensorId, int transferId, String sensorName,
			String resType, int dataCode, String sensorStudy){
		this.sensorId = sensorId;
		this.transferId = transferId;
		this.sensorName = sensorName;
		this.resType = resType;
		this.dataCode = dataCode;
		this.sensorStudy = sensorStudy;
	}
	
	public Map<String, Object> fillMap(){
		Map<String, Object> htParam = new HashMap<String, Object>();
		htParam.put("SENSOR_ID", sensorId);
		htParam.put("TRANSFER_ID", this.transferId);
		htParam.put("SENSOR_NAME", this.sensorName);
		htParam.put("RES_TYPE", this.resType);
		htParam.put("DATA_CODE", this.dataCode);
		htParam.put("SENSOR_STUDY", this.sensorStudy);
		
		return htParam;
	}
	
	public String getSensorId() {
		return sensorId;
	}
	public void setSensorId(String sensorId) {
		this.sensorId = sensorId;
	}
	public int getTransferId() {
		return transferId;
	}
	public void setTransferId(int transferId) {
		this.transferId = transferId;
	}
	public String getSensorName() {
		return sensorName;
	}
	public void setSensorName(String sensorName) {
		this.sensorName = sensorName;
	}
	public String getResType() {
		return resType;
	}
	public void setResType(String resType) {
		this.resType = resType;
	}
	public int getDataCode() {
		return dataCode;
	}
	public void setDataCode(int dataCode) {
		this.dataCode = dataCode;
	}
	public String getSensorStudy() {
		return sensorStudy;
	}
	public void setSensorStudy(String sensorStudy) {
		this.sensorStudy = sensorStudy;
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

	public int getAddrCode() {
		return addrCode;
	}

	public void setAddrCode(int addrCode) {
		this.addrCode = addrCode;
	}
}
