package com.joeysoft.kc868.client.handler;

import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.handler.codec.frame.FrameDecoder;

import com.joeysoft.kc868.client.packets.InPacket;
import com.joeysoft.kc868.client.support.PacketProcessor;

public class PacketDecoder extends FrameDecoder{
	private PacketProcessor packetProcessor;
	
	public PacketDecoder(PacketProcessor packetProcessor){
		this.packetProcessor = packetProcessor;
	}
	
	@Override
	protected Object decode(ChannelHandlerContext ctx, Channel channel,
			ChannelBuffer buf) throws Exception {
		if(buf.readableBytes() > 0) {
			InPacket packet = packetProcessor.getPacketHelper().processIn(buf);
			/*if(packet != null){
				packet.setChannel(channel);
			}*/
			return packet;
		}
		return null;
	}

}
