package com.joeysoft.kc868.client.packets;

import com.joeysoft.kc868.client.support.Protocol;

public abstract class OutPacket extends Packet{
	/** 超时截止时间，单位ms */
	private long timeout;
	/** 重发计数器 */
	private int resendCountDown;
	
	/**
	 * 
	 * @param command
	 * @throws PacketParseException 
	 */
	public OutPacket(char command, String message){
		super(command, message);
		this.resendCountDown = Protocol.MAX_RESEND;
	}

	public byte[] fill(){
		sbMessage = new StringBuffer();
		sbMessage.append(message);
		putHead();
		putBody();
		putTail();
		if(buffer != null && buffer.length > 0){
			return buffer;
		}
		return sbMessage.toString().getBytes();
	}
	
	@Override
    protected void putHead() {
    }
	
	@Override
	protected void putTail() {
	}
    
	@Override
    protected void parseHeader() throws PacketParseException {
		
	}
	
	@Override
    protected byte[] decryptBody(byte[] body, int offset, int length) {
		return body;
    }
	
	/* (non-Javadoc)
	 * @see com.ycmsoft.qqserver.qq.packets.OutPacket#encryptBody(byte[], int, int)
	 */
	@Override
	protected byte[] encryptBody(byte[] body, int offset, int length) {
		return body;
	}
	
	@Override
    protected void parseBody() throws PacketParseException {
    }

	/**
	 * 是否需要重发.
	 * 
	 * @return 需要重发返回true, 否则返回false.
	 */
	public final boolean needResend() {
		return (resendCountDown--) > 0;
	}
	
	public long getTimeout() {
		return timeout;
	}

	public void setTimeout(long timeout) {
		this.timeout = timeout;
	}
}
