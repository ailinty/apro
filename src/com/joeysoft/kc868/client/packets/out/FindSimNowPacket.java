package com.joeysoft.kc868.client.packets.out;

import com.joeysoft.kc868.client.packets.OutPacket;
import com.joeysoft.kc868.client.support.Protocol;

public class FindSimNowPacket extends OutPacket{
	
	public FindSimNowPacket() {
		super(Protocol.CMD_FIND_SIM_NOW, Protocol.MSG_FIND_SIM_NOW);
	}

	@Override
	protected void putBody() {
	}
}
