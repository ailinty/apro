package com.joeysoft.kc868.ui.helper;

import static com.joeysoft.kc868.resource.Messages.*;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

import com.joeysoft.kc868.KC868;
import com.joeysoft.kc868.resource.IconHolder;
import com.joeysoft.kc868.resource.Messages;
import com.joeysoft.kc868.ui.MainShell;
import com.joeysoft.kc868.widgets.menu.CMenu;
import com.joeysoft.kc868.widgets.menu.CMenuItem;
import com.joeysoft.kc868.widgets.menu.ISelectionListener;
import com.joeysoft.kc868.widgets.menu.SelectionEvent;

/**
 * 包含一些菜单初始化的方法和工具方法
 * 
 * @author Joey
 */
public class MenuHelper {
    private MainShell main;
    private ShellLauncher shellLauncher;
    private IconHolder icons;
   // private ExportHelper exportHelper;
    private Shell shell;
    private Display display;
    
    private CMenu sysMenu, languageMenu;
    
    public MenuHelper(MainShell main) {
        this.main = main;
        shellLauncher = main.getShellLauncher();
        icons = IconHolder.getInstance();
        shell = main.getShell();
        display = main.getDisplay();
    }    

	/**
	 * 创建所有菜单
	 */
	public void initMenu() {
		initLanguageMenu();
		initSysMenu();
	}

	/**
	 * 初始化系统菜单
	 */
	private void initSysMenu() {
		// 系统弹出菜单
		sysMenu = new CMenu();
		// 更换用户
		CMenuItem mi = new CMenuItem(sysMenu, SWT.PUSH);
		mi.setText(change_user);
		mi.setImage(icons.getImage(IconHolder.icoChangeUser));
		mi.addSelectionListener(new ISelectionListener() {
			public void widgetSelected(SelectionEvent e)  {
				main.changeUser();
			}
		});
		// 语言菜单
		final CMenuItem miStatus = new CMenuItem(sysMenu, SWT.CASCADE);
		miStatus.setText(change_language);
		miStatus.setImage(icons.getImage(IconHolder.icoLanguage));
		miStatus.setMenu(languageMenu);
				
		// 关于
		mi = new CMenuItem(sysMenu, SWT.PUSH);
		mi.setText(about);
		mi.setImage(icons.getImage(IconHolder.icoAbout));
		mi.addSelectionListener(new ISelectionListener() {
			public void widgetSelected(SelectionEvent e) {
				//AboutDialog ad = new AboutDialog(shell);
				//ad.open();
			}
		});
		// separator
    	final CMenuItem miRobotSeparator = new CMenuItem(sysMenu, SWT.SEPARATOR);        	
		// 退出
		mi = new CMenuItem(sysMenu, SWT.PUSH);
		mi.setText(exit);
		mi.setImage(icons.getImage(IconHolder.icoExit));
		mi.addSelectionListener(new ISelectionListener() {
			public void widgetSelected(SelectionEvent e) {
			    main.close();
			}
		});
	}
	
	/**
	 * 初始化语言菜单
	 */
	public void initLanguageMenu(){
		// 语言弹出菜单
		languageMenu = new CMenu();
		// 简体
		CMenuItem mi = new CMenuItem(languageMenu, SWT.PUSH);
		mi.setText(language_zh);
		//mi.setImage(icons.getImage(IconHolder.icoOnline));
		mi.addSelectionListener(new ISelectionListener() {
			public void widgetSelected(SelectionEvent e) {
				KC868.language = "zh";
				Messages.initialize();
				main.initControlText();
			}
		});
		// separator
		new CMenuItem(languageMenu, SWT.SEPARATOR);
		// 英文
		mi = new CMenuItem(languageMenu, SWT.PUSH);
		mi.setText(language_en);
		//mi.setImage(icons.getImage(IconHolder.icoHidden));
		mi.addSelectionListener(new ISelectionListener() {
			public void widgetSelected(SelectionEvent e) {
				KC868.language = "en";
				Messages.initialize();
				main.initControlText();
			}
		});
		// separator
		new CMenuItem(languageMenu, SWT.SEPARATOR);
		// 繁体
		mi = new CMenuItem(languageMenu, SWT.PUSH);
		mi.setText(language_tw);
		//mi.setImage(icons.getImage(IconHolder.icoOffline));
		mi.addSelectionListener(new ISelectionListener() {
			public void widgetSelected(SelectionEvent e) {
				KC868.language = "tw";
				Messages.initialize();
				main.initControlText();
			}
		});
	}
    
    /**
     * 设置系统菜单的可见性
     * 
     * @param b
     */
    public void setSystemMenuVisible(boolean b) {
    	hideAllMenu();
        sysMenu.setVisible(b);
    }
    
    /**
     * 设置系统菜单的data
     * 
     * @param data
     */
    public void setSystemMenuData(Object data) {
    	sysMenu.setData(data);
    }
    
    /**
     * @return
     * 		true如果系统菜单可见
     */
    public boolean isSystemMenuVisible() {
    	return sysMenu.isVisible();
    }
    
    public void setSystemMenuLocation(Point loc) {
    	sysMenu.setLocation(loc);
    }
    
    public void setSystemMenuLocation(int x, int y) {
    	sysMenu.setLocation(x, y);
    }
	
	/**
	 * 隐藏所有菜单
	 * 没办法，在MagicLinux 1.2下测试的时候，菜单居然出来了之后无法消失
	 * 只好加了这么一个方法
	 */
	public void hideAllMenu() {
		sysMenu.setVisible(false);
	}
}
