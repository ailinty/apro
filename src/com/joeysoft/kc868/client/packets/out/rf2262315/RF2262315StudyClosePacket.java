package com.joeysoft.kc868.client.packets.out.rf2262315;

import com.joeysoft.kc868.client.packets.OutPacket;
import com.joeysoft.kc868.client.support.Protocol;
import com.joeysoft.kc868.common.GlobalConst;

public class RF2262315StudyClosePacket extends OutPacket{

	public RF2262315StudyClosePacket() {
		super(Protocol.CMD_RFSTUY_315M_CLOSE, Protocol.MSG_RFSTUY_315M_STUDY_CLOSE_REPLY);
	}

	@Override
	protected void putBody() {
	}
}
