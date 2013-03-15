package com.joeysoft.kc868.client.packets.in;

import com.joeysoft.kc868.client.packets.InPacket;
import com.joeysoft.kc868.client.packets.PacketParseException;
import com.joeysoft.kc868.client.support.Protocol;
import com.joeysoft.kc868.common.GlobalConst;

public class FindSignalNowReplyPacket extends InPacket{

	private int degree;
	private boolean status;
	
	public FindSignalNowReplyPacket(char command, String message) throws PacketParseException{
		super(command, message);
	}
	
	@Override
	protected void parseBody() throws PacketParseException {
		String body = message.substring(Protocol.MSG_FIND_SIGNAL_NOW_REPLY.length());
		String[] strs = body.split(GlobalConst.CONST_STRING_COMMA);
		if(strs.length == 2){
			degree = Integer.valueOf(strs[0]);
			if("99".equals(strs[1])){
				status = true;
			}else{
				status = false;
			}
		}
	}

	public int getDegree() {
		return degree;
	}

	public void setDegree(int degree) {
		this.degree = degree;
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}
}
