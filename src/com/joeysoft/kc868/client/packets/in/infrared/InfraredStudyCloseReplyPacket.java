package com.joeysoft.kc868.client.packets.in.infrared;

import com.joeysoft.kc868.client.packets.InPacket;
import com.joeysoft.kc868.client.packets.PacketParseException;
import com.joeysoft.kc868.client.support.Protocol;

public class InfraredStudyCloseReplyPacket extends InPacket{

	
	public InfraredStudyCloseReplyPacket(char command, String message) throws PacketParseException{
		super(command, message);
	}
	
	@Override
	protected void parseBody() throws PacketParseException {
	}
}
