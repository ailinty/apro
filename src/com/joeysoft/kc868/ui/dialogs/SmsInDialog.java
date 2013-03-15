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
import com.joeysoft.kc868.client.packets.out.GSMWriteCmpPacket;
import com.joeysoft.kc868.client.packets.out.TelephoneWriteUserPacket;
import com.joeysoft.kc868.client.support.PacketProcessor;
import com.joeysoft.kc868.client.support.Protocol;
import com.joeysoft.kc868.db.bean.SmsIn;
import com.joeysoft.kc868.db.bean.TelOut;
import com.joeysoft.kc868.db.bo.BOSmsIn;
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
 * 
 * @author JOEY
 * 
 */
public class SmsInDialog extends Dialog implements IPacketListener {

	private Shell shell;
	private Display display;
	// Shell
	private Shell dialog;

	// 所有控件
	private ImageButton btnOk, btnCancel;

	private Text textSmsName, textSmsContent;

	// IconHolder实例
	private IconHolder icons = IconHolder.getInstance();

	private MainShell main;

	private PacketProcessor packetProcessor;

	private BOSmsIn boSmsIn;

	private SmsIn smsIn, retSmsIn;
	private boolean isAdd = true;

	/**
	 * 构造函数
	 * 
	 * @param shell
	 *            父窗口
	 * @param ignoreAuto
	 *            true表示忽略掉自动登录选项，不要自动返回
	 */
	public SmsInDialog(MainShell main) {
		super(main.getShell(), SWT.NO_TRIM | SWT.NO_BACKGROUND);
		this.main = main;
		this.packetProcessor = main.getClient().getPacketProcessor();
		this.shell = main.getShell();
		display = shell.getDisplay();
		boSmsIn = new BOSmsIn();
	}

	private void initLayout() {
		BorderStyler styler = new BorderStyler();
		styler.setHideWhenMinimize(false);
		styler.setResizable(false);
		Composite center = styler.decorateShell(dialog);
		// center.setBackgroundImage(icons.getBackgroundImage(IconHolder.bmpEquip));
		center.setBackgroundMode(SWT.INHERIT_FORCE);
		center.setBackground(Colors.LOGIN_BACKGROUND);

		UITool.setDefaultBackground(null);
		UITool.setDefaultForeground(Colors.WHITE);

		// 名称
		Label label = UITool.createLabel(center, name);
		label.setBounds(24, 27, 100, 20);
		label.setFont(Colors.GLOBAL_FONT);
		// 名称框
		textSmsName = UITool.createSingleText(center, SWT.SINGLE);
		textSmsName.setBackground(Colors.WHITE);
		textSmsName.setBounds(130, 27, 206, 20);

		// 电话
		label = UITool.createLabel(center, sms_content);
		label.setBounds(24, 67, 100, 20);
		label.setFont(Colors.GLOBAL_FONT);
		// 内容框
		textSmsContent = UITool.createMultiText(center);
		textSmsContent.setBackground(Colors.WHITE);
		textSmsContent.setBounds(130, 67, 206, 40);

		btnOk = UITool.createImageButton(center, button_ok,
				icons.getImage(IconHolder.bmpBtnLogin),
				icons.getImage(IconHolder.bmpBtnLoginUp),
				icons.getImage(IconHolder.bmpBtnLoginOn));
		btnOk.setFont(Colors.GLOBAL_FONT);
		btnOk.setBounds(54, 137, 114, 41);

		btnCancel = UITool.createImageButton(center, button_cancel,
				icons.getImage(IconHolder.bmpBtnLogin),
				icons.getImage(IconHolder.bmpBtnLoginUp),
				icons.getImage(IconHolder.bmpBtnLoginOn));
		btnCancel.setFont(Colors.GLOBAL_FONT);
		btnCancel.setBounds(195, 137, 114, 41);
	}

	/**
	 * 打开对话框
	 */
	public SmsIn open() {
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
		dialog.setSize(378, 217);
		Rectangle bounds = display.getPrimaryMonitor().getBounds();
		Rectangle rect = dialog.getBounds();
		int x = bounds.x + (bounds.width - rect.width) / 2;
		int y = bounds.y + (bounds.height - rect.height) / 2;
		dialog.setLocation(x, y);

		// event loop
		dialog.open();
		while (!dialog.isDisposed())
			if (!display.readAndDispatch())
				display.sleep();

		return retSmsIn;
	}

	/**
	 * 初始化监听器
	 */
	private void initListeners() {
		dialog.addShellListener(new ShellAdapter() {
			public void shellClosed(ShellEvent e) {
				unhookListener();
				main.getShellRegistry().deregisterSmsInDialog();
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
		if (!isAdd) { // 修改
			textSmsName.setText(smsIn.getSmsName());
			textSmsContent.setText(smsIn.getSmsContent());
		}
	}

	/**
     * 检验
     */
    private boolean validation(){
    	/*if(StringUtils.isEmpty(textSmsName.getText())){
    		MessageBoxHelper.openError(dialog, "短信名称不能为空！");
    		return false;
    	}
    	if(StringUtils.isEmpty(textSmsContent.getText())){
    		MessageBoxHelper.openError(dialog, "短信内容不能为空！");
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
    	if (isAdd) { // 添加
		} else {
			smsIn.setSmsName(textSmsName.getText());
			smsIn.setSmsContent(textSmsContent.getText());

			try {
				sendGSMWriteCmpPacket(smsIn);
			} catch (Exception e1) {
				e1.printStackTrace();
				btnOk.setEnabled(true);
				MessageBoxHelper.openError(dialog,
						message_opreate_error);
			}

		}
    }

	private void sendGSMWriteCmpPacket(SmsIn smsIn){
    	GSMWriteCmpPacket packet =  new GSMWriteCmpPacket();
    	packet.setSmsId(Integer.valueOf(smsIn.getSmsId().substring(DictConst.TABLE_PREFIX_SMS_IN.length())));
    	packet.setSmsContent(smsIn.getSmsContent());
    	packetProcessor.send(packet);
    }
	
	@Override
	public void packetArrived(PacketEvent e) {
		InPacket in = (InPacket) e.getSource();
		// 现在开始判断包的类型，作出相应的处理
		switch (in.getCommand()) {
		case Protocol.CMD_GSM_WRITE_CMP:
			processGSMWriteCmpSuccess(in);
			break;
		case Protocol.CMD_UNKNOWN:
			processUnknown(in);
			break;
		}
	}
	
	private void processGSMWriteCmpSuccess(InPacket in){
		display.syncExec(new Runnable() {
			public void run() {
				try {
					boSmsIn.update(smsIn);
					retSmsIn = smsIn;
					dialog.close();
				} catch (Exception e) {
					e.printStackTrace();
					btnOk.setEnabled(true);
					MessageBoxHelper.openError(dialog,
							message_opreate_error);
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
	private void hookListener() {
		packetProcessor.getRouter().installProcessor(this);
	}

	/**
	 * 移除监听器
	 */
	private void unhookListener() {
		packetProcessor.getRouter().removeProcessor(this);
	}

	public void setSmsIn(SmsIn smsIn) {
		if (smsIn != null)
			isAdd = false;
		this.smsIn = smsIn;
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
