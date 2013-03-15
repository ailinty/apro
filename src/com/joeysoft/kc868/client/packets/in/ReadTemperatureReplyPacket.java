package com.joeysoft.kc868.client.packets.in;

import com.joeysoft.kc868.client.packets.InPacket;
import com.joeysoft.kc868.client.packets.PacketParseException;
import com.joeysoft.kc868.client.support.Protocol;
import com.joeysoft.kc868.common.GlobalConst;

public class ReadTemperatureReplyPacket extends InPacket{

	/** 温度 */
	private int temperature;

	public ReadTemperatureReplyPacket(char command, String message) throws PacketParseException{
		super(command, message);
	}
	
	@Override
	protected void parseBody() throws PacketParseException {
		String body = message.substring( Protocol.MSG_READ_TEMPERATURE_REPLY.length());
		char sign = body.charAt(0); //负标志位
		if('!' == sign){
			temperature = 0 - Integer.valueOf(body.substring(1));
			
		}else{
			temperature = Integer.valueOf(body);
		}
	}

	public int getTemperature() {
		return temperature;
	}

	public void setTemperature(int temperature) {
		this.temperature = temperature;
	}
}
