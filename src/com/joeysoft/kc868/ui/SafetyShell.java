package com.joeysoft.kc868.ui;

import static com.joeysoft.kc868.resource.Messages.*;

import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseTrackAdapter;
import org.eclipse.swt.events.ShellAdapter;
import org.eclipse.swt.events.ShellEvent;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.ScrollBar;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.Tray;
import org.eclipse.swt.widgets.TrayItem;
import org.eclipse.swt.widgets.Tree;

import com.joeysoft.kc868.KC868;
import com.joeysoft.kc868.SystemConfig;
import com.joeysoft.kc868.client.KC868Client;
import com.joeysoft.kc868.client.event.IPacketListener;
import com.joeysoft.kc868.client.event.PacketEvent;
import com.joeysoft.kc868.client.packets.ErrorPacket;
import com.joeysoft.kc868.client.packets.InPacket;
import com.joeysoft.kc868.client.packets.out.SetWarningOffPacket;
import com.joeysoft.kc868.client.packets.out.SetWarningOnPacket;
import com.joeysoft.kc868.client.packets.out.lineate.SpeakSetSpeakOffPacket;
import com.joeysoft.kc868.client.packets.out.lineate.SpeakSetSpeakOnPacket;
import com.joeysoft.kc868.client.support.PacketProcessor;
import com.joeysoft.kc868.client.support.Protocol;
import com.joeysoft.kc868.db.bean.Device;
import com.joeysoft.kc868.db.bean.Floor;
import com.joeysoft.kc868.db.bean.Lineate;
import com.joeysoft.kc868.db.bean.Relay;
import com.joeysoft.kc868.db.bean.Room;
import com.joeysoft.kc868.db.bean.SensorOut;
import com.joeysoft.kc868.db.bean.SensorIn;
import com.joeysoft.kc868.db.bean.TempSensor;
import com.joeysoft.kc868.db.bean.Transfer;
import com.joeysoft.kc868.db.bo.BODevice;
import com.joeysoft.kc868.db.bo.BOFloor;
import com.joeysoft.kc868.db.bo.BOLineate;
import com.joeysoft.kc868.db.bo.BORelay;
import com.joeysoft.kc868.db.bo.BORoom;
import com.joeysoft.kc868.db.bo.BOSensorIn;
import com.joeysoft.kc868.db.bo.BOSensorOut;
import com.joeysoft.kc868.db.bo.BOSysConfig;
import com.joeysoft.kc868.db.bo.BOTempSensor;
import com.joeysoft.kc868.db.bo.BOTransfer;
import com.joeysoft.kc868.db.util.DictConst;
import com.joeysoft.kc868.resource.IconHolder;
import com.joeysoft.kc868.ui.dialogs.helper.TableDeviceHelper;
import com.joeysoft.kc868.ui.dialogs.helper.TableFloorHelper;
import com.joeysoft.kc868.ui.dialogs.helper.TableLineateHelper;
import com.joeysoft.kc868.ui.dialogs.helper.TableRelayHelper;
import com.joeysoft.kc868.ui.dialogs.helper.TableRoomHelper;
import com.joeysoft.kc868.ui.dialogs.helper.TableSensorInHelper;
import com.joeysoft.kc868.ui.dialogs.helper.TableSensorOutHelper;
import com.joeysoft.kc868.ui.dialogs.helper.TableTempSensorHelper;
import com.joeysoft.kc868.ui.dialogs.helper.TableTransferHelper;
import com.joeysoft.kc868.ui.dialogs.helper.UILineateHelper;
import com.joeysoft.kc868.ui.dialogs.helper.UIRelayHelper;
import com.joeysoft.kc868.ui.helper.MessageBoxHelper;
import com.joeysoft.kc868.ui.helper.ShellLauncher;
import com.joeysoft.kc868.ui.helper.ShellRegistry;
import com.joeysoft.kc868.ui.helper.UITool;
import com.joeysoft.kc868.widgets.ImageButton;

public class SafetyShell implements IPacketListener{

	private Shell shell;
    private Display display;
    
    private IconHolder icons = IconHolder.getInstance();
    
    private MainShell main;
    private PacketProcessor packetProcessor;
    
    private ImageButton btnBack;
    
    private ImageButton compSet, compUnSet, compBugleOpen, compBugleClose;
    private BOSysConfig boSysConfig;
    
    public SafetyShell(MainShell main){
    	this.main = main;
    	this.packetProcessor = main.getClient().getPacketProcessor();
        this.display = main.getDisplay();
        boSysConfig = new BOSysConfig();
    	initialize();
    }

	/**
     * 初始化
     */
    private void initialize() {
    	initLayout();
    	initListeners();
    	
    	hookListener();
    }
    
    /**
     * 初始化界面布局
     */
    private void initLayout() {
    	Composite body = initShell();
    	
    	// 设置背景图片
    	body.setBackgroundImage(icons.getImage(IconHolder.bmpBg));
    	body.setBackgroundMode(SWT.INHERIT_FORCE);
    	body.setBackground(Colors.LOGIN_BACKGROUND);
    	
    	Label label = UITool.createLabel(body, safety);
        label.setBounds(370, 27, 74, 40);
        label.setFont(Colors.LOADING_FONT);
        
        // 返回 按钮
        btnBack =  UITool.createImageButton(body, button_back, icons.getImage(IconHolder.btnBack), 
        		icons.getImage(IconHolder.btnBackOn), icons.getImage(IconHolder.btnBackOn));
        btnBack.setFont(Colors.GLOBAL_FONT);
        btnBack.setBounds(702, 27, 77, 36);
        btnBack.setTextColor(Colors.BLACK);
        
        compSet = UITool.createImageButton(body, safety_on, icons.getImage(IconHolder.btnSafeSet), 
        		icons.getImage(IconHolder.btnSafeSet), icons.getImage(IconHolder.btnSafeSetOn), SWT.BOTTOM);
        compSet.setBounds(155, 150, 185, 181);
        compSet.setCursor(body.getDisplay().getSystemCursor(SWT.CURSOR_HAND));
        compSet.setFont(Colors.LOADING_FONT);
        compSet.addMouseListener(new MouseAdapter() {
            public void mouseUp(MouseEvent e) {
            	sendSetWarningOn();
            }
        });
        
        compUnSet = UITool.createImageButton(body, safety_off, icons.getImage(IconHolder.btnSafeUnSet), 
        		icons.getImage(IconHolder.btnSafeUnSet), icons.getImage(IconHolder.btnSafeUnSetOn), SWT.BOTTOM);
        compUnSet.setBounds(465, 150, 185, 181);
        compUnSet.setCursor(body.getDisplay().getSystemCursor(SWT.CURSOR_HAND));
        compUnSet.setFont(Colors.LOADING_FONT);
    	compUnSet.addMouseListener(new MouseAdapter() {
            public void mouseUp(MouseEvent e) {
            	sendSetWarningOff();
            }
        });
		
        if(SystemConfig.getInstance().isEngine()){
        	compBugleOpen = UITool.createImageButton(body, "", icons.getImage(IconHolder.btnBugleOpen), 
            		icons.getImage(IconHolder.btnBugleOpen), icons.getImage(IconHolder.btnBugleOpenOn));
            compBugleOpen.setBounds(280, 400, 116, 114);
            compBugleOpen.setCursor(body.getDisplay().getSystemCursor(SWT.CURSOR_HAND));
            compBugleOpen.addMouseListener(new MouseAdapter() {
                public void mouseUp(MouseEvent e) {
                	sendSpeakSetSpeakOn();
                }
            });
            
            compBugleClose = UITool.createImageButton(body, "", icons.getImage(IconHolder.btnBugleClose), 
            		icons.getImage(IconHolder.btnBugleClose), icons.getImage(IconHolder.btnBugleCloseOn));
            compBugleClose.setBounds(415, 400, 116, 114);
            compBugleClose.setCursor(body.getDisplay().getSystemCursor(SWT.CURSOR_HAND));
            compBugleClose.addMouseListener(new MouseAdapter() {
                public void mouseUp(MouseEvent e) {
                	sendSpeakSetSpeakOff();
                }
            });
        }
    }
    
    /**
	 * 初始化shell和边框，等等
	 */
	private Composite initShell() {
		int shellStyle = SWT.NO_TRIM;
		shell = new Shell(display, shellStyle);
		
		shell.setImage(icons.getImage(IconHolder.icoHome));
		// 添加事件监听器
		shell.addDisposeListener(new DisposeListener() {
			public void widgetDisposed(DisposeEvent e) {
			}
		});
		shell.addShellListener(new ShellAdapter() {
			public void shellClosed(ShellEvent e) {
				unhookListener();
				main.show();
				main.getShellRegistry().deregisterSafetyShell();
			}
		});
		
		BorderStyler styler = new BorderStyler();
		styler.setCheckMinimizeWhenClose(true);
		styler.setMaximizeWhenDoubleClick(false);
		return styler.decorateShell(shell);
	}
	
    /**
	 * 打开shell，开始事件循环
	 */
	public void open()	{

		// 设置窗口位置和大小
		shell.setSize(800, 634);
		shell.setText(SystemConfig.getInstance().getSystemTitle());
		/*Rectangle bounds = display.getPrimaryMonitor().getBounds();
        Rectangle rect = shell.getBounds();
        int x = bounds.x + (bounds.width - rect.width) / 2;
        int y = bounds.y + (bounds.height - rect.height) / 2;*/
		shell.setLocation(SystemConfig.getInstance().getMainPoint());

		// 打开shell，开始事件循环
		shell.layout();
		shell.open();
		
		// 开始事件循环
		while(!shell.isDisposed()) 	{
			if(!display.readAndDispatch()) 
				display.sleep();
		}
	}
	
	/**
     * 初始化监听器
     */
    private void initListeners() {
    	// 返回按钮鼠标事件
    	btnBack.addMouseListener(new MouseAdapter() {
            public void mouseUp(MouseEvent e) {
            	close();
            }
        });
    }
    
    @Override
	public void packetArrived(PacketEvent e) {
    	InPacket in = (InPacket) e.getSource();
    	
		// 现在开始判断包的类型，作出相应的处理
		switch (in.getCommand()) {
       		case Protocol.CMD_EVENT_SET_WARNING:
       			proccessSetWarningSuccess(in);
       			break;
       		case Protocol.CMD_SPEAK_SET_SPEAK:
       			proccessSpeakSetSpeakSuccess(in);
       			break;
       		case Protocol.CMD_UNKNOWN:
            	processUnknown(in);
            	break;
        }
	}
    
    private void proccessSetWarningSuccess(InPacket in){
    	display.syncExec(new Runnable() {
			public void run() {
				MessageBoxHelper.openInformation(shell, message_opreate_success);
			}
		});
    }
    
    private void proccessSpeakSetSpeakSuccess(InPacket in){
    	display.syncExec(new Runnable() {
			public void run() {
				MessageBoxHelper.openInformation(shell, message_opreate_success);
			}
		});
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
				MessageBoxHelper.openError(shell, message);
			}
		});
	}
    
    private void sendSetWarningOn(){
    	SetWarningOnPacket packet = new SetWarningOnPacket();
    	packetProcessor.send(packet);
    	SystemConfig.getInstance().setSw("Y");
    	boSysConfig.update("SW", "Y");
    }
    
    private void sendSetWarningOff(){
    	SetWarningOffPacket packet = new SetWarningOffPacket();
    	packetProcessor.send(packet);
    	SystemConfig.getInstance().setSw("N");
    	boSysConfig.update("SW", "N");
    }
    
    private void sendSpeakSetSpeakOn(){
    	SpeakSetSpeakOnPacket packet = new SpeakSetSpeakOnPacket();
    	packetProcessor.send(packet);
    }
    
    private void sendSpeakSetSpeakOff(){
    	SpeakSetSpeakOffPacket packet = new SpeakSetSpeakOffPacket();
    	packetProcessor.send(packet);
    }
    
    /**
     * 添加监听器
     */
    private void hookListener(){
    	packetProcessor.getRouter().installProcessor(this);
    }
    
    /**
     * 移除监听器
     */
    private void unhookListener(){
    	packetProcessor.getRouter().removeProcessor(this);
    }
    
	/**
	 * 关闭主shell
	 */
	public void close() {
		if(shell != null && !shell.isDisposed())
			shell.close();
	}
	
	public Shell getShell() {
		return shell;
	}

	public Display getDisplay() {
		return display;
	}
}
