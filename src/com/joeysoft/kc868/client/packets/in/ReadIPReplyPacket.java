package com.joeysoft.kc868.client.packets.in;

import com.joeysoft.kc868.client.packets.InPacket;
import com.joeysoft.kc868.client.packets.PacketParseException;
import com.joeysoft.kc868.client.support.Protocol;
import com.joeysoft.kc868.common.GlobalConst;

public class ReadIPReplyPacket extends InPacket{

	/** 主机ip */
	private String IP;
	/** 子网掩码 */
	private String mask;
	/** 网关 */
	private String gate;

	public ReadIPReplyPacket(char command, String message) throws PacketParseException{
		super(command, message);
	}
	
	@Override
	protected void parseBody() throws PacketParseException {
		String body = message.substring( Protocol.MSG_READ_IP_REPLY.length());
		String[] strs = body.split(GlobalConst.CONST_STRING_COMMA);
		if(strs.length == 3){
			IP = strs[0];
			mask = strs[1];
			gate = strs[2];
		}
	}

	public String getIP() {
		return IP;
	}

	public String getMask() {
		return mask;
	}

	public String getGate() {
		return gate;
	}
}
