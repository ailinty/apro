package com.joeysoft.kc868.client.packets.out;

import com.joeysoft.kc868.client.packets.OutPacket;
import com.joeysoft.kc868.client.support.Protocol;

public class DebugPacket extends OutPacket{

	public DebugPacket(String message){
		super(Protocol.CMD_UNKNOWN, message);
	}

	@Override
	protected void putBody() {
		
	}
}
