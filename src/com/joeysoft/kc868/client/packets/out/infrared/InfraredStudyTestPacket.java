package com.joeysoft.kc868.client.packets.out.infrared;

import com.joeysoft.kc868.client.packets.OutPacket;
import com.joeysoft.kc868.client.support.Protocol;
import com.joeysoft.kc868.common.GlobalConst;

public class InfraredStudyTestPacket extends OutPacket{

	public InfraredStudyTestPacket() {
		super(Protocol.CMD_INFRARED_STUDY_TEST, Protocol.MSG_INFRARED_STUDY_TEST);
	}

	@Override
	protected void putBody() {
	}
}
