package com.joeysoft.kc868.ui;

import static com.joeysoft.kc868.resource.Messages.*;

import java.io.IOException;
import java.sql.Connection;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseTrackAdapter;
import org.eclipse.swt.events.ShellAdapter;
import org.eclipse.swt.events.ShellEvent;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Tray;
import org.eclipse.swt.widgets.TrayItem;

import com.joeysoft.kc868.KC868;
import com.joeysoft.kc868.SystemConfig;
import com.joeysoft.kc868.client.KC868Client;
import com.joeysoft.kc868.client.util.FileTool;
import com.joeysoft.kc868.common.StringUtil;
import com.joeysoft.kc868.db.DBConnection;
import com.joeysoft.kc868.db.bo.BOSysConfig;
import com.joeysoft.kc868.db.util.DataAddrCodeManager;
import com.joeysoft.kc868.db.util.DictManager;
import com.joeysoft.kc868.resource.IconHolder;
import com.joeysoft.kc868.ui.helper.MenuHelper;
import com.joeysoft.kc868.ui.helper.MessageBoxHelper;
import com.joeysoft.kc868.ui.helper.ShellLauncher;
import com.joeysoft.kc868.ui.helper.ShellRegistry;
import com.joeysoft.kc868.ui.helper.UITool;

public class MainShell {

	private Shell shell;
	private Display display;
	private TrayItem item;

	private IconHolder icons;

	// 房间
	private Label roomLb;
	private Label roomBtn;
	// 安防
	private Label safetyLb;
	private Label safetyBtn;
	// 设备
	private Label equipLb;
	private Label equipBtn;
	// 信息
	private Label infoLb;
	private Label infoBtn;
	// 媒体
	private Label smartLb;
	private Label smartBtn;
	// 情景
	private Label sceneLb;
	private Label sceneBtn;
	// 系统设置
	private Label setLb;
	private Label setBtn;
	// 帮助
	private Label helpLb;
	private Label helpBtn;

	private Label welcomeLb;
	private Label smartHomeLb;
	private Label conHostLb;

	private KC868Client client;

	// 菜单帮助类
	private MenuHelper menuHelper;
	// 窗口注册表
	private ShellRegistry shellRegistry;
	// 窗口发射器
	private ShellLauncher shellLauncher;

	// 更换用户
	private boolean changeUser;

	// 数据库连接
	private Connection conn;

	private BOSysConfig boSysConfig;

	public MainShell() {
		initialize();

		// 初始化菜单
		menuHelper = new MenuHelper(this);
		menuHelper.initMenu();
	}

	/**
	 * 初始化
	 */
	private void initialize() {
		this.display = Display.getCurrent();
		icons = IconHolder.getInstance();
		shellRegistry = new ShellRegistry();
		shellLauncher = new ShellLauncher(this);

		initLayout();
		
		try {
			conn = DBConnection.getConnection();
		} catch (Exception e) {
			e.printStackTrace();
		}

		boSysConfig = new BOSysConfig();
		boSysConfig.initConfig();

		setCenterTitle();
	}

	/**
	 * 初始化界面布局
	 */
	private void initLayout() {
		Composite body = initShell();

		// 设置背景图片
		body.setBackgroundImage(icons.getImage(IconHolder.bmpBgMain));
		body.setBackgroundMode(SWT.INHERIT_FORCE);

		UITool.setDefaultBackground(null);
		UITool.setDefaultForeground(Colors.WHITE);

		// -----------------------------------------------房间-------------------------------------------
		roomLb = UITool.createLabel(body, room);
		roomLb.setCursor(display.getSystemCursor(SWT.CURSOR_HAND));
		roomLb.setBounds(384, 133, 54, 27);
		roomLb.setFont(Colors.GLOBAL_FONT);

		// 房间
		roomBtn = new Label(body, SWT.NONE);
		roomBtn.setCursor(display.getSystemCursor(SWT.CURSOR_HAND));
		roomBtn.setBounds(348, 25, 110, 110);
		roomBtn.setImage(icons.getImage(IconHolder.icoHouse));
		roomBtn.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				roomBtn.setImage(icons.getImage(IconHolder.icoHouse));
			}

			public void mouseUp(MouseEvent e) {
				roomBtn.setImage(icons.getImage(IconHolder.icoHouseOn));
				hide();
				shellLauncher.openRoomShell();
			}
		});
		roomBtn.addMouseTrackListener(new MouseTrackAdapter() {
			@Override
			public void mouseEnter(MouseEvent e) {
				roomBtn.setImage(icons.getImage(IconHolder.icoHouseOn));
			}

			@Override
			public void mouseExit(MouseEvent e) {
				roomBtn.setImage(icons.getImage(IconHolder.icoHouse));
			}
		});

		// -----------------------------------------------设备-------------------------------------------
		equipLb = UITool.createLabel(body, equip);
		equipLb.setCursor(display.getSystemCursor(SWT.CURSOR_HAND));
		equipLb.setBounds(217, 191, 54, 19);
		equipLb.setFont(Colors.GLOBAL_FONT);

		// 设备
		equipBtn = new Label(body, SWT.NONE);
		equipBtn.setCursor(display.getSystemCursor(SWT.CURSOR_HAND));
		equipBtn.setBounds(190, 90, 110, 110);
		equipBtn.setImage(icons.getImage(IconHolder.icoEquip));
		equipBtn.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				equipBtn.setImage(icons.getImage(IconHolder.icoEquip));
			}

			public void mouseUp(MouseEvent e) {
				equipBtn.setImage(icons.getImage(IconHolder.icoEquipOn));
				if(!SystemConfig.getInstance().isAdmin()){
					MessageBoxHelper.openError(shell, error_not_authorize);
					return;
				}
				hide();
				shellLauncher.openEquipShell();
			}
		});
		equipBtn.addMouseTrackListener(new MouseTrackAdapter() {
			@Override
			public void mouseEnter(MouseEvent e) {
				equipBtn.setImage(icons.getImage(IconHolder.icoEquipOn));
			}

			@Override
			public void mouseExit(MouseEvent e) {
				equipBtn.setImage(icons.getImage(IconHolder.icoEquip));
			}
		});

		// -----------------------------------------------情景-------------------------------------------
		sceneLb = UITool.createLabel(body, scene);
		sceneLb.setCursor(display.getSystemCursor(SWT.CURSOR_HAND));
		sceneLb.setBounds(535, 191, 54, 19);
		sceneLb.setFont(Colors.GLOBAL_FONT);

		// 情景
		sceneBtn = new Label(body, SWT.NONE);
		sceneBtn.setCursor(display.getSystemCursor(SWT.CURSOR_HAND));
		sceneBtn.setBounds(502, 90, 110, 110);
		sceneBtn.setImage(icons.getImage(IconHolder.icoScene));
		sceneBtn.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				sceneBtn.setImage(icons.getImage(IconHolder.icoScene));
			}

			public void mouseUp(MouseEvent e) {
				sceneBtn.setImage(icons.getImage(IconHolder.icoSceneOn));
				hide();
				shellLauncher.openSceneShell();
			}
		});
		sceneBtn.addMouseTrackListener(new MouseTrackAdapter() {
			@Override
			public void mouseEnter(MouseEvent e) {
				sceneBtn.setImage(icons.getImage(IconHolder.icoSceneOn));
			}

			@Override
			public void mouseExit(MouseEvent e) {
				sceneBtn.setImage(icons.getImage(IconHolder.icoScene));
			}
		});

		// -----------------------------------------------安防-------------------------------------------
		safetyLb = UITool.createLabel(body, safety);
		safetyLb.setCursor(display.getSystemCursor(SWT.CURSOR_HAND));
		safetyLb.setBounds(166, 345, 54, 27);
		safetyLb.setFont(Colors.GLOBAL_FONT);

		// 安防
		safetyBtn = new Label(body, SWT.NONE);
		safetyBtn.setCursor(display.getSystemCursor(SWT.CURSOR_HAND));
		safetyBtn.setBounds(131, 244, 110, 110);
		safetyBtn.setImage(icons.getImage(IconHolder.icoSafety));
		safetyBtn.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				safetyBtn.setImage(icons.getImage(IconHolder.icoSafety));
			}

			public void mouseUp(MouseEvent e) {
				safetyBtn.setImage(icons.getImage(IconHolder.icoSafetyOn));
				hide();
				shellLauncher.openSafetyShell();
			}
		});
		safetyBtn.addMouseTrackListener(new MouseTrackAdapter() {
			@Override
			public void mouseEnter(MouseEvent e) {
				safetyBtn.setImage(icons.getImage(IconHolder.icoSafetyOn));
			}

			@Override
			public void mouseExit(MouseEvent e) {
				safetyBtn.setImage(icons.getImage(IconHolder.icoSafety));
			}
		});

		// -----------------------------------------------帮助-------------------------------------------
		helpLb = UITool.createLabel(body, help);
		helpLb.setCursor(display.getSystemCursor(SWT.CURSOR_HAND));
		helpLb.setBounds(601, 345, 54, 27);
		helpLb.setFont(Colors.GLOBAL_FONT);

		// 帮助
		helpBtn = new Label(body, SWT.NONE);
		helpBtn.setCursor(display.getSystemCursor(SWT.CURSOR_HAND));
		helpBtn.setBounds(564, 244, 110, 110);
		helpBtn.setImage(icons.getImage(IconHolder.icoHelp));
		helpBtn.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				helpBtn.setImage(icons.getImage(IconHolder.icoHelp));
			}

			public void mouseUp(MouseEvent e) {
				helpBtn.setImage(icons.getImage(IconHolder.icoHelpOn));
				Runtime r = Runtime.getRuntime();
				String filePath = KC868.CONFIG_DIR + "readme.chm";
				try {
					r.exec("hh.exe " + filePath);
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		});
		helpBtn.addMouseTrackListener(new MouseTrackAdapter() {
			@Override
			public void mouseEnter(MouseEvent e) {
				helpBtn.setImage(icons.getImage(IconHolder.icoHelpOn));
			}

			@Override
			public void mouseExit(MouseEvent e) {
				helpBtn.setImage(icons.getImage(IconHolder.icoHelp));
			}
		});

		// -----------------------------------------------智能-------------------------------------------
		smartLb = UITool.createLabel(body, smart);
		smartLb.setCursor(display.getSystemCursor(SWT.CURSOR_HAND));
		smartLb.setBounds(235, 489, 60, 27);
		smartLb.setFont(Colors.GLOBAL_FONT);

		// 智能
		smartBtn = new Label(body, SWT.NONE);
		smartBtn.setCursor(display.getSystemCursor(SWT.CURSOR_HAND));
		smartBtn.setBounds(190, 388, 110, 110);
		smartBtn.setImage(icons.getImage(IconHolder.icoSmart));
		smartBtn.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				smartBtn.setImage(icons.getImage(IconHolder.icoSmart));
			}

			public void mouseUp(MouseEvent e) {
				smartBtn.setImage(icons.getImage(IconHolder.icoSmartOn));
				if(!SystemConfig.getInstance().isAdmin()){
					MessageBoxHelper.openError(shell, error_not_authorize);
					return;
				}
				hide();
				shellLauncher.openSmartShell();
			}
		});
		smartBtn.addMouseTrackListener(new MouseTrackAdapter() {
			@Override
			public void mouseEnter(MouseEvent e) {
				smartBtn.setImage(icons.getImage(IconHolder.icoSmartOn));
			}

			@Override
			public void mouseExit(MouseEvent e) {
				smartBtn.setImage(icons.getImage(IconHolder.icoSmart));
			}
		});

		// -----------------------------------------------信息-------------------------------------------
		infoLb = UITool.createLabel(body, info);
		infoLb.setCursor(display.getSystemCursor(SWT.CURSOR_HAND));
		infoLb.setBounds(535, 489, 54, 27);
		infoLb.setFont(Colors.GLOBAL_FONT);

		// 信息
		infoBtn = new Label(body, SWT.NONE);
		infoBtn.setCursor(display.getSystemCursor(SWT.CURSOR_HAND));
		infoBtn.setBounds(502, 388, 110, 110);
		infoBtn.setImage(icons.getImage(IconHolder.icoInfo));
		infoBtn.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				infoBtn.setImage(icons.getImage(IconHolder.icoInfo));
			}

			public void mouseUp(MouseEvent e) {
				infoBtn.setImage(icons.getImage(IconHolder.icoInfoOn));
				hide();
				shellLauncher.openInfoShell();
			}
		});
		infoBtn.addMouseTrackListener(new MouseTrackAdapter() {
			@Override
			public void mouseEnter(MouseEvent e) {
				infoBtn.setImage(icons.getImage(IconHolder.icoInfoOn));
			}

			@Override
			public void mouseExit(MouseEvent e) {
				infoBtn.setImage(icons.getImage(IconHolder.icoInfo));
			}
		});

		// -----------------------------------------------系统-------------------------------------------
		setLb = UITool.createLabel(body, system);
		setLb.setBounds(384, 546, 54, 34);
		setLb.setFont(Colors.GLOBAL_FONT);

		// 系统
		setBtn = new Label(body, SWT.NONE);
		setBtn.setCursor(display.getSystemCursor(SWT.CURSOR_HAND));
		setBtn.setBounds(348, 446, 110, 110);
		setBtn.setImage(icons.getImage(IconHolder.icoSys));
		setBtn.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				setBtn.setImage(icons.getImage(IconHolder.icoSys));
			}

			public void mouseUp(MouseEvent e) {
				setBtn.setImage(icons.getImage(IconHolder.icoSysOn));
				if(!SystemConfig.getInstance().isAdmin()){
					MessageBoxHelper.openError(shell, error_not_authorize);
					return;
				}
				hide();
				shellLauncher.openSystemShell();
			}
		});
		setBtn.addMouseTrackListener(new MouseTrackAdapter() {
			@Override
			public void mouseEnter(MouseEvent e) {
				setBtn.setImage(icons.getImage(IconHolder.icoSysOn));
			}

			@Override
			public void mouseExit(MouseEvent e) {
				setBtn.setImage(icons.getImage(IconHolder.icoSys));
			}
		});

		String title1 = StringUtil.substring(SystemConfig.getInstance()
				.getCenterTitle(), 0, 8, "", "GBK");
		String title2 = StringUtil.substring(SystemConfig.getInstance()
				.getCenterTitle(), StringUtil.length(title1), StringUtil.length(title1) + 13, "", "GBK");
		String title3 = StringUtil.substring(SystemConfig.getInstance()
				.getCenterTitle(), StringUtil.length(title1 + title2), StringUtil.length(title1 + title2) + 8, "", "GBK");

		welcomeLb = UITool.createLabel(body, title1);
		welcomeLb.setBounds(335, 244, 135, 34);
		welcomeLb.setFont(Colors.LOADING_FONT);

		smartHomeLb = UITool.createLabel(body, title2);
		smartHomeLb.setBounds(300, 293, 215, 34);
		smartHomeLb.setFont(Colors.LOADING_FONT);

		conHostLb = UITool.createLabel(body, title3);
		conHostLb.setBounds(335, 345, 135, 34);
		conHostLb.setFont(Colors.LOADING_FONT);
	}

	/**
	 * 初始化shell和边框，等等
	 */
	private Composite initShell() {
		int shellStyle = SWT.NO_TRIM;
		shell = new Shell(display, shellStyle);

		shell.setImage(icons.getImage(IconHolder.icoHome));
		// 添加事件监听器
		shell.addDisposeListener(new DisposeListener() {
			public void widgetDisposed(DisposeEvent e) {
			}
		});
		shell.addShellListener(new ShellAdapter() {
			public void shellIconified(ShellEvent e) {
				if (item != null && !item.getVisible()) {
					initTray();
				}
				menuHelper.hideAllMenu();
			}

			public void shellClosed(ShellEvent e) {
				if (SystemConfig.getInstance().isSysChanged()
						&& MessageBoxHelper.openQuestion(shell, error_data_changed)) {
					e.doit = false;
				} else {
					e.doit = true;
					logout();
					// 释放Tray Icon
					if (item != null)
						item.dispose();
				}
			}
		});
		shell.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent e) {
				if (e.stateMask == (SWT.CTRL | SWT.SHIFT) && e.keyCode == 'd') {
					shellLauncher.openDebugShell();
				}
			}
		});

		BorderStyler styler = new BorderStyler(this);
		styler.setCheckMinimizeWhenClose(true);
		styler.setMaximizeWhenDoubleClick(false);
		styler.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				menuHelper.hideAllMenu();
			}
		});
		return styler.decorateShell(shell);
	}

	/**
	 * 打开shell，开始事件循环
	 */
	public void open() {

		try {
			conn = DBConnection.getConnection();
		} catch (Exception e) {
			e.printStackTrace();
		}
		// 初始化数据字典
		DictManager.getInstance().init();
		// 初始化地址码、数据码缓存器
		DataAddrCodeManager.getInstance().init();

		boSysConfig = new BOSysConfig();
		boSysConfig.initConfig();

		setCenterTitle();
		
		// 初始化系统Tray Icon
		initTray();

		// 设置窗口位置和大小
		shell.setSize(800, 634);
		shell.setText(SystemConfig.getInstance().getSystemTitle());

		Rectangle bounds = display.getPrimaryMonitor().getBounds();
		Rectangle rect = shell.getBounds();
		int x = bounds.x + (bounds.width - rect.width) / 2;
		int y = bounds.y + (bounds.height - rect.height) / 2;
		shell.setLocation(x, y);

		// 打开shell，开始事件循环
		shell.layout();
		shell.open();

		// 开始事件循环
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch())
				display.sleep();
		}
	}

	/**
	 * 设置控制标题
	 */
	public void initControlText() {
		shellRegistry.closeAll();
		this.show();
		setTitle(SystemConfig.getInstance().getSystemTitle());
		roomLb.setText(room);
		safetyLb.setText(safety);
		equipLb.setText(equip);
		infoLb.setText(info);
		smartLb.setText(smart);
		sceneLb.setText(scene);
		setLb.setText(system);
		helpLb.setText(help);
		item.setToolTipText(SystemConfig.getInstance().getSystemTitle());
	}

	/**
	 * 关闭主shell
	 */
	public void close() {
		if (shell != null && !shell.isDisposed())
			shell.close();
	}

	public void logout() {
		SystemConfig.getInstance().setClosed(true);
		shellRegistry.closeAll();

		try {
			DBConnection.freeConnection();
			FileTool.deleteFile(KC868.CONFIG_DIR + SystemConfig.getInstance().getDbLockFileName());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 显示shell
	 */
	public void show() {
		shell.setLocation(SystemConfig.getInstance().getMainPoint());
		shell.setVisible(true);
		shell.setActive();
	}

	/**
	 * 隐藏shell
	 */
	public void hide() {
		shell.setVisible(false);
		SystemConfig.getInstance().setMainPoint(shell.getLocation());
	}

	/**
	 * 设置标题条
	 * 
	 * @param string
	 */
	public void setTitle(String string) {
		shell.setText(string);
		BorderStyler styler = (BorderStyler) shell.getData(BorderStyler.STYLER);
		styler.repaintTitleBar();
	}

	/**
	 * 添加程序图标到系统托盘中
	 */
	private void initTray() {
		// 添加Tray Icon
		Tray tray = display.getSystemTray();
		if (tray == null)
			return;
		item = new TrayItem(tray, SWT.NONE);
		// 设置图标
		item.setImage(icons.getImage(IconHolder.icoHome));
		// 添加listener
		// 鼠标单击事件
		item.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event event) {
				if (shell.getMinimized()) {
					shell.setVisible(true);
					shell.setMinimized(false);
				} else {
					shell.setMinimized(true);
				}
			}
		});
		// 鼠标右键点击事件
		item.addListener(SWT.MenuDetect, new Listener() {
			public void handleEvent(Event event) {
				if (menuHelper.isSystemMenuVisible())
					menuHelper.setSystemMenuVisible(false);
				else {
					menuHelper.setSystemMenuData(1);
					menuHelper.setSystemMenuLocation(display
							.getCursorLocation());
					menuHelper.setSystemMenuVisible(true);
				}
			}
		});

		item.setToolTipText(SystemConfig.getInstance().getSystemTitle());
	}

	/**
	 * 更换用户
	 */
	public void changeUser() {
		changeUser = true;
		close();
	}

	public Shell getShell() {
		return shell;
	}

	public Display getDisplay() {
		return display;
	}

	public KC868Client getClient() {
		return client;
	}

	public void setClient(KC868Client client) {
		this.client = client;
	}

	public ShellRegistry getShellRegistry() {
		return shellRegistry;
	}

	public ShellLauncher getShellLauncher() {
		return shellLauncher;
	}

	public boolean isChangeUser() {
		return changeUser;
	}

	public void setCenterTitle() {

		String title1 = StringUtil.substring(SystemConfig.getInstance()
				.getCenterTitle(), 0, 8, "", "GBK");
		String title2 = StringUtil.substring(SystemConfig.getInstance()
				.getCenterTitle(), StringUtil.length(title1), StringUtil.length(title1) + 13, "", "GBK");
		String title3 = StringUtil.substring(SystemConfig.getInstance()
				.getCenterTitle(), StringUtil.length(title1 + title2), StringUtil.length(title1 + title2) + 8, "", "GBK");
		if(welcomeLb != null && !welcomeLb.isDisposed()){
			welcomeLb.setText(title1);
			smartHomeLb.setText(title2);
			conHostLb.setText(title3);
		}
	}

	/**
	 * 数据库连接
	 * 
	 * @return
	 */
	public Connection getConnection() {
		return conn;
	}

	/**
	 * Launch the application.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		MainShell mainShell = new MainShell();
		mainShell.open();
	}

}
