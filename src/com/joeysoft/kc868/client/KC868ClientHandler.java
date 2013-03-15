package com.joeysoft.kc868.client;

import org.jboss.netty.channel.ChannelEvent;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.ChannelStateEvent;
import org.jboss.netty.channel.ExceptionEvent;
import org.jboss.netty.channel.MessageEvent;
import org.jboss.netty.channel.SimpleChannelUpstreamHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.joeysoft.kc868.client.packets.ErrorPacket;
import com.joeysoft.kc868.client.packets.InPacket;
import com.joeysoft.kc868.client.support.PacketProcessor;

public class KC868ClientHandler extends SimpleChannelUpstreamHandler{
	 private static Logger logger = LoggerFactory.getLogger(KC868ClientHandler.class);
	
	private PacketProcessor packetProcessor;
	
	public KC868ClientHandler(PacketProcessor packetProcessor){
		this.packetProcessor = packetProcessor;
	}
	
    @Override
    public void handleUpstream(ChannelHandlerContext ctx, ChannelEvent e) throws Exception {
        if (e instanceof ChannelStateEvent) {
        	logger.debug(e.toString());
        }
        super.handleUpstream(ctx, e);
    }

    @Override
    public void channelConnected(ChannelHandlerContext ctx, ChannelStateEvent e) throws Exception {
    	packetProcessor.setConnctioned();
    }

    @Override
	public void channelDisconnected(ChannelHandlerContext ctx, ChannelStateEvent e) throws Exception {
    	logger.debug("channelDisconnected");
    	System.out.println("close......");
    	if(packetProcessor.isConnctioned()){
        	ErrorPacket error = new ErrorPacket(ErrorPacket.ERROR_REMOTE_CLOSED);
            packetProcessor.addIncomingPacket(error);
        }
    	packetProcessor.setDisConnctioned();
    }
    
    @Override
    public void messageReceived(ChannelHandlerContext ctx, MessageEvent e) {

        InPacket packet = (InPacket) e.getMessage();
        if(packet != null) {
        	//放入接收队列
        	packetProcessor.addIncomingPacket(packet);
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, ExceptionEvent e) {
    	logger.error("Unexpected exception from downstream.", e.getCause());
        e.getChannel().close();
        if(packetProcessor.isConnctioned()){
        	ErrorPacket error = new ErrorPacket(ErrorPacket.ERROR_REMOTE_CLOSED);
            packetProcessor.addIncomingPacket(error);
        }
        packetProcessor.setDisConnctioned();
    }
}
