package com.joeysoft.kc868.client.packets.in;

import org.apache.commons.lang.StringUtils;

import com.joeysoft.kc868.client.packets.InPacket;
import com.joeysoft.kc868.client.packets.PacketParseException;
import com.joeysoft.kc868.client.support.Protocol;

public class GetVersionReplyPacket extends InPacket{

	private String version;
	
	public GetVersionReplyPacket(char command, String message) throws PacketParseException{
		super(command, message);
	}
	
	@Override
	protected void parseBody() throws PacketParseException {
		int replyLength = Protocol.MSG_GET_VERSION_REPLY.length();
		version = message.substring(replyLength);
		if(StringUtils.isEmpty(version)){
			version = Protocol.DB_VERSION_MIN;
		}
	}

	public String getVersion() {
		return version;
	}
}
