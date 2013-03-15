package com.joeysoft.kc868.client.packets.out.lineate;

import com.joeysoft.kc868.client.packets.OutPacket;
import com.joeysoft.kc868.client.support.Protocol;
import com.joeysoft.kc868.common.GlobalConst;

public class IOSetADMinPacket extends OutPacket{
	/** 表示几路有线信号 */
	private int load;
	/** 表示设置的最小值*/
	private String minNum;
	
	public IOSetADMinPacket() {
		super(Protocol.CMD_IO_SET_AD, Protocol.MSG_IO_SET_AD);
	}

	@Override
	protected void putBody() {
		sbMessage.append(load);
		sbMessage.append(GlobalConst.CONST_STRING_COMMA);
		sbMessage.append(1023);
		sbMessage.append(GlobalConst.CONST_STRING_COMMA);
		sbMessage.append(minNum);
		
	}

	public int getLoad() {
		return load;
	}

	public void setLoad(int load) {
		this.load = load;
	}

	public String getMinNum() {
		return minNum;
	}

	public void setMinNum(String minNum) {
		this.minNum = minNum;
	}
}
