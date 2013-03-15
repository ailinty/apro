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
import org.eclipse.swt.dnd.Transfer;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;

import com.joeysoft.kc868.SystemConfig;
import com.joeysoft.kc868.db.bean.Device;
import com.joeysoft.kc868.db.bean.DeviceKey;
import com.joeysoft.kc868.db.bean.Floor;
import com.joeysoft.kc868.db.bean.Relay;
import com.joeysoft.kc868.db.bean.Room;
import com.joeysoft.kc868.db.bean.Scene;
import com.joeysoft.kc868.db.bean.SceneAction;
import com.joeysoft.kc868.db.bean.SensorOut;
import com.joeysoft.kc868.db.bean.SmsOut;
import com.joeysoft.kc868.db.bean.TelOut;
import com.joeysoft.kc868.db.bean.Threshold;
import com.joeysoft.kc868.db.bo.BODevice;
import com.joeysoft.kc868.db.bo.BODeviceKey;
import com.joeysoft.kc868.db.bo.BOFloor;
import com.joeysoft.kc868.db.bo.BOIcon;
import com.joeysoft.kc868.db.bo.BORelay;
import com.joeysoft.kc868.db.bo.BORoom;
import com.joeysoft.kc868.db.bo.BOScene;
import com.joeysoft.kc868.db.bo.BOSceneAction;
import com.joeysoft.kc868.db.bo.BOSensorOut;
import com.joeysoft.kc868.db.bo.BOSmsOut;
import com.joeysoft.kc868.db.bo.BOTelOut;
import com.joeysoft.kc868.db.bo.BOThreshold;
import com.joeysoft.kc868.db.util.MessageUtil;
import com.joeysoft.kc868.resource.IconHolder;
import com.joeysoft.kc868.resource.IconUtil;
import com.joeysoft.kc868.ui.Colors;
import com.joeysoft.kc868.ui.SmartShell;
import com.joeysoft.kc868.ui.dnd.SceneActionTransfer;
import com.joeysoft.kc868.ui.helper.MessageBoxHelper;
import com.joeysoft.kc868.ui.helper.UITool;
import com.joeysoft.kc868.widgets.ImageButton;

public class UISceneHelper {

	private SmartShell smartShell;

	private IconHolder icons = IconHolder.getInstance();

	private BOIcon boIcon = new BOIcon();
	private BOFloor boFloor = new BOFloor();
	private BORoom boRoom = new BORoom();
	private BODevice boDevice = new BODevice();
	private BOSensorOut boSensorOut = new BOSensorOut();
	private BOTelOut boTelOut = new BOTelOut();
	private BOSmsOut boSmsOut = new BOSmsOut();
	private BODeviceKey boDeviceKey = new BODeviceKey();
	private BOScene boScene = new BOScene();
	private BORelay boRelay = new BORelay();
	private BOThreshold boThreshold = new BOThreshold();
	private BOSceneAction boSceneAction = new BOSceneAction();

	private ImageButton btnSave;
	private Text textScenceName;
	private CCombo comboScene, comboIcon;
	private Label lbIcon;
	private ListViewer listAction;
	
	private List<SceneAction> actionList;
	private Scene scene;
	private int relayThreshold = 0;

	public UISceneHelper(SmartShell smartShell) {
		this.smartShell = smartShell;
	}

	/**
	 * 初始化
	 */
	public void initScene(){
		int count = boScene.getCount();
		Threshold threshold = boThreshold.get("SCENE");
		if(threshold.getThreshold() > count){ // 记录没有到阀值，添加
			for(int i=count; i<threshold.getThreshold(); i++){
				Scene scene = new Scene();
				scene.setSceneName(scene_mode + (i + 1));
				boScene.insert(scene);
			}
		}
	}
	
	public void createSceneUI(Composite comp) {
		initScene();
		comp.setVisible(false);
		clearComposite(comp);

		Transfer[] types = new Transfer[] { SceneActionTransfer.getInstance() };
		int operations = DND.DROP_MOVE | DND.DROP_COPY | DND.DROP_LINK;

		final Tree tree = new Tree(comp, SWT.BORDER);
		tree.setBounds(0, 5, 300, 400);

		final DragSource source = new DragSource(tree, operations);
		source.setTransfer(types);
		source.addDragListener(new DragSourceListener() {
			@Override
			public void dragStart(DragSourceEvent event) {
				TreeItem[] selection = tree.getSelection();
				if (selection.length > 0 && selection[0].getData(selection[0].getText()) instanceof SceneAction) {
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

		if(SystemConfig.getInstance().isEngine()){
			 // 继电器阀值
			Threshold threshold = boThreshold.get("RELAY");
			relayThreshold = threshold.getThreshold();
		}
		
		TreeItem tiFloors = new TreeItem(tree, SWT.NONE);
		tiFloors.setText(floor);
		for (Floor floor : boFloor.getList()) {
			TreeItem itFloor = new TreeItem(tiFloors, SWT.NONE);
			itFloor.setText(floor.getFloorName());
			for (Room room : boRoom.getListByFloor(floor.getFloor())) {
				TreeItem itRoom = new TreeItem(itFloor, SWT.NONE);
				itRoom.setText(room.getRoomName());

				// 常规设备
				TreeItem tiNorDevice = new TreeItem(itRoom, SWT.NONE);
				tiNorDevice.setText(normal_device);

				for (Device device : boDevice.getListByRoomId(room.getRoomId())) {
					TreeItem itDevice = new TreeItem(tiNorDevice, SWT.NONE);
					itDevice.setText(device.getDeviceName());
					/*java.util.List<DeviceKey> deviceKeyList = boDeviceKey
							.getListByDeviceId(device.getDeviceId());*/
					java.util.List<DeviceKey> deviceKeyList = boDeviceKey.getListValidByDeviceId(device.getDeviceId());
					
						for (int k = 0; k < deviceKeyList.size(); k++) {
							final DeviceKey dk = deviceKeyList.get(k);
							TreeItem item2 = new TreeItem(itDevice, SWT.NONE);
							String name = StringUtils.isEmpty(dk.getKeyName()) ? MessageUtil
									.getMessage(dk.getKeyType()) : dk
									.getKeyName();
							item2.setText(name);
							String text = floor.getFloorName() + "->"
									+ room.getRoomName() + "->" + normal_device + "->"
									+ device.getDeviceName() + "->" + name;
							item2.setData(
									name,
									new SceneAction(floor.getFloor(), room
											.getRoomId(), device.getDeviceId(),
											dk.getDeviceKeyId(), dk
													.getSensorTable(), dk
													.getSensorId(), text));
						}
					// itDevice.setExpanded(true);
				}
				tiNorDevice.setExpanded(true);
				// 非常规设备
				TreeItem tiSensorOut = new TreeItem(itRoom, SWT.NONE);
				tiSensorOut.setText(not_normal_device);

				java.util.List<SensorOut> sensorOutList = boSensorOut
						.getListByRoomId(room.getRoomId());
				for (SensorOut sensorOutDevice : sensorOutList) {
					TreeItem item = new TreeItem(tiSensorOut, SWT.NONE);
					item.setText(sensorOutDevice.getSensorName());
					String text = floor.getFloorName() + "->"
							+ room.getRoomName() + "->" + not_normal_device + "->"
							+ sensorOutDevice.getSensorName();
					item.setData(
							sensorOutDevice.getSensorName(),
							new SceneAction(sensorOutDevice.getFloor(), sensorOutDevice.getRoomId(), "SENSOR_OUT", sensorOutDevice
									.getSensorId(), text));
				}
				tiSensorOut.setExpanded(true);
				
				 if(SystemConfig.getInstance().isEngine()){
					// 继电器
					TreeItem tiRelay = new TreeItem(itRoom, SWT.NONE);
					tiRelay.setText(relay_out);

					for (Relay relay : boRelay.getListByRoomId(room.getRoomId())) {
						TreeItem item = new TreeItem(tiRelay, SWT.NONE);
						item.setText(relay.getRelayName() + relay.getRelayOnName());
						String text = floor.getFloorName() + "->"
								+ room.getRoomName() + "->" + relay_out + "->"
								+ relay.getRelayName() + relay.getRelayOnName();
						item.setData(
								relay.getRelayName() + relay.getRelayOnName(),
								new SceneAction(relay.getFloor(), relay.getRoomId(), "RELAY", "RL"+relay.getRelayId(), text));
						
						item = new TreeItem(tiRelay, SWT.NONE);
						item.setText(relay.getRelayName() + relay.getRelayOffName());
						text = floor.getFloorName() + "->"
								+ room.getRoomName() + "->" + relay_out + "->"
								+ relay.getRelayName() + relay.getRelayOffName();
						item.setData(
								relay.getRelayName() + relay.getRelayOffName(),
								new SceneAction(relay.getFloor(), relay.getRoomId(), "RELAY", "RL"+(relay.getRelayId() + relayThreshold), text));
					}
					tiRelay.setExpanded(true);
				 }
				itRoom.setExpanded(true);
			}
			itFloor.setExpanded(true);
		}
		tiFloors.setExpanded(true);
		 if(SystemConfig.getInstance().hasSim()){
			 TreeItem tiTelOut = new TreeItem(tree, SWT.NONE);
				tiTelOut.setText(new String[] { tel_out });
				java.util.List<TelOut> telOutList = boTelOut.getList();
				for (TelOut telOut : telOutList) {
					if (StringUtils.isNotEmpty(telOut.getTelName())) {
						String name = telOut.getTelName() + "->" + telOut.getTelPhone();
						TreeItem item = new TreeItem(tiTelOut, SWT.NONE);
						item.setText(name);
						item.setData(name, new SceneAction("TEL_OUT",
								telOut.getTelId(), name));
					}
				}
				tiTelOut.setExpanded(true);

				TreeItem tiSmsOut = new TreeItem(tree, SWT.NONE);
				tiSmsOut.setText(new String[] { sms_out });
				java.util.List<SmsOut> smsOutList = boSmsOut.getList();
				for (SmsOut smsOut : smsOutList) {
					if (StringUtils.isNotEmpty(smsOut.getSmsName())) {
						TreeItem item = new TreeItem(tiSmsOut, SWT.NONE);
						item.setText(smsOut.getSmsName());
						item.setData(smsOut.getSmsName(), new SceneAction("SMS_OUT",
								smsOut.getSmsId(), smsOut.getSmsName()));
					}
				}
				tiSmsOut.setExpanded(true);
		 }
		 
		 TreeItem tiSw = new TreeItem(tree, SWT.NONE);
		tiSw.setText(new String[] { safety_bugle });
		TreeItem item = new TreeItem(tiSw, SWT.NONE);
		item.setText(safety_on);
		item.setData(safety_on, new SceneAction("SW", "SWOK", safety_on));
		
		item = new TreeItem(tiSw, SWT.NONE);
		item.setText(safety_off);
		item.setData(safety_off, new SceneAction("SW", "SWNO", safety_off));
			
		if(SystemConfig.getInstance().isEngine()){
			item = new TreeItem(tiSw, SWT.NONE);
			item.setText(bugle_on);
			item.setData(bugle_on, new SceneAction("SW", "SP1", bugle_on));
			
			item = new TreeItem(tiSw, SWT.NONE);
			item.setText(bugle_off);
			item.setData(bugle_off, new SceneAction("SW", "SP2", bugle_off));
		 }

		tiSw.setExpanded(true);
		
		Composite compLeft = new Composite(comp, SWT.NONE);
		compLeft.setBounds(350, 5, 380, 400);

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

		label = UITool.createLabel(compLeft, scene_name);
		label.setBounds(0, 30, 50, 20);

		textScenceName = UITool.createSingleText(compLeft);
		textScenceName.setBounds(70, 30, 150, 20);
		textScenceName.setBackground(Colors.WHITE);
		
		label = UITool.createLabel(compLeft, scene_icon);
		label.setBounds(0, 60, 50, 20);

		lbIcon = UITool.createLabel(compLeft, "");
		lbIcon.setBounds(65, 53, 32, 32);
		
		comboIcon = UITool.createCCombo(compLeft);
		comboIcon.setBounds(100, 60, 120, 20);
		comboIcon.setBackground(Colors.WHITE);
		ComboHelper.initSceneIconComboList(comboIcon, boIcon.getListByType("SCENE"));
		comboIcon.select(0);
		comboIcon.addSelectionListener(new SelectionAdapter() {
            public void widgetSelected(SelectionEvent e) {
            	lbIcon.setImage(icons.getImageByName(IconUtil.getImage32(comboIcon.getText())));
            }
        });
		
		lbIcon.setImage(icons.getImageByName(IconUtil.getImage32(comboIcon.getText())));

		listAction = new ListViewer(compLeft, SWT.BORDER);
		listAction.getList().setBounds(0, 120, 300, 230);
		
		Button upBtn = UITool.createButton(compLeft, "︽"); 
		upBtn.setBounds(320, 170, 30, 30);
		upBtn.addMouseListener(new MouseAdapter() {
            public void mouseUp(MouseEvent e) {
            	if(listAction.getList().getSelectionCount() > 0){
            		SceneAction sceneAction = (SceneAction)(listAction.getData(listAction.getList().getSelection()[0]));
            		int index = listAction.getList().getSelectionIndex() - 1;
            		listAction.getList().remove(listAction.getList().getSelection()[0]);
            		if(index < 0 ) index = 0;
            		addListAction(sceneAction, index);
            	}
            }
        });		
		Button downBtn = UITool.createButton(compLeft, "︾"); 
		downBtn.setBounds(320, 230, 30, 30);
		downBtn.addMouseListener(new MouseAdapter() {
            public void mouseUp(MouseEvent e) {
            	if(listAction.getList().getSelectionCount() > 0){
            		SceneAction sceneAction = (SceneAction)(listAction.getData(listAction.getList().getSelection()[0]));
            		int index = listAction.getList().getSelectionIndex() + 1;
            		listAction.getList().remove(listAction.getList().getSelection()[0]);
            		
            		if(index > listAction.getList().getItemCount() ) index = listAction.getList().getItemCount();
            		addListAction(sceneAction, index);
            	}
            }
        });		

		final DropTarget target = new DropTarget(listAction.getList(), operations);
		target.setTransfer(types);
		target.addDropListener(new DropTargetAdapter() {
			@Override
			public void drop(DropTargetEvent event) {
				if (event.data == null) {
					event.detail = DND.DROP_NONE;
					return;
				}
				addListAction((SceneAction) event.data, -1);
			}
		});
		
		final DragSource source2 = new DragSource (listAction.getList(), operations);
		source2.setTransfer(types);
		source2.addDragListener(new DragSourceListener() {
			@Override
			public void dragStart(DragSourceEvent event) {
				if (listAction.getList().getSelectionCount() > 0 && (listAction.getData(listAction.getList().getSelection()[0])) instanceof SceneAction) {
					event.doit = true;
				} else {
					event.doit = false;
				}
			}

			@Override
			public void dragSetData(DragSourceEvent event) {
				event.data = (SceneAction)(listAction.getData(listAction.getList().getSelection()[0]));
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
	
	private boolean hasListAction(SceneAction sceneAction){
		for(int i=0; i<listAction.getList().getItemCount(); i++){
			SceneAction action = (SceneAction)listAction.getData(listAction.getList().getItem(i));
			if(sceneAction.getActionId() == action.getActionId()){
				return true;
			}
		}
		return false;
	}
	
	private void addListAction(SceneAction sceneAction, int index){
		if(listAction.getList().getItemCount() < 10){
			boolean has = false;
			boolean hasTelOut = false;
			for(int i=0; i<listAction.getList().getItemCount(); i++){
				SceneAction action = (SceneAction)listAction.getData(listAction.getList().getItem(i));
				// 情景模式定义中，只能选一个电话输出，不能同时选择两个电话号码拨出
				if("TEL_OUT".equals(action.getSensorTable())){
					hasTelOut = true;
				}
				/*if(sceneAction.getText().equals(action.getText()) && sceneAction.getSensorId().equals(action.getSensorId())){
					has = true;
					break;
				}*/
			}
			if(!has){
				if(index >= 0){
					if(index > listAction.getList().getItemCount()){
						index = listAction.getList().getItemCount();
					}
					listAction.getList().add(sceneAction.getText(), index);
					listAction.getList().setSelection(index);
				}else{
					listAction.add(sceneAction.getText());
					listAction.getList().setSelection(listAction.getList().getItemCount());
				}
				
				listAction.setData(sceneAction.getText(), sceneAction);
				
			}else{
				MessageBoxHelper.openError(smartShell.getShell(), error_action_exist);
			}
			if(hasTelOut && "TEL_OUT".equals(sceneAction.getSensorTable())){
				MessageBoxHelper.openError(smartShell.getShell(), error_tel_out_only);
			}
		}else{
			MessageBoxHelper.openError(smartShell.getShell(), error_scene_has_ten_action);
		}
		
	}
	
	/**
	 * 保存情景模式
	 */
	private void doSend(){
		scene = (Scene)comboScene.getData(comboScene.getText());
		scene.setSceneName(textScenceName.getText());
		scene.setSceneIcon(comboIcon.getText());
		
		actionList = new ArrayList<SceneAction>();
		int count = listAction.getList().getItemCount();
		int lost = 10 - count;
		
		StringBuffer actionSB = new StringBuffer();
		
		byte[] actionByte = new byte[count * 4 + lost * 2];
		int index = 0;
		
		for(int i=0; i<count; i++){
			SceneAction sceneAction = (SceneAction)listAction.getData(listAction.getList().getItem(i));
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
			
			sceneAction.setSceneId(scene.getSceneId());
			actionList.add(sceneAction);
			
			actionSB.append(sceneAction.getSensorId());
		}
		
		for(int i=0; i<lost; i++){
			actionByte[index++] = (byte)(0xFF);
			actionByte[index++] = (byte)(0xFF);
		}
		
		smartShell.sendEventWrite(scene.getSceneId(), actionByte, actionSB.toString());
	}
	
	public void doSave(){
		if(boScene.update(scene, actionList)){
			ComboHelper.initSceneComboList(comboScene, boScene.getList());
			comboScene.setText(scene.getSceneName());
			MessageBoxHelper.openInformation(smartShell.getShell(), message_opreate_success);
		}else{
			MessageBoxHelper.openError(smartShell.getShell(), message_opreate_error);
		}
	}
	
	private void changeScene(){
		if(StringUtils.isNotEmpty(comboScene.getText())){
			Scene scene = (Scene)comboScene.getData(comboScene.getText());
			textScenceName.setText(scene.getSceneName());
			comboIcon.setText(scene.getSceneIcon());
			
			java.util.List<SceneAction> actionList =  boSceneAction.getListBySceneId(scene.getSceneId());
			listAction.getList().removeAll();
			for(SceneAction action : actionList){
				listAction.add(action.getText());
				listAction.setData(action.getText(), action);
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
