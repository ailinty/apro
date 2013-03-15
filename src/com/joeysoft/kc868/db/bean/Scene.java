package com.joeysoft.kc868.db.bean;

import java.util.HashMap;
import java.util.Map;

public class Scene {
	private int sceneId;
	private String sceneName;
	private String sceneIcon;
	private String sceneStatus;
	
	private int sceneSecond;
	
	// 有设防
	private String sceneSWOK;
	
	public Scene(){
		
	}
	
	public Scene(int sceneId, String sceneName, String sceneIcon, String sceneStatus, int sceneSecond, String sceneSWOK){
		this.sceneId = sceneId;
		this.sceneName = sceneName;
		this.sceneIcon = sceneIcon;
		this.sceneStatus = sceneStatus;
		this.sceneSecond = sceneSecond;
		this.sceneSWOK = sceneSWOK;
	}

	public Map<String, Object> fillMap(){
		Map<String, Object> htParam = new HashMap<String, Object>();
		htParam.put("SCENE_ID", this.sceneId);
		htParam.put("SCENE_NAME", this.sceneName);
		htParam.put("SCENE_STATUS", this.sceneStatus);
		htParam.put("SCENE_ICON", this.sceneIcon);
		htParam.put("SCENE_SECOND", this.sceneSecond);
		htParam.put("SCENE_SWOK", this.sceneSWOK);
		return htParam;
	}
	
	public int getSceneId() {
		return sceneId;
	}
	public void setSceneId(int sceneId) {
		this.sceneId = sceneId;
	}
	public String getSceneName() {
		return sceneName;
	}
	public void setSceneName(String sceneName) {
		this.sceneName = sceneName;
	}
	public String getSceneIcon() {
		return sceneIcon;
	}
	public void setSceneIcon(String sceneIcon) {
		this.sceneIcon = sceneIcon;
	}

	public int getSceneSecond() {
		return sceneSecond;
	}

	public void setSceneSecond(int sceneSecond) {
		this.sceneSecond = sceneSecond;
	}

	public String getSceneStatus() {
		return sceneStatus;
	}

	public void setSceneStatus(String sceneStatus) {
		this.sceneStatus = sceneStatus;
	}

	public String getSceneSWOK() {
		return sceneSWOK;
	}

	public void setSceneSWOK(String sceneSWOK) {
		this.sceneSWOK = sceneSWOK;
	}
}
