package com.joeysoft.kc868.client.packets.out;

import com.joeysoft.kc868.client.packets.OutPacket;
import com.joeysoft.kc868.client.support.Protocol;
import com.joeysoft.kc868.common.GlobalConst;

public class SetSlaverPacket extends OutPacket{

	/** 传感器 */
	private int slaver;
	
	/** 传感器地址 */
	private int slaverAddr;
	
	public SetSlaverPacket() {
		super(Protocol.CMD_SET_SLAVER, Protocol.MSG_SET_SLAVER);
	}

	@Override
	protected void putBody() {
		sbMessage.append(slaver);
		sbMessage.append(GlobalConst.CONST_STRING_COMMA);
		sbMessage.append(slaverAddr);
	}

	public int getSlaver() {
		return slaver;
	}

	public void setSlaver(int slaver) {
		this.slaver = slaver;
	}

	public int getSlaverAddr() {
		return slaverAddr;
	}

	public void setSlaverAddr(int slaverAddr) {
		this.slaverAddr = slaverAddr;
	}
}
