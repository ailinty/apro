package com.joeysoft.kc868.client.packets.out.infrared;

import com.joeysoft.kc868.client.packets.OutPacket;
import com.joeysoft.kc868.client.support.Protocol;
import com.joeysoft.kc868.common.GlobalConst;

public class InfraredStudyPacket extends OutPacket{

	/** 红外信号 */
	private int sensorId;
	
	public InfraredStudyPacket() {
		super(Protocol.CMD_INFRARED_STUDY, Protocol.MSG_INFRARED_STUDY);
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
