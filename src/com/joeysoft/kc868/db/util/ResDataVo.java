package com.joeysoft.kc868.db.util;

/**
 * 电阻、数据码
 * @author JOEY
 *
 */
public class ResDataVo {
	private String resType;
	private int dataCode;
	
	public ResDataVo(String resType, int dataCode){
		this.resType = resType;
		this.dataCode = dataCode;
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
}
