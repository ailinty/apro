package com.joeysoft.kc868.client.packets.out.infrared;

import com.joeysoft.kc868.client.packets.OutPacket;
import com.joeysoft.kc868.client.support.Protocol;
import com.joeysoft.kc868.common.GlobalConst;

public class InfraredStudyClosePacket extends OutPacket{

	public InfraredStudyClosePacket() {
		super(Protocol.CMD_INFRARED_STUDY_CLOSE, Protocol.MSG_INFRARED_STUDY_CLOSE);
	}

	@Override
	protected void putBody() {
	}
}
