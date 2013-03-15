package com.joeysoft.kc868.client.packets.in.file;

import com.joeysoft.kc868.client.packets.InPacket;
import com.joeysoft.kc868.client.packets.PacketParseException;
import com.joeysoft.kc868.client.support.Protocol;

public class FileReadErrorReplyPacket extends InPacket{

	/**
	 * 文件大小
	 */
	private int fileSize;
	/**
	 * 判断文件是否可读
	 */
	boolean isFileOk;
	
	public FileReadErrorReplyPacket(char command, String message) throws PacketParseException{
		super(command, message);
	}
	
	@Override
	protected void parseBody() throws PacketParseException {
		String body = message.substring(Protocol.MSG_FILE_READ_HEAD_REPLY.length());
		try{
			fileSize = Integer.parseInt(body);
			isFileOk = true;
		} catch(NumberFormatException e) {
			isFileOk = false;
		}
	}

	public int getFileSize() {
		return fileSize;
	}

	public boolean isFileOk() {
		return isFileOk;
	}

	public void setFileSize(int fileSize) {
		this.fileSize = fileSize;
	}

	public void setFileOk(boolean isFileOk) {
		this.isFileOk = isFileOk;
	}
}
