package com.joeysoft.kc868.client.packets.out;

import com.joeysoft.kc868.client.packets.OutPacket;
import com.joeysoft.kc868.client.support.Protocol;

public class SetVersionPacket extends OutPacket{

	private String version;
	
	public SetVersionPacket() {
		super(Protocol.CMD_SET_VERSION, Protocol.MSG_SET_VERSION);
	}

	@Override
	protected void putBody() {
		sbMessage.append(version);
		sbMessage.append("-");
	}

	public void setVersion(String version) {
		this.version = version;
	}
}