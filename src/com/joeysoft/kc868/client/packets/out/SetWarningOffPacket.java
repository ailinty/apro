package com.joeysoft.kc868.client.packets.out;

import com.joeysoft.kc868.client.packets.OutPacket;
import com.joeysoft.kc868.client.support.Protocol;

public class SetWarningOffPacket extends OutPacket{

	public SetWarningOffPacket() {
		super(Protocol.CMD_EVENT_SET_WARNING, Protocol.MSG_EVENT_SET_WARNING_OFF);
	}

	@Override
	protected void putBody() {
	}
}
