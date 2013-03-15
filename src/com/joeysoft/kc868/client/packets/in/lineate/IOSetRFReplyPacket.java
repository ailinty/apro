package com.joeysoft.kc868.client.packets.in.lineate;

import com.joeysoft.kc868.client.packets.InPacket;
import com.joeysoft.kc868.client.packets.PacketParseException;

public class IOSetRFReplyPacket extends InPacket{

	public IOSetRFReplyPacket(char command, String message) throws PacketParseException{
		super(command, message);
	}
	
	@Override
	protected void parseBody() throws PacketParseException {
	}
}
