package com.joeysoft.kc868.client.packets.out.lineate;

import com.joeysoft.kc868.client.packets.OutPacket;
import com.joeysoft.kc868.client.support.Protocol;

public class RelayReadNowPacket extends OutPacket{
	public RelayReadNowPacket() {
		super(Protocol.CMD_RELAY_READ_NOW, Protocol.MSG_RELAY_READ_NOW);
	}

	@Override
	protected void putBody() {
	}
}
