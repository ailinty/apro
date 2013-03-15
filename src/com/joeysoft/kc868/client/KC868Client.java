package com.joeysoft.kc868.client;

import static com.joeysoft.kc868.resource.Messages.message_box_connection_error;

import java.net.InetSocketAddress;
import java.util.concurrent.Executors;

import org.jboss.netty.bootstrap.ClientBootstrap;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelFactory;
import org.jboss.netty.channel.ChannelFuture;
import org.jboss.netty.channel.socket.nio.NioClientSocketChannelFactory;

import com.joeysoft.kc868.SystemConfig;
import com.joeysoft.kc868.client.support.PacketProcessor;
import com.joeysoft.kc868.ui.MainShell;
import com.joeysoft.kc868.ui.dialogs.LoginDialog;
import com.joeysoft.kc868.ui.helper.MessageBoxHelper;

public class KC868Client implements Runnable{
	
	private ChannelFactory factory;
	private Channel channel;
	
	private PacketProcessor packetProcessor;
	private String host;
	private int port;
	
	private String pwd;
	
	private LoginDialog login;
	private MainShell main;
	
	public KC868Client(){
		packetProcessor = new PacketProcessor();
	}
	
	public KC868Client(MainShell main){
		packetProcessor = new PacketProcessor(main);
		this.main = main;
	}
	
	@Override
	public void run() {
		startClient();
	}
	
	public void connectionClient(){
		new Thread(this).start();
	}
	
	/**
	 * 客户端主程序
	 */
	private boolean startClient(){
		if(channel != null){
        	//channel.getCloseFuture().awaitUninterruptibly();
            channel.close().awaitUninterruptibly();
            channel = null;
        }
        if(factory != null){
        	factory.releaseExternalResources();
        	factory = null;
        }
        
		factory = new NioClientSocketChannelFactory (
				Executors.newCachedThreadPool(),
				Executors.newCachedThreadPool());
		
        ClientBootstrap bootstrap = new ClientBootstrap(factory);
        bootstrap.setPipelineFactory(new KC868ClientPipelineFactory(packetProcessor));
        try{
        	ChannelFuture future = bootstrap.connect(new InetSocketAddress(host, port));
        
        	 channel = future.awaitUninterruptibly().getChannel();
             if (!future.isSuccess()) {
             	future.getCause().printStackTrace();
             	bootstrap.releaseExternalResources();
             	openError();
             	return false;
             }
             packetProcessor.setChannel(channel);
        }catch(Exception e){
        	openError();
        	return false;
        }
        
        connectioned();
        return true;
	}
	
	/**
	 * 连接后， 发送登陆 获取密码包
	 */
	private void connectioned(){
		SystemConfig.getInstance().setConnected(true);
		if(login != null){
			login.getDisplay().syncExec(new Runnable() {
				public void run() {
					if(!login.getDialog().isDisposed()){
						login.login();
					}else{
						MessageBoxHelper.openInformation(login.getDisplay().getActiveShell(), "连接主机成功！");
					}
				}
			});
		}
	}
	
	/**
	 * 主机连接失败
	 */
	private void openError(){
		login.getDisplay().syncExec(new Runnable() {
			public void run() {
				if(!login.getDialog().isDisposed()){
					MessageBoxHelper.openError(login.getDialog(), message_box_connection_error);
					login.setLoginButtonEnabled(true);
				}
			}
		});
	}
	
	/**
	 * 释放资源
	 */
	public void stopClient(){
		
        if(channel != null){
        	//channel.getCloseFuture().awaitUninterruptibly();
            channel.close().awaitUninterruptibly();
            channel = null;
        }
        if(factory != null){
        	factory.releaseExternalResources();
        	factory = null;
        }
        if(packetProcessor != null){
			packetProcessor.release();
		}
	}
	
	public void stopChannel(){
		if(channel != null){
        	//channel.getCloseFuture().awaitUninterruptibly();
            channel.close().awaitUninterruptibly();
            channel = null;
        }
        if(factory != null){
        	factory.releaseExternalResources();
        	factory = null;
        }
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	public PacketProcessor getPacketProcessor() {
		return packetProcessor;
	}

	public void setLogin(LoginDialog login) {
		this.login = login;
	}

	public MainShell getMainShell() {
		return main;
	}

	public void setMainShell(MainShell main) {
		this.main = main;
		this.packetProcessor.setMainShell(main);
	}
}