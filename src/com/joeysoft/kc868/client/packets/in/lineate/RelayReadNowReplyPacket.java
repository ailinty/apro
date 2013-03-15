package com.joeysoft.kc868.client.packets.in.lineate;

import com.joeysoft.kc868.client.packets.InPacket;
import com.joeysoft.kc868.client.packets.PacketParseException;
import com.joeysoft.kc868.client.support.Protocol;
import com.joeysoft.kc868.common.GlobalConst;
import com.joeysoft.kc868.db.util.DataAddrCodeUtil;

public class RelayReadNowReplyPacket extends InPacket{

	private int relayStatus;
	
	public RelayReadNowReplyPacket(char command, String message) throws PacketParseException{
		super(command, message);
	}
	
	@Override
	protected void parseBody() throws PacketParseException {
		String body = message.substring(Protocol.MSG_RELAY_READ_NOW_REPLY.length());
		String[] strs = body.split(GlobalConst.CONST_STRING_COMMA);
		if(strs.length == 1){
			relayStatus = Integer.valueOf(strs[0]);
		}
	}

	public int getRelayStatus() {
		return relayStatus;
	}

	public void setRelayStatus(int relayStatus) {
		this.relayStatus = relayStatus;
	}
}
