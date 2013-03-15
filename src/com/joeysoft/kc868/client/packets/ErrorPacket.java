package com.joeysoft.kc868.client.packets;

import com.joeysoft.kc868.client.support.Protocol;

public class ErrorPacket extends InPacket{

    /** 远端已经关闭连接 */
    public static final int ERROR_REMOTE_CLOSED = 0;
    /** 操作超时 */
    public static final int ERROR_TIMEOUT = 1;
    
    public int errorCode;
    public String portName;
    
    // 用在超时错误中
    public OutPacket timeoutPacket;
    
    public ErrorPacket(int errorCode){
        super(Protocol.CMD_UNKNOWN);
        this.errorCode = errorCode;
    }
    
	@Override
	protected void parseBody() throws PacketParseException {
		
	}

}
