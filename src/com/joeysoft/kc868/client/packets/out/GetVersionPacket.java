package com.joeysoft.kc868.client.packets.out;

import com.joeysoft.kc868.client.packets.OutPacket;
import com.joeysoft.kc868.client.support.Protocol;

public class GetVersionPacket extends OutPacket{

	public GetVersionPacket(){
		super(Protocol.CMD_GET_VERSION, Protocol.MSG_GET_VERSION);
	}

	@Override
	protected void putBody() {
		
	}

}
