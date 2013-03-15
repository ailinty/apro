package com.joeysoft.kc868.db.bo;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.joeysoft.kc868.SystemConfig;
import com.joeysoft.kc868.db.DBConnection;
import com.joeysoft.kc868.db.SQLUtil;
import com.joeysoft.kc868.db.bean.Floor;

public class BOFloor {
	private static Logger logger = LoggerFactory.getLogger(BOFloor.class);

	public List<Floor> getList() {
		List<Floor> list = new ArrayList<Floor>();
		try {
			String sql = "SELECT * FROM FLOOR";
			List<Map> lt = (List<Map>) SQLUtil.selectSQLInList(
					DBConnection.getConnection(), sql);
			for (Map mp : lt) {
				Floor floor = new Floor(SQLUtil.returnInt(mp, "FLOOR"),
						SQLUtil.returnStr(mp, "FLOOR_NAME"));
				list.add(floor);
			}

		} catch (Exception e) {
			logger.error("BOFloor:", e.getCause());
			e.printStackTrace();
		}
		return list;
	}

	/**
	 * 得到Floor
	 * 
	 * @param DBConnection
	 *            .getConnection()
	 * @param floor
	 * @return
	 */
	public Floor get(Integer floor) {
		Floor fl = new Floor();
		try {
			String sql = "SELECT * FROM FLOOR WHERE FLOOR=?";
			Map htData = SQLUtil.getFirstRecord(DBConnection.getConnection(),
					floor, true, sql);
			if (htData != null) {
				fl.setFloor(floor);
				fl.setFloorName(SQLUtil.returnStr(htData, "FLOOR_NAME"));
			}
		} catch (Exception e) {
			logger.error("BOFloor:", e.getCause());
			e.printStackTrace();
		}
		return fl;
	}

	public int getMax() {
		try {
			Map htData = SQLUtil.getFirstRecord(DBConnection.getConnection(),
					null, true, "SELECT MAX(FLOOR) AS MAX FROM FLOOR");
			if (htData != null) {
				return SQLUtil.returnInt(htData, "MAX");
			}
			return -1;
		} catch (Exception e) {
			logger.error("BOFloor:", e.getCause());
			e.printStackTrace();
		}
		return -1;
	}

	/**
	 * 插入楼层
	 * 
	 * @param DBConnection
	 *            .getConnection()
	 * @param floorName
	 * @return
	 */
	public boolean insert(Floor floor) {
		try {
			SQLUtil.insertSQL(DBConnection.getConnection(), "FLOOR",
					floor.fillMap());
			
			DBConnection.getConnection().commit();
			// 设置系统被修改标志
			SystemConfig.getInstance().setSysChanged(true);
			return true;
		} catch (Exception e) {
			logger.error("BOFloor:", e.getCause());
			e.printStackTrace();
		}
		return false;
	}

	public boolean update(Floor floor) {
		try {
			Map<String, Object> htUpdateParam = new HashMap<String, Object>();
			htUpdateParam.put("FLOOR_NAME", floor.getFloorName());
			Map<String, Object> htKeyParam = new HashMap<String, Object>();
			htKeyParam.put("FLOOR", floor.getFloor());

			SQLUtil.updateSQL(DBConnection.getConnection(), "FLOOR",
					htUpdateParam, htKeyParam);
			
			DBConnection.getConnection().commit();
			// 设置系统被修改标志
			SystemConfig.getInstance().setSysChanged(true);
			return true;
		} catch (Exception e) {
			logger.error("BOFloor:", e.getCause());
			e.printStackTrace();
		}
		return false;
	}

	private boolean isUsedFloor(int floor) {
		try {
			String sql = "SELECT 1 FROM ROOM WHERE FLOOR_ID=?";
			Map htData = SQLUtil.getFirstRecord(DBConnection.getConnection(),
					floor, true, sql);
			if (htData != null) {
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	public boolean delete(int floor) {
		try {
			if(!isUsedFloor(floor)){
				Map<String, Object> htParam = new HashMap<String, Object>();
				htParam.put("FLOOR", floor);
	
				SQLUtil.deleteSQL(DBConnection.getConnection(), "FLOOR", htParam);
				
				DBConnection.getConnection().commit();
				// 设置系统被修改标志
				SystemConfig.getInstance().setSysChanged(true);
				
				return true;
			}
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
