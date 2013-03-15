package com.joeysoft.kc868.client.packets.out;

import com.joeysoft.kc868.client.packets.OutPacket;
import com.joeysoft.kc868.client.support.Protocol;

public class FindSignalNowPacket extends OutPacket{
	
	public FindSignalNowPacket() {
		super(Protocol.CMD_FIND_SIGNAL_NOW, Protocol.MSG_FIND_SIGNAL_NOW);
	}

	@Override
	protected void putBody() {
	}
}
