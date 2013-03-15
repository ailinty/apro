package com.joeysoft.kc868.ui;

import static com.joeysoft.kc868.resource.Messages.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.MessageBox;
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
import com.joeysoft.kc868.client.packets.InPacket;
import com.joeysoft.kc868.client.packets.OutPacket;
import com.joeysoft.kc868.client.packets.in.RF1101ReadReplyPacket;
import com.joeysoft.kc868.client.packets.out.RF1101ReadPacket;
import com.joeysoft.kc868.client.packets.out.lineate.RelaySetRelayOffPacket;
import com.joeysoft.kc868.client.packets.out.lineate.RelaySetRelayOnPacket;
import com.joeysoft.kc868.client.packets.util.SensorPacketUtil;
import com.joeysoft.kc868.client.support.PacketProcessor;
import com.joeysoft.kc868.client.support.Protocol;
import com.joeysoft.kc868.db.bean.Device;
import com.joeysoft.kc868.db.bean.DeviceKey;
import com.joeysoft.kc868.db.bean.Floor;
import com.joeysoft.kc868.db.bean.Lineate;
import com.joeysoft.kc868.db.bean.Relay;
import com.joeysoft.kc868.db.bean.Room;
import com.joeysoft.kc868.db.bean.SensorOut;
import com.joeysoft.kc868.db.bean.SensorIn;
import com.joeysoft.kc868.db.bean.TempSensor;
import com.joeysoft.kc868.db.bean.Transfer;
import com.joeysoft.kc868.db.bean.TsfSensor;
import com.joeysoft.kc868.db.bean.Unused;
import com.joeysoft.kc868.db.bo.BODevice;
import com.joeysoft.kc868.db.bo.BODeviceKey;
import com.joeysoft.kc868.db.bo.BOFloor;
import com.joeysoft.kc868.db.bo.BOLineate;
import com.joeysoft.kc868.db.bo.BORelay;
import com.joeysoft.kc868.db.bo.BORoom;
import com.joeysoft.kc868.db.bo.BOSensorIn;
import com.joeysoft.kc868.db.bo.BOSensorOut;
import com.joeysoft.kc868.db.bo.BOTempSensor;
import com.joeysoft.kc868.db.bo.BOTransfer;
import com.joeysoft.kc868.db.bo.BOTransferSensor;
import com.joeysoft.kc868.db.util.DictConst;
import com.joeysoft.kc868.exception.DataExistException;
import com.joeysoft.kc868.resource.IconHolder;
import com.joeysoft.kc868.resource.IconUtil;
import com.joeysoft.kc868.ui.dialogs.helper.TableDeviceHelper;
import com.joeysoft.kc868.ui.dialogs.helper.TableFloorHelper;
import com.joeysoft.kc868.ui.dialogs.helper.TableLineateHelper;
import com.joeysoft.kc868.ui.dialogs.helper.TableRelayHelper;
import com.joeysoft.kc868.ui.dialogs.helper.TableRoomHelper;
import com.joeysoft.kc868.ui.dialogs.helper.TableSensorInHelper;
import com.joeysoft.kc868.ui.dialogs.helper.TableSensorOutHelper;
import com.joeysoft.kc868.ui.dialogs.helper.TableTempSensorHelper;
import com.joeysoft.kc868.ui.dialogs.helper.TableTransferHelper;
import com.joeysoft.kc868.ui.dialogs.helper.UIDeviceHelper;
import com.joeysoft.kc868.ui.dialogs.helper.UILineateHelper;
import com.joeysoft.kc868.ui.dialogs.helper.UIRelayHelper;
import com.joeysoft.kc868.ui.helper.MessageBoxHelper;
import com.joeysoft.kc868.ui.helper.ShellLauncher;
import com.joeysoft.kc868.ui.helper.ShellRegistry;
import com.joeysoft.kc868.ui.helper.UITool;
import com.joeysoft.kc868.widgets.AlphaComposite;
import com.joeysoft.kc868.widgets.ImageButton;

/**
 * 房间
 * @author JOEY
 *
 */
public class RoomShell implements IPacketListener{

	private Shell shell;
    private Display display;
    
    // 所有控件
    private ImageButton btnBack, btnFlLeft, btnFlRight, btnRmLeft, btnRmRight, btnStudy;
    
    // 楼层面板
    private ScrolledComposite scFloors;
    private Composite compRooms;
    private Label lbFloor;
    // 房间左边面板， 房间右边面板
    private Composite compRmLeft, compRmRight, compTempWed;
    private ScrolledComposite scRmRight;
    
    private Label lbTemp, lbWed;
    
    // 房间左边面板分三列
    private int rmLeftCols = 3;
    // 偏移量
    private int increment = 104;
    // 当前位置
    private int nPos, nMax;
    
    // 当前楼层序号
    private int currentFloor = -1;
    // 每个楼层的当前房间序号
    private Map<Integer, Integer> mpCurrentRoom;
    
    // 每个房间的当前的设备序号
    private Map<Integer, Integer> mpCurrentDevice;
    
    private List<Floor> floorList;
    
    private IconHolder icons = IconHolder.getInstance();
    
    private PacketProcessor packetProcessor;
    
    private MainShell main;
    
    private BOFloor boFloor;
    private BORoom boRoom;
    private BODevice boDevice;
    private BODeviceKey boDeviceKey;
    private BOTransfer boTansfer;
    private BOTransferSensor boTransferSensor;
    private BOSensorOut boSensorOut;
    private BORelay boRelay;
    private BOTempSensor boTempSensor;
    
    private Unused nextUnused;
    
    private Transfer transfer;
    
    private TsfSensor tsfSensor;
    
    private DeviceKey deviceKey;
    
    private UIDeviceHelper uiDeviceHelper;
    
    // 是否在学习状态中..., 是否在学习中...
    private boolean isStudyStatus, isStudying;
    
    
    public RoomShell(MainShell main){
    	this.main = main;
        this.packetProcessor = main.getClient().getPacketProcessor();
        this.display = main.getDisplay();
    	initialize();
    }

	/**
     * 初始化
     */
    private void initialize() {
    	boFloor = new BOFloor();
    	boRoom = new BORoom();
    	boDevice =  new BODevice();
    	boDeviceKey = new BODeviceKey();
    	boTansfer =  new BOTransfer();
    	boTransferSensor =  new BOTransferSensor();
    	boSensorOut = new BOSensorOut();
    	boRelay = new BORelay();
    	boTempSensor = new BOTempSensor();
    	
    	uiDeviceHelper = new UIDeviceHelper(this);
    	
    	mpCurrentRoom = new HashMap<Integer, Integer>();
    	mpCurrentDevice = new HashMap<Integer, Integer>();
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
    	body.setBackgroundImage(icons.getImage(IconHolder.bmpBgRoom));
    	body.setBackgroundMode(SWT.INHERIT_FORCE);
    	body.setBackground(Colors.LOGIN_BACKGROUND);
    	
    	Label label = UITool.createLabel(body, room);
        label.setBounds(370, 27, 74, 40);
        label.setFont(Colors.LOADING_FONT);
        
        floorList = boFloor.getList();
        currentFloor = 0;
        
        if(floorList.size() <= 0){ // 空楼层
        	MessageBoxHelper.openError(shell, error_create_floor);
        }
        
        // 楼层控件
        
        Composite comp = new Composite(body, SWT.NONE);
		comp.setBounds(15, 27, 212, 36);
		comp.setBackgroundImage(icons.getImage(IconHolder.btnFloor));
		int nameLength = 0;
		if(floorList.size() > currentFloor && floorList.get(currentFloor).getFloorName() != null){
			nameLength = floorList.get(currentFloor).getFloorName().length();
		}

		String floorName = floorList.size() > currentFloor ?  floorList.get(currentFloor).getFloorName() : "";
		
		lbFloor = UITool.createLabel(comp, floorName);
		lbFloor.setBounds((comp.getClientArea().width - nameLength * 18) / 2, 10, nameLength * 18, 20);
		lbFloor.setFont(Colors.GLOBAL_FONT);
		lbFloor.setForeground(Colors.BLACK);
        
        btnFlLeft = UITool.createImageButton(comp, "", icons.getImage(IconHolder.btnFloorLeft), null, null);
        btnFlLeft.setFont(Colors.GLOBAL_FONT);
        btnFlLeft.setBounds(15, 8, 16, 22);
        btnFlLeft.addMouseListener(new MouseAdapter(){
			public void mouseUp(MouseEvent e) {
				currentFloor = currentFloor <=0 ? floorList.size() - 1 : currentFloor - 1;
				changeFloor();
	        }
		});
        
        btnFlRight = UITool.createImageButton(comp, "", icons.getImage(IconHolder.btnFloorRight), null, null);
        btnFlRight.setFont(Colors.GLOBAL_FONT);
        btnFlRight.setBounds(181, 8, 16, 22);
        btnFlRight.addMouseListener(new MouseAdapter(){
			public void mouseUp(MouseEvent e) {
				currentFloor = currentFloor >= floorList.size() - 1 ? 0 : currentFloor + 1;
				changeFloor();
	        }
		});
        
        // 返回 按钮
        btnBack =  UITool.createImageButton(body, button_back, icons.getImage(IconHolder.btnBack), 
        		icons.getImage(IconHolder.btnBackOn), icons.getImage(IconHolder.btnBackOn));
        btnBack.setFont(Colors.GLOBAL_FONT);
        btnBack.setBounds(702, 27, 77, 36);
        btnBack.setTextColor(Colors.BLACK);
        
    	scFloors = new ScrolledComposite(body, SWT.NONE);
    	scFloors.setBounds(32, 82, 728, 50);
    	compRooms = new Composite(scFloors, SWT.NONE);
    	scFloors.setContent(compRooms);
    	compRooms.setBounds(0, 0, 728, 50);
        
    	nMax = 728 - 728;
    	
    	Composite compRoom = new Composite(body, SWT.NONE);
    	compRoom.setBounds(34, 145, 750, 430);
		
    	// 温湿度传感器
    	showTempWed(compRoom);
    	
		compRmLeft = new Composite(compRoom, SWT.NONE);
		compRmLeft.setBounds(0, 40, 350, 420);
		
		scRmRight = new ScrolledComposite(compRoom, SWT.V_SCROLL);
		scRmRight.setBounds(380, 10, 365, 420);
		compRmRight = new Composite(scRmRight, SWT.NONE);
		scRmRight.setContent(compRmRight);
    	compRmRight.setBounds(0, 10, 365, 420);
    	
		
        btnRmLeft =  UITool.createImageButton(body, "", icons.getImage(IconHolder.btnLeft), null, null);
        btnRmLeft.setFont(Colors.GLOBAL_FONT);
        btnRmLeft.setBounds(12, 92, 16, 22);
        btnRmLeft.addMouseListener(new MouseAdapter(){
			public void mouseUp(MouseEvent e) {
				Point location = compRooms.getLocation();
				nPos = nPos - increment <= 0?0:nPos - increment;
				compRooms.setLocation(-nPos, location.y);
	        }
		});
        
        btnRmRight =  UITool.createImageButton(body, "", icons.getImage(IconHolder.btnRight), null, null);
        btnRmRight.setFont(Colors.GLOBAL_FONT);
        btnRmRight.setBounds(760, 92, 16, 22);
        btnRmRight.addMouseListener(new MouseAdapter(){
			public void mouseUp(MouseEvent e) {
				Point location = compRooms.getLocation();
				nPos = nPos + increment >= nMax?nMax:nPos + increment;
				compRooms.setLocation(-nPos, location.y);
	        }
		});
        
        changeFloor();
    }
    
    private void showTempWed(Composite compRoom){
    	compTempWed =  new Composite(compRoom, SWT.NONE);
    	compTempWed.setVisible(false);
    	compTempWed.setBounds(70, 0, 260, 40);
    	
    	Label label = UITool.createLabel(compTempWed, "");
    	label.setBounds(0, 0, 15, 29);
    	label.setImage(icons.getImage(IconHolder.icoTemp));
    	
    	lbTemp = UITool.createLabel(compTempWed, "");
    	lbTemp.setBounds(23, 5, 66, 29);
    	lbTemp.setFont(Colors.NORMAL_FONT);
    	
    	label = UITool.createLabel(compTempWed, "");
    	label.setBounds(90, 5, 19, 20);
    	label.setImage(icons.getImage(IconHolder.icoC));
    	
    	label = UITool.createLabel(compTempWed, "");
    	label.setBounds(140, 0, 15, 29);
    	label.setImage(icons.getImage(IconHolder.icoWed));
    	lbWed = UITool.createLabel(compTempWed, "");
    	lbWed.setBounds(160, 5, 80, 29);
    	lbWed.setFont(Colors.NORMAL_FONT);
    }
    
    private void changeTempWedText(Float temperature, Float humidity){
    	if(temperature != null){
    		lbTemp.setText(""+temperature);
    	}else{
    		lbTemp.setText("");
    	}
    	if(humidity != null){
    		lbWed.setText(""+humidity + "%");
    	}else{
    		lbWed.setText("");
    	}
    	compTempWed.setVisible(true);
    }
    
    /**
     * 切换楼层
     */
    private void changeFloor(){
    	compTempWed.setVisible(false);
    	lbWed.setText("");
    	lbTemp.setText("");
    	
    	Floor floor = null;
    	
    	if(floorList.size() > currentFloor){
    		floor = floorList.get(currentFloor);
    	}
    	
    	if(floor != null){
    		Integer currentRoom = mpCurrentRoom.get(currentFloor);
        	
        	lbFloor.setText(floor.getFloorName());
        	// 清空面板
        	clearComposite(compRooms); 
        	clearComposite(compRmLeft);
        	clearComposite(compRmRight);
        	
        	List<Room> roomList = boRoom.getListByFloor(floor.getFloor());// 房间列表
        	int roomSize = roomList.size();
        	
        	if(roomSize * 104 > 728){
        		nMax = roomSize * 104 - 728;
        		compRooms.setBounds(0, 0, roomSize * 104, 50);
        	}
        	
        	for(int r=0; r<roomSize; r++){
        		final Room room = roomList.get(r);
        		final ImageButton ibRooms = UITool.createImageButton(compRooms, room.getRoomName(), icons.getImage(IconHolder.btnSubMenuOff), 
                		null, null);
        		ibRooms.setBounds(r * 104, 0, 100, 43);
        		ibRooms.addMouseListener(new MouseAdapter(){
    				public void mouseUp(MouseEvent e) {
    					setRoomsOff();
    					ibRooms.setImage(icons.getImage(IconHolder.btnSubMenu));
    					changeRoom(room);
    			    }
    			});
        		
        		// 默认值
        		if(currentRoom == null && r ==0) currentRoom = room.getRoomId();
        		if(room.getRoomId() == currentRoom){
        			ibRooms.setImage(icons.getImage(IconHolder.btnSubMenu));
        			changeRoom(room);
        		}
        	}
    	}
    }
    
    /**
     * 切换房间
     */
    private void changeRoom(final Room room){
    	final int roomId = room.getRoomId();
    	compRmLeft.setVisible(false);
    	compRmRight.setVisible(false);
    	
    	compTempWed.setVisible(false);
    	lbWed.setText("");
    	lbTemp.setText("");
    	
    	mpCurrentRoom.put(currentFloor, roomId);
    	Integer currentDevice = mpCurrentDevice.get(roomId);
    	
    	clearComposite(compRmLeft);
    	clearComposite(compRmRight);
    	
    	// 灯光是否已经添加
    	boolean isLightCreated = false;
    	boolean isWindowCreated = false;
    	// 幕布是否已经添加
    	boolean isScreenCreated = false;
    	// 摄像头是否已经添加
    	boolean isVidiconCreated = false;
    	
    	int index = 0;
    	
    	// 显示湿湿度传感器
    	List<TempSensor> tempSensorList = boTempSensor.getListByRoomId(roomId);
    	if(!tempSensorList.isEmpty()){
    		TempSensor tempSensor = tempSensorList.get(0);
    		sendRF1101ReadPacket(tempSensor.getSensorId());
    	}
    	
    	List<Device> deviceList = boDevice.getListByRoomId(roomId);
		int deviceSize = deviceList.size();
		for(int d=0; d<deviceSize; d++){
			final Device device = deviceList.get(d);
			if(DictConst.DEVICE_TYPE_PD.equals(device.getDeviceType()) 
					|| DictConst.DEVICE_TYPE_TD.equals(device.getDeviceType())){
				if(isLightCreated){ // 灯光已存在
					continue;
				}
				device.setDeviceName(light);
				isLightCreated = true;
			}
			if(DictConst.DEVICE_TYPE_CL.equals(device.getDeviceType())){
				if(isWindowCreated){ // 窗帘已存在
					continue;
				}
				device.setDeviceName(window);
				isWindowCreated = true;
			}
			
			if(DictConst.DEVICE_TYPE_MB.equals(device.getDeviceType())){
				if(isScreenCreated){ // 幕布已存在
					continue;
				}
				device.setDeviceName(screen);
				isScreenCreated = true;
			}
			
			if(DictConst.DEVICE_TYPE_SX.equals(device.getDeviceType())){
				if(isVidiconCreated){ // 摄像头已存在
					continue;
				}
				device.setDeviceName(vidicon);
				isVidiconCreated = true;
			}
			
			final Label btn = UITool.createLabel(compRmLeft, device.getDeviceName());
    		btn.setCursor(display.getSystemCursor(SWT.CURSOR_HAND));
    		btn.setBounds((index%rmLeftCols) * 116, (index/rmLeftCols) * (96 + 30), 96, 96);
    		btn.setImage(icons.getImageByName(device.getDeviceIcon()));
    		btn.addMouseListener(new MouseAdapter() {
            	@Override
            	public void mouseDown(MouseEvent e) {
            		btn.setImage(icons.getImageByName(device.getDeviceIcon()));
            	}
        		public void mouseUp(MouseEvent e) {
        			btn.setImage(icons.getImageByName(IconUtil.getImageOn(device.getDeviceIcon())));
    				changeDevice(device);
        		}
        	});
    		btn.addMouseTrackListener(new MouseTrackAdapter() {
            	@Override
            	public void mouseEnter(MouseEvent e) {
            		btn.setImage(icons.getImageByName(IconUtil.getImageOn(device.getDeviceIcon())));
            	}
            	@Override
            	public void mouseExit(MouseEvent e) {
            		btn.setImage(icons.getImageByName(device.getDeviceIcon()));
            	}
            });
			
			Label label = UITool.createLabel(compRmLeft, device.getDeviceName());
			label.setFont(Colors.GLOBAL_FONT);
			label.setBounds((index%rmLeftCols) * 116 + 20, (index/rmLeftCols) * (96 + 30) + 96, 96, 20);
			
			// 默认值
    		if(currentDevice == null && d ==0) currentDevice = device.getDeviceId();
    		if(device.getDeviceId()== currentDevice){
    			changeDevice(device);
    		}
    		index++;
		}
		
		// 是否存在继电器
		if(SystemConfig.getInstance().isEngine() && boRelay.isExistsByRoomId(roomId)){
			final Label btn = UITool.createLabel(compRmLeft, relay_out);
    		btn.setCursor(display.getSystemCursor(SWT.CURSOR_HAND));
    		btn.setBounds((index%rmLeftCols) * 116, (index/rmLeftCols) * (96 + 30), 96, 96);
    		btn.setImage(icons.getImage(IconHolder.icoXd));
    		btn.addMouseListener(new MouseAdapter() {
            	@Override
            	public void mouseDown(MouseEvent e) {
            		btn.setImage(icons.getImage(IconHolder.icoXd));
            	}
        		public void mouseUp(MouseEvent e) {
        			btn.setImage(icons.getImage(IconHolder.icoXdOn));
        			Device device = new Device();
        			device.setRoomName(room.getRoomName());
        			device.setDeviceName(relay_out);
        			device.setDeviceType(DictConst.DEVICE_TYPE_XD);
        			device.setRoomId(roomId);
    				changeDevice(device);
        		}
        	});
    		btn.addMouseTrackListener(new MouseTrackAdapter() {
            	@Override
            	public void mouseEnter(MouseEvent e) {
            		btn.setImage(icons.getImage(IconHolder.icoXdOn));
            	}
            	@Override
            	public void mouseExit(MouseEvent e) {
            		btn.setImage(icons.getImage(IconHolder.icoXd));
            	}
            });
			
			Label label = UITool.createLabel(compRmLeft, relay_out);
			label.setFont(Colors.GLOBAL_FONT);
			label.setBounds((index%rmLeftCols) * 116 + 20, (index/rmLeftCols) * (96 + 30) + 96, 96, 20);
			
    		index++;
		}
				
		// 是否存在非常规设备
		if(boSensorOut.isExistsByRoomId(roomId)){
			final Label btn = UITool.createLabel(compRmLeft, other_device);
    		btn.setCursor(display.getSystemCursor(SWT.CURSOR_HAND));
    		btn.setBounds((index%rmLeftCols) * 116, (index/rmLeftCols) * (96 + 30), 96, 96);
    		btn.setImage(icons.getImage(IconHolder.icoOt));
    		btn.addMouseListener(new MouseAdapter() {
            	@Override
            	public void mouseDown(MouseEvent e) {
            		btn.setImage(icons.getImage(IconHolder.icoOt));
            	}
        		public void mouseUp(MouseEvent e) {
        			btn.setImage(icons.getImage(IconHolder.icoOtOn));
        			Device device = new Device();
        			device.setRoomName(room.getRoomName());
        			device.setDeviceName(other_device);
        			device.setDeviceType(DictConst.DEVICE_TYPE_QT);
        			device.setRoomId(roomId);
    				changeDevice(device);
        		}
        	});
    		btn.addMouseTrackListener(new MouseTrackAdapter() {
            	@Override
            	public void mouseEnter(MouseEvent e) {
            		btn.setImage(icons.getImage(IconHolder.icoOtOn));
            	}
            	@Override
            	public void mouseExit(MouseEvent e) {
            		btn.setImage(icons.getImage(IconHolder.icoOt));
            	}
            });
			
			Label label = UITool.createLabel(compRmLeft, other_device);
			label.setFont(Colors.GLOBAL_FONT);
			label.setBounds((index%rmLeftCols) * 116 + 20, (index/rmLeftCols) * (96 + 30) + 96, 96, 20);
			
    		index++;
		}
		
		
		compRmLeft.setVisible(true);
    	compRmRight.setVisible(true);
    }
    
    
    /**
     * 切换设备
     * @param 
     */
    private void changeDevice(Device device){
    	compRmRight.setBounds(0, 0, 365, 420);
    	isStudyStatus = false;
    	mpCurrentDevice.put(device.getRoomId(), device.getDeviceId());
    	compRmRight.setVisible(false);
    	clearComposite(compRmRight);
    	String title = device.getRoomName()+ " -> " + device.getDeviceName();
    	if(DictConst.DEVICE_TYPE_SX.equals(device.getDeviceType())){
    		title += double_zoom_in;
    	}
    	//int titleLen = title.getBytes().length;
    	
    	Label label = UITool.createLabel(compRmRight, title);
    	label.setBounds(10, 5, 260, 20);
    	label.setFont(Colors.GLOBAL_FONT);
    	
    	if(DictConst.DEVICE_TYPE_PD.equals(device.getDeviceType()) || DictConst.DEVICE_TYPE_TD.equals(device.getDeviceType())){ // 普通灯、调光灯
    		uiDeviceHelper.createLightUI(device, compRmRight);
		}else if(DictConst.DEVICE_TYPE_KT.equals(device.getDeviceType())){ // 空调
			// 学习 按钮
	        btnStudy =  UITool.createImageButton(compRmRight, study, icons.getImage(IconHolder.btnBack), null, null);
	        btnStudy.setFont(Colors.GLOBAL_FONT);
	        btnStudy.setBounds(270, 0, 77, 36);
	        btnStudy.setTextColor(Colors.BLACK);
	        btnStudy.addMouseListener(new MouseAdapter() {
                public void mouseDown(MouseEvent e) {
                	isStudyStatus = !isStudyStatus;
                	if(isStudyStatus){
                		btnStudy.setImage(icons.getImage(IconHolder.btnStudy));
                	}else{
                		btnStudy.setImage(icons.getImage(IconHolder.btnBack));
                	}
                }
    		});
	        
			uiDeviceHelper.createAirConditionUI(device, compRmRight);
		}else if(DictConst.DEVICE_TYPE_MB.equals(device.getDeviceType())){ // 幕布
			uiDeviceHelper.createScreenUI(device, compRmRight);
		}else if(DictConst.DEVICE_TYPE_CL.equals(device.getDeviceType())){ // 窗帘
	        
			uiDeviceHelper.createWindowUI(device, compRmRight);
		}else if(DictConst.DEVICE_TYPE_MT.equals(device.getDeviceType())){ // 多媒体
			// 学习 按钮
	        btnStudy =  UITool.createImageButton(compRmRight, study, icons.getImage(IconHolder.btnBack), null, null);
	        btnStudy.setFont(Colors.GLOBAL_FONT);
	        btnStudy.setBounds(270, 0, 77, 36);
	        btnStudy.setTextColor(Colors.BLACK);
	        btnStudy.addMouseListener(new MouseAdapter() {
	        	 public void mouseDown(MouseEvent e) {
	        		 isStudyStatus = !isStudyStatus;
                	if(isStudyStatus){
                		btnStudy.setImage(icons.getImage(IconHolder.btnStudy));
                	}else{
                		btnStudy.setImage(icons.getImage(IconHolder.btnBack));
                	}
                }
    		});
	        
			uiDeviceHelper.createMediaUI(device, compRmRight);
		}else if(DictConst.DEVICE_TYPE_QT.equals(device.getDeviceType())){ // 其它设备Z
			uiDeviceHelper.createOtherUI(device, compRmRight);
		}else if(DictConst.DEVICE_TYPE_XD.equals(device.getDeviceType())){ // 继电器
			uiDeviceHelper.createRelayUI(device, compRmRight);
		}else if(DictConst.DEVICE_TYPE_SX.equals(device.getDeviceType())){ // 摄像头
			final String url = uiDeviceHelper.createVidiconUI(device, compRmRight);
	    	label.addMouseListener(new MouseAdapter() {
		        public void mouseUp(MouseEvent e) {
		        	getMainShell().getShellLauncher().openVidiconShell(url);
		        }
		    });
		}
    	compRmRight.setVisible(true);
    }
    
    /**
     * 发送或学习操作，是发送还是学习通过isStudyStatus判断
     */
    public boolean sendOrStudyProccess(DeviceKey dk){
    	boolean ret = false;
    	deviceKey = dk;
    	if(isStudyStatus){ // 学习过程
    		isStudying = true;
    		// 恢复btnStudy
    		/*if(btnStudy != null && !btnStudy.isDisposed()){
    			btnStudy.setImage(icons.getImage(IconHolder.btnBack));
    		}*/
    		
			List<Transfer> transferList = boTansfer.getListByRoomId(dk.getRoomId());
			if(transferList.size() <= 0){ // 没有转发器
				MessageBoxHelper.openError(shell, error_room_havt_transfer);
				//isStudyStatus = false;
				return false;
			}
			if(transferList.size() == 1){ // 只有一个转发器，不弹出选择对话框
				transfer = transferList.get(0);
			}else{ // 有多个转发器，弹出选择对话框
				transfer = main.getShellLauncher().openSelectTransferDialog(transferList);
				if(transfer == null){
					// 恢复btnStudy
		    		/*if(btnStudy != null && !btnStudy.isDisposed()){
		    			btnStudy.setImage(icons.getImage(IconHolder.btnBack));
		    		}*/
					//isStudyStatus = false;
					return false;
				}
			}
			
			tsfSensor = new TsfSensor();
			//tsfSensor.setSensorId(sensorId);
			tsfSensor.setTransferId(transfer.getTransferId());
			tsfSensor.setSensorName(dk.getKeyName());
			tsfSensor.setAddrCode(transfer.getAddrCode());
			tsfSensor.setCodeType(transfer.getCodeType());
			tsfSensor.setFreqType(transfer.getFreqType());
			tsfSensor.setResType(transfer.getResType());
			//tsfSensor.setDataCode();
			try {
				if(StringUtils.isNotEmpty(dk.getSensorId())){ // 再次学习，收回资源
					boTransferSensor.deleteSensor(dk.getSensorId());
				}
			
				nextUnused = boTransferSensor.insertBefore(tsfSensor);
				ret = sendSensorWritePacket(tsfSensor);
				MessageBoxHelper.openInformation(shell, study_in);
			} catch (Exception e) {
				e.printStackTrace();
				//isStudyStatus = false;
				MessageBoxHelper.openError(shell, e.getMessage());
				ret = false;
			}
    	}else{ //发送
    		ret = sendDeviceKeySendPacket(dk);
    	}
    	return ret;
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
				main.getShellRegistry().deregisterRoomShell();
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
        case Protocol.CMD_PT2262_315M_WRITE:
        case Protocol.CMD_PT2262_433M_WRITE:
        case Protocol.CMD_EV1527_315M_WRITE:
        case Protocol.CMD_EV1527_433M_WRITE:
        	processSensorWriteSuccess(in);
        	break;
        case Protocol.CMD_PT2262_315M_SEND:
        case Protocol.CMD_PT2262_433M_SEND:
        case Protocol.CMD_EV1527_315M_SEND:
        case Protocol.CMD_EV1527_433M_SEND:
        	processSensorSendSuccess(in);
        	break;
        case Protocol.CMD_RELAY_SET_RELAY:
        	processRelaySetSuccess(in);
        case Protocol.CMD_RF1101_READ:
        	processRF1101ReadSuccess(in);
        	break;
        }
	}
    
    /**
     * 发送读取传感器数值
     * @param rfNum
     */
    public void sendRF1101ReadPacket(int rfNum){
    	RF1101ReadPacket packet = new RF1101ReadPacket();
    	packet.setRFNum(rfNum);
    	packetProcessor.sendNoResend(packet);
    }
    
    /**
     * 发送写入包
     * @param tsfSensor
     */
    public boolean sendSensorWritePacket(TsfSensor tsfSensor){
    	if(tsfSensor != null && StringUtils.isNotEmpty(tsfSensor.getSensorId())){
	    	OutPacket packet = SensorPacketUtil.getSensorWriteOutPacket(tsfSensor.getSensorId(), tsfSensor.getFreqType(), 
	    			tsfSensor.getCodeType(), tsfSensor.getResType(), tsfSensor.getAddrCode(), tsfSensor.getDataCode());
	    	
	    	packetProcessor.send(packet);
	    	return true;
    	}
    	return false;
    }
    
    /**
     * 发送sensorOut send包
     * @param sensorOut
     */
    public void sendSensorOutSendPacket(SensorOut sensorOut){
    	if(sensorOut != null && StringUtils.isNotEmpty(sensorOut.getSensorId())){
	    	OutPacket packet = SensorPacketUtil.getSensorSendOutPacket(sensorOut.getSensorId(), sensorOut.getFreqType()
	    			, sensorOut.getCodeType());
	    	packetProcessor.send(packet);
    	}
    }
    
    /**
     * 发送DeviceKey send包
     * @param sensorOut
     */
    public boolean sendDeviceKeySendPacket(DeviceKey deviceKey){
    	if(deviceKey != null && StringUtils.isNotEmpty(deviceKey.getSensorId())){
	    	OutPacket packet = SensorPacketUtil.getSensorSendOutPacket(deviceKey.getSensorId());
	    	packetProcessor.send(packet);
	    	return true;
    	}
    	return false;
    }
    
    /**
     * 发送DeviceKey send包
     * @param sensorOut
     */
    public void sendTransferSendPacket(Transfer transfer){
    	if(transfer != null && StringUtils.isNotEmpty(transfer.getSensorId())){
    		OutPacket packet = SensorPacketUtil.getSensorSendOutPacket(transfer.getSensorId(), transfer.getFreqType(),
        			transfer.getCodeType());
        	packetProcessor.send(packet);
    	}
    }
    
    /**
     * 发送DeviceKey send包
     * @param sensorOut
     */
    public void sendTransferSensorSendPacket(TsfSensor tsfSensor){
    	if(tsfSensor != null && StringUtils.isNotEmpty(tsfSensor.getSensorId())){
    		OutPacket packet = SensorPacketUtil.getSensorSendOutPacket(tsfSensor.getSensorId(), tsfSensor.getFreqType(),
        			tsfSensor.getCodeType());
        	packetProcessor.send(packet);
    	}
    }
    
    /**
     * 发送DeviceKey send包
     * @param sensorOut
     */
    public void sendRelaySendPacket(Relay relay, boolean rStatus){
    	if(relay != null && relay.getRelayId() > 0){
    		if(rStatus){
        		RelaySetRelayOnPacket packet = new RelaySetRelayOnPacket();
        		packet.setLoad(relay.getRelayId());
        		packetProcessor.send(packet);
        	}else{
        		RelaySetRelayOffPacket packet = new RelaySetRelayOffPacket();
        		packet.setLoad(relay.getRelayId());
        		packetProcessor.send(packet);
        	}
    	}
    }
    
    /**
     * 处理发送传感器读取成功事件
     * @param in
     */
    private void processRF1101ReadSuccess(InPacket in){
    	RF1101ReadReplyPacket reply = (RF1101ReadReplyPacket)in;
    	final Float temp = reply.getTemperature();
    	final Float Wed = reply.getHumidity();
    	display.syncExec(new Runnable() {
			public void run() {
				changeTempWedText(temp, Wed);
			}
    	});
    }
    
    /**
     * 处理发送继电器成功事件
     * @param in
     */
    private void processRelaySetSuccess(InPacket in){
    	
    }
    
    
    /**
     * 处理发送信号成功事件
     * @param in
     */
    private void processSensorSendSuccess(InPacket in){
    	if(isStudyStatus && isStudying){ // 学习状态而且在学习过程中...
    		isStudying = false;
    		try {
				Thread.sleep(2000);
				sendTransferSensorSendPacket(tsfSensor);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
    	}
    }
    
    /**
     * 处理写入信号成功事件
     * @param in
     */
    private void processSensorWriteSuccess(InPacket in){
    	display.syncExec(new Runnable() {
			public void run() {
				try {
					boTransferSensor.insert(tsfSensor, nextUnused, deviceKey);
					sendTransferSendPacket(transfer); // 发送学习命令
				} catch (DataExistException e) {
					tsfSensor = null;
					MessageBoxHelper.openError(shell, e.getMessage());
				} catch (Exception e) {
					tsfSensor = null;
					e.printStackTrace();
					MessageBoxHelper.openError(shell, message_opreate_error);
				}
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
    private void setRoomsOff(){
    	for(Control c : compRooms.getChildren()){
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
