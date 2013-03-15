package com.joeysoft.kc868.client.packets.out.pt2262315;

import com.joeysoft.kc868.client.packets.OutPacket;
import com.joeysoft.kc868.client.support.Protocol;
import com.joeysoft.kc868.common.GlobalConst;
import com.joeysoft.kc868.db.util.DataAddrCodeUtil;

public class PT2262315DeletePacket extends OutPacket{

	/** 无线信号 */
	private int sensorId;
	/** 振荡电阻 */
	private String resType;
	/** 地址码 */
	private int addrCode;
	/** 数据码 */
	private int dataCode;
	
	public PT2262315DeletePacket() {
		super(Protocol.CMD_PT2262_315M_DELETE, Protocol.MSG_PT2262_315M_DELETE);
	}

	@Override
	protected void putBody() {
		sbMessage.append(sensorId);
		sbMessage.append(GlobalConst.CONST_STRING_COMMA);
		sbMessage.append(resType);
		sbMessage.append(GlobalConst.CONST_STRING_COMMA);
		sbMessage.append(DataAddrCodeUtil.getDataAddrCodeBit(addrCode, dataCode, 16));
	}

	public int getSensorId() {
		return sensorId;
	}

	public void setSensorId(int sensorId) {
		this.sensorId = sensorId;
	}

	public String getResType() {
		return resType;
	}

	public void setResType(String resType) {
		this.resType = resType;
	}

	public int getAddrCode() {
		return addrCode;
	}

	public void setAddrCode(int addrCode) {
		this.addrCode = addrCode;
	}

	public int getDataCode() {
		return dataCode;
	}

	public void setDataCode(int dataCode) {
		this.dataCode = dataCode;
	}
}
