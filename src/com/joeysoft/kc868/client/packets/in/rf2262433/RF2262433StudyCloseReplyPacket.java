package com.joeysoft.kc868.client.packets.in.rf2262433;

import com.joeysoft.kc868.client.packets.InPacket;
import com.joeysoft.kc868.client.packets.PacketParseException;
import com.joeysoft.kc868.client.support.Protocol;

public class RF2262433StudyCloseReplyPacket extends InPacket{

	
	public RF2262433StudyCloseReplyPacket(char command, String message) throws PacketParseException{
		super(command, message);
	}
	
	@Override
	protected void parseBody() throws PacketParseException {
	}
}
