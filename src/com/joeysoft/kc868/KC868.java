package com.joeysoft.kc868;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

import com.joeysoft.kc868.client.KC868Client;
import com.joeysoft.kc868.client.util.FileTool;
import com.joeysoft.kc868.resource.IconHolder;
import com.joeysoft.kc868.ui.Colors;
import com.joeysoft.kc868.ui.MainShell;
import com.joeysoft.kc868.ui.dialogs.LoginDialog;
import com.joeysoft.kc868.ui.dialogs.SplashScreen;
import com.joeysoft.kc868.ui.helper.MessageBoxHelper;

/**
 * 主程序入口
 * @author JOEY
 *
 */
public class KC868 {

	// 是否windows
	public static boolean IS_WIN32;
	public static boolean IS_GTK;
	
	/** 安装目录 */
	public static String INSTALL_DIR;
	
	/**  配置文件目录*/
	public static String CONFIG_DIR;
	
	/** 用户登录历史信息文件 */
	public static String LOGIN_HISTORY;
	/** 系统设置文件 */
	public static String GLOBAL;
	
	/** 语言 */
    public static String language;
	    
	/**
	 * Launch the application.
	 * @param args
	 */
	public static void main(String[] args) {
		// 获得平台类型
		detectPlatform();
		// 初始化系统文件路径	
		initSysFilePath(args);
		FileTool.deleteFile(KC868.CONFIG_DIR + SystemConfig.getInstance().getDbLockFileName());
		
		Shell shell = new Shell(new Display());
		
		Colors.init();
		
		MainShell mainShell = new MainShell();
		
		KC868Client client = new KC868Client(mainShell);
		LoginDialog login = new LoginDialog(shell, false);
		login.setClient(client);
		client.setLogin(login);
		SplashScreen splashScreen = new SplashScreen(shell);
		splashScreen.setClient(client);
		
		// 是否同步成功
		boolean isSynced = false;
		// 是否点了登陆
		boolean isOk = true;
		// 
		boolean changeUser = true;
		
		while(changeUser){
			if(mainShell.getShell().isDisposed()){
				client.stopChannel();
				mainShell = new MainShell();
				client.setMainShell(mainShell);
				login.setIgnoreAuto(true);
			}
			changeUser = false;
			isSynced = false;
			isOk = true;
			login.setConncetioned(false);
			while(!isSynced && isOk){
				isOk = login.open();
				if(login.isLogined()){
					splashScreen.open();
					if(splashScreen.isSynced()){
						isSynced = true;
					}else{
						isSynced = false;
					}
				}else{
					isOk = false;
				}
			}
			
			if(isOk && login.isConncetioned() && login.isLogined() && isSynced){
				mainShell.setClient(client);
				mainShell.open();
				changeUser = mainShell.isChangeUser();
			}
		}
		/*mainShell.setClient(client);
		mainShell.open();*/
		Colors.dispose();
		IconHolder.getInstance().dispose();
		client.stopClient();
		System.exit(-1);
	}

	/**
	 * 获得平台类型
	 */
	private static void detectPlatform() {
	    // 如果不是Mac，底层也不是gtk，则做双缓冲
		String platform = SWT.getPlatform();
		IS_WIN32 = "win32".equals(platform);
		IS_GTK = "gtk".equals(platform);
	}
	
	/**
	 * 初始化系统文件的路径，对于每个用户来说，这些值是不变的，所以这个方法只调用一次
	 * @param args
	 */
	private static void initSysFilePath(String[] args) {
		if(args.length == 0)
			INSTALL_DIR = ".";
		else
			INSTALL_DIR = args[0];
		
		CONFIG_DIR = INSTALL_DIR + "/config/";
		
		LOGIN_HISTORY = INSTALL_DIR + "/config/logins.xml";
		GLOBAL = INSTALL_DIR + "/config/global.xml";
		
		language = "zh";
	}
}
