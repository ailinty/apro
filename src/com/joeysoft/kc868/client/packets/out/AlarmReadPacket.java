package com.joeysoft.kc868.client.packets.out;

import com.joeysoft.kc868.client.packets.OutPacket;
import com.joeysoft.kc868.client.support.Protocol;
import com.joeysoft.kc868.common.GlobalConst;

public class AlarmReadPacket extends OutPacket{

	/** 定时器 */
	private int alarmNum;
	
	public AlarmReadPacket() {
		super(Protocol.CMD_ALARM_READ, Protocol.MSG_ALARM_READ);
	}

	@Override
	protected void putBody() {
		sbMessage.append(alarmNum);
	}

	public int getAlarmNum() {
		return alarmNum;
	}

	public void setAlarmNum(int alarmNum) {
		this.alarmNum = alarmNum;
	}
}
