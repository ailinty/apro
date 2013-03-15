package com.joeysoft.kc868.db.bo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.joeysoft.kc868.SystemConfig;
import com.joeysoft.kc868.db.DBConnection;
import com.joeysoft.kc868.db.SQLUtil;
import com.joeysoft.kc868.db.bean.Vidicon;

/**
 * 摄像头
 * @author JOEY
 *
 */
public class BOVidicon {
	
	private BOUnused boUnused = new BOUnused();
	
	/**
	 * 
	 * @return
	 */
	public List<Vidicon> getList(){
		List<Vidicon> list = new ArrayList<Vidicon>();
		try {
			String sql = "SELECT VIDICON.*, DEVICE.* FROM VIDICON, DEVICE WHERE VIDICON.DEVICE_ID = DEVICE.DEVICE_ID";
			
			List<Map> lt = (List<Map>)SQLUtil.selectSQLInList(DBConnection.getConnection(), sql);
			for(Map mp : lt){
				Vidicon vidicon = new Vidicon();
				vidicon.setDeviceId(SQLUtil.returnInt(mp, "DEVICE_ID"));
				vidicon.setVidiconId(SQLUtil.returnInt(mp, "VIDICON_ID"));
				vidicon.setVidiconPort(SQLUtil.returnStr(mp, "VIDICON_PORT"));
				vidicon.setVidiconPwd(SQLUtil.returnStr(mp, "VIDICON_PWD"));
				vidicon.setVidiconUrl(SQLUtil.returnStr(mp, "VIDICON_URL"));
				vidicon.setVidiconUser(SQLUtil.returnStr(mp, "VIDICON_USER"));
				vidicon.setRoomId(SQLUtil.returnInt(mp, "ROOM_ID"));
				list.add(vidicon);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	
	/**
	 * 获取房间里的非常规设备
	 * @return
	 */
	public List<Vidicon> getListByRoomId(int roomId){
		List<Vidicon> list = new ArrayList<Vidicon>();
		try {
			StringBuffer sb = new StringBuffer(200);
			sb.append("SELECT VIDICON.*, DEVICE.* FROM VIDICON, DEVICE");
			sb.append(" WHERE VIDICON.DEVICE_ID = DEVICE.DEVICE_ID");
			sb.append(" AND DEVICE.ROOM_ID = ? ");
			
			List<Map> lt = (List<Map>)SQLUtil.selectSQLInList(DBConnection.getConnection(), roomId, sb.toString());
			for(Map mp : lt){
				Vidicon vidicon = new Vidicon();
				vidicon.setDeviceId(SQLUtil.returnInt(mp, "DEVICE_ID"));
				vidicon.setVidiconId(SQLUtil.returnInt(mp, "VIDICON_ID"));
				vidicon.setVidiconPort(SQLUtil.returnStr(mp, "VIDICON_PORT"));
				vidicon.setVidiconPwd(SQLUtil.returnStr(mp, "VIDICON_PWD"));
				vidicon.setVidiconUrl(SQLUtil.returnStr(mp, "VIDICON_URL"));
				vidicon.setVidiconUser(SQLUtil.returnStr(mp, "VIDICON_USER"));
				vidicon.setRoomId(SQLUtil.returnInt(mp, "ROOM_ID"));
				list.add(vidicon);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	
	/**
	 * 得到vidicon
	 * @param vidiconId
	 * @return
	 */
	public Vidicon get(String vidiconId){
		Vidicon vidicon = new Vidicon();
		try {
			String sql = "SELECT VIDICON.*, DEVICE.* FROM VIDICON, DEVICE WHERE VIDICON.DEVICE_ID = DEVICE.DEVICE_ID";
			Map htData = SQLUtil.getFirstRecord(DBConnection.getConnection(), vidiconId, true, sql);
			if(htData != null){
				vidicon.setDeviceId(SQLUtil.returnInt(htData, "DEVICE_ID"));
				vidicon.setVidiconPort(SQLUtil.returnStr(htData, "VIDICON_PORT"));
				vidicon.setVidiconPwd(SQLUtil.returnStr(htData, "VIDICON_PWD"));
				vidicon.setVidiconUrl(SQLUtil.returnStr(htData, "VIDICON_URL"));
				vidicon.setVidiconUser(SQLUtil.returnStr(htData, "VIDICON_USER"));
				vidicon.setRoomId(SQLUtil.returnInt(htData, "ROOM_ID"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return vidicon;
	}
	
	/**
	 * 得到vidicon
	 * @param vidiconId
	 * @return
	 */
	public Vidicon getByDeviceId(int deviceId){
		Vidicon vidicon = new Vidicon();
		try {
			String sql = "SELECT VIDICON.*, DEVICE.* FROM VIDICON, DEVICE WHERE VIDICON.DEVICE_ID = DEVICE.DEVICE_ID AND DEVICE.DEVICE_ID=?";
			Map htData = SQLUtil.getFirstRecord(DBConnection.getConnection(), deviceId, true, sql);
			if(htData != null){
				vidicon.setDeviceId(SQLUtil.returnInt(htData, "DEVICE_ID"));
				vidicon.setVidiconId(SQLUtil.returnInt(htData, "VIDICON_ID"));
				vidicon.setVidiconPort(SQLUtil.returnStr(htData, "VIDICON_PORT"));
				vidicon.setVidiconPwd(SQLUtil.returnStr(htData, "VIDICON_PWD"));
				vidicon.setVidiconUrl(SQLUtil.returnStr(htData, "VIDICON_URL"));
				vidicon.setVidiconUser(SQLUtil.returnStr(htData, "VIDICON_USER"));
				vidicon.setRoomId(SQLUtil.returnInt(htData, "ROOM_ID"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return vidicon;
	}
	
	/**
	 * 插入
	 * @param icon
	 * @return
	 */
	public boolean insert(Vidicon vidicon){
		try {
			SQLUtil.insertSQL(DBConnection.getConnection(), "VIDICON", vidicon.fillMap());
			DBConnection.getConnection().commit();
			// 设置系统被修改标志
			SystemConfig.getInstance().setSysChanged(true);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public boolean update(Vidicon vidicon){
		try {
			Map<String, Object> htUpdateParam = new HashMap<String, Object>();
			htUpdateParam.put("VIDICON_URL", vidicon.getVidiconUrl());
			htUpdateParam.put("VIDICON_PORT", vidicon.getVidiconPort());
			htUpdateParam.put("VIDICON_USER", vidicon.getVidiconUser());
			htUpdateParam.put("VIDICON_PWD", vidicon.getVidiconPwd());
			Map<String, Object> htKeyParam = new HashMap<String, Object>();
			htKeyParam.put("VIDICON_ID", vidicon.getVidiconId());
			
			SQLUtil.updateSQL(DBConnection.getConnection(), "VIDICON", htUpdateParam, htKeyParam);
			DBConnection.getConnection().commit();
			// 设置系统被修改标志
			SystemConfig.getInstance().setSysChanged(true);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public boolean delete(int vidiconId){
		try {
			Map<String, Object> htParam = new HashMap<String, Object>();
			htParam.put("VIDICON_ID", vidiconId);
			
			SQLUtil.deleteSQL(DBConnection.getConnection(), "VIDICON", htParam);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
}
