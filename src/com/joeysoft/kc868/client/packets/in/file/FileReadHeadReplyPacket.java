package com.joeysoft.kc868.client.packets.in.file;

import com.joeysoft.kc868.client.packets.InPacket;
import com.joeysoft.kc868.client.packets.PacketParseException;
import com.joeysoft.kc868.client.support.Protocol;
import com.joeysoft.kc868.common.GlobalConst;
import com.joeysoft.kc868.db.util.DataAddrCodeUtil;

public class FileReadHeadReplyPacket extends InPacket{

	/**
	 * 文件大小
	 */
	private int fileSize;
	/**
	 * 文件总包数
	 */
	private int fileSegmentCount;
	/**
	 * 判断文件是否可读
	 */
	boolean isFileOk;
	
	public FileReadHeadReplyPacket(char command, String message) throws PacketParseException{
		super(command, message);
	}
	
	@Override
	protected void parseBody() throws PacketParseException {
		String body = message.substring(Protocol.MSG_FILE_READ_HEAD_REPLY.length());
		String[] strs = body.split(GlobalConst.CONST_STRING_COMMA);
		try{
			if(strs.length == 2){
				fileSize = Integer.valueOf(strs[0]);
				fileSegmentCount = Integer.valueOf(strs[1]);
			}else {
				fileSize = Integer.parseInt(body);
			}
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

	public int getFileSegmentCount() {
		return fileSegmentCount;
	}

	public void setFileSegmentCount(int fileSegmentCount) {
		this.fileSegmentCount = fileSegmentCount;
	}
}
