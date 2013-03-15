package com.joeysoft.kc868.db.bo;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.joeysoft.kc868.SystemConfig;
import com.joeysoft.kc868.db.DBConnection;
import com.joeysoft.kc868.db.SQLUtil;
import com.joeysoft.kc868.db.bean.Unused;
import com.joeysoft.kc868.db.util.DataAddrCodeManager;

public class BOClear {
	private static Logger logger = LoggerFactory.getLogger(BOClear.class);
	BOUnused boUnused = new BOUnused();
	
	public boolean clearAllTable(){
		try {
			Map<String, Object> htParam = new HashMap<String, Object>();
			SQLUtil.deleteSQL(DBConnection.getConnection(), "ALARM", htParam);
			SQLUtil.deleteSQL(DBConnection.getConnection(), "DEVICE", htParam);
			SQLUtil.deleteSQL(DBConnection.getConnection(), "DEVICE_KEY", htParam);
			SQLUtil.deleteSQL(DBConnection.getConnection(), "FLOOR", htParam);
			SQLUtil.deleteSQL(DBConnection.getConnection(), "LINEATE", htParam);
			SQLUtil.deleteSQL(DBConnection.getConnection(), "RELAY", htParam);
			SQLUtil.deleteSQL(DBConnection.getConnection(), "ROOM", htParam);
			SQLUtil.deleteSQL(DBConnection.getConnection(), "SCENE", htParam);
			SQLUtil.deleteSQL(DBConnection.getConnection(), "SCENE_ACTION", htParam);
			SQLUtil.deleteSQL(DBConnection.getConnection(), "SCENE_BIND", htParam);
			SQLUtil.deleteSQL(DBConnection.getConnection(), "SENSOR", htParam);
			SQLUtil.deleteSQL(DBConnection.getConnection(), "SENSOR_IN", htParam);
			SQLUtil.deleteSQL(DBConnection.getConnection(), "SENSOR_NOR", htParam);
			SQLUtil.deleteSQL(DBConnection.getConnection(), "SMS_IN", htParam);
			SQLUtil.deleteSQL(DBConnection.getConnection(), "SMS_OUT", htParam);
			SQLUtil.deleteSQL(DBConnection.getConnection(), "TEL_IN", htParam);
			SQLUtil.deleteSQL(DBConnection.getConnection(), "TEL_OUT", htParam);
			SQLUtil.deleteSQL(DBConnection.getConnection(), "TEMP_SENSOR", htParam);
			SQLUtil.deleteSQL(DBConnection.getConnection(), "TRANSFER", htParam);
			SQLUtil.deleteSQL(DBConnection.getConnection(), "TSF_SENSOR", htParam);
			SQLUtil.deleteSQL(DBConnection.getConnection(), "VIDICON", htParam);
			
			boUnused.updateUnused(new Unused("ALARM", "", 1));
			boUnused.updateUnused(new Unused("BUGLE", "", 1));
			boUnused.updateUnused(new Unused("LINEATE", "", 1));
			boUnused.updateUnused(new Unused("RA", "", 1));
			boUnused.updateUnused(new Unused("RB", "", 1));
			boUnused.updateUnused(new Unused("RC", "", 1));
			boUnused.updateUnused(new Unused("RD", "", 1));
			boUnused.updateUnused(new Unused("SRA", "", 1));
			boUnused.updateUnused(new Unused("SRB", "", 1));
			boUnused.updateUnused(new Unused("SRC", "", 1));
			boUnused.updateUnused(new Unused("SRD", "", 1));
			boUnused.updateUnused(new Unused("RELAY", "", 1));
			boUnused.updateUnused(new Unused("SCENE", "", 1));
			boUnused.updateUnused(new Unused("SMS_IN", "", 1));
			boUnused.updateUnused(new Unused("SMS_OUT", "", 1));
			boUnused.updateUnused(new Unused("TEL_IN", "", 1));
			boUnused.updateUnused(new Unused("TEL_OUT", "", 1));
			boUnused.updateUnused(new Unused("TEMP_SENSOR", "", 1));
			boUnused.updateUnused(new Unused("TEMP_SENSOR_ADDR", "", 1));
			
			DataAddrCodeManager.getInstance().removeAll();
			
			DBConnection.getConnection().commit();
			// 设置系统被修改标志
			SystemConfig.getInstance().setSysChanged(true);
			return true;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
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
