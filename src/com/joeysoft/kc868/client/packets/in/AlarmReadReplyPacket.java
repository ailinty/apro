package com.joeysoft.kc868.client.packets.in;

import com.joeysoft.kc868.client.packets.InPacket;
import com.joeysoft.kc868.client.packets.PacketParseException;
import com.joeysoft.kc868.client.support.Protocol;
import com.joeysoft.kc868.common.GlobalConst;

public class AlarmReadReplyPacket extends InPacket{

	private int alarmNum;
	
	private int week;
	
	private String hours;
	private String minutes;
	private String seconds;
	
	public AlarmReadReplyPacket(char command, String message) throws PacketParseException{
		super(command, message);
	}
	
	@Override
	protected void parseBody() throws PacketParseException {
		String body = message.substring( Protocol.MSG_ALARM_READ_REPLY.length());
		String[] vers = body.split(GlobalConst.CONST_STRING_COMMA);
		if(vers.length == 6){
			alarmNum = Integer.valueOf(vers[0]);
			//Integer.valueOf(vers[1]);
			week = Integer.valueOf(vers[2]);
			hours = vers[3];
			minutes = vers[4];
			seconds = vers[5];
		}
	}

	public int getAlarmNum() {
		return alarmNum;
	}

	public int getWeek() {
		return week;
	}

	public String getHours() {
		return hours;
	}

	public String getMinutes() {
		return minutes;
	}

	public String getSeconds() {
		return seconds;
	}
}
