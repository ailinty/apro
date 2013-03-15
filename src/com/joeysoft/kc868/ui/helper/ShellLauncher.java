package com.joeysoft.kc868.ui.helper;

import static com.joeysoft.kc868.resource.Messages.message_opreate_error;

import java.util.List;

import com.joeysoft.kc868.SystemConfig;
import com.joeysoft.kc868.db.bean.Device;
import com.joeysoft.kc868.db.bean.Floor;
import com.joeysoft.kc868.db.bean.Lineate;
import com.joeysoft.kc868.db.bean.Relay;
import com.joeysoft.kc868.db.bean.Room;
import com.joeysoft.kc868.db.bean.SensorOut;
import com.joeysoft.kc868.db.bean.SensorIn;
import com.joeysoft.kc868.db.bean.SmsIn;
import com.joeysoft.kc868.db.bean.SmsOut;
import com.joeysoft.kc868.db.bean.TelIn;
import com.joeysoft.kc868.db.bean.TelOut;
import com.joeysoft.kc868.db.bean.TempSensor;
import com.joeysoft.kc868.db.bean.Transfer;
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
 * 窗口打开类
 * @author JOEY
 *
 */
public class ShellLauncher {
	private MainShell main;
	
	public ShellLauncher(MainShell main){
		this.main = main;
	}
	
	public void openVidiconShell(String url){
		if(!main.getShellRegistry().isVidiconShellOpend()) {
			VidiconShell shell = ShellFactory.createVidiconShell(main);
			shell.setUrl(url);
            main.getShellRegistry().registerVidiconShell(shell);
            shell.open();
        }else{
        	VidiconShell shell = ShellFactory.createVidiconShell(main);
			shell.setUrl(url);
			shell.open();
        }
	}
	
	public void openSceneShell(){
		if(!main.getShellRegistry().isSceneShellOpend()) {
			SceneShell shell = ShellFactory.createSceneShell(main);
            main.getShellRegistry().registerSceneShell(shell);
            shell.open();
        }else{
        	main.getShellRegistry().getSceneShell().open();
        }
	}
	
	public void openInfoShell(){
		if(!main.getShellRegistry().isInfoShellOpend()) {
			InfoShell shell = ShellFactory.createInfoShell(main);
            main.getShellRegistry().registerInfoShell(shell);
            shell.open();
        }else{
        	main.getShellRegistry().getInfoShell().open();
        }
	}
	
	public TelIn openTelInDialog(TelIn telIn){
		if(!main.getShellRegistry().isTelInDialogOpend()) {
			TelInDialog dialog = ShellFactory.createTelInDialog(main);
			dialog.setTelIn(telIn);
            main.getShellRegistry().registerTelInDialog(dialog);
            return dialog.open();
        }
		return null;
	}
	
	public TelOut openTelOutDialog(TelOut telOut){
		if(!main.getShellRegistry().isTelOutDialogOpend()) {
			TelOutDialog dialog = ShellFactory.createTelOutDialog(main);
			dialog.setTelOut(telOut);
			main.getShellRegistry().registerTelOutDialog(dialog);
            return dialog.open();
        }
		return null;
	}
	
	public SmsIn openSmsInDialog(SmsIn smsIn){
		if(!main.getShellRegistry().isSmsInDialogOpend()) {
			SmsInDialog dialog = ShellFactory.createSmsInDialog(main);
			dialog.setSmsIn(smsIn);
            main.getShellRegistry().registerSmsInDialog(dialog);
            return dialog.open();
        }
		return null;
	}
	
	public SmsOut openSmsOutDialog(SmsOut smsOut){
		if(!main.getShellRegistry().isSmsOutDialogOpend()) {
			SmsOutDialog dialog = ShellFactory.createSmsOutDialog(main);
			dialog.setSmsOut(smsOut);
			main.getShellRegistry().registerSmsOutDialog(dialog);
            return dialog.open();
        }
		return null;
	}
	
	public void openDebugShell(){
		if(!main.getShellRegistry().isDebugShellOpend()) {
			DebugShell shell = ShellFactory.createDebugShell(main);
            main.getShellRegistry().registerDebugShell(shell);
            shell.open();
        }else{
        	main.getShellRegistry().getDebugShell().open();
        }
	}
	
	public Transfer openSelectTransferDialog(List<Transfer> transferList){
		if(!main.getShellRegistry().isSelectTransferDialogOpend()) {
			SelectTransferDialog dialog = ShellFactory.createSelectTransferDialog(main);
			dialog.setTransferList(transferList);
            main.getShellRegistry().registerSelectTransferDialog(dialog);
            return dialog.open();
        }else{
        	return null;
        }
	}
	
	public void openSmartShell(){
		if(!main.getShellRegistry().isSmartShellOpend()) {
			SmartShell shell = ShellFactory.createSmartShell(main);
            main.getShellRegistry().registerSmartShell(shell);
            shell.open();
        }else{
        	main.getShellRegistry().getSmartShell().open();
        }
	}
	
	public void openSystemShell(){
		if(!main.getShellRegistry().isSystemShellOpend()) {
			SystemShell shell = ShellFactory.createSystemShell(main);
            main.getShellRegistry().registerSystemShell(shell);
            shell.open();
        }else{
        	main.getShellRegistry().getSystemShell().open();
        }
	}
	
	public void openSafetyShell(){
		if(!main.getShellRegistry().isSafetyShellOpend()) {
			SafetyShell shell = ShellFactory.createSafetyShell(main);
            main.getShellRegistry().registerSafetyShell(shell);
            shell.open();
        }else{
        	main.getShellRegistry().getSafetyShell().open();
        }
	}
	
	public void openRoomShell(){
		if(!main.getShellRegistry().isRoomShellOpend()) {
			RoomShell shell = ShellFactory.createRoomShell(main);
            main.getShellRegistry().registerRoomShell(shell);
            shell.open();
        }else{
        	main.getShellRegistry().getRoomShell().open();
        }
	}
	
	public TempSensor openTempSensorDialog(TempSensor tempSensor){
		if(!main.getShellRegistry().isTempSensorDialogOpend()) {
			TempSensorDialog dialog = ShellFactory.createTempSensorDialog(main);
			dialog.setTempSensor(tempSensor);
            main.getShellRegistry().registerTempSensorDialog(dialog);
            return dialog.open();
        }
		return null;
	}
	
	public SensorIn openSensorInDialog(SensorIn sensorIn){
		if(!main.getShellRegistry().isSensorInDialogOpend()) {
			SensorInDialog dialog = ShellFactory.createSensorInDialog(main);
			dialog.setSensorIn(sensorIn);
            main.getShellRegistry().registerSensorInDialog(dialog);
            return dialog.open();
        }
		return null;
	}
	
	public Transfer openTransferDialog(Transfer transfer){
		if(!main.getShellRegistry().isTransferDialogOpend()) {
			TransferDialog dialog = ShellFactory.createTransferDialog(main);
			dialog.setTransfer(transfer);
            main.getShellRegistry().registerTransferDialog(dialog);
            return dialog.open();
        }
		return null;
	}
	
	public Device openDeviceDialog(Device device){
		if(!main.getShellRegistry().isDeviceDialogOpend()) {
			DeviceDialog dialog = ShellFactory.createDeviceDialog(main);
			dialog.setDevice(device);
            main.getShellRegistry().registerDeviceDialog(dialog);
            return dialog.open();
        }
		return null;
	}
	
	public Lineate openLineateDialog( Lineate lineate){
		if(!main.getShellRegistry().isLineateDialogOpend()) {
			LineateDialog dialog = ShellFactory.createLineateDialog(main);
			dialog.setLineate(lineate);
            main.getShellRegistry().registerLineateDialog(dialog);
            return dialog.open();
        }
		return null;
	}
	
	public SensorOut openSensorOutDialog(SensorOut sensorOut){
		if(!main.getShellRegistry().isSensorOutDialogOpend()) {
			SensorOutDialog dialog = ShellFactory.createSensorOutDialog(main);
			dialog.setSensorOut(sensorOut);
            main.getShellRegistry().registerSensorOutDialog(dialog);
            return dialog.open();
        }
		return null;
	}
	
	public Relay openRelayDialog(Relay relay){
		if(!main.getShellRegistry().isRelayDialogOpend()) {
			RelayDialog dialog = ShellFactory.createRelayDialog(main);
			dialog.setRelay(relay);
            main.getShellRegistry().registerRelayDialog(dialog);
            return dialog.open();
        }
		return null;
	}
	
	public Room openRoomDialog(Room room){
		if(!main.getShellRegistry().isRoomDialogOpend()) {
			RoomDialog dialog = ShellFactory.createRoomDialog(main);
			dialog.setRoom(room);
            main.getShellRegistry().registerRoomDialog(dialog);
            return dialog.open();
        }
		return null;
	}
	
	public Floor openFloorDialog(Floor floor){
		if(!main.getShellRegistry().isFloorDialogOpend()) {
			FloorDialog dialog = ShellFactory.createFloorDialog(main);
			dialog.setFloor(floor);
            main.getShellRegistry().registerFloorDialog(dialog);
            return dialog.open();
        }
		return null;
	}
	
	public void openEquipShell(){
		if(!main.getShellRegistry().isEquipShellOpend()) {
			EquipShell shell = ShellFactory.createEquipShell(main);
            main.getShellRegistry().registerEquipShell(shell);
            shell.open();
        }else{
        	main.getShellRegistry().getEquipShell().open();
        }
	}
	
}