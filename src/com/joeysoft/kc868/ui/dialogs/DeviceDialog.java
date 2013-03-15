package com.joeysoft.kc868.ui.dialogs;

import static com.joeysoft.kc868.resource.Messages.*;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CCombo;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.ShellAdapter;
import org.eclipse.swt.events.ShellEvent;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import com.joeysoft.kc868.SystemConfig;
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
import com.joeysoft.kc868.db.bean.Room;
import com.joeysoft.kc868.db.bean.SensorNor;
import com.joeysoft.kc868.db.bean.vo.DeviceLtWnCl;
import com.joeysoft.kc868.db.bo.BODevice;
import com.joeysoft.kc868.db.bo.BOFloor;
import com.joeysoft.kc868.db.bo.BORoom;
import com.joeysoft.kc868.db.util.DataAddrCodeUtil;
import com.joeysoft.kc868.db.util.DictConst;
import com.joeysoft.kc868.db.util.DictManager;
import com.joeysoft.kc868.exception.DataExistException;
import com.joeysoft.kc868.resource.IconHolder;
import com.joeysoft.kc868.ui.BorderStyler;
import com.joeysoft.kc868.ui.Colors;
import com.joeysoft.kc868.ui.MainShell;
import com.joeysoft.kc868.ui.dialogs.helper.ComboHelper;
import com.joeysoft.kc868.ui.helper.MessageBoxHelper;
import com.joeysoft.kc868.ui.helper.UITool;
import com.joeysoft.kc868.widgets.ImageButton;

/**
 * 设备对话框
 * @author JOEY
 *
 */
public class DeviceDialog extends Dialog implements IPacketListener{

	private Shell shell;
    private Display display;
	 // Shell
    private Shell dialog;
    
    // 所有控件
    private ImageButton btnOk, btnCancel;
    
    private Text textDeviceName, textVidiconUrl, textVidiconPort, textVidiconUser, textVidiconPwd;
    private CCombo comboDeviceType, comboRoom, comboFloor, comboFreqType, comboCodeType, comboResType;
    
    private Composite compType;
    
    // IconHolder实例
    private IconHolder icons = IconHolder.getInstance();
    
    private MainShell main;
    private PacketProcessor packetProcessor;
    
    private BODevice boDevice;
    private BOFloor boFloor;
    private BORoom boRoom;
    
    private Device device, retDevice;
    private boolean isAdd = true;
    
    private int ltWnClCurrentIndex;
    private List<DeviceLtWnCl> ltWnClList;
    
    private int[] iSwitch = new int[]{1,1,1,1,1,1,1,1};
    
    /**
     * 构造函数
     * 
     * @param shell
     * 		父窗口
     * @param ignoreAuto
     * 		true表示忽略掉自动登录选项，不要自动返回
     */
    public DeviceDialog(MainShell main) {
        super(main.getShell(), SWT.NO_TRIM | SWT.NO_BACKGROUND);
        this.main = main;
        this.packetProcessor = main.getClient().getPacketProcessor();
        this.shell = main.getShell();
        display = shell.getDisplay();
        boDevice = new BODevice();
        boFloor = new BOFloor();
        boRoom = new BORoom();
    }

    private void initLayout() {
    	BorderStyler styler = new BorderStyler();
    	styler.setHideWhenMinimize(false);
    	styler.setResizable(false);
    	Composite center = styler.decorateShell(dialog);
    	//center.setBackgroundImage(icons.getBackgroundImage(IconHolder.bmpEquip));
    	center.setBackgroundMode(SWT.INHERIT_FORCE);
    	center.setBackground(Colors.LOGIN_BACKGROUND);

        UITool.setDefaultBackground(null);
        UITool.setDefaultForeground(Colors.WHITE);
        
        // 设备名称
        Label label = UITool.createLabel(center, device_name);
        label.setBounds(24, 27, 124, 20);
        label.setFont(Colors.GLOBAL_FONT);
        // 无线设备名称框
        textDeviceName = UITool.createSingleText(center, SWT.SINGLE);
        textDeviceName.setBackground(Colors.WHITE);
        textDeviceName.setBounds(160, 27, 176, 20);
       
        // 设备类型
        label = UITool.createLabel(center, device_type);
        label.setBounds(24, 67, 124, 20);
        label.setFont(Colors.GLOBAL_FONT);
        // 设备类型框
        comboDeviceType = UITool.createCCombo(center, SWT.SIMPLE | SWT.READ_ONLY);
        comboDeviceType.setBounds(160, 67, 176, 20);
        comboDeviceType.setBackground(Colors.WHITE);
        ComboHelper.initComboList(comboDeviceType, 
        		DictManager.getInstance().getDictList(DictConst.DEVICE_TYPE));
        comboDeviceType.select(0);
        
        // 楼层
        label = UITool.createLabel(center, floor);
        label.setBounds(24, 107, 124, 20);
        label.setFont(Colors.GLOBAL_FONT);
        // 楼层框
        comboFloor = UITool.createCCombo(center, SWT.SIMPLE | SWT.READ_ONLY);
        comboFloor.setBounds(160, 107, 176, 20);
        comboFloor.setBackground(Colors.WHITE);
        ComboHelper.initFloorComboList(comboFloor, boFloor.getList());
        comboFloor.select(0);
        
        // 房间
        label = UITool.createLabel(center, room);
        label.setBounds(24, 147, 124, 20);
        label.setFont(Colors.GLOBAL_FONT);
        // 房间框
        comboRoom = UITool.createCCombo(center, SWT.SIMPLE | SWT.READ_ONLY);
        comboRoom.setBounds(160, 147, 176, 20);
        comboRoom.setBackground(Colors.WHITE);
        Floor floor = (Floor)comboFloor.getData(comboFloor.getText());
        if(floor != null) ComboHelper.initRoomComboList(comboRoom, boRoom.getListByFloor(floor.getFloor()));
        comboRoom.select(0);
        
        compType = new Composite(center, SWT.NONE);
        compType.setBounds(0, 187, 378, 141);
        
        // 按钮区
        btnOk =  UITool.createImageButton(compType, button_ok, icons.getImage(IconHolder.bmpBtnLogin), 
        		icons.getImage(IconHolder.bmpBtnLoginUp), icons.getImage(IconHolder.bmpBtnLoginOn));
        btnOk.setFont(Colors.GLOBAL_FONT);
        btnOk.setBounds(54, 30, 114, 41);
        // 确定按钮鼠标事件
        btnOk.addMouseListener(new MouseAdapter() {
            public void mouseUp(MouseEvent e) {
            	doOk();
            }
        });
        
        btnCancel = UITool.createImageButton(compType, button_cancel, icons.getImage(IconHolder.bmpBtnLogin), 
        		icons.getImage(IconHolder.bmpBtnLoginUp), icons.getImage(IconHolder.bmpBtnLoginOn));
        btnCancel.setFont(Colors.GLOBAL_FONT);
        btnCancel.setBounds(195, 30, 114, 41);
        // 取消按钮鼠标事件
        btnCancel.addMouseListener(new MouseAdapter() {
            public void mouseUp(MouseEvent e) {
            	dialog.close();
            }
        });
    }
    
    /**
	 * 打开对话框
	 */
	public Device open() {
		// create dialog
	    Shell parent = getParent();
	    Display display = parent.getDisplay();
		dialog = new Shell(parent, getStyle());
		
		initV();
		
		initLayout();
		// 初始化控件的值
		initValue();
		// init event listeners
		initListeners();
		
		hookListener();
		
		// set title and image
		dialog.setImage(icons.getImage(IconHolder.icoHome));
		dialog.setText(SystemConfig.getInstance().getSystemTitle());
		// set dialog to center of screen
		dialog.pack();
		// 设置窗口位置和大小
		dialog.setSize(378, 306);
		Rectangle bounds = display.getPrimaryMonitor().getBounds();
        Rectangle rect = dialog.getBounds();
        int x = bounds.x + (bounds.width - rect.width) / 2;
        int y = bounds.y + (bounds.height - rect.height) / 2;
        dialog.setLocation(x, y);
        
		// event loop
		dialog.open();
		initControl();
		
		while(!dialog.isDisposed()) 
			if(!display.readAndDispatch()) 
			    display.sleep();
			
		return retDevice;
	}
	
    /**
     * 初始化监听器
     */
    private void initListeners() {
    	dialog.addShellListener(new ShellAdapter() {
			public void shellClosed(ShellEvent e) {
				unhookListener();
				main.getShellRegistry().deregisterDeviceDialog();
			}
		});
    	
        // 楼层下拉框
        comboFloor.addSelectionListener(new SelectionAdapter(){
			@Override
			public void widgetSelected(SelectionEvent e) {
				Floor floor = (Floor)comboFloor.getData(comboFloor.getText());
				ComboHelper.initRoomComboList(comboRoom, boRoom.getListByFloor(floor.getFloor()));
				comboRoom.select(0);
			}
        });
        
        // 楼层下拉框
        comboDeviceType.addSelectionListener(new SelectionAdapter(){
			@Override
			public void widgetSelected(SelectionEvent e) {
				String deviceType = (String)comboDeviceType.getData(comboDeviceType.getText());
				changeDeviceType(deviceType);
			}
        });
    }
    
    /**
     * 选择设备类型
     * @param deviceType
     */
    private void changeDeviceType(String deviceType){
    	compType.setVisible(false);
    	clearComposite(compType);
    	int btnY = 30;
    	int dialogHeight = 306;
    	
    	if(DictConst.DEVICE_TYPE_SX.equals(deviceType)){
    		Label label = UITool.createLabel(compType, vidicon_url);
            label.setBounds(24, 0, 124, 20);
            label.setFont(Colors.GLOBAL_FONT);
            
            textVidiconUrl = UITool.createSingleText(compType, SWT.SINGLE);
            textVidiconUrl.setBackground(Colors.WHITE);
            textVidiconUrl.setBounds(160, 0, 176, 20);
            
            label = UITool.createLabel(compType, vidicon_port);
            label.setBounds(24, 47, 124, 20);
            label.setFont(Colors.GLOBAL_FONT);
            
            textVidiconPort = UITool.createSingleText(compType, SWT.SINGLE);
            textVidiconPort.setBackground(Colors.WHITE);
            textVidiconPort.setBounds(160, 47, 176, 20);
            
           label = UITool.createLabel(compType, vidicon_user);
            label.setBounds(24, 87, 124, 20);
            label.setFont(Colors.GLOBAL_FONT);
            
            textVidiconUser = UITool.createSingleText(compType, SWT.SINGLE);
            textVidiconUser.setBackground(Colors.WHITE);
            textVidiconUser.setBounds(160, 87, 176, 20);
            
            label = UITool.createLabel(compType, vidicon_pwd);
            label.setBounds(24, 127, 124, 20);
            label.setFont(Colors.GLOBAL_FONT);
            
            textVidiconPwd = UITool.createSingleText(compType, SWT.SINGLE);
            textVidiconPwd.setBackground(Colors.WHITE);
            textVidiconPwd.setBounds(160, 127, 176, 20);
            
            btnY = 190;
            dialogHeight = 466;
            
    	}else if(DictConst.DEVICE_TYPE_PD.equals(deviceType) || DictConst.DEVICE_TYPE_TD.equals(deviceType)
    			|| DictConst.DEVICE_TYPE_CL.equals(deviceType) || DictConst.DEVICE_TYPE_MB.equals(deviceType)){// 电灯// 窗帘// 幕布
    		 // 频率类型
    		Label label = UITool.createLabel(compType, freq_type);
            label.setBounds(24, 0, 124, 20);
            label.setFont(Colors.GLOBAL_FONT);
            // 频率类型框
            comboFreqType = UITool.createCCombo(compType, SWT.SIMPLE | SWT.READ_ONLY);
            comboFreqType.setBounds(160, 0, 176, 20);
            comboFreqType.setBackground(Colors.WHITE);
            
            ComboHelper.initComboList(comboFreqType, 
            		DictManager.getInstance().getDictList(DictConst.FREQ_TYPE));
            comboFreqType.select(0);
            
            // 编码类型
            label = UITool.createLabel(compType, code_type);
            label.setBounds(24, 47, 124, 20);
            label.setFont(Colors.GLOBAL_FONT);
            // 编码类型框
            comboCodeType = UITool.createCCombo(compType, SWT.SIMPLE | SWT.READ_ONLY);
            comboCodeType.setBounds(160, 47, 176, 20);
            comboCodeType.setBackground(Colors.WHITE);
            ComboHelper.initComboList(comboCodeType, 
            		DictManager.getInstance().getDictList(DictConst.CODE_TYPE));
            comboCodeType.select(0);
            // 编码类型下拉框
            comboCodeType.addSelectionListener(new SelectionAdapter(){
    			@Override
    			public void widgetSelected(SelectionEvent e) {
    				String codeType = (String)comboCodeType.getData(comboCodeType.getText());
    				if("2262".equals(codeType)){
    					ComboHelper.initComboList(comboResType, 
    			        		DictManager.getInstance().getDictList(DictConst.RES_TYPE));
    				}else if("1527".equals(codeType)){
    					ComboHelper.initComboList(comboResType, 
    			        		DictManager.getInstance().getDictList(DictConst.RES_TYPE2));
    				}
    				
    				comboResType.select(0);
    			}
            });
            
            // 电阻类型
            label = UITool.createLabel(compType, res_type);
            label.setBounds(24, 87, 124, 20);
            label.setFont(Colors.GLOBAL_FONT);
            // 电阻类型框
            comboResType = UITool.createCCombo(compType, SWT.SIMPLE | SWT.READ_ONLY);
            comboResType.setBounds(160, 87, 176, 20);
            comboResType.setBackground(Colors.WHITE);
            ComboHelper.initComboList(comboResType, 
            		DictManager.getInstance().getDictList(DictConst.RES_TYPE));
            comboResType.select(0);
            
            btnY = 150;
            dialogHeight = 426;
            
            if(DictConst.DEVICE_TYPE_TD.equals(deviceType)){ // 调光灯
            	// 设备名称
                label = UITool.createLabel(compType, addr_code);
                label.setBounds(24, 127, 70, 20);
                label.setFont(Colors.GLOBAL_FONT);
                
                label = UITool.createLabel(compType, "H");
                label.setBounds(100, 127, 10, 20);
                label.setFont(Colors.GLOBAL_FONT);
                
                label = UITool.createLabel(compType, "N");
                label.setBounds(100, 147, 10, 20);
                label.setFont(Colors.GLOBAL_FONT);
                
                label = UITool.createLabel(compType, "L");
                label.setBounds(100, 167, 10, 20);
                label.setFont(Colors.GLOBAL_FONT);
                
                // 地址码框
                for(int i=0; i<iSwitch.length; i++){
                	final int index = i;
                	final Label lbSwitch = UITool.createLabel(compType, "");
                	lbSwitch.setBounds(i*30+120, 127, 23, 54);
                	lbSwitch.setImage(iSwitch[index] == 1?icons.getImage(IconHolder.switch1):iSwitch[index] == 0?icons.getImage(IconHolder.switch3):icons.getImage(IconHolder.switch2));
                	if(isAdd){
                		lbSwitch.setCursor(compType.getDisplay().getSystemCursor(SWT.CURSOR_HAND));
                    	lbSwitch.addMouseListener(new MouseAdapter() {
                            public void mouseUp(MouseEvent e) {
                            	switch(iSwitch[index]){
                            	case 1:
                            		iSwitch[index] = 2;
                            		break;
                            	case 2:
                            		iSwitch[index] = 0;
                            		break;
                            	case 0:
                            		iSwitch[index] = 1;
                            		break;
                            	}
                            	lbSwitch.setImage(iSwitch[index] == 1?icons.getImage(IconHolder.switch1):iSwitch[index] == 0?icons.getImage(IconHolder.switch3):icons.getImage(IconHolder.switch2));
                            }
                        });
                	}
                	
                	label = UITool.createLabel(compType, ""+(iSwitch.length - i));
                    label.setBounds(i*30+127, 183, 10, 20);
                    label.setFont(Colors.GLOBAL_FONT);
                }
            	
                btnY = 210;
                dialogHeight = 486;
            }
    	}
    	dialog.setSize(378, dialogHeight);
    	compType.setBounds(0, 187, 378, 341);
         
         // 按钮区
         btnOk =  UITool.createImageButton(compType, button_ok, icons.getImage(IconHolder.bmpBtnLogin), 
         		icons.getImage(IconHolder.bmpBtnLoginUp), icons.getImage(IconHolder.bmpBtnLoginOn));
         btnOk.setFont(Colors.GLOBAL_FONT);
         btnOk.setBounds(54, btnY, 114, 41);
         // 确定按钮鼠标事件
         btnOk.addMouseListener(new MouseAdapter() {
             public void mouseUp(MouseEvent e) {
             	doOk();
             }
         });
         
         btnCancel = UITool.createImageButton(compType, button_cancel, icons.getImage(IconHolder.bmpBtnLogin), 
         		icons.getImage(IconHolder.bmpBtnLoginUp), icons.getImage(IconHolder.bmpBtnLoginOn));
         btnCancel.setFont(Colors.GLOBAL_FONT);
         btnCancel.setBounds(195, btnY, 114, 41);
         // 取消按钮鼠标事件
         btnCancel.addMouseListener(new MouseAdapter() {
             public void mouseUp(MouseEvent e) {
             	dialog.close();
             }
         });
         
         compType.setVisible(true);
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
    
    private void initV(){
    	if(!isAdd){
    		iSwitch = DataAddrCodeUtil.getSwitchs8Int(device.getAddrCode());
    	}
    }
    
    /**
     * 初始化控件的值
     */
    private void initValue() {
    	if(!isAdd){ // 修改
        	textDeviceName.setText(device.getDeviceName());
        	comboDeviceType.setText(DictManager.getInstance().getDictName(DictConst.DEVICE_TYPE, device.getDeviceType()));
        	comboDeviceType.setEnabled(false);
        	comboFloor.setText(device.getFloorName());
        	Floor floor = (Floor)comboFloor.getData(comboFloor.getText());
            if(floor != null) ComboHelper.initRoomComboList(comboRoom, boRoom.getListByFloor(floor.getFloor()));
        	comboRoom.setText(device.getRoomName());
    	}
    }
    
    private void initControl(){
    	if(!isAdd){
    		if(DictConst.DEVICE_TYPE_SX.equals(device.getDeviceType())){
        		changeDeviceType(device.getDeviceType());
        		
        		textVidiconUrl.setText(device.getVidiconUrl()); 
            	textVidiconPort.setText(device.getVidiconPort());
            	textVidiconUser.setText(device.getVidiconUser());
            	textVidiconPwd.setText(device.getVidiconPwd());
        	}else if(DictConst.DEVICE_TYPE_TD.equals(device.getDeviceType())){
        		changeDeviceType(device.getDeviceType());
        		
        		comboFreqType.setText(DictManager.getInstance().getDictName(DictConst.FREQ_TYPE, device.getFreqType()));
            	comboCodeType.setText(DictManager.getInstance().getDictName(DictConst.CODE_TYPE, device.getCodeType()));
            	String codeType = (String)comboCodeType.getData(comboCodeType.getText());
    			if("2262".equals(codeType)){
    				ComboHelper.initComboList(comboResType, 
    		        		DictManager.getInstance().getDictList(DictConst.RES_TYPE));
    			}else if("1527".equals(codeType)){
    				ComboHelper.initComboList(comboResType, 
    		        		DictManager.getInstance().getDictList(DictConst.RES_TYPE2));
    			}
            	comboResType.setText(DictManager.getInstance().getDictName(DictConst.RES_TYPE, device.getResType()));
            	
            	comboFreqType.setEnabled(false);
            	comboCodeType.setEnabled(false);
            	comboResType.setEnabled(false);
        	}
    	}
    }
    
    /**
     * 检验
     */
    private boolean validation(){
    	if(StringUtils.isEmpty(textDeviceName.getText())){
    		MessageBoxHelper.openError(dialog, validation_device_name_isEmpty);
    		return false;
    	}
    	if(textDeviceName.getText().getBytes().length > 15){
    		MessageBoxHelper.openError(dialog, validation_device_name_length);
    		return false;
    	}
    	if(StringUtils.isEmpty(comboFloor.getText())){
    		MessageBoxHelper.openError(dialog, validation_floor_isEmpty);
    		return false;
    	}
    	if(StringUtils.isEmpty(comboRoom.getText())){
    		MessageBoxHelper.openError(dialog, validation_room_isEmpty);
    		return false;
    	}
    	if(StringUtils.isEmpty(comboDeviceType.getText())){
    		MessageBoxHelper.openError(dialog, validation_device_isEmpty);
    		return false;
    	}
    	
    	return true;
    }
    
    /**
     * 确定按钮时调用这个方法
     */
    private void doOk() {
    	if(!validation()){
    		return;
    	}
    	btnOk.setEnabled(false);
    	if(isAdd){ // 添加
    		device =  new Device();
    		Room room = (Room)comboRoom.getData(comboRoom.getText());
    		device.setRoomId(room.getRoomId());
    		device.setDeviceName(textDeviceName.getText());
    		device.setDeviceType((String)comboDeviceType.getData(comboDeviceType.getText()));
    		device.setRoomName(room.getRoomName());
    		device.setFloorName(room.getFloorName());
			
    		try {
    			if(DictConst.DEVICE_TYPE_PD.equals(device.getDeviceType()) || DictConst.DEVICE_TYPE_TD.equals(device.getDeviceType())
    	    			|| DictConst.DEVICE_TYPE_CL.equals(device.getDeviceType()) || DictConst.DEVICE_TYPE_MB.equals(device.getDeviceType())){// 电灯// 窗帘// 幕布
    				
    				device.setFreqType((String)comboFreqType.getData(comboFreqType.getText()));
    	    		device.setCodeType((String)comboCodeType.getData(comboCodeType.getText()));
    	    		device.setResType((String)comboResType.getData(comboResType.getText()));
    	    		if(DictConst.DEVICE_TYPE_TD.equals(device.getDeviceType())){
    	    			device.setAddrCode(DataAddrCodeUtil.getAddrCode8Int(iSwitch));
    	    		}
    	    			
    				ltWnClList = boDevice.insertLtWnClBefore(device);
    				SendLtWnClWritePacket();
    			}else{
    				if(DictConst.DEVICE_TYPE_SX.equals(device.getDeviceType())){
    					device.setVidiconPort(textVidiconPort.getText());
    					device.setVidiconPwd(textVidiconPwd.getText());
    					device.setVidiconUrl(textVidiconUrl.getText());
    					device.setVidiconUser(textVidiconUser.getText());
    				}
    				
    				boDevice.insert(device);
    				retDevice = device;
    				dialog.close();
    			}
			} catch (DataExistException e) {
				btnOk.setEnabled(true);
        		MessageBoxHelper.openError(dialog, e.getMessage());
				e.printStackTrace();
			}catch (Exception e) {
				btnOk.setEnabled(true);
        		MessageBoxHelper.openError(dialog, message_opreate_error);
				e.printStackTrace();
			}
    		
    		
    	}else{
    		device.setDeviceName(textDeviceName.getText());
    		Room room = (Room)comboRoom.getData(comboRoom.getText());
    		device.setRoomId(room.getRoomId());
    		device.setDeviceName(textDeviceName.getText());
    		device.setDeviceType((String)comboDeviceType.getData(comboDeviceType.getText()));
    		device.setRoomName(room.getRoomName());
    		device.setFloorName(room.getFloorName());
    		
    		if(DictConst.DEVICE_TYPE_SX.equals(device.getDeviceType())){
    			device.setVidiconId(device.getVidiconId());
				device.setVidiconPort(textVidiconPort.getText());
				device.setVidiconPwd(textVidiconPwd.getText());
				device.setVidiconUrl(textVidiconUrl.getText());
				device.setVidiconUser(textVidiconUser.getText());
			}
    		
    		if(boDevice.update(device)){
    			retDevice = device;
        		dialog.close();
        	}else{
        		btnOk.setEnabled(true);
        		MessageBoxHelper.openError(dialog, message_opreate_error);
        	}
    	}
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
    	   proccessLtWnClWriteSuccess(in);
       	break;
       case Protocol.CMD_UNKNOWN:
       	processUnknown(in);
       	break;
       }
	}
    
    /**
     * 发送电灯、窗帘、幕布的write
     */
    private void SendLtWnClWritePacket(){
    	DeviceLtWnCl deviceLtWnCl= ltWnClList.get(ltWnClCurrentIndex);
    	SensorNor sensorNor = deviceLtWnCl.getSensorNor();
    	OutPacket packet = SensorPacketUtil.getSensorWriteOutPacket(sensorNor.getSensorId(),
    			sensorNor.getFreqType(), sensorNor.getCodeType(), sensorNor.getResType(),
    			sensorNor.getAddrCode(), sensorNor.getDataCode()) ;
    	packetProcessor.send(packet);
    }
    
    /**
     * 处理Write发送成功
     */
    private void proccessLtWnClWriteSuccess(InPacket in){
    	ltWnClCurrentIndex++;
    	
    	if(ltWnClCurrentIndex >= ltWnClList.size()){ // 最后一个成功
    		try {
    			display.syncExec(new Runnable() {
    				public void run() {
    					try {
    						boDevice.insertLtWnCl(device, ltWnClList);
    						retDevice = device;
    						close();
    					} catch (DataExistException e) {
    						btnOk.setEnabled(true);
    						MessageBoxHelper.openError(dialog, e.getMessage());
    					} catch (Exception e) {
    						e.printStackTrace();
    						btnOk.setEnabled(true);
    						MessageBoxHelper.openError(dialog, message_opreate_error);
    					}
    				}
    			});
			} catch (Exception e) {
				e.printStackTrace();
				btnOk.setEnabled(true);
			}
    	}else{
    		SendLtWnClWritePacket();
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
				btnOk.setEnabled(true);
				MessageBoxHelper.openError(shell, message);
			}
		});
	}
    
    /**
	 * 关闭dialog
	 */
	public void close() {
		dialog.close();
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
    
	public void setDevice(Device device) {
		if(device != null){
			isAdd = false;
		}
		this.device = device;
	}

	public Display getDisplay() {
		return display;
	}

	public Shell getDialog() {
		return dialog;
	}

	public Shell getShell() {
		return shell;
	}
	
	
}
