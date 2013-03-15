package com.joeysoft.kc868.ui.dialogs.helper;

import static com.joeysoft.kc868.resource.Messages.*;
import org.eclipse.swt.widgets.Table;

import com.joeysoft.kc868.db.bean.Relay;
import com.joeysoft.kc868.db.bean.Threshold;
import com.joeysoft.kc868.db.bo.BORelay;
import com.joeysoft.kc868.db.bo.BOThreshold;
import com.joeysoft.kc868.db.bo.BOUnused;
import com.joeysoft.kc868.resource.Messages;

/**
 * 继电器UI帮助类
 * @author JOEY
 *
 */
public class UIRelayHelper {
	private Table tbRelay;
	private BORelay boRelay;
	private BOThreshold boThreshold;
	
	public UIRelayHelper(Table tbRelay){
		this.tbRelay = tbRelay;
		boRelay = new BORelay();
		boThreshold = new BOThreshold();
	}
	
	/**
	 * 初始化继电器
	 */
	public void initRelay(){
		int count = boRelay.getCount();
		Threshold threshold = boThreshold.get("RELAY");
		if(threshold.getThreshold() > count){ // 记录没有到阀值，添加
			for(int i=count; i<threshold.getThreshold(); i++){
				Relay relay = new Relay();
				
				//relay.setRelayId(null);
				relay.setRelayName(Messages.relay_out + (i + 1));
				relay.setRelayOffName(mb_on);
				relay.setRelayOnName(mb_off);
				relay.setRelayStatus("N");
				
				boRelay.insert(relay);
			}
		}
		
	}
}
