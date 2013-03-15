package com.joeysoft.kc868.client.packets.out.infrared;

import com.joeysoft.kc868.client.packets.OutPacket;
import com.joeysoft.kc868.client.support.Protocol;
import com.joeysoft.kc868.common.GlobalConst;

public class InfraredWritePacket extends OutPacket{
	
	public InfraredWritePacket() {
		super(Protocol.CMD_INFRARED_STUDY_WRITE, Protocol.MSG_INFRARED_STUDY_WRITE);
	}

	@Override
	protected void putBody() {
	}
}
