package com.joeysoft.kc868.client.packets.out.pt2262315;

import com.joeysoft.kc868.client.packets.OutPacket;
import com.joeysoft.kc868.client.support.Protocol;
import com.joeysoft.kc868.common.GlobalConst;

public class PT2262315SendPacket extends OutPacket{

	/** 无线信号 */
	private int sensorId;
	
	public PT2262315SendPacket() {
		super(Protocol.CMD_PT2262_315M_SEND, Protocol.MSG_PT2262_315M_SEND);
	}

	@Override
	protected void putBody() {
		sbMessage.append(sensorId);
	}

	public int getSensorId() {
		return sensorId;
	}

	public void setSensorId(int sensorId) {
		this.sensorId = sensorId;
	}
}
