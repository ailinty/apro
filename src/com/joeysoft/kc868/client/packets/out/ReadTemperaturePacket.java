package com.joeysoft.kc868.client.packets.out;

import com.joeysoft.kc868.client.packets.OutPacket;
import com.joeysoft.kc868.client.support.Protocol;

public class ReadTemperaturePacket extends OutPacket{

	public ReadTemperaturePacket() {
		super(Protocol.CMD_READ_TEMPERATURE, Protocol.MSG_READ_TEMPERATURE);
	}

	@Override
	protected void putBody() {
	}

}
