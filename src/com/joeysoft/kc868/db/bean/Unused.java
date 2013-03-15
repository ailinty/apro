package com.joeysoft.kc868.db.bean;

import java.util.HashMap;
import java.util.Map;

public class Unused {
	private String tableName;
	private String unused = "";
	private int maxUnused;
	
	// 下一次可用编号
	private int nextUnused;
	// 是否是第一条记录
	private boolean isNew = false;
	
	public Unused(){
	}
	
	public Unused(String tableName, String unused, int maxUnused){
		this.tableName = tableName;
		this.unused = unused;
		this.maxUnused = maxUnused;
	}
	
	public Map<String, Object> fillMap(){
		Map<String, Object> htParam = new HashMap<String, Object>();
		htParam.put("TABLE_NAME", this.tableName);
		htParam.put("UNUSED", this.unused);
		htParam.put("MAXUNUSED", this.maxUnused);
		
		return htParam;
	}
	
	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public String getUnused() {
		return unused;
	}
	public void setUnused(String unused) {
		this.unused = unused;
	}
	public int getMaxUnused() {
		return maxUnused;
	}
	public void setMaxUnused(int maxUnused) {
		this.maxUnused = maxUnused;
	}

	public int getNextUnused() {
		return nextUnused;
	}

	public void setNextUnused(int nextUnused) {
		this.nextUnused = nextUnused;
	}

	public boolean isNew() {
		return isNew;
	}

	public void setNew(boolean isNew) {
		this.isNew = isNew;
	}
}
