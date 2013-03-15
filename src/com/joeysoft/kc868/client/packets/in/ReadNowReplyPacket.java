package com.joeysoft.kc868.client.packets.in;

import com.joeysoft.kc868.client.packets.InPacket;
import com.joeysoft.kc868.client.packets.PacketParseException;
import com.joeysoft.kc868.client.support.Protocol;
import com.joeysoft.kc868.common.GlobalConst;

public class ReadNowReplyPacket extends InPacket{
	
	private String year;
	private String month;
	private String day;
	private String hour;
	private String minute;
	private String second;
	
	public ReadNowReplyPacket(char command, String message) throws PacketParseException{
		super(command, message);
	}
	
	@Override
	protected void parseBody() throws PacketParseException {
		String body = message.substring( Protocol.MSG_READ_NOW_REPLY.length());
		String[] strs = body.split(GlobalConst.CONST_STRING_COMMA);
		if(strs.length == 6){
			year = strs[0];
			month = strs[1];
			day = strs[2];
			hour = strs[3];
			minute = strs[4];
			second = strs[5];
		}
	}

	public String getYear() {
		return year;
	}

	public String getMonth() {
		return month;
	}

	public String getDay() {
		return day;
	}

	public String getHour() {
		return hour;
	}

	public String getSecond() {
		return second;
	}

	public String getMinute() {
		return minute;
	}
}
