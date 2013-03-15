package com.joeysoft.kc868.db.bo;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.joeysoft.kc868.SystemConfig;
import com.joeysoft.kc868.db.DBConnection;
import com.joeysoft.kc868.db.SQLUtil;
import com.joeysoft.kc868.db.bean.Lineate;
import com.joeysoft.kc868.db.bean.Relay;
import com.joeysoft.kc868.db.bean.Unused;
import com.joeysoft.kc868.db.util.DictConst;
import com.joeysoft.kc868.db.util.UnusedUtil;

public class BOLineate {
	private BOUnused boUnused = new BOUnused();
	
	public List<Lineate> getList(){
		List<Lineate> list = new ArrayList<Lineate>();
		try {
			String sql = "SELECT ROOM.*, FLOOR.*,LINEATE.* FROM (ROOM LEFT JOIN FLOOR ON ROOM.FLOOR = FLOOR.FLOOR) " +
					"RIGHT JOIN LINEATE ON ROOM.ROOM_ID = LINEATE.ROOM_ID ORDER BY LINEATE_ID";
			List<Map> lt = (List<Map>)SQLUtil.selectSQLInList(DBConnection.getConnection(), sql);
			for(Map mp : lt){
				
				Lineate lineate = new Lineate(SQLUtil.returnInt(mp, "LINEATE_ID"), SQLUtil.returnInt(mp, "ROOM_ID"), SQLUtil.returnStr(mp, "LINEATE_NAME"), 
						SQLUtil.returnStr(mp, "LINEATE_TYPE"), SQLUtil.returnStr(mp, "LINEATE_UD"), SQLUtil.returnStr(mp, "LINEATE_GL"),
						SQLUtil.returnStr(mp, "LINEATE_V"));
				
				lineate.setFloor(SQLUtil.returnInt(mp, "FLOOR"));
				lineate.setFloorName(SQLUtil.returnStr(mp, "FLOOR_NAME"));
				lineate.setRoomName(SQLUtil.returnStr(mp, "ROOM_NAME"));
				
				list.add(lineate);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	
	public List<Lineate> getListByRoomId(int roomId){
		List<Lineate> list = new ArrayList<Lineate>();
		try {
			String sql = "SELECT LINEATE.* FROM LINEATE WHERE LINEATE.ROOM_ID = ?";
			List<Map> lt = (List<Map>)SQLUtil.selectSQLInList(DBConnection.getConnection(), roomId, sql);
			for(Map mp : lt){
				
				Lineate lineate = new Lineate(SQLUtil.returnInt(mp, "LINEATE_ID"), SQLUtil.returnInt(mp, "ROOM_ID"), SQLUtil.returnStr(mp, "LINEATE_NAME"), 
						SQLUtil.returnStr(mp, "LINEATE_TYPE"), SQLUtil.returnStr(mp, "LINEATE_UD"), SQLUtil.returnStr(mp, "LINEATE_GL"),
						SQLUtil.returnStr(mp, "LINEATE_V"));
				
				lineate.setFloor(SQLUtil.returnInt(mp, "FLOOR"));
				lineate.setFloorName(SQLUtil.returnStr(mp, "FLOOR_NAME"));
				lineate.setRoomName(SQLUtil.returnStr(mp, "ROOM_NAME"));
				
				list.add(lineate);
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
			Map htData =  SQLUtil.getFirstRecord(DBConnection.getConnection(), null, true, "SELECT COUNT(*) AS COUNT FROM LINEATE");
			if(htData != null){
				return SQLUtil.returnInt(htData, "COUNT");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return count;
	}
	/**
	 * 得到Lineate
	 * @param lineateId
	 * @return
	 */
	public Lineate get(int lineateId){
		Lineate rm = new Lineate();
		try {
			String sql = "SELECT * FROM LINEATE WHERE LINEATE_ID=?";
			Map htData = SQLUtil.getFirstRecord(DBConnection.getConnection(), lineateId, true, sql);
			if(htData != null){
				rm.setLineateId(lineateId);
				rm.setLineateId(SQLUtil.returnInt(htData, "ROOM_ID"));
				rm.setLineateName(SQLUtil.returnStr(htData, "LINEATE_NAME"));
				rm.setLineateType(SQLUtil.returnStr(htData, "LINEATE_TYPE"));
				rm.setLineateUD(SQLUtil.returnStr(htData, "LINEATE_UD"));
				rm.setLineateGL(SQLUtil.returnStr(htData, "LINEATE_GL"));
				rm.setLineateV(SQLUtil.returnStr(htData, "LINEATE_V"));
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
	public boolean insert(Lineate lineate){
		try {
			DBConnection.getConnection().setAutoCommit(false);
			Unused unused = boUnused.get("LINEATE");
			
			Unused next = UnusedUtil.getNextUnused(unused);
			lineate.setLineateId(next.getNextUnused());
			SQLUtil.insertSQL(DBConnection.getConnection(), "LINEATE", lineate.fillMap());
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
	
	public boolean update(Lineate lineate){
		try {
			Map<String, Object> htUpdateParam = new HashMap<String, Object>();
			htUpdateParam.put("ROOM_ID", lineate.getRoomId());
			htUpdateParam.put("LINEATE_NAME", lineate.getLineateName());
			htUpdateParam.put("LINEATE_TYPE", lineate.getLineateType());
			htUpdateParam.put("LINEATE_UD", lineate.getLineateUD());
			htUpdateParam.put("LINEATE_GL", lineate.getLineateGL());
			htUpdateParam.put("LINEATE_V", lineate.getLineateV());
			Map<String, Object> htKeyParam = new HashMap<String, Object>();
			htKeyParam.put("LINEATE_ID", lineate.getLineateId());
			
			SQLUtil.updateSQL(DBConnection.getConnection(), "LINEATE", htUpdateParam, htKeyParam);
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
	
	public boolean delete(int lineateId){
		try {
			Map<String, Object> htParam = new HashMap<String, Object>();
			htParam.put("LINEATE_ID", lineateId);
			
			SQLUtil.deleteSQL(DBConnection.getConnection(), "LINEATE", htParam);
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
	 * 
	 * 返回十进制MODE设置，用8位二进制表示，1代表开关量，0代表模拟量，组合后转十进制
	 * @return
	 */
	public int getModeNum(Lineate lineate){
		List<Lineate> list = getList();
		String[] mode2Bits = new String[list.size()];
		String mode2Bit = "";
		
		for(int i=0; i<list.size(); i++){
			Lineate lt = list.get(i);
			if(lt.getLineateId() == lineate.getLineateId()){
				mode2Bits[i] = lineate.getLineateType();
			}else{
				mode2Bits[i] = lt.getLineateType();
			}
		}
		for(int i=0; i<mode2Bits.length; i++){
			mode2Bit += mode2Bits[i];
		}
		
		return Integer.valueOf(mode2Bit, 2);
	}
	
	/**
	 * 返回十进制开关量
	 * @return
	 */
	public int getRFNum(Lineate lineate){
		List<Lineate> list = getList();
		String[] mode2Bits = new String[list.size()];
		String mode2Bit = "";
		
		for(int i=0; i<list.size(); i++){
			Lineate lt = list.get(i);
			if(lt.getLineateId() == lineate.getLineateId()){
				if(lineate.getLineateType().equals(DictConst.LINEATE_TYPE_UD)){ // 开关量
					mode2Bits[i] = lineate.getLineateUD();
				}else{ // 模拟量
					mode2Bits[i] = DictConst.LINEATE_TYPE_GL;
				}
			}else{
				if(lt.getLineateType().equals(DictConst.LINEATE_TYPE_UD)){ // 开关量
					mode2Bits[i] = lt.getLineateUD();
				}else{ // 模拟量
					mode2Bits[i] = DictConst.LINEATE_TYPE_GL;
				}
			}
		}
		for(int i=0; i<mode2Bits.length; i++){
			mode2Bit = mode2Bits[i] + mode2Bit;
		}
		
		return Integer.valueOf(mode2Bit, 2);
	}
	
	/**
	 * 返回load路的限值
	 * @return
	 */
	public String getADNum(int load){
		 Lineate lineate = get(load);
		 return lineate.getLineateV();
	}
}
