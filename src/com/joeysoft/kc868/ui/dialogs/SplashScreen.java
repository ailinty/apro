package com.joeysoft.kc868.ui.dialogs;

import static com.joeysoft.kc868.resource.Messages.*;

import java.io.File;
import java.io.IOException;
import java.util.zip.ZipException;

import org.apache.commons.io.FileUtils;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.events.ShellAdapter;
import org.eclipse.swt.events.ShellEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.graphics.Pattern;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

import com.joeysoft.kc868.KC868;
import com.joeysoft.kc868.SystemConfig;
import com.joeysoft.kc868.client.KC868Client;
import com.joeysoft.kc868.client.event.IPacketListener;
import com.joeysoft.kc868.client.event.PacketEvent;
import com.joeysoft.kc868.client.packets.ErrorPacket;
import com.joeysoft.kc868.client.packets.InPacket;
import com.joeysoft.kc868.client.packets.OutPacket;
import com.joeysoft.kc868.client.packets.in.AlarmReadReplyPacket;
import com.joeysoft.kc868.client.packets.in.GetVersionReplyPacket;
import com.joeysoft.kc868.client.packets.in.ReadHostReplyPacket;
import com.joeysoft.kc868.client.packets.in.file.FileReadErrorReplyPacket;
import com.joeysoft.kc868.client.packets.in.file.FileReadHeadReplyPacket;
import com.joeysoft.kc868.client.packets.in.file.FileReadReplyPacket;
import com.joeysoft.kc868.client.packets.in.lineate.RelayReadNowReplyPacket;
import com.joeysoft.kc868.client.packets.out.AlarmReadPacket;
import com.joeysoft.kc868.client.packets.out.GetVersionPacket;
import com.joeysoft.kc868.client.packets.out.ReadHostPacket;
import com.joeysoft.kc868.client.packets.out.file.FileReadHeadPacket;
import com.joeysoft.kc868.client.packets.out.file.FileReadNewPacket;
import com.joeysoft.kc868.client.packets.out.file.FileReadStartPacket;
import com.joeysoft.kc868.client.packets.out.lineate.RelayReadNowPacket;
import com.joeysoft.kc868.client.support.Protocol;
import com.joeysoft.kc868.client.util.FileTool;
import com.joeysoft.kc868.common.StringUtil;
import com.joeysoft.kc868.common.ZipUtil;
import com.joeysoft.kc868.db.DBConnection;
import com.joeysoft.kc868.db.bean.Alarm;
import com.joeysoft.kc868.db.bo.BOAlarm;
import com.joeysoft.kc868.db.util.DictConst;
import com.joeysoft.kc868.resource.IconHolder;
import com.joeysoft.kc868.ui.BorderStyler;
import com.joeysoft.kc868.ui.Colors;
import com.joeysoft.kc868.ui.DebugShell;
import com.joeysoft.kc868.ui.helper.MessageBoxHelper;
import com.joeysoft.kc868.xsd.GlobalUtil;
import com.joeysoft.kc868.xsd.global.GlobalSetting;

import org.eclipse.swt.layout.FillLayout;

public class SplashScreen extends Dialog implements IPacketListener{

	private Display display;
	
	private Shell dialog;
	
	private boolean ok;
	
	// 成功同步
	private boolean synced;
	
	// IconHolder实例
    private IconHolder icons = IconHolder.getInstance();
    
    private KC868Client client;
    
    private boolean isNew = true;
    
    //private String fileName = "backup.zip";
    private String fileName = SystemConfig.getInstance().getZipFileName();
    
    private int fileSize;

    private int nextSegment = 1, segmentCount = 0;
    // 每个分片大小
    private int segmentSize = 1024;
    
    private int offset;
    // 进度条
    private int percent;
    
    private byte[] buffer;
    private Canvas canvas;
    private GC gc = null;
    // 系统设置文件
    private GlobalSetting global;
    // 当前数据库版本号
    private String dbVersion;
    // 主机数据库版本号
    private String hostDBVersion;
    
    // 当前读取定时器编号
    private int alarmCrrentIndex = 1;

    private BOAlarm boAlarm;
    
    private boolean isSyncError;
    
    private Shell shell;
    private DebugShell debugShell;
    
	/**
	 * Create the dialog.
	 * @param parent
	 * @param style
	 */
	public SplashScreen(Shell shell) {
		super(shell, SWT.NO_TRIM | SWT.NO_BACKGROUND | SWT.ON_TOP);
		this.shell = shell;
        display = shell.getDisplay();
        
        boAlarm = new BOAlarm();
	}

	/**
	 * Open the dialog.
	 * @return the result
	 */
	public Object open() {
		// create dialog
	    Shell parent = getParent();
	    Display display = parent.getDisplay();
		dialog = new Shell(parent, getStyle());
		
		initLayout();
		
		initValue();
		
		initListeners();
		hookListener();
		
		Image image = icons.getImage(IconHolder.bmpBgLoading);
		dialog.setBackgroundImage(image);
		ImageData imdata = image.getImageData();
		dialog.setSize(imdata.width, imdata.height);
		dialog.setBackgroundMode(SWT.INHERIT_FORCE);
		dialog.setLayout(new FillLayout());
		
		Rectangle bounds = display.getPrimaryMonitor().getBounds();
        Rectangle rect = dialog.getBounds();
        int x = bounds.x + (bounds.width - rect.width) / 2;
        int y = bounds.y + (bounds.height - rect.height) / 2;
        dialog.setLocation(x, y);
        
		dialog.open();
		
		if(SystemConfig.getInstance().isLoginSync()){
			sendSyncPacket();
		}else{
			alarmCrrentIndex = 1;
			sendAlarmReadPacket();
		}
		//sendGetVersion();
		//syncFinished();
		while(!dialog.isDisposed()) 
			if(!display.readAndDispatch()) 
			    display.sleep();
		return ok;
	}

	/**
	 * Create contents of the dialog.
	 */
	private void initLayout() {
		BorderStyler styler = new BorderStyler();
    	styler.setHideWhenMinimize(false);
    	//styler.setShowButton(false);
    	styler.setResizable(false);
    	Composite center = styler.decorateShell(dialog);
    	
    	center.setLayout(new FillLayout());
    	canvas = new Canvas(center, SWT.NONE);
    	canvas.setBackgroundMode(SWT.INHERIT_FORCE);
    	gc = new GC(canvas);
		gc.setLineWidth(8);
		gc.setForeground(new Color(Display.getDefault(), 0, 255, 255));
		
		canvas.addPaintListener(new PaintListener() {  
    		public void paintControl(PaintEvent arg0) {
    			arg0.gc.setAntialias (SWT.ON);
    			arg0.gc.setLineWidth(8);
    			arg0.gc.setForeground(new Color(Display.getDefault(), 0, 255, 255));  
    			arg0.gc.drawArc(261,137, 268, 268, 0 , percent * 5);
    			
    			Color background = gc.getBackground ();
				Pattern p = new Pattern (arg0.display, 261,137, 268, 268, background, 0, background, 0);
    			arg0.gc.setBackgroundPattern(p);
    			
    			arg0.gc.setForeground(Colors.WHITE);  
    			arg0.gc.setFont(Colors.LOADING_FONT);
    			arg0.gc.drawText(StringUtil.substring(splash_loading, 0, 8, "", "GBK"), 328, 211);
    			arg0.gc.drawText(StringUtil.substring(splash_loading, 8, StringUtil.length(splash_loading), "", "GBK"), 328, 262);
    			arg0.gc.drawText(splash_waiting, 345, 313);
    			p.dispose();
    			arg0.gc.dispose();
    		}
    	});
	}

	/**
     * 初始化值
     */
    private void initValue() {
    	alarmCrrentIndex = 1;
    	
    	// 检查配置文件，如果不存在则创建一个
    	File globalFile = new File(KC868.GLOBAL);
    	// 读入系统设置文件
    	global = GlobalUtil.load(globalFile);
    	if(global == null)
    		global = GlobalUtil.createDefault();
    	
    	dbVersion = global.getDBVersion();
    }
    
    /**
     * 保存值
     */
    private void saveValue(){
    	// 检查配置文件，如果不存在则创建一个
    	File globalFile = new File(KC868.GLOBAL);
    	// 读入系统设置文件
    	global = GlobalUtil.load(globalFile);
    	if(global == null)
    		global = GlobalUtil.createDefault();
    	
    	global.setDBVersion(dbVersion);
    	GlobalUtil.save(globalFile, global);
    }
    
	/**
     * 初始化监听器
     */
    private void initListeners() {
    	dialog.addShellListener(new ShellAdapter() {
			public void shellClosed(ShellEvent e) {
				if(gc != null) gc.dispose();
				if(canvas != null) canvas.dispose();
				unhookListener();
			}
		});
    	
    	dialog.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent e) {
				if (e.stateMask == (SWT.CTRL | SWT.SHIFT) && e.keyCode == 'd') {
					if(debugShell == null){
						debugShell = new DebugShell(shell);
					}
					
					debugShell.open();
				}
			}
		});
    }
    
	/**
     * 添加监听器
     */
    private void hookListener(){
    	client.getPacketProcessor().getRouter().installProcessor(this);
    }
	
    /**
     * 移除监听器
     */
    private void unhookListener(){
    	client.getPacketProcessor().getRouter().removeProcessor(this);
    }
    
    /**
     * 发送get Version 包
     */
    private void sendGetVersion(){
    	GetVersionPacket packet = new GetVersionPacket();
    	client.getPacketProcessor().send(packet);
    	addDebugSendPacket(packet);
    }
    
    /**
     * 发送读文件包
     */
    private void sendSyncPacket(){
    	isSyncError = false;
    	FileReadHeadPacket packet = new FileReadHeadPacket();
    	packet.setFileName(fileName);
		client.getPacketProcessor().send(packet);
		addDebugSendPacket(packet);
    }
    
    /**
     * 发送读取定时器
     */
    private void sendAlarmReadPacket(){
    	/*try {
			Thread.sleep(300);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}*/
    	AlarmReadPacket packet = new AlarmReadPacket();
    	packet.setAlarmNum(alarmCrrentIndex);
    	client.getPacketProcessor().send(packet);
    	addDebugSendPacket(packet);
    }
    
    
    /**
     * 同步完成
     * 同步主机文件结束、不需要同步
     */
    private void syncFinished(){
    	buffer = null;
    	offset = 0;
    	percent = 0;
    	synced = true;
    	dbVersion = hostDBVersion;
    	saveValue();
    	
    	display.syncExec(new Runnable() {
			public void run() {
				gc.drawArc(261,137, 268, 268, 0 , 360);
				if(isSyncError){
					MessageBoxHelper.openWarning(dialog, error_config_file);
				}
				// 关闭登陆窗口
				close();
				
			}
		});
    }
    
    @Override
	public void packetArrived(PacketEvent e) {
    	InPacket in = (InPacket) e.getSource();
    	addDebugReceivePacket(in);
    	 // 现在开始判断包的类型，作出相应的处理
        switch (in.getCommand()) {
	        case Protocol.CMD_GET_VERSION:
	        	processGetVersionSuccess(in);
	        	break;
	        case Protocol.CMD_FILE_READ_ERROR:
	        	processFileReadErrorSuccess(in);
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
	        case Protocol.CMD_ALARM_READ:
				processAlarmReadSuccess(in);
				break;
	        case Protocol.CMD_READ_HOST:
       			proccessReadHostSuccess(in);
       			break;
	        case Protocol.CMD_RELAY_READ_NOW:
	        	proccessRelayReadNowSuccess(in);
       			break;
	        case Protocol.CMD_UNKNOWN:
	        	processUnknown(in);
	        	break;
        }
	}

    private void sendReadHost(){
    	ReadHostPacket packet = new ReadHostPacket();
    	client.getPacketProcessor().send(packet);
    	addDebugSendPacket(packet);
    }
    
    private void proccessReadHostSuccess(InPacket in){
    	final ReadHostReplyPacket reply = (ReadHostReplyPacket)in;
		SystemConfig.getInstance().setHardVer(reply.getHardVer());
		SystemConfig.getInstance().setHostTemp(reply.getHostTemp());
		SystemConfig.getInstance().setHostType(reply.getHostType());
		SystemConfig.getInstance().setSoftVer(reply.getSoftVer());
		percent += 10;
    	display.syncExec(new Runnable() {
			public void run() {
				gc.setAntialias(SWT.ON);
				gc.drawArc(261,137, 268, 268, 0 , percent * 5);
			}
    	});
    	
    	// 工程版本
    	if(SystemConfig.getInstance().isEngine()){
    		sendRelayReadNow();
    	}else{
    		syncFinished();
    	}
    }
    
    private void sendRelayReadNow(){
    	RelayReadNowPacket packet = new RelayReadNowPacket();
    	client.getPacketProcessor().send(packet);
    	addDebugSendPacket(packet);
    }
    
    private void proccessRelayReadNowSuccess(InPacket in){
    	RelayReadNowReplyPacket reply = (RelayReadNowReplyPacket)in;
    	SystemConfig.getInstance().setRelayStatus(reply.getRelayStatus());
    	syncFinished();
    }
    
    /**
     * 处理获取数据库版本号
     * @param in
     */
    private void processGetVersionSuccess(InPacket in){
		GetVersionReplyPacket packet = (GetVersionReplyPacket)in;
		hostDBVersion = packet.getVersion();
		
		// 当前数据库版本号不等于主机数据库版本号时， 发送同步主机
		if(!dbVersion.equals(hostDBVersion)){
			sendSyncPacket();
		}else{
			// 不需要同步
			//syncFinished();
			sendReadHost();
		}
    }
    
    /**
     * 处理文件读取错误
     * @param in
     */
    private void processFileReadErrorSuccess(InPacket in){
    	FileReadErrorReplyPacket reply = (FileReadErrorReplyPacket)in;
		/*display.syncExec(new Runnable() {
			public void run() {
				MessageBoxHelper.openError(dialog, message_box_sync_error);
			}
		});*/
    	
    	//syncFinished();
    	alarmCrrentIndex = 1;
    	sendAlarmReadPacket();
	}
    
    /**
     * 处理接收文件读取头
     * @param in
     */
    private void processFileReadHeadSuccess(InPacket in){
    	offset = 0;
    	FileReadHeadReplyPacket reply = (FileReadHeadReplyPacket)in;
    	if(reply.isFileOk()){
    		FileTool.deleteFile(KC868.CONFIG_DIR + fileName);
    		fileSize = reply.getFileSize();
    		buffer = new byte[fileSize];
    		
    		if(isNew){ // 新协议
    			nextSegment = 1;
    			//segmentCount = (int)(fileSize - 1) / segmentSize + 1;
    			segmentCount = reply.getFileSegmentCount();
            	// 发送接收包
        		FileReadNewPacket packet = new FileReadNewPacket();
        		packet.setSegment(nextSegment++);
        		client.getPacketProcessor().send(packet);
        		addDebugSendPacket(packet);
    		}else {
    			// 发送准备接收包
            	FileReadStartPacket packet = new FileReadStartPacket();
        		client.getPacketProcessor().send(packet);
        		addDebugSendPacket(packet);
    		}
    	}else{
    		buffer = null;
	    	offset = 0;
	    	percent = 0;
	    	synced = false;
	    	
    		display.syncExec(new Runnable() {
    			public void run() {
    				MessageBoxHelper.openError(dialog, message_box_sync_error);
    				close();
    			}
    		});
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
    	percent = offset * 72 / (fileSize + 72);
    	
    	if(isNew){
        	if(nextSegment <= segmentCount){
/*        		try {
					Thread.sleep(300);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
*/        		FileReadNewPacket packet = new FileReadNewPacket();
        		packet.setSegment(nextSegment++);
        		client.getPacketProcessor().send(packet);
        		addDebugSendPacket(packet);
        	}else{ // 读文件结束
        		processFileReadEndSuccess(null);
        	}
    	}
    	display.syncExec(new Runnable() {
			public void run() {
				gc.setAntialias(SWT.ON);
				gc.drawArc(261,137, 268, 268, 0 , percent * 5);
			}
    	});
	}
    
    /**
     * 处理接收文件结束
     * @param in
     */
    private void processFileReadEndSuccess(InPacket in){
    	try {
			FileTool.saveFile(buffer, 0, buffer.length, KC868.CONFIG_DIR + fileName);
			try {
				DBConnection.freeConnection();
				FileTool.deleteFile(KC868.CONFIG_DIR + SystemConfig.getInstance().getDbLockFileName());
				File dbFile = new File(KC868.CONFIG_DIR + SystemConfig.getInstance().getDbFileName());
				if(dbFile.exists()){
					FileUtils.copyFile(dbFile, new File(KC868.CONFIG_DIR + "db_bak.mdb"));
				}
				FileTool.deleteFile(KC868.CONFIG_DIR + SystemConfig.getInstance().getDbFileName());
				ZipUtil.unzip(KC868.CONFIG_DIR + fileName, KC868.CONFIG_DIR);
				FileTool.deleteFile(KC868.CONFIG_DIR + "db_bak.mdb");
			} catch (Exception e) {
				e.printStackTrace();
				/*buffer = null;
		    	offset = 0;
		    	percent = 0;
		    	synced = false;*/
				isSyncError = true;
				File File = new File(KC868.CONFIG_DIR + "db_bak.mdb");
				if(File.exists()){
					FileUtils.copyFile(File,new File(KC868.CONFIG_DIR + SystemConfig.getInstance().getDbFileName()));
				}
				FileTool.deleteFile(KC868.CONFIG_DIR + "db_bak.mdb");
			}/*catch (Exception e) {
				e.printStackTrace();
				buffer = null;
		    	offset = 0;
		    	percent = 0;
		    	synced = false;
				display.syncExec(new Runnable() {
					public void run() {
						MessageBoxHelper.openError(dialog, message_box_sync_error);
						close();
					}
				});
			}*/
			// 同步完成
			//syncFinished();
			alarmCrrentIndex = 1;
			sendAlarmReadPacket();
		} catch (IOException e) {
			buffer = null;
	    	offset = 0;
	    	percent = 0;
	    	synced = false;
			display.syncExec(new Runnable() {
				public void run() {
					MessageBoxHelper.openError(dialog, message_box_sync_error);
					close();
				}
			});
		}
	}
    
    /**
     * 读取定时器事件
     * @param in
     */
    private void processAlarmReadSuccess(InPacket in) {
    	if(alarmCrrentIndex >= DictConst.ALARM_COUT){
    		sendReadHost();
    	}else{
    		AlarmReadReplyPacket reply = (AlarmReadReplyPacket) in;
    		Alarm alarm = new Alarm();
    		alarm.setAlarmId(reply.getAlarmNum());
    		alarm.setAlarmWeek(reply.getWeek());
    		alarm.setAlarmHour(reply.getHours());
    		alarm.setAlarmMinute(reply.getMinutes());
    		alarm.setAlarmSecond(reply.getSeconds());
    		//SystemConfig.getInstance().putAlarm(alarm);
    		boAlarm.update(alarm);
    		alarmCrrentIndex++;
    		sendAlarmReadPacket();
    	}
    	if(!SystemConfig.getInstance().isLoginSync()){
    		percent += 10;
        	display.syncExec(new Runnable() {
    			public void run() {
    				gc.setAntialias(SWT.ON);
    				gc.drawArc(261,137, 268, 268, 0 , percent * 5);
    			}
        	});
    	}
	}
    
    /**
     * 处理未知应答包，一般是主机有问题
     * @param in
     */
    private void processUnknown(InPacket in){
    	String errorMessage = "";
    	if(in instanceof ErrorPacket) {
            ErrorPacket error = (ErrorPacket)in;
            switch(error.errorCode) {
            case ErrorPacket.ERROR_REMOTE_CLOSED:
            	errorMessage = message_box_disconnection;
            	break;
            case ErrorPacket.ERROR_TIMEOUT:
            	errorMessage = message_box_receive_timeout;
            	break;
        }
    	}else{
    		errorMessage = message_box_unknown;
    	}
        
    	final String message = errorMessage;
		display.syncExec(new Runnable() {
			public void run() {
				MessageBoxHelper.openError(dialog, message_box_common_error_title, message);
				close();
			}
		});
	}
    
    public void close(){
    	if(debugShell != null){
    		debugShell.close();
    	}
    	dialog.close();
    }
    
    public void addDebugSendPacket(OutPacket packet){
    	if(debugShell != null){
    		debugShell.addSendPacket("发送包: " + packet.getSbMessage().toString());
    	}
    }

    public void addDebugReceivePacket(InPacket in){
    	if(debugShell != null){
    		debugShell.addReceivePacket("收到包: " + in.getMessage());
    	}
    	
    }
    
	public boolean isSynced() {
		return synced;
	}

	public void setClient(KC868Client client) {
		this.client = client;
	}
}
