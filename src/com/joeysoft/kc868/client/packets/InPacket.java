package com.joeysoft.kc868.client.packets;

public abstract class InPacket extends Packet{
	
	public InPacket(){
		super();
	}
	
	public InPacket(char command){
		super(command, "");
	}
	
	public InPacket(char command, String message) throws PacketParseException{
		super(command, message);
		parseHeader();
		parseBody();
	}
	
	public InPacket(char command, byte[] buf) throws PacketParseException{
		super(buf);
		this.command = command;
		parseHeader();
		parseBody();
	}
	
	public InPacket(char command, String message, byte[] buf) throws PacketParseException{
		super(buf);
		this.command = command;
		this.message = message;
		parseHeader();
		parseBody();
	}
	
	@Override
    protected void putHead() {
    }
    
	@Override
    protected void putBody() {
    }
	
	@Override
    protected void putTail() {
    }
	
	@Override
	protected void parseHeader() throws PacketParseException {
	}
	
	@Override
    protected byte[] encryptBody(byte[] body, int offset, int len) {
		return body;
    }
	
	@Override
	protected byte[] decryptBody(byte[] body, int offset, int len) {
		return body;
	}
}
