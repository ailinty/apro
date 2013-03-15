package com.joeysoft.kc868.client.packets.in;

import com.joeysoft.kc868.client.packets.InPacket;
import com.joeysoft.kc868.client.packets.PacketParseException;
import com.joeysoft.kc868.client.support.Protocol;
import com.joeysoft.kc868.common.GlobalConst;

public class GetPasswordReplyPacket extends InPacket{

	private String password;
	
	public GetPasswordReplyPacket(char command, String message) throws PacketParseException{
		super(command, message);
	}
	
	@Override
	protected void parseBody() throws PacketParseException {
		String body = message.substring(Protocol.MSG_GET_PASSWORD_REPLY.length());
		String[] strs = body.split(GlobalConst.CONST_STRING_COMMA);
		if(strs.length == 1){
			password = strs[0];
		}
	}

	public String getPassword() {
		return password;
	}
}
