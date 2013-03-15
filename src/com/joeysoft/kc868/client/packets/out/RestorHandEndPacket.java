package com.joeysoft.kc868.client.packets.out;

import com.joeysoft.kc868.client.packets.OutPacket;
import com.joeysoft.kc868.client.support.Protocol;

public class RestorHandEndPacket extends OutPacket{
	
	public RestorHandEndPacket() {
		super(Protocol.CMD_RESTOR_HAND_END, Protocol.MSG_RESTOR_HAND_END);
	}

	@Override
	protected void putBody() {
		
	}
}
