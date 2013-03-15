package com.joeysoft.kc868.client;

import static org.jboss.netty.channel.Channels.pipeline;

import org.jboss.netty.channel.ChannelPipeline;
import org.jboss.netty.channel.ChannelPipelineFactory;

import com.joeysoft.kc868.client.handler.PacketDecoder;
import com.joeysoft.kc868.client.handler.PacketEncoder;
import com.joeysoft.kc868.client.support.PacketProcessor;

public class KC868ClientPipelineFactory implements ChannelPipelineFactory{
	private PacketProcessor packetProcessor;

	public KC868ClientPipelineFactory(PacketProcessor packetProcessor){
		this.packetProcessor = packetProcessor;
	}

	@Override
	public ChannelPipeline getPipeline() throws Exception {
		 // Create a default pipeline implementation.
        ChannelPipeline pipeline = pipeline();

        // Add the text line codec combination first,
       /* pipeline.addLast("framer", new DelimiterBasedFrameDecoder(
                8192, Delimiters.lineDelimiter()));
        pipeline.addLast("decoder", new StringDecoder());
        pipeline.addLast("encoder", new StringEncoder());*/
        //pipeline.addLast("timeout", new IdleStateHandler(timer, 10, 10, 0));//此两项为添加心跳机制 10秒查看一次在线的客户端channel是否空闲，IdleStateHandler为netty jar包中提供的类
        //pipeline.addLast("hearbeat", new KeepAliveHandler());

        // and then business logic.
        
        pipeline.addLast("decoder", new PacketDecoder(packetProcessor));
        pipeline.addLast("encoder", new PacketEncoder());
        
        pipeline.addLast("handler", new KC868ClientHandler(packetProcessor));

        return pipeline;
	}

}
