package com.joeysoft.kc868.client.packets.out;


import com.joeysoft.kc868.client.packets.OutPacket;
import com.joeysoft.kc868.client.support.Protocol;
import com.joeysoft.kc868.client.util.Util;
import com.joeysoft.kc868.common.GlobalConst;
import com.joeysoft.kc868.common.StringUtil;

public class GSMWriteSmsPacket extends OutPacket{

	private int smsId;
	
	private String smsContent;
	
	public GSMWriteSmsPacket() {
		super(Protocol.CMD_GSM_WRITE_SMS, Protocol.MSG_GSM_WRITE_SMS);
	}

	@Override
	protected void putBody() {
		sbMessage.append(smsId);
		sbMessage.append(GlobalConst.CONST_STRING_COMMA);
		sbMessage.append(Util.toEncodedUnicode(smsContent));
	}

	public int getSmsId() {
		return smsId;
	}

	public void setSmsId(int smsId) {
		this.smsId = smsId;
	}

	public String getSmsContent() {
		return smsContent;
	}

	public void setSmsContent(String smsContent) {
		this.smsContent = smsContent;
	}

}
