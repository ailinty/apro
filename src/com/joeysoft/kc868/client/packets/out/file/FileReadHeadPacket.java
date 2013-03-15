package com.joeysoft.kc868.client.packets.out.file;

import com.joeysoft.kc868.client.packets.OutPacket;
import com.joeysoft.kc868.client.support.Protocol;

public class FileReadHeadPacket extends OutPacket{

	/** 文件名 */
	private String fileName;
	
	public FileReadHeadPacket() {
		super(Protocol.CMD_FILE_READ_HEAD, Protocol.MSG_FILE_READ_HEAD);
	}

	@Override
	protected void putBody() {
		sbMessage.append(fileName);
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

}
