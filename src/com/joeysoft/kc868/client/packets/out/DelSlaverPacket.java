package com.joeysoft.kc868.client.packets.out;

import com.joeysoft.kc868.client.packets.OutPacket;
import com.joeysoft.kc868.client.support.Protocol;
import com.joeysoft.kc868.common.GlobalConst;

public class DelSlaverPacket extends OutPacket{

	/** 传感器 */
	private int slaver;
	
	public DelSlaverPacket() {
		super(Protocol.CMD_DEL_SLAVER, Protocol.MSG_DEL_SLAVER);
	}

	@Override
	protected void putBody() {
		sbMessage.append(slaver);
	}

	public int getSlaver() {
		return slaver;
	}

	public void setSlaver(int slaver) {
		this.slaver = slaver;
	}
}
