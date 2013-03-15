package com.joeysoft.kc868.client.packets.out;

import com.joeysoft.kc868.client.packets.OutPacket;
import com.joeysoft.kc868.client.support.Protocol;
import com.joeysoft.kc868.common.GlobalConst;

public class SetTLimitPacket extends OutPacket{

	/** 传感器 */
	private int slaver;
	/** 上限温度*/
	private int tempUpper;
	/** 下限温度*/
	private int tempLower;
	
	public SetTLimitPacket() {
		super(Protocol.CMD_SET_T_LIMIT, Protocol.MSG_SET_T_LIMIT);
	}

	@Override
	protected void putBody() {
		sbMessage.append(slaver);
		sbMessage.append(GlobalConst.CONST_STRING_COMMA);
		sbMessage.append(tempUpper);
		sbMessage.append(GlobalConst.CONST_STRING_COMMA);
		sbMessage.append(tempLower);
	}
	
	public int getSlaver() {
		return slaver;
	}

	public void setSlaver(int slaver) {
		this.slaver = slaver;
	}

	public int getTempUpper() {
		return tempUpper;
	}

	public void setTempUpper(int tempUpper) {
		this.tempUpper = tempUpper;
	}

	public int getTempLower() {
		return tempLower;
	}

	public void setTempLower(int tempLower) {
		this.tempLower = tempLower;
	}
}
