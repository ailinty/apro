package com.joeysoft.kc868.db.bo;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.joeysoft.kc868.SystemConfig;
import com.joeysoft.kc868.db.DBConnection;
import com.joeysoft.kc868.db.SQLUtil;
import com.joeysoft.kc868.db.bean.Room;

public class BORoom {

	public List<Room> getList() {
		List<Room> list = new ArrayList<Room>();
		try {
			String sql = "SELECT ROOM.*, FLOOR.FLOOR_NAME FROM ROOM LEFT OUTER JOIN FLOOR ON ROOM.FLOOR = FLOOR.FLOOR";
			List<Map> lt = (List<Map>) SQLUtil.selectSQLInList(
					DBConnection.getConnection(), sql);
			for (Map mp : lt) {
				Room room = new Room(SQLUtil.returnInt(mp, "FLOOR"),
						SQLUtil.returnInt(mp, "ROOM_ID"), SQLUtil.returnStr(mp,
								"ROOM_NAME"));
				room.setFloorName(SQLUtil.returnStr(mp, "FLOOR_NAME"));
				list.add(room);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	/**
	 * 
	 * @param floor
	 * @return
	 */
	public List<Room> getListByFloor(int floor) {
		List<Room> list = new ArrayList<Room>();
		try {
			String sql = "SELECT ROOM.*, FLOOR.FLOOR_NAME FROM ROOM LEFT OUTER JOIN FLOOR ON ROOM.FLOOR = FLOOR.FLOOR WHERE FLOOR.FLOOR=?";
			List<Map> lt = (List<Map>) SQLUtil.selectSQLInList(
					DBConnection.getConnection(), floor, sql);
			for (Map mp : lt) {
				Room room = new Room(SQLUtil.returnInt(mp, "FLOOR"),
						SQLUtil.returnInt(mp, "ROOM_ID"), SQLUtil.returnStr(mp,
								"ROOM_NAME"));
				room.setFloorName(SQLUtil.returnStr(mp, "FLOOR_NAME"));
				list.add(room);
			}

		} catch (Exception e) {
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
	public Room get(int roomId) {
		Room rm = new Room();
		try {
			String sql = "SELECT * FROM ROOM WHERE ROOM_ID=?";
			Map htData = SQLUtil.getFirstRecord(DBConnection.getConnection(),
					roomId, true, sql);
			if (htData != null) {
				rm.setRoomId(roomId);
				rm.setFloor(SQLUtil.returnInt(htData, "ROOM_ID"));
				rm.setRoomName(SQLUtil.returnStr(htData, "ROOM_NAME"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return rm;
	}

	public int getMax() {
		try {
			Map htData = SQLUtil.getFirstRecord(DBConnection.getConnection(),
					null, true, "SELECT MAX(ROOM_ID) AS MAX FROM ROOM");
			if (htData != null) {
				return SQLUtil.returnInt(htData, "MAX");
			}
			return -1;
		} catch (Exception e) {
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
	public boolean insert(Room room) {
		try {
			SQLUtil.insertSQL(DBConnection.getConnection(), "ROOM",
					room.fillMap());
			
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

	public boolean update(Room room) {
		try {
			Map<String, Object> htUpdateParam = new HashMap<String, Object>();
			htUpdateParam.put("ROOM_NAME", room.getRoomName());
			htUpdateParam.put("FLOOR", room.getFloor());
			Map<String, Object> htKeyParam = new HashMap<String, Object>();
			htKeyParam.put("ROOM_ID", room.getRoomId());

			SQLUtil.updateSQL(DBConnection.getConnection(), "ROOM",
					htUpdateParam, htKeyParam);
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
	 * 当前房间是否被使用了
	 * 
	 * @return
	 */
	private boolean isUsedRoom(int roomId) {
		try {
			String sql = "SELECT 1 FROM DEVICE WHERE ROOM_ID=?";
			Map htData = SQLUtil.getFirstRecord(DBConnection.getConnection(),
					roomId, true, sql);
			if (htData != null && !htData.isEmpty()) {
				return true;
			}
			sql = "SELECT 1 FROM LINEATE WHERE ROOM_ID=?";
			htData = SQLUtil.getFirstRecord(DBConnection.getConnection(),
					roomId, true, sql);
			if (htData != null && !htData.isEmpty()) {
				return true;
			}
			sql = "SELECT 1 FROM RELAY WHERE ROOM_ID=?";
			htData = SQLUtil.getFirstRecord(DBConnection.getConnection(),
					roomId, true, sql);
			if (htData != null && !htData.isEmpty()) {
				return true;
			}
			sql = "SELECT 1 FROM SENSOR_IN WHERE ROOM_ID=?";
			htData = SQLUtil.getFirstRecord(DBConnection.getConnection(),
					roomId, true, sql);
			if (htData != null && !htData.isEmpty()) {
				return true;
			}
			sql = "SELECT 1 FROM SENSOR_NOR WHERE ROOM_ID=?";
			htData = SQLUtil.getFirstRecord(DBConnection.getConnection(),
					roomId, true, sql);
			if (htData != null && !htData.isEmpty()) {
				return true;
			}
			sql = "SELECT 1 FROM SENSOR WHERE ROOM_ID=?";
			htData = SQLUtil.getFirstRecord(DBConnection.getConnection(),
					roomId, true, sql);
			if (htData != null && !htData.isEmpty()) {
				return true;
			}
			sql = "SELECT 1 FROM TEMP_SENSOR WHERE ROOM_ID=?";
			htData = SQLUtil.getFirstRecord(DBConnection.getConnection(),
					roomId, true, sql);
			if (htData != null && !htData.isEmpty()) {
				return true;
			}
			sql = "SELECT 1 FROM TRANSFER WHERE ROOM_ID=?";
			htData = SQLUtil.getFirstRecord(DBConnection.getConnection(),
					roomId, true, sql);
			if (htData != null && !htData.isEmpty()) {
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	public boolean delete(int roomId) {
		try {
			if(!isUsedRoom(roomId)){
				Map<String, Object> htParam = new HashMap<String, Object>();
				htParam.put("ROOM_ID", roomId);

				SQLUtil.deleteSQL(DBConnection.getConnection(), "ROOM", htParam);
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
