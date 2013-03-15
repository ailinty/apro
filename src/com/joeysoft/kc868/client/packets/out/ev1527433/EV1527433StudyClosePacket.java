package com.joeysoft.kc868.client.packets.out.ev1527433;

import com.joeysoft.kc868.client.packets.OutPacket;
import com.joeysoft.kc868.client.support.Protocol;
import com.joeysoft.kc868.common.GlobalConst;

public class EV1527433StudyClosePacket extends OutPacket{

	public EV1527433StudyClosePacket() {
		super(Protocol.CMD_EV1527_433M_STUDY_CLOSE, Protocol.MSG_EV1527_433M_STUDY_CLOSE);
	}

	@Override
	protected void putBody() {
	}
}
