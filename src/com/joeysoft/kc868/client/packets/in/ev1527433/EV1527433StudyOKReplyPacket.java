package com.joeysoft.kc868.client.packets.in.ev1527433;

import com.joeysoft.kc868.client.packets.PacketParseException;
import com.joeysoft.kc868.client.packets.in.SensorStudyOKReplyPacket;
import com.joeysoft.kc868.client.support.Protocol;
import com.joeysoft.kc868.common.GlobalConst;
import com.joeysoft.kc868.db.util.DataAddrCodeUtil;

public class EV1527433StudyOKReplyPacket extends SensorStudyOKReplyPacket{
	
	public EV1527433StudyOKReplyPacket(char command, String message) throws PacketParseException{
		super(command, message);
	}
	
	@Override
	protected void parseBody() throws PacketParseException {
		String body = message.substring(Protocol.MSG_EV1527_433M_STUDY_OK_REPLY.length());
		String[] strs = body.split(GlobalConst.CONST_STRING_COMMA);
		if(strs.length == 3){
			load = Integer.valueOf(strs[0]);
			resType = strs[1];
			int[] addrData = DataAddrCodeUtil.splitAddrCodeDataCode1527(Integer.valueOf(strs[2]));
			addrCode = addrData[0];
			dataCode = addrData[1];
		}
	}
}