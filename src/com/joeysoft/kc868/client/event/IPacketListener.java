package com.joeysoft.kc868.client.event;

/**
 * 包事件监听器
 * @author JOEY
 *
 */
public interface IPacketListener {
	/**
     * 包到达时调用此方法
     * 
     * @param e
     */
    public void packetArrived(PacketEvent e);
}
