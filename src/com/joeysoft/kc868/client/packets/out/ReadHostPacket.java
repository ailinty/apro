package com.joeysoft.kc868.client.packets.out;

import com.joeysoft.kc868.client.packets.OutPacket;
import com.joeysoft.kc868.client.support.Protocol;

public class ReadHostPacket extends OutPacket{

	public ReadHostPacket() {
		super(Protocol.CMD_READ_HOST, Protocol.MSG_READ_HOST);
	}

	@Override
	protected void putBody() {
	}

}
