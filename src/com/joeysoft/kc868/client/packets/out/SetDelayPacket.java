package com.joeysoft.kc868.client.packets.out;

import com.joeysoft.kc868.client.packets.OutPacket;
import com.joeysoft.kc868.client.support.Protocol;

public class SetDelayPacket extends OutPacket{

	/** 延时值 */
	private int delay;
	
	public SetDelayPacket() {
		super(Protocol.CMD_EVENT_DELAY, Protocol.MSG_EVENT_DELAY);
	}

	@Override
	protected void putBody() {
		sbMessage.append(delay);
	}

	public int getDelay() {
		return delay;
	}

	public void setDelay(int delay) {
		this.delay = delay;
	}
}
