package com.joeysoft.kc868.db.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.map.LinkedMap;

import com.joeysoft.kc868.db.bean.Dict;
import com.joeysoft.kc868.db.bo.BODict;

/**
 * 数据字典管理类
 * @author JOEY
 *
 */
public final class DictManager {
	private Map<String, Map<String, Dict>> dictMap = new HashMap<String, Map<String, Dict>>();
	
	// 单一实例
    private static DictManager dictManager = new DictManager();
    private BODict boDict = new BODict();
    
    private DictManager(){
    	
    }
    
    public static DictManager getInstance(){
    	return dictManager;
    }
    
    /**
     * 初始化数据
     * @param dictList
     */
    public void init(){
    	List<Dict> dictList = boDict.getList();
    	
    	for(Dict dict : dictList){
    		Map<String, Dict> codeMp = null;
    		if(dictMap.containsKey(dict.getDictCode())){
    			codeMp = dictMap.get(dict.getDictCode());
    		}else{
    			codeMp =  new LinkedHashMap<String, Dict>();
    			dictMap.put(dict.getDictCode(), codeMp);
    		}
    		codeMp.put(dict.getDictValue(), dict);
    	}
    }
    
    /**
     * 获取Code的list<dict>
     * @param dictCode
     * @return
     */
    public List<Dict> getDictList(String dictCode){
    	Map<String, Dict> codeMp = dictMap.get(dictCode);
    	if(codeMp != null && !codeMp.isEmpty()){
    		return new ArrayList<Dict>(codeMp.values());
    	}
    	return new ArrayList<Dict>();
    }
    
    /**
     * 获取code value 的name
     * @param dictCode
     * @param dictValue
     * @return
     */
    public String getDictName(String dictCode, String dictValue){
    	Map<String, Dict> codeMp = dictMap.get(dictCode);
    	if(codeMp != null && !codeMp.isEmpty()){
    		Dict dict = codeMp.get(dictValue);
    		if(dict != null) return dict.getDictName();
    		
    	}
    	return dictValue;
    }
}
