package com.joeysoft.kc868.ui.helper;

import com.joeysoft.kc868.SystemConfig;
import com.joeysoft.kc868.ui.DebugShell;
import com.joeysoft.kc868.ui.EquipShell;
import com.joeysoft.kc868.ui.InfoShell;
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
 * 窗口注册类
 * @author JOEY
 *
 */
public class ShellRegistry {
	
	private TempSensorDialog tempSensorDialog;
	
	private SensorInDialog sensorInDialog;
	private TransferDialog transferDialog;
	private DeviceDialog deviceDialog;
	private LineateDialog lineateDialog;
	private SensorOutDialog sensorOutDialog;
	private FloorDialog floorDialog;
	private RoomDialog roomDialog;
	private RelayDialog relayDialog;
	private SelectTransferDialog selectTransferDialog;
	private TelInDialog telInDialog;
	private TelOutDialog telOutDialog;
	private SmsInDialog smsInDialog;
	private SmsOutDialog smsOutDialog;
	
	
	private SceneShell sceneShell;
	private SmartShell smartShell;
	private EquipShell equipShell;
	private RoomShell roomShell;
	private SafetyShell safetyShell;
	private SystemShell systemShell;
	private DebugShell debugShell;
	private InfoShell infoShell;
	
	private VidiconShell vidiconShell;
	
	public VidiconShell getVidiconShell() {
		return vidiconShell;
	}
	
	public void registerVidiconShell(VidiconShell vidiconShell){
		this.vidiconShell = vidiconShell;
	}
	
	public void deregisterVidiconShell(){
		this.vidiconShell = null;
	}
	
	public boolean isVidiconShellOpend(){
		return this.vidiconShell != null;
	}
	
	public SceneShell getSceneShell() {
		return sceneShell;
	}
	
	public void registerSceneShell(SceneShell sceneShell){
		this.sceneShell = sceneShell;
	}
	
	public void deregisterSceneShell(){
		SystemConfig.getInstance().setMainPoint(sceneShell.getShell().getLocation());
		this.sceneShell = null;
	}
	
	public boolean isSceneShellOpend(){
		return this.infoShell != null;
	}
	
	public InfoShell getInfoShell() {
		return infoShell;
	}
	
	public void registerInfoShell(InfoShell infoShell){
		this.infoShell = infoShell;
	}
	
	public void deregisterInfoShell(){
		SystemConfig.getInstance().setMainPoint(infoShell.getShell().getLocation());
		this.infoShell = null;
	}
	
	public boolean isInfoShellOpend(){
		return this.infoShell != null;
	}
	
	public TelInDialog getTelInDialog() {
		return telInDialog;
	}
	
	public void registerTelInDialog(TelInDialog telInDialog){
		this.telInDialog = telInDialog;
	}
	
	public void deregisterTelInDialog(){
		this.telInDialog = null;
	}
	
	public boolean isTelInDialogOpend(){
		return this.telInDialog != null;
	}
	
	public TelOutDialog getTelOutDialog() {
		return telOutDialog;
	}
	
	public void registerTelOutDialog(TelOutDialog telOutDialog){
		this.telOutDialog = telOutDialog;
	}
	
	public void deregisterTelOutDialog(){
		this.telOutDialog = null;
	}
	
	public boolean isTelOutDialogOpend(){
		return this.telOutDialog != null;
	}
	
	public SmsInDialog getSmsInDialog() {
		return smsInDialog;
	}
	
	public void registerSmsInDialog(SmsInDialog smsInDialog){
		this.smsInDialog = smsInDialog;
	}
	
	public void deregisterSmsInDialog(){
		this.smsInDialog = null;
	}
	
	public boolean isSmsInDialogOpend(){
		return this.smsInDialog != null;
	}
	
	public SmsOutDialog getSmsOutDialog() {
		return smsOutDialog;
	}
	
	public void registerSmsOutDialog(SmsOutDialog smsOutDialog){
		this.smsOutDialog = smsOutDialog;
	}
	
	public void deregisterSmsOutDialog(){
		this.smsOutDialog = null;
	}
	
	public boolean isSmsOutDialogOpend(){
		return this.smsOutDialog != null;
	}
	
	public SmartShell getSmartShell() {
		return smartShell;
	}
	
	public void registerSmartShell(SmartShell smartShell){
		this.smartShell = smartShell;
	}
	
	public void deregisterSmartShell(){
		SystemConfig.getInstance().setMainPoint(smartShell.getShell().getLocation());
		this.smartShell = null;
	}
	
	public boolean isSmartShellOpend(){
		return this.smartShell != null;
	}
	
	public DebugShell getDebugShell() {
		return debugShell;
	}
	
	public void registerDebugShell(DebugShell debugShell){
		this.debugShell = debugShell;
	}
	
	public void deregisterDebugShell(){
		this.debugShell = null;
	}
	
	public boolean isDebugShellOpend(){
		return this.debugShell != null;
	}
	
	public SelectTransferDialog getSelectTransferDialog() {
		return selectTransferDialog;
	}
	
	public void registerSelectTransferDialog(SelectTransferDialog selectTransferDialog){
		this.selectTransferDialog = selectTransferDialog;
	}
	
	public void deregisterSelectTransferDialog(){
		this.selectTransferDialog = null;
	}
	
	public boolean isSelectTransferDialogOpend(){
		return this.selectTransferDialog != null;
	}
	
	public SystemShell getSystemShell() {
		return systemShell;
	}
	
	public void registerSystemShell(SystemShell systemShell){
		this.systemShell = systemShell;
	}
	
	public void deregisterSystemShell(){
		SystemConfig.getInstance().setMainPoint(systemShell.getShell().getLocation());
		this.systemShell = null;
	}
	
	public boolean isSystemShellOpend(){
		return this.systemShell != null;
	}
	
	public SafetyShell getSafetyShell() {
		return safetyShell;
	}
	
	public void registerSafetyShell(SafetyShell safetyShell){
		this.safetyShell = safetyShell;
	}
	
	public void deregisterSafetyShell(){
		SystemConfig.getInstance().setMainPoint(safetyShell.getShell().getLocation());
		this.safetyShell = null;
	}
	
	public boolean isSafetyShellOpend(){
		return this.safetyShell != null;
	}
		
	public RoomShell getRoomShell() {
		return roomShell;
	}
	
	public void registerRoomShell(RoomShell roomShell){
		this.roomShell = roomShell;
	}
	
	public void deregisterRoomShell(){
		SystemConfig.getInstance().setMainPoint(roomShell.getShell().getLocation());
		this.roomShell = null;
	}
	
	public boolean isRoomShellOpend(){
		return this.roomShell != null;
	}

	public TempSensorDialog getTempSensorDialog() {
		return tempSensorDialog;
	}
	
	public void registerTempSensorDialog(TempSensorDialog tempSensorDialog){
		this.tempSensorDialog = tempSensorDialog;
	}
	
	public void deregisterTempSensorDialog(){
		this.tempSensorDialog = null;
	}
	
	public boolean isTempSensorDialogOpend(){
		return this.tempSensorDialog != null;
	}
	
	public SensorInDialog getSensorInDialog() {
		return sensorInDialog;
	}
	
	public void registerSensorInDialog(SensorInDialog sensorInDialog){
		this.sensorInDialog = sensorInDialog;
	}
	
	public void deregisterSensorInDialog(){
		this.sensorInDialog = null;
	}
	
	public boolean isSensorInDialogOpend(){
		return this.sensorInDialog != null;
	}
	
	public TransferDialog getTransferDialog() {
		return transferDialog;
	}
	
	public void registerTransferDialog(TransferDialog transferDialog){
		this.transferDialog = transferDialog;
	}
	
	public void deregisterTransferDialog(){
		this.transferDialog = null;
	}
	
	public boolean isTransferDialogOpend(){
		return this.transferDialog != null;
	}
	
	public DeviceDialog getDeviceDialog() {
		return deviceDialog;
	}
	
	public void registerDeviceDialog(DeviceDialog deviceDialog){
		this.deviceDialog = deviceDialog;
	}
	
	public void deregisterDeviceDialog(){
		this.deviceDialog = null;
	}
	
	public boolean isDeviceDialogOpend(){
		return this.deviceDialog != null;
	}
	
	public LineateDialog getLineateDialog() {
		return lineateDialog;
	}
	
	public void registerLineateDialog(LineateDialog lineateDialog){
		this.lineateDialog = lineateDialog;
	}
	
	public void deregisterLineateDialog(){
		this.lineateDialog = null;
	}
	
	public boolean isLineateDialogOpend(){
		return this.lineateDialog != null;
	}
	
	public SensorOutDialog getSensorOutDialog() {
		return sensorOutDialog;
	}
	
	public void registerSensorOutDialog(SensorOutDialog sensorOutDialog){
		this.sensorOutDialog = sensorOutDialog;
	}
	
	public void deregisterSensorOutDialog(){
		this.sensorOutDialog = null;
	}
	
	public boolean isSensorOutDialogOpend(){
		return this.sensorOutDialog != null;
	}
	
	public void registerRelayDialog(RelayDialog relayDialog){
		this.relayDialog = relayDialog;
	}
	
	public void deregisterRelayDialog(){
		this.relayDialog = null;
	}
	
	public boolean isRelayDialogOpend(){
		return this.relayDialog != null;
	}
	
	public RoomDialog getRoomDialog() {
		return roomDialog;
	}
	
	public void registerRoomDialog(RoomDialog roomDialog){
		this.roomDialog = roomDialog;
	}
	
	public void deregisterRoomDialog(){
		this.roomDialog = null;
	}
	
	public boolean isRoomDialogOpend(){
		return this.roomDialog != null;
	}
	
	public FloorDialog getFloorDialog() {
		return floorDialog;
	}
	
	public void registerFloorDialog(FloorDialog floorDialog){
		this.floorDialog = floorDialog;
	}
	
	public void deregisterFloorDialog(){
		this.floorDialog = null;
	}
	
	public boolean isFloorDialogOpend(){
		return this.floorDialog != null;
	}
	
	public EquipShell getEquipShell() {
		return equipShell;
	}
	
	public void registerEquipShell(EquipShell equipShell){
		this.equipShell = equipShell;
	}
	
	public void deregisterEquipShell(){
		SystemConfig.getInstance().setMainPoint(equipShell.getShell().getLocation());
		this.equipShell = null;
	}
	
	public boolean isEquipShellOpend(){
		return this.equipShell != null;
	}
	
	public void closeAll(){
		if(transferDialog != null) transferDialog.close();
		if(deviceDialog != null) deviceDialog.close();
		if(lineateDialog != null) lineateDialog.close();
		if(sensorOutDialog != null) sensorOutDialog.close();
		if(floorDialog != null) floorDialog.close();
		if(roomDialog != null) roomDialog.close();
		if(relayDialog != null) relayDialog.close();
		if(equipShell != null) equipShell.close();
		if(roomShell != null) roomShell.close();
		if(tempSensorDialog != null) tempSensorDialog.close();
		if(sensorInDialog != null) sensorInDialog.close();
		if(selectTransferDialog != null) selectTransferDialog.close();
		if(telInDialog != null) telInDialog.close();
		if(telOutDialog != null) telOutDialog.close();
		if(smsInDialog != null) smsInDialog.close();
		if(smsOutDialog != null) smsOutDialog.close();
		if(sceneShell != null) sceneShell.close();
		if(smartShell != null) smartShell.close();
		if(safetyShell != null) safetyShell.close();
		if(systemShell != null) systemShell.close();
		if(debugShell != null) debugShell.close();
		if(infoShell != null) infoShell.close();
		if(vidiconShell != null) vidiconShell.close();
	}
}
