package com.joeysoft.kc868.ui;

import static com.joeysoft.kc868.resource.Messages.*;

import org.apache.commons.lang.StringUtils;
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
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.ScrollBar;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Tray;
import org.eclipse.swt.widgets.TrayItem;
import org.eclipse.swt.widgets.Tree;

import com.joeysoft.kc868.KC868;
import com.joeysoft.kc868.client.KC868Client;
import com.joeysoft.kc868.client.event.IPacketListener;
import com.joeysoft.kc868.client.event.PacketEvent;
import com.joeysoft.kc868.client.packets.out.DebugPacket;
import com.joeysoft.kc868.client.support.PacketProcessor;
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

/**
 * 测试命令窗口
 * @author JOEY
 *
 */
public class DebugShell{

	private Shell shell;
    private Display display;
    
    private IconHolder icons = IconHolder.getInstance();
    
    private MainShell main;
    private PacketProcessor packetProcessor;
    
    private List listSend, listReceive;
    private Text textSend;
    private Button btnSend;
    
    public DebugShell(Shell shell){
        this.display = shell.getDisplay();
    	initialize();
    }
    
    public DebugShell(MainShell main){
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

        UITool.setDefaultBackground(null);
        UITool.setDefaultForeground(Colors.WHITE);
    	
    	textSend = UITool.createSingleText(body);
    	textSend.setBackground(Colors.WHITE);
    	textSend.setBounds(10, 30, 270, 20);
    	
    	btnSend = UITool.createButton(body, "发送");
    	btnSend.setBounds(310, 30, 40, 20);
    	btnSend.addMouseListener(new MouseAdapter() {
    		public void mouseUp(MouseEvent e) { 
    			if(StringUtils.isNotEmpty(textSend.getText())){
    				packetProcessor.sendNoResend(new DebugPacket(textSend.getText()));
    			}
    		}
    	});
    	
    	Label label = UITool.createLabel(body, "发送包：");
    	label.setBounds(10, 60, 100, 20);
    	listSend = new List(body, SWT.V_SCROLL | SWT.H_SCROLL | SWT.MULTI | SWT.BORDER | SWT.WRAP);
    	listSend.setBackground(Colors.WHITE);
    	listSend.setBounds(10, 80, 370, 200);
    	
    	Button btnClear = UITool.createButton(body, "清空");
    	btnClear.setBounds(310, 60, 40, 20);
    	btnClear.addMouseListener(new MouseAdapter() {
    		public void mouseUp(MouseEvent e) { 
    			listSend.removeAll();
    		}
    	});
        
    	label = UITool.createLabel(body, "接收包：");
    	label.setBounds(10, 300, 100, 20);
    	
    	btnClear = UITool.createButton(body, "清空");
    	btnClear.setBounds(310, 300, 40, 20);
    	btnClear.addMouseListener(new MouseAdapter() {
    		public void mouseUp(MouseEvent e) { 
    			listReceive.removeAll();
    		}
    	});
    	
    	listReceive = new List(body, SWT.V_SCROLL | SWT.H_SCROLL | SWT.MULTI | SWT.BORDER | SWT.WRAP);
    	listReceive.setBackground(Colors.WHITE);
        listReceive.setBounds(10, 320, 370, 200);
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
				if(main != null){
					main.getShellRegistry().deregisterDebugShell();
				}
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
		shell.setSize(400, 564);
		Rectangle bounds = display.getPrimaryMonitor().getBounds();
        Rectangle rect = shell.getBounds();
        int x = bounds.x + (bounds.width - rect.width);
        int y = bounds.y + (bounds.height - rect.height) / 2;
        shell.setLocation(x, y);

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
	 * 添加发送信息
	 * @param message
	 */
	public void addSendPacket(final String message){
		display.syncExec(new Runnable() {
			public void run() {
				listSend.add(message, 0);
			}
		});
	}
	
	/**
	 * 添加接收信息
	 * @param message
	 */
	public void addReceivePacket(final String message){
		display.syncExec(new Runnable() {
			public void run() {
				listReceive.add(message, 0);
			}
		});
	}
	
	/**
     * 初始化监听器
     */
    private void initListeners() {
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
