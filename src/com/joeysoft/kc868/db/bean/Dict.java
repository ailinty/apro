package com.joeysoft.kc868.db.bean;

import java.util.HashMap;
import java.util.Map;

public class Dict {
	private int dictId;
	
	private String dictCode;
	
	private String dictName;
	
	private String dictValue;
	
	public Dict(){
		
	}
	
	public Dict(int dictId, String dictCode, String dictName, String dictValue){
		this.dictId = dictId;
		this.dictCode = dictCode;
		this.dictName = dictName;
		this.dictValue = dictValue;
	}
	
	public Map<String, Object> fillMap(){
		Map<String, Object> htParam = new HashMap<String, Object>();
		htParam.put("DICT_CODE", this.dictCode);
		htParam.put("DICT_NAME", this.dictName);
		htParam.put("DICT_VALUE", this.dictValue);
		
		return htParam;
	}

	public int getDictId() {
		return dictId;
	}

	public void setDictId(int dictId) {
		this.dictId = dictId;
	}

	public String getDictCode() {
		return dictCode;
	}

	public void setDictCode(String dictCode) {
		this.dictCode = dictCode;
	}

	public String getDictName() {
		return dictName;
	}

	public void setDictName(String dictName) {
		this.dictName = dictName;
	}

	public String getDictValue() {
		return dictValue;
	}

	public void setDictValue(String dictValue) {
		this.dictValue = dictValue;
	}
	
}
