package com.joeysoft.kc868.client.packets.in.file;


import com.joeysoft.kc868.client.packets.InPacket;
import com.joeysoft.kc868.client.packets.PacketParseException;

public class FileReadReplyPacket extends InPacket{
	
	public FileReadReplyPacket(char command, byte[] buf) throws PacketParseException{
		super(command, buf);
	}
	
	public FileReadReplyPacket(char command, String message, byte[] buf) throws PacketParseException{
		super(command, message, buf);
	}
	
	@Override
	protected void parseBody() throws PacketParseException {
	}

	public byte[] getBuffer(){
		return buffer;
	}
}
