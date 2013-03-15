package com.joeysoft.kc868.client.packets;

import org.jboss.netty.buffer.ChannelBuffer;

public final class PacketHelper {
	private IParser parser;
	
	private PacketHistory history;
	
	public PacketHelper(){
		parser = new PacketParser(this);
		history = new PacketHistory();
	}
	
	
	public InPacket processIn(ChannelBuffer buf) throws PacketParseException{
		// 解析包
        try {
        	if(!parser.accept(buf))
        		return null;
        	
            InPacket ret = parser.parseIncoming(buf, 2, parser.getLength());
	        return ret;
        } catch (PacketParseException e) {
            throw e;
		} finally {
        }
	}

	public IParser getParser() {
		return parser;
	}
	
	/**
	 * @return Returns the history.
	 */
	public PacketHistory getHistory() {
		return history;
	}
}
