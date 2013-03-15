package com.joeysoft.kc868.ui.dialogs.helper;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

import com.joeysoft.kc868.SystemConfig;
import com.joeysoft.kc868.db.bean.SmsIn;
import com.joeysoft.kc868.db.bean.SmsOut;
import com.joeysoft.kc868.db.bean.TelIn;
import com.joeysoft.kc868.db.bean.TelOut;
import com.joeysoft.kc868.db.bean.Threshold;
import com.joeysoft.kc868.db.bo.BOSmsIn;
import com.joeysoft.kc868.db.bo.BOSmsOut;
import com.joeysoft.kc868.db.bo.BOTelIn;
import com.joeysoft.kc868.db.bo.BOTelOut;
import com.joeysoft.kc868.db.bo.BOThreshold;
import com.joeysoft.kc868.ui.Colors;
import com.joeysoft.kc868.ui.helper.UITool;

public class UIGSMHelper {
	private BOThreshold boThreshold;
	private BOTelIn boTelIn;
	private BOTelOut boTelOut;
	private BOSmsIn boSmsIn;
	private BOSmsOut boSmsOut;

	public UIGSMHelper() {
		boThreshold = new BOThreshold();
		boTelIn = new BOTelIn();
		boTelOut = new BOTelOut();
		boSmsIn = new BOSmsIn();
		boSmsOut = new BOSmsOut();
	}

	/**
	 * 初始化GSM
	 */
	public void initGSM() {
		int count = boTelIn.getCount();
		
		Threshold threshold = boThreshold.get("TEL_IN");
		int thresholdCount = threshold.getThreshold();
		if(SystemConfig.getInstance().isHardSoftVer2030() && thresholdCount > 8){
			thresholdCount = 8;
		}
		if (thresholdCount > count) { // 记录没有到阀值，添加
			for (int i = count; i < thresholdCount; i++) {
				TelIn telIn = new TelIn();
				boTelIn.insert(telIn);
			}
		}

		count = boTelOut.getCount();
		if(SystemConfig.getInstance().isHardSoftVer2030() && count > 8){
			count = 8;
		}
		threshold = boThreshold.get("TEL_OUT");
		thresholdCount = threshold.getThreshold();
		if(SystemConfig.getInstance().isHardSoftVer2030() && thresholdCount > 8){
			thresholdCount = 8;
		}
		if (thresholdCount > count) { // 记录没有到阀值，添加
			for (int i = count; i < thresholdCount; i++) {
				TelOut telOut = new TelOut();
				boTelOut.insert(telOut);
			}
		}

		count = boSmsIn.getCount();
		threshold = boThreshold.get("SMS_IN");
		thresholdCount = threshold.getThreshold();
		if(SystemConfig.getInstance().isHardSoftVer2030() && thresholdCount > 8){
			thresholdCount = 8;
		}
		if (thresholdCount > count) { // 记录没有到阀值，添加
			for (int i = count; i < thresholdCount; i++) {
				SmsIn smsIn = new SmsIn();
				boSmsIn.insert(smsIn);
			}
		}

		count = boSmsOut.getCount();
		threshold = boThreshold.get("SMS_OUT");
		thresholdCount = threshold.getThreshold();
		if(SystemConfig.getInstance().isHardSoftVer2030() && thresholdCount > 8){
			thresholdCount = 8;
		}
		if (thresholdCount > count) { // 记录没有到阀值，添加
			for (int i = count; i < thresholdCount; i++) {
				SmsOut smsOut = new SmsOut();
				boSmsOut.insert(smsOut);
			}
		}
	}
}
