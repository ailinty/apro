package com.joeysoft.kc868.db.bo;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.joeysoft.kc868.SystemConfig;
import com.joeysoft.kc868.db.DBConnection;
import com.joeysoft.kc868.db.SQLUtil;
import com.joeysoft.kc868.db.bean.Relay;
import com.joeysoft.kc868.db.bean.Unused;
import com.joeysoft.kc868.db.util.UnusedUtil;

public class BORelay {
	
	private BOUnused boUnused = new BOUnused();
	
	
	public List<Relay> getList(){
		List<Relay> list = new ArrayList<Relay>();
		try {
			String sql = "SELECT ROOM.*, FLOOR.*, RELAY.* FROM (ROOM LEFT JOIN FLOOR ON ROOM.FLOOR = FLOOR.FLOOR) RIGHT JOIN RELAY ON ROOM.ROOM_ID = RELAY.ROOM_ID";
			List<Map> lt = (List<Map>)SQLUtil.selectSQLInList(DBConnection.getConnection(), sql);
			for(Map mp : lt){
				Relay relay = new Relay(SQLUtil.returnInt(mp, "RELAY_ID"), SQLUtil.returnInt(mp, "ROOM_ID"), SQLUtil.returnStr(mp, "RELAY_NAME"), 
						SQLUtil.returnStr(mp, "RELAY_ON_NAME"), SQLUtil.returnStr(mp, "RELAY_OFF_NAME"), SQLUtil.returnStr(mp, "RELAY_STATUS"));
				
				relay.setFloor(SQLUtil.returnInt(mp, "FLOOR"));
				relay.setFloorName(SQLUtil.returnStr(mp, "FLOOR_NAME"));
				relay.setRoomName(SQLUtil.returnStr(mp, "ROOM_NAME"));
				
				list.add(relay);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	
	public List<Relay> getListByRoomId(int roomId){
		List<Relay> list = new ArrayList<Relay>();
		try {
			String sql = "SELECT RELAY.*, FLOOR.*, ROOM.ROOM_NAME FROM RELAY, FLOOR, ROOM WHERE FLOOR.FLOOR = ROOM.FLOOR AND ROOM.ROOM_ID = RELAY.ROOM_ID AND RELAY.ROOM_ID=?";
			List<Map> lt = (List<Map>)SQLUtil.selectSQLInList(DBConnection.getConnection(), roomId, sql);
			for(Map mp : lt){
				Relay relay = new Relay(SQLUtil.returnInt(mp, "RELAY_ID"), SQLUtil.returnInt(mp, "ROOM_ID"), SQLUtil.returnStr(mp, "RELAY_NAME"), 
						SQLUtil.returnStr(mp, "RELAY_ON_NAME"), SQLUtil.returnStr(mp, "RELAY_OFF_NAME"), SQLUtil.returnStr(mp, "RELAY_STATUS"));
				
				relay.setFloor(SQLUtil.returnInt(mp, "FLOOR"));
				relay.setFloorName(SQLUtil.returnStr(mp, "FLOOR_NAME"));
				relay.setRoomName(SQLUtil.returnStr(mp, "ROOM_NAME"));
				
				list.add(relay);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	
	/**
	 * 获取记录总数
	 * @param DBConnection.getConnection()
	 * @return
	 */
	public int getCount(){
		int count = 0;
		try {
			Map htData =  SQLUtil.getFirstRecord(DBConnection.getConnection(), null, true, "SELECT COUNT(*) AS COUNT FROM RELAY");
			if(htData != null){
				return SQLUtil.returnInt(htData, "COUNT");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return count;
	}
	
	
	/**
	 * 得到Relay
	 * @param DBConnection.getConnection()
	 * @param relayId
	 * @return
	 */
	public Relay get(Integer relayId){
		Relay fl = new Relay();
		try {
			String sql = "SELECT ROOM.*, FLOOR.*, RELAY.* FROM (ROOM LEFT JOIN FLOOR ON ROOM.FLOOR = FLOOR.FLOOR) RIGHT JOIN RELAY ON ROOM.ROOM_ID = RELAY.ROOM_ID";
			Map htData = SQLUtil.getFirstRecord(DBConnection.getConnection(), relayId, true, sql);
			if(htData != null){
				fl.setRelayId(relayId);
				fl.setRoomId(SQLUtil.returnInt(htData, "ROOM_ID"));
				fl.setRelayName(SQLUtil.returnStr(htData, "RELAY_NAME"));
				fl.setRelayOffName(SQLUtil.returnStr(htData, "RELAY_OFF_NAME"));
				fl.setRelayOnName(SQLUtil.returnStr(htData, "RELAY_ON_NAME"));
				fl.setRelayStatus(SQLUtil.returnStr(htData, "RELAY_STATUS"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return fl;
	}
	
	/**
	 * 判断房间是否存在继电器
	 * @param roomId
	 * @return
	 */
	public boolean isExistsByRoomId(int roomId){
		try {
			String sql = "SELECT RELAY.*, FLOOR.*, ROOM.ROOM_NAME FROM RELAY, FLOOR, ROOM WHERE FLOOR.FLOOR = ROOM.FLOOR AND ROOM.ROOM_ID = RELAY.ROOM_ID AND RELAY.ROOM_ID=?";
			Map htData = SQLUtil.getFirstRecord(DBConnection.getConnection(), roomId, true, sql);
			if(htData != null && htData.size() > 0){
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	/**
	 * 插入楼层
	 * @param DBConnection.getConnection()
	 * @param floorName
	 * @return
	 */
	public boolean insert(Relay relay){
		try {
			DBConnection.getConnection().setAutoCommit(false);
			Unused unused = boUnused.get("RELAY");
			
			Unused next = UnusedUtil.getNextUnused(unused);
			relay.setRelayId(next.getNextUnused());
			SQLUtil.insertSQL(DBConnection.getConnection(), "RELAY", relay.fillMap());
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
	
	public void update(Relay relay) throws Exception{
		try {
			Map<String, Object> htUpdateParam = new HashMap<String, Object>();
			htUpdateParam.put("ROOM_ID", relay.getRoomId());
			htUpdateParam.put("RELAY_NAME", relay.getRelayName());
			htUpdateParam.put("RELAY_ON_NAME", relay.getRelayOnName());
			htUpdateParam.put("RELAY_OFF_NAME", relay.getRelayOffName());
			htUpdateParam.put("RELAY_STATUS", relay.getRelayStatus());
			Map<String, Object> htKeyParam = new HashMap<String, Object>();
			htKeyParam.put("RELAY_ID", relay.getRelayId());
			
			SQLUtil.updateSQL(DBConnection.getConnection(), "RELAY", htUpdateParam, htKeyParam);
			DBConnection.getConnection().commit();
			// 设置系统被修改标志
			SystemConfig.getInstance().setSysChanged(true);
		} catch (Exception e) {
			e.printStackTrace();
			DBConnection.getConnection().rollback();
			throw e;
		}
	}
	
	public boolean delete(Integer floor){
		try {
			Map<String, Object> htParam = new HashMap<String, Object>();
			htParam.put("RELAY", floor);
			
			SQLUtil.deleteSQL(DBConnection.getConnection(), "RELAY", htParam);
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
