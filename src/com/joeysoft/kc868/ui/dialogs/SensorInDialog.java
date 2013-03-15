package com.joeysoft.kc868.ui.dialogs;

import static com.joeysoft.kc868.resource.Messages.*;

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
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.ProgressBar;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import com.joeysoft.kc868.SystemConfig;
import com.joeysoft.kc868.client.event.IPacketListener;
import com.joeysoft.kc868.client.event.PacketEvent;
import com.joeysoft.kc868.client.packets.ErrorPacket;
import com.joeysoft.kc868.client.packets.InPacket;
import com.joeysoft.kc868.client.packets.OutPacket;
import com.joeysoft.kc868.client.packets.in.SensorStudyOKReplyPacket;
import com.joeysoft.kc868.client.packets.in.rf2262315.RF2262315StudyOKReplyPacket;
import com.joeysoft.kc868.client.packets.util.SensorPacketUtil;
import com.joeysoft.kc868.client.support.PacketProcessor;
import com.joeysoft.kc868.client.support.Protocol;
import com.joeysoft.kc868.db.bean.SensorIn;
import com.joeysoft.kc868.db.bean.Floor;
import com.joeysoft.kc868.db.bean.Room;
import com.joeysoft.kc868.db.bean.Unused;
import com.joeysoft.kc868.db.bo.BOFloor;
import com.joeysoft.kc868.db.bo.BORoom;
import com.joeysoft.kc868.db.bo.BOSensorIn;
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
 * 无线输入对话框
 * @author JOEY
 *
 */
public class SensorInDialog extends Dialog implements IPacketListener{

	private Shell shell;
    private Display display;
	 // Shell
    private Shell dialog;
    
    private Composite center;
    
    // 所有控件
    private ImageButton btnOk, btnCancel;
    
    private Text textSensorName;
    private CCombo comboRoom, comboFloor, comboFreqType, comboCodeType;
    
    // IconHolder实例
    private IconHolder icons = IconHolder.getInstance();
    
    private MainShell main;
    
    private PacketProcessor packetProcessor;
    
    private ProgressBar progressBar;
    private boolean isStudying;
    
    private BOSensorIn boSensorIn;
    private BOFloor boFloor;
    private BORoom boRoom;
    
    private SensorIn sensorIn;
    
    // 返回
    private SensorIn retSensor;
    
    private boolean isAdd = true;
    
    private Unused nextUnused;
    
    /**
     * 构造函数
     * 
     * @param shell
     * 		父窗口
     * @param ignoreAuto
     * 		true表示忽略掉自动登录选项，不要自动返回
     */
    public SensorInDialog(MainShell main) {
        super(main.getShell(), SWT.NO_TRIM | SWT.NO_BACKGROUND | SWT.ON_TOP);
        this.main = main;
        this.packetProcessor = main.getClient().getPacketProcessor();
        this.shell = main.getShell();
        display = shell.getDisplay();
        boSensorIn = new BOSensorIn();
        boFloor = new BOFloor();
        boRoom = new BORoom();
    }

    private void initLayout() {
    	BorderStyler styler = new BorderStyler();
    	styler.setHideWhenMinimize(false);
    	styler.setResizable(false);
    	center = styler.decorateShell(dialog);
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
        
        // 按钮区
        btnOk =  UITool.createImageButton(center, button_ok, icons.getImage(IconHolder.bmpBtnLogin), 
        		icons.getImage(IconHolder.bmpBtnLoginUp), icons.getImage(IconHolder.bmpBtnLoginOn));
        btnOk.setFont(Colors.GLOBAL_FONT);
        btnOk.setBounds(54, 317, 114, 41);
        
        btnCancel = UITool.createImageButton(center, button_cancel, icons.getImage(IconHolder.bmpBtnLogin), 
        		icons.getImage(IconHolder.bmpBtnLoginUp), icons.getImage(IconHolder.bmpBtnLoginOn));
        btnCancel.setFont(Colors.GLOBAL_FONT);
        btnCancel.setBounds(195, 317, 114, 41);
    }
    
    /**
	 * 打开对话框
	 */
	public SensorIn open() {
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
		dialog.setSize(378, 396);
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
			
		return retSensor;
	}
	
    /**
     * 初始化监听器
     */
    private void initListeners() {
    	dialog.addShellListener(new ShellAdapter() {
			public void shellClosed(ShellEvent e) {
				unhookListener();
				main.getShellRegistry().deregisterSensorInDialog();
			}
		});
    	
    	// 取消按钮鼠标事件
        btnCancel.addMouseListener(new MouseAdapter() {
            public void mouseUp(MouseEvent e) {
            	doCancel();
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
        	textSensorName.setText(sensorIn.getSensorName());
        	comboFloor.setText(sensorIn.getFloorName());
        	Floor floor = (Floor)comboFloor.getData(comboFloor.getText());
            if(floor != null) ComboHelper.initRoomComboList(comboRoom, boRoom.getListByFloor(floor.getFloor()));
            comboRoom.setText(sensorIn.getRoomName());
        	comboFreqType.setText(DictManager.getInstance().getDictName(DictConst.FREQ_TYPE, sensorIn.getFreqType()));
        	comboCodeType.setText(DictManager.getInstance().getDictName(DictConst.CODE_TYPE, sensorIn.getCodeType()));
        	
        	comboFreqType.setEnabled(false);
        	comboCodeType.setEnabled(false);
    	}
        	
    }
    
    private void doCancel(){
    	if(isStudying){
    		sendSensorStudyClose();
    	}else{
    		dialog.close();
    	}
    }
    
    /**
     * 检验
     */
    private boolean validation(){
    	if(StringUtils.isEmpty(textSensorName.getText())){
    		MessageBoxHelper.openError(dialog, validation_sensor_name_isEmpty);
    		return false;
    	}
    	if(StringUtils.isEmpty(comboFloor.getText())){
    		MessageBoxHelper.openError(dialog, validation_room_isEmpty);
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
    		sensorIn =  new SensorIn();
    		Room room = (Room)comboRoom.getData(comboRoom.getText());
    		sensorIn.setRoomId(room.getRoomId());
    		sensorIn.setSensorName(textSensorName.getText());
    		sensorIn.setFreqType((String)comboFreqType.getData(comboFreqType.getText()));
    		sensorIn.setCodeType((String)comboCodeType.getData(comboCodeType.getText()));
    		
    		sensorIn.setRoomName(room.getRoomName());
    		sensorIn.setFloorName(room.getFloorName());
			
    		try {
    			nextUnused = boSensorIn.insertBefore(sensorIn);
    			
				sendSensorStudy();
    		} catch (DataExistException e) {
    			btnOk.setEnabled(true);
    			MessageBoxHelper.openError(dialog, e.getMessage());
			} catch (Exception e) {
				e.printStackTrace();
				btnOk.setEnabled(true);
				MessageBoxHelper.openError(dialog, message_opreate_error);
			}
    	}else{
    		sensorIn.setSensorName(textSensorName.getText());
    		Room room = (Room)comboRoom.getData(comboRoom.getText());
    		sensorIn.setRoomId(room.getRoomId());
    		sensorIn.setFreqType((String)comboFreqType.getData(comboFreqType.getText()));
    		sensorIn.setCodeType((String)comboCodeType.getData(comboCodeType.getText()));
    		
    		sensorIn.setRoomName(room.getRoomName());
    		sensorIn.setFloorName(room.getFloorName());
    		
    		try {
				boSensorIn.update(sensorIn);
				retSensor = sensorIn;
				dialog.close();
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
        case Protocol.CMD_PT2262_315M_STUDY:
        case Protocol.CMD_PT2262_433M_STUDY:
        case Protocol.CMD_EV1527_315M_STUDY:
        case Protocol.CMD_EV1527_433M_STUDY:
        	processSensorStudyStart(in);
        	break;
        case Protocol.CMD_PT2262_315M_STUDY_OK:
        case Protocol.CMD_PT2262_433M_STUDY_OK:
        case Protocol.CMD_EV1527_315M_STUDY_OK:
        case Protocol.CMD_EV1527_433M_STUDY_OK:
        	processSensorStudySuccess(in);
        	break;
        case Protocol.CMD_PT2262_315M_STUDY_CLOSE:
        case Protocol.CMD_PT2262_433M_STUDY_CLOSE:
        case Protocol.CMD_EV1527_315M_STUDY_CLOSE:
        case Protocol.CMD_EV1527_433M_STUDY_CLOSE:
        	processSensorStudyCloseSuccess(in);
        	break;
        case Protocol.CMD_RFSTUY_315M_STUDY:
        case Protocol.CMD_RFSTUY_433M_STUDY:
        	processSensorRFStuyStart(in);
        	break;
        case Protocol.CMD_RFSTUY_315M_STUDY_OK:
        case Protocol.CMD_RFSTUY_433M_STUDY_OK:
        	processSensorRFStuySuccess(in);
        	break;
        case Protocol.CMD_RFSTUY_315M_CLOSE:
        case Protocol.CMD_RFSTUY_433M_CLOSE:
        	processSensorRFStuyCloseSuccess(in);
        	break;	
        case Protocol.CMD_RFSTUY_315M_DELETE:
        case Protocol.CMD_RFSTUY_433M_DELETE:
        	//processSensorRFStuyDeleteSuccess(in);
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
    private void sendSensorStudy(){
    	OutPacket packet = null;
    	if(SystemConfig.getInstance().isHardSoftVer2030()){
    		packet = SensorPacketUtil.getSensorInStudyOutPacket(sensorIn.getSensorId(), sensorIn.getFreqType(), sensorIn.getCodeType());
    	}else{
    		packet = SensorPacketUtil.getSensorInRFStudyOutPacket(sensorIn.getSensorId(), sensorIn.getFreqType(), sensorIn.getCodeType());
    	}
    	
    	packetProcessor.send(packet);
    }
    
    private void sendSensorStudyClose(){
    	OutPacket packet = null;
    	if(SystemConfig.getInstance().isHardSoftVer2030()){
    		packet = SensorPacketUtil.getSensorInStudyCloseOutPacket(sensorIn.getFreqType(), sensorIn.getCodeType());
    	}else{
    		packet = SensorPacketUtil.getSensorInRFStudyCloseOutPacket(sensorIn.getFreqType(), sensorIn.getCodeType());
    	}
    	
    	packetProcessor.send(packet);
    }
    
    /**
     * 处理学习开始
     * @param in
     */
    private void processSensorStudyStart(InPacket in){
    	display.syncExec(new Runnable() {
			public void run() {
				isStudying = true;
				btnOk.setEnabled(false);
				createProgressBar();
			}
    	});
    }
    
    /**
     * 处理学习开始
     * @param in
     */
    private void processSensorRFStuyStart(InPacket in){
    	display.syncExec(new Runnable() {
			public void run() {
				isStudying = true;
				btnOk.setEnabled(false);
				createProgressBar();
			}
    	});
    }
    
    /**
     * 处理学习成功
     * @param in
     */
    private void processSensorRFStuySuccess(InPacket in){
    	
    	try {
    		isStudying = false;
    		RF2262315StudyOKReplyPacket reply = (RF2262315StudyOKReplyPacket)in;
        	/*sensorIn.setResType(reply.getResType());
        	sensorIn.setAddrCode(reply.getAddrCode());
        	sensorIn.setDataCode(reply.getDataCode());*/
        	boSensorIn.insertRFSensor(sensorIn, nextUnused);
        	retSensor = sensorIn;

        	display.syncExec(new Runnable() {
    			public void run() {
    				btnOk.setEnabled(true);
    				close();
    			}
        	});
		} catch (Exception e) {
			MessageBoxHelper.openError(dialog, message_opreate_error);
			e.printStackTrace();
		}
    }
    
    /**
     * 处理学习取消
     * @param in
     */
    private void processSensorRFStuyCloseSuccess(InPacket in){
    	display.syncExec(new Runnable() {
			public void run() {
				btnOk.setEnabled(true);
				close();
			}
    	});
    }
    
    private void createProgressBar(){
		final ProgressBar bar = new ProgressBar(center, SWT.HORIZONTAL);
		bar.setBounds(80, 247, 200, 20);
		bar.setMaximum(100);
		new Thread(new Runnable(){
		    @Override
		    public void run() {
		    	while(isStudying){
		    		display.syncExec(new Runnable() {
		    			public void run() {
		    				if(bar != null && !bar.isDisposed()){
		    					bar.setSelection(bar.getSelection()>=bar.getMaximum()?0:bar.getSelection()+10);
		    				}
		    			}
			    	});
			    	
		    		try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
		    	}
		    }
		}).start();
    }
    
    /**
     * 处理学习成功
     * @param in
     */
    private void processSensorStudySuccess(InPacket in){
    	
    	try {
    		isStudying = false;
    		SensorStudyOKReplyPacket reply = (SensorStudyOKReplyPacket)in;
        	sensorIn.setResType(reply.getResType());
        	sensorIn.setAddrCode(reply.getAddrCode());
        	sensorIn.setDataCode(reply.getDataCode());
        	boSensorIn.insert(sensorIn, nextUnused);
        	retSensor = sensorIn;

        	display.syncExec(new Runnable() {
    			public void run() {
    				btnOk.setEnabled(true);
    				close();
    			}
        	});
		} catch (Exception e) {
			MessageBoxHelper.openError(dialog, message_opreate_error);
			e.printStackTrace();
		}
    }
    
    /**
     * 处理学习取消
     * @param in
     */
    private void processSensorStudyCloseSuccess(InPacket in){
    	display.syncExec(new Runnable() {
			public void run() {
				btnOk.setEnabled(true);
				close();
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
	
	public void setSensorIn(SensorIn sensorIn) {
		if(sensorIn != null) isAdd = false;
		this.sensorIn = sensorIn;
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
