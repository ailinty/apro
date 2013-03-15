package com.joeysoft.kc868.client.packets.out;

import com.joeysoft.kc868.client.packets.OutPacket;
import com.joeysoft.kc868.client.support.Protocol;
import com.joeysoft.kc868.common.GlobalConst;

public class WriteTimePacket extends OutPacket{

	private String year;
	private String month;
	private String day;
	private String hour;
	private String minute;
	private String second;
	
	public WriteTimePacket() {
		super(Protocol.CMD_WRITE_TIME, Protocol.MSG_WRITE_TIME);
	}

	@Override
	protected void putBody() {
		sbMessage.append(year);
		sbMessage.append(GlobalConst.CONST_STRING_COMMA);
		sbMessage.append(month);
		sbMessage.append(GlobalConst.CONST_STRING_COMMA);
		sbMessage.append(day);
		sbMessage.append(GlobalConst.CONST_STRING_COMMA);
		sbMessage.append(hour);
		sbMessage.append(GlobalConst.CONST_STRING_COMMA);
		sbMessage.append(minute);
		sbMessage.append(GlobalConst.CONST_STRING_COMMA);
		sbMessage.append(second);
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public String getMonth() {
		return month;
	}

	public void setMonth(String month) {
		this.month = month;
	}

	public String getDay() {
		return day;
	}

	public void setDay(String day) {
		this.day = day;
	}

	public String getHour() {
		return hour;
	}

	public void setHour(String hour) {
		this.hour = hour;
	}

	public String getMinute() {
		return minute;
	}

	public void setMinute(String minute) {
		this.minute = minute;
	}

	public String getSecond() {
		return second;
	}

	public void setSecond(String second) {
		this.second = second;
	}
}
