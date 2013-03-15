package com.joeysoft.kc868.client.handler;

import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.buffer.ChannelBuffers;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.handler.codec.oneone.OneToOneEncoder;

import com.joeysoft.kc868.client.packets.OutPacket;

public class PacketEncoder extends OneToOneEncoder{

	@Override
	protected Object encode(ChannelHandlerContext ctx, Channel channel,
			Object obj) throws Exception {
		OutPacket packet = (OutPacket) obj;
		ChannelBuffer buffer = ChannelBuffers.dynamicBuffer();
		buffer.writeBytes(packet.fill());
		return buffer;
	}
}
