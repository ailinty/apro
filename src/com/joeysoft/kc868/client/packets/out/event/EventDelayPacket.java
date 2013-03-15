package com.joeysoft.kc868.client.packets.out.event;

import com.joeysoft.kc868.client.packets.OutPacket;
import com.joeysoft.kc868.client.support.Protocol;
import com.joeysoft.kc868.common.GlobalConst;

public class EventDelayPacket extends OutPacket{

	private int delay;
	
	
	public EventDelayPacket() {
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
