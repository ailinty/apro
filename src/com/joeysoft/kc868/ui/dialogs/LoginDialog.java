package com.joeysoft.kc868.ui.dialogs;

import static com.joeysoft.kc868.resource.Messages.*;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CCombo;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.ShellAdapter;
import org.eclipse.swt.events.ShellEvent;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import com.joeysoft.kc868.KC868;
import com.joeysoft.kc868.SystemConfig;
import com.joeysoft.kc868.client.KC868Client;
import com.joeysoft.kc868.client.event.IPacketListener;
import com.joeysoft.kc868.client.event.PacketEvent;
import com.joeysoft.kc868.client.packets.ErrorPacket;
import com.joeysoft.kc868.client.packets.InPacket;
import com.joeysoft.kc868.client.packets.in.GetPasswordReplyPacket;
import com.joeysoft.kc868.client.packets.in.UnknownInPacket;
import com.joeysoft.kc868.client.packets.out.GetPasswordAdminPacket;
import com.joeysoft.kc868.client.packets.out.GetPasswordHostPacket;
import com.joeysoft.kc868.client.support.Protocol;
import com.joeysoft.kc868.common.Md5;
import com.joeysoft.kc868.resource.IconHolder;
import com.joeysoft.kc868.resource.Messages;
import com.joeysoft.kc868.ui.BorderStyler;
import com.joeysoft.kc868.ui.Colors;
import com.joeysoft.kc868.ui.helper.MessageBoxHelper;
import com.joeysoft.kc868.ui.helper.UITool;
import com.joeysoft.kc868.ui.listener.AroundBorderPaintListener;
import com.joeysoft.kc868.widgets.ImageButton;
import com.joeysoft.kc868.widgets.qstyle.Slat;
import com.joeysoft.kc868.xsd.LoginUtil;
import com.joeysoft.kc868.xsd.login.Login;
import com.joeysoft.kc868.xsd.login.LoginFactory;
import com.joeysoft.kc868.xsd.login.Logins;

public class LoginDialog extends Dialog implements IPacketListener{

	private Shell shell;
    private Display display;
	 // Shell
    private Shell dialog;
	// 是否记住密码
    private boolean rememberPassword = true;
    // 是否同步主机
    private boolean loginSync = true;
	// 是否自动登录
    private boolean autoLogin;
    // 是否忽略自动登录选项
    private boolean ignoreAuto = true;
    
    // 用户类型:管理员、普通用户
    private int userType;
    // ip地址
    private String IP;
    // 密码
    private String pwd;
    private String md5Pwd;
    // port 端口
    private String port;
    // 用户是否点了登陆按钮
    private boolean ok;
    // 用户这次是否修改了密码
    private boolean changed;
    
    // 是否与服务器连接
    private boolean conncetioned;
    // 是否登录成功 判断密码是否正确
    private boolean logined;
    
    // 所有控件
    private ImageButton btnLogin;
    private ImageButton btnCancel;
    private ImageButton btnClear;
    private Label btnPwd, btnAuto, btnSync;
    private Text textPwd, textPort;
    private CCombo comboUser, comboIP;
    
    private Md5 md5;
    
    // IconHolder实例
    private IconHolder icons = IconHolder.getInstance();
    
    // 登陆历史信息文件根元素对象
    private Logins logins;
    
    private KC868Client client;
    /**
     * 构造函数
     * 
     * @param shell
     * 		父窗口
     * @param ignoreAuto
     * 		true表示忽略掉自动登录选项，不要自动返回
     */
    public LoginDialog(Shell shell, boolean ignoreAuto) {
        super(shell, SWT.NO_TRIM | SWT.NO_BACKGROUND);
        this.ignoreAuto = ignoreAuto;
        this.shell = shell;
        display = shell.getDisplay();
        md5 = new Md5();
    }

    private void initLayout() {
    	BorderStyler styler = new BorderStyler();
    	styler.setHideWhenMinimize(false);
    	styler.setResizable(false);
    	Composite center = styler.decorateShell(dialog);
    	center.setBackgroundImage(icons.getBackgroundImage(IconHolder.bmpBgLogin));
    	center.setBackgroundMode(SWT.INHERIT_FORCE);
    	center.setBackground(Colors.LOGIN_BACKGROUND);

        UITool.setDefaultBackground(null);
        UITool.setDefaultForeground(Colors.WHITE);
        
        Label titleLabel = UITool.createLabel(center, SystemConfig.getInstance().getSystemTitle());
        titleLabel.setBounds(190, 38, 600, 40);
        titleLabel.setFont(Colors.TITLE_FONT);
        
        // 用户
        Label label = UITool.createLabel(center, login_user);
        label.setBounds(268, 182, 54, 20);
        label.setFont(Colors.GLOBAL_FONT);
        // 用户下拉框
        comboUser = UITool.createCCombo(center, SWT.SIMPLE | SWT.READ_ONLY);
        comboUser.setBounds(328, 182, 176, 20);
        comboUser.setBackground(Colors.WHITE);
        comboUser.add(login_user_super, 0);
        comboUser.add(login_user_normal, 1);
        comboUser.select(0);
        
        // 地址
        label = UITool.createLabel(center, login_ip);
        label.setBounds(268, 237, 54, 20);
        label.setFont(Colors.GLOBAL_FONT);
        // 地址下拉框
        comboIP = UITool.createCCombo(center, SWT.FLAT);
        comboIP.setBounds(328, 237, 176, 20);
        comboIP.setBackground(Colors.WHITE);
        
        btnClear  =  UITool.createImageButton(center, login_clear, icons.getImage(IconHolder.bmpBtnClear), 
        		icons.getImage(IconHolder.bmpBtnClearUp), icons.getImage(IconHolder.bmpBtnClearOn));
        btnClear.setFont(Colors.GLOBAL_FONT);
        btnClear.setBounds(514, 237, 72, 26);
        btnClear.addMouseListener(new MouseAdapter(){
       	 public void mouseDown(MouseEvent e) {
       		 clearLogins();
       	 }
       });
        
        // 密码
        label = UITool.createLabel(center, login_pwd);
        label.setBounds(268, 292, 54, 20);
        label.setFont(Colors.GLOBAL_FONT);
        // 密码框
        textPwd = UITool.createSingleText(center, SWT.SINGLE | SWT.PASSWORD);
        textPwd.setBackground(Colors.WHITE);
        textPwd.setEchoChar('*');
        textPwd.setBounds(328, 292, 176, 20);
        
        // 端口
        label = UITool.createLabel(center, login_port);
        label.setBounds(268, 347, 54, 20);
        label.setFont(Colors.GLOBAL_FONT);
        // 端口框
        textPort = UITool.createSingleText(center, SWT.SINGLE);
        textPort.setBackground(Colors.WHITE);
        textPort.setBounds(328, 347, 176, 20);
        
        
        btnPwd = UITool.createLabel(center, "", new GridData(GridData.FILL_HORIZONTAL));
        btnPwd.setImage(icons.getImage(IconHolder.bmpCheckboxOn));
        btnPwd.setBounds(228, 402, 30, 30);
        btnPwd.addMouseListener(new MouseAdapter(){
        	 public void mouseDown(MouseEvent e) {
        		 rememberPassword = !rememberPassword;
        		 if(rememberPassword) 
        			 btnPwd.setImage(icons.getImage(IconHolder.bmpCheckbox));
        		 else
        			 btnPwd.setImage(icons.getImage(IconHolder.bmpCheckboxOn));
             }
        });
        
        label = UITool.createLabel(center, login_remember, new GridData(GridData.FILL_HORIZONTAL));
        label.setFont(Colors.GLOBAL_FONT);
        label.setBounds(260, 407, 84, 20);
        label.addMouseListener(new MouseAdapter(){
	       	 public void mouseDown(MouseEvent e) {
	    		 rememberPassword = !rememberPassword;
	    		 if(rememberPassword) 
	    			 btnPwd.setImage(icons.getImage(IconHolder.bmpCheckbox));
	    		 else
	    			 btnPwd.setImage(icons.getImage(IconHolder.bmpCheckboxOn));
	         }
	    });
        
        btnAuto = UITool.createLabel(center, "", new GridData(GridData.FILL_HORIZONTAL));
        btnAuto.setImage(icons.getImage(IconHolder.bmpCheckboxOn));
        btnAuto.setBounds(350, 402, 30, 30);
        btnAuto.addMouseListener(new MouseAdapter(){
	       	 public void mouseDown(MouseEvent e) {
	       		autoLogin = !autoLogin;
	       		 if(autoLogin) 
	       			btnAuto.setImage(icons.getImage(IconHolder.bmpCheckbox));
	       		 else
	       			btnAuto.setImage(icons.getImage(IconHolder.bmpCheckboxOn));
	            }
	       });
        
        label = UITool.createLabel(center, login_auto_login, new GridData(GridData.FILL_HORIZONTAL));
        label.setFont(Colors.GLOBAL_FONT);
        label.setBounds(382, 407, 84, 20);
        label.addMouseListener(new MouseAdapter(){
	       	 public void mouseDown(MouseEvent e) {
	       		ignoreAuto = !ignoreAuto;
	       		 if(ignoreAuto) 
	       			btnAuto.setImage(icons.getImage(IconHolder.bmpCheckbox));
	       		 else
	       			btnAuto.setImage(icons.getImage(IconHolder.bmpCheckboxOn));
	            }
	       });
        
        
        btnSync = UITool.createLabel(center, "", new GridData(GridData.FILL_HORIZONTAL));
        btnSync.setImage(icons.getImage(IconHolder.bmpCheckboxOn));
        btnSync.setBounds(472, 402, 30, 30);
        btnSync.addMouseListener(new MouseAdapter(){
	       	 public void mouseDown(MouseEvent e) {
	       		loginSync = !loginSync;
	       		 if(loginSync) 
	       			btnSync.setImage(icons.getImage(IconHolder.bmpCheckbox));
	       		 else
	       			btnSync.setImage(icons.getImage(IconHolder.bmpCheckboxOn));
	            }
	       });
        
        label = UITool.createLabel(center, login_sync, new GridData(GridData.FILL_HORIZONTAL));
        label.setFont(Colors.GLOBAL_FONT);
        label.setBounds(504, 407, 84, 20);
        label.addMouseListener(new MouseAdapter(){
	       	 public void mouseDown(MouseEvent e) {
	       		loginSync = !loginSync;
	       		 if(loginSync) 
	       			btnSync.setImage(icons.getImage(IconHolder.bmpCheckbox));
	       		 else
	       			btnSync.setImage(icons.getImage(IconHolder.bmpCheckboxOn));
	            }
	       });
        
        btnLogin =  UITool.createImageButton(center, button_login, icons.getImage(IconHolder.bmpBtnLogin), 
        		icons.getImage(IconHolder.bmpBtnLoginUp), icons.getImage(IconHolder.bmpBtnLoginOn));
        btnLogin.setFont(Colors.GLOBAL_FONT);
        btnLogin.setBounds(268, 457, 114, 41);
        
        btnCancel = UITool.createImageButton(center, button_cancel, icons.getImage(IconHolder.bmpBtnLogin), 
        		icons.getImage(IconHolder.bmpBtnLoginUp), icons.getImage(IconHolder.bmpBtnLoginOn));
        btnCancel.setFont(Colors.GLOBAL_FONT);
        btnCancel.setBounds(410, 457, 114, 41);
    }
    
    /**
	 * 打开对话框
	 */
	public boolean open() {
		ok = false;
		logined = false;
		// create dialog
	    Shell parent = getParent();
	    Display display = parent.getDisplay();
		dialog = new Shell(parent, getStyle());
		
		initLayout();
		// 初始化控件的值
		initValue();
		/*if(!ignoreAuto && chkAuto.getSelection()) {
			saveInput();
			return true;
		}*/
		// init event listeners
		initListeners();
		
		hookListener();
		
		// set title and image
		dialog.setImage(icons.getImage(IconHolder.icoHome));
		dialog.setText(SystemConfig.getInstance().getSystemTitle());
		// set dialog to center of screen
		dialog.pack();
		// 设置窗口位置和大小
		dialog.setSize(800, 634);
		Rectangle bounds = display.getPrimaryMonitor().getBounds();
        Rectangle rect = dialog.getBounds();
        int x = bounds.x + (bounds.width - rect.width) / 2;
        int y = bounds.y + (bounds.height - rect.height) / 2;
        dialog.setLocation(x, y);
        
		// 设置密码框获得焦点
        textPwd.setFocus();
        comboIP.setSelection(new Point(0, 0));
        comboUser.setSelection(new Point(0, 0));
		// event loop
		dialog.open();
		if(!ignoreAuto && autoLogin){
			doOk();
		}
		while(!dialog.isDisposed())
			if(!display.readAndDispatch()) 
			    display.sleep();
			
		return ok;
	}
	
    /**
     * 初始化监听器
     */
    private void initListeners() {
    	dialog.addShellListener(new ShellAdapter() {
			public void shellClosed(ShellEvent e) {
				unhookListener();
			}
		});
    	
    	 // 取消按钮鼠标事件
        btnCancel.addMouseListener(new MouseAdapter() {
            public void mouseUp(MouseEvent e) {
            	ok = false;
            	Colors.dispose();
            	dialog.close();
            }
        });
        // 登陆按钮鼠标事件
        btnLogin.addMouseListener(new MouseAdapter() {
            public void mouseUp(MouseEvent e) {
            	btnLogin.setEnabled(false);
                doOk();
            }
        });
        // 密码框的输入事件
        textPwd.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                if(e.character == SWT.CR) {
                    doOk();
                }
            }
        });
        // 密码框的改变事件
        textPwd.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				changed = true;
			}        		
    	});
        // ip 地址改变事件
        comboIP.addSelectionListener(new SelectionAdapter() {
            public void widgetSelected(SelectionEvent e) {
                // 查找选择的号码的信息，重新设置控件的value
                Login login = LoginUtil.findLogin(logins, comboIP.getText());
                if(login == null) {
                    return;
                }
                setInitValue(login);
            }
        });
    }
    
    /**
     * 用户按了登录按钮时调用这个方法
     */
    private void doOk() {
    	ok = true;
    	saveInput();
    	// 检查配置文件，如果不存在则创建一个
    	File loginHistory = new File(KC868.LOGIN_HISTORY);
    	if(logins == null)
        	logins = LoginUtil.createDefault();
    	 // 检查是否当前登陆是新的记录
        Login login = LoginUtil.findLogin(logins, comboIP.getText());
        if(login == null) {
        	// 如果没有，把新的登陆信息加入
	        login = LoginFactory.eINSTANCE.createLogin();
	        // 设置登陆信息
	        setLoginValue(login);
	        // 添加login
	        logins.getLogin().add(login);	      
	        
	        /*
	         * 这里新建了一个临时列表来排序，因为一个很奇怪的bug
	         * 使用logins.getLogin()得到的列表，排序时会出错误，
	         * 确实没有道理，也许是EMF的bug
	         */
	        List<Login> temp = new ArrayList<Login>(logins.getLogin());
	        logins.getLogin().clear();
	        logins.getLogin().addAll(temp);
        } else {
        	setLoginValue(login);
        }
        // 修改最后一次登陆的IP信息
        logins.setLastLogin(comboIP.getText());
        
        // 写入文件
        LoginUtil.save(loginHistory, logins);
        
        if(!conncetioned){ // 连接主机
        	connectioning();
        }else{
        	login();
        }
    }
    
    /**
     * 清空列表
     */
    private void clearLogins(){
    	File loginHistory = new File(KC868.LOGIN_HISTORY);
    	if(logins == null){
        	logins = LoginUtil.createDefault();
    	}
    	
    	logins.getLogin().clear();
    	 // 写入文件
        LoginUtil.save(loginHistory, logins);
        
        comboIP.removeAll();
    }
    
    /**
     * 添加监听器
     */
    private void hookListener(){
    	client.getPacketProcessor().getRouter().installProcessor(this);
    }
    
    /**
     * 移除监听器
     */
    private void unhookListener(){
    	client.getPacketProcessor().getRouter().removeProcessor(this);
    }
    
    /**
     * 连接
     */
    public void connectioning(){
    	client.setHost(IP);
		client.setPort(Integer.valueOf(port));
		client.setPwd(pwd);
		client.connectionClient();
    }
    
    /**
     * 登陆 发送获取密码包
     */
    public void login(){
		conncetioned = true;
		if(SystemConfig.getInstance().isAdmin()){
			GetPasswordAdminPacket packet = new GetPasswordAdminPacket();
			client.getPacketProcessor().send(packet);
		}else{
			GetPasswordHostPacket packet = new GetPasswordHostPacket();
			client.getPacketProcessor().send(packet);
		}
    }
    
    @Override
	public void packetArrived(PacketEvent e) {
    	InPacket in = (InPacket) e.getSource();
    	
    	 // 现在开始判断包的类型，作出相应的处理
        switch (in.getCommand()) {
	        case Protocol.CMD_GET_PASSWORD:
	        	processGetPasswordSuccess(in);
	        	break;
	        case Protocol.CMD_UNKNOWN:
	        	processUnknown(in);
	        	break;
        }
	}
    
    /**
     * 处理获取密码应答包
     * @param in
     */
    private void processGetPasswordSuccess(InPacket in){
		GetPasswordReplyPacket reply = (GetPasswordReplyPacket)in;
		if(md5Pwd.equals(md5.getMD5ofStr(reply.getPassword()))){
			logined = true;
			if(SystemConfig.getInstance().isAdmin()){
				SystemConfig.getInstance().setAdminPassword(reply.getPassword());
			}else{
				SystemConfig.getInstance().setPassword(reply.getPassword());
			}
			
			display.syncExec(new Runnable() {
				public void run() {
					// 关闭登陆窗口
					dialog.close();
				}
			});
		}else{
			display.syncExec(new Runnable() {
				public void run() {
					setLoginButtonEnabled(true);
					MessageBoxHelper.openError(dialog, message_box_user_pwd_error);
				}
			});
		}
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
                	conncetioned = false;
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
				setLoginButtonEnabled(true);
				MessageBoxHelper.openError(dialog, message);
			}
		});
	}
    
    /**
     * 设置登录按钮
     * @param b
     */
    public void setLoginButtonEnabled(boolean b){
    	btnLogin.setEnabled(b);
    }
    
    /**
     * 把当前信息存入Login对象中
     * @param login Login对象
     */
    private void setLoginValue(Login login) {
        login.setIP(comboIP.getText());
        login.setUser(String.valueOf(comboUser.getSelectionIndex()));
        login.setPort(textPort.getText());
        login.setRememberPassword(autoLogin || rememberPassword);
        login.setLoginSync(loginSync);
        login.setAutoLogin(autoLogin);
        if(rememberPassword){
        	login.setPassword(md5Pwd);
        }
        
        /*if(rememberPassword)
            login.setPassword(new String(codec.encode(md5pwd)));
        else
        	login.setPassword(new String(codec.encode(getTrianglePasswordKey(md5pwd))));*/
    }
    
    /**
     * 初始化控件的值
     */
    private void initValue() {
    	// 检查配置文件，如果不存在则创建一个
    	File loginHistory = new File(KC868.LOGIN_HISTORY);
    	// 读入登陆信息文件
    	logins = LoginUtil.load(loginHistory);
    	if(logins == null)
    	    logins = LoginUtil.createDefault();
    	
    	// 得到上一次登陆的人的历史信息
        Login login = LoginUtil.findLogin(logins, logins.getLastLogin());
        // 把所有的IP都加到combo里面
        addAllIP();
        // 检查login，不应该为null，如果为null了，那文件有问题，采用第一项login
        if(login == null) {
            List<Login> list = logins.getLogin();
            if(list == null || list.size() == 0) return;
            login = list.get(0);
        }
        // 设置初始值
        setInitValue(login);
    }
    
    /**
     * 把所有的IP都加到combo里面
     */
    private void addAllIP() {
		for(Login login : (List<Login>)logins.getLogin())
            comboIP.add(login.getIP());
    }
    
    /**
     * 设置初始值
     * @param login Login对象
     */
    private void setInitValue(Login login) {
        comboIP.setText(login.getIP());
        comboUser.select(Integer.valueOf(login.getUser()));
       // chkRemember.setSelection();
        rememberPassword = login.isAutoLogin() || login.isRememberPassword();
        if(rememberPassword)
        	btnPwd.setImage(icons.getImage(IconHolder.bmpCheckbox));
   		 else
   			btnPwd.setImage(icons.getImage(IconHolder.bmpCheckboxOn));
        autoLogin = login.isAutoLogin();
        if(autoLogin) 
   			btnAuto.setImage(icons.getImage(IconHolder.bmpCheckbox));
   		 else
   			btnAuto.setImage(icons.getImage(IconHolder.bmpCheckboxOn));
       
        loginSync = login.isLoginSync();
        if(loginSync) 
   			btnSync.setImage(icons.getImage(IconHolder.bmpCheckbox));
   		 else
   			btnSync.setImage(icons.getImage(IconHolder.bmpCheckboxOn));
        
        if(rememberPassword) {
        	textPwd.setText("xxxxxxxx");
        	// md5pwd = codec.decode(login.getPassword().getBytes());
	        //pwd = login.getPassword();
	        md5Pwd = login.getPassword();
	    } else {
	        textPwd.setText("");
	        md5Pwd = "";
	        //md5pwd = new byte[16];
	    }
        
        textPort.setText(login.getPort());
        changed = false;
    }
    
    /**
     * 保存输入结果
     */
    private void saveInput() {
        rememberPassword = autoLogin || rememberPassword;
        IP = comboIP.getText();
        userType = comboUser.getSelectionIndex();
        port = textPort.getText();
        if(changed){
        	pwd = textPwd.getText();
        	md5Pwd = md5.getMD5ofStr(pwd);
        }
        
        SystemConfig.getInstance().setLoginSync(loginSync);
        SystemConfig.getInstance().setUserType(userType);
    }
    
	public boolean isRememberPassword() {
		return rememberPassword;
	}

	public boolean isAutoLogin() {
		return autoLogin;
	}

	public int getUserType() {
		return userType;
	}

	public String getIP() {
		return IP;
	}

	public String getPwd() {
		return pwd;
	}

	public String getPort() {
		return port;
	}

	public boolean isChanged() {
		return changed;
	}

	public boolean isLoginSync() {
		return loginSync;
	}

	public boolean isConncetioned() {
		return conncetioned;
	}

	public KC868Client getClient() {
		return client;
	}

	public void setClient(KC868Client client) {
		this.client = client;
	}

	public boolean isLogined() {
		return logined;
	}
	
	public void setConncetioned(boolean conncetioned) {
		this.conncetioned = conncetioned;
	}
	
	public void setIgnoreAuto(boolean ignoreAuto) {
		this.ignoreAuto = ignoreAuto;
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
