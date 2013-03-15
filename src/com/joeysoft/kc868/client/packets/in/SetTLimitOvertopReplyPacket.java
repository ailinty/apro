package com.joeysoft.kc868.client.packets.in;

import com.joeysoft.kc868.client.packets.InPacket;
import com.joeysoft.kc868.client.packets.PacketParseException;
import com.joeysoft.kc868.client.support.Protocol;

public class SetTLimitOvertopReplyPacket extends InPacket{

	private String status;
	
	public SetTLimitOvertopReplyPacket(char command, String message) throws PacketParseException{
		super(command, message);
	}
	
	@Override
	protected void parseBody() throws PacketParseException {
		if(message.equals(Protocol.MSG_SET_T_LIMIT_OK_REPLY)){
			status = "OK";
		}else if(message.equals(Protocol.MSG_SET_T_LIMIT_OVERTOP_REPLY)){
			status = "OVERTOP";
		}else{
			status = "UNDER";
		}
	}

	public String getStatus() {
		return status;
	}
}
