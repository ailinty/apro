package com.joeysoft.kc868.db.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.joeysoft.kc868.db.bean.SensorNor;
import com.joeysoft.kc868.db.bean.SensorOut;
import com.joeysoft.kc868.db.bean.Transfer;
import com.joeysoft.kc868.db.bean.TsfSensor;
import com.joeysoft.kc868.db.bo.BOSensorNor;
import com.joeysoft.kc868.db.bo.BOSensorOut;
import com.joeysoft.kc868.db.bo.BOTransfer;
import com.joeysoft.kc868.db.bo.BOTransferSensor;


public class DataAddrCodeManager {
	//          电阻             地址码                   数据码         对象
	private Map<String, Map<Integer, ArrayList<Integer>>> resDataAddrCodeMap = 
			new HashMap<String, Map<Integer, ArrayList<Integer>>>();
	
	// 单一实例
	private static DataAddrCodeManager dataAddrCodeManager = new DataAddrCodeManager();
	
	private BOTransferSensor boTransferSensor =  new BOTransferSensor();
	private BOSensorOut boSensorOut =  new BOSensorOut();
	private BOSensorNor boensorNor =  new BOSensorNor();
	
	private DataAddrCodeManager(){
    	
    }
    
    public static DataAddrCodeManager getInstance(){
    	return dataAddrCodeManager;
    }
    
    /**
     * 初始化数据
     * @param list
     */
    public void init(){
    	
		// 红外转发器
		for(TsfSensor tsfSensor : boTransferSensor.getList()){
			addDataAddrCode(tsfSensor.getResType(), tsfSensor.getAddrCode(), tsfSensor.getDataCode());
		}
		
		// 非常规类
		for(SensorOut sensorOut : boSensorOut.getList()){
			addDataAddrCode(sensorOut.getResType(), sensorOut.getAddrCode(), sensorOut.getDataCode());
		}
		
		// 电灯类
		for(SensorNor sensorNor : boensorNor.getList()){
			addDataAddrCode(sensorNor.getResType(), sensorNor.getAddrCode(), sensorNor.getDataCode());
		}
    }
    
    /**
     * 是否已经存在电阻、地址码，如果存在表示已经被使用
     * @param resType
     * @param addrCode
     * @return
     */
    public boolean isContains(String resType, int addrCode){
    	if(resDataAddrCodeMap.containsKey(resType)){
    		resDataAddrCodeMap.get(resType);
    		Map<Integer, ArrayList<Integer>> dataAddrCodeMap = resDataAddrCodeMap.get(resType);
    		if(dataAddrCodeMap.containsKey(addrCode)){
    			return true;
    		}
    	}
    	
    	return false;
    }
    
    /**
     * 判断电阻、地址码的数据码已经都被使用完了
     * @param resType
     * @param addrCode
     * @return
     */
    public boolean isFull(String resType, int addrCode){
    	if(resDataAddrCodeMap.containsKey(resType)){
    		Map<Integer, ArrayList<Integer>> dataAddrCodeMap = resDataAddrCodeMap.get(resType);
    		if(dataAddrCodeMap.containsKey(addrCode)){
    			ArrayList<Integer> dataList = dataAddrCodeMap.get(addrCode);
    			if(dataList.size() >= 16){
    				return true;
    			}
        	}
    	}
    	
    	return false;
    }
    
    /**
     * 是否已经存在电阻、地址码、数据码
     * @param resType 电阻
     * @param dataCode
     * @param addrCode
     */
    public boolean isContains(String resType, int addrCode, int dataCode){
    	if(resDataAddrCodeMap.containsKey(resType)){
    		Map<Integer, ArrayList<Integer>> dataAddrCodeMap = resDataAddrCodeMap.get(resType);
    		if(dataAddrCodeMap.containsKey(addrCode)){
    			ArrayList<Integer> dataList = dataAddrCodeMap.get(addrCode);
        		if(dataList.contains(dataCode)){
        			return true;
        		}
        	}
    	}
    	
    	return false;
    }
    
    /**
     * 返回数据码列表
     * @param resType
     * @param addrCode
     * @return
     */
    public ArrayList<Integer> getDataList(String resType, int addrCode){
    	if(resDataAddrCodeMap.containsKey(resType)){
    		Map<Integer, ArrayList<Integer>> dataAddrCodeMap = resDataAddrCodeMap.get(resType);
    		if(dataAddrCodeMap.containsKey(addrCode)){
    			return dataAddrCodeMap.get(addrCode);
        	}
    	}
    	return null;
    }
    
    /**
     * 添加电阻、地址码、数据码到缓存中
     * @param resType
     * @param addrCode
     * @param dataCode
     */
    public void addDataAddrCode(String resType, int addrCode, int dataCode){
    	Map<Integer, ArrayList<Integer>> dataAddrCodeMap = null;
    	if(resDataAddrCodeMap.containsKey(resType)){
    		dataAddrCodeMap = resDataAddrCodeMap.get(resType);
    	}else{
    		dataAddrCodeMap = new HashMap<Integer, ArrayList<Integer>>();
    		resDataAddrCodeMap.put(resType, dataAddrCodeMap);
    	}
    	
    	ArrayList<Integer> dataList = null;
    	if(dataAddrCodeMap.containsKey(addrCode)){
    		dataList = dataAddrCodeMap.get(addrCode);
    	}else{
    		dataList =  new ArrayList<Integer>();
    		dataAddrCodeMap.put(addrCode, dataList);
    	}
    	
    	dataList.add(dataCode);
    }
    
    /**
     * 移除电阻、数据码、地址码
     * @param resType
     * @param addrCode
     * @param dataCode
     */
    public void removeDataAddrCode(String resType, int addrCode, int dataCode){
    	Map<Integer, ArrayList<Integer>> dataAddrCodeMap = null;
    	if(resDataAddrCodeMap.containsKey(resType)){
    		dataAddrCodeMap = resDataAddrCodeMap.get(resType);
    		
    		ArrayList<Integer> dataList = null;
        	if(dataAddrCodeMap.containsKey(addrCode)){
        		dataList = dataAddrCodeMap.get(addrCode);
        		dataList.remove((Integer)dataCode);
        		
        		if(dataList.isEmpty()) {
        			dataAddrCodeMap.remove(addrCode);
        			dataList = null;
        		}
        	}
    	}
    }
    
    /**
     * 清除所有
     */
    public void removeAll(){
    	resDataAddrCodeMap = null;
    	resDataAddrCodeMap = new HashMap<String, Map<Integer, ArrayList<Integer>>>();
    }
}
