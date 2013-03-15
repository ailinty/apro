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
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import com.joeysoft.kc868.SystemConfig;
import com.joeysoft.kc868.client.event.IPacketListener;
import com.joeysoft.kc868.client.event.PacketEvent;
import com.joeysoft.kc868.client.packets.ErrorPacket;
import com.joeysoft.kc868.client.packets.InPacket;
import com.joeysoft.kc868.client.packets.out.GetVersionPacket;
import com.joeysoft.kc868.client.packets.out.lineate.IOSetADMaxPacket;
import com.joeysoft.kc868.client.packets.out.lineate.IOSetADMinPacket;
import com.joeysoft.kc868.client.packets.out.lineate.IOSetModeNumPacket;
import com.joeysoft.kc868.client.packets.out.lineate.IOSetRFNumPacket;
import com.joeysoft.kc868.client.support.PacketProcessor;
import com.joeysoft.kc868.client.support.Protocol;
import com.joeysoft.kc868.db.bean.Floor;
import com.joeysoft.kc868.db.bean.Lineate;
import com.joeysoft.kc868.db.bean.Room;
import com.joeysoft.kc868.db.bo.BOFloor;
import com.joeysoft.kc868.db.bo.BOLineate;
import com.joeysoft.kc868.db.bo.BORoom;
import com.joeysoft.kc868.db.util.DictConst;
import com.joeysoft.kc868.db.util.DictManager;
import com.joeysoft.kc868.resource.IconHolder;
import com.joeysoft.kc868.ui.BorderStyler;
import com.joeysoft.kc868.ui.Colors;
import com.joeysoft.kc868.ui.MainShell;
import com.joeysoft.kc868.ui.dialogs.helper.ComboHelper;
import com.joeysoft.kc868.ui.helper.MessageBoxHelper;
import com.joeysoft.kc868.ui.helper.UITool;
import com.joeysoft.kc868.widgets.ImageButton;

/**
 * 楼层对话框
 * @author JOEY
 *
 */
public class LineateDialog extends Dialog implements IPacketListener{

	private Shell shell;
    private Display display;
	 // Shell
    private Shell dialog;
    
    // 所有控件
    private ImageButton btnOk, btnCancel;
    
    private Text textLineateName, textLineateV;
    private CCombo comboLineateType, comboRoom, comboFloor;
    private Group gpLineateUD, gpLineateGL;
    private Button radioLineateU, radioLineateD, radioLineateG, radioLineateL;
    private Label lbLineateV;
    
    // IconHolder实例
    private IconHolder icons = IconHolder.getInstance();
    
    private MainShell main;
    
    private BOLineate boLineate;
    private BOFloor boFloor;
    private BORoom boRoom;
    
    private Lineate lineate;
    private Lineate retLineate;
    
    private PacketProcessor packetProcessor;
    
    /**
     * 构造函数
     * 
     * @param shell
     * 		父窗口
     * @param ignoreAuto
     * 		true表示忽略掉自动登录选项，不要自动返回
     */
    public LineateDialog(MainShell main) {
        super(main.getShell(), SWT.NO_TRIM | SWT.NO_BACKGROUND);
        this.main = main;
        this.packetProcessor = main.getClient().getPacketProcessor();
        this.shell = main.getShell();
        display = shell.getDisplay();
        boLineate = new BOLineate();
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
        
        // 有线电名称
        Label label = UITool.createLabel(center, lineate_name);
        label.setBounds(24, 27, 124, 20);
        label.setFont(Colors.GLOBAL_FONT);
        // 有线电名称框
        textLineateName = UITool.createSingleText(center, SWT.SINGLE);
        textLineateName.setBackground(Colors.WHITE);
        textLineateName.setBounds(160, 27, 176, 20);
        
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
        
        // 信号模式
        label = UITool.createLabel(center, current_status);
        label.setBounds(24, 147, 124, 20);
        label.setFont(Colors.GLOBAL_FONT);
        // 信号模式
        comboLineateType = UITool.createCCombo(center, SWT.SIMPLE | SWT.READ_ONLY);
        comboLineateType.setBounds(160, 147, 176, 20);
        comboLineateType.setBackground(Colors.WHITE);
        ComboHelper.initComboList(comboLineateType, 
        		DictManager.getInstance().getDictList(DictConst.LINEATE_TYPE));
        comboLineateType.select(0);
        comboLineateType.addSelectionListener(new SelectionAdapter(){
			@Override
			public void widgetSelected(SelectionEvent e) {
				String type = (String)comboLineateType.getData(comboLineateType.getText());
				changeLineateType(type);
			}
        });
        
        // 触发方式
        label = UITool.createLabel(center, trigger_mode);
        label.setBounds(24, 187, 124, 20);
        label.setFont(Colors.GLOBAL_FONT);
        
        // 开关量
        gpLineateUD = UITool.createGroup(center, "");
        gpLineateUD.setBounds(160, 177, 176, 40);
        
        radioLineateU= UITool.createRadio(gpLineateUD, lineate_u);
        radioLineateU.setBounds(20, 12, 70, 20);
        radioLineateD= UITool.createRadio(gpLineateUD, lineate_d);
        radioLineateD.setBounds(100, 12, 70, 20);
        
        // 模拟量
        gpLineateGL = UITool.createGroup(center, "");
        gpLineateGL.setBounds(160, 177, 176, 40);
        
        radioLineateG = UITool.createRadio(gpLineateGL, lineate_g);
        radioLineateG.setBounds(20, 12, 70, 20);
        radioLineateL = UITool.createRadio(gpLineateGL, lineate_l);
        radioLineateL.setBounds(100, 12, 70, 20);
        
        // 有线电度数
        lbLineateV = UITool.createLabel(center, lineate_v);
        lbLineateV.setBounds(24, 232, 124, 20);
        lbLineateV.setFont(Colors.GLOBAL_FONT);
        lbLineateV.setVisible(false);
        // 有线电度数框
        textLineateV = UITool.createSingleText(center, SWT.SINGLE);
        textLineateV.setBackground(Colors.WHITE);
        textLineateV.setBounds(160, 232, 176, 20);
        textLineateV.setVisible(false);
        
        btnOk =  UITool.createImageButton(center, button_ok, icons.getImage(IconHolder.bmpBtnLogin), 
        		icons.getImage(IconHolder.bmpBtnLoginUp), icons.getImage(IconHolder.bmpBtnLoginOn));
        btnOk.setFont(Colors.GLOBAL_FONT);
        btnOk.setBounds(54, 282, 114, 41);
        
        btnCancel = UITool.createImageButton(center, button_cancel, icons.getImage(IconHolder.bmpBtnLogin), 
        		icons.getImage(IconHolder.bmpBtnLoginUp), icons.getImage(IconHolder.bmpBtnLoginOn));
        btnCancel.setFont(Colors.GLOBAL_FONT);
        btnCancel.setBounds(195, 282, 114, 41);
    }
    
    /**
	 * 打开对话框
	 */
	public Lineate open() {
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
		dialog.setSize(378, 370);
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
			
		return retLineate;
	}
	
    /**
     * 初始化监听器
     */
    private void initListeners() {
    	dialog.addShellListener(new ShellAdapter() {
			public void shellClosed(ShellEvent e) {
				unhookListener();
				main.getShellRegistry().deregisterLineateDialog();
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
    }
    
    /**
     * 初始化控件的值
     */
    private void initValue() {
    	if(lineate != null){ // 修改
        	textLineateName.setText(lineate.getLineateName());
        	comboFloor.setText(lineate.getFloorName());
        	Floor floor = (Floor)comboFloor.getData(comboFloor.getText());
            if(floor != null) ComboHelper.initRoomComboList(comboRoom, boRoom.getListByFloor(floor.getFloor()));
        	comboRoom.setText(lineate.getRoomName());
        	
        	comboLineateType.setText(DictManager.getInstance().getDictName(DictConst.LINEATE_TYPE, lineate.getLineateType()));
        	
        	changeLineateType(lineate.getLineateType());
        	radioLineateU.setSelection(lineate.getLineateUD().equals(DictConst.LINEATE_UD_U) ? true:false);
        	radioLineateD.setSelection(lineate.getLineateUD().equals(DictConst.LINEATE_UD_D) ? true:false);
        	radioLineateG.setSelection(lineate.getLineateGL().equals(DictConst.LINEATE_GL_G) ? true:false);
        	radioLineateL.setSelection(lineate.getLineateGL().equals(DictConst.LINEATE_GL_L) ? true:false);
    		textLineateV.setText(lineate.getLineateV());
    	}
    }
    
    private void changeLineateType(String lineateType){
    	if(DictConst.LINEATE_TYPE_UD.equals(lineateType)){
			gpLineateUD.setVisible(true);
			radioLineateU.setVisible(true);
			radioLineateD.setVisible(true);
			
			gpLineateGL.setVisible(false);
			radioLineateG.setVisible(false);
			radioLineateL.setVisible(false);
			
			lbLineateV.setVisible(false);
			textLineateV.setVisible(false);
		}else{
			gpLineateUD.setVisible(false);
			radioLineateU.setVisible(false);
			radioLineateD.setVisible(false);
			
			gpLineateGL.setVisible(true);
			radioLineateG.setVisible(true);
			radioLineateL.setVisible(true);
			
			lbLineateV.setVisible(true);
			textLineateV.setVisible(true);
		}
    }
    
    /**
     * 检验
     */
    private boolean validation(){
    	if(StringUtils.isEmpty(textLineateName.getText())){
    		MessageBoxHelper.openError(dialog, validation_lineate_name_isEmpty);
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
    	if(StringUtils.isEmpty(comboLineateType.getText())){
    		MessageBoxHelper.openError(dialog, validation_lineate_type_isEmpty);
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
    	Room room = (Room)comboRoom.getData(comboRoom.getText());
    	lineate.setRoomId(room.getRoomId());
    	
    	lineate.setRoomName(room.getRoomName());
    	lineate.setFloorName(room.getFloorName());
		
		lineate.setLineateName(textLineateName.getText());
		lineate.setLineateType((String)comboLineateType.getData(comboLineateType.getText()));
		lineate.setLineateUD(radioLineateU.getSelection()?"1":"0");
		lineate.setLineateGL(radioLineateG.getSelection()?"1":"0");
		lineate.setLineateV(textLineateV.getText());

		sendSetModeNum(lineate);
    }
    
    /**
     * 处理成功后，更新
     */
    private void processSuccess(){
    	display.syncExec(new Runnable() {
			public void run() {
				if(boLineate.update(lineate)){
		    		retLineate = lineate;
					close();
		    	}else{
		    		btnOk.setEnabled(true);
		    		MessageBoxHelper.openError(dialog, message_opreate_error);
		    	}
			}
    	});
    }
    
    @Override
	public void packetArrived(PacketEvent e) {
    	InPacket in = (InPacket) e.getSource();
    	
    	 // 现在开始判断包的类型，作出相应的处理
        switch (in.getCommand()) {
	        case Protocol.CMD_IO_SET_MODE_NUM:
	        	processIOSetModeNumSuccess(in);
	        	break;
	        case Protocol.CMD_IO_SET_R_F_NUM:
	        	processIOSetRFNumSuccess(in);
	        	break;
	        case Protocol.CMD_IO_SET_AD:
	        	processIOSetADSuccess(in);
	        	break;
	        case Protocol.CMD_IO_IRQ_HIGH:
	        	break;
	        case Protocol.CMD_UNKNOWN:
	        	processUnknown(in);
	        	break;
        }
	}

    /**
     * 发送配置工作模式
     */
    private void sendSetModeNum(Lineate lineate){
    	IOSetModeNumPacket packet = new IOSetModeNumPacket();
    	packet.setModeNum(boLineate.getModeNum(lineate));
    	packetProcessor.send(packet);
    }
    
    /**
     * 发送开关量设置
     */
    private void sendSetRFNum(){
    	IOSetRFNumPacket packet = new IOSetRFNumPacket();
    	packet.setRFNum(boLineate.getRFNum(lineate));
    	
    	packetProcessor.send(packet);
    }
    
    /**
     * 发送模拟量最小限值设置
     */
    private void sendSetADMin(Lineate lineate){
    	IOSetADMinPacket packet = new IOSetADMinPacket();
    	
    	packet.setLoad(lineate.getLineateId());
    	packet.setMinNum(lineate.getLineateV());
    	
    	packetProcessor.send(packet);
    }
    
    /**
     * 发送模拟量最大限值设置
     */
    private void sendSetADMax(Lineate lineate){
    	IOSetADMaxPacket packet = new IOSetADMaxPacket();
    	
    	packet.setLoad(lineate.getLineateId());
    	packet.setMaxNum(lineate.getLineateV());
    	
    	packetProcessor.send(packet);
    }
    
    /**
     * 处理配置工作模式成功
     * @param in
     */
    private void processIOSetModeNumSuccess(InPacket in){
    	/*try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}*/
    	//配置工作模式成功后发送
    	if(DictConst.LINEATE_TYPE_UD.equals(lineate.getLineateType())){ // 开关量
    		sendSetRFNum();
    	}else{ // 模拟量
    		if(DictConst.LINEATE_GL_G.equals(lineate.getLineateGL())){ // 最大限值
    			sendSetADMax(lineate);
    		}else{ // 最小限值
    			sendSetADMin(lineate);
    		}
    	}
    }
    
    /**
     * 处理设置开关量成功
     * @param in
     */
    private void processIOSetRFNumSuccess(InPacket in){
    	processSuccess();
    }
    
    /**
     * 处理设置模拟量成功
     * @param in
     */
    private void processIOSetADSuccess(InPacket in){
    	processSuccess();
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
    
	public void setLineate(Lineate lineate) {
		this.lineate = lineate;
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
