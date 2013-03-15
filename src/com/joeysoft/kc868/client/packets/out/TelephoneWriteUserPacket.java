package com.joeysoft.kc868.client.packets.out;

import com.joeysoft.kc868.SystemConfig;
import com.joeysoft.kc868.client.packets.OutPacket;
import com.joeysoft.kc868.client.support.Protocol;
import com.joeysoft.kc868.common.GlobalConst;

public class TelephoneWriteUserPacket extends OutPacket{

	private int telId;
	
	private String telphone;
	
	private String countryCode;
	
	public TelephoneWriteUserPacket() {
		super(Protocol.CMD_TELEPHONE_WRITE_USER, Protocol.MSG_TELEPHONE_WRITE_USER);
	}

	@Override
	protected void putBody() {
		sbMessage.append(telId);
		sbMessage.append(GlobalConst.CONST_STRING_COMMA);
		sbMessage.append(countryCode);
		sbMessage.append(telphone);
	}

	public int getTelId() {
		return telId;
	}

	public void setTelId(int telId) {
		this.telId = telId;
	}

	public String getTelphone() {
		return telphone;
	}

	public void setTelphone(String telphone) {
		this.telphone = telphone;
	}

	public String getCountryCode() {
		return countryCode;
	}

	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}
}
