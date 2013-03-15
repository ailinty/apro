package com.joeysoft.kc868.client.packets.in.rf2262433;

import com.joeysoft.kc868.client.packets.PacketParseException;
import com.joeysoft.kc868.client.packets.in.SensorStudyOKReplyPacket;
import com.joeysoft.kc868.client.support.Protocol;
import com.joeysoft.kc868.common.GlobalConst;
import com.joeysoft.kc868.db.util.DataAddrCodeUtil;

public class RF2262433StudyOKReplyPacket extends SensorStudyOKReplyPacket{
	
	public RF2262433StudyOKReplyPacket(char command, String message) throws PacketParseException{
		super(command, message);
	}
	
	@Override
	protected void parseBody() throws PacketParseException {
	}
}
