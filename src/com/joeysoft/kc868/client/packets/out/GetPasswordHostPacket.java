package com.joeysoft.kc868.client.packets.out;

import com.joeysoft.kc868.client.packets.OutPacket;
import com.joeysoft.kc868.client.support.Protocol;

public class GetPasswordHostPacket extends OutPacket{

	public GetPasswordHostPacket(){
		super(Protocol.CMD_GET_PASSWORD, Protocol.MSG_GET_PASSWORD_HOST);
	}

	@Override
	protected void putBody() {
		
	}

}
