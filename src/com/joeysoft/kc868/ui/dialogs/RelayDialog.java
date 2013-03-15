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
import com.joeysoft.kc868.client.packets.InPacket;
import com.joeysoft.kc868.db.bean.Floor;
import com.joeysoft.kc868.db.bean.Relay;
import com.joeysoft.kc868.db.bean.Relay;
import com.joeysoft.kc868.db.bean.Room;
import com.joeysoft.kc868.db.bo.BOFloor;
import com.joeysoft.kc868.db.bo.BORelay;
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
 * 继电器对话框
 * @author JOEY
 *
 */
public class RelayDialog extends Dialog implements IPacketListener{

	private Shell shell;
    private Display display;
	 // Shell
    private Shell dialog;
    
    // 所有控件
    private ImageButton btnOk, btnCancel;
    
    private Text textRelayName, textRelayOnName, textRelayOffName;
    private CCombo comboRelayStatus, comboFloor, comboRoom;
    
    // IconHolder实例
    private IconHolder icons = IconHolder.getInstance();
    
    private MainShell main;
    
    private BORelay boRelay;
    private BOFloor boFloor;
    private BORoom boRoom;
    
    private Relay relay, retRelay;
    private boolean isAdd = true;
    
    /**
     * 构造函数
     * 
     * @param shell
     * 		父窗口
     */
    public RelayDialog(MainShell main) {
        super(main.getShell(), SWT.NO_TRIM | SWT.NO_BACKGROUND);
        this.main = main;
        this.shell = main.getShell();
        display = shell.getDisplay();
        boRelay = new BORelay();
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
        
        // 继电器名称
        Label label = UITool.createLabel(center, relay_name);
        label.setBounds(24, 27, 124, 20);
        label.setFont(Colors.GLOBAL_FONT);
        // 继电器名称框
        textRelayName = UITool.createSingleText(center, SWT.SINGLE);
        textRelayName.setBackground(Colors.WHITE);
        textRelayName.setBounds(160, 27, 176, 20);
        
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
        
        // 打开继电器名称
        label = UITool.createLabel(center, relay_on_name);
        label.setBounds(24, 147, 124, 20);
        label.setFont(Colors.GLOBAL_FONT);
        // 打开继电器名称框
        textRelayOnName = UITool.createSingleText(center, SWT.SINGLE);
        textRelayOnName.setBackground(Colors.WHITE);
        textRelayOnName.setBounds(160, 147, 176, 20);
        
        // 关闭继电器名称
        label = UITool.createLabel(center,relay_off_name);
        label.setBounds(24, 187, 124, 20);
        label.setFont(Colors.GLOBAL_FONT);
        // 关闭继电器名称框
        textRelayOffName = UITool.createSingleText(center, SWT.SINGLE);
        textRelayOffName.setBackground(Colors.WHITE);
        textRelayOffName.setBounds(160, 187, 176, 20);
        
        // 当前状态
        label = UITool.createLabel(center, current_status);
        label.setBounds(24, 227, 124, 20);
        label.setFont(Colors.GLOBAL_FONT);
        // 继电器状态
        comboRelayStatus = UITool.createCCombo(center, SWT.SIMPLE | SWT.READ_ONLY);
        comboRelayStatus.setBounds(160, 227, 176, 20);
        comboRelayStatus.setBackground(Colors.WHITE);
        ComboHelper.initComboList(comboRelayStatus, 
        		DictManager.getInstance().getDictList(DictConst.RELAY_STATUS));
        comboRelayStatus.select(0);
        
        btnOk =  UITool.createImageButton(center, button_ok, icons.getImage(IconHolder.bmpBtnLogin), 
        		icons.getImage(IconHolder.bmpBtnLoginUp), icons.getImage(IconHolder.bmpBtnLoginOn));
        btnOk.setFont(Colors.GLOBAL_FONT);
        btnOk.setBounds(54, 297, 114, 41);
        
        btnCancel = UITool.createImageButton(center, button_cancel, icons.getImage(IconHolder.bmpBtnLogin), 
        		icons.getImage(IconHolder.bmpBtnLoginUp), icons.getImage(IconHolder.bmpBtnLoginOn));
        btnCancel.setFont(Colors.GLOBAL_FONT);
        btnCancel.setBounds(195, 297, 114, 41);
    }
    
    /**
	 * 打开对话框
	 */
	public Relay open() {
		// create dialog
	    Shell parent = getParent();
	    Display display = parent.getDisplay();
		dialog = new Shell(parent, getStyle());
		
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
		dialog.setSize(378, 377);
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
			
		return retRelay;
	}
	
    /**
     * 初始化监听器
     */
    private void initListeners() {
    	dialog.addShellListener(new ShellAdapter() {
			public void shellClosed(ShellEvent e) {
				main.getShellRegistry().deregisterRelayDialog();
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
    	if(!isAdd){ // 修改
        	textRelayName.setText(relay.getRelayName());
        	textRelayOnName.setText(relay.getRelayOnName());
        	textRelayOffName.setText(relay.getRelayOffName());
        	comboRelayStatus.setText(DictManager.getInstance().getDictName(DictConst.RELAY_STATUS, relay.getRelayStatus()));
        	
        	comboFloor.setText(relay.getFloorName());
        	Floor floor = (Floor)comboFloor.getData(comboFloor.getText());
            if(floor != null) ComboHelper.initRoomComboList(comboRoom, boRoom.getListByFloor(floor.getFloor()));
            comboRoom.setText(relay.getRoomName());
    	}
    }
    
    /**
     * 检验
     */
    private boolean validation(){
    	if(StringUtils.isEmpty(textRelayName.getText())){
    		MessageBoxHelper.openError(dialog, validation_relay_name_isEmpty);
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
    	if(StringUtils.isEmpty(textRelayOnName.getText())){
    		MessageBoxHelper.openError(dialog, validation_relay_on_name_isEmpty);
    		return false;
    	}
    	if(StringUtils.isEmpty(textRelayOffName.getText())){
    		MessageBoxHelper.openError(dialog, validation_relay_off_name_isEmpty);
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
    		relay =  new Relay();
    		//relay.setRelay(boRelay.getMax() + 1);
    		relay.setRelayName(textRelayName.getText());
			
    		if(boRelay.insert(relay)){
    			retRelay = relay;
        		dialog.close();
        	}else{
        		btnOk.setEnabled(true);
        		MessageBoxHelper.openError(dialog, message_opreate_error);
        	}
    	}else{
    		Room room = (Room)comboRoom.getData(comboRoom.getText());
    		relay.setRoomId(room.getRoomId());
    		relay.setRelayName(textRelayName.getText());
    		relay.setRoomName(room.getRoomName());
    		relay.setFloorName(room.getFloorName());
    		relay.setRelayOnName(textRelayOnName.getText());
    		relay.setRelayOffName(textRelayOffName.getText());
    		relay.setRelayStatus((String)comboRelayStatus.getData(comboRelayStatus.getText()));
			
    		try {
				boRelay.update(relay);
				retRelay = relay;
				dialog.close();
			} catch (Exception e1) {
				e1.printStackTrace();
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
        }
	}

    /**
	 * 关闭dialog
	 */
	public void close() {
		dialog.close();
	}
	
	public void setRelay(Relay relay) {
		if(relay != null) isAdd = false;
		this.relay = relay;
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
