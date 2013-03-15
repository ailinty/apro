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
import com.joeysoft.kc868.db.bean.Transfer;
import com.joeysoft.kc868.db.bean.Floor;
import com.joeysoft.kc868.db.bean.Room;
import com.joeysoft.kc868.db.bean.TsfSensor;
import com.joeysoft.kc868.db.bo.BOTransfer;
import com.joeysoft.kc868.db.bo.BOFloor;
import com.joeysoft.kc868.db.bo.BORoom;
import com.joeysoft.kc868.db.bo.BOTransferSensor;
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
 * 红外转发器对话框
 * @author JOEY
 *
 */
public class TransferDialog extends Dialog implements IPacketListener{

	private Shell shell;
    private Display display;
	 // Shell
    private Shell dialog;
    
    // 所有控件
    private ImageButton btnOk, btnCancel;
    
    private Text textTransferName;
    private CCombo comboRoom, comboFloor, comboFreqType, comboCodeType, comboResType;
    
    // IconHolder实例
    private IconHolder icons = IconHolder.getInstance();
    
    private MainShell main;
    
    private PacketProcessor packetProcessor;
    
    private BOTransfer boTransfer;
    private BOTransferSensor boTransferSensor;
    private BOFloor boFloor;
    private BORoom boRoom;
    
    private Transfer transfer, retTransfer;
    private boolean isAdd = true;
    private boolean[] bSwitch = new boolean[]{true,true,true,true,true,true,true,true};
    
    /**
     * 构造函数
     * 
     * @param shell
     * 		父窗口
     * @param ignoreAuto
     * 		true表示忽略掉自动登录选项，不要自动返回
     */
    public TransferDialog(MainShell main) {
        super(main.getShell(), SWT.NO_TRIM | SWT.NO_BACKGROUND);
        this.main = main;
        this.packetProcessor = main.getClient().getPacketProcessor();
        this.shell = main.getShell();
        display = shell.getDisplay();
        boTransfer = new BOTransfer();
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
        textTransferName = UITool.createSingleText(center, SWT.SINGLE);
        textTransferName.setBackground(Colors.WHITE);
        textTransferName.setBounds(160, 27, 176, 20);
        
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
        
        // 地址码
        label = UITool.createLabel(center, addr_code);
        label.setBounds(24, 267, 70, 20);
        label.setFont(Colors.GLOBAL_FONT);
        
        label = UITool.createLabel(center, "H");
        label.setBounds(100, 267, 10, 20);
        label.setFont(Colors.GLOBAL_FONT);
        
        label = UITool.createLabel(center, "L");
        label.setBounds(100, 287, 10, 20);
        label.setFont(Colors.GLOBAL_FONT);
        
        for(int i=0; i<bSwitch.length; i++){
        	final int index = i;
        	final Label lbSwitch = UITool.createLabel(center, "");
        	lbSwitch.setBounds(i*30+120, 267, 23, 36);
        	lbSwitch.setImage(bSwitch[index]?icons.getImage(IconHolder.switchOn):icons.getImage(IconHolder.switchOff));
        	if(isAdd){
        		lbSwitch.setCursor(center.getDisplay().getSystemCursor(SWT.CURSOR_HAND));
            	lbSwitch.addMouseListener(new MouseAdapter() {
                    public void mouseUp(MouseEvent e) {
                    	bSwitch[index] = !bSwitch[index];
                    	lbSwitch.setImage(bSwitch[index]?icons.getImage(IconHolder.switchOn):icons.getImage(IconHolder.switchOff));
                    }
                });
        	}
        	
        	label = UITool.createLabel(center, ""+(bSwitch.length - i));
            label.setBounds(i*30+127, 303, 10, 20);
            label.setFont(Colors.GLOBAL_FONT);
        }
        
        // 按钮区
        btnOk =  UITool.createImageButton(center, button_ok, icons.getImage(IconHolder.bmpBtnLogin), 
        		icons.getImage(IconHolder.bmpBtnLoginUp), icons.getImage(IconHolder.bmpBtnLoginOn));
        btnOk.setFont(Colors.GLOBAL_FONT);
        btnOk.setBounds(54, 337, 114, 41);
        
        btnCancel = UITool.createImageButton(center, button_cancel, icons.getImage(IconHolder.bmpBtnLogin), 
        		icons.getImage(IconHolder.bmpBtnLoginUp), icons.getImage(IconHolder.bmpBtnLoginOn));
        btnCancel.setFont(Colors.GLOBAL_FONT);
        btnCancel.setBounds(195, 337, 114, 41);
    }
    
    /**
	 * 打开对话框
	 */
	public Transfer open() {
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
		dialog.setSize(378, 416);
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
			
		return retTransfer;
	}
	
    /**
     * 初始化监听器
     */
    private void initListeners() {
    	dialog.addShellListener(new ShellAdapter() {
			public void shellClosed(ShellEvent e) {
				unhookListener();
				main.getShellRegistry().deregisterTransferDialog();
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
    		bSwitch = DataAddrCodeUtil.getSwitchs8(transfer.getAddrCode());
    	}
    }
    
    /**
     * 初始化控件的值
     */
    private void initValue() {
    	if(!isAdd){ // 修改
        	textTransferName.setText(transfer.getTransferName());
        	comboFloor.setText(transfer.getFloorName());
        	Floor floor = (Floor)comboFloor.getData(comboFloor.getText());
            if(floor != null) ComboHelper.initRoomComboList(comboRoom, boRoom.getListByFloor(floor.getFloor()));
            comboRoom.setText(transfer.getRoomName());
        	/*comboFreqType.setText(DictManager.getInstance().getDictName(DictConst.FREQ_TYPE, transfer.getFreqType()));
        	comboCodeType.setText(DictManager.getInstance().getDictName(DictConst.CODE_TYPE, transfer.getCodeType()));
        	comboResType.setText(DictManager.getInstance().getDictName(DictConst.RES_TYPE, transfer.getResType()));*/
        	comboFreqType.setEnabled(false);
        	comboCodeType.setEnabled(false);
        	comboResType.setEnabled(false);
    	}
        	
    }
    
    /**
     * 检验
     */
    private boolean validation(){
    	if(StringUtils.isEmpty(textTransferName.getText())){
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
    		transfer =  new Transfer();
    		Room room = (Room)comboRoom.getData(comboRoom.getText());
    		transfer.setRoomId(room.getRoomId());
    		transfer.setTransferName(textTransferName.getText());
    		transfer.setFreqType((String)comboFreqType.getData(comboFreqType.getText()));
    		transfer.setCodeType((String)comboCodeType.getData(comboCodeType.getText()));
    		transfer.setResType((String)comboResType.getData(comboResType.getText()));
    		transfer.setAddrCode(DataAddrCodeUtil.getAddrCode8(bSwitch));
    		
    		transfer.setRoomName(room.getRoomName());
    		transfer.setFloorName(room.getFloorName());
			
    		try {
    			TsfSensor tsfSensor = boTransfer.insertBefore(transfer);
    			sendSensorWrite(tsfSensor);
    		} catch (DataExistException e) {
    			btnOk.setEnabled(true);
    			MessageBoxHelper.openError(dialog, e.getMessage());
			} catch (Exception e) {
				e.printStackTrace();
				btnOk.setEnabled(true);
				MessageBoxHelper.openError(dialog, message_opreate_error);
			}
    	}else{
    		Room room = (Room)comboRoom.getData(comboRoom.getText());
    		transfer.setRoomId(room.getRoomId());
    		transfer.setTransferName(textTransferName.getText());
    		/*transfer.setFreqType((String)comboFreqType.getData(comboFreqType.getText()));
    		transfer.setCodeType((String)comboCodeType.getData(comboCodeType.getText()));
    		transfer.setResType((String)comboResType.getData(comboResType.getText()));
    		transfer.setAddrCode(Integer.valueOf(textAddrCode.getText()));*/
    		
    		transfer.setRoomName(room.getRoomName());
    		transfer.setFloorName(room.getFloorName());
    		
    		try {
				boTransfer.update(transfer);
				retTransfer =  transfer;
				btnOk.setEnabled(true);
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
     * 写入00000011
     * @param tsfSensor
     */
    private void sendSensorWrite(TsfSensor tsfSensor){
    	OutPacket packet = SensorPacketUtil.getSensorWriteOutPacket(Integer.valueOf(tsfSensor.getSensorId().substring(2)), 
    			tsfSensor.getFreqType(), tsfSensor.getCodeType(), tsfSensor.getResType(), tsfSensor.getAddrCode(), tsfSensor.getDataCode());
    	packetProcessor.send(packet);
    }
    
    /**
     * 处理Write成功
     * @param in
     */
    private void processSensorWriteSuccess(InPacket in){
    	display.syncExec(new Runnable() {
			public void run() {
				try {
					boTransfer.insert(transfer);
					retTransfer = transfer;
					btnOk.setEnabled(true);
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
    
	public void setTransfer(Transfer transfer) {
		if(transfer != null) isAdd = false;
		this.transfer = transfer;
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
