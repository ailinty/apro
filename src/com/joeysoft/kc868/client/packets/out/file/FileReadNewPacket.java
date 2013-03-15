package com.joeysoft.kc868.client.packets.out.file;

import com.joeysoft.kc868.client.packets.OutPacket;
import com.joeysoft.kc868.client.support.Protocol;
import com.joeysoft.kc868.common.GlobalMethod;

public class FileReadNewPacket extends OutPacket{
	
	private int segment;
	
	public FileReadNewPacket() {
		super(Protocol.CMD_FILE_READ, Protocol.MSG_FILE_READ_NEW);
	}

	@Override
	protected void putBody() {
		sbMessage.append(GlobalMethod.addLeadingZero(segment, 3));
	}

	public int getSegment() {
		return segment;
	}

	public void setSegment(int segment) {
		this.segment = segment;
	}
}
