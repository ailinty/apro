package com.joeysoft.kc868.client.packets.out.lineate;

import com.joeysoft.kc868.client.packets.OutPacket;
import com.joeysoft.kc868.client.support.Protocol;

public class IOSetModeNumPacket extends OutPacket{
	/** 8路开并量、模拟量的十进制表示 */
	private int modeNum;
	
	public IOSetModeNumPacket() {
		super(Protocol.CMD_IO_SET_MODE_NUM, Protocol.MSG_IO_SET_MODE_NUM);
	}

	@Override
	protected void putBody() {
		sbMessage.append(modeNum);
	}

	public int getModeNum() {
		return modeNum;
	}

	public void setModeNum(int modeNum) {
		this.modeNum = modeNum;
	}
}
