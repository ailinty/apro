package com.joeysoft.kc868.client.packets.out;

import com.joeysoft.kc868.client.packets.OutPacket;
import com.joeysoft.kc868.client.support.Protocol;
import com.joeysoft.kc868.common.GlobalConst;

public class SetIPPacket extends OutPacket{

	private String ip;
	private String mask;
	private String gate;
	
	public SetIPPacket() {
		super(Protocol.CMD_SET_IP, Protocol.MSG_SET_IP);
	}

	@Override
	protected void putBody() {
		sbMessage.append(ip);
		sbMessage.append(GlobalConst.CONST_STRING_COMMA);
		sbMessage.append(mask);
		sbMessage.append(GlobalConst.CONST_STRING_COMMA);
		sbMessage.append(gate);
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getMask() {
		return mask;
	}

	public void setMask(String mask) {
		this.mask = mask;
	}

	public String getGate() {
		return gate;
	}

	public void setGate(String gate) {
		this.gate = gate;
	}
}
