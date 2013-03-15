package com.joeysoft.kc868.client.packets.out.rf2262433;

import com.joeysoft.kc868.client.packets.OutPacket;
import com.joeysoft.kc868.client.support.Protocol;

public class RF2262433DeletePacket extends OutPacket{

	/** 无线信号 */
	private int sensorId;
	
	public RF2262433DeletePacket() {
		super(Protocol.CMD_RFSTUY_433M_DELETE, Protocol.MSG_RFSTUY_433M_DELETE);
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
