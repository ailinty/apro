package com.joeysoft.kc868.client.packets.out.event;

import com.joeysoft.kc868.client.packets.OutPacket;
import com.joeysoft.kc868.client.support.Protocol;
import com.joeysoft.kc868.common.GlobalConst;

public class EventConnectPacket extends OutPacket{

	/** 情景 */
	private int scene;
	/** 触发源*/
	private String eventSource;
	
	
	public EventConnectPacket() {
		super(Protocol.CMD_EVENT_CONNECT, Protocol.MSG_EVENT_CONNECT);
	}

	@Override
	protected void putBody() {
		sbMessage.append(eventSource);
		sbMessage.append(GlobalConst.CONST_STRING_COMMA);
		sbMessage.append(scene);
	}

	public int getScene() {
		return scene;
	}

	public void setScene(int scene) {
		this.scene = scene;
	}

	public String getEventSource() {
		return eventSource;
	}

	public void setEventSource(String eventSource) {
		this.eventSource = eventSource;
	}
}
