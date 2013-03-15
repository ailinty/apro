package com.joeysoft.kc868.ui;

import static com.joeysoft.kc868.resource.Messages.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CCombo;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseTrackAdapter;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.ShellAdapter;
import org.eclipse.swt.events.ShellEvent;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.ProgressBar;
import org.eclipse.swt.widgets.Scale;
import org.eclipse.swt.widgets.ScrollBar;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.Text;
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
import com.joeysoft.kc868.client.packets.OutPacket;
import com.joeysoft.kc868.client.packets.in.AlarmReadReplyPacket;
import com.joeysoft.kc868.client.packets.in.SetPasswordReplyPacket;
import com.joeysoft.kc868.client.packets.out.AlarmReadPacket;
import com.joeysoft.kc868.client.packets.out.AlarmWritePacket;
import com.joeysoft.kc868.client.packets.out.ResetHostPacket;
import com.joeysoft.kc868.client.packets.out.SetPasswordAdminPacket;
import com.joeysoft.kc868.client.packets.out.event.EventConnectPacket;
import com.joeysoft.kc868.client.packets.out.event.EventDelayPacket;
import com.joeysoft.kc868.client.packets.out.event.EventDisConnectPacket;
import com.joeysoft.kc868.client.packets.out.event.EventWritePacket;
import com.joeysoft.kc868.client.packets.util.SensorPacketUtil;
import com.joeysoft.kc868.client.support.PacketProcessor;
import com.joeysoft.kc868.client.support.Protocol;
import com.joeysoft.kc868.db.bean.Device;
import com.joeysoft.kc868.db.bean.DeviceKey;
import com.joeysoft.kc868.db.bean.Floor;
import com.joeysoft.kc868.db.bean.JSonFileWrite;
import com.joeysoft.kc868.db.bean.Lineate;
import com.joeysoft.kc868.db.bean.Relay;
import com.joeysoft.kc868.db.bean.Room;
import com.joeysoft.kc868.db.bean.SensorOut;
import com.joeysoft.kc868.db.bean.SensorIn;
import com.joeysoft.kc868.db.bean.TempSensor;
import com.joeysoft.kc868.db.bean.Transfer;
import com.joeysoft.kc868.db.bean.TsfSensor;
import com.joeysoft.kc868.db.bo.BODevice;
import com.joeysoft.kc868.db.bo.BODeviceKey;
import com.joeysoft.kc868.db.bo.BOFloor;
import com.joeysoft.kc868.db.bo.BOLineate;
import com.joeysoft.kc868.db.bo.BORelay;
import com.joeysoft.kc868.db.bo.BORoom;
import com.joeysoft.kc868.db.bo.BOSensorIn;
import com.joeysoft.kc868.db.bo.BOSensorOut;
import com.joeysoft.kc868.db.bo.BOSysConfig;
import com.joeysoft.kc868.db.bo.BOTempSensor;
import com.joeysoft.kc868.db.bo.BOTransfer;
import com.joeysoft.kc868.db.bo.BOTransferSensor;
import com.joeysoft.kc868.db.util.DictConst;
import com.joeysoft.kc868.resource.IconHolder;
import com.joeysoft.kc868.resource.IconUtil;
import com.joeysoft.kc868.ui.dialogs.InformationDialog;
import com.joeysoft.kc868.ui.dialogs.helper.TableDeviceHelper;
import com.joeysoft.kc868.ui.dialogs.helper.TableFloorHelper;
import com.joeysoft.kc868.ui.dialogs.helper.TableLineateHelper;
import com.joeysoft.kc868.ui.dialogs.helper.TableRelayHelper;
import com.joeysoft.kc868.ui.dialogs.helper.TableRoomHelper;
import com.joeysoft.kc868.ui.dialogs.helper.TableSensorInHelper;
import com.joeysoft.kc868.ui.dialogs.helper.TableSensorOutHelper;
import com.joeysoft.kc868.ui.dialogs.helper.TableTempSensorHelper;
import com.joeysoft.kc868.ui.dialogs.helper.TableTransferHelper;
import com.joeysoft.kc868.ui.dialogs.helper.UIAlarmHelper;
import com.joeysoft.kc868.ui.dialogs.helper.UIDeviceHelper;
import com.joeysoft.kc868.ui.dialogs.helper.UILineateHelper;
import com.joeysoft.kc868.ui.dialogs.helper.UIRelayHelper;
import com.joeysoft.kc868.ui.dialogs.helper.UISceneBindHelper;
import com.joeysoft.kc868.ui.dialogs.helper.UISceneHelper;
import com.joeysoft.kc868.ui.helper.MessageBoxHelper;
import com.joeysoft.kc868.ui.helper.ShellLauncher;
import com.joeysoft.kc868.ui.helper.ShellRegistry;
import com.joeysoft.kc868.ui.helper.UITool;
import com.joeysoft.kc868.widgets.AlphaComposite;
import com.joeysoft.kc868.widgets.ImageButton;

/**
 * 智能设置
 * @author JOEY
 *
 */
public class SmartShell implements IPacketListener{

	private Shell shell;
    private Display display;
    
    // 所有控件
    private ImageButton btnBack;
    
    // 楼层面板
    private ScrolledComposite scTab;
    private Composite compTab;
    
    private Composite compPage;
    
    private IconHolder icons = IconHolder.getInstance();
    
    private PacketProcessor packetProcessor;
    
    private MainShell main;

    private UIAlarmHelper uiAlarmHelper;
    private UISceneHelper uiSceneHelper;
    private UISceneBindHelper uiSceneBindHelper;
    
    private Label lbSecond;
	private Scale scale;
	// 动作间隔时间
	private int secondInterval;
    
	private BOSysConfig boSysConfig;
	
    public SmartShell(MainShell main){
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
    	uiAlarmHelper = new UIAlarmHelper(this);
    	uiSceneHelper = new UISceneHelper(this);
    	uiSceneBindHelper = new UISceneBindHelper(this);
    	  
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
    	
    	Label label = UITool.createLabel(body, smart);
        label.setBounds(370, 27, 74, 40);
        label.setFont(Colors.LOADING_FONT);
        
        // 返回 按钮
        btnBack =  UITool.createImageButton(body, button_back, icons.getImage(IconHolder.btnBack), 
        		icons.getImage(IconHolder.btnBackOn), icons.getImage(IconHolder.btnBackOn));
        btnBack.setFont(Colors.GLOBAL_FONT);
        btnBack.setBounds(702, 27, 77, 36);
        btnBack.setTextColor(Colors.BLACK);
        
        scTab = new ScrolledComposite(body, SWT.NONE);
        scTab.setBounds(32, 82, 728, 70);
    	compTab = new Composite(scTab, SWT.NONE);
    	scTab.setContent(compTab);
    	compTab.setBounds(0, 0, 728, 70);
        
    	compPage = new Composite(body, SWT.NONE);
    	compPage.setBounds(34, 150, 730, 420);
		
    	// 定时器
    	final ImageButton ibAlarm = UITool.createImageButton(compTab, alarm, icons.getImage(IconHolder.btnSubMenu), 
        		null, null);
    	ibAlarm.setBounds(0 * 104, 0, 100, 43);
    	ibAlarm.addMouseListener(new MouseAdapter(){
			public void mouseUp(MouseEvent e) {
				setTabOff();
				uiAlarmHelper.createAlarmUI(compPage);
				ibAlarm.setImage(icons.getImage(IconHolder.btnSubMenu));
		    }
		});
    	
    	// 情景设置
    	final ImageButton lbSenceSet = UITool.createImageButton(compTab, scene_set, icons.getImage(IconHolder.btnSubMenuOff), 
        		null, null);
    	lbSenceSet.setBounds(1 * 104, 0, 100, 43);
    	lbSenceSet.addMouseListener(new MouseAdapter(){
			public void mouseUp(MouseEvent e) {
				setTabOff();
				uiSceneHelper.createSceneUI(compPage);
				lbSenceSet.setImage(icons.getImage(IconHolder.btnSubMenu));
		    }
		});
    	
    	// 联动设置
    	final ImageButton ibSenceBind = UITool.createImageButton(compTab, bind_set, icons.getImage(IconHolder.btnSubMenuOff), 
        		null, null);
    	ibSenceBind.setBounds(2 * 104, 0, 100, 43);
    	ibSenceBind.addMouseListener(new MouseAdapter(){
			public void mouseUp(MouseEvent e) {
				setTabOff();
				uiSceneBindHelper.createSceneBindUI(compPage);
				ibSenceBind.setImage(icons.getImage(IconHolder.btnSubMenu));
		    }
		});
    	
    	// 参数配置
    	final ImageButton ibSetConfig = UITool.createImageButton(compTab, param_set, icons.getImage(IconHolder.btnSubMenuOff), 
        		null, null);
    	ibSetConfig.setBounds(3 * 104, 0, 100, 43);
    	ibSetConfig.addMouseListener(new MouseAdapter(){
			public void mouseUp(MouseEvent e) {
				setTabOff();
				openSetConfigTab();
				ibSetConfig.setImage(icons.getImage(IconHolder.btnSubMenu));
		    }
		});
    	
    	uiAlarmHelper.createAlarmUI(compPage);
    }
    
    private void openSetConfigTab(){
    	compPage.setVisible(false);
    	setTabOff();
    	clearComposite(compPage);
    	
    	Label label = UITool.createLabel(compPage, "");
    	label.setImage(icons.getImage(IconHolder.bmpBtnPause));
		label.setBounds(90, 80, 48, 48);
		
    	label = UITool.createLabel(compPage, action_time);
		label.setBounds(145, 80, 100, 20);
		label.setFont(Colors.GLOBAL_FONT);

		label = UITool.createLabel(compPage, scene_mode_action_time);
		label.setBounds(145, 110, 400, 20); 
		label.setFont(Colors.GL_FONT);
		
		lbSecond = UITool.createLabel(compPage, "");
		lbSecond.setBounds(580, 90, 10, 20);
		lbSecond.setText(String.valueOf(SystemConfig.getInstance().getActionInterval()));

		label = UITool.createLabel(compPage, time_s);
		label.setBounds(600, 90, 50, 20);

		Scale scale = new Scale(compPage, SWT.NONE);
		scale.setBounds(420, 90, 150, 20);
		scale.setMaximum(5);
		scale.setIncrement(1);
		scale.setMinimum(1);
		scale.setPageIncrement(1);
		scale.setSelection(SystemConfig.getInstance().getActionInterval());
		scale.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				Scale s = (Scale) e.getSource();
				secondInterval = s.getSelection();
				lbSecond.setText(String.valueOf(secondInterval));
			}
		});
    	
    	ImageButton ib =  UITool.createImageButton(compPage, set, icons.getImage(IconHolder.bmpBtnLogin), 
     		icons.getImage(IconHolder.bmpBtnLoginUp), icons.getImage(IconHolder.bmpBtnLoginOn));
    	ib.setFont(Colors.GLOBAL_FONT);
    	ib.setBounds(308, 350, 114, 41);
    	ib.addMouseListener(new MouseAdapter() {
         public void mouseUp(MouseEvent e) {
        	 sendEventDelay(secondInterval);
         }
     });
    	 compPage.setVisible(true);
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
				main.getShellRegistry().deregisterSmartShell();
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
        case Protocol.CMD_ALARM_WRITE:
        	processAlarmWriteSuccess(in);
        	break;
        case Protocol.CMD_EVENT_WRITE:
        	processEventWriteSuccess(in);
        	break;
        case Protocol.CMD_EVENT_CONNECT:
        	processEventConnectSuccess(in);
        	break;
        case Protocol.CMD_EVENT_DISCONNECT:
        	processEventDisConnectSuccess(in);
        	break;
        case Protocol.CMD_EVENT_DELAY:
        	processEventDelaySuccess(in);
        	break;
        case Protocol.CMD_RESET_HOST:
        	processResetHostSuccess(in);
        	break;
        case Protocol.CMD_RESET_HOST_OVER:
        	processResetHostOverSuccess(in);
        	break;
        case Protocol.CMD_UNKNOWN:
        	//processUnknown(in);
        	break;
        }
	}
    
    public void sendAlarmWritePacket(int alarmId, String alarmParam){
    	AlarmWritePacket packet = new AlarmWritePacket();
    	packet.setAlarmNum(alarmId);
    	packet.setAlarmParams(alarmParam);
    	packetProcessor.send(packet);
    }
    
    public void sendAlarmReadPacket(int alarmId){
    	AlarmReadPacket packet = new AlarmReadPacket();
    	packet.setAlarmNum(alarmId);
    	packetProcessor.send(packet);
    }
    
    public void sendEventConnect(int scene, String eventSource){
    	EventConnectPacket packet = new EventConnectPacket();
    	packet.setScene(scene);
    	packet.setEventSource(eventSource);
    	packetProcessor.send(packet);
    }
    
    private void processEventConnectSuccess(InPacket in){
    	display.syncExec(new Runnable() {
			public void run() {
				uiSceneBindHelper.doSave();
			}
		});
    }
    
    public void sendEventDisConnect(int scene, String eventSource){
    	EventDisConnectPacket packet = new EventDisConnectPacket();
    	packet.setScene(scene);
    	packet.setEventSource(eventSource);
    	packetProcessor.send(packet);
    }
    
    private void processEventDisConnectSuccess(InPacket in){
    	if(uiSceneBindHelper.isBind()){
    		uiSceneBindHelper.sendEventConnect();
    	}else{
    		display.syncExec(new Runnable() {
    			public void run() {
    				uiSceneBindHelper.doDelete();
    			}
    		});
    	}
    }
    
    public void sendEventWrite(int scene, byte[] actionStr, String actionS){
    	EventWritePacket packet = new EventWritePacket();
    	packet.setScene(scene);
    	packet.setAction(actionStr);
    	packet.setActionStr(actionS);
    	packetProcessor.send(packet);
    }
    
    private void processEventWriteSuccess(InPacket in){
    	display.syncExec(new Runnable() {
			public void run() {
				uiSceneHelper.doSave();
			}
		});
    }
    
    public void sendEventDelay(int delay){
    	EventDelayPacket packet = new EventDelayPacket();
    	packet.setDelay(delay);
    	packetProcessor.send(packet);
    }
    
    private void processEventDelaySuccess(InPacket in){
    	display.syncExec(new Runnable() {
			public void run() {
				boSysConfig.update("ACTION_INTERVAL", ""+secondInterval);
				SystemConfig.getInstance().setActionInterval(secondInterval);
				MessageBoxHelper.openInformation(shell, success_set_action_time);
			}
		});
    }
    
    private void processAlarmWriteSuccess(InPacket in){
    	uiAlarmHelper.plusAlarmIndex();
    	if(uiAlarmHelper.getAlarmCount() == uiAlarmHelper.getAlarmIndex() ){
    		//sendResetHost();
    		display.syncExec(new Runnable() {
    			public void run() {
    				MessageBoxHelper.openInformation(shell, success_set_alarm);
    			}
        	});
    	}
    }
    
    public void sendResetHost(){
    	ResetHostPacket packet = new ResetHostPacket();
    	packetProcessor.send(packet);
    }
    
    private void processResetHostSuccess(InPacket in){
    	
    }
    
    private void processResetHostOverSuccess(InPacket in){
    	display.syncExec(new Runnable() {
			public void run() {
				uiAlarmHelper.getInformationDialog().close();
				MessageBoxHelper.openInformation(shell, success_set_alarm);
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
    /**
     * 清空面板
     */
    private void clearComposite(Composite comp){
    	for(Control c : comp.getChildren()){
    		if(c!= null && !c.isDisposed()){
    			c.dispose();
    		}
    	}
    	comp.redraw();
    }
    
    /**
     * 设置所有的房间图片为off
     */
    private void setTabOff(){
    	for(Control c : compTab.getChildren()){
    		if(c instanceof ImageButton){
    			((ImageButton) c).setImage(icons.getImage(IconHolder.btnSubMenuOff));
    		}
    	}
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

	public MainShell getMainShell() {
		return main;
	}
}
