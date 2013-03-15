package com.joeysoft.kc868.client.packets.out.lineate;

import com.joeysoft.kc868.client.packets.OutPacket;
import com.joeysoft.kc868.client.support.Protocol;
import com.joeysoft.kc868.common.GlobalConst;

public class RelaySetRelayOnPacket extends OutPacket{
	/** 几路 */
	private int load;
	
	public RelaySetRelayOnPacket() {
		super(Protocol.CMD_RELAY_SET_RELAY, Protocol.MSG_RELAY_SET_RELAY);
	}

	@Override
	protected void putBody() {
		sbMessage.append(load);
		sbMessage.append(GlobalConst.CONST_STRING_COMMA);
		sbMessage.append("SINGLE_ON");
	}

	public int getLoad() {
		return load;
	}

	public void setLoad(int load) {
		this.load = load;
	}
}
