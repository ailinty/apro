package com.joeysoft.kc868.client.support;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.Callable;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

import org.jboss.netty.channel.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.joeysoft.kc868.SystemConfig;
import com.joeysoft.kc868.client.event.IPacketListener;
import com.joeysoft.kc868.client.event.PacketEvent;
import com.joeysoft.kc868.client.packets.ErrorPacket;
import com.joeysoft.kc868.client.packets.InPacket;
import com.joeysoft.kc868.client.packets.OutPacket;
import com.joeysoft.kc868.client.packets.PacketHelper;
import com.joeysoft.kc868.client.packets.PacketHistory;
import com.joeysoft.kc868.client.packets.in.AlarmReadReplyPacket;
import com.joeysoft.kc868.client.packets.in.UnknownInPacket;
import com.joeysoft.kc868.client.packets.out.event.EventWritePacket;
import com.joeysoft.kc868.client.packets.util.Util;
import com.joeysoft.kc868.db.bean.Alarm;
import com.joeysoft.kc868.ui.MainShell;
import com.joeysoft.kc868.ui.helper.MessageBoxHelper;

public class PacketProcessor implements IPacketListener {

	private static Logger logger = LoggerFactory
			.getLogger(PacketProcessor.class);
	private PacketHelper packetHelper;
	/** 接收队列 */
	private Queue<InPacket> receiveQueue;
	/** 线程执行器 */
	public static final ScheduledExecutorService executor = Executors
			.newSingleThreadScheduledExecutor();
	/** 包事件触发过程 */
	protected Callable<Object> packetEventTrigger;
	/** 包处理器路由器 */
	private ProcessorRouter router;
	/** 包处理器数目 */
	protected static final int PROCESSOR_COUNT = 3;

	/** 重发过程 */
	protected ResendTrigger<Object> resendTrigger;

	private Channel channel;

	/** 表示是否于主机连接 */
	private boolean connctioned;

	private MainShell main;

	public PacketProcessor() {
		router = new ProcessorRouter(PROCESSOR_COUNT);

		packetEventTrigger = new PacketEventTrigger<Object>(this);

		packetHelper = new PacketHelper();
		receiveQueue = new LinkedList<InPacket>();
		resendTrigger = new ResendTrigger<Object>(this);

		router.installProcessor(this);
	}

	public PacketProcessor(MainShell main) {
		this.main = main;
		router = new ProcessorRouter(PROCESSOR_COUNT);

		packetEventTrigger = new PacketEventTrigger<Object>(this);

		packetHelper = new PacketHelper();
		receiveQueue = new LinkedList<InPacket>();
		resendTrigger = new ResendTrigger<Object>(this);

		router.installProcessor(this);
	}

	public void release() {
		resendTrigger.clear();
		executor.shutdownNow();
	}

	/**
	 * 发送包到指定的channel
	 * 
	 * @param channelId
	 * @param packet
	 */
	public void send(OutPacket packet) {
		if(channel != null && channel.isConnected()){
			channel.write(packet);
			logger.debug("发送包" + packet.getSbMessage().toString());
			packet.setTimeout(System.currentTimeMillis() + Protocol.TIMEOUT_SEND);
			this.addResendPacket(packet);
			if (main != null && main.getShellRegistry().isDebugShellOpend()) {
				if(packet instanceof EventWritePacket){
					main.getShellRegistry().getDebugShell()
					.addSendPacket("发送包: " + ((EventWritePacket) packet).getActionStr());
				}else{
					main.getShellRegistry().getDebugShell()
					.addSendPacket("发送包: " + packet.getSbMessage().toString());
				}
			}
		}
	}

	/**
	 * 发送包到指定的channel，无重发机制
	 * 
	 * @param packet
	 */
	public void sendNoResend(OutPacket packet) {
		if(channel != null && channel.isConnected()){
			channel.write(packet);
			logger.debug("发送包" + packet.getSbMessage().toString());
			packet.setTimeout(System.currentTimeMillis() + Protocol.TIMEOUT_SEND);
			if (main != null && main.getShellRegistry().isDebugShellOpend()) {
				if(packet instanceof EventWritePacket){
					main.getShellRegistry().getDebugShell()
					.addSendPacket("发送包: " + ((EventWritePacket) packet).getActionStr());
				}else{
					main.getShellRegistry().getDebugShell()
					.addSendPacket("发送包: " + packet.getSbMessage().toString());
				}
			}
		}
	}

	/**
	 * 删除一个重发包
	 * 
	 * @param packet
	 */
	public void removeResendPacket(InPacket packet) {
		resendTrigger.remove(packet);
	}

	/**
	 * 添加一个包到重发队列
	 * 
	 * @param packet
	 * @param port
	 */
	public void addResendPacket(OutPacket packet) {
		resendTrigger.add(packet);
	}

	/**
	 * 添加一个包到接收队列
	 * 
	 * @param packet
	 */
	public synchronized void addIncomingPacket(InPacket packet) {
		if (packet == null)
			return;
		receiveQueue.offer(packet);
		executor.submit(packetEventTrigger);
	}

	/**
	 * 从接收队列中得到第一个包，并且把这个包从队列中移除
	 * 
	 * @return 接收队列的第一个包，没有返回null
	 */
	public synchronized InPacket removeIncomingPacket() {
		return receiveQueue.poll();
	}

	/**
	 * @return true表示接收队列为空
	 */
	public synchronized boolean isEmpty() {
		return receiveQueue.isEmpty();
	}

	/**
	 * 通知包处理器包到达事件
	 * 
	 * @param e
	 */
	public void firePacketArrivedEvent(PacketEvent e) {
		router.packetArrived(e);
	}

	@Override
	public void packetArrived(PacketEvent e) {
		InPacket in = (InPacket) e.getSource();

		if (in instanceof UnknownInPacket) {
			logger.error("收到一个未知格式包 " + in.getMessage());
			if (main != null && main.getShellRegistry().isDebugShellOpend()) {
				main.getShellRegistry().getDebugShell()
						.addReceivePacket("收到一个未知格式包 " + in.getMessage());
			}
			return;
		}

		// 显示调试信息
		if (in instanceof ErrorPacket) {
			logger.debug("开始处理错误通知包，错误类型："
					+ Util.getErrorString(((ErrorPacket) in).errorCode));
			if (main != null && main.getShellRegistry().isDebugShellOpend()) {
				main.getShellRegistry()
						.getDebugShell()
						.addReceivePacket(
								"开始处理错误通知包，错误类型："
										+ Util.getErrorString(((ErrorPacket) in).errorCode));
			}
		} else {
			logger.debug("开始处理" + in.toString());

			if (main != null && main.getShellRegistry().isDebugShellOpend()) {
				main.getShellRegistry().getDebugShell()
						.addReceivePacket("收到包: " + in.getMessage());
			}
		}

		removeResendPacket(in);

		// 现在开始判断包的类型，作出相应的处理
		switch (in.getCommand()) {
		/*
		 * case Protocol.CMD_GET_PASSWORD: processGetPasswordSuccess(in); break;
		 */
		case Protocol.CMD_UNKNOWN:
			processUnknown(in);
			break;
		}
	}

	/**
	 * 处理未知命令包，有些和协议无关的包也使用这个命令，比如ErrorPacket
	 * 
	 * @param in
	 */
	private void processUnknown(InPacket in) {
		if (in instanceof ErrorPacket) {
			ErrorPacket error = (ErrorPacket) in;
			switch (error.errorCode) {
			}
		}
	}

	/**
	 * @return Returns the monitor.
	 */
	public PacketHistory getHistory() {
		return packetHelper.getHistory();
	}

	public PacketHelper getPacketHelper() {
		return packetHelper;
	}

	public Channel getChannel() {
		return channel;
	}

	public void setChannel(Channel channel) {
		this.channel = channel;
	}

	public ProcessorRouter getRouter() {
		return router;
	}

	public boolean isConnctioned() {
		return connctioned;
	}

	public void setConnctioned() {
		this.connctioned = true;
	}
	
	public void setDisConnctioned() {
		/*if(this.connctioned && !SystemConfig.getInstance().isClosed()){
				main.getDisplay().syncExec(new Runnable() {
					public void run() {
						if(main.getDisplay().getActiveShell() != null){
							MessageBoxHelper.openWarning(main.getDisplay().getActiveShell(), "与主机断开连接！");
						}
					}
				});
		}*/
		resendTrigger.clear();
		this.connctioned = false;
	}

	public MainShell getMainShell() {
		return main;
	}

	public void setMainShell(MainShell main) {
		this.main = main;
	}
}
