package com.joeysoft.kc868.db.bean;

import java.util.HashMap;
import java.util.Map;

public class SceneBind {
	private int sceneBindId = -1;
	private int sceneId = -1;
	private String sourceTable;
	private String sourceId;
	private String sourceCmd;
	private String sourceText;
	
	private String sceneName;
	
	public SceneBind(){
		
	}
	
	public SceneBind(String sourceTable, String sourceId, String sourceCmd, String sourceText){
		this.sourceTable = sourceTable;
		this.sourceId = sourceId;
		this.sourceCmd = sourceCmd;
		this.sourceText = sourceText;
	}
	
	public SceneBind(int sceneBindId, int sceneId, String sourceTable, String sourceId, String sourceCmd, String sourceText){
		this.sceneBindId = sceneBindId;
		this.sceneId = sceneId;
		this.sourceTable = sourceTable;
		this.sourceId = sourceId;
		this.sourceCmd = sourceCmd;
		this.sourceText = sourceText;
	}
	
	public Map<String, Object> fillMap(){
		Map<String, Object> htParam = new HashMap<String, Object>();
		htParam.put("SCENE_ID", this.sceneId);
		htParam.put("SOURCE_TABLE", this.sourceTable);
		htParam.put("SOURCE_ID", this.sourceId);
		htParam.put("SOURCE_CMD", this.sourceCmd);
		htParam.put("SOURCE_TEXT", this.sourceText);
		return htParam;
	}
	
	public int getSceneBindId() {
		return sceneBindId;
	}

	public void setSceneBindId(int sceneBindId) {
		this.sceneBindId = sceneBindId;
	}

	public int getSceneId() {
		return sceneId;
	}

	public void setSceneId(int sceneId) {
		this.sceneId = sceneId;
	}

	public String getSourceTable() {
		return sourceTable;
	}

	public void setSourceTable(String sourceTable) {
		this.sourceTable = sourceTable;
	}

	public String getSourceId() {
		return sourceId;
	}

	public void setSourceId(String sourceId) {
		this.sourceId = sourceId;
	}

	public String getSceneName() {
		return sceneName;
	}

	public void setSceneName(String sceneName) {
		this.sceneName = sceneName;
	}

	public String getSourceCmd() {
		return sourceCmd;
	}

	public void setSourceCmd(String sourceCmd) {
		this.sourceCmd = sourceCmd;
	}

	public String getSourceText() {
		return sourceText;
	}

	public void setSourceText(String sourceText) {
		this.sourceText = sourceText;
	}
	
	
}
