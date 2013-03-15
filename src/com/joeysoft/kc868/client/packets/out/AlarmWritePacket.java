package com.joeysoft.kc868.client.packets.out;

import com.joeysoft.kc868.client.packets.OutPacket;
import com.joeysoft.kc868.client.support.Protocol;
import com.joeysoft.kc868.common.GlobalConst;

public class AlarmWritePacket extends OutPacket{

	/** 定时器 */
	private int alarmNum;
	
	private String alarmParams;
	
	public AlarmWritePacket() {
		super(Protocol.CMD_ALARM_WRITE, Protocol.MSG_ALARM_WRITE);
	}

	@Override
	protected void putBody() {
		sbMessage.append(alarmNum);
		sbMessage.append(GlobalConst.CONST_STRING_COMMA);
		sbMessage.append(alarmParams);
	}

	public int getAlarmNum() {
		return alarmNum;
	}

	public void setAlarmNum(int alarmNum) {
		this.alarmNum = alarmNum;
	}

	public String getAlarmParams() {
		return alarmParams;
	}

	public void setAlarmParams(String alarmParams) {
		this.alarmParams = alarmParams;
	}
}
