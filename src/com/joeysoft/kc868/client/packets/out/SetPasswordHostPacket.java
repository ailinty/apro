package com.joeysoft.kc868.client.packets.out;

import com.joeysoft.kc868.client.packets.OutPacket;
import com.joeysoft.kc868.client.support.Protocol;
import com.joeysoft.kc868.common.GlobalConst;

public class SetPasswordHostPacket extends OutPacket{

	/** 主机密码 */
	private String password;
	
	public SetPasswordHostPacket() {
		super(Protocol.CMD_SET_PASSWORD, Protocol.MSG_SET_PASSWORD_HOST);
	}

	@Override
	protected void putBody() {
		sbMessage.append(password);
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}
