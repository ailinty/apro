package com.joeysoft.kc868.client.packets.in;

import com.joeysoft.kc868.client.packets.InPacket;
import com.joeysoft.kc868.client.packets.PacketParseException;
import com.joeysoft.kc868.client.support.Protocol;
import com.joeysoft.kc868.common.GlobalConst;

public class RF1101ReadReplyPacket extends InPacket{

	/** 传感器*/
	private int RFNum;
	/** 传感器类型 */
	private String RFType;
	
	/** 温度*/
	private Float temperature;
	private Float humidity;
	
	
	public RF1101ReadReplyPacket(char command, String message) throws PacketParseException{
		super(command, message);
	}
	
	@Override
	protected void parseBody() throws PacketParseException {
		String body = message.substring( Protocol.MSG_RF1101_READ_REPLY.length());
		String[] vers = body.split(GlobalConst.CONST_STRING_COMMA);
		if(vers.length == 2){
			RFNum = Integer.valueOf(vers[0]);
			RFType = vers[1];
		}else if(vers.length == 3) {
			RFNum = Integer.valueOf(vers[0]);
			RFType = vers[1];
			temperature = Float.valueOf(vers[2]);
		}else if(vers.length == 4) {
			RFNum = Integer.valueOf(vers[0]);
			RFType = vers[1];
			temperature = Float.valueOf(vers[2]);
			humidity = Float.valueOf(vers[3]);
		}
	}

	public int getRFNum() {
		return RFNum;
	}

	public void setRFNum(int rFNum) {
		RFNum = rFNum;
	}

	public String getRFType() {
		return RFType;
	}

	public void setRFType(String rFType) {
		RFType = rFType;
	}

	public Float getTemperature() {
		return temperature;
	}

	public void setTemperature(Float temperature) {
		this.temperature = temperature;
	}

	public Float getHumidity() {
		return humidity;
	}

	public void setHumidity(Float humidity) {
		this.humidity = humidity;
	}
}
