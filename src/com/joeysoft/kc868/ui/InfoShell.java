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
import com.joeysoft.kc868.client.packets.in.FindSignalNowReplyPacket;
import com.joeysoft.kc868.client.packets.in.FindSimNowOkReplyPacket;
import com.joeysoft.kc868.client.packets.in.ReadHostReplyPacket;
import com.joeysoft.kc868.client.packets.out.FindSignalNowPacket;
import com.joeysoft.kc868.client.packets.out.FindSimNowPacket;
import com.joeysoft.kc868.client.packets.out.ReadHostPacket;
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

public class InfoShell implements IPacketListener{

	private Shell shell;
    private Display display;
    
    private IconHolder icons = IconHolder.getInstance();
    
    private MainShell main;
    private PacketProcessor packetProcessor;
    
    private ImageButton btnBack;
    
    private Label lbHostType, lbPCSoft, lbHostSoft, lbHostHard, lbHostTemp, lbSIMStatus, lbSIMDegree; 
    
    public InfoShell(MainShell main){
    	this.main = main;
    	this.packetProcessor = main.getClient().getPacketProcessor();
        this.display = main.getDisplay();
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
    	body.setBackgroundImage(icons.getImage(IconHolder.bmpBgSub));
    	body.setBackgroundMode(SWT.INHERIT_FORCE);
    	body.setBackground(Colors.LOGIN_BACKGROUND);
    	
    	Label label = UITool.createLabel(body, info);
        label.setBounds(370, 27, 74, 40);
        label.setFont(Colors.LOADING_FONT);
        
        // 返回 按钮
        btnBack =  UITool.createImageButton(body, button_back, icons.getImage(IconHolder.btnBack), 
        		icons.getImage(IconHolder.btnBackOn), icons.getImage(IconHolder.btnBackOn));
        btnBack.setFont(Colors.GLOBAL_FONT);
        btnBack.setBounds(702, 27, 77, 36);
        btnBack.setTextColor(Colors.BLACK);
        
        /*label = UITool.createLabel(body, "主机类型：");
        label.setBounds(134, 200, 60, 20);
        lbHostType = UITool.createLabel(body, SystemConfig.getInstance().getHostType());
        lbHostType.setBounds(200, 200, 100, 20);*/
        
        label = UITool.createLabel(body, host_hard_ver);
        label.setBounds(134, 240, 80, 20);
        lbHostHard = UITool.createLabel(body, SystemConfig.getInstance().getHardVer());
        lbHostHard.setBounds(220, 240, 100, 20);
        
        label = UITool.createLabel(body, host_soft_ver);
        label.setBounds(134, 280, 80, 20);
        lbHostSoft = UITool.createLabel(body, SystemConfig.getInstance().getSoftVer());
        lbHostSoft.setBounds(220, 280, 100, 20);
        
        label = UITool.createLabel(body, host_temp);
        label.setBounds(134, 320, 60, 20);
        lbHostTemp = UITool.createLabel(body, SystemConfig.getInstance().getHostTemp());
        lbHostTemp.setBounds(200, 320, 30, 20);
        
        label = UITool.createLabel(body, "");
    	label.setBounds(229, 315, 19, 20);
    	label.setImage(icons.getImage(IconHolder.icoC));
        
        label = UITool.createLabel(body, pc_soft_ver);
        label.setBounds(134, 360, 80, 20);
        lbPCSoft = UITool.createLabel(body, SystemConfig.getInstance().getPcSoft());
        lbPCSoft.setBounds(220, 360, 100, 20);
        
        label = UITool.createLabel(body, sim_status);
        label.setBounds(134, 400, 80, 20);
        lbSIMStatus = UITool.createLabel(body, "");
        lbSIMStatus.setBounds(220, 393, 35, 25);
        lbSIMStatus.setImage(icons.getImage(IconHolder.icoSim1));
        
        label = UITool.createLabel(body, sim_singel);
        label.setBounds(134, 440, 80, 20);
        lbSIMDegree = UITool.createLabel(body, "");
        lbSIMDegree.setBounds(220, 433, 32, 16);
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
				main.getShellRegistry().deregisterInfoShell();
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
		if(SystemConfig.getInstance().hasSim()){
			sendFindSimNow();
		}
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
       		case Protocol.CMD_FIND_SIM_NOW:
       			proccessFindSimNowSuccess(in);
       			break;
       		case Protocol.CMD_FIND_SIGNAL_NOW:
       			proccessFindSignalNowSuccess(in);
       			break;
       		case Protocol.CMD_UNKNOWN:
            	processUnknown(in);
            	break;
        }
	}
    
    private void proccessFindSimNowSuccess(final InPacket in){
    	display.syncExec(new Runnable() {
			public void run() {
				if(in instanceof FindSimNowOkReplyPacket){
					lbSIMStatus.setImage(icons.getImage(IconHolder.icoSim2));
				}else{
					lbSIMStatus.setImage(icons.getImage(IconHolder.icoSim1));
				}
			}
    	});
    	/*try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}*/
    	sendFindSignalNow();
    }
    
    private void proccessFindSignalNowSuccess(final InPacket in){
    	final FindSignalNowReplyPacket reply = (FindSignalNowReplyPacket)in;
    	display.syncExec(new Runnable() {
			public void run() {
				if(31 == reply.getDegree()){
					lbSIMDegree.setImage(icons.getImage(IconHolder.icoSigna5));
				}else if(30 < reply.getDegree() && reply.getDegree() >= 16){
					lbSIMDegree.setImage(icons.getImage(IconHolder.icoSigna4));
				}else if(15 < reply.getDegree() && reply.getDegree() >= 9){
					lbSIMDegree.setImage(icons.getImage(IconHolder.icoSigna3));
				}else if(8 < reply.getDegree() && reply.getDegree() >= 1){
					lbSIMDegree.setImage(icons.getImage(IconHolder.icoSigna2));
				}else{
					lbSIMDegree.setImage(icons.getImage(IconHolder.icoSigna1));
				}
					
			}
    	});
    }
    
    private void sendFindSignalNow(){
    	FindSignalNowPacket packet = new FindSignalNowPacket();
    	packetProcessor.send(packet);
    }
    
    private void sendFindSimNow(){
    	FindSimNowPacket packet = new FindSimNowPacket();
    	packetProcessor.send(packet);
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
    
	/**
	 * 关闭主shell
	 */
	public void close() {
		if(shell != null && !shell.isDisposed())
			shell.close();
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
    
	public Shell getShell() {
		return shell;
	}

	public Display getDisplay() {
		return display;
	}
}
