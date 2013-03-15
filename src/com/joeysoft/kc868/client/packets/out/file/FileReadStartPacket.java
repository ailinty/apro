package com.joeysoft.kc868.client.packets.out.file;

import com.joeysoft.kc868.client.packets.OutPacket;
import com.joeysoft.kc868.client.support.Protocol;
import com.joeysoft.kc868.common.GlobalConst;

public class FileReadStartPacket extends OutPacket{
	
	public FileReadStartPacket() {
		super(Protocol.CMD_FILE_READ, Protocol.MSG_FILE_READ_START);
	}

	@Override
	protected void putBody() {
	}

}
