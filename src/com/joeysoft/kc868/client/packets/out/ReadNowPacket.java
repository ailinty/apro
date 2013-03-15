package com.joeysoft.kc868.client.packets.out;

import com.joeysoft.kc868.client.packets.OutPacket;
import com.joeysoft.kc868.client.support.Protocol;

public class ReadNowPacket extends OutPacket{

	public ReadNowPacket(){
		super(Protocol.CMD_READ_NOW, Protocol.MSG_READ_NOW);
	}

	@Override
	protected void putBody() {
		
	}

}
