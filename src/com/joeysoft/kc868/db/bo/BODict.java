package com.joeysoft.kc868.db.bo;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.joeysoft.kc868.db.DBConnection;
import com.joeysoft.kc868.db.SQLUtil;
import com.joeysoft.kc868.db.bean.Dict;

/**
 * 数据字典
 * @author JOEY
 *
 */
public class BODict {
	
	public List<Dict> getList(){
		List<Dict> list = new ArrayList<Dict>();
		try {
			String sql = "SELECT * FROM DICT";
			List<Map> lt = (List<Map>)SQLUtil.selectSQLInList(DBConnection.getConnection(), sql);
			for(Map mp : lt){
				Dict dict = new Dict(SQLUtil.returnInt(mp, "DICT_ID"), SQLUtil.returnStr(mp, "DICT_CODE")
						, SQLUtil.returnStr(mp, "DICT_NAME"), SQLUtil.returnStr(mp, "DICT_VALUE"));
				list.add(dict);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	
	/**
	 * 得到Dict
	 * @param DBConnection.getConnection()
	 * @param dict
	 * @return
	 */
	public List<Dict> getList(String dictCode){
		List<Dict> list = new ArrayList<Dict>();
		try {
			String sql = "SELECT * FROM DICT WHERE DICT_CODE=?";
			List<Map> lt = (List<Map>)SQLUtil.selectSQLInList(DBConnection.getConnection(), dictCode, sql);
			for(Map mp : lt){
				Dict dict = new Dict(SQLUtil.returnInt(mp, "DICT_ID"), SQLUtil.returnStr(mp, "DICT_CODE")
						, SQLUtil.returnStr(mp, "DICT_NAME"), SQLUtil.returnStr(mp, "DICT_VALUE"));
				list.add(dict);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	
	/**
	 * 插入楼层
	 * @param DBConnection.getConnection()
	 * @param dictName
	 * @return
	 */
	public boolean insert(Dict dict){
		try {
			SQLUtil.insertSQL(DBConnection.getConnection(), "DICT", dict.fillMap());
			DBConnection.getConnection().commit();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public boolean update(Dict dict){
		try {
			Map<String, Object> htUpdateParam = new HashMap<String, Object>();
			htUpdateParam.put("DICT_NAME", dict.getDictName());
			htUpdateParam.put("DICT_VALUE", dict.getDictValue());
			Map<String, Object> htKeyParam = new HashMap<String, Object>();
			htKeyParam.put("DICT_ID", dict.getDictId());
			
			SQLUtil.updateSQL(DBConnection.getConnection(), "DICT", htUpdateParam, htKeyParam);
			DBConnection.getConnection().commit();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public boolean delete(Integer dictId){
		try {
			Map<String, Object> htParam = new HashMap<String, Object>();
			htParam.put("DICT_ID", dictId);
			
			SQLUtil.deleteSQL(DBConnection.getConnection(), "DICT", htParam);
			DBConnection.getConnection().commit();
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
