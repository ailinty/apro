package com.joeysoft.kc868.ui.dialogs;

import static com.joeysoft.kc868.resource.Messages.*;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CCombo;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.events.ShellAdapter;
import org.eclipse.swt.events.ShellEvent;
import org.eclipse.swt.events.VerifyEvent;
import org.eclipse.swt.events.VerifyListener;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
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
import com.joeysoft.kc868.db.bean.Floor;
import com.joeysoft.kc868.db.bean.Room;
import com.joeysoft.kc868.db.bean.SensorOut;
import com.joeysoft.kc868.db.bean.Unused;
import com.joeysoft.kc868.db.bo.BOFloor;
import com.joeysoft.kc868.db.bo.BOIcon;
import com.joeysoft.kc868.db.bo.BORoom;
import com.joeysoft.kc868.db.bo.BOSensorOut;
import com.joeysoft.kc868.db.util.DataAddrCodeUtil;
import com.joeysoft.kc868.db.util.DictConst;
import com.joeysoft.kc868.db.util.DictManager;
import com.joeysoft.kc868.exception.DataExistException;
import com.joeysoft.kc868.exception.ThresholdException;
import com.joeysoft.kc868.resource.IconHolder;
import com.joeysoft.kc868.ui.BorderStyler;
import com.joeysoft.kc868.ui.Colors;
import com.joeysoft.kc868.ui.MainShell;
import com.joeysoft.kc868.ui.dialogs.helper.ComboHelper;
import com.joeysoft.kc868.ui.helper.MessageBoxHelper;
import com.joeysoft.kc868.ui.helper.UITool;
import com.joeysoft.kc868.widgets.ImageButton;

/**
 * 无线输出(非常规设备)对话框
 * @author JOEY
 *
 */
public class SensorOutDialog extends Dialog implements IPacketListener{

	private Shell shell;
    private Display display;
	 // Shell
    private Shell dialog;
    
    // 所有控件
    private ImageButton btnOk, btnCancel;
    
    private Composite addrDataComp;
    
    private Text textSensorName, textPosition;
    private Button radioAuto, radioInput;
    
    private CCombo comboFloor, comboRoom, comboFreqType, comboCodeType,
    	comboResType, comboIcon;
    
    // IconHolder实例
    private IconHolder icons = IconHolder.getInstance();
    
    private MainShell main;
    private PacketProcessor packetProcessor;
    
    private BOSensorOut boSensorOut;
    private BOFloor boFloor;
    private BORoom boRoom;
    private BOIcon boIcon;
    
    private Unused nextUnused;
    
    private SensorOut sensorOut, retSensorOut;
    
    private boolean isAdd = true;
    private int[]iSwitch = new int[]{1,1,1,1,1,1,1,1};
    private boolean[] bSwitch4 = new boolean[]{true,true,true,true};
    
    /**
     * 构造函数
     * 
     * @param shell
     * 		父窗口
     * @param ignoreAuto
     * 		true表示忽略掉自动登录选项，不要自动返回
     */
    public SensorOutDialog(MainShell main) {
        super(main.getShell(), SWT.NO_TRIM | SWT.NO_BACKGROUND);
        this.main = main;
        this.packetProcessor = main.getClient().getPacketProcessor();
        this.shell = main.getShell();
        display = shell.getDisplay();
        boSensorOut = new BOSensorOut();
        boFloor = new BOFloor();
        boRoom = new BORoom();
        boIcon = new BOIcon();
        
        hookListener();
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
        Label label = UITool.createLabel(center, name);
        label.setBounds(24, 27, 124, 20);
        label.setFont(Colors.GLOBAL_FONT);
        // 无线设备名称框
        textSensorName = UITool.createSingleText(center, SWT.SINGLE);
        textSensorName.setBackground(Colors.WHITE);
        textSensorName.setBounds(160, 27, 176, 20);
       
        // 楼层
        label = UITool.createLabel(center, floor);
        label.setBounds(24, 67, 124, 20);
        label.setFont(Colors.GLOBAL_FONT);
        // 楼层框
        comboFloor = UITool.createCCombo(center, SWT.SIMPLE | SWT.READ_ONLY);
        comboFloor.setBounds(160, 67, 176, 20);
        comboFloor.setBackground(Colors.WHITE);
        ComboHelper.initFloorComboList(comboFloor, boFloor.getList());
        comboFloor.select(0);
        
        // 房间
        label = UITool.createLabel(center, room);
        label.setBounds(24, 107, 124, 20);
        label.setFont(Colors.GLOBAL_FONT);
        // 房间框
        comboRoom = UITool.createCCombo(center, SWT.SIMPLE | SWT.READ_ONLY);
        comboRoom.setBounds(160, 107, 176, 20);
        comboRoom.setBackground(Colors.WHITE);
        Floor floor = (Floor)comboFloor.getData(comboFloor.getText());
        if(floor != null) ComboHelper.initRoomComboList(comboRoom, boRoom.getListByFloor(floor.getFloor()));
        comboRoom.select(0);
        
        // 频率类型
        label = UITool.createLabel(center, freq_type);
        label.setBounds(24, 147, 124, 20);
        label.setFont(Colors.GLOBAL_FONT);
        // 频率类型框
        comboFreqType = UITool.createCCombo(center, SWT.SIMPLE | SWT.READ_ONLY);
        comboFreqType.setBounds(160, 147, 176, 20);
        comboFreqType.setBackground(Colors.WHITE);
        
        ComboHelper.initComboList(comboFreqType, 
        		DictManager.getInstance().getDictList(DictConst.FREQ_TYPE));
        comboFreqType.select(0);
        
        // 编码类型
        label = UITool.createLabel(center, code_type);
        label.setBounds(24, 187, 124, 20);
        label.setFont(Colors.GLOBAL_FONT);
        // 编码类型框
        comboCodeType = UITool.createCCombo(center, SWT.SIMPLE | SWT.READ_ONLY);
        comboCodeType.setBounds(160, 187, 176, 20);
        comboCodeType.setBackground(Colors.WHITE);
        ComboHelper.initComboList(comboCodeType, 
        		DictManager.getInstance().getDictList(DictConst.CODE_TYPE));
        comboCodeType.select(0);
        
        // 电阻类型
        label = UITool.createLabel(center, res_type);
        label.setBounds(24, 227, 124, 20);
        label.setFont(Colors.GLOBAL_FONT);
        // 电阻类型框
        comboResType = UITool.createCCombo(center, SWT.SIMPLE | SWT.READ_ONLY);
        comboResType.setBounds(160, 227, 176, 20);
        comboResType.setBackground(Colors.WHITE);
        ComboHelper.initComboList(comboResType, 
        		DictManager.getInstance().getDictList(DictConst.RES_TYPE));
        comboResType.select(0);
        
        // 图标
        label = UITool.createLabel(center, icon);
        label.setBounds(24, 267, 124, 20);
        label.setFont(Colors.GLOBAL_FONT);
        // 电阻类型框
        comboIcon = UITool.createCCombo(center, SWT.SIMPLE | SWT.READ_ONLY);
        comboIcon.setBounds(160, 267, 176, 20);
        comboIcon.setBackground(Colors.WHITE);
        ComboHelper.initIconComboList(comboIcon, boIcon.getListByType(DictConst.DEVICE_TYPE_QT));
        comboIcon.select(0);
        
        
        label = UITool.createLabel(center, "排序");
        label.setBounds(24, 307, 50, 20);
        label.setFont(Colors.GLOBAL_FONT);
        // 排序
        textPosition = UITool.createSingleText(center, SWT.SINGLE);
        textPosition.setBackground(Colors.WHITE);
        textPosition.setBounds(104, 307, 56, 20);
        textPosition.addVerifyListener(new VerifyListener() {    
        	public void verifyText(VerifyEvent e) {    
        		// 几种情况，输入控制键，输入中文，输入字符，输入数字    
        		// 正整数验证    
        		Pattern pattern = Pattern.compile("[0-9]\\d*");    
        		Matcher matcher = pattern.matcher(e.text);
        		if (matcher.matches()) // 处理数字
        			e.doit = true;
        		else if (e.text.length() > 0) // 有字符情况,包含中文、空格 
        			e.doit = false;
        		else
        			// 控制键    
        			e.doit = true;
        		}    
        });   
        
        radioInput= UITool.createRadio(center, radio_input);
        radioInput.setBounds(224, 307, 70, 20);
        radioInput.setSelection(true);
        radioInput.addSelectionListener(new SelectionAdapter(){
			@Override
			public void widgetSelected(SelectionEvent e) {
				addrDataComp.setVisible(e.doit);
			}
        });
        
        radioAuto= UITool.createRadio(center, radio_auto);
        radioAuto.setBounds(294, 307, 70, 20);
        radioAuto.addSelectionListener(new SelectionAdapter(){
			@Override
			public void widgetSelected(SelectionEvent e) {
				addrDataComp.setVisible(!e.doit);
			}
        });
        
        addrDataComp = new Composite(center, SWT.NONE);
        addrDataComp.setBounds(0, 347, 378, 116);
        
        // 地址码
        label = UITool.createLabel(addrDataComp, addr_code);
        label.setBounds(24, 0, 70, 20);
        label.setFont(Colors.GLOBAL_FONT);
        
        label = UITool.createLabel(addrDataComp, "H");
        label.setBounds(100, 0, 10, 20);
        label.setFont(Colors.GLOBAL_FONT);
        
        label = UITool.createLabel(addrDataComp, "N");
        label.setBounds(100, 20, 10, 20);
        label.setFont(Colors.GLOBAL_FONT);
        
        label = UITool.createLabel(addrDataComp, "L");
        label.setBounds(100, 40, 10, 20);
        label.setFont(Colors.GLOBAL_FONT);
        
        // 地址码框
        for(int i=0; i<iSwitch.length; i++){
        	final int index = i;
        	final Label lbSwitch = UITool.createLabel(addrDataComp, "");
        	lbSwitch.setBounds(i*30+120, 0, 23, 54);
        	lbSwitch.setImage(iSwitch[index] == 1?icons.getImage(IconHolder.switch1):iSwitch[index] == 0?icons.getImage(IconHolder.switch3):icons.getImage(IconHolder.switch2));
        	if(isAdd || SystemConfig.getInstance().isAdmin()){
        		lbSwitch.setCursor(center.getDisplay().getSystemCursor(SWT.CURSOR_HAND));
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
        	
        	label = UITool.createLabel(addrDataComp, ""+(iSwitch.length - i));
            label.setBounds(i*30+127, 56, 10, 20);
            label.setFont(Colors.GLOBAL_FONT);
        }
        
        // 数据码
        label = UITool.createLabel(addrDataComp, data_code);
        label.setBounds(24, 80, 70, 20);
        label.setFont(Colors.GLOBAL_FONT);
        
        label = UITool.createLabel(addrDataComp, "H");
        label.setBounds(100, 80, 10, 20);
        label.setFont(Colors.GLOBAL_FONT);
        
        label = UITool.createLabel(addrDataComp, "L");
        label.setBounds(100, 100, 10, 20);
        label.setFont(Colors.GLOBAL_FONT);
        
        // 数据码框
        for(int i=0; i<bSwitch4.length; i++){
        	final int index = i;
        	final Label lbSwitch = UITool.createLabel(addrDataComp, "");
        	lbSwitch.setBounds(i*30+120, 80, 23, 36);
        	lbSwitch.setImage(bSwitch4[index]?icons.getImage(IconHolder.switchOn):icons.getImage(IconHolder.switchOff));
        	if(isAdd || SystemConfig.getInstance().isAdmin()){
        		lbSwitch.setCursor(center.getDisplay().getSystemCursor(SWT.CURSOR_HAND));
        		lbSwitch.addMouseListener(new MouseAdapter() {
                    public void mouseUp(MouseEvent e) {
                    	bSwitch4[index] = !bSwitch4[index];
                    	lbSwitch.setImage(bSwitch4[index]?icons.getImage(IconHolder.switchOn):icons.getImage(IconHolder.switchOff));
                    }
                });
        	}
        	
        	label = UITool.createLabel(addrDataComp, ""+(bSwitch4.length - i));
            label.setBounds(i*30+127, 116, 10, 20);
            label.setFont(Colors.GLOBAL_FONT);
        }
        
        // 按钮区
        btnOk =  UITool.createImageButton(center, button_ok, icons.getImage(IconHolder.bmpBtnLogin), 
        		icons.getImage(IconHolder.bmpBtnLoginUp), icons.getImage(IconHolder.bmpBtnLoginOn));
        btnOk.setFont(Colors.GLOBAL_FONT);
        btnOk.setBounds(54, 497, 114, 41);
        
        btnCancel = UITool.createImageButton(center, button_cancel, icons.getImage(IconHolder.bmpBtnLogin), 
        		icons.getImage(IconHolder.bmpBtnLoginUp), icons.getImage(IconHolder.bmpBtnLoginOn));
        btnCancel.setFont(Colors.GLOBAL_FONT);
        btnCancel.setBounds(195, 497, 114, 41);
    }
    
    /**
	 * 打开对话框
	 */
	public SensorOut open() {
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
		
		// set title and image
		dialog.setImage(icons.getImage(IconHolder.icoHome));
		dialog.setText(SystemConfig.getInstance().getSystemTitle());
		// set dialog to center of screen
		dialog.pack();
		// 设置窗口位置和大小
		dialog.setSize(378, 577);
		Rectangle bounds = display.getPrimaryMonitor().getBounds();
        Rectangle rect = dialog.getBounds();
        int x = bounds.x + (bounds.width - rect.width) / 2;
        int y = bounds.y + (bounds.height - rect.height) / 2;
        dialog.setLocation(x, y);
        
		// event loop
		dialog.open();
		while(!dialog.isDisposed()) 
			if(!display.readAndDispatch()) 
			    display.sleep();
			
		return retSensorOut;
	}
	
    /**
     * 初始化监听器
     */
    private void initListeners() {
    	dialog.addShellListener(new ShellAdapter() {
			public void shellClosed(ShellEvent e) {
				unhookListener();
				main.getShellRegistry().deregisterSensorOutDialog();
			}
		});
    	
    	// 取消按钮鼠标事件
        btnCancel.addMouseListener(new MouseAdapter() {
            public void mouseUp(MouseEvent e) {
            	dialog.close();
            }
        });
        
        // 确定按钮鼠标事件
        btnOk.addMouseListener(new MouseAdapter() {
            public void mouseUp(MouseEvent e) {
            	doOk();
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
    	
    }
    
    private void initV(){
    	if(!isAdd){
    		iSwitch = DataAddrCodeUtil.getSwitchs8Int(sensorOut.getAddrCode());
    		bSwitch4 = DataAddrCodeUtil.getSwitchs4(sensorOut.getDataCode());
    	}
    }
    
    /**
     * 初始化控件的值
     */
    private void initValue() {
    	if(!isAdd){ // 修改
        	textSensorName.setText(sensorOut.getSensorName());
        	comboFloor.setText(sensorOut.getFloorName());
        	Floor floor = (Floor)comboFloor.getData(comboFloor.getText());
            if(floor != null) ComboHelper.initRoomComboList(comboRoom, boRoom.getListByFloor(floor.getFloor()));
        	comboRoom.setText(sensorOut.getRoomName());
        	comboFreqType.setText(DictManager.getInstance().getDictName(DictConst.FREQ_TYPE, sensorOut.getFreqType()));
        	comboCodeType.setText(DictManager.getInstance().getDictName(DictConst.CODE_TYPE, sensorOut.getCodeType()));
        	
        	String codeType = (String)comboCodeType.getData(comboCodeType.getText());
			if("2262".equals(codeType)){
				ComboHelper.initComboList(comboResType, 
		        		DictManager.getInstance().getDictList(DictConst.RES_TYPE));
			}else if("1527".equals(codeType)){
				ComboHelper.initComboList(comboResType, 
		        		DictManager.getInstance().getDictList(DictConst.RES_TYPE2));
			}
			
        	comboResType.setText(DictManager.getInstance().getDictName(DictConst.RES_TYPE, sensorOut.getResType()));
        	
        	if(!SystemConfig.getInstance().isAdmin()){ // 不是管理员
        		comboFreqType.setEnabled(false);
            	comboCodeType.setEnabled(false);
            	comboResType.setEnabled(false);
        	}
        	
        	textPosition.setText(""+sensorOut.getPosition());
    	}
    }
    
    /**
     * 检验
     */
    private boolean validation(){
    	if(StringUtils.isEmpty(textSensorName.getText())){
    		MessageBoxHelper.openError(dialog, validation_name_isEmpty);
    		return false;
    	}
    	if(textSensorName.getText().getBytes().length > 18){
    		MessageBoxHelper.openError(dialog, validation_name_length6);
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
    	
    	return true;
    }
    
    /**
     * 用户按了登录按钮时调用这个方法
     */
    private void doOk() {
    	if(!validation()){
    		return;
    	}
    	btnOk.setEnabled(false);
    	if(isAdd){ // 添加
    		sensorOut =  new SensorOut();
    		Room room = (Room)comboRoom.getData(comboRoom.getText());
    		sensorOut.setRoomId(room.getRoomId());
    		sensorOut.setSensorName(textSensorName.getText());
    		sensorOut.setFreqType((String)comboFreqType.getData(comboFreqType.getText()));
    		sensorOut.setCodeType((String)comboCodeType.getData(comboCodeType.getText()));
    		sensorOut.setResType((String)comboResType.getData(comboResType.getText()));
    		sensorOut.setIconName(comboIcon.getText());
    		
    		String strPosition = textPosition.getText();
    		if(StringUtils.isEmpty(strPosition)){
    			strPosition = "0";
    		}
    		sensorOut.setPosition(Integer.valueOf(textPosition.getText()));
    		
    		sensorOut.setRoomName(room.getRoomName());
    		sensorOut.setFloorName(room.getFloorName());
    		
    		if(radioInput.getSelection()){
    			sensorOut.setAddrCode(DataAddrCodeUtil.getAddrCode8Int(iSwitch));
        		sensorOut.setDataCode(DataAddrCodeUtil.getDataCode4(bSwitch4));
    		}else{
    			sensorOut.setAddrCode(-1);
    			sensorOut.setDataCode(-1);
    		}
    		
    		try {
    			nextUnused = boSensorOut.insertBefore(sensorOut);
				sendSensorWrite(sensorOut);
			} catch (Exception e) {
				btnOk.setEnabled(true);
				MessageBoxHelper.openError(dialog, e.getMessage());
				e.printStackTrace();
			}
    		
    	}else{
    		boolean isChanged = false;
    		String orgFregType =  sensorOut.getFreqType();
    		String orgCodeType =  sensorOut.getCodeType();
    		String orgResType =  sensorOut.getResType();
    		int orgAddrCode = sensorOut.getAddrCode();
    		int orgDataCode = sensorOut.getDataCode();
    		
    		sensorOut.setSensorName(textSensorName.getText());
    		Room room = (Room)comboRoom.getData(comboRoom.getText());
    		sensorOut.setRoomId(room.getRoomId());
    		sensorOut.setFreqType((String)comboFreqType.getData(comboFreqType.getText()));
    		sensorOut.setCodeType((String)comboCodeType.getData(comboCodeType.getText()));
    		sensorOut.setResType((String)comboResType.getData(comboResType.getText()));
    		sensorOut.setIconName(comboIcon.getText());
    		
    		sensorOut.setAddrCode(DataAddrCodeUtil.getAddrCode8Int(iSwitch));
    		sensorOut.setDataCode(DataAddrCodeUtil.getDataCode4(bSwitch4));
    		
    		String strPosition = textPosition.getText();
    		if(StringUtils.isEmpty(strPosition)){
    			strPosition = "0";
    		}
    		sensorOut.setPosition(Integer.valueOf(textPosition.getText()));
    		
    		sensorOut.setRoomName(room.getRoomName());
    		sensorOut.setFloorName(room.getFloorName());
    		
    		try {
    			if(!orgFregType.equals(sensorOut.getFreqType())){
    				isChanged = true;
    			}
    			if(!orgCodeType.equals(sensorOut.getCodeType())){
    				isChanged = true;
    			}
    			if(!orgResType.equals(sensorOut.getResType())){
    				isChanged = true;
    			}
    			if(orgAddrCode != sensorOut.getAddrCode()){
    				isChanged = true;
    			}
    			if(orgDataCode != sensorOut.getDataCode()){
    				isChanged = true;
    			}
    			if(isChanged && SystemConfig.getInstance().isAdmin()){
    				sendSensorWrite(sensorOut);
    			}else{
    				boSensorOut.update(sensorOut);
    				retSensorOut = sensorOut;
    				dialog.close();
    			}
    		} catch (DataExistException e) {
    			btnOk.setEnabled(true);
    			MessageBoxHelper.openError(dialog, e.getMessage());
			} catch (Exception e) {
				e.printStackTrace();
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
        	processSensorWriteSuccess(in);
        	break;
        case Protocol.CMD_UNKNOWN:
        	processUnknown(in);
        	break;
        }
	}
    
    /**
     * 发送写入无线信号
     * @param sensorOut
     */
    private void sendSensorWrite(SensorOut sensorOut){
    	OutPacket packet = SensorPacketUtil.getSensorWriteOutPacket(sensorOut.getSensorId(), 
    			sensorOut.getFreqType(), sensorOut.getCodeType(), sensorOut.getResType(), sensorOut.getAddrCode(), sensorOut.getDataCode());
    	
    	packetProcessor.send(packet);
    }
    
    /**
     * 处理无线写入成功事件
     * @param in
     */
    private void processSensorWriteSuccess(InPacket in){
    	display.syncExec(new Runnable() {
			public void run() {
				try {
					if(isAdd){
						boSensorOut.insert(sensorOut, nextUnused);
					}else{
						boSensorOut.update(sensorOut);
					}
					
					retSensorOut = sensorOut;
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
    
	public void setSensorOut(SensorOut sensorOut) {
		if(sensorOut != null) isAdd = false;
		this.sensorOut = sensorOut;
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
