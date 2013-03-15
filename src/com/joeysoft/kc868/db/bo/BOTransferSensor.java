package com.joeysoft.kc868.db.bo;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.joeysoft.kc868.SystemConfig;
import com.joeysoft.kc868.db.DBConnection;
import com.joeysoft.kc868.db.SQLUtil;
import com.joeysoft.kc868.db.bean.DeviceKey;
import com.joeysoft.kc868.db.bean.Transfer;
import com.joeysoft.kc868.db.bean.TsfSensor;
import com.joeysoft.kc868.db.bean.Unused;
import com.joeysoft.kc868.db.bean.vo.SensorVo;
import com.joeysoft.kc868.db.bean.vo.TransferSensorVo;
import com.joeysoft.kc868.db.util.DataAddrCodeManager;
import com.joeysoft.kc868.db.util.DataAddrCodeUtil;
import com.joeysoft.kc868.db.util.ResDataVo;
import com.joeysoft.kc868.db.util.SensorUtil;
import com.joeysoft.kc868.db.util.UnusedUtil;
import com.joeysoft.kc868.exception.DataExistException;
import com.joeysoft.kc868.exception.ThresholdException;

/**
 * 红外转发器命令
 * @author JOEY
 *
 */
public class BOTransferSensor {
	
	private BOUnused boUnused = new BOUnused();
	private BODeviceKey boDeviceKey = new BODeviceKey();
	
	
	public List<TsfSensor> getList(){
		List<TsfSensor> list = new ArrayList<TsfSensor>();
		try {
			String sql = "SELECT TSF_SENSOR.*, TSF_SENSOR.SENSOR_ID AS TSF_SENSOR_ID, TRANSFER.ADDR_CODE, " +
					"TRANSFER.FREQ_TYPE, TRANSFER.CODE_TYPE " +
					"FROM TSF_SENSOR, TRANSFER WHERE TSF_SENSOR.TRANSFER_ID = TRANSFER.TRANSFER_ID";
			List<Map> lt = (List<Map>)SQLUtil.selectSQLInList(DBConnection.getConnection(), sql);
			for(Map mp : lt){
				TsfSensor tsfSensor = new TsfSensor(SQLUtil.returnStr(mp, "TSF_SENSOR_ID"), SQLUtil.returnInt(mp, "TRANSFER_ID"), 
						SQLUtil.returnStr(mp, "SENSOR_NAME"), SQLUtil.returnStr(mp, "RES_TYPE"), SQLUtil.returnInt(mp, "DATA_CODE"),
						SQLUtil.returnStr(mp, "SENSOR_STUDY"));
				tsfSensor.setTransferId(SQLUtil.returnInt(mp, "TRANSFER_ID"));
				
				tsfSensor.setAddrCode(SQLUtil.returnInt(mp, "ADDR_CODE"));
				tsfSensor.setResType(SQLUtil.returnStr(mp, "RES_TYPE"));
				tsfSensor.setFreqType(SQLUtil.returnStr(mp, "FREQ_TYPE"));
				tsfSensor.setCodeType(SQLUtil.returnStr(mp, "CODE_TYPE"));
				
				list.add(tsfSensor);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	
	public List<TsfSensor> getListByTransferId(int transferId){
		List<TsfSensor> list = new ArrayList<TsfSensor>();
		try {
			String sql = "SELECT TSF_SENSOR.*,TRANSFER.ADDR_CODE, " +
					"TRANSFER.FREQ_TYPE, TRANSFER.CODE_TYPE " +
					"FROM TSF_SENSOR, TRANSFER WHERE TSF_SENSOR.TRANSFER_ID = TRANSFER.TRANSFER_ID AND TRANSFER_ID=?";
			List<Map> lt = (List<Map>)SQLUtil.selectSQLInList(DBConnection.getConnection(), transferId, sql);
			for(Map mp : lt){
				TsfSensor tsfSensor = new TsfSensor(SQLUtil.returnStr(mp, "SENSOR_ID"), SQLUtil.returnInt(mp, "TRANSFER_ID"), 
						SQLUtil.returnStr(mp, "SENSOR_NAME"), SQLUtil.returnStr(mp, "RES_TYPE"), SQLUtil.returnInt(mp, "DATA_CODE"),
						SQLUtil.returnStr(mp, "SENSOR_STUDY"));
				
				tsfSensor.setTransferId(SQLUtil.returnInt(mp, "TRANSFER_ID"));
				
				tsfSensor.setAddrCode(SQLUtil.returnInt(mp, "ADDR_CODE"));
				tsfSensor.setResType(SQLUtil.returnStr(mp, "RES_TYPE"));
				tsfSensor.setFreqType(SQLUtil.returnStr(mp, "FREQ_TYPE"));
				tsfSensor.setCodeType(SQLUtil.returnStr(mp, "CODE_TYPE"));
				
				list.add(tsfSensor);
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
	public TsfSensor get(String sensorId){
		TsfSensor rm = new TsfSensor();
		try {
			String sql = "SELECT TSF_SENSOR.*,TRANSFER.ADDR_CODE, " +
					"TRANSFER.FREQ_TYPE, TRANSFER.CODE_TYPE "+
					"FROM TSF_SENSOR, TRANSFER WHERE TSF_SENSOR.TRANSFER_ID = TRANSFER.TRANSFER_ID AND TSF_SENSOR.SENSOR_ID=?";
			Map htData = SQLUtil.getFirstRecord(DBConnection.getConnection(), sensorId, true, sql);
			if(htData != null){
				rm.setTransferId(SQLUtil.returnInt(htData, "TRANSFER_ID"));
				rm.setSensorId(sensorId);
				rm.setFreqType(SQLUtil.returnStr(htData, "FREQ_TYPE"));
				rm.setCodeType(SQLUtil.returnStr(htData, "CODE_TYPE"));
				rm.setResType(SQLUtil.returnStr(htData, "RES_TYPE"));
				rm.setAddrCode(SQLUtil.returnInt(htData, "ADDR_CODE"));
				rm.setDataCode(SQLUtil.returnInt(htData, "DATA_CODE"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return rm;
	}
	
	/**
	 * 删除sensor，收回资源
	 * @param SensorId
	 * @throws SQLException 
	 */
	public void deleteSensor(String sensorId) throws Exception{
		try{
			TsfSensor tsfSensor = get(sensorId);
			// 如果原来有sensorId的，收回资源
			if(StringUtils.isNotEmpty(tsfSensor.getSensorId())){
				DataAddrCodeManager.getInstance().removeDataAddrCode(tsfSensor.getResType(), tsfSensor.getAddrCode(), tsfSensor.getDataCode());
				// 收回主键
				boUnused.addUnused(tsfSensor.getSensorId().substring(0,2), Integer.valueOf(tsfSensor.getSensorId().substring(2)));
				
				Map<String, Object> htParam = new HashMap<String, Object>();
				htParam.put("SENSOR_ID", sensorId);
				SQLUtil.deleteSQL(DBConnection.getConnection(), "TSF_SENSOR", htParam);
				DBConnection.getConnection().commit();
				// 设置系统被修改标志
				SystemConfig.getInstance().setSysChanged(true);
			}
		}catch(Exception e){
			DBConnection.getConnection().rollback();
			throw new Exception(e);
		}
		
	}
	
	public Unused insertBefore(TsfSensor tsfSensor) throws Exception{
		
		// 取无线的编号前缀[RA,RB,RC,RD]
		String tableName = SensorUtil.getSensorCodeOut(tsfSensor.getFreqType(), tsfSensor.getCodeType());
			
		Unused unused = boUnused.get(tableName);
		
		Unused next = UnusedUtil.getNextUnused(unused);
		
		boolean isThreshold = boUnused.isThreshold(next);
		if(isThreshold){
			throw new ThresholdException();
		}
		
		ResDataVo resDataVo = DataAddrCodeUtil.getUnusedDataCodeNextRes(tsfSensor.getCodeType(), tsfSensor.getResType(), tsfSensor.getAddrCode());
		if(resDataVo == null){
			throw new Exception(tsfSensor.getAddrCode()+"地址码下已没有可用数据码！");
		}
		tsfSensor.setResType(resDataVo.getResType());
		tsfSensor.setDataCode(resDataVo.getDataCode());
		
		
		// 编号前缀[RA,RB,RC,RD] 加编号
		tsfSensor.setSensorId(tableName + next.getNextUnused());
		
		return next;
	}
	
	/**
	 * 插入
	 * @param DBConnection.getConnection()
	 * @param floorName
	 * @return
	 * @throws Exception 
	 */
	public TsfSensor insert(TsfSensor tsfSensor, Unused next, DeviceKey deviceKey) throws Exception{
		try{
			SQLUtil.insertSQL(DBConnection.getConnection(), "TSF_SENSOR", tsfSensor.fillMap());
			// 把地址码加入缓存
			DataAddrCodeManager.getInstance().addDataAddrCode(tsfSensor.getResType(), tsfSensor.getAddrCode(), tsfSensor.getDataCode());
			
			boUnused.updateUnused(next);
			// 更新按键表
			deviceKey.setSensorId(tsfSensor.getSensorId());
			deviceKey.setSensorTable("TSF_SENSOR");
			boDeviceKey.updateSensor(deviceKey);
			
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
	
	public void deleteByTransferId(int transferId) throws Exception{
		
		List<TsfSensor> sensorList = getListByTransferId(transferId);
		
		// 收到资源
		for(TsfSensor tsfSensor : sensorList){
			DataAddrCodeManager.getInstance().removeDataAddrCode(tsfSensor.getResType(), tsfSensor.getAddrCode(), tsfSensor.getDataCode());
			// 收回主键
			boUnused.addUnused(tsfSensor.getSensorId().substring(0,2), Integer.valueOf(tsfSensor.getSensorId().substring(2)));
		}
		
		Map<String, Object> htParam = new HashMap<String, Object>();
		htParam.put("TRANSFER_ID", transferId);
		SQLUtil.deleteSQL(DBConnection.getConnection(), "TSF_SENSOR", htParam);
	}
	
}
