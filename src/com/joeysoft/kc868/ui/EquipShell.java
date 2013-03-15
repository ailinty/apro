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
import org.eclipse.swt.widgets.Control;
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
import com.joeysoft.kc868.client.packets.OutPacket;
import com.joeysoft.kc868.client.packets.util.SensorPacketUtil;
import com.joeysoft.kc868.client.support.PacketProcessor;
import com.joeysoft.kc868.client.support.Protocol;
import com.joeysoft.kc868.db.bean.Device;
import com.joeysoft.kc868.db.bean.Floor;
import com.joeysoft.kc868.db.bean.Lineate;
import com.joeysoft.kc868.db.bean.Relay;
import com.joeysoft.kc868.db.bean.Room;
import com.joeysoft.kc868.db.bean.SensorOut;
import com.joeysoft.kc868.db.bean.SensorIn;
import com.joeysoft.kc868.db.bean.SmsIn;
import com.joeysoft.kc868.db.bean.SmsOut;
import com.joeysoft.kc868.db.bean.TelIn;
import com.joeysoft.kc868.db.bean.TelOut;
import com.joeysoft.kc868.db.bean.TempSensor;
import com.joeysoft.kc868.db.bean.Transfer;
import com.joeysoft.kc868.db.bo.BODevice;
import com.joeysoft.kc868.db.bo.BOFloor;
import com.joeysoft.kc868.db.bo.BOLineate;
import com.joeysoft.kc868.db.bo.BORelay;
import com.joeysoft.kc868.db.bo.BORoom;
import com.joeysoft.kc868.db.bo.BOSceneAction;
import com.joeysoft.kc868.db.bo.BOSceneBind;
import com.joeysoft.kc868.db.bo.BOSensorIn;
import com.joeysoft.kc868.db.bo.BOSensorOut;
import com.joeysoft.kc868.db.bo.BOSmsIn;
import com.joeysoft.kc868.db.bo.BOSmsOut;
import com.joeysoft.kc868.db.bo.BOTelIn;
import com.joeysoft.kc868.db.bo.BOTelOut;
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
import com.joeysoft.kc868.ui.dialogs.helper.TableSmsInHelper;
import com.joeysoft.kc868.ui.dialogs.helper.TableSmsOutHelper;
import com.joeysoft.kc868.ui.dialogs.helper.TableTelInHelper;
import com.joeysoft.kc868.ui.dialogs.helper.TableTelOutHelper;
import com.joeysoft.kc868.ui.dialogs.helper.TableTempSensorHelper;
import com.joeysoft.kc868.ui.dialogs.helper.TableTransferHelper;
import com.joeysoft.kc868.ui.dialogs.helper.UIGSMHelper;
import com.joeysoft.kc868.ui.dialogs.helper.UILineateHelper;
import com.joeysoft.kc868.ui.dialogs.helper.UIRelayHelper;
import com.joeysoft.kc868.ui.helper.MessageBoxHelper;
import com.joeysoft.kc868.ui.helper.ShellLauncher;
import com.joeysoft.kc868.ui.helper.ShellRegistry;
import com.joeysoft.kc868.ui.helper.UITool;
import com.joeysoft.kc868.widgets.ImageButton;

public class EquipShell implements IPacketListener{

	private Shell shell;
    private Display display;
    
    // 所有控件
    private ImageButton btnAdd, btnDel, btnEdit;
    private ImageButton btnBack, btnOut, btnIn, btnOutLeft, btnOutRight, btnInLeft, btnInRight;
    
    private ImageButton ibFloor, ibRoom, ibRelay, ibSensorOut, ibTransfer, ibDevice,ibTelIn, ibSmsIn;
    
    private ImageButton ibSensorIn, ibLineate, ibTempSensor, ibTelOut, ibSmsOut;
    
    private int selectionOutMenu = DictConst.MENU_FLOOR, selectionInMenu = DictConst.MENU_SENSORIN, selectionPage = DictConst.PAGE_OUT;
    
    private Table tbFloor, tbRoom, tbRelay, tbSensorOut, tbDevice, tbTransfer, tbTelOut, tbSmsOut;
    private Table tbLineate, tbSensorIn, tbTempSensor, tbTelIn, tbSmsIn;
    
    private ScrolledComposite scOut, scIn;
    private Composite menuOut, menuIn, menuPage;
    private int menuOutCount, menuInCount;
    
    private Composite body;
    // 偏移量
    private int increment = 104;
    // 当前位置
    private int outNPos, inNPos, outNMax, inNMax;
    
    private IconHolder icons = IconHolder.getInstance();
    
    private PacketProcessor packetProcessor;
    
    private MainShell main;
   
    private TableFloorHelper tableFloorHelper;
    private TableRoomHelper tableRoomHelper;
    private TableRelayHelper tableRelayHelper;
    private TableSensorOutHelper tableSensorOutHelper;
    private TableLineateHelper tableLineateHelper;
    private TableDeviceHelper tableDeviceHelper;
    private TableTransferHelper tableTransferHelper;
    private TableSensorInHelper tableSensorInHelper;
    private TableTempSensorHelper tableTempSensorHelper;
    private TableTelInHelper tableTelInHelper;
    private TableTelOutHelper tableTelOutHelper;
    private TableSmsInHelper tableSmsInHelper;
    private TableSmsOutHelper tableSmsOutHelper;
    
    
    private BOFloor boFloor;
    private BORoom boRoom;
    private BORelay boRelay;
    private BOSensorOut boSensorOut;
    private BOLineate boLineate;
    private BODevice boDevice;
    private BOTransfer boTransfer;
    private BOSensorIn boSensorIn;
    private BOTempSensor boTempSensor;
    private BOTelOut boTelOut;
    private BOTelIn boTelIn;
    private BOSmsOut boSmsOut;
    private BOSmsIn boSmsIn;
    private BOSceneAction booSceneAction;
    private BOSceneBind boSceneBind;
    
    private UIRelayHelper uiRelayHelper;
    private UILineateHelper uiLineateHelper;
    private UIGSMHelper uiGSMHelper;
    
    private SensorIn sensorIn;
    
    public EquipShell(MainShell main){
    	this.main = main;
        this.packetProcessor = main.getClient().getPacketProcessor();
        this.display = main.getDisplay();
    	initialize();
    }

	/**
     * 初始化
     */
    private void initialize() {
    	tableFloorHelper = new TableFloorHelper();
    	tableRoomHelper = new TableRoomHelper();
    	tableRelayHelper =  new TableRelayHelper();
    	tableSensorOutHelper = new TableSensorOutHelper();
    	tableLineateHelper = new TableLineateHelper();
    	tableDeviceHelper =  new TableDeviceHelper();
    	tableTransferHelper =  new TableTransferHelper();
    	tableSensorInHelper = new TableSensorInHelper();
    	tableTempSensorHelper =  new TableTempSensorHelper();
    	tableTelInHelper = new TableTelInHelper();
        tableTelOutHelper = new TableTelOutHelper();
        tableSmsInHelper = new TableSmsInHelper();
        tableSmsOutHelper = new TableSmsOutHelper();
    	
    	boFloor = new BOFloor();
    	boRoom = new BORoom();
    	boRelay = new BORelay();
    	boLineate = new BOLineate();
    	boDevice = new BODevice();
    	boTransfer = new BOTransfer();
    	boSensorOut =  new BOSensorOut();
    	boSensorIn = new BOSensorIn();
    	boTempSensor = new BOTempSensor();
    	boTelOut = new BOTelOut();
    	boTelIn = new BOTelIn();
    	boSmsOut = new BOSmsOut();
    	boSmsIn = new BOSmsIn();
    	booSceneAction = new BOSceneAction();
    	boSceneBind =  new BOSceneBind();
    	
    	uiRelayHelper = new UIRelayHelper(tbRelay);
    	uiLineateHelper = new UILineateHelper(tbLineate);
    	uiGSMHelper =  new UIGSMHelper();
    	
    	menuOutCount = 5;
    	menuInCount = 2;
    	
    	if(SystemConfig.getInstance().hasSim()){
    		menuOutCount += 2;
    		menuInCount += 2;
    	}
    	if(SystemConfig.getInstance().isEngine()){
    		menuOutCount += 1;
    		menuInCount += 1;
    	}
    	
    	initLayout();
    	initListeners();
    	
    	hookListener();
    }
    
    /**
     * 初始化界面布局
     */
    private void initLayout() {
    	body = initShell();
    	
    	// 设置背景图片
    	body.setBackgroundImage(icons.getImage(IconHolder.bmpBgSub));
    	body.setBackgroundMode(SWT.INHERIT_FORCE);
    	body.setBackground(Colors.LOGIN_BACKGROUND);
    	
    	Label label = UITool.createLabel(body, equip);
        label.setBounds(370, 27, 74, 40);
        label.setFont(Colors.LOADING_FONT);
        
        btnOut = UITool.createImageButton(body, out, icons.getImage(IconHolder.btnNormalOn), null, null);
        btnOut.setFont(Colors.GLOBAL_FONT);
        btnOut.setBounds(15, 27, 77, 36);
        btnOut.setTextColor(Colors.BLACK);
        
        btnIn =  UITool.createImageButton(body, in, icons.getImage(IconHolder.btnNormal), null, null);
        btnIn.setFont(Colors.GLOBAL_FONT);
        btnIn.setBounds(105, 27, 77, 36);
        btnIn.setTextColor(Colors.BLACK);
        
        btnBack =  UITool.createImageButton(body, button_back, icons.getImage(IconHolder.btnBack), 
        		icons.getImage(IconHolder.btnBackOn), icons.getImage(IconHolder.btnBackOn));
        btnBack.setFont(Colors.GLOBAL_FONT);
        btnBack.setBounds(702, 27, 77, 36);
        btnBack.setTextColor(Colors.BLACK);
        
        scOut = new ScrolledComposite(body, SWT.NONE);
        scOut.setBounds(32, 82, 728, 70);
        menuOut = new Composite(scOut, SWT.NONE);
        scOut.setContent(menuOut);
        menuOut.setBounds(0, 0, menuOutCount * 104, 70);
        
        outNMax = menuOutCount * 104 - 728;

        btnOutLeft =  UITool.createImageButton(body, "", icons.getImage(IconHolder.btnLeft), null, null);
        btnOutLeft.setFont(Colors.GLOBAL_FONT);
        btnOutLeft.setBounds(12, 92, 16, 22);
        btnOutLeft.addMouseListener(new MouseAdapter(){
			public void mouseUp(MouseEvent e) {
				Point location = menuOut.getLocation();
				outNPos = outNPos - increment <= 0?0:outNPos - increment;
				menuOut.setLocation(-outNPos, location.y);
	        }
		});
        
        btnOutRight =  UITool.createImageButton(body, "", icons.getImage(IconHolder.btnRight), null, null);
        btnOutRight.setFont(Colors.GLOBAL_FONT);
        btnOutRight.setBounds(760, 92, 16, 22);
        btnOutRight.addMouseListener(new MouseAdapter(){
			public void mouseUp(MouseEvent e) {
				Point location = menuOut.getLocation();
				outNPos = outNPos + increment >= outNMax?outNMax:outNPos + increment;
				menuOut.setLocation(-outNPos, location.y);
	        }
		});

        //-----------------------------------------------输出---------------------------------------------
        int outIndex = 0;
        // 楼层
        ibFloor = UITool.createImageButton(menuOut, floor, icons.getImage(IconHolder.btnSubMenu), 
        		null, null);
        ibFloor.setBounds(104 * outIndex++, 0, 100, 43);
        
    	// 房间
        ibRoom = UITool.createImageButton(menuOut, room, icons.getImage(IconHolder.btnSubMenuOff), 
        		null, null);
        ibRoom.setBounds(104 * outIndex++, 0, 100, 43);
        
    	// 红外转发器
        ibTransfer = UITool.createImageButton(menuOut, transfer, icons.getImage(IconHolder.btnSubMenuOff), 
        		null, null);
        ibTransfer.setBounds(104 * outIndex++, 0, 100, 43);
        
        // 常规设备
        ibDevice = UITool.createImageButton(menuOut, normal_device, icons.getImage(IconHolder.btnSubMenuOff), 
        		null, null);
        ibDevice.setBounds(104 * outIndex++, 0, 100, 43);
        
    	// 无线输出
    	ibSensorOut = UITool.createImageButton(menuOut, sensor_out, icons.getImage(IconHolder.btnSubMenuOff), 
        		null, null);
        ibSensorOut.setBounds(104 * outIndex++, 0, 100, 43);
        
        if(SystemConfig.getInstance().hasSim()){
        	 // GSM
            ibTelOut = UITool.createImageButton(menuOut, tel_out, icons.getImage(IconHolder.btnSubMenuOff), 
            		null, null);
            ibTelOut.setBounds(104 * outIndex++, 0, 100, 43);
            
            ibSmsOut = UITool.createImageButton(menuOut, sms_out, icons.getImage(IconHolder.btnSubMenuOff), 
            		null, null);
            ibSmsOut.setBounds(104 * outIndex++, 0, 100, 43);
        }
        if(SystemConfig.getInstance().isEngine()){
        	 // 继电器
            ibRelay = UITool.createImageButton(menuOut, relay_out, icons.getImage(IconHolder.btnSubMenuOff), 
            		null, null);
            ibRelay.setBounds(104 * outIndex++, 0, 100, 43);
        }
        
        //-----------------------------------------------输入---------------------------------------------
        int inIndex = 0;
        scIn = new ScrolledComposite(body, SWT.NONE);
        scIn.setBounds(32, 82, 728, 70);
        scIn.setVisible(false);
        menuIn = new Composite(scIn, SWT.NONE);
        scIn.setContent(menuIn);
        menuIn.setBounds(0, 0, 728, 70);
        menuIn.setVisible(false);
        
        // 无线输入
        ibSensorIn = UITool.createImageButton(menuIn, sensor_in, icons.getImage(IconHolder.btnSubMenu), 
        		null, null);
        ibSensorIn.setBounds(104 * inIndex++, 0, 100, 43);

        // 温湿度传感器
        ibTempSensor = UITool.createImageButton(menuIn, temp_sensor, icons.getImage(IconHolder.btnSubMenuOff), 
        		null, null);
        ibTempSensor.setBounds(104 * inIndex++, 0, 100, 43);
        
       if(SystemConfig.getInstance().isEngine()){
    	   // 有线输入
           ibLineate = UITool.createImageButton(menuIn, lineate, icons.getImage(IconHolder.btnSubMenuOff), 
           		null, null);
           ibLineate.setBounds(104 * inIndex++, 0, 100, 43);
       }
       
       if(SystemConfig.getInstance().hasSim()){
         	 // GSM
    	   ibTelIn = UITool.createImageButton(menuIn, tel_in, icons.getImage(IconHolder.btnSubMenuOff), 
           		null, null);
           ibTelIn.setBounds(104 * inIndex++, 0, 100, 43);
           
           ibSmsIn = UITool.createImageButton(menuIn, sms_in, icons.getImage(IconHolder.btnSubMenuOff), 
           		null, null);
           ibSmsIn.setBounds(104 * inIndex++, 0, 100, 43);
         }
        
        menuPage = new Composite(body, SWT.NONE);
        menuPage.setBounds(74, 175, 750, 318);
        
        // 控钮区
        btnAdd =  UITool.createImageButton(body, button_add, icons.getImage(IconHolder.bmpBtnLogin), 
        		icons.getImage(IconHolder.bmpBtnLoginUp), icons.getImage(IconHolder.bmpBtnLoginOn));
        btnAdd.setFont(Colors.GLOBAL_FONT);
        btnAdd.setBounds(197, 520, 114, 41);
        
        btnEdit = UITool.createImageButton(body, button_edit, icons.getImage(IconHolder.bmpBtnLogin), 
        		icons.getImage(IconHolder.bmpBtnLoginUp), icons.getImage(IconHolder.bmpBtnLoginOn));
        btnEdit.setFont(Colors.GLOBAL_FONT);
        btnEdit.setBounds(335, 520, 114, 41);
        
        btnDel = UITool.createImageButton(body, button_del, icons.getImage(IconHolder.bmpBtnLogin), 
        		icons.getImage(IconHolder.bmpBtnLoginUp), icons.getImage(IconHolder.bmpBtnLoginOn));
        btnDel.setFont(Colors.GLOBAL_FONT);
        btnDel.setBounds(473, 520, 114, 41);
        
        openFloor();
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
				unhookListener();
			}
		});
		shell.addShellListener(new ShellAdapter() {
			public void shellClosed(ShellEvent e) {
				main.show();
				main.getShellRegistry().deregisterEquipShell();
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
    	
    	// 添加按钮鼠标事件
        btnAdd.addMouseListener(new MouseAdapter() {
            public void mouseUp(MouseEvent e) {
            	addButtonClick();
            }
        });
        // 删除按钮鼠标事件
        btnDel.addMouseListener(new MouseAdapter() {
            public void mouseUp(MouseEvent e) {
            	deleteButtonClick();
            }
        });
        // 修改按钮鼠标事件
        btnEdit.addMouseListener(new MouseAdapter() {
            public void mouseUp(MouseEvent e) {
            	editButtonClick();
            }
        });
        
        btnOut.addMouseListener(new MouseAdapter() {
            public void mouseUp(MouseEvent e) {
            	changePage(DictConst.PAGE_OUT);
            }
        });
        
        btnIn.addMouseListener(new MouseAdapter() {
            public void mouseUp(MouseEvent e) {
            	changePage(DictConst.PAGE_IN);
            }
        });
        
        // 楼层按钮
        ibFloor.addMouseListener(new MouseAdapter() {
            public void mouseUp(MouseEvent e) {
            	openFloor();
            }
        });
        
        // 楼层按钮
        ibRoom.addMouseListener(new MouseAdapter() {
            public void mouseUp(MouseEvent e) {
            	openRoom();
            }
        });
        
        // 红外转发器按钮
        ibTransfer.addMouseListener(new MouseAdapter() {
            public void mouseUp(MouseEvent e) {
            	openTransfer();
            }
        });
        
        // 无线输出按钮
        ibSensorOut.addMouseListener(new MouseAdapter() {
            public void mouseUp(MouseEvent e) {
            	openSensorOut();
            }
        });
        
        // 常规设备按钮
        ibDevice.addMouseListener(new MouseAdapter() {
            public void mouseUp(MouseEvent e) {
            	openDevice();
            }
        });
        
    	// 无线输入按钮
        ibSensorIn.addMouseListener(new MouseAdapter() {
            public void mouseUp(MouseEvent e) {
            	openSensorIn();
            }
        });
        
        // 湿温度按钮
        ibTempSensor.addMouseListener(new MouseAdapter() {
            public void mouseUp(MouseEvent e) {
            	openTempSensor();
            }
        });
        
        if(SystemConfig.getInstance().hasSim()){
        	// GSM
            ibTelIn.addMouseListener(new MouseAdapter() {
                public void mouseUp(MouseEvent e) {
                	openTelIn();
                }
            });
            
            ibTelOut.addMouseListener(new MouseAdapter() {
                public void mouseUp(MouseEvent e) {
                	openTelOut();
                }
            });
            
            ibSmsIn.addMouseListener(new MouseAdapter() {
                public void mouseUp(MouseEvent e) {
                	openSmsIn();
                }
            });
            
            ibSmsOut.addMouseListener(new MouseAdapter() {
                public void mouseUp(MouseEvent e) {
                	openSmsOut();
                }
            });
        }
        
        if(SystemConfig.getInstance().isEngine()){
        	// 继电器按钮
            ibRelay.addMouseListener(new MouseAdapter() {
                public void mouseUp(MouseEvent e) {
                	openRelay();
                }
            });
            
            // 有线电按钮
            ibLineate.addMouseListener(new MouseAdapter() {
                public void mouseUp(MouseEvent e) {
                	openLineate();
                }
            });
        }
    }
    
    /**
     * 添加按钮事件
     */
    private void addButtonClick(){
    	switch(selectionPage){
    		case DictConst.PAGE_OUT:
    			switch(selectionOutMenu){
	    			case DictConst.MENU_FLOOR:
	    				Floor floor = main.getShellLauncher().openFloorDialog(null);
	    	    		if(floor != null) {
	    	    			tableFloorHelper.insertTable(tbFloor, floor);
	    	    		}
	    				break;
	    			case DictConst.MENU_ROOM:
	    				Room room = main.getShellLauncher().openRoomDialog(null);
	    	    		if(room != null) {
	    	    			tableRoomHelper.insertTable(tbRoom, room);
	    	    		}
	    				break;
	    			case DictConst.MENU_TRANSFER:
	    				Transfer transfer = main.getShellLauncher().openTransferDialog(null);
	    	    		if(transfer != null) {
	    	    			tableTransferHelper.insertTable(tbTransfer, transfer);
	    	    		}
	    				break;
	    			case DictConst.MENU_DEVICE:
	    				Device device = main.getShellLauncher().openDeviceDialog(null);
	    	    		if(device != null) {
	    	    			tableDeviceHelper.insertTable(tbDevice, device);
	    	    		}
	    				break;
	    			case DictConst.MENU_SENSOROUT:
	    				SensorOut sensorOut = main.getShellLauncher().openSensorOutDialog(null);
	    	    		if(sensorOut != null) {
	    	    			tableSensorOutHelper.insertTable(tbSensorOut, sensorOut);
	    	    		}
	    				break;
	    			case DictConst.MENU_RELAY:
	    				MessageBoxHelper.openWarning(shell, error_relay_cant_add);
	    				break;
	    		}
    			break;
    		case DictConst.PAGE_IN:
    			switch(selectionInMenu){
	    			case DictConst.MENU_SENSORIN:
	    				SensorIn sensorIn = main.getShellLauncher().openSensorInDialog(null);
	    	    		if(sensorIn != null) {
	    	    			tableSensorInHelper.insertTable(tbSensorIn, sensorIn);
	    	    		}
	    				break;
	    			case DictConst.MENU_LINEATE:
	    				MessageBoxHelper.openWarning(shell, error_lineate_cant_add);
	    				break;
	    			case DictConst.MENU_TEMP_SENSOR:
	    				TempSensor tempSensor = main.getShellLauncher().openTempSensorDialog(null);
	    	    		if(tempSensor != null) {
	    	    			tableTempSensorHelper.insertTable(tbTempSensor, tempSensor);
	    	    		}
	    				break;
	    		}
    			break;
    	}
    	
    }
    
    /**
     * 修改按钮事件
     */
    private void editButtonClick(){
    	switch(selectionPage){
			case DictConst.PAGE_OUT:
				switch(selectionOutMenu){
					case DictConst.MENU_FLOOR:
						if(tbFloor.getSelectionCount() > 0){
							Floor oldFloor = (Floor)tbFloor.getSelection()[0].getData();
				    		
				    		Floor floor = main.getShellLauncher().openFloorDialog(oldFloor);
				        	tableFloorHelper.updateTable(tbFloor, floor);
						}
						break;
					case DictConst.MENU_ROOM:
						if(tbRoom.getSelectionCount() > 0){
							Room oldRoom = (Room)tbRoom.getSelection()[0].getData();
				    		
				    		Room room = main.getShellLauncher().openRoomDialog(oldRoom);
				        	tableRoomHelper.updateTable(tbRoom, room);
						}
						
						break;
					case DictConst.MENU_TRANSFER:
						if(tbTransfer.getSelectionCount() > 0){
							Transfer oldTransfer = (Transfer)tbTransfer.getSelection()[0].getData();
				    		Transfer transfer = main.getShellLauncher().openTransferDialog(oldTransfer);
				        	tableTransferHelper.updateTable(tbTransfer, transfer);
						}
						
						break;
					case DictConst.MENU_DEVICE:
						if(tbDevice.getSelectionCount() > 0){
							Device oldDevice = (Device)tbDevice.getSelection()[0].getData();
				    		Device device = main.getShellLauncher().openDeviceDialog(oldDevice);
				        	tableDeviceHelper.updateTable(tbDevice, device);
						}
						
						break;
					case DictConst.MENU_SENSOROUT:
						if(tbSensorOut.getSelectionCount() > 0){
							SensorOut oldSensorOut = (SensorOut)tbSensorOut.getSelection()[0].getData();
				    		SensorOut sensorOut = main.getShellLauncher().openSensorOutDialog(oldSensorOut);
				        	tableSensorOutHelper.updateTable(tbSensorOut, sensorOut);	
						}
						
						break;
					case DictConst.MENU_TEL_OUT:
						if(tbTelOut.getSelectionCount() > 0){
							TelOut oldTelOut = (TelOut)tbTelOut.getSelection()[0].getData();
							TelOut telOut = main.getShellLauncher().openTelOutDialog(oldTelOut);
				        	tableTelOutHelper.updateTable(tbTelOut, telOut);
						}
						
						break;
					case DictConst.MENU_SMS_OUT:
						if(tbSmsOut.getSelectionCount() > 0){
							SmsOut oldSmsOut = (SmsOut)tbSmsOut.getSelection()[0].getData();
							SmsOut smsOut = main.getShellLauncher().openSmsOutDialog(oldSmsOut);
				        	tableSmsOutHelper.updateTable(tbSmsOut, smsOut);
						}
						break;
					case DictConst.MENU_RELAY:
						if(tbRelay.getSelectionCount() > 0){
							Relay oldRelay = (Relay)tbRelay.getSelection()[0].getData();
				    		Relay relay = main.getShellLauncher().openRelayDialog(oldRelay);
				        	tableRelayHelper.updateTable(tbRelay, relay);
						}
						
						break;
				}
				break;
			case DictConst.PAGE_IN:
		    	switch(selectionInMenu){
					case DictConst.MENU_SENSORIN:
						if(tbSensorIn.getSelectionCount() > 0){
							SensorIn oldSensorIn = (SensorIn)tbSensorIn.getSelection()[0].getData();
							SensorIn sensorIn = main.getShellLauncher().openSensorInDialog(oldSensorIn);
				        	tableSensorInHelper.updateTable(tbSensorIn, sensorIn);
						}
						
						break;
					case DictConst.MENU_LINEATE:
						if(tbLineate.getSelectionCount() > 0){
							Lineate oldLineate = (Lineate)tbLineate.getSelection()[0].getData();
				    		Lineate lineate = main.getShellLauncher().openLineateDialog(oldLineate);
				        	tableLineateHelper.updateTable(tbLineate,lineate);
						}
						
						break;
					case DictConst.MENU_TEMP_SENSOR:
						if(tbTempSensor.getSelectionCount() > 0){
							TempSensor oldTempSensor = (TempSensor)tbTempSensor.getSelection()[0].getData();
		    	    		TempSensor tempSensor = main.getShellLauncher().openTempSensorDialog(oldTempSensor);
				        	tableTempSensorHelper.updateTable(tbTempSensor, tempSensor);
						}
	    	    		
						break;
					case DictConst.MENU_TEL_IN:
						if(tbTelIn.getSelectionCount() > 0){
							TelIn oldTelIn = (TelIn)tbTelIn.getSelection()[0].getData();
							TelIn telIn = main.getShellLauncher().openTelInDialog(oldTelIn);
				        	tableTelInHelper.updateTable(tbTelIn, telIn);
						}
						
						break;
					case DictConst.MENU_SMS_IN:
						if(tbSmsIn.getSelectionCount() > 0){
							SmsIn oldSmsIn = (SmsIn)tbSmsIn.getSelection()[0].getData();
							SmsIn smsIn = main.getShellLauncher().openSmsInDialog(oldSmsIn);
				        	tableSmsInHelper.updateTable(tbSmsIn, smsIn);
						}
						break;
				}
				break;
    	}
    }
    
    /**
     * 删除按钮事件
     */
    private void deleteButtonClick(){
    	switch(selectionPage){
			case DictConst.PAGE_OUT:
				switch(selectionOutMenu){
					case DictConst.MENU_FLOOR:
						if(tbFloor.getSelectionCount() > 0 && MessageBoxHelper.openQuestion(shell, message_delete_title)){
							Floor floor = (Floor)tbFloor.getSelection()[0].getData();
							if(boFloor.delete(floor.getFloor())){
			        			//tbFloor.remove(tbFloor.getSelectionIndex());
								tableFloorHelper.deleteTable(tbFloor);
			        		}else{
			        			MessageBoxHelper.openError(shell, message_delete_error);
			        		}
						}
						break;
					case DictConst.MENU_ROOM:
						if(tbRoom.getSelectionCount() > 0 && MessageBoxHelper.openQuestion(shell, message_delete_title)){
			    			Room room = (Room)tbRoom.getSelection()[0].getData();
			    			if(boRoom.delete(room.getRoomId())){
			        			//tbRoom.remove(tbRoom.getSelectionIndex());
			        			tableRoomHelper.deleteTable(tbRoom);
			        		}else{
			        			MessageBoxHelper.openError(shell, "房间已经被使用，删除失败！");
			        		}
			    		}
						break;
					case DictConst.MENU_TRANSFER:
						if(tbTransfer.getSelectionCount() > 0 && MessageBoxHelper.openQuestion(shell, message_delete_title)){
			    			Transfer transfer = (Transfer)tbTransfer.getSelection()[0].getData();
			    			try {
								boTransfer.delete(transfer.getTransferId());
								//tbTransfer.remove(tbTransfer.getSelectionIndex());
								tableTransferHelper.deleteTable(tbTransfer);
							} catch (Exception e) {
								e.printStackTrace();
								MessageBoxHelper.openError(shell, message_delete_error);
							}
			    		}
						break;
					case DictConst.MENU_DEVICE:
						if(tbDevice.getSelectionCount() > 0 && MessageBoxHelper.openQuestion(shell, message_delete_title)){
			    			Device device = (Device)tbDevice.getSelection()[0].getData();
			    			if(booSceneAction.isExistAction(device.getDeviceId())){
		    					MessageBoxHelper.openWarning(shell, error_scene_mode_off);
		    				}else{
		    					if(boDevice.delete(device.getDeviceId())){
				        			//tbDevice.remove(tbDevice.getSelectionIndex());
				        			tableDeviceHelper.deleteTable(tbDevice);
				        		}
		    				}
			    		}
						break;
					case DictConst.MENU_SENSOROUT:
						if(tbSensorOut.getSelectionCount() > 0 && MessageBoxHelper.openQuestion(shell, message_delete_title)){
			    			SensorOut sensor = (SensorOut)tbSensorOut.getSelection()[0].getData();
			    			try {
			    				if(booSceneAction.isExistAction("SENSOR_OUT", sensor.getSensorId())){
			    					MessageBoxHelper.openWarning(shell, error_scene_mode_off);
			    				}else{
									boSensorOut.delete(sensor);
									//tbSensorOut.remove(tbSensorOut.getSelectionIndex());
									tableSensorOutHelper.deleteTable(tbSensorOut);
			    				}
							} catch (Exception e) {
								e.printStackTrace();
								MessageBoxHelper.openError(shell, message_delete_error);
							}
			    		}
						break;
					case DictConst.MENU_RELAY:
						MessageBoxHelper.openWarning(shell, error_relay_cant_delete);
						break;
				}
				break;
			case DictConst.PAGE_IN:
				switch(selectionInMenu){
					case DictConst.MENU_SENSORIN:
						if(tbSensorIn.getSelectionCount() > 0 && MessageBoxHelper.openQuestion(shell, message_delete_title)){
							sensorIn = (SensorIn)tbSensorIn.getSelection()[0].getData();
							if(boSceneBind.isExistBind("SENSOR_IN", sensorIn.getSensorId())){
		    					MessageBoxHelper.openWarning(shell, error_scene_mode_off);
		    				}else{
				    			sendSensorInDeletePacket(sensorIn);
		    				}
			    		}
						break;
					case DictConst.MENU_LINEATE:
						MessageBoxHelper.openWarning(shell, error_lineate_cant_delete);
						break;
					case DictConst.MENU_TEMP_SENSOR:
						if(tbTempSensor.getSelectionCount() > 0 && MessageBoxHelper.openQuestion(shell, message_delete_title)){
			    			TempSensor sensor = (TempSensor)tbTempSensor.getSelection()[0].getData();
			    			try {
			    				boolean isExist = boSceneBind.isExistBind("TEMP_SENSOR", sensor.getSensorId());
			    				
			    				if(isExist){
			    					MessageBoxHelper.openWarning(shell, error_scene_mode_off);
			    					break;
			    				}else{
			    					boTempSensor.delete(sensor.getSensorId());
									//tbTempSensor.remove(tbTempSensor.getSelectionIndex());
									tableTempSensorHelper.deleteTable(tbTempSensor);
			    				}
							} catch (Exception e) {
								e.printStackTrace();
								MessageBoxHelper.openError(shell, message_delete_error);
							}
			    		}
						break;
				}
				break;
		}
    }
    
    private void setOutSubMenuOff(){
    	setEditTextDefaut();
    	ibFloor.setImage(icons.getImage(IconHolder.btnSubMenuOff));
    	ibRoom.setImage(icons.getImage(IconHolder.btnSubMenuOff));
    	ibSensorOut.setImage(icons.getImage(IconHolder.btnSubMenuOff));
    	ibTransfer.setImage(icons.getImage(IconHolder.btnSubMenuOff));
    	ibDevice.setImage(icons.getImage(IconHolder.btnSubMenuOff));
    	if(SystemConfig.getInstance().isEngine()){
    		ibRelay.setImage(icons.getImage(IconHolder.btnSubMenuOff));
    	}
    	
    	if(SystemConfig.getInstance().hasSim()){
    		ibTelOut.setImage(icons.getImage(IconHolder.btnSubMenuOff));
        	ibSmsOut.setImage(icons.getImage(IconHolder.btnSubMenuOff));
    	}
    }
    
    private void setInSubMenuOff(){
    	setEditTextDefaut();
    	ibSensorIn.setImage(icons.getImage(IconHolder.btnSubMenuOff));
    	ibTempSensor.setImage(icons.getImage(IconHolder.btnSubMenuOff));
    	if(SystemConfig.getInstance().isEngine()){
    		ibLineate.setImage(icons.getImage(IconHolder.btnSubMenuOff));
    	}
    	if(SystemConfig.getInstance().hasSim()){
	    	ibTelIn.setImage(icons.getImage(IconHolder.btnSubMenuOff));
	    	ibSmsIn.setImage(icons.getImage(IconHolder.btnSubMenuOff));
    	}
    }
    
    private void changePage(int pageNum){
    	if(pageNum == DictConst.PAGE_OUT){
    		menuOut.setVisible(true);
    		scOut.setVisible(true);
    		menuIn.setVisible(false);
    		scIn.setVisible(true);
    		btnOutRight.setVisible(true);
    		btnOutLeft.setVisible(true);
    		setInSubMenuOff();
    		switch(selectionOutMenu){
    			case DictConst.MENU_FLOOR:
    				openFloor();
    				break;
    			case DictConst.MENU_ROOM:
    				openRoom();
    				break;
    			case DictConst.MENU_TRANSFER:
    				openTransfer();
    				break;
    			case DictConst.MENU_DEVICE:
    				openDevice();
    				break;
    			case DictConst.MENU_SENSOROUT:
    				openSensorOut();
    				break;
    			case DictConst.MENU_TEL_OUT:
    				openTelOut();
    				break;
    			case DictConst.MENU_SMS_OUT:
    				openSmsOut();
    				break;
    			case DictConst.MENU_RELAY:
    				openRelay();
    				break;
    			
    		}
    		btnOut.setImage(icons.getImage(IconHolder.btnNormalOn));
    		btnIn.setImage(icons.getImage(IconHolder.btnNormal));
    	}else{
    		menuOut.setVisible(false);
    		scOut.setVisible(false);
    		menuIn.setVisible(true);
    		scIn.setVisible(true);
    		btnOutRight.setVisible(false);
    		btnOutLeft.setVisible(false);
    		setOutSubMenuOff();
            
    		switch(selectionInMenu){
				case DictConst.MENU_SENSORIN:
					openSensorIn();
					break;
				case DictConst.MENU_LINEATE:
					openLineate();
					break;
				case DictConst.MENU_TEMP_SENSOR:
					openTempSensor();
					break;
				case DictConst.MENU_TEL_IN:
					openTelIn();
					break;
				case DictConst.MENU_SMS_IN:
					openSmsIn();
					break;
			}
    		
    		btnIn.setImage(icons.getImage(IconHolder.btnNormalOn));
    		btnOut.setImage(icons.getImage(IconHolder.btnNormal));
    	}
    	
    	selectionPage = pageNum;
    }
    
    private void setEditTextDefaut(){
    	btnEdit.setText("修改");
    }
    
    private void setEditTextSpec(){
    	btnEdit.setText("添加/删除");
    }
    
    /**
     * 打开楼层
     */
    private void openFloor(){
    	menuPage.setVisible(false);
		selectionOutMenu = DictConst.MENU_FLOOR;
    	setOutSubMenuOff();
    	clearComposite(menuPage);
    	tbFloor = tableFloorHelper.createTable(menuPage);
    	tableFloorHelper.showTable(tbFloor, boFloor.getList());
    	tbFloor.setBounds(0, 0, 650, 310);
    	ibFloor.setImage(icons.getImage(IconHolder.btnSubMenu));
    	menuPage.setVisible(true);
    	btnAdd.setVisible(true);
    	btnEdit.setVisible(true);
    	btnDel.setVisible(true);
    }
    
    /**
     * 打开房间
     */
    private void openRoom(){
    	menuPage.setVisible(false);
		selectionOutMenu = DictConst.MENU_ROOM;
		setOutSubMenuOff();
		clearComposite(menuPage);
		tbRoom = tableRoomHelper.createTable(menuPage);
    	tbRoom.setBounds(0, 0, 650, 310);
    	tableRoomHelper.showTable(tbRoom, boRoom.getList());
    	ibRoom.setImage(icons.getImage(IconHolder.btnSubMenu));
    	menuPage.setVisible(true);
    	btnAdd.setVisible(true);
    	btnEdit.setVisible(true);
    	btnDel.setVisible(true);
    }
    
    /**
     * 打开红外转发器
     */
    private void openTransfer(){
    	menuPage.setVisible(false);
		selectionOutMenu = DictConst.MENU_TRANSFER;
    	setOutSubMenuOff();
    	clearComposite(menuPage);
    	ibTransfer.setImage(icons.getImage(IconHolder.btnSubMenu));
        tbTransfer = tableTransferHelper.createTable(menuPage);
        tbTransfer.setBounds(0, 0, 650, 310);
    	tableTransferHelper.showTable(tbTransfer, boTransfer.getList());
    	menuPage.setVisible(true);
    	btnAdd.setVisible(true);
    	btnEdit.setVisible(true);
    	btnDel.setVisible(true);
    }
    
    /**
     * 打开GSM
     */
    private void openTelOut(){
    	menuPage.setVisible(false);
    	selectionOutMenu = DictConst.MENU_TEL_OUT;
    	setOutSubMenuOff();
    	clearComposite(menuPage);
    	ibTelOut.setImage(icons.getImage(IconHolder.btnSubMenu));
    	tbTelOut = tableTelOutHelper.createTable(menuPage);
    	tbTelOut.setBounds(0, 0, 650, 310);
    	// 初始化
    	uiGSMHelper.initGSM();
    	tableTelOutHelper.showTable(tbTelOut, boTelOut.getList());
    	setEditTextSpec();
    	menuPage.setVisible(true);
    	btnAdd.setVisible(false);
    	btnEdit.setVisible(true);
    	btnDel.setVisible(false);
    }
    
    /**
     * 打开GSM
     */
    private void openSmsOut(){
    	menuPage.setVisible(false);
    	selectionOutMenu = DictConst.MENU_SMS_OUT;
    	setOutSubMenuOff();
    	clearComposite(menuPage);
    	ibSmsOut.setImage(icons.getImage(IconHolder.btnSubMenu));
    	tbSmsOut = tableSmsOutHelper.createTable(menuPage);
    	tbSmsOut.setBounds(0, 0, 650, 310);
    	// 初始化
    	uiGSMHelper.initGSM();
    	tableSmsOutHelper.showTable(tbSmsOut, boSmsOut.getList());
    	setEditTextSpec();
    	menuPage.setVisible(true);
    	btnAdd.setVisible(false);
    	btnEdit.setVisible(true);
    	btnDel.setVisible(false);
    }
    
    /**
     * 打开无线输出
     */
    private void openSensorOut(){
    	menuPage.setVisible(false);
		selectionOutMenu = DictConst.MENU_SENSOROUT;
    	setOutSubMenuOff();
    	clearComposite(menuPage);
    	ibSensorOut.setImage(icons.getImage(IconHolder.btnSubMenu));
    	tbSensorOut = tableSensorOutHelper.createTable(menuPage);
        tbSensorOut.setBounds(0, 0, 650, 310);
    	tableSensorOutHelper.showTable(tbSensorOut, boSensorOut.getList());
    	menuPage.setVisible(true);
    	btnAdd.setVisible(true);
    	btnEdit.setVisible(true);
    	btnDel.setVisible(true);
    }
    
    /**
     * 打开常规设备
     */
    private void openDevice(){
    	menuPage.setVisible(false);
		selectionOutMenu = DictConst.MENU_DEVICE;
    	setOutSubMenuOff();
    	clearComposite(menuPage);
    	ibDevice.setImage(icons.getImage(IconHolder.btnSubMenu));
        tbDevice = tableDeviceHelper.createTable(menuPage);
        tbDevice.setBounds(0, 0, 650, 310);
    	tableDeviceHelper.showTable(tbDevice, boDevice.getList());
    	menuPage.setVisible(true);
    	btnAdd.setVisible(true);
    	btnEdit.setVisible(true);
    	btnDel.setVisible(true);
    }
    
    /**
     * 打开继电器
     */
    private void openRelay(){
    	menuPage.setVisible(false);
		selectionOutMenu = DictConst.MENU_RELAY;
    	setOutSubMenuOff();
    	clearComposite(menuPage);
    	ibRelay.setImage(icons.getImage(IconHolder.btnSubMenu));
    	tbRelay = tableRelayHelper.createTable(menuPage);
        tbRelay.setBounds(0, 0, 650, 310);
    	// 初始化继电器
    	uiRelayHelper.initRelay();
    	tableRelayHelper.showTable(tbRelay, boRelay.getList());
    	menuPage.setVisible(true);
    	btnAdd.setVisible(false);
    	btnEdit.setVisible(true);
    	btnDel.setVisible(false);
    }
    
    /**
     * 打开无线输入
     */
    private void openSensorIn(){
    	menuPage.setVisible(false);
		selectionInMenu = DictConst.MENU_SENSORIN;
    	setInSubMenuOff();
    	clearComposite(menuPage);
    	ibSensorIn.setImage(icons.getImage(IconHolder.btnSubMenu));
    	tbSensorIn = tableSensorInHelper.createTable(menuPage);
        tbSensorIn.setBounds(0, 0, 650, 310);
    	tableSensorInHelper.showTable(tbSensorIn, boSensorIn.getList());
    	menuPage.setVisible(true);
    	btnAdd.setVisible(true);
    	btnEdit.setVisible(true);
    	btnDel.setVisible(true);
    }
    
    /**
     * 打开有线电输入
     */
    private void openLineate(){
    	menuPage.setVisible(false);
		selectionInMenu = DictConst.MENU_LINEATE;
		setInSubMenuOff();
		clearComposite(menuPage);
    	ibLineate.setImage(icons.getImage(IconHolder.btnSubMenu));
        tbLineate = tableLineateHelper.createTable(menuPage);
        tbLineate.setBounds(0, 0, 650, 310);
    	// 初始化有线电
    	uiLineateHelper.initLineate();
    	tableLineateHelper.showTable(tbLineate, boLineate.getList());
    	menuPage.setVisible(true);
    	btnAdd.setVisible(false);
    	btnEdit.setVisible(true);
    	btnDel.setVisible(false);
    }
    
    /**
     * 湿温度
     */
    private void openTempSensor(){
    	menuPage.setVisible(false);
		selectionInMenu = DictConst.MENU_TEMP_SENSOR;
		setInSubMenuOff();
		clearComposite(menuPage);
    	ibTempSensor.setImage(icons.getImage(IconHolder.btnSubMenu));
    	tbTempSensor = tableTempSensorHelper.createTable(menuPage);
        tbTempSensor.setBounds(0, 0, 650, 310);
    	tableTempSensorHelper.showTable(tbTempSensor, boTempSensor.getList());
    	tbTempSensor.setVisible(true);
    	menuPage.setVisible(true);
    	btnAdd.setVisible(true);
    	btnEdit.setVisible(true);
    	btnDel.setVisible(true);
    }
    
    /**
     * 打开GSM
     */
    private void openTelIn(){
    	menuPage.setVisible(false);
    	selectionInMenu = DictConst.MENU_TEL_IN;
    	setInSubMenuOff();
    	clearComposite(menuPage);
    	ibTelIn.setImage(icons.getImage(IconHolder.btnSubMenu));
    	tbTelIn = tableTelInHelper.createTable(menuPage);
    	tbTelIn.setBounds(0, 0, 650, 310);
    	// 初始化
    	uiGSMHelper.initGSM();
    	tableTelInHelper.showTable(tbTelIn, boTelIn.getList());
    	setEditTextSpec();
    	menuPage.setVisible(true);
    	btnAdd.setVisible(false);
    	btnEdit.setVisible(true);
    	btnDel.setVisible(false);
    }
    
    /**
     * 打开GSM
     */
    private void openSmsIn(){
    	menuPage.setVisible(false);
    	selectionInMenu = DictConst.MENU_SMS_IN;
    	setInSubMenuOff();
    	clearComposite(menuPage);
    	ibSmsIn.setImage(icons.getImage(IconHolder.btnSubMenu));
    	tbSmsIn = tableSmsInHelper.createTable(menuPage);
    	tbSmsIn.setBounds(0, 0, 650, 310);
    	// 初始化
    	uiGSMHelper.initGSM();
    	tableSmsInHelper.showTable(tbSmsIn, boSmsIn.getList());
    	setEditTextSpec();
    	menuPage.setVisible(true);
    	btnAdd.setVisible(false);
    	btnEdit.setVisible(true);
    	btnDel.setVisible(false);
    }
    
	@Override
	public void packetArrived(PacketEvent e) {
		InPacket in = (InPacket) e.getSource();
    	
		// 现在开始判断包的类型，作出相应的处理
		switch (in.getCommand()) {
       		case Protocol.CMD_PT2262_315M_DELETE:
       		case Protocol.CMD_PT2262_433M_DELETE:
       		case Protocol.CMD_EV1527_315M_DELETE:
       		case Protocol.CMD_EV1527_433M_DELETE:
       		case Protocol.CMD_RFSTUY_315M_DELETE:
       		case Protocol.CMD_RFSTUY_433M_DELETE:
       			processSensorDeleteSuccess(in);
       			break;
       		case Protocol.CMD_UNKNOWN:
            	processUnknown(in);
            	break;
        }
	}
	
	/**
	 * 
	 * @param sensorIn
	 */
	private void sendSensorInDeletePacket(SensorIn sensorIn){
		OutPacket packet = null;
		if(SystemConfig.getInstance().isHardSoftVer2030()){
			packet = SensorPacketUtil.getSensorInDeleteOutPacket(sensorIn.getSensorId(), sensorIn.getFreqType(), sensorIn.getCodeType(), 
					sensorIn.getResType(), sensorIn.getAddrCode(), sensorIn.getDataCode());
		}else{
			packet = SensorPacketUtil.getSensorInRFDeleteOutPacket(sensorIn.getSensorId(), sensorIn.getFreqType(), sensorIn.getCodeType(), 
					sensorIn.getResType(), sensorIn.getAddrCode(), sensorIn.getDataCode());
		}
		
		
		packetProcessor.send(packet);
	}
	
	 /**
     * 处理无线输入删除事件
     * @param in
     */
    private void processSensorDeleteSuccess(InPacket in){
    	display.syncExec(new Runnable() {
			public void run() {
				try {
					boSensorIn.delete(sensorIn);
					//tbSensorIn.remove(tbSensorIn.getSelectionIndex());
					tableSensorInHelper.deleteTable(tbSensorIn);
				} catch (Exception e) {
					e.printStackTrace();
					MessageBoxHelper.openError(shell, message_opreate_error);
				}
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
