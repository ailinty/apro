package com.joeysoft.kc868.client.packets.out.pt2262433;

import com.joeysoft.kc868.client.packets.OutPacket;
import com.joeysoft.kc868.client.support.Protocol;
import com.joeysoft.kc868.common.GlobalConst;

public class PT2262433StudyClosePacket extends OutPacket{

	public PT2262433StudyClosePacket() {
		super(Protocol.CMD_PT2262_433M_STUDY_CLOSE, Protocol.MSG_PT2262_433M_STUDY_CLOSE);
	}

	@Override
	protected void putBody() {
	}
}
