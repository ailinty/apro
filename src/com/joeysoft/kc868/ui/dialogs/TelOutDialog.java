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
import com.joeysoft.kc868.client.packets.out.TelephoneWriteCmpPacket;
import com.joeysoft.kc868.client.packets.out.TelephoneWriteUserPacket;
import com.joeysoft.kc868.client.support.PacketProcessor;
import com.joeysoft.kc868.client.support.Protocol;
import com.joeysoft.kc868.db.bean.TelIn;
import com.joeysoft.kc868.db.bean.TelOut;
import com.joeysoft.kc868.db.bo.BOTelOut;
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
 * 电话对话框
 * @author JOEY
 *
 */
public class TelOutDialog extends Dialog implements IPacketListener{

	private Shell shell;
    private Display display;
	 // Shell
    private Shell dialog;
    
    // 所有控件
    private ImageButton btnOk, btnCancel;
    
    private Text textTelName, textTelPhone, textCountryCode;
    
    // IconHolder实例
    private IconHolder icons = IconHolder.getInstance();
    
    private MainShell main;
    private PacketProcessor packetProcessor;
    
    private BOTelOut boTelOut;
    
    private TelOut telOut, retTelOut;
    private boolean isAdd = true;
    
    /**
     * 构造函数
     * 
     * @param shell
     * 		父窗口
     * @param ignoreAuto
     * 		true表示忽略掉自动登录选项，不要自动返回
     */
    public TelOutDialog(MainShell main) {
        super(main.getShell(), SWT.NO_TRIM | SWT.NO_BACKGROUND);
        this.main = main;
        this.packetProcessor = main.getClient().getPacketProcessor();
        this.shell = main.getShell();
        display = shell.getDisplay();
        boTelOut = new BOTelOut();
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
        label.setBounds(24, 27, 100, 20);
        label.setFont(Colors.GLOBAL_FONT);
        // 名称框
        textTelName = UITool.createSingleText(center, SWT.SINGLE);
        textTelName.setBackground(Colors.WHITE);
        textTelName.setBounds(130, 27, 206, 20);
        
        // 国家代码
        label = UITool.createLabel(center, country_code);
        label.setBounds(24, 67, 100, 20);
        label.setFont(Colors.GLOBAL_FONT);
        // 国家代码
        textCountryCode = UITool.createSingleText(center, SWT.SINGLE);
        textCountryCode.setBackground(Colors.WHITE);
        textCountryCode.setBounds(130, 67, 206, 20);
        
        // 电话
        label = UITool.createLabel(center, telphone);
        label.setBounds(24, 107, 100, 20);
        label.setFont(Colors.GLOBAL_FONT);
        // 电话框
        textTelPhone = UITool.createSingleText(center, SWT.SINGLE);
        textTelPhone.setBackground(Colors.WHITE);
        textTelPhone.setBounds(130, 107, 206, 20);
        
        
        btnOk =  UITool.createImageButton(center, button_ok, icons.getImage(IconHolder.bmpBtnLogin), 
        		icons.getImage(IconHolder.bmpBtnLoginUp), icons.getImage(IconHolder.bmpBtnLoginOn));
        btnOk.setFont(Colors.GLOBAL_FONT);
        btnOk.setBounds(54, 177, 114, 41);
        
        btnCancel = UITool.createImageButton(center, button_cancel, icons.getImage(IconHolder.bmpBtnLogin), 
        		icons.getImage(IconHolder.bmpBtnLoginUp), icons.getImage(IconHolder.bmpBtnLoginOn));
        btnCancel.setFont(Colors.GLOBAL_FONT);
        btnCancel.setBounds(195, 177, 114, 41);
    }
    
    /**
	 * 打开对话框
	 */
	public TelOut open() {
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
		dialog.setSize(378, 257);
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
			
		return retTelOut;
	}
	
    /**
     * 初始化监听器
     */
    private void initListeners() {
    	dialog.addShellListener(new ShellAdapter() {
			public void shellClosed(ShellEvent e) {
				unhookListener();
				main.getShellRegistry().deregisterTelOutDialog();
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
    	if(!isAdd){ // 修改
        	textTelName.setText(telOut.getTelName());
        	textTelPhone.setText(telOut.getTelPhone());
        	textCountryCode.setText(telOut.getCountryCode());
    	}
    }
    
    /**
     * 检验
     */
    private boolean validation(){
    	/*if(StringUtils.isEmpty(textTelName.getText())){
    		MessageBoxHelper.openError(dialog, "电话名称不能为空！");
    		return false;
    	}
    	if(StringUtils.isEmpty(textTelPhone.getText())){
    		MessageBoxHelper.openError(dialog, "电话号码不能为空！");
    		return false;
    	}
    	
    	if(StringUtils.isEmpty(textCountryCode.getText())){
    		MessageBoxHelper.openError(dialog, "国家代码不能为空！");
    		return false;
    	}*/
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
    	}else{
    		telOut.setTelName(textTelName.getText());
    		telOut.setTelPhone(textTelPhone.getText());
    		telOut.setCountryCode(textCountryCode.getText());
			
    		try {
    			sendTelephoneWriteUserPacket(telOut);
			} catch (Exception e1) {
				e1.printStackTrace();
				btnOk.setEnabled(true);
				MessageBoxHelper.openError(dialog, message_opreate_error);
			}
    	
    	}
    }
    	
    
    private void sendTelephoneWriteUserPacket(TelOut telOut){
    	TelephoneWriteUserPacket packet =  new TelephoneWriteUserPacket();
    	packet.setTelId(Integer.valueOf(telOut.getTelId().substring(DictConst.TABLE_PREFIX_TEL_OUT.length())));
    	packet.setTelphone(telOut.getTelPhone());
    	packet.setCountryCode(telOut.getCountryCode());
    	packetProcessor.send(packet);
    }
    
    @Override
	public void packetArrived(PacketEvent e) {
    	InPacket in = (InPacket) e.getSource();
		// 现在开始判断包的类型，作出相应的处理
		switch (in.getCommand()) {
		case Protocol.CMD_TELEPHONE_WRITE_USER:
			processTelephoneWriteUserSuccess(in);
			break;
		case Protocol.CMD_UNKNOWN:
			processUnknown(in);
			break;
		}
	}

    private void processTelephoneWriteUserSuccess(InPacket in){
    	display.syncExec(new Runnable() {
			public void run() {
				try {
					boTelOut.update(telOut);
					retTelOut = telOut;
					dialog.close();
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

    /**
	 * 关闭dialog
	 */
	public void close() {
		dialog.close();
	}
	
	public void setTelOut(TelOut telOut) {
		if(telOut != null) isAdd = false;
		this.telOut = telOut;
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
