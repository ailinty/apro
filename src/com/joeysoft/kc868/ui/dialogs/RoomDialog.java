package com.joeysoft.kc868.ui.dialogs;

import static com.joeysoft.kc868.resource.Messages.*;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CCombo;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.ShellAdapter;
import org.eclipse.swt.events.ShellEvent;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.Text;

import com.joeysoft.kc868.SystemConfig;
import com.joeysoft.kc868.client.event.IPacketListener;
import com.joeysoft.kc868.client.event.PacketEvent;
import com.joeysoft.kc868.client.packets.InPacket;
import com.joeysoft.kc868.client.packets.in.infrared.InfraredSendReplyPacket;
import com.joeysoft.kc868.client.packets.in.infrared.InfraredStudyCloseReplyPacket;
import com.joeysoft.kc868.client.packets.in.infrared.InfraredStudyOverFlowReplyPacket;
import com.joeysoft.kc868.client.packets.in.infrared.InfraredStudyOverReplyPacket;
import com.joeysoft.kc868.client.packets.in.infrared.InfraredStudyReplyPacket;
import com.joeysoft.kc868.client.packets.in.infrared.InfraredStudyTestReplyPacket;
import com.joeysoft.kc868.client.packets.in.infrared.InfraredStudyWriteReplyPacket;
import com.joeysoft.kc868.client.packets.out.infrared.InfraredSendPacket;
import com.joeysoft.kc868.client.packets.out.infrared.InfraredStudyClosePacket;
import com.joeysoft.kc868.client.packets.out.infrared.InfraredStudyPacket;
import com.joeysoft.kc868.client.packets.out.infrared.InfraredStudyTestPacket;
import com.joeysoft.kc868.client.packets.out.infrared.InfraredWritePacket;
import com.joeysoft.kc868.client.support.PacketProcessor;
import com.joeysoft.kc868.client.support.Protocol;
import com.joeysoft.kc868.db.bean.Floor;
import com.joeysoft.kc868.db.bean.Room;
import com.joeysoft.kc868.db.bo.BOFloor;
import com.joeysoft.kc868.db.bo.BORoom;
import com.joeysoft.kc868.resource.IconHolder;
import com.joeysoft.kc868.ui.BorderStyler;
import com.joeysoft.kc868.ui.Colors;
import com.joeysoft.kc868.ui.MainShell;
import com.joeysoft.kc868.ui.dialogs.helper.ComboHelper;
import com.joeysoft.kc868.ui.helper.MessageBoxHelper;
import com.joeysoft.kc868.ui.helper.UITool;
import com.joeysoft.kc868.widgets.ImageButton;

/**
 * 房间对话框
 * @author JOEY
 *
 */
public class RoomDialog extends Dialog implements IPacketListener{

	private Shell shell;
    private Display display;
	 // Shell
    private Shell dialog;
    
    // 所有控件
    private ImageButton btnOk, btnCancel;
    
    private Text textRoomName;
    
    private CCombo comboFloor;
    
    // IconHolder实例
    private IconHolder icons = IconHolder.getInstance();
    
    private MainShell main;
    
    private BOFloor boFloor;
    
    private BORoom boRoom;
    
    private Room room, retRoom;
    private boolean isAdd = true;
    
    /**
     * 构造函数
     * 
     * @param shell
     * 		父窗口
     * @param ignoreAuto
     * 		true表示忽略掉自动登录选项，不要自动返回
     */
    public RoomDialog(MainShell main) {
        super(main.getShell(), SWT.NO_TRIM | SWT.NO_BACKGROUND);
        this.main = main;
        this.shell = main.getShell();
        display = shell.getDisplay();
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
        
        // 房间名称
        Label label = UITool.createLabel(center, room_name);
        label.setBounds(24, 37, 74, 20);
        label.setFont(Colors.GLOBAL_FONT);
        // 房间名称框
        textRoomName = UITool.createSingleText(center, SWT.SINGLE);
        textRoomName.setBackground(Colors.WHITE);
        textRoomName.setBounds(120, 37, 176, 20);
        
        // 楼层名称
        label = UITool.createLabel(center, floor_name);
        label.setBounds(24, 77, 74, 20);
        label.setFont(Colors.GLOBAL_FONT);
        // 楼层名称框
        comboFloor = UITool.createCCombo(center, SWT.SIMPLE | SWT.READ_ONLY);
        comboFloor.setBounds(120, 77, 176, 20);
        comboFloor.setBackground(Colors.WHITE);
        ComboHelper.initFloorComboList(comboFloor, boFloor.getList());
        
        btnOk =  UITool.createImageButton(center, button_ok, icons.getImage(IconHolder.bmpBtnLogin), 
        		icons.getImage(IconHolder.bmpBtnLoginUp), icons.getImage(IconHolder.bmpBtnLoginOn));
        btnOk.setFont(Colors.GLOBAL_FONT);
        btnOk.setBounds(34, 145, 114, 41);
        
        btnCancel = UITool.createImageButton(center, button_cancel, icons.getImage(IconHolder.bmpBtnLogin), 
        		icons.getImage(IconHolder.bmpBtnLoginUp), icons.getImage(IconHolder.bmpBtnLoginOn));
        btnCancel.setFont(Colors.GLOBAL_FONT);
        btnCancel.setBounds(175, 145, 114, 41);
    }
    
    /**
	 * 打开对话框
	 */
	public Room open() {
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
		dialog.setSize(338, 230);
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
			
		return retRoom;
	}
	
    /**
     * 初始化监听器
     */
    private void initListeners() {
    	dialog.addShellListener(new ShellAdapter() {
			public void shellClosed(ShellEvent e) {
				main.getShellRegistry().deregisterRoomDialog();
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
    	
    }
    
    /**
     * 初始化控件的值
     */
    private void initValue() {
    	if(!isAdd){ //修改
    		comboFloor.setText(room.getFloorName());
        	textRoomName.setText(room.getRoomName());
    	}
    }
    
    /**
     * 检验
     */
    private boolean validation(){
    	if(StringUtils.isEmpty(textRoomName.getText())){
    		MessageBoxHelper.openError(dialog, validation_room_name_isEmpty);
    		return false;
    	}
    	if(StringUtils.isEmpty(comboFloor.getText())){
    		MessageBoxHelper.openError(dialog, validation_floor_isEmpty);
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
    		room = new Room();
    		room.setRoomId(boRoom.getMax() + 1);
    		Floor floor = (Floor)comboFloor.getData(comboFloor.getText());
			room.setFloor(floor.getFloor());
			room.setFloorName(floor.getFloorName());
			room.setRoomName(textRoomName.getText());
    		
    		if(boRoom.insert(room)){
    			retRoom = room;
        		dialog.close();
        	}else{
        		btnOk.setEnabled(true);
        		MessageBoxHelper.openError(dialog, message_opreate_error);
        	}
    	}else{ // 修改
    		Floor floor = (Floor)comboFloor.getData(comboFloor.getText());
			room.setFloor(floor.getFloor());
			room.setFloorName(floor.getFloorName());
			room.setRoomName(textRoomName.getText());
			
    		if(boRoom.update(room)){
    			retRoom = room;
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
        }
	}

    /**
	 * 关闭dialog
	 */
	public void close() {
		dialog.close();
	}
	
	public void setRoom(Room room) {
		if(room != null) isAdd = false;
		this.room = room;
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
