package com.joeysoft.kc868.client.packets.in;

import com.joeysoft.kc868.client.packets.InPacket;
import com.joeysoft.kc868.client.packets.PacketParseException;
import com.joeysoft.kc868.client.support.Protocol;
import com.joeysoft.kc868.common.GlobalConst;

public class ReadHostReplyPacket extends InPacket{

	/** 硬件版本 */
	private String hardVer;
	/** 软件版本 */
	private String softVer;
	/** 主机类型 */
	private String hostType;
	
	private String hostTemp;

	public ReadHostReplyPacket(char command, String message) throws PacketParseException{
		super(command, message);
	}
	
	@Override
	protected void parseBody() throws PacketParseException {
		String body = message.substring( Protocol.MSG_READ_HOST_REPLY.length());
		String[] vers = body.split(GlobalConst.CONST_STRING_COMMA);
		if(vers.length == 4){
			hardVer = vers[0];
			softVer = vers[1];
			hostType = vers[2];
			hostTemp = vers[3];
		}
	}

	public String getHardVer() {
		return hardVer;
	}

	public String getSoftVer() {
		return softVer;
	}

	public String getHostType() {
		return hostType;
	}

	public String getHostTemp() {
		return hostTemp;
	}

	public void setHostTemp(String hostTemp) {
		this.hostTemp = hostTemp;
	}

	public void setHardVer(String hardVer) {
		this.hardVer = hardVer;
	}

	public void setSoftVer(String softVer) {
		this.softVer = softVer;
	}

	public void setHostType(String hostType) {
		this.hostType = hostType;
	}
}
