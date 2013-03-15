package com.joeysoft.kc868.client.packets.out.file;

import com.joeysoft.kc868.client.packets.OutPacket;
import com.joeysoft.kc868.client.support.Protocol;
import com.joeysoft.kc868.common.GlobalConst;

public class FileWriteHeadPacket extends OutPacket{

	/** 文件名 */
	private String fileName;
	/** 文件大小*/
	private long fileSize;
	
	public FileWriteHeadPacket() {
		super(Protocol.CMD_FILE_WRITE_START, Protocol.MSG_FILE_WRITE_HEAD);
	}

	@Override
	protected void putBody() {
		sbMessage.append(fileName);
		sbMessage.append(GlobalConst.CONST_STRING_COMMA);
		sbMessage.append(fileSize);
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public long getFileSize() {
		return fileSize;
	}

	public void setFileSize(long fileSize) {
		this.fileSize = fileSize;
	}
}
