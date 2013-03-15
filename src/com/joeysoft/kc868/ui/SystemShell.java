package com.joeysoft.kc868.ui;

import static com.joeysoft.kc868.resource.Messages.*;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CCombo;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseTrackAdapter;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.events.ShellAdapter;
import org.eclipse.swt.events.ShellEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Pattern;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.ProgressBar;
import org.eclipse.swt.widgets.ScrollBar;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Tray;
import org.eclipse.swt.widgets.TrayItem;
import org.eclipse.swt.widgets.Tree;

import com.joeysoft.kc868.KC868;
import com.joeysoft.kc868.SystemConfig;
import com.joeysoft.kc868.client.KC868Client;
import com.joeysoft.kc868.client.event.IPacketListener;
import com.joeysoft.kc868.client.event.PacketEvent;
import com.joeysoft.kc868.client.packets.ErrorPacket;
import com.joeysoft.kc868.client.packets.InPacket;
import com.joeysoft.kc868.client.packets.OutPacket;
import com.joeysoft.kc868.client.packets.in.ReadNowReplyPacket;
import com.joeysoft.kc868.client.packets.in.SetPasswordReplyPacket;
import com.joeysoft.kc868.client.packets.in.file.FileReadEndReplyPacket;
import com.joeysoft.kc868.client.packets.in.file.FileReadHeadReplyPacket;
import com.joeysoft.kc868.client.packets.in.file.FileReadReplyPacket;
import com.joeysoft.kc868.client.packets.in.file.FileWriteEndReplyPacket;
import com.joeysoft.kc868.client.packets.in.file.FileWriteErrorReplyPacket;
import com.joeysoft.kc868.client.packets.in.file.FileWriteOKReplyPacket;
import com.joeysoft.kc868.client.packets.in.file.FileWriteStartReplyPacket;
import com.joeysoft.kc868.client.packets.out.AlarmWritePacket;
import com.joeysoft.kc868.client.packets.out.GSMWriteCmpPacket;
import com.joeysoft.kc868.client.packets.out.GSMWriteSmsPacket;
import com.joeysoft.kc868.client.packets.out.GetPasswordHostPacket;
import com.joeysoft.kc868.client.packets.out.HostReSetPacket;
import com.joeysoft.kc868.client.packets.out.ReadNowPacket;
import com.joeysoft.kc868.client.packets.out.ResetHostPacket;
import com.joeysoft.kc868.client.packets.out.RestorHandEndPacket;
import com.joeysoft.kc868.client.packets.out.RestorHandStartPacket;
import com.joeysoft.kc868.client.packets.out.SetIPPacket;
import com.joeysoft.kc868.client.packets.out.SetPasswordAdminPacket;
import com.joeysoft.kc868.client.packets.out.SetPasswordHostPacket;
import com.joeysoft.kc868.client.packets.out.SetWarningOffPacket;
import com.joeysoft.kc868.client.packets.out.SetWarningOnPacket;
import com.joeysoft.kc868.client.packets.out.TelephoneWriteCmpPacket;
import com.joeysoft.kc868.client.packets.out.TelephoneWriteUserPacket;
import com.joeysoft.kc868.client.packets.out.WriteTimePacket;
import com.joeysoft.kc868.client.packets.out.event.EventConnectPacket;
import com.joeysoft.kc868.client.packets.out.event.EventDelayPacket;
import com.joeysoft.kc868.client.packets.out.event.EventDisConnectPacket;
import com.joeysoft.kc868.client.packets.out.event.EventWritePacket;
import com.joeysoft.kc868.client.packets.out.file.FileReadHeadPacket;
import com.joeysoft.kc868.client.packets.out.file.FileReadNewPacket;
import com.joeysoft.kc868.client.packets.out.file.FileReadStartPacket;
import com.joeysoft.kc868.client.packets.out.file.FileWriteHeadPacket;
import com.joeysoft.kc868.client.packets.out.file.FileWritePacket;
import com.joeysoft.kc868.client.packets.util.SensorPacketUtil;
import com.joeysoft.kc868.client.support.PacketProcessor;
import com.joeysoft.kc868.client.support.Protocol;
import com.joeysoft.kc868.client.util.FileSegmentor;
import com.joeysoft.kc868.client.util.FileTool;
import com.joeysoft.kc868.common.GlobalConst;
import com.joeysoft.kc868.common.GlobalMethod;
import com.joeysoft.kc868.common.StringUtil;
import com.joeysoft.kc868.common.ZipUtil;
import com.joeysoft.kc868.db.DBConnection;
import com.joeysoft.kc868.db.bean.Alarm;
import com.joeysoft.kc868.db.bean.Device;
import com.joeysoft.kc868.db.bean.DeviceKey;
import com.joeysoft.kc868.db.bean.Floor;
import com.joeysoft.kc868.db.bean.JSonFileWrite;
import com.joeysoft.kc868.db.bean.Lineate;
import com.joeysoft.kc868.db.bean.Relay;
import com.joeysoft.kc868.db.bean.Room;
import com.joeysoft.kc868.db.bean.Scene;
import com.joeysoft.kc868.db.bean.SceneAction;
import com.joeysoft.kc868.db.bean.SceneBind;
import com.joeysoft.kc868.db.bean.SensorNor;
import com.joeysoft.kc868.db.bean.SensorOut;
import com.joeysoft.kc868.db.bean.SensorIn;
import com.joeysoft.kc868.db.bean.SetRestart;
import com.joeysoft.kc868.db.bean.SmsIn;
import com.joeysoft.kc868.db.bean.SmsOut;
import com.joeysoft.kc868.db.bean.TelIn;
import com.joeysoft.kc868.db.bean.TelOut;
import com.joeysoft.kc868.db.bean.TempSensor;
import com.joeysoft.kc868.db.bean.Transfer;
import com.joeysoft.kc868.db.bean.TsfSensor;
import com.joeysoft.kc868.db.bo.BOAlarm;
import com.joeysoft.kc868.db.bo.BOClear;
import com.joeysoft.kc868.db.bo.BODevice;
import com.joeysoft.kc868.db.bo.BODeviceKey;
import com.joeysoft.kc868.db.bo.BOFloor;
import com.joeysoft.kc868.db.bo.BOLineate;
import com.joeysoft.kc868.db.bo.BORelay;
import com.joeysoft.kc868.db.bo.BORoom;
import com.joeysoft.kc868.db.bo.BOScene;
import com.joeysoft.kc868.db.bo.BOSceneAction;
import com.joeysoft.kc868.db.bo.BOSceneBind;
import com.joeysoft.kc868.db.bo.BOSensorIn;
import com.joeysoft.kc868.db.bo.BOSensorNor;
import com.joeysoft.kc868.db.bo.BOSensorOut;
import com.joeysoft.kc868.db.bo.BOSetRestart;
import com.joeysoft.kc868.db.bo.BOSmsIn;
import com.joeysoft.kc868.db.bo.BOSmsOut;
import com.joeysoft.kc868.db.bo.BOSysConfig;
import com.joeysoft.kc868.db.bo.BOTelIn;
import com.joeysoft.kc868.db.bo.BOTelOut;
import com.joeysoft.kc868.db.bo.BOTempSensor;
import com.joeysoft.kc868.db.bo.BOTransfer;
import com.joeysoft.kc868.db.bo.BOTransferSensor;
import com.joeysoft.kc868.db.util.DictConst;
import com.joeysoft.kc868.resource.IconHolder;
import com.joeysoft.kc868.resource.IconUtil;
import com.joeysoft.kc868.ui.dialogs.InformationDialog;
import com.joeysoft.kc868.ui.dialogs.helper.TableDeviceHelper;
import com.joeysoft.kc868.ui.dialogs.helper.TableFloorHelper;
import com.joeysoft.kc868.ui.dialogs.helper.TableLineateHelper;
import com.joeysoft.kc868.ui.dialogs.helper.TableRelayHelper;
import com.joeysoft.kc868.ui.dialogs.helper.TableRoomHelper;
import com.joeysoft.kc868.ui.dialogs.helper.TableSensorInHelper;
import com.joeysoft.kc868.ui.dialogs.helper.TableSensorOutHelper;
import com.joeysoft.kc868.ui.dialogs.helper.TableTempSensorHelper;
import com.joeysoft.kc868.ui.dialogs.helper.TableTransferHelper;
import com.joeysoft.kc868.ui.dialogs.helper.UIDeviceHelper;
import com.joeysoft.kc868.ui.dialogs.helper.UILineateHelper;
import com.joeysoft.kc868.ui.dialogs.helper.UIRelayHelper;
import com.joeysoft.kc868.ui.helper.MessageBoxHelper;
import com.joeysoft.kc868.ui.helper.ShellLauncher;
import com.joeysoft.kc868.ui.helper.ShellRegistry;
import com.joeysoft.kc868.ui.helper.UITool;
import com.joeysoft.kc868.widgets.AlphaComposite;
import com.joeysoft.kc868.widgets.ImageButton;

/**
 * 系统设置
 * 
 * @author JOEY
 * 
 */
public class SystemShell implements IPacketListener {

	private Shell shell;
	private Display display;

	// 所有控件
	private ImageButton btnBack;

	// 楼层面板
	private ScrolledComposite scTab;
	private Composite compTab;

	private Composite compPage;

	private IconHolder icons = IconHolder.getInstance();

	private PacketProcessor packetProcessor;

	private MainShell main;

	private Text textYear, textMonth, textDay, textHour, textMinute,
			textSecond;
	
	private Label lbYear, lbMonth, lbDay, lbHour, lbMinute, lbSecond;

	private Text textAdminOrgPwd, textAdminPwd, textAdminPwdCheck, textOrgPwd,
			textPwd, textPwdCheck, textIp, textMask, textGate;

	private Text textSystemTitle, textCenterTitle;
	private Label lbSystemTitleLen, lbCenterTitleLen;

	private ImageButton btnEdit, btnWrite, btnBackup, btnRevert, btnReset, btnConnect;
	
	private Button checkAlarm, checkSun, checkMon, checkTues, checkWed, checkThurs, checkFrid, checkSat;
	private CCombo comboHour, comboMinute, comboSecond;

	private boolean isNew = true;
	
	private FileSegmentor fileSegmentor;
	private int nextSegment, segmentCount;
	private int fileSize;
	private byte[] buffer, bufferP315In, bufferP433In;
	// 需要读出的文件当前index, 需要读出的文件总数
	private int writeFileIndex = 1, writeFileCount = 3;
	private int offset;
	// 进度条
	private int percent;
	private String writeFileName;
	// 需要写入的文件当前index, 需要写入的文件总数
	private int syncFileIndex = 1, syncFileCount = 3;
	
	private String filePath;
	
	// 当前操作是备份还是还原 1：备份 2：还原
	private int iBackupOrRestore = 0;

	private ProgressBar bar;

	private BOClear boClear;

	private BOSysConfig boSysConfig;

	private int index = 0;

	private BOSensorOut boSensorOut;
	private BOSensorNor boSensorNor;
	private List<Object> sensorList;

	private BOTransferSensor boTransferSensor;
	private List<TsfSensor> tsfSensorList;

	private BOSensorIn boSensorIn;
	private List<SensorIn> sensorInList;

	private BOTelIn boTelIn;
	private List<TelIn> telInList;

	private BOTelOut boTelOut;
	private List<TelOut> telOutList;

	private BOSmsIn boSmsIn;
	private List<SmsIn> smsInList;

	private BOSmsOut boSmsOut;
	private List<SmsOut> smsOutList;

	private BOScene boScene;
	private List<Scene> sceneList;
	private List<SceneBind> sceneBindList;

	private BOSceneBind boSceneBind;
	private BOSceneAction boSceneAction;

	private BOAlarm boAlarm;
	private List<Alarm> alarmList;

	private BOSetRestart boSetRestart;
	
	private InformationDialog informationDialog;
	
	private boolean isSetAdminPwd;
	
	private boolean isFileRead;
	
	private String message;
	
	// 现在在同步哪个sensorWrite 0 为sensorOut, 1 为tsfSensor
	private int sensorWrite = 0;

	public SystemShell(MainShell main) {
		this.main = main;
		this.packetProcessor = main.getClient().getPacketProcessor();
		this.display = main.getDisplay();
		boClear = new BOClear();
		boSysConfig = new BOSysConfig();
		boSensorOut = new BOSensorOut();
		boSensorNor = new BOSensorNor();
		boTransferSensor = new BOTransferSensor();
		boSensorIn = new BOSensorIn();
		boTelIn = new BOTelIn();
		boTelOut = new BOTelOut();
		boSmsIn = new BOSmsIn();
		boSmsOut = new BOSmsOut();
		boScene = new BOScene();
		boSceneBind = new BOSceneBind();
		boSceneAction = new BOSceneAction();
		boAlarm = new BOAlarm();
		boSetRestart = new BOSetRestart();
		initialize();
	}

	/**
	 * 初始化
	 */
	private void initialize() {

		initLayout();
		initListeners();

		hookListener();
	}

	/**
	 * 初始化界面布局
	 */
	private void initLayout() {
		Composite body = initShell();
		// body.setLayout(new FillLayout());
		// 设置背景图片
		body.setBackgroundImage(icons.getImage(IconHolder.bmpBgSub));
		body.setBackgroundMode(SWT.INHERIT_FORCE);
		body.setBackground(Colors.LOGIN_BACKGROUND);

		Label label = UITool.createLabel(body, system);
		label.setBounds(370, 27, 74, 40);
		label.setFont(Colors.LOADING_FONT);

		// 返回 按钮
		btnBack = UITool.createImageButton(body, button_back,
				icons.getImage(IconHolder.btnBack),
				icons.getImage(IconHolder.btnBackOn),
				icons.getImage(IconHolder.btnBackOn));
		btnBack.setFont(Colors.GLOBAL_FONT);
		btnBack.setBounds(702, 27, 77, 36);
		btnBack.setTextColor(Colors.BLACK);

		scTab = new ScrolledComposite(body, SWT.NONE);
		scTab.setBounds(32, 82, 728, 70);
		compTab = new Composite(scTab, SWT.NONE);
		scTab.setContent(compTab);
		compTab.setBounds(0, 0, 728, 70);

		compPage = new Composite(body, SWT.NONE);
		compPage.setBounds(34, 150, 730, 420);

		// 修改密码
		final ImageButton ibChangePwd = UITool.createImageButton(compTab,
				change_pwd, icons.getImage(IconHolder.btnSubMenuOff), null, null);
		ibChangePwd.setBounds(0 * 104, 0, 100, 43);
		ibChangePwd.addMouseListener(new MouseAdapter() {
			public void mouseUp(MouseEvent e) {
				openChangePwdTab();
				ibChangePwd.setImage(icons.getImage(IconHolder.btnSubMenu));
			}
		});

		// 网络设置
		/*final ImageButton ibSetNetwork = UITool.createImageButton(compTab,
				"网络设置", icons.getImage(IconHolder.btnSubMenuOff), null, null);
		ibSetNetwork.setBounds(1 * 104, 0, 100, 43);
		ibSetNetwork.addMouseListener(new MouseAdapter() {
			public void mouseUp(MouseEvent e) {
				openChangeNetTab();
				ibSetNetwork.setImage(icons.getImage(IconHolder.btnSubMenu));
			}
		});*/

		// 数据同步
		final ImageButton ibSync = UITool.createImageButton(compTab, login_sync,
				icons.getImage(IconHolder.btnSubMenuOff), null, null);
		ibSync.setBounds(1 * 104, 0, 100, 43);
		ibSync.addMouseListener(new MouseAdapter() {
			public void mouseUp(MouseEvent e) {
				openSyncTab();
				ibSync.setImage(icons.getImage(IconHolder.btnSubMenu));
			}
		});

		// 时间设置
		final ImageButton ibSetTime = UITool.createImageButton(compTab, system_set,
				icons.getImage(IconHolder.btnSubMenuOff), null, null);
		ibSetTime.setBounds(2 * 104, 0, 100, 43);
		ibSetTime.addMouseListener(new MouseAdapter() {
			public void mouseUp(MouseEvent e) {
				sendReadNow();
				openSetTimeTab();
				ibSetTime.setImage(icons.getImage(IconHolder.btnSubMenu));
			}
		});

		// 恢复默认
		final ImageButton ibResume = UITool.createImageButton(compTab, resume_default,
				icons.getImage(IconHolder.btnSubMenuOff), null, null);
		ibResume.setBounds(3 * 104, 0, 100, 43);
		ibResume.addMouseListener(new MouseAdapter() {
			public void mouseUp(MouseEvent e) {
				openClearTab();
				ibResume.setImage(icons.getImage(IconHolder.btnSubMenu));
			}
		});

		openChangePwdTab();
		ibChangePwd.setImage(icons.getImage(IconHolder.btnSubMenu));
	}

	private void openSetTimeTab() {
		compPage.setVisible(false);
		setTabOff();
		clearComposite(compPage);
		
		initHostTime();
		if(SystemConfig.getInstance().isHardSoftVer2131()){
			initSetRestart();
		}
		
		//-------------------------------------------标题
		initSystemTitle();

		compPage.setVisible(true);
	}
	
	/**
	 * 设置主机时间
	 */
	private void initHostTime(){
		int hostTimeLableH = 40;
		
		Label label = UITool.createLabel(compPage, "");
    	label.setImage(icons.getImage(IconHolder.bmpHistory));
		label.setBounds(50, 20, 48, 48);
		
		label = UITool.createLabel(compPage, time);
		label.setBounds(140, hostTimeLableH, 50, 20);
		
		lbYear = UITool.createLabel(compPage, "");
		lbYear.setBounds(200, hostTimeLableH, 30, 20);
		
		label = UITool.createLabel(compPage, year);
		label.setBounds(240, hostTimeLableH, 20, 20);
		
		lbMonth = UITool.createLabel(compPage, "");
		lbMonth.setBounds(260, hostTimeLableH, 20, 20);
		
		label = UITool.createLabel(compPage, month);
		label.setBounds(290, hostTimeLableH, 20, 20);
		
		lbDay = UITool.createLabel(compPage, "");
		lbDay.setBounds(310, hostTimeLableH, 20, 20);
		
		label = UITool.createLabel(compPage, week_7);
		label.setBounds(340, hostTimeLableH, 20, 20);
		
		lbHour = UITool.createLabel(compPage, "");
		lbHour.setBounds(370, hostTimeLableH, 20, 20);
		
		label = UITool.createLabel(compPage, time_h);
		label.setBounds(400, hostTimeLableH, 20, 20);
		
		lbMinute = UITool.createLabel(compPage, "");
		lbMinute.setBounds(430, hostTimeLableH, 20, 20);
		
		label = UITool.createLabel(compPage, time_m);
		label.setBounds(460, hostTimeLableH, 20, 20);
		
		lbSecond = UITool.createLabel(compPage, "");
		lbSecond.setBounds(490, hostTimeLableH, 20, 20);
		label = UITool.createLabel(compPage, time_s);
		label.setBounds(520, hostTimeLableH, 20, 20);

		int setTimeLableH = 100;
		
		label = UITool.createLabel(compPage, "");
    	label.setImage(icons.getImage(IconHolder.bmpFileTemp));
		label.setBounds(50, 80, 48, 48);
		
		label = UITool.createLabel(compPage, set_time);
		label.setBounds(140, setTimeLableH, 50, 20);

		textYear = UITool.createSingleText(compPage, SWT.SINGLE);
		textYear.setBackground(Colors.WHITE);
		textYear.setBounds(200, setTimeLableH, 30, 20);

		label = UITool.createLabel(compPage, year);
		label.setBounds(240, setTimeLableH, 20, 20);

		textMonth = UITool.createSingleText(compPage, SWT.SINGLE);
		
		textMonth.setBackground(Colors.WHITE);
		textMonth.setBounds(260, setTimeLableH, 20, 20);

		label = UITool.createLabel(compPage, month);
		label.setBounds(290, setTimeLableH, 20, 20);

		textDay = UITool.createSingleText(compPage, SWT.SINGLE);
		textDay.setBackground(Colors.WHITE);
		textDay.setBounds(310, setTimeLableH, 20, 20);

		label = UITool.createLabel(compPage, week_7);
		label.setBounds(340, setTimeLableH, 20, 20);

		textHour = UITool.createSingleText(compPage, SWT.SINGLE);
		textHour.setBackground(Colors.WHITE);
		textHour.setBounds(370, setTimeLableH, 20, 20);

		label = UITool.createLabel(compPage, time_h);
		label.setBounds(400, setTimeLableH, 20, 20);

		textMinute = UITool.createSingleText(compPage, SWT.SINGLE);
		textMinute.setBackground(Colors.WHITE);
		textMinute.setBounds(430, setTimeLableH, 20, 20);

		label = UITool.createLabel(compPage, time_m);
		label.setBounds(460, setTimeLableH, 20, 20);

		textSecond = UITool.createSingleText(compPage, SWT.SINGLE);
		textSecond.setBackground(Colors.WHITE);
		textSecond.setBounds(490, setTimeLableH, 20, 20);

		label = UITool.createLabel(compPage, time_s);
		label.setBounds(520, setTimeLableH, 20, 20);

		btnEdit = UITool.createImageButton(compPage, button_edit,
				icons.getImage(IconHolder.bmpBtnLogin),
				icons.getImage(IconHolder.bmpBtnLoginUp),
				icons.getImage(IconHolder.bmpBtnLoginOn));
		btnEdit.setFont(Colors.GLOBAL_FONT);
		btnEdit.setBounds(608, 90, 114, 41);
		btnEdit.addMouseListener(new MouseAdapter() {
			public void mouseUp(MouseEvent e) {
				sendWriteTime();
			}
		});
	}
	
	/**
	 * 设置自动重启时间
	 */
	private void initSetRestart(){
		int restartTimeLableH = 160;
		int restartTimeLableX = 150;
		
		Label label = UITool.createLabel(compPage, "");
    	label.setImage(icons.getImage(IconHolder.bmpFileTemp));
		label.setBounds(50, 140, 48, 48);
		
		label = UITool.createLabel(compPage, system_set_startup_time);
		label.setBounds(140, 200, 150, 20);
		
		btnEdit = UITool.createImageButton(compPage, set,
				icons.getImage(IconHolder.bmpBtnLogin),
				icons.getImage(IconHolder.bmpBtnLoginUp),
				icons.getImage(IconHolder.bmpBtnLoginOn));
		btnEdit.setFont(Colors.GLOBAL_FONT);
		btnEdit.setBounds(608, 190, 114, 41);
		btnEdit.addMouseListener(new MouseAdapter() {
			public void mouseUp(MouseEvent e) {
				setRestart();
			}
		});
		
		label = UITool.createLabel(compPage, week);
		label.setBounds(restartTimeLableX - 10, restartTimeLableH + 4, 50, 20);
		
		checkSun = UITool.createCheckbox(compPage, week_7);
		checkSun.setBounds(restartTimeLableX + 40, restartTimeLableH, 30, 20);

		checkMon = UITool.createCheckbox(compPage, week_1);
		checkMon.setBounds(restartTimeLableX + 40 * 2, restartTimeLableH, 30, 20);
		
		checkTues = UITool.createCheckbox(compPage, week_2);
		checkTues.setBounds(restartTimeLableX + 40 * 3, restartTimeLableH, 30, 20);
		
		checkWed = UITool.createCheckbox(compPage, week_3);
		checkWed.setBounds(restartTimeLableX + 40 * 4, restartTimeLableH, 30, 20);
		
		checkThurs = UITool.createCheckbox(compPage, week_4);
		checkThurs.setBounds(restartTimeLableX + 40 * 5, restartTimeLableH, 30, 20);
		
		checkFrid = UITool.createCheckbox(compPage, week_5);
		checkFrid.setBounds(restartTimeLableX + 40 * 6, restartTimeLableH, 30, 20);
		
		checkSat = UITool.createCheckbox(compPage, week_6);
		checkSat.setBounds(restartTimeLableX + 40 * 7, restartTimeLableH, 30, 20);
		
		label = UITool.createLabel(compPage, time);
		label.setBounds(restartTimeLableX + 40 * 8, restartTimeLableH + 4, 40, 20);
		label.setForeground(Colors.BLACK);
		String[] items = new String[24];
		for(int h=0; h<24; h++){
			items[h] = String.valueOf(h);
		}
		
		comboHour = UITool.createCCombo(compPage, SWT.SIMPLE | SWT.READ_ONLY);
		comboHour.setBounds(restartTimeLableX + 40 * 9, restartTimeLableH, 40, 20);
		comboHour.setBackground(Colors.WHITE);
		comboHour.setItems(items);
		comboHour.select(0);
		comboHour.setEditable(false);
		
		label = UITool.createLabel(compPage, time_h);
		label.setBounds(restartTimeLableX + 40 * 10, restartTimeLableH + 4, 15, 20);
		label.setForeground(Colors.BLACK);
		
		items = new String[60];
		for(int h=0; h<60; h++){
			items[h] = String.valueOf(h);
		}
		
		comboMinute = UITool.createCCombo(compPage, SWT.SIMPLE | SWT.READ_ONLY);
		comboMinute.setBounds(restartTimeLableX + 40 * 11, restartTimeLableH, 40, 20);
		comboMinute.setBackground(Colors.WHITE);
		comboMinute.setItems(items);
		comboMinute.select(0);
		comboMinute.setEditable(false);
		
		label = UITool.createLabel(compPage, time_m);
		label.setBounds(restartTimeLableX + 40 * 12, restartTimeLableH + 4, 15, 20);
		label.setForeground(Colors.BLACK);
		
		comboSecond = UITool.createCCombo(compPage, SWT.SIMPLE | SWT.READ_ONLY);
		comboSecond.setBounds(restartTimeLableX + 40 * 13, restartTimeLableH, 40, 20);
		comboSecond.setBackground(Colors.WHITE);
		comboSecond.setItems(items);
		comboSecond.select(0);
		comboSecond.setEditable(false);
		
		label = UITool.createLabel(compPage, time_s);
		label.setBounds(restartTimeLableX + 40 * 14, restartTimeLableH + 4, 15, 20);
		label.setForeground(Colors.BLACK);
		
		// 初始化
		List<SetRestart> setRestartList = boSetRestart.getList();
		if(setRestartList != null && setRestartList.size() > 0){
			SetRestart setRestart = setRestartList.get(0);
			String strWeek = setRestart.getReWeek();
			if(StringUtils.isNotEmpty(strWeek)){
				int week = Integer.valueOf(strWeek);
			
				if((week&0x01) == 0x01){
					checkSun.setSelection(true);
				}
				if((week&0x02) == 0x02){
					checkMon.setSelection(true);
				}
				if((week&0x04) == 0x04){
					checkTues.setSelection(true);
				}
				if((week&0x08) == 0x08){
					checkWed.setSelection(true);
				}
				if((week&0x10) == 0x10){
					checkThurs.setSelection(true);
				}
				if((week&0x20) == 0x20){
					checkFrid.setSelection(true);
				}
				if((week&0x40) == 0x40){
					checkSat.setSelection(true);
				}
				
				comboHour.setText(setRestart.getReHour());
				comboMinute.setText(setRestart.getReMinute());
				comboSecond.setText(setRestart.getReSecond());
			}
		}
	}
	
	/**
	 * 标题
	 */
	private void initSystemTitle(){
		Label label = UITool.createLabel(compPage, "");
    	label.setImage(icons.getImage(IconHolder.bmpAppPreferences));
		label.setBounds(50, 280, 48, 48);
		
		label = UITool.createLabel(compPage, title);
		label.setBounds(140, 300, 50, 20);

		textSystemTitle = UITool.createSingleText(compPage, SWT.SINGLE);
		textSystemTitle.setBackground(Colors.WHITE);
		textSystemTitle.setBounds(200, 300, 200, 20);
		textSystemTitle.setText(SystemConfig.getInstance().getSystemTitle());

		label = UITool.createLabel(compPage, "");
    	label.setImage(icons.getImage(IconHolder.bmpAppLargeIcons));
		label.setBounds(50, 340, 48, 48);
		
		label = UITool.createLabel(compPage, ceter_title);
		label.setBounds(140, 360, 50, 20);
			
		textCenterTitle = UITool.createSingleText(compPage, SWT.SINGLE);
		textCenterTitle.setBackground(Colors.WHITE);
		textCenterTitle.setBounds(200, 360, 200, 20);
		textCenterTitle.setText(SystemConfig.getInstance().getCenterTitle());
		textCenterTitle.addModifyListener(new ModifyListener(){
			public void modifyText(ModifyEvent e) {
				lbCenterTitleLen.setText(""+StringUtil.length(textCenterTitle.getText()));
			}
		});
		
		lbCenterTitleLen = UITool.createLabel(compPage, "");
		lbCenterTitleLen.setBounds(450, 300, 200, 20);
		
		btnEdit = UITool.createImageButton(compPage, button_edit,
				icons.getImage(IconHolder.bmpBtnLogin),
				icons.getImage(IconHolder.bmpBtnLoginUp),
				icons.getImage(IconHolder.bmpBtnLoginOn));
		btnEdit.setFont(Colors.GLOBAL_FONT);
		btnEdit.setBounds(608, 320, 114, 41);
		btnEdit.addMouseListener(new MouseAdapter() {
			public void mouseUp(MouseEvent e) {
				setSystemTitle();
			}
		});
	}

	private void openClearTab() {
		compPage.setVisible(false);
		setTabOff();
		clearComposite(compPage);
		
		Label label = UITool.createLabel(compPage, "");
    	label.setImage(icons.getImage(IconHolder.bmpNew));
		label.setBounds(50, 80, 48, 48);
		
		label = UITool.createLabel(compPage, resume_default);
		label.setBounds(115, 80, 100, 20);
		label.setFont(Colors.GLOBAL_FONT);

		label = UITool.createLabel(compPage, system_set_clear);
		label.setBounds(115, 100, 480, 20); 
		label.setFont(Colors.GL_FONT);
		
		ImageButton btnClear = UITool.createImageButton(compPage, resume_default,
				icons.getImage(IconHolder.bmpBtnLogin),
				icons.getImage(IconHolder.bmpBtnLoginUp),
				icons.getImage(IconHolder.bmpBtnLoginOn));
		btnClear.setFont(Colors.GLOBAL_FONT);
		btnClear.setBounds(608, 80, 114, 41);
		btnClear.addMouseListener(new MouseAdapter() {
			public void mouseUp(MouseEvent e) {
				if (MessageBoxHelper.openQuestion(shell, system_set_clear_true)) {
					if (boClear.clearAllTable()) {
						MessageBoxHelper.openInformation(shell, system_set_clear_success);
					} else {
						MessageBoxHelper.openInformation(shell, system_set_clear_error);
					}
				}
			}
		});
		
		label = UITool.createLabel(compPage, "");
    	label.setImage(icons.getImage(IconHolder.bmpDelete));
		label.setBounds(50, 240, 48, 48);
		
		label = UITool.createLabel(compPage, system_set_clear_arp);
		label.setBounds(115, 240, 100, 20);
		label.setFont(Colors.GLOBAL_FONT);

		label = UITool.createLabel(compPage, system_set_clear_arp_info);
		label.setBounds(115, 260, 490, 20); 
		label.setFont(Colors.GL_FONT);

		ImageButton btnARP = UITool.createImageButton(compPage, system_set_clear_arp,
				icons.getImage(IconHolder.bmpBtnLogin),
				icons.getImage(IconHolder.bmpBtnLoginUp),
				icons.getImage(IconHolder.bmpBtnLoginOn));
		btnARP.setFont(Colors.GLOBAL_FONT);
		btnARP.setBounds(608, 240, 114, 41);
		btnARP.addMouseListener(new MouseAdapter() {
			public void mouseUp(MouseEvent e) {
				if (MessageBoxHelper.openQuestion(shell, system_set_clear_arp_true)) {
					try {
						Runtime.getRuntime().exec("arp -d");
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				}
			}
		});
		
		compPage.setVisible(true);
	}

	private void openARPTab() {
		compPage.setVisible(false);
		setTabOff();
		clearComposite(compPage);
		if (MessageBoxHelper.openQuestion(shell, system_set_clear_arp_true)) {
			try {
				Runtime.getRuntime().exec("arp -d");
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
		compPage.setVisible(true);
	}

	private void setSystemTitle() {
		String systemTitle = textSystemTitle.getText();
		if (StringUtils.isEmpty(systemTitle)) {
			MessageBoxHelper.openError(shell, system_set_validation_title_isEmpty);
			return;
		}
		boSysConfig.update("SYSTEM_TITLE", systemTitle);
		SystemConfig.getInstance().setSystemTitle(systemTitle);
		main.setTitle(systemTitle);
		shell.setText(systemTitle);
		
		String centerTitle = textCenterTitle.getText();
		if (StringUtils.isEmpty(centerTitle)) {
			textCenterTitle.forceFocus();
			MessageBoxHelper.openError(shell, system_set_validation_center_isEmpty);
			return;
		}
		int len = StringUtil.length(centerTitle);
		if(len != 28 && len != 29){
			textCenterTitle.forceFocus();
			MessageBoxHelper.openError(shell, system_set_validation_center_length);
			return;
		}
		boSysConfig.update("CENTER_TITLE", centerTitle);
		SystemConfig.getInstance().setCenterTitle(centerTitle);
		main.setCenterTitle();
		
		MessageBoxHelper.openInformation(shell, message_opreate_success);
	}

	private void openSyncTab() {
		compPage.setVisible(false);
		setTabOff();
		clearComposite(compPage);
		bar = new ProgressBar(compPage, SWT.NONE);
		bar.setMaximum(100);
		bar.setBounds(200, 0, 300, 20);

		Label label = UITool.createLabel(compPage, "");
    	label.setImage(icons.getImage(IconHolder.bmpFolder));
		label.setBounds(50, 30, 48, 48);
		
		label = UITool.createLabel(compPage, system_set_write_config);
		label.setBounds(115, 30, 100, 20);
		label.setFont(Colors.GLOBAL_FONT);

		label = UITool.createLabel(compPage, system_set_write_config_info);
		label.setBounds(115, 60, 480, 20); 
		label.setFont(Colors.GL_FONT);
		
		label = UITool.createLabel(compPage, system_set_write_config_info2);
		label.setBounds(115, 80, 600, 20); 
		label.setFont(Colors.GL_FONT);
		
		btnWrite = UITool.createImageButton(compPage, system_set_write_config,
				icons.getImage(IconHolder.bmpBtnLogin),
				icons.getImage(IconHolder.bmpBtnLoginUp),
				icons.getImage(IconHolder.bmpBtnLoginOn));
		btnWrite.setFont(Colors.GLOBAL_FONT);
		btnWrite.setBounds(608, 30, 114, 41);
		btnWrite.addMouseListener(new MouseAdapter() {
			public void mouseUp(MouseEvent e) {
				message = message_opreate_success;
				syncFileIndex = 1;
				JSonFileWrite.createJSonFile();
				try {
					ZipUtil.zip(KC868.CONFIG_DIR
							+ SystemConfig.getInstance().getDbFileName(),
							KC868.CONFIG_DIR + SystemConfig.getInstance()
											.getZipFileName());
					fileWrite(SystemConfig.getInstance().getJsonZipFileName());
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		});
		
		label = UITool.createLabel(compPage, "");
    	label.setImage(icons.getImage(IconHolder.bmpFolderDownloads));
		label.setBounds(50, 110, 48, 48);
		
		label = UITool.createLabel(compPage, system_set_backup_config);
		label.setBounds(115, 110, 100, 20);
		label.setFont(Colors.GLOBAL_FONT);

		label = UITool.createLabel(compPage, system_set_backup_config_info);
		label.setBounds(115, 140, 480, 20); 
		label.setFont(Colors.GL_FONT);
		
		btnBackup = UITool.createImageButton(compPage, system_set_backup_config,
				icons.getImage(IconHolder.bmpBtnLogin),
				icons.getImage(IconHolder.bmpBtnLoginUp),
				icons.getImage(IconHolder.bmpBtnLoginOn));
		btnBackup.setFont(Colors.GLOBAL_FONT);
		btnBackup.setBounds(608, 110, 114, 41);
		btnBackup.addMouseListener(new MouseAdapter() {
			public void mouseUp(MouseEvent e) {
				//sendGetPasswordForFileRead();
				doBackup();
			}
		});
		
		label = UITool.createLabel(compPage, "");
    	label.setImage(icons.getImage(IconHolder.bmpFolderUpload));
		label.setBounds(50, 190, 48, 48);
		
		label = UITool.createLabel(compPage, system_set_revert_config);
		label.setBounds(115, 190, 100, 20);
		label.setFont(Colors.GLOBAL_FONT);

		label = UITool.createLabel(compPage, system_set_revert_config_info);
		label.setBounds(115, 220, 480, 20); 
		label.setFont(Colors.GL_FONT);
		
		label = UITool.createLabel(compPage, system_set_revert_config_info2);
		label.setBounds(115, 240, 600, 20); 
		label.setFont(Colors.GL_FONT);
		
		btnRevert = UITool.createImageButton(compPage, system_set_revert_config,
				icons.getImage(IconHolder.bmpBtnLogin),
				icons.getImage(IconHolder.bmpBtnLoginUp),
				icons.getImage(IconHolder.bmpBtnLoginOn));
		btnRevert.setFont(Colors.GLOBAL_FONT);
		btnRevert.setBounds(608, 190, 114, 41);
		btnRevert.addMouseListener(new MouseAdapter() {
			public void mouseUp(MouseEvent e) {
				doRestore();
				//revertHost();
			}
		});
		
		label = UITool.createLabel(compPage, "");
    	label.setImage(icons.getImage(IconHolder.bmpInfo));
		label.setBounds(50, 270, 48, 48);
		
		label = UITool.createLabel(compPage, system_set_host_startup);
		label.setBounds(115, 270, 100, 20);
		label.setFont(Colors.GLOBAL_FONT);

		label = UITool.createLabel(compPage, system_set_host_startup_info);
		label.setBounds(115, 300, 480, 20); 
		label.setFont(Colors.GL_FONT);
		
		btnReset = UITool.createImageButton(compPage, system_set_host_startup,
				icons.getImage(IconHolder.bmpBtnLogin),
				icons.getImage(IconHolder.bmpBtnLoginUp),
				icons.getImage(IconHolder.bmpBtnLoginOn));
		btnReset.setFont(Colors.GLOBAL_FONT);
		btnReset.setBounds(608, 270, 114, 41);
		btnReset.addMouseListener(new MouseAdapter() {
			public void mouseUp(MouseEvent e) {
				sendResetHost();
				informationDialog = new InformationDialog(main);
				informationDialog.setText(system_set_host_resting);
				informationDialog.setTitleText(system_set_host_resting);
				informationDialog.open();
			}
		});
		
		label = UITool.createLabel(compPage, "");
    	label.setImage(icons.getImage(IconHolder.bmpApply));
		label.setBounds(50, 350, 48, 48);
		
		label = UITool.createLabel(compPage, system_set_host_reconnection);
		label.setBounds(115, 350, 100, 20);
		label.setFont(Colors.GLOBAL_FONT);

		label = UITool.createLabel(compPage, system_set_host_reconnection_info);
		label.setBounds(115, 380, 480, 20); 
		label.setFont(Colors.GL_FONT);
		
		btnConnect = UITool.createImageButton(compPage, system_set_host_reconnection,
				icons.getImage(IconHolder.bmpBtnLogin),
				icons.getImage(IconHolder.bmpBtnLoginUp),
				icons.getImage(IconHolder.bmpBtnLoginOn));
		btnConnect.setFont(Colors.GLOBAL_FONT);
		btnConnect.setBounds(608, 350, 114, 41);
		btnConnect.addMouseListener(new MouseAdapter() {
			public void mouseUp(MouseEvent e) {
				main.getClient().connectionClient();
			}
		});
		
		/*btnSync = UITool.createImageButton(compPage, "同步主机",
				icons.getImage(IconHolder.bmpBtnLogin),
				icons.getImage(IconHolder.bmpBtnLoginUp),
				icons.getImage(IconHolder.bmpBtnLoginOn));
		btnSync.setFont(Colors.GLOBAL_FONT);
		btnSync.setBounds(408, 350, 114, 41);
		btnSync.addMouseListener(new MouseAdapter() {
			public void mouseUp(MouseEvent e) {
				sendSensorOutAll();
			}
		});*/
		compPage.setVisible(true);
	}

	// 修改密码
	private void openChangePwdTab() {
		compPage.setVisible(false);
		setTabOff();
		clearComposite(compPage);

		Label label = UITool.createLabel(compPage, "");
    	label.setImage(icons.getImage(IconHolder.bmpUser));
		label.setBounds(60, 65, 48, 48);
		
		Group group = UITool.createGroup(compPage, system_set_normal_user_pwd);
		group.setBounds(130, 5, 460, 150);

		// 原密码
		label = UITool.createLabel(group, system_set_old_pwd);
		label.setBounds(20, 30, 84, 20);
		label.setFont(Colors.GLOBAL_FONT);
		// 密码框
		textOrgPwd = UITool.createSingleText(group, SWT.SINGLE | SWT.PASSWORD);
		textOrgPwd.setBackground(Colors.WHITE);
		textOrgPwd.setEchoChar('*');
		textOrgPwd.setBounds(120, 30, 176, 20);

		// 新密码
		label = UITool.createLabel(group, system_set_new_pwd);
		label.setBounds(20, 70, 84, 20);
		label.setFont(Colors.GLOBAL_FONT);
		// 密码框
		textPwd = UITool.createSingleText(group, SWT.SINGLE | SWT.PASSWORD);
		textPwd.setBackground(Colors.WHITE);
		textPwd.setEchoChar('*');
		textPwd.setBounds(120, 70, 176, 20);
		
		label = UITool.createLabel(group, system_set_validation_pwd_length);
		label.setBounds(310, 70, 100, 20);

		// 确认密码
		label = UITool.createLabel(group, system_set_pwd_check);
		label.setBounds(20, 110, 84, 20);
		label.setFont(Colors.GLOBAL_FONT);
		// 确认密码框
		textPwdCheck = UITool
				.createSingleText(group, SWT.SINGLE | SWT.PASSWORD);
		textPwdCheck.setBackground(Colors.WHITE);
		textPwdCheck.setEchoChar('*');
		textPwdCheck.setBounds(120, 110, 176, 20);
		
		btnEdit = UITool.createImageButton(compPage, button_edit,
				icons.getImage(IconHolder.bmpBtnLogin),
				icons.getImage(IconHolder.bmpBtnLoginUp),
				icons.getImage(IconHolder.bmpBtnLoginOn));
		btnEdit.setFont(Colors.GLOBAL_FONT);
		btnEdit.setBounds(608, 65, 114, 41);
		btnEdit.addMouseListener(new MouseAdapter() {
			public void mouseUp(MouseEvent e) {
				/*if (!textOrgPwd.getText().equals(
						SystemConfig.getInstance().getPassword())) {
					MessageBoxHelper.openError(shell, "主机原密码错误！");
					return;
				}*/
				if (StringUtils.isEmpty(textPwd.getText())) {
					MessageBoxHelper.openError(shell, system_set_validation_host_pwd_isEmpty);
					return;
				}
				if (textPwd.getText().length() != 8) {
					MessageBoxHelper.openError(shell, system_set_validation_host_pwd_length);
					return;
				}
				if (!textPwd.getText().equals(textPwdCheck.getText())) {
					MessageBoxHelper.openError(shell, system_set_validation_host_pwd_check);
					return;
				}
				isSetAdminPwd = false;
				sendSetPasswordHost();
			}
		});

		// ------------------------------------------管理员密码------------------------------------------
		
		label = UITool.createLabel(compPage, "");
    	label.setImage(icons.getImage(IconHolder.bmpUsers));
		label.setBounds(60, 265, 48, 48);
		
		group = UITool.createGroup(compPage, system_set_admin_pwd);
		group.setBounds(130, 210, 460, 150);

		// 密码
		label = UITool.createLabel(group, system_set_old_pwd);
		label.setBounds(20, 30, 84, 20);
		label.setFont(Colors.GLOBAL_FONT);
		// 密码框
		textAdminOrgPwd = UITool
				.createSingleText(group, SWT.SINGLE | SWT.PASSWORD);
		textAdminOrgPwd.setBackground(Colors.WHITE);
		textAdminOrgPwd.setEchoChar('*');
		textAdminOrgPwd.setBounds(120, 30, 176, 20);

		// 新密码
		label = UITool.createLabel(group, system_set_new_pwd);
		label.setBounds(20, 70, 84, 20);
		label.setFont(Colors.GLOBAL_FONT);
		// 新密码框
		textAdminPwd = UITool.createSingleText(group, SWT.SINGLE
				| SWT.PASSWORD);
		textAdminPwd.setBackground(Colors.WHITE);
		textAdminPwd.setEchoChar('*');
		textAdminPwd.setBounds(120, 70, 176, 20);
		
		label = UITool.createLabel(group, system_set_validation_pwd_length);
		label.setBounds(310, 70, 100, 20);

		// 确认密码
		label = UITool.createLabel(group, system_set_pwd_check);
		label.setBounds(20, 110, 84, 20);
		label.setFont(Colors.GLOBAL_FONT);
		// 确认密码框
		textAdminPwdCheck = UITool.createSingleText(group, SWT.SINGLE
				| SWT.PASSWORD);
		textAdminPwdCheck.setBackground(Colors.WHITE);
		textAdminPwdCheck.setEchoChar('*');
		textAdminPwdCheck.setBounds(120, 110, 176, 20);

		btnEdit = UITool.createImageButton(compPage, button_edit,
				icons.getImage(IconHolder.bmpBtnLogin),
				icons.getImage(IconHolder.bmpBtnLoginUp),
				icons.getImage(IconHolder.bmpBtnLoginOn));
		btnEdit.setFont(Colors.GLOBAL_FONT);
		btnEdit.setBounds(608, 265, 114, 41);
		btnEdit.addMouseListener(new MouseAdapter() {
			public void mouseUp(MouseEvent e) {
				if (!textAdminOrgPwd.getText().equals(
						SystemConfig.getInstance().getAdminPassword())) {
					MessageBoxHelper.openError(shell, system_set_validation_admin_pwd_error);
					return;
				}
				if (StringUtils.isEmpty(textAdminPwd.getText())) {
					MessageBoxHelper.openError(shell, system_set_validation_admin_pwd_isEmpty);
					return;
				}

				if (textAdminPwd.getText().length() != 8) {
					MessageBoxHelper.openError(shell, system_set_validation_admin_pwd_length);
					return;
				}

				if (!textAdminPwd.getText().equals(textAdminPwdCheck.getText())) {
					MessageBoxHelper.openError(shell, system_set_validation_admin_pwd_check);
					return;
				}
				isSetAdminPwd = true;
				sendSetPasswordAdmin();
			}
		});

		compPage.setVisible(true);
	}

	// 网络参数
	private void openChangeNetTab() {
		compPage.setVisible(false);
		setTabOff();
		clearComposite(compPage);

		// 服务器IP
		Label label = UITool.createLabel(compPage, "服务器IP");
		label.setBounds(200, 30, 84, 20);
		label.setFont(Colors.GLOBAL_FONT);
		// 服务器IP
		textIp = UITool.createSingleText(compPage, SWT.SINGLE);
		textIp.setBackground(Colors.WHITE);
		textIp.setBounds(300, 30, 176, 20);

		// 子网掩码
		label = UITool.createLabel(compPage, "子网掩码");
		label.setBounds(200, 70, 84, 20);
		label.setFont(Colors.GLOBAL_FONT);
		// 子网掩码
		textMask = UITool.createSingleText(compPage, SWT.SINGLE);
		textMask.setBackground(Colors.WHITE);
		textMask.setBounds(300, 70, 176, 20);

		// 网关
		label = UITool.createLabel(compPage, "网关");
		label.setBounds(200, 110, 84, 20);
		label.setFont(Colors.GLOBAL_FONT);
		// 网关
		textGate = UITool.createSingleText(compPage, SWT.SINGLE);
		textGate.setBackground(Colors.WHITE);
		textGate.setBounds(300, 110, 176, 20);

		btnEdit = UITool.createImageButton(compPage, button_edit,
				icons.getImage(IconHolder.bmpBtnLogin),
				icons.getImage(IconHolder.bmpBtnLoginUp),
				icons.getImage(IconHolder.bmpBtnLoginOn));
		btnEdit.setFont(Colors.GLOBAL_FONT);
		btnEdit.setBounds(308, 350, 114, 41);
		btnEdit.addMouseListener(new MouseAdapter() {
			public void mouseUp(MouseEvent e) {
				if (StringUtils.isEmpty(textIp.getText())) {
					MessageBoxHelper.openError(shell, "服务器IP不能为空！");
					return;
				}
				if (StringUtils.isEmpty(textMask.getText())) {
					MessageBoxHelper.openError(shell, "子网掩码不能为空！");
					return;
				}
				if (StringUtils.isEmpty(textGate.getText())) {
					MessageBoxHelper.openError(shell, "网关不能为空！");
					return;
				}

				sendNetSetIp();
			}
		});

		compPage.setVisible(true);
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
			public void shellClosed(ShellEvent e) {
				unhookListener();
				main.show();
				main.getShellRegistry().deregisterSystemShell();
			}
		});

		BorderStyler styler = new BorderStyler();
		styler.setCheckMinimizeWhenClose(true);
		styler.setMaximizeWhenDoubleClick(false);
		return styler.decorateShell(shell);
	}

	/**
	 * 打开shell，开始事件循环
	 */
	public void open() {

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
	 * 初始化监听器
	 */
	private void initListeners() {
		// 返回按钮鼠标事件
		btnBack.addMouseListener(new MouseAdapter() {
			public void mouseUp(MouseEvent e) {
				close();
			}
		});
	}
	
	@Override
	public void packetArrived(PacketEvent e) {
		InPacket in = (InPacket) e.getSource();

		// 现在开始判断包的类型，作出相应的处理
		switch (in.getCommand()) {
		case Protocol.CMD_SET_PASSWORD:
			processSetPasswrodSuccess(in);
			break;
		case Protocol.CMD_GET_PASSWORD:
			processGetPasswrodSuccess(in);
			break;
		case Protocol.CMD_RESTOR_HAND_START:
			processRestorHandStartSuccess(in);
			break;
		case Protocol.CMD_RESTOR_HAND_END:
			processRestorHandEndSuccess(in);
			break;
		case Protocol.CMD_SET_IP:
			processSetIPSuccess(in);
			break;
		case Protocol.CMD_FILE_WRITE_START:
			processFileWriteStartSuccess(in);
			break;
		case Protocol.CMD_FILE_WRITE_OK:
			processFileWriteOkSuccess(in);
			break;
		case Protocol.CMD_FILE_WRITE_END:
			processFileWriteEndSuccess(in);
			break;
		case Protocol.CMD_FILE_WRITE_ERROR:
			processFileWriteErrorSuccess(in);
			break;
		case Protocol.CMD_FILE_READ_HEAD:
			processFileReadHeadSuccess(in);
			break;
		case Protocol.CMD_FILE_READ:
			processFileReadSuccess(in);
			break;
		case Protocol.CMD_FILE_READ_END:
			processFileReadEndSuccess(in);
			break;
		case Protocol.CMD_FILE_READ_EMPTY:
			processFileReadEndSuccess(in);
			break;
		case Protocol.CMD_READ_NOW:
			processReadNowSuccess(in);
			break;
		case Protocol.CMD_WRITE_TIME:
			processWriteTimeSuccess(in);
			break;
		case Protocol.CMD_PT2262_315M_WRITE:
		case Protocol.CMD_PT2262_433M_WRITE:
		case Protocol.CMD_EV1527_315M_WRITE:
		case Protocol.CMD_EV1527_433M_WRITE:
			processSensorWriteSuccess(in);
			break;
		case Protocol.CMD_PT2262_315M_INPUT:
		case Protocol.CMD_PT2262_433M_INPUT:
		case Protocol.CMD_EV1527_315M_INPUT:
		case Protocol.CMD_EV1527_433M_INPUT:
			processSensorInputSuccess(in);
			break;
		case Protocol.CMD_TELEPHONE_WRITE_CMP:
			processTelephoneWriteCmpSuccess(in);
			break;
		case Protocol.CMD_TELEPHONE_WRITE_USER:
			processTelephoneWriteUserSuccess(in);
			break;
		case Protocol.CMD_GSM_WRITE_CMP:
			processGSMWriteCmpSuccess(in);
			break;
		case Protocol.CMD_GSM_WRITE_SMS:
			processGSMWriteSmsSuccess(in);
			break;
		case Protocol.CMD_EVENT_WRITE:
			processSceneSuccess(in);
			break;
		case Protocol.CMD_EVENT_CONNECT:
			processSceneBindSuccess(in);
			break;
		case Protocol.CMD_EVENT_DELAY:
			processEventDelaySuccess(in);
			break;
		case Protocol.CMD_EVENT_SET_WARNING:
			proccessSetWarningSuccess(in);
			break;
		case Protocol.CMD_ALARM_WRITE:
			processAlarmSuccess(in);
			break;
		case Protocol.CMD_HOST_SET_RESET:
			processHostSetResetSuccess(in);
			break;
		  case Protocol.CMD_RESET_HOST:
	        	processResetHostSuccess(in);
	        	break;
		  case Protocol.CMD_RESET_HOST_OVER:
	        	processResetHostOverSuccess(in);
	        	break;
		case Protocol.CMD_UNKNOWN:
			//processUnknown(in);
			break;
		}
	}
	
	/**
	 * 还原配置
	 */
	private void revertHost(){
		try {
			message = error_system_set_revert_config_success;
			if(SystemConfig.getInstance().isHardSoftVer2030()){
				FileDialog fileDialog = new FileDialog(shell, SWT.OPEN);
				fileDialog.setFilterExtensions(new String[]{SystemConfig.getInstance().getDbFileName()});
				String fileName = fileDialog.open();
				
				if(StringUtils.isNotEmpty(fileName)){
					DBConnection.freeConnection();
					FileTool.deleteFile(KC868.CONFIG_DIR + SystemConfig.getInstance().getDbLockFileName());
					FileTool.deleteFile(KC868.CONFIG_DIR + SystemConfig.getInstance().getDbFileName());
					FileTool.copyFile(fileName, KC868.CONFIG_DIR + SystemConfig.getInstance().getDbFileName());
					
					ZipUtil.zip(KC868.CONFIG_DIR + SystemConfig.getInstance().getDbFileName(), KC868.CONFIG_DIR + SystemConfig.getInstance()
											.getZipFileName());
					JSonFileWrite.createJSonFile();
					sendSensorOutAll();
				}
			}else{
				display.syncExec(new Runnable() {
					public void run() {
						DirectoryDialog directoryDialog = new DirectoryDialog(shell, SWT.OPEN);
						filePath = directoryDialog.open();
						if(filePath == null){
							iBackupOrRestore = 0;
							return;
						}
					}
				});
				
				if(StringUtils.isNotEmpty(filePath)){
					if(!filePath.endsWith("\\")){
						filePath += "\\";
					}
					File dbZipFile = new File(filePath + SystemConfig.getInstance().getZipFileName());
					
					if(dbZipFile.exists()){
						DBConnection.freeConnection();
						FileTool.deleteFile(KC868.CONFIG_DIR + SystemConfig.getInstance().getDbLockFileName());
						FileTool.deleteFile(KC868.CONFIG_DIR + SystemConfig.getInstance().getDbFileName());
						ZipUtil.unzip(filePath + SystemConfig.getInstance().getZipFileName(), KC868.CONFIG_DIR);
						File file = new File(filePath + SystemConfig.getInstance().getP315MInFileName());
						if(file.exists()){
							FileTool.deleteFile(KC868.CONFIG_DIR + SystemConfig.getInstance().getP315MInFileName());
							FileTool.copyFile(filePath + SystemConfig.getInstance().getP315MInFileName(), 
									KC868.CONFIG_DIR + SystemConfig.getInstance().getP315MInFileName());
						}
						file = new File(filePath + SystemConfig.getInstance().getP433MInFileName());
						if(file.exists()){
							FileTool.deleteFile(KC868.CONFIG_DIR + SystemConfig.getInstance().getP433MInFileName());
							FileTool.copyFile(filePath + SystemConfig.getInstance().getP433MInFileName(), 
									KC868.CONFIG_DIR + SystemConfig.getInstance().getP433MInFileName());
						}
						
						JSonFileWrite.createJSonFile();
						sendSensorOutAll();
					}else{
						display.syncExec(new Runnable() {
							public void run() {
								iBackupOrRestore = 0;
								MessageBoxHelper.openError(shell, error_system_set_dbzip_not_exist);
							}
						});
					}
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			display.syncExec(new Runnable() {
				public void run() {
					iBackupOrRestore = 0;
					MessageBoxHelper.openError(shell, error_system_set_revert_config_error);
				}
			});
		}
	}

	public void sendResetHost() {
		ResetHostPacket packet = new ResetHostPacket();
		packetProcessor.send(packet);
	}

	private void processResetHostSuccess(InPacket in) {
		
	}
	
	private void processResetHostOverSuccess(InPacket in){
		display.syncExec(new Runnable() {
			public void run() {
				if(informationDialog != null){
					informationDialog.close();
				}
				MessageBoxHelper.openInformation(shell, error_system_set_revert_success);
			}
		});
	}

	private void sendSensorOutAll() {
		sensorWrite = 0;
		index = 0;
		sensorList = new ArrayList<Object>();
		sensorList.addAll(boSensorOut.getList());
		sensorList.addAll(boSensorNor.getList());
		sensorList.addAll(boTransferSensor.getList());
		if(sensorList == null || sensorList.size() <= 0){
			processSensorWriteSuccess(null);
		}else{
			sendSensorWrite(sensorList.get(index++));
		}
	}

	/**
	 * 发送写入无线信号
	 * 
	 * @param sensorOut
	 */
	private void sendSensorWrite(Object sensor) {
		OutPacket packet = null;
		if(sensor instanceof SensorOut){
			SensorOut sensorOut = (SensorOut)sensor;
			packet = SensorPacketUtil.getSensorWriteOutPacket(
					sensorOut.getSensorId(), sensorOut.getFreqType(),
					sensorOut.getCodeType(), sensorOut.getResType(),
					sensorOut.getAddrCode(), sensorOut.getDataCode());
		}else if(sensor instanceof SensorNor){
			SensorNor sensorNor = (SensorNor)sensor;
			packet = SensorPacketUtil.getSensorWriteOutPacket(sensorNor.getSensorId(),
	    			sensorNor.getFreqType(), sensorNor.getCodeType(), sensorNor.getResType(),
	    			sensorNor.getAddrCode(), sensorNor.getDataCode()) ;
		}else if(sensor instanceof TsfSensor){
			TsfSensor tsfSensor = (TsfSensor)sensor;
			packet = SensorPacketUtil.getSensorWriteOutPacket(Integer.valueOf(tsfSensor.getSensorId().substring(2)), 
	    			tsfSensor.getFreqType(), tsfSensor.getCodeType(), tsfSensor.getResType(), tsfSensor.getAddrCode(), tsfSensor.getDataCode());
		}
		
		packetProcessor.send(packet);
	}

	/**
	 * 处理无线写入成功事件
	 * 
	 * @param in
	 */
	private void processSensorWriteSuccess(InPacket in) {
		if (index < sensorList.size()) {
			sendSensorWrite(sensorList.get(index++));
			display.syncExec(new Runnable() {
				public void run() {
					bar.setSelection(20 * index / sensorList.size());
				}
			});
		} else { // 结束
			sendSensorInAll();
		}
	}
	
	private void sendSensorInAll() {
		display.syncExec(new Runnable() {
			public void run() {
				bar.setSelection(20);
			}
		});
		if(SystemConfig.getInstance().isHardSoftVer2030()){
			index = 0;
			sensorInList = boSensorIn.getList();
			if(sensorInList == null || sensorInList.size() <= 0){
				processSensorInputSuccess(null);
			}else{
				sendSensorInput(sensorInList.get(index++));
			}
		}else{
			sendAlarmAll();
		}
	}

	/**
	 * 发送无线输入
	 * 
	 * @param sensorOut
	 */
	private void sendSensorInput(SensorIn sensorIn) {
		OutPacket packet = SensorPacketUtil.getSensorInInputOutPacket(
				sensorIn.getSensorId(), sensorIn.getFreqType(),
				sensorIn.getCodeType(), sensorIn.getResType(),
				sensorIn.getAddrCode(), sensorIn.getDataCode());

		packetProcessor.send(packet);
	}

	/**
	 * 处理无线写入成功事件
	 * 
	 * @param in
	 */
	private void processSensorInputSuccess(InPacket in) {
		if (index < sensorInList.size()) {
			sendSensorInput(sensorInList.get(index++));
			display.syncExec(new Runnable() {
				public void run() {
					bar.setSelection(10 * index / sensorInList.size() + 20);
				}
			});
		} else { // 结束
			sendAlarmAll();
		}
	}

	private void sendAlarmAll() {
		display.syncExec(new Runnable() {
			public void run() {
				bar.setSelection(50);
			}
		});
		index = 0;
		alarmList = boAlarm.getListValid();
		if(alarmList == null || alarmList.size() <= 0){
			processAlarmSuccess(null);
		}else{
			sendAlarm(alarmList.get(index++));
		}
	}

	private void sendAlarm(Alarm alarm) {
		String alarmParams = alarm.getAlarmWeek()
				+ GlobalConst.CONST_STRING_COMMA + alarm.getAlarmHour()
				+ GlobalConst.CONST_STRING_COMMA + alarm.getAlarmMinute()
				+ GlobalConst.CONST_STRING_COMMA + alarm.getAlarmSecond();

		AlarmWritePacket packet = new AlarmWritePacket();
		packet.setAlarmNum(alarm.getAlarmId());
		packet.setAlarmParams(alarmParams);
		packetProcessor.send(packet);
	}

	private void processAlarmSuccess(InPacket in) {
		if (index < alarmList.size()) {
			/*try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}*/
			sendAlarm(alarmList.get(index++));
			display.syncExec(new Runnable() {
				public void run() {
					bar.setSelection(10 * index / alarmList.size() + 50);
				}
			});
		} else { // 结束
			if (SystemConfig.getInstance().hasSim()) {
				sendTelephoneWriteCmpAll();
			} else {
				sendSceneAll();
			}
		}
	}

	private void sendTelephoneWriteCmpAll() {
		display.syncExec(new Runnable() {
			public void run() {
				bar.setSelection(60);
			}
		});
		index = 0;
		telInList = boTelIn.getListValid();
		if(telInList == null || telInList.size() <= 0){
			processTelephoneWriteCmpSuccess(null);
		}else{
			sendTelephoneWriteCmp(telInList.get(index++));
		}
	}

	private void sendTelephoneWriteCmp(TelIn telIn) {
		TelephoneWriteCmpPacket packet = new TelephoneWriteCmpPacket();
		packet.setTelId(Integer.valueOf(telIn.getTelId().substring(
				DictConst.TABLE_PREFIX_TEL_IN.length())));
		packet.setTelphone(telIn.getTelPhone());
		packet.setCountryCode(telIn.getCountryCode());
		packetProcessor.send(packet);
	}

	private void processTelephoneWriteCmpSuccess(InPacket in) {
		if (index < telInList.size()) {
			sendTelephoneWriteCmp(telInList.get(index++));
			display.syncExec(new Runnable() {
				public void run() {
					bar.setSelection(10 * index / telInList.size() + 60);
				}
			});
		} else { // 结束
			sendTelephoneWriteUserAll();
		}
	}

	private void sendTelephoneWriteUserAll() {
		display.syncExec(new Runnable() {
			public void run() {
				bar.setSelection(70);
			}
		});
		index = 0;
		telOutList = boTelOut.getListValid();
		if(telOutList == null || telOutList.size() <= 0){
			processTelephoneWriteUserSuccess(null);
		}else{
			sendTelephoneWriteUser(telOutList.get(index++));
		}
	}

	private void sendTelephoneWriteUser(TelOut telOut) {
		TelephoneWriteUserPacket packet = new TelephoneWriteUserPacket();
		packet.setTelId(Integer.valueOf(telOut.getTelId().substring(
				DictConst.TABLE_PREFIX_TEL_OUT.length())));
		packet.setTelphone(telOut.getTelPhone());
		packet.setCountryCode(telOut.getCountryCode());
		packetProcessor.send(packet);
	}

	private void processTelephoneWriteUserSuccess(InPacket in) {
		if (index < telOutList.size()) {
			sendTelephoneWriteUser(telOutList.get(index++));
			display.syncExec(new Runnable() {
				public void run() {
					bar.setSelection(10 * index / telOutList.size() + 70);
				}
			});
		} else { // 结束
			sendGSMWriteCmpAll();
		}
	}

	private void sendGSMWriteCmpAll() {
		display.syncExec(new Runnable() {
			public void run() {
				bar.setSelection(80);
			}
		});
		index = 0;
		smsInList = boSmsIn.getListValid();
		if(smsInList == null || smsInList.size() <= 0){
			processGSMWriteCmpSuccess(null);
		}else{
			sendGSMWriteCmp(smsInList.get(index++));
		}
	}

	private void sendGSMWriteCmp(SmsIn smsIn) {
		GSMWriteCmpPacket packet = new GSMWriteCmpPacket();
		packet.setSmsId(Integer.valueOf(smsIn.getSmsId().substring(
				DictConst.TABLE_PREFIX_SMS_IN.length())));
		packet.setSmsContent(smsIn.getSmsContent());
		packetProcessor.send(packet);
	}

	private void processGSMWriteCmpSuccess(InPacket in) {
		if (index < smsInList.size()) {
			sendGSMWriteCmp(smsInList.get(index++));
			display.syncExec(new Runnable() {
				public void run() {
					bar.setSelection(10 * index / smsInList.size() + 80);
				}
			});
		} else { // 结束
			sendGSMWriteSmsAll();
		}
	}

	private void sendGSMWriteSmsAll() {
		display.syncExec(new Runnable() {
			public void run() {
				bar.setSelection(90);
			}
		});
		index = 0;
		smsOutList = boSmsOut.getListValid();
		if(smsOutList == null || smsOutList.size() <= 0){
			processGSMWriteSmsSuccess(null);
		}else{
			sendGSMWriteSms(smsOutList.get(index++));
		}
	}

	private void sendGSMWriteSms(SmsOut smsOut) {
		GSMWriteSmsPacket packet = new GSMWriteSmsPacket();
		packet.setSmsId(Integer.valueOf(smsOut.getSmsId().substring(
				DictConst.TABLE_PREFIX_SMS_OUT.length())));
		packet.setSmsContent(smsOut.getSmsContent());
		packetProcessor.send(packet);
	}

	private void processGSMWriteSmsSuccess(InPacket in) {
		if (index < smsOutList.size()) {
			sendGSMWriteSms(smsOutList.get(index++));
			display.syncExec(new Runnable() {
				public void run() {
					bar.setSelection(10 * index / smsOutList.size() + 90);
				}
			});
		} else { // 结束
			sendSceneAll();
		}
	}
	
	private void sendSceneAll() {
		display.syncExec(new Runnable() {
			public void run() {
				bar.setSelection(30);
			}
		});
		index = 0;
		sceneList = boScene.getListValid();
		if(sceneList == null || sceneList.size() <= 0){
			processSceneSuccess(null);
		}else{
			sendScene(sceneList.get(index++));
		}
	}

	private void sendScene(Scene scene) {
		List<SceneAction> actionList = boSceneAction.getListBySceneId(scene
				.getSceneId());
		int count = actionList.size();
		int lost = 10 - count;

		byte[] actionByte = new byte[count * 4 + lost * 2];
		int index = 0;
		StringBuffer actionSB = new StringBuffer();
		for (int i = 0; i < count; i++) {
			SceneAction sceneAction = actionList.get(i);
			
			if("SW".equals(sceneAction.getSensorTable())){ // 设防、解防
				String prefix = sceneAction.getSensorId();
				int size = prefix.length();
				if(size >= 1){
					actionByte[index++] = (byte)prefix.charAt(0);
				}
				if(size >= 2){
					actionByte[index++] = (byte)prefix.charAt(1);
				}
				if(size >= 3){
					actionByte[index++] = (byte)prefix.charAt(2);
				}
				if(size >= 4){
					actionByte[index++] = (byte)prefix.charAt(3);
				}
				if("SWOK".equals(sceneAction.getSensorTable())){ //设防
					scene.setSceneSWOK("Y");
				}
			}else{
				String prefix = sceneAction.getSensorId().substring(0,2);
				int sensorId = Integer.valueOf(sceneAction.getSensorId().substring(2));
				actionByte[index++] = (byte)prefix.charAt(0);
				actionByte[index++] = (byte)prefix.charAt(1);
				if("SMS_OUT".equals(sceneAction.getSensorTable())){ //短信输出特殊处理
					actionByte[index++] = (byte)(sensorId);
					actionByte[index++] = (byte)(sensorId);
				}else{
					actionByte[index++] = (byte)0xFF;
					actionByte[index++] = (byte)(sensorId & 0xFF);
				}
			}
			
			actionSB.append(sceneAction.getSensorId());
		}

		for (int i = 0; i < lost; i++) {
			actionByte[index++] = (byte) (0xFF);
			actionByte[index++] = (byte) (0xFF);
		}

		EventWritePacket packet = new EventWritePacket();
		packet.setScene(scene.getSceneId());
		packet.setAction(actionByte);
		packet.setActionStr(actionSB.toString());
		packetProcessor.send(packet);
	}

	private void processSceneSuccess(InPacket in) {
		if (index < sceneList.size()) {
			sendScene(sceneList.get(index++));
			display.syncExec(new Runnable() {
				public void run() {
					bar.setSelection(10 * index / sceneList.size() + 30);
				}
			});
		} else { // 结束
			sendSceneBindAll();
		}
	}

	private void sendSceneBindAll() {
		display.syncExec(new Runnable() {
			public void run() {
				bar.setSelection(40);
			}
		});
		index = 0;
		sceneBindList = boSceneBind.getList();
		if(sceneBindList == null || sceneBindList.size() <= 0){
			processSceneBindSuccess(null);
		}else{
			sendSceneBind(sceneBindList.get(index++));
		}
	}

	/**
	 * 保存情景触发源
	 */
	private void sendSceneBind(SceneBind sceneBind) {
		EventConnectPacket packet = new EventConnectPacket();
		packet.setScene(sceneBind.getSceneId());
		packet.setEventSource(sceneBind.getSourceCmd());
		packetProcessor.send(packet);
	}

	private void processSceneBindSuccess(InPacket in) {
		if (index < sceneBindList.size()) {
			sendSceneBind(sceneBindList.get(index++));
			display.syncExec(new Runnable() {
				public void run() {
					bar.setSelection(10 * index / sceneBindList.size() + 40);
				}
			});
		} else { // 结束
			sendEventDelay(SystemConfig.getInstance().getActionInterval());
		}
	}

	public void sendEventDelay(int delay) {
		EventDelayPacket packet = new EventDelayPacket();
		packet.setDelay(delay);
		packetProcessor.send(packet);
	}

	private void processEventDelaySuccess(InPacket in) {
		if ("Y".equals(SystemConfig.getInstance().getSw())) {
			sendSetWarningOn();
		} else {
			sendSetWarningOff();
		}
	}

	private void sendSetWarningOn() {
		SetWarningOnPacket packet = new SetWarningOnPacket();
		packetProcessor.send(packet);
	}

	private void sendSetWarningOff() {
		SetWarningOffPacket packet = new SetWarningOffPacket();
		packetProcessor.send(packet);
	}

	private void proccessSetWarningSuccess(InPacket in) {
		syncFileIndex = 1;
		fileWrite(SystemConfig.getInstance().getJsonZipFileName());
	}
	
	/**
	 * 在读文件之前先读一下密码
	 */
	public void sendGetPasswordForFileRead(){
		isFileRead = true;
		
		GetPasswordHostPacket packet = new GetPasswordHostPacket();
		packetProcessor.send(packet);
	}
	
	private void processGetPasswrodSuccess(InPacket in) {
		if(isFileRead){
			fileRead(SystemConfig.getInstance().getZipFileName());
		}
		isFileRead = false;
	}
	
	/**
	 * 做备份
	 */
	public void doBackup(){
		iBackupOrRestore = 1;
		writeFileIndex = 1;
		isFileRead = true;
		sendRestorHandStart();
	}
	
	/**
	 * 做还原
	 */
	public void doRestore(){
		iBackupOrRestore = 2;
		sendRestorHandStart();
	}
	
	public void sendRestorHandStart(){
		RestorHandStartPacket packet = new RestorHandStartPacket();
		packetProcessor.send(packet);
	}
	
	public void processRestorHandStartSuccess(InPacket in) {
		if(iBackupOrRestore == 1){ //备份
			if(isFileRead){
				fileRead(SystemConfig.getInstance().getZipFileName());
			}
			isFileRead = false;
		}else if(iBackupOrRestore == 2){// 还原
			revertHost();
		}
	}
	
	public void sendRestorHandEnd(){
		RestorHandEndPacket packet = new RestorHandEndPacket();
		packetProcessor.send(packet);
	}
	
	public void processRestorHandEndSuccess(InPacket in){
		if(iBackupOrRestore == 1){//备份
			display.syncExec(new Runnable() {
				public void run() {
					bar.setSelection(100);
					MessageBoxHelper.openInformation(shell, error_system_set_backup_config_success);
					bar.setSelection(0);
				}
			});
		}else if(iBackupOrRestore == 2){// 还原
			SystemConfig.getInstance().setSysChanged(false);
			display.syncExec(new Runnable() {
				public void run() {
					bar.setSelection(100);
					MessageBoxHelper.openInformation(shell, message);
					bar.setSelection(0);
				}
			});
		}
		iBackupOrRestore = 0;
	}

	/**
	 * 读Json文件
	 */
	public void fileRead(String fileName) {
		offset = 0;
		percent = 0;
		buffer = null;
		bufferP315In = null;
		bufferP433In = null;
		FileReadHeadPacket packet = new FileReadHeadPacket();

		writeFileName = fileName;
		packet.setFileName(writeFileName);
		packetProcessor.send(packet);
	}

	/**
	 * 处理接收文件读取头
	 * 
	 * @param in
	 */
	private void processFileReadHeadSuccess(InPacket in) {

		FileReadHeadReplyPacket reply = (FileReadHeadReplyPacket) in;
		if (reply.isFileOk()) {
			//FileTool.deleteFile(writeFileName);
			fileSize = reply.getFileSize();
			
			switch(writeFileIndex){
			case 1: // db.zip
				buffer = new byte[fileSize];
				break;
			case 2: // P315M_IN.DAT
				bufferP315In = new byte[fileSize];
				break;
			case 3: // P433M_IN.DAT
				bufferP433In = new byte[fileSize];
				break;
			}
			
			if(isNew){ // 新协议
    			nextSegment = 1;
    			//segmentCount = (int)(fileSize - 1) / segmentSize + 1;
    			segmentCount = reply.getFileSegmentCount();
    			if(segmentCount > 0 ){
    				// 发送接收包
            		FileReadNewPacket packet = new FileReadNewPacket();
            		packet.setSegment(nextSegment++);
            		packetProcessor.send(packet);
    			}
    		}else {
    			// 发送准备接收包
            	FileReadStartPacket packet = new FileReadStartPacket();
        		packetProcessor.send(packet);
    		}
			
		} else {
			System.out.println(error_system_set_read_error);
		}
	}

	/**
	 * 处理接收文件内容
	 * 
	 * @param in
	 */
	private void processFileReadSuccess(InPacket in) {
		FileReadReplyPacket reply = (FileReadReplyPacket)in;
    	int len = reply.getBuffer().length;
    	// 接收byte[]
    	switch(writeFileIndex){
		case 1: // db.zip
			System.arraycopy(reply.getBuffer(), 0, buffer, offset, len);
			break;
		case 2: // P315M_IN.DAT
			System.arraycopy(reply.getBuffer(), 0, bufferP315In, offset, len);
			break;
		case 3: // P433M_IN.DAT
			System.arraycopy(reply.getBuffer(), 0, bufferP433In, offset, len);
			break;
		}
    	
    	offset += len;
    	percent = offset * 100 / (fileSize);
    	
    	display.syncExec(new Runnable() {
			public void run() {
				bar.setSelection(percent);
			}
    	});
    	
    	if(isNew){
        	if(nextSegment <= segmentCount){
        		/*try {
					Thread.sleep(300);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}*/
        		FileReadNewPacket packet = new FileReadNewPacket();
        		packet.setSegment(nextSegment++);
        		packetProcessor.send(packet);
        	}else{ // 读文件结束
        		processFileReadEndSuccess(null);
        	}
    	}
	}

	/**
	 * 处理接收文件结束
	 * 
	 * @param in
	 */
	private void processFileReadEndSuccess(InPacket in) {
		display.syncExec(new Runnable() {
			public void run() {
				try {
					switch(writeFileIndex){
					case 1: // db.zip
						DirectoryDialog directoryDialog = new DirectoryDialog(shell, SWT.SAVE);
						filePath = directoryDialog.open();
						if(filePath == null){
							bar.setSelection(0);
							return;
						}
						if(!filePath.endsWith("\\")){
							filePath += "\\";
						}
						
						FileTool.saveFile(buffer, 0, buffer.length, filePath+ SystemConfig.getInstance().getZipFileName());
						DBConnection.freeConnection();
						FileTool.deleteFile(filePath + SystemConfig.getInstance().getDbLockFileName());
						FileTool.deleteFile(filePath + SystemConfig.getInstance().getDbFileName());
						//ZipUtil.unzip(filePath + SystemConfig.getInstance().getZipFileName(), filePath);
						//FileTool.deleteFile(filePath + SystemConfig.getInstance().getZipFileName());
						buffer = null;
						if(SystemConfig.getInstance().isHardSoftVer2131()){
							writeFileIndex = 2;
							fileRead(SystemConfig.getInstance().getP315MInFileName());
						}else{ // 备份结束
							sendRestorHandEnd();
						}
						
						break;
					case 2: // P315M_IN.DAT
						if(bufferP315In != null && bufferP315In.length > 0){
							FileTool.saveFile(bufferP315In, 0, bufferP315In.length, filePath+ SystemConfig.getInstance().getP315MInFileName());
						}
						writeFileIndex = 3;
						fileRead(SystemConfig.getInstance().getP433MInFileName());
						bufferP315In = null;
						break;
					case 3: // P433M_IN.DAT
						if(bufferP433In != null && bufferP433In.length > 0){
							FileTool.saveFile(bufferP433In, 0, bufferP433In.length, filePath+ SystemConfig.getInstance().getP315MInFileName());
						}
						bufferP433In = null;
						sendRestorHandEnd();
						break;
					}
				} catch (Exception e) {
					e.printStackTrace();
					iBackupOrRestore = 0;
					MessageBoxHelper.openInformation(shell, error_system_set_backup_config_error);
					bar.setSelection(0);
				}
				
			}
		});
		
		buffer = null;
		offset = 0;
	}

	public void fileWrite(String writeFileName) {
		this.writeFileName = writeFileName;
		FileWriteHeadPacket packet = new FileWriteHeadPacket();
		buffer = null;
		nextSegment = 0;
		fileSegmentor = new FileSegmentor(KC868.CONFIG_DIR + writeFileName, 4096);
		segmentCount = fileSegmentor.getTotalFragments();

		packet.setFileName(writeFileName);
		packet.setFileSize(fileSegmentor.getFileSize());

		packetProcessor.send(packet);
	}

	private void processFileWriteStartSuccess(InPacket in) {
		FileWriteStartReplyPacket reply = (FileWriteStartReplyPacket) in;

		byte[] buf = fileSegmentor.getFragment(nextSegment);
		buffer = new byte[buf.length];
		// buffer = new byte[buf.length + 1];
		System.arraycopy(buf, 0, buffer, 0, buf.length);
		// buffer[buf.length] = '\0';

		FileWritePacket packet = new FileWritePacket();
		packet.setBuf(buffer);

		if (nextSegment >= segmentCount) {
			fileSegmentor.close();
		}

		nextSegment++;
		/*try {
			Thread.sleep(300);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}*/
		packetProcessor.send(packet);

		buffer = null;
	}

	private void processFileWriteOkSuccess(InPacket in) {
		FileWriteOKReplyPacket reply = (FileWriteOKReplyPacket) in;

		byte[] buf = fileSegmentor.getFragment(nextSegment);
		buffer = new byte[buf.length];
		// buffer = new byte[buf.length + 1];
		System.arraycopy(buf, 0, buffer, 0, buf.length);
		// buffer[buf.length] = '\0';

		FileWritePacket packet = new FileWritePacket();
		packet.setBuf(buffer);

		if (nextSegment >= segmentCount) {
			fileSegmentor.close();
		} else {
			nextSegment++;
			/*try {
				Thread.sleep(300);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}*/
			packetProcessor.send(packet);
		}
		buffer = null;

		display.syncExec(new Runnable() {
			public void run() {
				bar.setSelection(100 * nextSegment / segmentCount);
			}
		});
	}

	private void processFileWriteEndSuccess(InPacket in) {
		//FileWriteEndReplyPacket packet = (FileWriteEndReplyPacket) in;
		if (syncFileIndex == 1) { // mobile2.zip 文件
			syncFileIndex = 2;
			display.syncExec(new Runnable() {
				public void run() {
					bar.setSelection(100);
				}
			});
			/*
			try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}*/
			fileWrite(SystemConfig.getInstance().getJsonFileName());
			display.syncExec(new Runnable() {
				public void run() {
					bar.setSelection(0);
					// MessageBoxHelper.openInformation(shell, "数据同步成功！");
				}
			});
		} else if(syncFileIndex == 2){ // mobile2.txt 文件
			syncFileIndex = 3;
			display.syncExec(new Runnable() {
				public void run() {
					bar.setSelection(100);
				}
			});
			/*try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}*/
			fileWrite(SystemConfig.getInstance().getZipFileName());
			display.syncExec(new Runnable() {
				public void run() {
					bar.setSelection(0);
					// MessageBoxHelper.openInformation(shell, "数据同步成功！");
				}
			});
		} else if(syncFileIndex == 3){ // db.zip 文件, 当2030版本时，已经结束；  2131版本还有两文件还原P315M_IN.DAT P433M_IN.DAT
			if(iBackupOrRestore == 2){ // 还原
				if(SystemConfig.getInstance().isHardSoftVer2030()){
					sendRestorHandEnd();
				}else{
					syncFileIndex = 4;
					File file = new File(KC868.CONFIG_DIR + SystemConfig.getInstance().getP315MInFileName());
					if(file.exists()){
						
						display.syncExec(new Runnable() {
							public void run() {
								bar.setSelection(100);
							}
						});
						
						/*try {
							Thread.sleep(3000);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}*/
						fileWrite(SystemConfig.getInstance().getP315MInFileName());
						display.syncExec(new Runnable() {
							public void run() {
								bar.setSelection(0);
								// MessageBoxHelper.openInformation(shell, "数据同步成功！");
							}
						});
					}else{
						processFileWriteEndSuccess(null);
					}
				}
			}else{ // 同步
				SystemConfig.getInstance().setSysChanged(false);
				display.syncExec(new Runnable() {
					public void run() {
						bar.setSelection(100);
						MessageBoxHelper.openInformation(shell, message);
						bar.setSelection(0);
					}
				});
			}
		}else if(syncFileIndex == 4){ // P315M_IN.DAT
			syncFileIndex = 5;
			File file = new File(KC868.CONFIG_DIR + SystemConfig.getInstance().getP433MInFileName());
			if(file.exists()){
				display.syncExec(new Runnable() {
					public void run() {
						bar.setSelection(100);
					}
				});
				
				/*try {
					Thread.sleep(3000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}*/
				fileWrite(SystemConfig.getInstance().getP433MInFileName());
				display.syncExec(new Runnable() {
					public void run() {
						bar.setSelection(0);
						// MessageBoxHelper.openInformation(shell, "数据同步成功！");
					}
				});
			}else{
				processFileWriteEndSuccess(null);
			}
		}else if(syncFileIndex == 5){ // P433M_IN.DAT 还原结束
			sendRestorHandEnd();
		}
	}

	private void processFileWriteErrorSuccess(InPacket in) {
		FileWriteErrorReplyPacket packet = (FileWriteErrorReplyPacket) in;

		display.syncExec(new Runnable() {
			public void run() {
				bar.setSelection(0);
				MessageBoxHelper.openInformation(shell, error_system_set_sync_error);
			}
		});
	}

	/**
	 * 发送读取主机时间
	 */
	private void sendReadNow() {
		ReadNowPacket packet = new ReadNowPacket();
		packetProcessor.send(packet);
	}

	/**
	 * 处理读取主机时间
	 * 
	 * @param in
	 */
	private void processReadNowSuccess(InPacket in) {
		final ReadNowReplyPacket reply = (ReadNowReplyPacket) in;

		display.syncExec(new Runnable() {
			public void run() {
				textYear.setText("" + reply.getYear());
				textMonth.setText("" + reply.getMonth());
				textDay.setText("" + reply.getDay());
				textHour.setText("" + reply.getHour());
				textMinute.setText("" + reply.getMinute());
				textSecond.setText("" + reply.getSecond());
				
				lbYear.setText("" + reply.getYear());
				lbMonth.setText("" + reply.getMonth());
				lbDay.setText("" + reply.getDay());
				lbHour.setText("" + reply.getHour());
				lbMinute.setText("" + reply.getMinute());
				lbSecond.setText("" + reply.getSecond());
			}
		});
	}

	/**
	 * 发送设置自动重启动时间
	 */
	private void setRestart(){
		int week = 0;
		String alarmParams = "";
		
		if(checkSun.getSelection()) week += 1;
		if(checkMon.getSelection()) week += 2;
		if(checkTues.getSelection()) week += 4;
		if(checkWed.getSelection()) week += 8;
		if(checkThurs.getSelection()) week += 16;
		if(checkFrid.getSelection()) week += 32;
		if(checkSat.getSelection()) week += 64;
		
		alarmParams += week + GlobalConst.CONST_STRING_COMMA +
				comboHour.getText() + GlobalConst.CONST_STRING_COMMA + comboMinute.getText() + 
				GlobalConst.CONST_STRING_COMMA + comboSecond.getText();
		
		HostReSetPacket packet = new HostReSetPacket();
    	packet.setParams(alarmParams);
    	packetProcessor.send(packet);
	}
	
	/**
	 * 设置自动重启主机成功
	 */
	private void processHostSetResetSuccess(InPacket in){
		display.syncExec(new Runnable() {
			public void run() {
				int week = 0;
				
				if(checkSun.getSelection()) week += 1;
				if(checkMon.getSelection()) week += 2;
				if(checkTues.getSelection()) week += 4;
				if(checkWed.getSelection()) week += 8;
				if(checkThurs.getSelection()) week += 16;
				if(checkFrid.getSelection()) week += 32;
				if(checkSat.getSelection()) week += 64;
				
				SetRestart setRestart = new SetRestart();
				setRestart.setReWeek(""+week);
				setRestart.setReHour(comboHour.getText());
				setRestart.setReMinute(comboMinute.getText());
				setRestart.setReSecond(comboSecond.getText());
				
				boSetRestart.update(setRestart);
				MessageBoxHelper.openInformation(shell, error_system_set_host_startup_success);
			}
		});
	}
	
	/**
	 * 发送设置主机时间
	 */
	private void sendWriteTime() {
		WriteTimePacket packet = new WriteTimePacket();
		
	 	packet.setYear(textYear.getText());
		packet.setMonth(textMonth.getText());
		packet.setDay(textDay.getText()); 
		packet.setHour(textHour.getText());
		packet.setMinute(textMinute.getText());
		packet.setSecond(textSecond.getText());
		 

		/*Calendar calendar = Calendar.getInstance();
		calendar.setTimeZone(TimeZone.getTimeZone("Asia/Shanghai"));
		calendar.setTime(new Date());

		packet.setYear("" + calendar.get(Calendar.YEAR));
		packet.setMonth("" + (calendar.get(Calendar.MONTH) + 1));
		packet.setDay("" + calendar.get(Calendar.DAY_OF_MONTH));
		packet.setHour("" + calendar.get(Calendar.HOUR_OF_DAY));
		packet.setMinute("" + calendar.get(Calendar.MINUTE));
		packet.setSecond("" + calendar.get(Calendar.SECOND));*/

		packetProcessor.send(packet);
	}

	/**
	 * 处理读取主机时间
	 * 
	 * @param in
	 */
	private void processWriteTimeSuccess(InPacket in) {
		display.syncExec(new Runnable() {
			public void run() {
				MessageBoxHelper.openInformation(shell, error_system_set_host_time_success);
			}
		});
	}

	/**
	 * 发送设定管理员密码
	 */
	private void sendSetPasswordAdmin() {
		SetPasswordAdminPacket packet = new SetPasswordAdminPacket();
		packet.setPassword(textAdminPwd.getText());
		packetProcessor.send(packet);
	}
	
	/**
	 * 发送设定主机密码
	 */
	private void sendSetPasswordHost() {
		SetPasswordHostPacket packet = new SetPasswordHostPacket();
		packet.setPassword(textPwd.getText());
		packetProcessor.send(packet);
	}

	/**
	 * 处理设定成功
	 * 
	 * @param in
	 */
	private void processSetPasswrodSuccess(InPacket in) {
		SetPasswordReplyPacket reply = (SetPasswordReplyPacket) in;

		display.syncExec(new Runnable() {
			public void run() {
				if(isSetAdminPwd){
					SystemConfig.getInstance().setAdminPassword(
							textAdminPwd.getText());
				}else{
					SystemConfig.getInstance().setPassword(textPwd.getText());
				}

				MessageBoxHelper.openInformation(shell, error_system_set_pwd_success);
			}
		});
	}

	/**
	 * 发送设定网络参数
	 */
	private void sendNetSetIp() {
		SetIPPacket packet = new SetIPPacket();
		packet.setIp(textIp.getText());
		packet.setMask(textMask.getText());
		packet.setGate(textGate.getText());
		packetProcessor.send(packet);
	}

	/**
	 * 处理设定成功
	 * 
	 * @param in
	 */
	private void processSetIPSuccess(InPacket in) {
		display.syncExec(new Runnable() {
			public void run() {

				MessageBoxHelper.openInformation(shell, error_system_set_net_success);
			}
		});
	}

	/**
	 * 处理未知应答包，一般是主机有问题
	 * 
	 * @param in
	 */
	private void processUnknown(InPacket in) {
		String errorMessage = "";
		if (in instanceof ErrorPacket) {
			ErrorPacket error = (ErrorPacket) in;
			switch (error.errorCode) {
			case ErrorPacket.ERROR_REMOTE_CLOSED:
				errorMessage = message_box_disconnection;
				break;
			case ErrorPacket.ERROR_TIMEOUT:
				errorMessage = message_box_receive_timeout;
				break;
			}
		} else {
			errorMessage = message_box_unknown;
		}

		final String message = errorMessage;
		display.syncExec(new Runnable() {
			public void run() {
				MessageBoxHelper.openError(shell, message);
			}
		});
	}

	/**
	 * 清空面板
	 */
	private void clearComposite(Composite comp) {
		for (Control c : comp.getChildren()) {
			if (c != null && !c.isDisposed()) {
				c.dispose();
			}
		}
		comp.redraw();
	}

	/**
	 * 设置所有的房间图片为off
	 */
	private void setTabOff() {
		for (Control c : compTab.getChildren()) {
			if (c instanceof ImageButton) {
				((ImageButton) c).setImage(icons
						.getImage(IconHolder.btnSubMenuOff));
			}
		}
	}

	/**
	 * 关闭主shell
	 */
	public void close() {
		if (shell != null && !shell.isDisposed())
			shell.close();
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

	public Shell getShell() {
		return shell;
	}

	public Display getDisplay() {
		return display;
	}
}
