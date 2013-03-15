package com.joeysoft.kc868.client.packets.out;

import com.joeysoft.kc868.client.packets.OutPacket;
import com.joeysoft.kc868.client.support.Protocol;

public class RF1101ReadPacket extends OutPacket{

	/** 传感器 */
	private int RFNum;
	
	public RF1101ReadPacket() {
		super(Protocol.CMD_RF1101_READ, Protocol.MSG_RF1101_READ);
	}

	@Override
	protected void putBody() {
		sbMessage.append(RFNum);
	}

	public int getRFNum() {
		return RFNum;
	}

	public void setRFNum(int rFNum) {
		RFNum = rFNum;
	}
}
