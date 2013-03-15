package com.joeysoft.kc868.client.packets.out;

import com.joeysoft.kc868.client.packets.OutPacket;
import com.joeysoft.kc868.client.support.Protocol;

public class GetPasswordAdminPacket extends OutPacket{

	public GetPasswordAdminPacket(){
		super(Protocol.CMD_GET_PASSWORD, Protocol.MSG_GET_PASSWORD_ADMIN);
	}

	@Override
	protected void putBody() {
		
	}

}
