package com.joeysoft.kc868.db.util;

import org.apache.commons.lang.StringUtils;

import com.joeysoft.kc868.db.bean.Unused;

/**
 * 表主键编码帮助类
 * @author JOEY
 *
 */
public class UnusedUtil {
	/**
	 * 表的主键
	 * @return
	 */
	public static Unused getNextUnused(Unused unused){
		if(unused.isNew()){
			unused.setNew(true);
			unused.setMaxUnused(1);
		}
		Unused next = unused;
		String unusedStr = unused.getUnused();
		if(StringUtils.isEmpty(unusedStr)){
			next.setUnused("");
			next.setNextUnused(unused.getMaxUnused());
			next.setMaxUnused(unused.getMaxUnused() + 1);
		}else{
			String unsedS[] = unusedStr.split(",");
			if(unsedS.length == 1){
				next.setUnused("");
			}else{
				next.setUnused(unusedStr.substring(unsedS[0].length() + 1));
			}
			
			next.setNextUnused(Integer.valueOf(unsedS[0]));
			next.setMaxUnused(unused.getMaxUnused());
		}
		
		return next;
	}
}
