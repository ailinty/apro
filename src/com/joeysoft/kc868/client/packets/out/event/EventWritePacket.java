package com.joeysoft.kc868.client.packets.out.event;

import com.joeysoft.kc868.client.packets.OutPacket;
import com.joeysoft.kc868.client.support.Protocol;
import com.joeysoft.kc868.common.GlobalConst;
import com.joeysoft.kc868.common.GlobalMethod;

public class EventWritePacket extends OutPacket{

	private int scene;
	private byte[] action;
	private String actionStr;
	
	public EventWritePacket() {
		super(Protocol.CMD_EVENT_WRITE, Protocol.MSG_EVENT_WRITE);
	}

	@Override
	protected void putBody() {
		/*sbMessage.append(scene);
		sbMessage.append(GlobalConst.CONST_STRING_COMMA);
		sbMessage.append(action);*/
		String strScene = String.valueOf(scene);
		int len = Protocol.MSG_EVENT_WRITE.length();
		buffer = new byte[len + strScene.length() + action.length + 2];
		System.arraycopy(Protocol.MSG_EVENT_WRITE.getBytes(), 0, buffer, 0, len);
		for(int i=0; i<strScene.length(); i++){
			buffer[len + i] = (byte)(strScene.charAt(i));
		}
		buffer[len + strScene.length()] = (byte)',';
		System.arraycopy(action, 0, buffer, len + strScene.length() + 1, action.length);
	}

	public int getScene() {
		return scene;
	}

	public void setScene(int scene) {
		this.scene = scene;
	}

	public byte[] getAction() {
		return action;
	}

	public void setAction(byte[] action) {
		this.action = action;
	}

	public String getActionStr() {
		return actionStr;
	}

	public void setActionStr(String actionStr) {
		this.actionStr = actionStr;
	}
}
