package com.joeysoft.kc868.client.packets.out.lineate;

import com.joeysoft.kc868.client.packets.OutPacket;
import com.joeysoft.kc868.client.support.Protocol;
import com.joeysoft.kc868.common.GlobalConst;

public class IOSetRFNumPacket extends OutPacket{
	/** 开关量的上升1、下降0沿的十进制表示 */
	private int RFNum;
	
	public IOSetRFNumPacket() {
		super(Protocol.CMD_IO_SET_R_F_NUM, Protocol.MSG_IO_SET_R_F_NUM);
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
