package com.joeysoft.kc868.client.packets.out;

import com.joeysoft.kc868.client.packets.OutPacket;
import com.joeysoft.kc868.client.support.Protocol;

public class ResetHostPacket extends OutPacket{

	public ResetHostPacket() {
		super(Protocol.CMD_RESET_HOST, Protocol.MSG_RESET_HOST);
	}

	@Override
	protected void putBody() {
	}

}
