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
import com.joeysoft.kc868.client.packets.out.SetHLimitPacket;
import com.joeysoft.kc868.client.packets.out.SetSlaverPacket;
import com.joeysoft.kc868.client.packets.out.SetTLimitPacket;
import com.joeysoft.kc868.client.support.PacketProcessor;
import com.joeysoft.kc868.client.support.Protocol;
import com.joeysoft.kc868.db.bean.TempSensor;
import com.joeysoft.kc868.db.bean.Floor;
import com.joeysoft.kc868.db.bean.Room;
import com.joeysoft.kc868.db.bean.Unused;
import com.joeysoft.kc868.db.bo.BOTempSensor;
import com.joeysoft.kc868.db.bo.BOFloor;
import com.joeysoft.kc868.db.bo.BORoom;
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
 * 湿温度传感器对话框
 * @author JOEY
 *
 */
public class TempSensorDialog extends Dialog implements IPacketListener{

	private Shell shell;
    private Display display;
	 // Shell
    private Shell dialog;
    
    // 所有控件
    private ImageButton btnOk, btnCancel;
    
    private Text textSensorName, textSensorAddr, textSensorUpper, textSensorLower, textHumidityUpper, textHumidityLower;
    private CCombo comboRoom, comboFloor;
    
    // IconHolder实例
    private IconHolder icons = IconHolder.getInstance();
    
    private MainShell main;
    
    private PacketProcessor packetProcessor;
    
    private BOTempSensor boTempSensor;
    private BOFloor boFloor;
    private BORoom boRoom;
    
    private TempSensor tempSensor, retTempSensor;
    
    private Unused next;
    private boolean isAdd = true;
    
    /**
     * 构造函数
     * 
     * @param shell
     * 		父窗口
     * @param ignoreAuto
     * 		true表示忽略掉自动登录选项，不要自动返回
     */
    public TempSensorDialog(MainShell main) {
        super(main.getShell(), SWT.NO_TRIM | SWT.NO_BACKGROUND);
        this.main = main;
        this.packetProcessor = main.getClient().getPacketProcessor();
        this.shell = main.getShell();
        display = shell.getDisplay();
        boTempSensor = new BOTempSensor();
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
        
        // 名称
        Label label = UITool.createLabel(center, name);
        label.setBounds(24, 27, 124, 20);
        label.setFont(Colors.GLOBAL_FONT);
        // 名称框
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
        
        // 地址码
        label = UITool.createLabel(center, addr_code);
        label.setBounds(24, 147, 124, 20);
        label.setFont(Colors.GLOBAL_FONT);
        // 地址码框
        textSensorAddr = UITool.createSingleText(center, SWT.SINGLE);
        textSensorAddr.setBackground(Colors.WHITE);
        textSensorAddr.setBounds(160, 147, 176, 20);
        
        // 最高温度
        label = UITool.createLabel(center, sensor_upper);
        label.setBounds(24, 187, 124, 20);
        label.setFont(Colors.GLOBAL_FONT);
        // 最高温度框
        textSensorUpper = UITool.createSingleText(center, SWT.SINGLE);
        textSensorUpper.setBackground(Colors.WHITE);
        textSensorUpper.setBounds(160, 187, 176, 20);
        
        label = UITool.createLabel(center, "");
    	label.setBounds(336, 187, 19, 20);
    	label.setImage(icons.getImage(IconHolder.icoC));
        
        // 最低温度
        label = UITool.createLabel(center, sensor_lower);
        label.setBounds(24, 227, 124, 20);
        label.setFont(Colors.GLOBAL_FONT);
        // 最低温度框
        textSensorLower = UITool.createSingleText(center, SWT.SINGLE);
        textSensorLower.setBackground(Colors.WHITE);
        textSensorLower.setBounds(160, 227, 176, 20);
        
        label = UITool.createLabel(center, "");
    	label.setBounds(336, 227, 19, 20);
    	label.setImage(icons.getImage(IconHolder.icoC));
        
        // 最高湿度
        label = UITool.createLabel(center, humidity_upper);
        label.setBounds(24, 267, 124, 20);
        label.setFont(Colors.GLOBAL_FONT);
        // 最高湿度框
        textHumidityUpper = UITool.createSingleText(center, SWT.SINGLE);
        textHumidityUpper.setBackground(Colors.WHITE);
        textHumidityUpper.setBounds(160, 267, 176, 20);
        
        label = UITool.createLabel(center, "%");
    	label.setBounds(336, 267, 19, 20);
    	label.setFont(Colors.NORMAL_FONT);
        
        // 最低湿度
        label = UITool.createLabel(center, humidity_lower);
        label.setBounds(24, 307, 124, 20);
        label.setFont(Colors.GLOBAL_FONT);
        // 最低湿度框
        textHumidityLower = UITool.createSingleText(center, SWT.SINGLE);
        textHumidityLower.setBackground(Colors.WHITE);
        textHumidityLower.setBounds(160, 307, 176, 20);
        
        label = UITool.createLabel(center, "%");
    	label.setBounds(336, 307, 19, 20);
    	label.setFont(Colors.NORMAL_FONT);
        // 按钮区
        btnOk =  UITool.createImageButton(center, button_ok, icons.getImage(IconHolder.bmpBtnLogin), 
        		icons.getImage(IconHolder.bmpBtnLoginUp), icons.getImage(IconHolder.bmpBtnLoginOn));
        btnOk.setFont(Colors.GLOBAL_FONT);
        btnOk.setBounds(54, 357, 114, 41);
        
        btnCancel = UITool.createImageButton(center, button_cancel, icons.getImage(IconHolder.bmpBtnLogin), 
        		icons.getImage(IconHolder.bmpBtnLoginUp), icons.getImage(IconHolder.bmpBtnLoginOn));
        btnCancel.setFont(Colors.GLOBAL_FONT);
        btnCancel.setBounds(195, 357, 114, 41);
    }
    
    /**
	 * 打开对话框
	 */
	public TempSensor open() {
		// create dialog
	    Shell parent = getParent();
	    Display display = parent.getDisplay();
		dialog = new Shell(parent, getStyle());
		
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
		dialog.setSize(378, 437);
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
			
		return retTempSensor;
	}
	
    /**
     * 初始化监听器
     */
    private void initListeners() {
    	dialog.addShellListener(new ShellAdapter() {
			public void shellClosed(ShellEvent e) {
				unhookListener();
				main.getShellRegistry().deregisterTempSensorDialog();
			}
		});
    	
    	// 取消按钮鼠标事件
        btnCancel.addMouseListener(new MouseAdapter() {
            public void mouseUp(MouseEvent e) {
            	retTempSensor = null;
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
    }
    
    /**
     * 初始化控件的值
     */
    private void initValue() {
    	if(!isAdd){ // 修改
        	textSensorName.setText(tempSensor.getSensorName());
        	comboFloor.setText(tempSensor.getFloorName());
        	Floor floor = (Floor)comboFloor.getData(comboFloor.getText());
            if(floor != null) ComboHelper.initRoomComboList(comboRoom, boRoom.getListByFloor(floor.getFloor()));
            comboRoom.setText(tempSensor.getRoomName());
        	textSensorAddr.setText(""+tempSensor.getSensorAddr());
        	
        	textSensorUpper.setText(""+tempSensor.getSensorUpper());
        	textSensorLower.setText(""+tempSensor.getSensorLower());
        	
        	textHumidityUpper.setText(""+tempSensor.getHumidityUpper());
        	textHumidityLower.setText(""+tempSensor.getHumidityLower());
        	
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
    	if(StringUtils.isEmpty(comboFloor.getText())){
    		MessageBoxHelper.openError(dialog, validation_floor_isEmpty);
    		return false;
    	}
    	if(StringUtils.isEmpty(comboRoom.getText())){
    		MessageBoxHelper.openError(dialog, validation_room_isEmpty);
    		return false;
    	}
    	if(StringUtils.isEmpty(textSensorUpper.getText())){
    		MessageBoxHelper.openError(dialog, validation_sensor_upper_isEmpty);
    		return false;
    	}
    	if(StringUtils.isEmpty(textSensorLower.getText())){
    		MessageBoxHelper.openError(dialog, validation_sensor_lower_isEmpty);
    		return false;
    	}
    	if(StringUtils.isEmpty(textHumidityUpper.getText())){
    		MessageBoxHelper.openError(dialog, validation_humidity_upper_isEmpty);
    		return false;
    	}
    	if(StringUtils.isEmpty(textHumidityLower.getText())){
    		MessageBoxHelper.openError(dialog, validation_humidity_lower_isEmpty);
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
    		
    		tempSensor =  new TempSensor();
    		Room room = (Room)comboRoom.getData(comboRoom.getText());
    		
    		List<TempSensor> list = boTempSensor.getListByRoomId(room.getRoomId());
    		if(list != null && list.size() > 0){
    			MessageBoxHelper.openError(dialog, error_device_exist);
    			return;
    		}
    		
    		tempSensor.setRoomId(room.getRoomId());
    		tempSensor.setSensorName(textSensorName.getText());
    		tempSensor.setSensorAddr(Integer.valueOf(textSensorAddr.getText()));
    		tempSensor.setSensorUpper(Integer.valueOf(textSensorUpper.getText()));
    		tempSensor.setSensorLower(Integer.valueOf(textSensorLower.getText()));
    		
    		tempSensor.setHumidityUpper(Integer.valueOf(textHumidityUpper.getText()));
    		tempSensor.setHumidityLower(Integer.valueOf(textHumidityLower.getText()));
    		
    		tempSensor.setRoomName(room.getRoomName());
    		tempSensor.setFloorName(room.getFloorName());
			
    		try {
    			next = boTempSensor.insertBefore(tempSensor);
				retTempSensor = tempSensor;
				sendSetSlaver(tempSensor.getSensorId(), tempSensor.getSensorAddr());
			} catch (DataExistException e) {
				btnOk.setEnabled(true);
    			MessageBoxHelper.openError(dialog, e.getMessage());
			} catch (Exception e) {
				e.printStackTrace();
				btnOk.setEnabled(true);
				MessageBoxHelper.openError(dialog, message_opreate_error);
			}
    	}else{
    		tempSensor.setSensorName(textSensorName.getText());
    		Room room = (Room)comboRoom.getData(comboRoom.getText());
    		tempSensor.setRoomId(room.getRoomId());
    		tempSensor.setSensorAddr(Integer.valueOf(textSensorAddr.getText()));
    		tempSensor.setSensorUpper(Integer.valueOf(textSensorUpper.getText()));
    		tempSensor.setSensorLower(Integer.valueOf(textSensorLower.getText()));
    		
    		tempSensor.setHumidityUpper(Integer.valueOf(textHumidityUpper.getText()));
    		tempSensor.setHumidityLower(Integer.valueOf(textHumidityLower.getText()));
    		
    		tempSensor.setRoomName(room.getRoomName());
    		tempSensor.setFloorName(room.getFloorName());
    		
    		try {
				sendSetTLimit(tempSensor.getSensorId(), tempSensor.getSensorUpper(), tempSensor.getSensorLower());
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
        case Protocol.CMD_SET_SLAVER:
        	processSetSlaverSuccess(in);
        	break;
        case Protocol.CMD_DEL_SLAVER:
        	processDelSlaverSuccess(in);
        	break;
        case Protocol.CMD_SET_T_LIMIT:
        	processSetTLimitSuccess(in);
        	break;
        case Protocol.CMD_SET_H_LIMIT:
        	processSetHLimitSuccess(in);
        	break;
        case Protocol.CMD_UNKNOWN:
        	processUnknown(in);
        	break;
        }
	}
    
    /**
     * 设置无线温湿度传感器参数
     * @param slaver
     * @param slaverAddr
     */
    private void sendSetSlaver(int slaver, int slaverAddr){
    	SetSlaverPacket packet = new SetSlaverPacket();
    	packet.setSlaver(slaver);
    	packet.setSlaverAddr(slaverAddr);
    	packetProcessor.send(packet);
    }
    
    /**
     * 设置无线温湿度传感器限值
     * @param slaver
     * @param slaverAddr
     */
    private void sendSetTLimit(int slaver, int upper, int lower){
    	SetTLimitPacket packet = new SetTLimitPacket();
    	packet.setSlaver(slaver);
    	packet.setTempUpper(upper);
    	packet.setTempLower(lower);
    	packetProcessor.send(packet);
    }
    
    /**
     * 设置无线温湿度传感器湿度限值 
     * @param slaver
     * @param slaverAddr
     */
    private void sendSetHLimit(int slaver, int upper, int lower){
    	SetHLimitPacket packet = new SetHLimitPacket();
    	packet.setSlaver(slaver);
    	packet.setTempUpper(upper);
    	packet.setTempLower(lower);
    	packetProcessor.send(packet);
    }
    
    /**
    * 处理设置无线温湿度参数
    * @param in
    */
	private void processSetSlaverSuccess(InPacket in){
		try {
			//boTempSensor.updateStudyOK(tempSensor.getSensorId());
			/*Thread.sleep(2000);*/
			sendSetTLimit(tempSensor.getSensorId(), tempSensor.getSensorUpper(), tempSensor.getSensorLower());
		} catch (Exception e) {
			e.printStackTrace();
			btnOk.setEnabled(true);
			MessageBoxHelper.openError(dialog, message_opreate_error);
		}
    }
	
	/**
    * 处理删除无线温湿度
    * @param in
    */
	private void processDelSlaverSuccess(InPacket in){
    	
    }
	
	/**
    * 处理设置无线温湿度-温度限值
    * @param in
    */
	private void processSetTLimitSuccess(final InPacket in){
		display.syncExec(new Runnable() {
			public void run() {
				if(Protocol.MSG_SET_T_LIMIT_OK_REPLY.equals(in.getMessage())){
					// 设置湿度
					sendSetHLimit(tempSensor.getSensorId(), tempSensor.getHumidityUpper(), tempSensor.getHumidityLower());
		    	}else if(Protocol.MSG_SET_T_LIMIT_OVERTOP_REPLY.equals(in.getMessage())){
		    		MessageBoxHelper.openWarning(dialog, error_temp_gt_upper);
		    	}else if(Protocol.MSG_SET_T_LIMIT_UNDER_REPLY.equals(in.getMessage())){
		    		MessageBoxHelper.openWarning(dialog, error_temp_lt_lower);
		    	}
			}
		});
    }
	
	/**
	    * 处理设置无线 湿度- 湿度限值
	    * @param in
	    */
		private void processSetHLimitSuccess(final InPacket in){
			display.syncExec(new Runnable() {
				public void run() {
					if(Protocol.MSG_SET_H_LIMIT_OK_REPLY.equals(in.getMessage())){
			    		
			    	}
			    	try {
			    		if(isAdd){
			    			boTempSensor.insert(tempSensor, next);
			    		}else{
			    			boTempSensor.update(tempSensor);
			    		}
			    		
						//boTempSensor.updateAlertOK(tempSensor.getSensorId());
						retTempSensor = tempSensor;
						close();
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
				MessageBoxHelper.openError(dialog, message);
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
    
	public void setTempSensor(TempSensor tempSensor) {
		if(tempSensor != null) isAdd = false;
		this.tempSensor = tempSensor;
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
