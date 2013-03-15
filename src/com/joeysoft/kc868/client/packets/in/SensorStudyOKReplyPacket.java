package com.joeysoft.kc868.client.packets.in;

import com.joeysoft.kc868.client.packets.InPacket;
import com.joeysoft.kc868.client.packets.PacketParseException;
import com.joeysoft.kc868.client.support.Protocol;
import com.joeysoft.kc868.common.GlobalConst;

public abstract class SensorStudyOKReplyPacket extends InPacket{

	protected int load;
	protected String resType;
	protected int addrCode;
	protected int dataCode;
	
	public SensorStudyOKReplyPacket(char command, String message) throws PacketParseException{
		super(command, message);
	}
	
	public int getLoad() {
		return load;
	}

	public void setLoad(int load) {
		this.load = load;
	}

	public String getResType() {
		return resType;
	}

	public void setResType(String resType) {
		this.resType = resType;
	}

	public int getAddrCode() {
		return addrCode;
	}

	public void setAddrCode(int addrCode) {
		this.addrCode = addrCode;
	}

	public int getDataCode() {
		return dataCode;
	}

	public void setDataCode(int dataCode) {
		this.dataCode = dataCode;
	}
}