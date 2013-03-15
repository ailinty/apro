package com.joeysoft.kc868.client.packets.out;

import com.joeysoft.kc868.client.packets.OutPacket;
import com.joeysoft.kc868.client.support.Protocol;
import com.joeysoft.kc868.common.GlobalConst;

public class RmvTLimitReadPacket extends OutPacket{

	/** 情景 */
	private int scene;
	
	private String sceneParams;
	
	
	public RmvTLimitReadPacket() {
		super(Protocol.CMD_RMV_T_LIMIT, Protocol.MSG_RMV_T_LIMIT);
	}

	@Override
	protected void putBody() {
		sbMessage.append(scene);
		sbMessage.append(GlobalConst.CONST_STRING_COMMA);
		sbMessage.append(sceneParams);
	}

	public int getScene() {
		return scene;
	}

	public void setScene(int scene) {
		this.scene = scene;
	}

	public String getSceneParams() {
		return sceneParams;
	}

	public void setSceneParams(String sceneParams) {
		this.sceneParams = sceneParams;
	}
	
}
