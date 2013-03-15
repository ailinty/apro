package com.joeysoft.kc868.client.packets;

import java.nio.ByteBuffer;

import org.jboss.netty.channel.Channel;

import com.joeysoft.kc868.client.support.Protocol;

public abstract class Packet{
	/**
	 * IsoMessage
	 */
	protected String message;
	
	protected byte[] buffer;
	protected StringBuffer sbMessage = new StringBuffer();
	/**
	 * 交易类型
	 */
	protected char command;
	
	public Packet(){
	}
	
	public Packet(char command, String message){
		this.command = command;
		this.message = message;
		sbMessage.append(message);
	}
	
	public Packet(byte[] buf){
		buffer = new byte[buf.length];
		System.arraycopy(buf, 0, buffer, 0, buf.length);
	}
	
	/**
	 * 初始化包头
	 * 
	 * @param buf
	 *
	 */
	protected abstract void putHead();
	
	/**
	 * 初始化包体
	 * 
	 * @param buf
	 * 			ByteBuffer
	 */
	protected abstract void putBody();
	
	/**
	 * 将包尾部转化为字节流
	 * 
	 */
	protected abstract void putTail();	
	
	/**
	 * 解析包头
	 * 
	 * @param buf
	 * 		ByteBuffer
	 * @throws PacketParseException
	 * 		如果解析出错
	 */
	protected abstract void parseHeader() throws PacketParseException;
	
	/**
	 * 加密包体
	 * 
	 * @param b 
	 * 		未加密的字节数组
	 * @param offset
	 * 		包体开始的偏移
	 * @param length
	 * 		包体长度
	 * @return
	 * 		加密的包体
	 */
	protected abstract byte[] encryptBody(byte[] b, int offset, int length);
	
	/**
	 * 解密包体
	 * 
     * @param body
     * 			包体字节数组
     * @param offset
     * 			包体开始偏移
     * @param length
     * 			包体长度
     * @return 解密的包体字节数组
     */
	protected abstract byte[] decryptBody(byte[] body, int offset, int length);
	
	/**
	 * 解析包体
	 * 
	 * @param buf
	 * 			ByteBuffer
	 * @throws PacketParseException
	 * 			如果解析出错
	 */
	protected abstract void parseBody() throws PacketParseException;
	
	public final boolean equals(Object obj) {
		if (obj instanceof Packet)
			return equals((Packet) obj);
		else
			return super.equals(obj);
	}
	
	/**
	 * 是否与另一个包相等. 必须command、序列号、posId都相等才能认为两个包相等. 即使这两个包并非相同类型.
	 * 
	 * @param packet
	 *                   被比较的包.
	 * @return 相等返回true, 否则返回false.
	 */
	public final boolean equals(Packet packet) {
		return 
			command == packet.command;
	}

	public char getCommand() {
		return command;
	}

	public String getMessage() {
		return message;
	}

	public StringBuffer getSbMessage() {
		return sbMessage;
	}
}
