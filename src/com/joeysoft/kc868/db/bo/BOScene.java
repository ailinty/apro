package com.joeysoft.kc868.db.bo;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import com.joeysoft.kc868.SystemConfig;
import com.joeysoft.kc868.db.DBConnection;
import com.joeysoft.kc868.db.SQLUtil;
import com.joeysoft.kc868.db.bean.Scene;
import com.joeysoft.kc868.db.bean.SceneAction;
import com.joeysoft.kc868.db.bean.Unused;
import com.joeysoft.kc868.db.util.UnusedUtil;

public class BOScene {
	
	private BOUnused boUnused = new BOUnused();
	private BOSceneAction boSceneAction = new BOSceneAction();
	
	public List<Scene> getList(){
		List<Scene> list = new ArrayList<Scene>();
		try {
			String sql = "SELECT * FROM SCENE ORDER BY SCENE_ID";
			List<Map> lt = (List<Map>)SQLUtil.selectSQLInList(DBConnection.getConnection(), sql);
			for(Map mp : lt){
				Scene scene = new Scene(SQLUtil.returnInt(mp, "SCENE_ID"), SQLUtil.returnStr(mp, "SCENE_NAME"), 
						SQLUtil.returnStr(mp, "SCENE_ICON"), SQLUtil.returnStr(mp, "SCENE_STATUS"), SQLUtil.returnInt(mp, "SCENE_SECOND")
						, SQLUtil.returnStr(mp, "SCENE_SWOK"));
				
				list.add(scene);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	
	public List<Scene> getListValid(){
		List<Scene> list = new ArrayList<Scene>();
		try {
			String sql = "SELECT * FROM SCENE WHERE SCENE_STATUS = 'Y' ORDER BY SCENE_ID";
			List<Map> lt = (List<Map>)SQLUtil.selectSQLInList(DBConnection.getConnection(), sql);
			for(Map mp : lt){
				Scene scene = new Scene(SQLUtil.returnInt(mp, "SCENE_ID"), SQLUtil.returnStr(mp, "SCENE_NAME"), 
						SQLUtil.returnStr(mp, "SCENE_ICON"), SQLUtil.returnStr(mp, "SCENE_STATUS"), SQLUtil.returnInt(mp, "SCENE_SECOND")
						, SQLUtil.returnStr(mp, "SCENE_SWOK"));
				
				list.add(scene);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	
	/**
	 * 判断情景是否绑定
	 * @param roomId
	 * @return
	 */
	public boolean isValidScene(int sceneId){
		try {
			String sql = "SELECT * FROM SCENE_BIND WHERE SCENE_ID=?";
			Map htData = SQLUtil.getFirstRecord(DBConnection.getConnection(), sceneId, true, sql);
			if(htData != null && htData.size() > 0){
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	/**
	 * 获取记录总数
	 * @param DBConnection.getConnection()
	 * @return
	 */
	public int getCount(){
		int count = 0;
		try {
			Map htData =  SQLUtil.getFirstRecord(DBConnection.getConnection(), null, true, "SELECT COUNT(*) AS COUNT FROM SCENE");
			if(htData != null){
				return SQLUtil.returnInt(htData, "COUNT");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return count;
	}
	
	/**
	 * 得到Key
	 * @param keyId
	 * @return
	 */
	public Scene get(int keyId){
		Scene rm = new Scene();
		try {
			String sql = "SELECT * FROM SCENE WHERE SCENE_ID=?";
			Map htData = SQLUtil.getFirstRecord(DBConnection.getConnection(), keyId, true, sql);
			if(htData != null){
				rm.setSceneId(SQLUtil.returnInt(htData, "SCENE_ID"));
				rm.setSceneName(SQLUtil.returnStr(htData, "SCENE_NAME"));
				rm.setSceneIcon(SQLUtil.returnStr(htData, "SCENE_ICON"));
				rm.setSceneStatus(SQLUtil.returnStr(htData, "SCENE_STATUS"));
				rm.setSceneSecond(SQLUtil.returnInt(htData, "SCENE_SECOND"));
				rm.setSceneSWOK(SQLUtil.returnStr(htData, "SCENE_SWOK"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return rm;
	}
	
	/**
	 * 插入
	 * @param DBConnection.getConnection()
	 * @param floorName
	 * @return
	 */
	public boolean insert(Scene scene){
		try {
			Unused unused = boUnused.get("SCENE");
			Unused next = UnusedUtil.getNextUnused(unused);
			scene.setSceneId(next.getNextUnused());
			
			SQLUtil.insertSQL(DBConnection.getConnection(), "SCENE", scene.fillMap());
			boUnused.updateUnused(next);
				
			DBConnection.getConnection().commit();
			// 设置系统被修改标志
			SystemConfig.getInstance().setSysChanged(true);
			return true;
		} catch (Exception e) {
			try {
				DBConnection.getConnection().rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		}
		return false;
	}
	
	public boolean update(Scene scene, List<SceneAction> actionList){
		try {
			
			boSceneAction.deleteBySenceId(scene.getSceneId());
			for(SceneAction sceneAction : actionList){
				boSceneAction.insert(sceneAction);
			}
			
			if(actionList != null && actionList.size() > 0){
				scene.setSceneStatus("Y");
			}else{
				scene.setSceneStatus("N");
			}
			
			Map<String, Object> htUpdateParam = new HashMap<String, Object>();
			htUpdateParam.put("SCENE_NAME", scene.getSceneName());
			htUpdateParam.put("SCENE_ICON", scene.getSceneIcon());
			htUpdateParam.put("SCENE_STATUS", scene.getSceneStatus());
			htUpdateParam.put("SCENE_SECOND", scene.getSceneSecond());
			htUpdateParam.put("SCENE_SWOK", scene.getSceneSWOK());
			Map<String, Object> htKeyParam = new HashMap<String, Object>();
			htKeyParam.put("SCENE_ID", scene.getSceneId());
			
			SQLUtil.updateSQL(DBConnection.getConnection(), "SCENE", htUpdateParam, htKeyParam);
			
			DBConnection.getConnection().commit();
			// 设置系统被修改标志
			SystemConfig.getInstance().setSysChanged(true);
			return true;
		} catch (Exception e) {
			try {
				DBConnection.getConnection().rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		}
		return false;
	}
	
	public boolean delete(int alarmId){
		try {
			Map<String, Object> htParam = new HashMap<String, Object>();
			htParam.put("SCENE_ID", alarmId);
			
			SQLUtil.deleteSQL(DBConnection.getConnection(), "SCENE", htParam);
			DBConnection.getConnection().commit();
			// 设置系统被修改标志
			SystemConfig.getInstance().setSysChanged(true);
			return true;
		} catch (Exception e) {
			try {
				DBConnection.getConnection().rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		}
		return false;
	}
}
