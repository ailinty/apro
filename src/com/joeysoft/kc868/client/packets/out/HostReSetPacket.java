package com.joeysoft.kc868.client.packets.out;

import com.joeysoft.kc868.client.packets.OutPacket;
import com.joeysoft.kc868.client.support.Protocol;

public class HostReSetPacket extends OutPacket{

	private String params;
	
	public HostReSetPacket() {
		super(Protocol.CMD_HOST_SET_RESET, Protocol.MSG_HOST_SET_RESET);
	}

	@Override
	protected void putBody() {
		sbMessage.append(params);
	}

	public String getParams() {
		return params;
	}

	public void setParams(String params) {
		this.params = params;
	}
}
