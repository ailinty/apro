package com.joeysoft.kc868;

import java.io.IOException;

import com.joeysoft.kc868.client.KC868Client;
import com.joeysoft.kc868.client.event.IPacketListener;
import com.joeysoft.kc868.client.event.PacketEvent;
import com.joeysoft.kc868.client.packets.InPacket;
import com.joeysoft.kc868.client.packets.in.GetPasswordReplyPacket;
import com.joeysoft.kc868.client.packets.in.GetVersionReplyPacket;
import com.joeysoft.kc868.client.packets.in.OperationStatusErrorPacket;
import com.joeysoft.kc868.client.packets.in.SetVersionReplyPacket;
import com.joeysoft.kc868.client.packets.in.file.FileReadEndReplyPacket;
import com.joeysoft.kc868.client.packets.in.file.FileReadHeadReplyPacket;
import com.joeysoft.kc868.client.packets.in.file.FileReadReplyPacket;
import com.joeysoft.kc868.client.packets.in.file.FileWriteEndReplyPacket;
import com.joeysoft.kc868.client.packets.in.file.FileWriteErrorReplyPacket;
import com.joeysoft.kc868.client.packets.in.file.FileWriteOKReplyPacket;
import com.joeysoft.kc868.client.packets.in.file.FileWriteStartReplyPacket;
import com.joeysoft.kc868.client.packets.out.GetPasswordAdminPacket;
import com.joeysoft.kc868.client.packets.out.GetVersionPacket;
import com.joeysoft.kc868.client.packets.out.SetVersionPacket;
import com.joeysoft.kc868.client.packets.out.file.FileReadHeadPacket;
import com.joeysoft.kc868.client.packets.out.file.FileReadStartPacket;
import com.joeysoft.kc868.client.packets.out.file.FileWriteHeadPacket;
import com.joeysoft.kc868.client.packets.out.file.FileWritePacket;
import com.joeysoft.kc868.client.support.Protocol;
import com.joeysoft.kc868.client.util.FileSegmentor;
import com.joeysoft.kc868.client.util.FileTool;
import com.joeysoft.kc868.common.ZipUtil;
import com.joeysoft.kc868.ui.helper.MessageBoxHelper;

public class KC868Test implements IPacketListener{

	private KC868Client client;
	
	private FileSegmentor fileSegmentor;
	
	private int nextSegment, segmentCount;
	
	private int fileSize;
	
	private int offset;
    
    private byte[] buffer;
    
    private String version;
	
	@Override
	public void packetArrived(PacketEvent e) {
		InPacket in = (InPacket) e.getSource();
    	
   		// 现在开始判断包的类型，作出相应的处理
		switch (in.getCommand()) {
	        case Protocol.CMD_GET_PASSWORD:
	        	processGetPasswordSuccess(in);
	        	break;
	        case Protocol.CMD_GET_VERSION:
	        	processGetVersionSuccess(in);
	        	break;
	        case Protocol.CMD_SET_VERSION:
	        	processSetVersionSuccess(in);
	        	break;
	        case Protocol.CMD_FILE_WRITE_START:
	        	processFileWriteStartSuccess(in);
	        	break;
	        case Protocol.CMD_FILE_WRITE_OK:
	        	processFileWriteOkSuccess(in);
	        	break;
	        case Protocol.CMD_FILE_WRITE_END:
	        	processFileWriteEndSuccess(in);
	        	break;
	        case Protocol.CMD_FILE_WRITE_ERROR:
	        	processFileWriteErrorSuccess(in);
	        	break;
	        	
	        case Protocol.CMD_FILE_READ_HEAD:
	        	processFileReadHeadSuccess(in);
	        	break;
	        case Protocol.CMD_FILE_READ:
	        	processFileReadSuccess(in);
	        	break;
	        case Protocol.CMD_FILE_READ_END:
	        	processFileReadEndSuccess(in);	
	        	break;
	        	
	        case Protocol.CMD_OPERATION_STATUS_ERROR:
	        	processOperationStatusError(in);	
	        	break;
		}		
	}
	
	/**
     * 处理获取密码应答包
     * @param in
     */
    private void processGetPasswordSuccess(InPacket in){
		GetPasswordReplyPacket packet = (GetPasswordReplyPacket)in;
		
		//fileWrite();
		
		fileRead();
		
		//getVersion();
		//setVersion();
	}
    
    private void getVersion(){
    	GetVersionPacket packet = new GetVersionPacket();
		client.getPacketProcessor().send(packet);
    }
    
    private void processGetVersionSuccess(InPacket in){
		GetVersionReplyPacket packet = (GetVersionReplyPacket)in;
		version = packet.getVersion();
		System.out.println(packet.getVersion());
	}
    
    private void setVersion(){
    	SetVersionPacket packet = new SetVersionPacket();
    	version = "001";
    	packet.setVersion(version);
		client.getPacketProcessor().send(packet);
    }
    
    private void processSetVersionSuccess(InPacket in){
		SetVersionReplyPacket packet = (SetVersionReplyPacket)in;
	}
    
    private void processFileWriteStartSuccess(InPacket in){
		FileWriteStartReplyPacket reply = (FileWriteStartReplyPacket)in;
		
		byte[] buf = fileSegmentor.getFragment(nextSegment);
		buffer = new byte[buf.length];
		//buffer = new byte[buf.length + 1];
		System.arraycopy(buf, 0, buffer, 0, buf.length);
		//buffer[buf.length] = '\0';
		
		FileWritePacket packet = new FileWritePacket();
		packet.setBuf(buffer);
		
		if(nextSegment >= segmentCount) {
			fileSegmentor.close();
		}
		
		nextSegment++;
		client.getPacketProcessor().send(packet);
		
		buffer = null;
	}
    
    private void processFileWriteOkSuccess(InPacket in){
		FileWriteOKReplyPacket reply = (FileWriteOKReplyPacket)in;
		
		byte[] buf = fileSegmentor.getFragment(nextSegment);
		buffer = new byte[buf.length];
		//buffer = new byte[buf.length + 1];
		System.arraycopy(buf, 0, buffer, 0, buf.length);
		//buffer[buf.length] = '\0';
		
		FileWritePacket packet = new FileWritePacket();
		packet.setBuf(buffer);
		
		if(nextSegment >= segmentCount) {
			fileSegmentor.close();
		}
		
		nextSegment++;
		client.getPacketProcessor().send(packet);
		
		buffer = null;
	}
    
    private void processFileWriteEndSuccess(InPacket in){
		FileWriteEndReplyPacket packet = (FileWriteEndReplyPacket)in;
		System.out.println("写入成功！");
	}
    
    private void processFileWriteErrorSuccess(InPacket in){
		FileWriteErrorReplyPacket packet = (FileWriteErrorReplyPacket)in;
	}
    
    /**
     * 处理接收文件读取头
     * @param in
     */
    private void processFileReadHeadSuccess(InPacket in){
    	String fileName = "mobile2.txt";
    	
    	FileReadHeadReplyPacket reply = (FileReadHeadReplyPacket)in;
    	if(reply.isFileOk()){
    		FileTool.deleteFile("./config/"+ fileName);
    		fileSize = reply.getFileSize();
    		buffer = new byte[fileSize];
        	// 发送准备接收包
        	FileReadStartPacket packet = new FileReadStartPacket();
    		client.getPacketProcessor().send(packet);
    	}else{
    		System.out.println("读取错误!");
    	}
	}
    
    /**
     * 处理接收文件内容
     * @param in
     */
    private void processFileReadSuccess(InPacket in){
    	FileReadReplyPacket reply = (FileReadReplyPacket)in;
    	int len = reply.getBuffer().length;
    	// 接收byte[]
    	System.arraycopy(reply.getBuffer(), 0, buffer, offset, len);
    	offset += len;
    	System.out.println(offset);
	}
    
    /**
     * 处理接收文件结束
     * @param in
     */
    private void processFileReadEndSuccess(InPacket in){
    	String fileName = "mobile2.txt";
    	
    	FileReadEndReplyPacket reply = (FileReadEndReplyPacket)in;
    	try {
			FileTool.saveFile(buffer, 0, buffer.length, "./config/"+ fileName);
		} catch (IOException e) {
		}
    	buffer = null;
    	offset = 0;
	}
    
   private void processOperationStatusError(InPacket in){
	   OperationStatusErrorPacket reply = (OperationStatusErrorPacket)in;
	   System.out.println("操作错误!");
   }
    
    public void fileRead(){
    	String fileName = "db.zip";
    	
    	FileReadHeadPacket packet = new FileReadHeadPacket();
    	packet.setFileName(fileName);
		client.getPacketProcessor().send(packet);
    }
    
    public void fileWrite(){
    	String fileName = "mobile2.txt";
    	FileWriteHeadPacket packet = new FileWriteHeadPacket();
    	
    	fileSegmentor = new FileSegmentor("./config/"+ fileName, 4096);
    	segmentCount = fileSegmentor.getTotalFragments();
    	
    	packet.setFileName(fileName);
    	packet.setFileSize(fileSegmentor.getFileSize());
    	
		client.getPacketProcessor().send(packet);
    }
	
    public void start(){
    	client = new KC868Client();
    	
    	//client.setHost("hificat.vicp.net");
    	//client.setHost("192.168.1.230");

    	client.setHost("123wuzhouhuai.eicp.net");
    	//client.setHost("192.168.1.210");
		client.setPort(5000);
		client.setPwd("12345678");
		client.connectionClient();
		
		try {
			Thread.sleep(7000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		client.getPacketProcessor().getRouter().installProcessor(this);
		
		GetPasswordAdminPacket packet = new GetPasswordAdminPacket();
		client.getPacketProcessor().send(packet);
    }
    
    public static void zip(){
    	try {
			ZipUtil.zip("./config/"
					+ SystemConfig.getInstance().getDbFileName(),
					"./config/" + SystemConfig.getInstance()
									.getZipFileName());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		/*KC868Test kc868 = new KC868Test();
		kc868.start();*/
		zip();
	}

}
