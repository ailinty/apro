package com.joeysoft.kc868.client.packets.out.lineate;

import com.joeysoft.kc868.client.packets.OutPacket;
import com.joeysoft.kc868.client.support.Protocol;
import com.joeysoft.kc868.common.GlobalConst;

public class SpeakSetSpeakOnPacket extends OutPacket{
	
	public SpeakSetSpeakOnPacket() {
		super(Protocol.CMD_SPEAK_SET_SPEAK, Protocol.MSG_SPEAK_SET_SPEAK_ON);
	}

	@Override
	protected void putBody() {
	}
}
