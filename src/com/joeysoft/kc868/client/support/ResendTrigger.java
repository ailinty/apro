package com.joeysoft.kc868.client.support;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

import com.joeysoft.kc868.client.packets.ErrorPacket;
import com.joeysoft.kc868.client.packets.InPacket;
import com.joeysoft.kc868.client.packets.OutPacket;
import com.joeysoft.kc868.client.packets.PacketHistory;

public class ResendTrigger<T> implements Callable<T> {
	private PacketProcessor packetProcessor;

	// 检查应答包是否到达的类
    private PacketHistory history;
    
	// 超时队列
    private Queue<OutPacket> timeoutQueue;

    // temp variable
    private String portName;
	
	public ResendTrigger(PacketProcessor packetProcessor) {
        this.packetProcessor = packetProcessor;
        history = packetProcessor.getHistory();
        timeoutQueue = new LinkedList<OutPacket>();
        packetProcessor.executor.schedule(this, Protocol.TIMEOUT_SEND, TimeUnit.MILLISECONDS);
	}
	
    /**
     * 添加一个包到超时队列
     * 
     * @param packet
     * 		发送包对象
     * @param name
     * 		port名称
     */
    public synchronized void add(OutPacket packet) {
        timeoutQueue.offer(packet);
    }
    
    /**
     * 清空重发队列
     */
    public synchronized void clear() {
    	timeoutQueue.clear();
    }
    
    /**
     * 得到超时队列的第一个包，不把它从队列中删除
     * 
     * @return 
     * 		超时队列的第一个包，如果没有，返回null
     */
    public synchronized OutPacket get() {
		return timeoutQueue.peek();
    }
    
    /**
     * 得到超时队列的第一个包，并把它从队列中删除
     * 
     * @return 
     * 		超时队列的第一个包，如果没有，返回null
     */
    public synchronized OutPacket remove() {
    	return timeoutQueue.poll();
    }
    
    /**
     * 删除ack对应的请求包
     * 
     * @param ack
     */
    public synchronized void remove(InPacket ack) {
        int hash = ack.getCommand();
        for(Iterator<OutPacket> i = timeoutQueue.iterator(); i.hasNext(); ) {
        	OutPacket packet = i.next();
        	if(packet.getCommand() == hash) {
        		i.remove();
        		break;
        	}else{
        		if(ack.getCommand() == Protocol.CMD_FILE_WRITE_END 
        				&& packet.getCommand() == Protocol.CMD_FILE_WRITE_OK){
        			i.remove();
        			break;
        		}else if(ack.getCommand() == Protocol.CMD_FILE_READ_ERROR 
        				&& packet.getCommand() == Protocol.CMD_FILE_READ_HEAD){
        			i.remove();
        			break;
        		}else if(ack.getCommand() == Protocol.CMD_FILE_READ_EMPTY 
        				&& packet.getCommand() == Protocol.CMD_FILE_READ_HEAD){
        			i.remove();
        			break;
        		}
        	}
        }
    }
    
    /**
     * 得到下一个包的超时时间
     * 
     * @return 
     * 		下一个包的超时时间，如果队列为空，返回一个固定值
     */
    private long getTimeoutLeft() {
        OutPacket packet = get();
        if(packet == null)
            return Protocol.TIMEOUT_SEND;
        else
            return packet.getTimeout() - System.currentTimeMillis();
    }    
    
    /**
     * 触发超时事件
     * @param p
     */
    private void fireOperationTimeOutEvent(OutPacket packet) {
    	ErrorPacket error = new ErrorPacket(ErrorPacket.ERROR_TIMEOUT);
    	error.timeoutPacket = packet;
    	packetProcessor.addIncomingPacket(error);
    }

	/* (non-Javadoc)
	 * @see java.util.concurrent.Callable#call()
	 */
	public T call() throws Exception {
    	long t = getTimeoutLeft();
		while(t <= 0) {
			OutPacket packet = remove();      
			
			// 发送
			if(packet != null && !history.check(packet, false)) {
				if(packet.needResend()) {
					// 重发次数未到最大，重发
					packetProcessor.send(packet);
				} else {
					// 触发操作超时事件
					fireOperationTimeOutEvent(packet);
				}   
			} 
			
			t = getTimeoutLeft();
		}
		packetProcessor.executor.schedule(this, t, TimeUnit.MILLISECONDS);

    	return null;
	}
}
