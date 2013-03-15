package com.joeysoft.kc868.client.packets.out.ev1527433;

import com.joeysoft.kc868.client.packets.OutPacket;
import com.joeysoft.kc868.client.support.Protocol;
import com.joeysoft.kc868.common.GlobalConst;

public class EV1527433SendPacket extends OutPacket{

	/** 无线信号 */
	private int sensorId;
	
	public EV1527433SendPacket() {
		super(Protocol.CMD_EV1527_433M_SEND, Protocol.MSG_EV1527_433M_SEND);
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
