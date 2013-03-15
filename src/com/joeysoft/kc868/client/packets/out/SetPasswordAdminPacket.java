package com.joeysoft.kc868.client.packets.out;

import com.joeysoft.kc868.client.packets.OutPacket;
import com.joeysoft.kc868.client.support.Protocol;
import com.joeysoft.kc868.common.GlobalConst;

public class SetPasswordAdminPacket extends OutPacket{

	/** 管理员密码 */
	private String password;
	
	public SetPasswordAdminPacket() {
		super(Protocol.CMD_SET_PASSWORD, Protocol.MSG_SET_PASSWORD_ADMIN);
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
