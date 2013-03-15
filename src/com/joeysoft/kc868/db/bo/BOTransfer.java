package com.joeysoft.kc868.db.bo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.joeysoft.kc868.SystemConfig;
import com.joeysoft.kc868.db.DBConnection;
import com.joeysoft.kc868.db.SQLUtil;
import com.joeysoft.kc868.db.bean.DeviceKey;
import com.joeysoft.kc868.db.bean.Transfer;
import com.joeysoft.kc868.db.bean.TsfSensor;
import com.joeysoft.kc868.db.bean.Unused;
import com.joeysoft.kc868.db.util.DataAddrCodeManager;
import com.joeysoft.kc868.db.util.DataAddrCodeUtil;
import com.joeysoft.kc868.db.util.DictConst;
import com.joeysoft.kc868.db.util.SensorUtil;
import com.joeysoft.kc868.db.util.UnusedUtil;
import com.joeysoft.kc868.exception.DataExistException;

public class BOTransfer {
	private BOUnused boUnused = new BOUnused();
	private BOTransferSensor boTransferSensor = new BOTransferSensor();
	private BODeviceKey boDeviceKey = new BODeviceKey();
	
	public List<Transfer> getList(){
		List<Transfer> list = new ArrayList<Transfer>();
		try {
			String sql = "SELECT TRANSFER.*, FLOOR.*, ROOM.ROOM_NAME FROM TRANSFER, FLOOR, ROOM WHERE FLOOR.FLOOR = ROOM.FLOOR AND ROOM.ROOM_ID = TRANSFER.ROOM_ID";
			List<Map> lt = (List<Map>)SQLUtil.selectSQLInList(DBConnection.getConnection(), sql);
			for(Map mp : lt){
				
				Transfer transfer = new Transfer(SQLUtil.returnInt(mp, "TRANSFER_ID"), SQLUtil.returnInt(mp, "ROOM_ID"), 
						SQLUtil.returnStr(mp, "SENSOR_ID"), SQLUtil.returnStr(mp, "TRANSFER_NAME"), 
						SQLUtil.returnStr(mp, "FREQ_TYPE"), SQLUtil.returnStr(mp, "CODE_TYPE"),
						SQLUtil.returnStr(mp, "RES_TYPE"), SQLUtil.returnInt(mp, "ADDR_CODE"));
				transfer.setFloor(SQLUtil.returnInt(mp, "FLOOR"));
				transfer.setFloorName(SQLUtil.returnStr(mp, "FLOOR_NAME"));
				transfer.setRoomName(SQLUtil.returnStr(mp, "ROOM_NAME"));
				list.add(transfer);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	
	public List<Transfer> getListByRoomId(int roomId){
		List<Transfer> list = new ArrayList<Transfer>();
		try {
			String sql = "SELECT TRANSFER.*, FLOOR.*, ROOM.ROOM_NAME FROM TRANSFER, FLOOR, ROOM WHERE FLOOR.FLOOR = ROOM.FLOOR " +
					"AND ROOM.ROOM_ID = TRANSFER.ROOM_ID AND TRANSFER.ROOM_ID=?";
			List<Map> lt = (List<Map>)SQLUtil.selectSQLInList(DBConnection.getConnection(), roomId, sql);
			for(Map mp : lt){
				
				Transfer transfer = new Transfer(SQLUtil.returnInt(mp, "TRANSFER_ID"), SQLUtil.returnInt(mp, "ROOM_ID"), 
						SQLUtil.returnStr(mp, "SENSOR_ID"), SQLUtil.returnStr(mp, "TRANSFER_NAME"), 
						SQLUtil.returnStr(mp, "FREQ_TYPE"), SQLUtil.returnStr(mp, "CODE_TYPE"),
						SQLUtil.returnStr(mp, "RES_TYPE"), SQLUtil.returnInt(mp, "ADDR_CODE"));
				transfer.setFloor(SQLUtil.returnInt(mp, "FLOOR"));
				transfer.setFloorName(SQLUtil.returnStr(mp, "FLOOR_NAME"));
				transfer.setRoomName(SQLUtil.returnStr(mp, "ROOM_NAME"));
				list.add(transfer);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	
	/**
	 * 得到Transfer
	 * @param transferId
	 * @return
	 */
	public Transfer get(int transferId){
		Transfer rm = new Transfer();
		try {
			String sql = "SELECT TRANSFER.*, FLOOR.*, ROOM.ROOM_NAME FROM TRANSFER, FLOOR, ROOM WHERE FLOOR.FLOOR = ROOM.FLOOR AND ROOM.ROOM_ID = TRANSFER.ROOM_ID AND TRANSFER_ID=?";
			Map htData = SQLUtil.getFirstRecord(DBConnection.getConnection(), transferId, true, sql);
			if(htData != null){
				rm.setTransferId(transferId);
				rm.setRoomId(SQLUtil.returnInt(htData, "ROOM_ID"));
				rm.setSensorId(SQLUtil.returnStr(htData, "SENSOR_ID"));
				rm.setTransferName(SQLUtil.returnStr(htData, "TRANSFER_NAME"));
				rm.setFreqType(SQLUtil.returnStr(htData, "FREQ_TYPE"));
				rm.setCodeType(SQLUtil.returnStr(htData, "CODE_TYPE"));
				rm.setResType(SQLUtil.returnStr(htData, "RES_TYPE"));
				rm.setAddrCode(SQLUtil.returnInt(htData, "ADDR_CODE"));
				
				rm.setFloor(SQLUtil.returnInt(htData, "FLOOR"));
				rm.setFloorName(SQLUtil.returnStr(htData, "FLOOR_NAME"));
				rm.setRoomName(SQLUtil.returnStr(htData, "ROOM_NAME"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return rm;
	}
	
	public int getMax(){
		try {
			Map htData =  SQLUtil.getFirstRecord(DBConnection.getConnection(), null, true, "SELECT MAX(TRANSFER_ID) AS MAX FROM TRANSFER");
			if(htData != null){
				return SQLUtil.returnInt(htData, "MAX");
			}
			return -1;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return -1;
	}
	
	/**
	 * 判断房间是否存在同名转发器
	 * @param roomId
	 * @param sensorName
	 */
	public boolean isExistByName(int roomId, String transferName){
		try {
			String sql = "SELECT TRANSFER.*, FLOOR.*, ROOM.ROOM_NAME FROM TRANSFER, FLOOR, ROOM WHERE FLOOR.FLOOR = ROOM.FLOOR AND ROOM.ROOM_ID = TRANSFER.ROOM_ID " +
					"AND TRANSFER.ROOM_ID=? AND TRANSFER.TRANSFER_NAME=?";
			Map htData = SQLUtil.getFirstRecord(DBConnection.getConnection(), roomId, transferName, true, sql);
			if(htData != null && htData.size() > 0){
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	/**
	 * 插入前判断
	 * @param DBConnection.getConnection()
	 * @param floorName
	 * @return Unused
	 * @throws Exception 
	 */
	public TsfSensor insertBefore(Transfer transfer) throws Exception{
		
		TsfSensor tsfSensor = new TsfSensor();
		if(isExistByName(transfer.getRoomId(), transfer.getTransferName())){
			throw new DataExistException("已经存在名称为"+transfer.getTransferName()+"的红外转发器！");
		}
		
		// 判断地址码是否存在
		if(DataAddrCodeManager.getInstance().isContains(transfer.getResType(), transfer.getAddrCode(), DictConst.TRANSFER_STUDY_DATACODE)){
			throw new DataExistException("此地址码已经存在，建议使用" + DataAddrCodeUtil.getUnusedAddrCode(transfer.getCodeType(), transfer.getResType()));
		}
		
		tsfSensor.setSensorName(transfer.getTransferName());
		tsfSensor.setTransferId(transfer.getTransferId());
		tsfSensor.setFreqType(transfer.getFreqType());
		tsfSensor.setCodeType(transfer.getCodeType());
		tsfSensor.setResType(transfer.getResType());
		tsfSensor.setAddrCode(transfer.getAddrCode());
		tsfSensor.setDataCode(DictConst.TRANSFER_STUDY_DATACODE);
				
		// 取无线的编号前缀[RA,RB,RC,RD]
		String tableName = SensorUtil.getSensorCodeOut(tsfSensor.getFreqType(), tsfSensor.getCodeType());
			
		Unused unused = boUnused.get(tableName);
		
		Unused next = UnusedUtil.getNextUnused(unused);
		
		// 编号前缀[RA,RB,RC,RD] 加编号
		tsfSensor.setSensorId(tableName + next.getNextUnused());
					
		
		return tsfSensor;
	}
	
	/**
	 * 插入
	 * @param DBConnection.getConnection()
	 * @param floorName
	 * @return
	 * @throws Exception 
	 */
	public TsfSensor insert(Transfer transfer) throws Exception{
		
		TsfSensor tsfSensor = new TsfSensor();
		try{
			transfer.setTransferId(getMax() + 1); // 编码+1
			
			// 保存学习命令
			tsfSensor.setSensorName(transfer.getTransferName());
			tsfSensor.setTransferId(transfer.getTransferId());
			tsfSensor.setFreqType(transfer.getFreqType());
			tsfSensor.setCodeType(transfer.getCodeType());
			tsfSensor.setResType(transfer.getResType());
			tsfSensor.setAddrCode(transfer.getAddrCode());
			tsfSensor.setDataCode(DictConst.TRANSFER_STUDY_DATACODE);
			// 取无线的编号前缀[RA,RB,RC,RD]
			String tableName = SensorUtil.getSensorCodeOut(tsfSensor.getFreqType(), tsfSensor.getCodeType());
				
			Unused unused = boUnused.get(tableName);
			
			Unused next = UnusedUtil.getNextUnused(unused);
			
			// 编号前缀[RA,RB,RC,RD] 加编号
			tsfSensor.setSensorId(tableName + next.getNextUnused());
			
			transfer.setSensorId(tsfSensor.getSensorId());
			
			SQLUtil.insertSQL(DBConnection.getConnection(), "TRANSFER", transfer.fillMap());
			
			SQLUtil.insertSQL(DBConnection.getConnection(), "TSF_SENSOR", tsfSensor.fillMap());
			// 把地址码加入缓存
			DataAddrCodeManager.getInstance().addDataAddrCode(tsfSensor.getResType(), tsfSensor.getAddrCode(), DictConst.TRANSFER_STUDY_DATACODE);
			
			boUnused.updateUnused(next);
			
			DBConnection.getConnection().commit();
			// 设置系统被修改标志
			SystemConfig.getInstance().setSysChanged(true);
		}catch(Exception e){
			DBConnection.getConnection().rollback();
			e.printStackTrace();
			throw new Exception(e);
		}
		
		return tsfSensor;
	}
	
	public void update(Transfer transfer) throws Exception{
		Map<String, Object> htUpdateParam = new HashMap<String, Object>();
		htUpdateParam.put("TRANSFER_NAME", transfer.getTransferName());
		htUpdateParam.put("ROOM_ID", transfer.getRoomId());
		Map<String, Object> htKeyParam = new HashMap<String, Object>();
		htKeyParam.put("TRANSFER_ID", transfer.getTransferId());
		
		SQLUtil.updateSQL(DBConnection.getConnection(), "TRANSFER", htUpdateParam, htKeyParam);
	}
	
	public void delete(int transferId)throws Exception{
		try {
			Transfer transfer = get(transferId);
			// 把按键更新为未学习
			List<DeviceKey> keyList = boDeviceKey.getListBySensorTableTansferId(transferId);
			for(DeviceKey deviceKey : keyList){
				deviceKey.setSensorId("");
				deviceKey.setSensorTable("");
				boDeviceKey.updateSensor(deviceKey);
			}
			
			boTransferSensor.deleteByTransferId(transferId);
			
			Map<String, Object> htParam = new HashMap<String, Object>();
			htParam.put("TRANSFER_ID", transferId);
			
			SQLUtil.deleteSQL(DBConnection.getConnection(), "TRANSFER", htParam);
			
			DataAddrCodeManager.getInstance().removeDataAddrCode(transfer.getResType(), transfer.getAddrCode(), DictConst.TRANSFER_STUDY_DATACODE);
			
			DBConnection.getConnection().commit();
			// 设置系统被修改标志
			SystemConfig.getInstance().setSysChanged(true);
		} catch (Exception e) {
			DBConnection.getConnection().rollback();
			e.printStackTrace();
			throw new Exception(e);
		}
	}
}
