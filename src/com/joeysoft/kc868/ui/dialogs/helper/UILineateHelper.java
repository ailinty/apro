package com.joeysoft.kc868.ui.dialogs.helper;

import static com.joeysoft.kc868.resource.Messages.*;
import org.eclipse.swt.widgets.Table;

import com.joeysoft.kc868.db.bean.Lineate;
import com.joeysoft.kc868.db.bean.Threshold;
import com.joeysoft.kc868.db.bo.BOLineate;
import com.joeysoft.kc868.db.bo.BOThreshold;
import com.joeysoft.kc868.resource.Messages;

/**
 * 有线电UI帮助类
 * @author JOEY
 *
 */
public class UILineateHelper {
	private Table tbLineate;
	private BOLineate boLineate;
	private BOThreshold boThreshold;
	
	public UILineateHelper(Table tbLineate){
		this.tbLineate = tbLineate;
		boLineate = new BOLineate();
		boThreshold = new BOThreshold();
	}
	
	/**
	 * 初始化有线电
	 */
	public void initLineate(){
		int count = boLineate.getCount();
		Threshold threshold = boThreshold.get("LINEATE");
		if(threshold.getThreshold() > count){ // 记录没有到阀值，添加
			for(int i=count; i<threshold.getThreshold(); i++){
				Lineate lineate = new Lineate();
				
				//lineate.setLineateId(null);
				lineate.setLineateName(Messages.lineate + (i + 1));
				lineate.setLineateType("1");
				lineate.setLineateUD("1");
				lineate.setLineateGL("");
				lineate.setLineateV("");
				
				boLineate.insert(lineate);
			}
		}
		
	}
}
