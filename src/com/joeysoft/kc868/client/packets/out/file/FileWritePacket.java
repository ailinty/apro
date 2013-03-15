package com.joeysoft.kc868.client.packets.out.file;

import com.joeysoft.kc868.client.packets.OutPacket;
import com.joeysoft.kc868.client.support.Protocol;

public class FileWritePacket extends OutPacket{

	private byte[] buf;
	
	public FileWritePacket() {
		super(Protocol.CMD_FILE_WRITE_OK, null);
	}

	@Override
	protected void putBody() {
		buffer = buf;
	}

	public byte[] getBuf() {
		return buf;
	}

	public void setBuf(byte[] buf) {
		this.buf = buf;
	}

	
}
