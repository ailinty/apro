package com.joeysoft.kc868.client.packets.in.event;

import com.joeysoft.kc868.client.packets.InPacket;
import com.joeysoft.kc868.client.packets.PacketParseException;
import com.joeysoft.kc868.client.support.Protocol;

public class EventDelayReplyPacket extends InPacket{

	
	public EventDelayReplyPacket(char command, String message) throws PacketParseException{
		super(command, message);
	}
	
	@Override
	protected void parseBody() throws PacketParseException {
	}
}
