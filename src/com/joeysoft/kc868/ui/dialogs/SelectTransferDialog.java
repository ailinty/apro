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
import com.joeysoft.kc868.db.util.DictConst;
import com.joeysoft.kc868.db.util.DictManager;
import com.joeysoft.kc868.exception.DataExistException;
import com.joeysoft.kc868.resource.IconHolder;
import com.joeysoft.kc868.resource.Messages;
import com.joeysoft.kc868.ui.BorderStyler;
import com.joeysoft.kc868.ui.Colors;
import com.joeysoft.kc868.ui.MainShell;
import com.joeysoft.kc868.ui.dialogs.helper.ComboHelper;
import com.joeysoft.kc868.ui.helper.MessageBoxHelper;
import com.joeysoft.kc868.ui.helper.UITool;
import com.joeysoft.kc868.widgets.ImageButton;

/**
 * 红外转发器选择对话框
 * @author JOEY
 *
 */
public class SelectTransferDialog extends Dialog{

	private Shell shell;
    private Display display;
	 // Shell
    private Shell dialog;
    
    // 所有控件
    private ImageButton btnOk, btnCancel;
    
    private Text textTransferName, textAddrCode;
    private CCombo comboTransfer;
    
    // IconHolder实例
    private IconHolder icons = IconHolder.getInstance();
    
    private MainShell main;
    
    private Transfer transfer;
    
    private List<Transfer> transferList;
    
    /**
     * 构造函数
     * 
     * @param shell
     * 		父窗口
     * @param ignoreAuto
     * 		true表示忽略掉自动登录选项，不要自动返回
     */
    public SelectTransferDialog(MainShell main) {
        super(main.getShell(), SWT.NO_TRIM | SWT.NO_BACKGROUND);
        this.main = main;
        this.shell = main.getShell();
        display = shell.getDisplay();
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
        Label label = UITool.createLabel(center, Messages.transfer);
        label.setBounds(24, 27, 124, 20);
        label.setFont(Colors.GLOBAL_FONT);
        
        // 红外转发器框
        comboTransfer = UITool.createCCombo(center, SWT.SIMPLE | SWT.READ_ONLY);
        comboTransfer.setBounds(160, 27, 176, 20);
        comboTransfer.setBackground(Colors.WHITE);
        comboTransfer.select(0);
        
        
        // 按钮区
        btnOk =  UITool.createImageButton(center, button_ok, icons.getImage(IconHolder.bmpBtnLogin), 
        		icons.getImage(IconHolder.bmpBtnLoginUp), icons.getImage(IconHolder.bmpBtnLoginOn));
        btnOk.setFont(Colors.GLOBAL_FONT);
        btnOk.setBounds(54, 110, 114, 41);
        
        btnCancel = UITool.createImageButton(center, button_cancel, icons.getImage(IconHolder.bmpBtnLogin), 
        		icons.getImage(IconHolder.bmpBtnLoginUp), icons.getImage(IconHolder.bmpBtnLoginOn));
        btnCancel.setFont(Colors.GLOBAL_FONT);
        btnCancel.setBounds(195, 110, 114, 41);
    }
    
    /**
	 * 打开对话框
	 */
	public Transfer open() {
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
		dialog.setSize(378, 196);
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
			
		return transfer;
	}
	
    /**
     * 初始化监听器
     */
    private void initListeners() {
    	dialog.addShellListener(new ShellAdapter() {
			public void shellClosed(ShellEvent e) {
				main.getShellRegistry().deregisterSelectTransferDialog();
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
    	if(transferList != null){
    		ComboHelper.initTransferComboList(comboTransfer, transferList);
    		comboTransfer.select(0);
    	}
    }
    
    /**
     * 检验
     */
    private boolean validation(){
    	if(StringUtils.isEmpty(comboTransfer.getText())){
    		MessageBoxHelper.openError(dialog, validation_transfer_isEmpty);
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
    	transfer = (Transfer)comboTransfer.getData(comboTransfer.getText());
    	close();
    }
    
    /**
	 * 关闭dialog
	 */
	public void close() {
		dialog.close();
	}
	
	public void setTransfer(Transfer transfer) {
		this.transfer = transfer;
	}
	
	public void setTransferList(List<Transfer> transferList) {
		this.transferList = transferList;
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
