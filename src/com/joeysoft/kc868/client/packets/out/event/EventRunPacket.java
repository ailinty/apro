package com.joeysoft.kc868.client.packets.out.event;

import com.joeysoft.kc868.client.packets.OutPacket;
import com.joeysoft.kc868.client.support.Protocol;
import com.joeysoft.kc868.common.GlobalConst;

public class EventRunPacket extends OutPacket{

	/** 情景 */
	private int scene;
	
	
	public EventRunPacket() {
		super(Protocol.CMD_EVENT_RUN, Protocol.MSG_EVENT_RUN);
	}

	@Override
	protected void putBody() {
		sbMessage.append(scene);
	}

	public int getScene() {
		return scene;
	}

	public void setScene(int scene) {
		this.scene = scene;
	}
}
