package com.joeysoft.kc868.ui.helper;

import com.joeysoft.kc868.ui.DebugShell;
import com.joeysoft.kc868.ui.EquipShell;
import com.joeysoft.kc868.ui.InfoShell;
import com.joeysoft.kc868.ui.MainShell;
import com.joeysoft.kc868.ui.RoomShell;
import com.joeysoft.kc868.ui.SafetyShell;
import com.joeysoft.kc868.ui.SceneShell;
import com.joeysoft.kc868.ui.SmartShell;
import com.joeysoft.kc868.ui.SystemShell;
import com.joeysoft.kc868.ui.VidiconShell;
import com.joeysoft.kc868.ui.dialogs.DeviceDialog;
import com.joeysoft.kc868.ui.dialogs.FloorDialog;
import com.joeysoft.kc868.ui.dialogs.InformationDialog;
import com.joeysoft.kc868.ui.dialogs.LineateDialog;
import com.joeysoft.kc868.ui.dialogs.RelayDialog;
import com.joeysoft.kc868.ui.dialogs.RoomDialog;
import com.joeysoft.kc868.ui.dialogs.SelectTransferDialog;
import com.joeysoft.kc868.ui.dialogs.SensorInDialog;
import com.joeysoft.kc868.ui.dialogs.SensorOutDialog;
import com.joeysoft.kc868.ui.dialogs.SmsInDialog;
import com.joeysoft.kc868.ui.dialogs.SmsOutDialog;
import com.joeysoft.kc868.ui.dialogs.TelInDialog;
import com.joeysoft.kc868.ui.dialogs.TelOutDialog;
import com.joeysoft.kc868.ui.dialogs.TempSensorDialog;
import com.joeysoft.kc868.ui.dialogs.TransferDialog;

/**
 * 窗口工厂 类
 * @author JOEY
 *
 */
public class ShellFactory {
	
	public static VidiconShell createVidiconShell(MainShell main) {
        return new VidiconShell(main);
    }
	
	public static SceneShell createSceneShell(MainShell main) {
        return new SceneShell(main);
    }
	
	public static InfoShell createInfoShell(MainShell main) {
        return new InfoShell(main);
    }
	
	public static TelInDialog createTelInDialog(MainShell main) {
        return new TelInDialog(main);
    }
	
	public static TelOutDialog createTelOutDialog(MainShell main) {
        return new TelOutDialog(main);
    }
	
	public static SmsInDialog createSmsInDialog(MainShell main) {
        return new SmsInDialog(main);
    }
	
	public static SmsOutDialog createSmsOutDialog(MainShell main) {
        return new SmsOutDialog(main);
    }
	
	public static SmartShell createSmartShell(MainShell main) {
        return new SmartShell(main);
    }
	
	public static DebugShell createDebugShell(MainShell main) {
        return new DebugShell(main);
    }
	
	public static SelectTransferDialog createSelectTransferDialog(MainShell main) {
        return new SelectTransferDialog(main);
    }
	
	public static SystemShell createSystemShell(MainShell main) {
        return new SystemShell(main);
    }
	
	public static SafetyShell createSafetyShell(MainShell main) {
        return new SafetyShell(main);
    }
	
	public static RoomShell createRoomShell(MainShell main) {
        return new RoomShell(main);
    }
	
	public static TempSensorDialog createTempSensorDialog(MainShell main) {
        return new TempSensorDialog(main);
    }
	
	public static SensorInDialog createSensorInDialog(MainShell main) {
        return new SensorInDialog(main);
    }
	
	public static TransferDialog createTransferDialog(MainShell main) {
        return new TransferDialog(main);
    }
	
	public static DeviceDialog createDeviceDialog(MainShell main) {
        return new DeviceDialog(main);
    }
	
	public static LineateDialog createLineateDialog(MainShell main) {
        return new LineateDialog(main);
    }
	
	public static SensorOutDialog createSensorOutDialog(MainShell main) {
        return new SensorOutDialog(main);
    }
	
	public static RelayDialog createRelayDialog(MainShell main) {
        return new RelayDialog(main);
    }
	
	public static RoomDialog createRoomDialog(MainShell main) {
        return new RoomDialog(main);
    }
	
	public static FloorDialog createFloorDialog(MainShell main) {
        return new FloorDialog(main);
    }
	
	public static EquipShell createEquipShell(MainShell main) {
        return new EquipShell(main);
    }
}
