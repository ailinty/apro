package com.joeysoft.kc868.client.packets.out.infrared;

import com.joeysoft.kc868.client.packets.OutPacket;
import com.joeysoft.kc868.client.support.Protocol;
import com.joeysoft.kc868.common.GlobalConst;

public class InfraredSendPacket extends OutPacket{
	/** 红外信号 */
	private int sensorId;
	
	public InfraredSendPacket() {
		super(Protocol.CMD_INFRARED_SEND, Protocol.MSG_INFRARED_SEND);
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
