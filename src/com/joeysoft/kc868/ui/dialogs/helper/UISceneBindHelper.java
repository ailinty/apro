package com.joeysoft.kc868.ui.dialogs.helper;

import static com.joeysoft.kc868.resource.Messages.*;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.eclipse.jface.viewers.ListViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CCombo;
import org.eclipse.swt.dnd.DND;
import org.eclipse.swt.dnd.DragSource;
import org.eclipse.swt.dnd.DragSourceEvent;
import org.eclipse.swt.dnd.DragSourceListener;
import org.eclipse.swt.dnd.DropTarget;
import org.eclipse.swt.dnd.DropTargetAdapter;
import org.eclipse.swt.dnd.DropTargetEvent;
import org.eclipse.swt.dnd.TextTransfer;
import org.eclipse.swt.dnd.Transfer;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Scale;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;

import com.joeysoft.kc868.db.bean.Alarm;
import com.joeysoft.kc868.db.bean.Device;
import com.joeysoft.kc868.db.bean.DeviceKey;
import com.joeysoft.kc868.db.bean.Floor;
import com.joeysoft.kc868.db.bean.Lineate;
import com.joeysoft.kc868.db.bean.Relay;
import com.joeysoft.kc868.db.bean.Room;
import com.joeysoft.kc868.db.bean.Scene;
import com.joeysoft.kc868.db.bean.SceneAction;
import com.joeysoft.kc868.db.bean.SceneBind;
import com.joeysoft.kc868.db.bean.SensorIn;
import com.joeysoft.kc868.db.bean.SensorOut;
import com.joeysoft.kc868.db.bean.SmsIn;
import com.joeysoft.kc868.db.bean.TelIn;
import com.joeysoft.kc868.db.bean.TempSensor;
import com.joeysoft.kc868.db.bean.Threshold;
import com.joeysoft.kc868.db.bo.BOAlarm;
import com.joeysoft.kc868.db.bo.BODevice;
import com.joeysoft.kc868.db.bo.BODeviceKey;
import com.joeysoft.kc868.db.bo.BOFloor;
import com.joeysoft.kc868.db.bo.BOLineate;
import com.joeysoft.kc868.db.bo.BORoom;
import com.joeysoft.kc868.db.bo.BOScene;
import com.joeysoft.kc868.db.bo.BOSceneBind;
import com.joeysoft.kc868.db.bo.BOSensorIn;
import com.joeysoft.kc868.db.bo.BOSensorOut;
import com.joeysoft.kc868.db.bo.BOSmsIn;
import com.joeysoft.kc868.db.bo.BOSmsIn;
import com.joeysoft.kc868.db.bo.BOTelIn;
import com.joeysoft.kc868.db.bo.BOTelIn;
import com.joeysoft.kc868.db.bo.BOTempSensor;
import com.joeysoft.kc868.db.bo.BOThreshold;
import com.joeysoft.kc868.db.util.DictConst;
import com.joeysoft.kc868.db.util.MessageUtil;
import com.joeysoft.kc868.db.util.SensorUtil;
import com.joeysoft.kc868.resource.IconHolder;
import com.joeysoft.kc868.resource.Messages;
import com.joeysoft.kc868.ui.Colors;
import com.joeysoft.kc868.ui.SmartShell;
import com.joeysoft.kc868.ui.dnd.SceneBindTransfer;
import com.joeysoft.kc868.ui.helper.MessageBoxHelper;
import com.joeysoft.kc868.ui.helper.UITool;
import com.joeysoft.kc868.widgets.ImageButton;

public class UISceneBindHelper {

	private SmartShell smartShell;

	private IconHolder icons = IconHolder.getInstance();

	private BOFloor boFloor = new BOFloor();
	private BORoom boRoom = new BORoom();
	private BOSensorIn boSensorIn = new BOSensorIn();
	
	private BOTelIn boTelIn = new BOTelIn();
	private BOSmsIn boSmsIn = new BOSmsIn();
	private BOScene boScene = new BOScene();
	private BOSceneBind boSceneBind = new BOSceneBind();
	private BOLineate boLineate = new BOLineate();
	private BOTempSensor boTempSensor =  new BOTempSensor();
	private BOAlarm boAlarm = new BOAlarm();

	private ImageButton btnSave;
	private CCombo comboScene;
	private ListViewer listAction;
	
	private Scene scene;
	private SceneBind sceneBind;
	
	private boolean isBind = false;

	public UISceneBindHelper(SmartShell smartShell) {
		this.smartShell = smartShell;
	}

	public void createSceneBindUI(Composite comp) {
		comp.setVisible(false);
		clearComposite(comp);

		Transfer[] types = new Transfer[] { SceneBindTransfer.getInstance() };
		int operations = DND.DROP_MOVE | DND.DROP_COPY | DND.DROP_LINK;

		final Tree tree = new Tree(comp, SWT.BORDER);
		tree.setBounds(0, 5, 300, 400);

		final DragSource source = new DragSource(tree, operations);
		source.setTransfer(types);
		source.addDragListener(new DragSourceListener() {
			@Override
			public void dragStart(DragSourceEvent event) {
				TreeItem[] selection = tree.getSelection();
				if (selection.length > 0 && selection[0].getData(selection[0].getText()) instanceof SceneBind) {
					event.doit = true;
				} else {
					event.doit = false;
				}
			}

			@Override
			public void dragSetData(DragSourceEvent event) {
				TreeItem[] selection = tree.getSelection();
				event.data = selection[0].getData(selection[0].getText());
			}

			@Override
			public void dragFinished(DragSourceEvent event) {

			}
		});

		TreeItem tiFloors = new TreeItem(tree, SWT.NONE);
		tiFloors.setText(floor);
		for (Floor floor : boFloor.getList()) {
			TreeItem itFloor = new TreeItem(tiFloors, SWT.NONE);
			itFloor.setText(floor.getFloorName());
			for (Room room : boRoom.getListByFloor(floor.getFloor())) {
				TreeItem itRoom = new TreeItem(itFloor, SWT.NONE);
				itRoom.setText(room.getRoomName());

				// 无线输入
				TreeItem tiSensorIn = new TreeItem(itRoom, SWT.NONE);
				tiSensorIn.setText(sensor_in);
				
				for(SensorIn sensorIn : boSensorIn.getListByRoomId(room.getRoomId())){
					TreeItem ti = new TreeItem(tiSensorIn, SWT.NONE);
					String text = floor.getFloorName() + "->"
							+ room.getRoomName() + "->" + sensor_in+ "->"
							+ sensorIn.getSensorName();
					ti.setText(sensorIn.getSensorName());
					ti.setData(sensorIn.getSensorName(),
							new SceneBind("SENSOR_IN", sensorIn.getSensorId(), sensorIn.getSensorId(), text));
				}
				tiSensorIn.setExpanded(true);
				
				// 有线输入
				TreeItem tiLineate = new TreeItem(itRoom, SWT.NONE);
				tiLineate.setText(lineate);
				
				for(Lineate lineate : boLineate.getListByRoomId(room.getRoomId())){
					TreeItem ti = new TreeItem(tiLineate, SWT.NONE);
					String text = floor.getFloorName() + "->"
							+ room.getRoomName() + "->" + lineate + "->"
							+ lineate.getLineateName();
					ti.setText(lineate.getLineateName());
					ti.setData(lineate.getLineateName(),
							new SceneBind("LINEATE", ""+lineate.getLineateId(), 
									SensorUtil.getLineateCode(lineate) + lineate.getLineateId(), text));
				}
				
				// 温湿度传感器
				TreeItem tiTempSensor = new TreeItem(itRoom, SWT.NONE);
				tiTempSensor.setText(temp_sensor);
				
				for(TempSensor tempSensor : boTempSensor.getListByRoomId(room.getRoomId())){
					TreeItem tiTemp = new TreeItem(tiTempSensor, SWT.NONE);
					tiTemp.setText(tempSensor.getSensorName());
					
					TreeItem tiTemp1 = new TreeItem(tiTemp, SWT.NONE);
					tiTemp1.setText(temp);
					
					TreeItem ti = new TreeItem(tiTemp1, SWT.NONE);
					String text = floor.getFloorName() + "->"
							+ room.getRoomName() + "->" + temp_sensor + "->"
							+ tempSensor.getSensorName() + "->" + gt_temp_upper+tempSensor.getSensorUpper();
					ti.setText(gt_temp_upper+tempSensor.getSensorUpper());
					ti.setData(gt_temp_upper+tempSensor.getSensorUpper(),
							new SceneBind("TEMP_SENSOR", ""+tempSensor.getSensorId(), 
									DictConst.TABLE_PREFIX_TEMP_SRO + tempSensor.getSensorId(),  text));
					
					ti = new TreeItem(tiTemp1, SWT.NONE);
					text = floor.getFloorName() + "->"
							+ room.getRoomName() + "->" + temp_sensor + "->"
							+ tempSensor.getSensorName() + "->" + lt_temp_lower+tempSensor.getSensorLower();
					ti.setText(lt_temp_lower+tempSensor.getSensorLower());
					ti.setData(lt_temp_lower+tempSensor.getSensorLower(),
							new SceneBind("TEMP_SENSOR", ""+tempSensor.getSensorId(), 
									DictConst.TABLE_PREFIX_TEMP_SRU + tempSensor.getSensorId(), text));
					
					TreeItem tiTemp2 = new TreeItem(tiTemp, SWT.NONE);
					tiTemp2.setText(humidity);
					
					ti = new TreeItem(tiTemp2, SWT.NONE);
					text = floor.getFloorName() + "->"
							+ room.getRoomName() + "->" + temp_sensor + "->"
							+ tempSensor.getSensorName() + "->" + gt_humidity_upper+tempSensor.getHumidityUpper();
					ti.setText(gt_humidity_upper+tempSensor.getHumidityUpper());
					ti.setData(gt_humidity_upper+tempSensor.getHumidityUpper(),
							new SceneBind("TEMP_SENSOR", ""+tempSensor.getSensorId(), 
									DictConst.TABLE_PREFIX_TEMP_SHO + tempSensor.getSensorId(),  text));
					
					ti = new TreeItem(tiTemp2, SWT.NONE);
					text = floor.getFloorName() + "->"
							+ room.getRoomName() + "->" + temp_sensor+ "->"
							+ tempSensor.getSensorName() + "->" + lt_humidity_lower+tempSensor.getHumidityLower();
					ti.setText(lt_humidity_lower+tempSensor.getHumidityLower());
					ti.setData(lt_humidity_lower+tempSensor.getHumidityLower(),
							new SceneBind("TEMP_SENSOR", ""+tempSensor.getSensorId(), 
									DictConst.TABLE_PREFIX_TEMP_SHU + tempSensor.getSensorId(), text));
				}
				
				itRoom.setExpanded(true);
			}
			itFloor.setExpanded(true);
		}
		tiFloors.setExpanded(true);

		TreeItem tiTelIn = new TreeItem(tree, SWT.NONE);
		tiTelIn.setText(new String[] { tel_in });
		java.util.List<TelIn> telInList = boTelIn.getList();
		for (TelIn telIn : telInList) {
			if (StringUtils.isNotEmpty(telIn.getTelName())) {
				String name = telIn.getTelName() + "->" + telIn.getTelPhone();
				TreeItem item = new TreeItem(tiTelIn, SWT.NONE);
				item.setText(name);
				item.setData(name, new SceneBind("TEL_IN",
						telIn.getTelId(), telIn.getTelId(), name));
			}
		}
		tiTelIn.setExpanded(true);

		TreeItem tiSmsIn = new TreeItem(tree, SWT.NONE);
		tiSmsIn.setText(new String[] { sms_in });
		java.util.List<SmsIn> smsOutList = boSmsIn.getList();
		for (SmsIn smsOut : smsOutList) {
			if (StringUtils.isNotEmpty(smsOut.getSmsName())) {
				TreeItem item = new TreeItem(tiSmsIn, SWT.NONE);
				item.setText(smsOut.getSmsName());
				item.setData(smsOut.getSmsName(), new SceneBind("SMS_OUT",
						smsOut.getSmsId(), smsOut.getSmsId(), smsOut.getSmsName()));
			}
		}
		tiSmsIn.setExpanded(true);
		
		TreeItem tiAlarm = new TreeItem(tree, SWT.NONE);
		tiAlarm.setText(new String[] {Messages.alarm });
		for (Alarm alarm : boAlarm.getList()) {
			if(alarm.getAlarmId() <= 0){
				continue;
			}
			if (StringUtils.isNotEmpty(Messages.alarm+alarm.getAlarmId())) {
				TreeItem item = new TreeItem(tiAlarm, SWT.NONE);
				item.setText(Messages.alarm+alarm.getAlarmId());
				item.setData(Messages.alarm+alarm.getAlarmId(), new SceneBind("ALARM",
						""+alarm.getAlarmId(), DictConst.TABLE_PREFIX_ALARM + alarm.getAlarmId(), Messages.alarm+alarm.getAlarmId()));
			}
		}
		tiAlarm.setExpanded(true);

		Composite compLeft = new Composite(comp, SWT.NONE);
		compLeft.setBounds(400, 5, 300, 400);

		Label label = UITool.createLabel(compLeft, scene_mode);
		label.setBounds(0, 0, 50, 20);

		comboScene = UITool.createCCombo(compLeft);
		comboScene.setBounds(70, 0, 150, 20);
		comboScene.setBackground(Colors.WHITE);
		ComboHelper.initSceneComboList(comboScene, boScene.getList());
		comboScene.addSelectionListener(new SelectionAdapter(){
			@Override
			public void widgetSelected(SelectionEvent e) {
				changeScene();
			}
        });
		comboScene.select(0);

		label = UITool.createLabel(compLeft, trigger_source);
		label.setBounds(0, 100, 50, 20);

		listAction = new ListViewer(compLeft, SWT.BORDER);
		listAction.getList().setBounds(0, 120, 300, 230);

		final DropTarget target = new DropTarget(listAction.getList(), operations);
		target.setTransfer(types);
		target.addDropListener(new DropTargetAdapter() {
			@Override
			public void drop(DropTargetEvent event) {
				if (event.data == null) {
					event.detail = DND.DROP_NONE;
					return;
				}
				addListAction((SceneBind) event.data);
			}
		});
		
		final DragSource source2 = new DragSource (listAction.getList(), operations);
		source2.setTransfer(types);
		source2.addDragListener(new DragSourceListener() {
			@Override
			public void dragStart(DragSourceEvent event) {
				if (listAction.getList().getSelectionCount() > 0 &&
						(listAction.getData(listAction.getList().getSelection()[0])) instanceof SceneBind) {
					event.doit = true;
				} else {
					event.doit = false;
				}
			}

			@Override
			public void dragSetData(DragSourceEvent event) {
				event.data = (SceneBind)(listAction.getData(listAction.getList().getSelection()[0]));
			}

			@Override
			public void dragFinished(DragSourceEvent event) {
				listAction.getList().remove(listAction.getList().getSelection()[0]);
			}
		});

		btnSave = UITool.createImageButton(compLeft, button_save,
				icons.getImage(IconHolder.bmpBtnLogin),
				icons.getImage(IconHolder.bmpBtnLoginUp),
				icons.getImage(IconHolder.bmpBtnLoginOn));
		btnSave.setFont(Colors.GLOBAL_FONT);
		btnSave.setBounds(100, 360, 114, 41);
		btnSave.addMouseListener(new MouseAdapter() {
            public void mouseUp(MouseEvent e) {
            	doSend();
            }
        });
		
		changeScene();
		comp.setVisible(true);
	}
	
	private void addListAction(SceneBind bind){
		if(listAction.getList().getItemCount() < 1){
			scene = (Scene)comboScene.getData(comboScene.getText());
			
			if("Y".equals(scene.getSceneSWOK()) && "SENSOR_IN".equals(bind.getSourceTable())){ // 有设防，不可以是无线安防类输入触发源
				MessageBoxHelper.openError(smartShell.getShell(), scene.getSceneName()+error_scene_sensor_out);
			}else{
				listAction.add(bind.getSourceText());
				listAction.setData(bind.getSourceText(), bind);
			}
		}else{
			MessageBoxHelper.openError(smartShell.getShell(), error_trigger_source_only);
		}
		
	}
	
	/**
	 * 保存情景触发源
	 */
	private void doSend(){
		isBind  = false;
		scene = (Scene)comboScene.getData(comboScene.getText());
		
		int count = listAction.getList().getItemCount();
		if(count >= 1){
			isBind = true;
			sceneBind = (SceneBind)listAction.getData(listAction.getList().getItem(0));
			sceneBind.setSceneId(scene.getSceneId());
			String sourceCmd = sceneBind.getSourceCmd();
			//smartShell.sendEventConnect(scene.getSceneId(), sourceCmd);
			// 先解绑
			smartShell.sendEventDisConnect(scene.getSceneId(), sourceCmd);
		}else{
			java.util.List<SceneBind> bindList =  boSceneBind.getListBySceneId(scene.getSceneId());
			for(SceneBind bind : bindList){
				smartShell.sendEventDisConnect(scene.getSceneId(), bind.getSourceCmd());
			}
		}
	}
	
	public boolean isBind(){
		return isBind;
	}
	
	// 绑定
	public void sendEventConnect(){
		smartShell.sendEventConnect(scene.getSceneId(), sceneBind.getSourceCmd());
	}
	
	public void doSave(){
		if(boSceneBind.insertOrUpdate(sceneBind)){
			MessageBoxHelper.openInformation(smartShell.getShell(), message_opreate_success);
		}else{
			MessageBoxHelper.openError(smartShell.getShell(), message_opreate_error);
		}
	}
	
	public void doDelete(){
		if(boSceneBind.deleteBySenceId(scene.getSceneId())){
			MessageBoxHelper.openInformation(smartShell.getShell(), message_opreate_success);
		}else{
			MessageBoxHelper.openError(smartShell.getShell(), message_opreate_error);
		}
	}
	
	private void changeScene(){
		if(StringUtils.isNotEmpty(comboScene.getText())){
			Scene scene = (Scene)comboScene.getData(comboScene.getText());
			java.util.List<SceneBind> bindList =  boSceneBind.getListBySceneId(scene.getSceneId());
			listAction.getList().removeAll();
			for(SceneBind bind : bindList){
				listAction.add(bind.getSourceText());
				listAction.setData(bind.getSourceText(), bind);
			}
		}
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
}
