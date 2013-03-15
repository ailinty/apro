package com.joeysoft.kc868.client.packets.out.ev1527315;

import com.joeysoft.kc868.client.packets.OutPacket;
import com.joeysoft.kc868.client.support.Protocol;
import com.joeysoft.kc868.common.GlobalConst;

public class EV1527315StudyClosePacket extends OutPacket{

	public EV1527315StudyClosePacket() {
		super(Protocol.CMD_EV1527_315M_STUDY_CLOSE, Protocol.MSG_EV1527_315M_STUDY_CLOSE);
	}

	@Override
	protected void putBody() {
	}
}
