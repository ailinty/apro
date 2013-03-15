package com.joeysoft.kc868.client.packets.in.event;

import com.joeysoft.kc868.client.packets.InPacket;
import com.joeysoft.kc868.client.packets.PacketParseException;

public class EventRunReplyPacket extends InPacket{

	public EventRunReplyPacket(char command, String message) throws PacketParseException{
		super(command, message);
	}
	
	@Override
	protected void parseBody() throws PacketParseException {
	}
}
