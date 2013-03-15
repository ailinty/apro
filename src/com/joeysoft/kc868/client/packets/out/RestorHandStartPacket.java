package com.joeysoft.kc868.client.packets.out;

import com.joeysoft.kc868.client.packets.OutPacket;
import com.joeysoft.kc868.client.support.Protocol;

public class RestorHandStartPacket extends OutPacket{
	
	public RestorHandStartPacket() {
		super(Protocol.CMD_RESTOR_HAND_START, Protocol.MSG_RESTOR_HAND_START);
	}

	@Override
	protected void putBody() {
		
	}
}
