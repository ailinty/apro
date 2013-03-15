package com.joeysoft.kc868.client.event;

import java.util.EventObject;

/**
 * Packet事件类
 * 
 * @author JOEY
 *
 */
public class PacketEvent extends EventObject{

	private static final long serialVersionUID = -8746662947115974374L;
	public int type;

	public PacketEvent(Object source) {
		super(source);
	}

}
