package com.joeysoft.kc868.client.packets.out;

import com.joeysoft.kc868.client.packets.OutPacket;
import com.joeysoft.kc868.client.support.Protocol;

public class ReadIPPacket extends OutPacket{

	public ReadIPPacket() {
		super(Protocol.CMD_READ_IP, Protocol.MSG_READ_IP);
	}

	@Override
	protected void putBody() {
	}

}
