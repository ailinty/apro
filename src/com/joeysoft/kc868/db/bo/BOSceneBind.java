package com.joeysoft.kc868.db.bo;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.joeysoft.kc868.SystemConfig;
import com.joeysoft.kc868.db.DBConnection;
import com.joeysoft.kc868.db.SQLUtil;
import com.joeysoft.kc868.db.bean.SceneBind;

public class BOSceneBind {

	private BOUnused boUnused = new BOUnused();

	public List<SceneBind> getList() {
		List<SceneBind> list = new ArrayList<SceneBind>();
		try {
			String sql = "SELECT * FROM SCENE_BIND ORDER BY SCENE_BIND_ID";
			List<Map> lt = (List<Map>) SQLUtil.selectSQLInList(
					DBConnection.getConnection(), sql);
			for (Map mp : lt) {

				SceneBind bind = new SceneBind(SQLUtil.returnInt(mp,
						"SCENE_BIND_ID"), SQLUtil.returnInt(mp, "SCENE_ID"),
						SQLUtil.returnStr(mp, "SOURCE_TABLE"),
						SQLUtil.returnStr(mp, "SOURCE_ID"), SQLUtil.returnStr(
								mp, "SOURCE_CMD"), SQLUtil.returnStr(mp,
								"SOURCE_TEXT"));

				list.add(bind);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	public List<SceneBind> getListBySceneId(int sceneId) {
		List<SceneBind> list = new ArrayList<SceneBind>();
		try {
			String sql = "SELECT * FROM SCENE_BIND WHERE SCENE_ID = ? ORDER BY SCENE_BIND_ID";
			List<Map> lt = (List<Map>) SQLUtil.selectSQLInList(
					DBConnection.getConnection(), sceneId, sql);
			for (Map mp : lt) {

				SceneBind bind = new SceneBind(SQLUtil.returnInt(mp,
						"SCENE_BIND_ID"), SQLUtil.returnInt(mp, "SCENE_ID"),
						SQLUtil.returnStr(mp, "SOURCE_TABLE"),
						SQLUtil.returnStr(mp, "SOURCE_ID"), SQLUtil.returnStr(
								mp, "SOURCE_CMD"), SQLUtil.returnStr(mp,
								"SOURCE_TEXT"));

				list.add(bind);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	
	/**
	 * 判断是否存在动作
	 * @param sceneId
	 * @param sensorTable
	 * @param sensorId
	 * @return
	 */
	public boolean isExistBind(String sensorTable, int sensorId){
		try {
			String sql = "SELECT 1 FROM SCENE_BIND WHERE SOURCE_TABLE = ? AND SOURCE_ID = ?";
			Map htData = SQLUtil.getFirstRecord(DBConnection.getConnection(), sensorTable, sensorId, true, sql);
			if(htData != null && htData.size() > 0){
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	/**
	 * 判断是否存在动作
	 * @param sceneId
	 * @param sensorTable
	 * @param sensorId
	 * @return
	 */
	public boolean isExistBind(String sensorTable, String sensorId){
		try {
			String sql = "SELECT 1 FROM SCENE_BIND WHERE SOURCE_TABLE = ? AND SOURCE_ID = ?";
			Map htData = SQLUtil.getFirstRecord(DBConnection.getConnection(), sensorTable, sensorId, true, sql);
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
	 * 
	 * @param DBConnection
	 *            .getConnection()
	 * @return
	 */
	public int getCount() {
		int count = 0;
		try {
			Map htData = SQLUtil.getFirstRecord(DBConnection.getConnection(),
					null, true, "SELECT COUNT(*) AS COUNT FROM SCENE_BIND");
			if (htData != null) {
				return SQLUtil.returnInt(htData, "COUNT");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return count;
	}

	/**
	 * 得到Key
	 * 
	 * @param keyId
	 * @return
	 */
	public SceneBind get(int bindId) {
		SceneBind rm = new SceneBind();
		try {
			String sql = "SELECT * FROM SCENE_BIND WHERE SCENE_BIND_ID=?";
			Map htData = SQLUtil.getFirstRecord(DBConnection.getConnection(),
					bindId, true, sql);
			if (htData != null) {
				rm.setSceneBindId(bindId);
				rm.setSceneId(SQLUtil.returnInt(htData, "SCENE_ID"));
				rm.setSourceTable(SQLUtil.returnStr(htData, "SOURCE_TABLE"));
				rm.setSourceId(SQLUtil.returnStr(htData, "SOURCE_ID"));
				rm.setSourceCmd(SQLUtil.returnStr(htData, "SOURCE_CMD"));
				rm.setSourceText(SQLUtil.returnStr(htData, "SOURCE_TEXT"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return rm;
	}

	public boolean insertOrUpdate(SceneBind bind) {
		try {
			deleteBySenceId(bind.getSceneId());
			
			/*if (bind.getSceneBindId() == -1) {
				insert(bind);
			} else {
				update(bind);
			}*/
			
			insert(bind);
			
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

	/**
	 * 插入
	 * 
	 * @param DBConnection
	 *            .getConnection()
	 * @param floorName
	 * @return
	 * @throws Exception
	 */
	private void insert(SceneBind bind) throws Exception {
		SQLUtil.insertSQL(DBConnection.getConnection(), "SCENE_BIND",
				bind.fillMap());
	}

	private void update(SceneBind bind) throws Exception {
		Map<String, Object> htUpdateParam = new HashMap<String, Object>();
		htUpdateParam.put("SCENE_ID", bind.getSceneId());
		htUpdateParam.put("SOURCE_ID", bind.getSourceId());
		htUpdateParam.put("SOURCE_TABLE", bind.getSourceTable());
		htUpdateParam.put("SOURCE_CMD", bind.getSourceCmd());
		htUpdateParam.put("SOURCE_TEXT", bind.getSourceText());
		Map<String, Object> htKeyParam = new HashMap<String, Object>();
		htKeyParam.put("SCENE_BIND_ID", bind.getSceneBindId());

		SQLUtil.updateSQL(DBConnection.getConnection(), "SCENE_BIND",
				htUpdateParam, htKeyParam);
	}

	public boolean delete(int bindId) {
		try {
			Map<String, Object> htParam = new HashMap<String, Object>();
			htParam.put("SCENE_BIND_ID", bindId);

			SQLUtil.deleteSQL(DBConnection.getConnection(), "SCENE_BIND",
					htParam);
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

	public boolean deleteBySenceId(int senceId){
		try {
			Map<String, Object> htParam = new HashMap<String, Object>();
			htParam.put("SCENE_ID", senceId);

			SQLUtil.deleteSQL(DBConnection.getConnection(), "SCENE_BIND",
					htParam);
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
