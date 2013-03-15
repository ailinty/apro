package com.joeysoft.kc868.client.packets.out.lineate;

import com.joeysoft.kc868.client.packets.OutPacket;
import com.joeysoft.kc868.client.support.Protocol;
import com.joeysoft.kc868.common.GlobalConst;

public class IOSetADMaxPacket extends OutPacket{
	/** 表示几路有线信号 */
	private int load;
	/** 表示设置的最大值*/
	private String maxNum;
	
	public IOSetADMaxPacket() {
		super(Protocol.CMD_IO_SET_AD, Protocol.MSG_IO_SET_AD);
	}

	@Override
	protected void putBody() {
		sbMessage.append(load);
		sbMessage.append(GlobalConst.CONST_STRING_COMMA);
		sbMessage.append(maxNum);
		sbMessage.append(GlobalConst.CONST_STRING_COMMA);
		sbMessage.append(1);
		
	}

	public int getLoad() {
		return load;
	}

	public void setLoad(int load) {
		this.load = load;
	}

	public String getMaxNum() {
		return maxNum;
	}

	public void setMaxNum(String maxNum) {
		this.maxNum = maxNum;
	}
}
