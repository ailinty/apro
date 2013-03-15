package com.joeysoft.kc868.ui.dialogs.helper;

import static com.joeysoft.kc868.resource.Messages.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.eclipse.swt.SWT;
import org.eclipse.swt.browser.Browser;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Scale;

import com.joeysoft.kc868.SystemConfig;
import com.joeysoft.kc868.db.bean.Device;
import com.joeysoft.kc868.db.bean.DeviceKey;
import com.joeysoft.kc868.db.bean.Relay;
import com.joeysoft.kc868.db.bean.SensorOut;
import com.joeysoft.kc868.db.bean.Transfer;
import com.joeysoft.kc868.db.bean.Vidicon;
import com.joeysoft.kc868.db.bo.BODevice;
import com.joeysoft.kc868.db.bo.BODeviceKey;
import com.joeysoft.kc868.db.bo.BORelay;
import com.joeysoft.kc868.db.bo.BOSensorOut;
import com.joeysoft.kc868.db.bo.BOTransfer;
import com.joeysoft.kc868.db.bo.BOVidicon;
import com.joeysoft.kc868.db.util.DictConst;
import com.joeysoft.kc868.db.util.MessageUtil;
import com.joeysoft.kc868.resource.IconHolder;
import com.joeysoft.kc868.resource.IconUtil;
import com.joeysoft.kc868.ui.Colors;
import com.joeysoft.kc868.ui.RoomShell;
import com.joeysoft.kc868.ui.helper.UITool;
import com.joeysoft.kc868.widgets.ImageButton;
import com.joeysoft.kc868.widgets.LevelBar;
import com.joeysoft.kc868.widgets.SliderEvent;
import com.joeysoft.kc868.widgets.SliderListener;

public class UIDeviceHelper {
	private IconHolder icons = IconHolder.getInstance();
	private BODevice boDevice = new BODevice();
	private BODeviceKey boDeviceKey = new BODeviceKey();
	private BOSensorOut boSensorOut = new BOSensorOut();
	private BORelay boRelay = new BORelay();
	private BOVidicon boVidicon = new BOVidicon();
	
	private BOTransfer boTransfer =  new BOTransfer();
	
	private RoomShell roomShell;
	
	private Label lbConTemp;
	private Browser browser;
	
	
	public UIDeviceHelper(RoomShell roomShell){
		this.roomShell = roomShell;
	}
	
	/**
	 * 添加灯光
	 * @param compRmRight
	 * @param device
	 */
	public void createLightUI(Device device, Composite compRmRight){
		int count = 0;
		int index = 0;
		List<Device> deviceList = boDevice.getListByLightPD(device.getRoomId()); // 普通灯
		count = deviceList.size();
		for(int d=0; d<deviceList.size(); d++){
			final Device ltDevice = deviceList.get(d);
			
			Composite comp = new Composite(compRmRight, SWT.NONE);
			comp.setBounds(40, (index * 74) + 40, 260, 54);
			comp.setBackgroundImage(icons.getImage(IconHolder.bmpBgLight));
			
			int nameLength = ltDevice.getDeviceName().getBytes().length;
			
			final List<DeviceKey> deviceKeyList = boDeviceKey.getListByDeviceId(ltDevice.getDeviceId());
			
			Label label = UITool.createLabel(comp, ltDevice.getDeviceName());
			label.setBounds((comp.getClientArea().width - nameLength * 8) / 2, 20, nameLength * 9, 20);
			label.setFont(Colors.GLOBAL_FONT);
			
			for(int k=0; k<deviceKeyList.size(); k++){
	    		final DeviceKey dk = deviceKeyList.get(k);
	    		final Label btn = UITool.createLabel(comp, ltDevice.getDeviceName());
	    		btn.setCursor(compRmRight.getDisplay().getSystemCursor(SWT.CURSOR_HAND));
	    		if(StringUtils.isEmpty(dk.getSensorId())){
	    			btn.setImage(icons.getGrayImageByName(dk.getIconName()));
	    		}else{
	    			btn.setImage(icons.getImageByName(dk.getIconName()));
	    		}
	    		btn.setBounds(k * 190 + 20, 3, 30, 48);
	    		btn.addMouseListener(new MouseAdapter() {
	                public void mouseUp(MouseEvent e) {
	                	roomShell.sendDeviceKeySendPacket(dk);
	                }
	    		});
	    	}
			index++;
		}
		
		deviceList = boDevice.getListByLightTD(device.getRoomId()); // 调光灯
		count += deviceList.size();
		compRmRight.setBounds(0, 0, 365, count * 74 + 80);
		for(int d=0; d<deviceList.size(); d++){
			final Device ltDevice = deviceList.get(d);
			
			Composite comp = new Composite(compRmRight, SWT.NONE);
			comp.setBounds(40, (index * 74) + 40, 260, 54);
			comp.setBackgroundImage(icons.getImage(IconHolder.bmpBgLight));
			
			int nameLength = ltDevice.getDeviceName().getBytes().length;
			
			final List<DeviceKey> deviceKeyList = boDeviceKey.getListByDeviceId(ltDevice.getDeviceId());
			
			for(int k=0; k<deviceKeyList.size(); k++){
				int keySize = deviceKeyList.size();
				
				Label label = UITool.createLabel(comp, ltDevice.getDeviceName());
				label.setBounds((comp.getClientArea().width - nameLength * 8) / 2, 8, (int)((float)nameLength * 8.5), 20);
				label.setFont(Colors.GLOBAL_FONT);
				
				Label btn = UITool.createLabel(comp, ltDevice.getDeviceName());
	    		btn.setImage(icons.getImage(IconHolder.btnLightOff));
	    		btn.setBounds(0 * 190 + 20, 3, 30, 48);
	    		
	    		btn = UITool.createLabel(comp, ltDevice.getDeviceName());
	    		btn.setImage(icons.getImage(IconHolder.btnLightOn));
	    		btn.setBounds(1 * 190 + 20, 3, 30, 48);
	    		
	    		final Scale bar = new Scale (comp, SWT.HORIZONTAL);
	    		bar.setBounds(50, 28, 159, 18);
	    		bar.setMaximum(keySize - 1);//设置最大值为100
	    		bar.setMinimum(0);//设置最小值为0
	    		bar.setIncrement(1);//设置拖动滑块时增长的值
	    		bar.setPageIncrement(1);//设置单击滑块空白处一次移动的值
	    		bar.setSelection(SystemConfig.getInstance().getLightLevelBarValue(ltDevice.getDeviceId()));//设置当前选中的值
	    		bar.addMouseListener(new MouseAdapter(){
	    			@Override
	    			public void mouseUp(MouseEvent e) {
	    				int index = bar.getSelection();
						SystemConfig.getInstance().setLightLevelBarValue(ltDevice.getDeviceId(), index);
						// 从1开始
						//if(index > 0){
							DeviceKey dk = deviceKeyList.get(index);
							roomShell.sendDeviceKeySendPacket(dk);
						//}
	    			}
	    		});
	    		/*bar.addListener(SWT.Selection, new Listener () {
	    		    public void handleEvent (Event event) {
	    				int index = bar.getSelection();
						SystemConfig.getInstance().setLightLevelBarValue(ltDevice.getDeviceId(), index);
						// 从1开始
						//if(index > 0){
							DeviceKey dk = deviceKeyList.get(index);
							roomShell.sendDeviceKeySendPacket(dk);
						//}
	    		    }
	    		});*/
	    		
	    		/*LevelBar bar = new LevelBar(comp, SWT.HORIZONTAL);
	    		bar.setBackgroundImage(icons.getImage(IconHolder.btnLightAdjust));
	    		bar.setThumbImage(icons.getImage(IconHolder.btnAdjust));
	    		bar.setBounds(50, 35, 159, 18);
	    		bar.setMaxValue(keySize - 1);
	    		bar.setValue(SystemConfig.getInstance().getLightLevelBarValue(ltDevice.getDeviceId()));
	    		bar.addSliderListener(new SliderListener(){
					@Override
					public void valueChanged(SliderEvent event) {
						int index = event.getValue();
						SystemConfig.getInstance().setLightLevelBarValue(ltDevice.getDeviceId(), index);
						// 从1开始
						//if(index > 0){
							DeviceKey dk = deviceKeyList.get(index);
							roomShell.sendDeviceKeySendPacket(dk);
						//}
					}
	    		});*/
	    	}
			
			index++;
		}
	}
	
	/**
	 * 添加空调
	 * @param device
	 * @param compRmRight
	 */
	public void createAirConditionUI(Device device, Composite compRmRight){
		int conCols = 5; //5列
		final List<DeviceKey> deviceKeyList = boDeviceKey.getListByDeviceId(device.getDeviceId());
		if(deviceKeyList.size() != DictConst.DEVICE_TYPE_KT_COUNT){
			return;
		}
		
		Label label = UITool.createLabel(compRmRight, "");
		label.setBounds(150, 40, 15, 29);
		label.setImage(icons.getImage(IconHolder.icoTemp));
		
		lbConTemp = UITool.createLabel(compRmRight, "");
		lbConTemp.setBounds(170, 43, 30, 20);
		lbConTemp.setFont(Colors.NORMAL_FONT);
		lbConTemp.setText(""+(SystemConfig.getInstance().getConLevelBarValue()+ 16));
		
		label = UITool.createLabel(compRmRight, "");
		label.setBounds(200, 40, 20, 29);
		label.setImage(icons.getImage(IconHolder.icoC));
		
		final Scale bar = new Scale (compRmRight, SWT.HORIZONTAL);
		bar.setBounds(0, 80, 357, 18);
		bar.setMaximum(DictConst.DEVICE_TYPE_KT_THUMB_COUNT);//设置最大值为100
		bar.setMinimum(0);//设置最小值为0
		bar.setIncrement(1);//设置拖动滑块时增长的值
		bar.setPageIncrement(1);//设置单击滑块空白处一次移动的值
		bar.setSelection(SystemConfig.getInstance().getConLevelBarValue());//设置当前选中的值
		bar.addMouseListener(new MouseAdapter(){
			@Override
			public void mouseUp(MouseEvent e) {
				//sliderValue.setText( "当前值为"+scale.getSelection());
		    	int index = bar.getSelection();
				lbConTemp.setText(""+(index + 16));
				SystemConfig.getInstance().setConLevelBarValue(index);
				// 从1开始
				if(index > 0){
					DeviceKey dk = deviceKeyList.get(index - 1);
					roomShell.sendOrStudyProccess(dk);
				}
			}
			
		});
		/*bar.addListener(SWT.Selection, new Listener () {
		    public void handleEvent (Event event) {
		    	//sliderValue.setText( "当前值为"+scale.getSelection());
		    	int index = bar.getSelection();
				lbConTemp.setText(""+(index + 16));
				SystemConfig.getInstance().setConLevelBarValue(index);
				// 从1开始
				if(index > 0){
					DeviceKey dk = deviceKeyList.get(index - 1);
					roomShell.sendOrStudyProccess(dk);
				}
		    }
		});*/
		   
		/*LevelBar bar = new LevelBar(compRmRight, SWT.HORIZONTAL);
		bar.setBackgroundImage(icons.getImage(IconHolder.btnAirAdjust));
		bar.setThumbImage(icons.getImage(IconHolder.btnAdjust));
		bar.setBounds(0, 80, 357, 18);
		bar.setMaxValue(DictConst.DEVICE_TYPE_KT_THUMB_COUNT); //14个滑块
		bar.setValue(SystemConfig.getInstance().getConLevelBarValue());
		bar.addSliderListener(new SliderListener(){
			@Override
			public void valueChanged(SliderEvent event) {
				int index = event.getValue();
				lbConTemp.setText(""+(index + 16));
				SystemConfig.getInstance().setConLevelBarValue(event.getValue());
				// 从1开始
				if(index > 0){
					DeviceKey dk = deviceKeyList.get(index - 1);
					roomShell.sendOrStudyProccess(dk);
				}
			}
		});*/
		
		int index = DictConst.DEVICE_TYPE_KT_THUMB_COUNT;
		
		for(int k=0; k<10; k++){
    		final DeviceKey dk = deviceKeyList.get(k + index);
    		
    		final Composite comp = new Composite(compRmRight, SWT.NONE);
    		comp.setBounds((k%conCols) * 72, (k/conCols * 72) + 120, 58, 54);
    		comp.setBackgroundImage(icons.getImage(IconHolder.btnCon));
    		if(StringUtils.isEmpty(dk.getSensorId())){
    			comp.setBackgroundImage(icons.getGrayImage(IconHolder.btnCon));
    		}else{
    			comp.setBackgroundImage(icons.getImage(IconHolder.btnCon));
    		}
    		comp.setCursor(compRmRight.getDisplay().getSystemCursor(SWT.CURSOR_HAND));
    		comp.addMouseListener(new MouseAdapter() {
        		public void mouseUp(MouseEvent e) {  
        			boolean ok = roomShell.sendOrStudyProccess(dk);
                	if(ok){
                		comp.setBackgroundImage(icons.getImage(IconHolder.btnCon));
                	}
        		}
        		
        		public void mouseDown(MouseEvent e) {
        			comp.setBackgroundImage(icons.getImage(IconHolder.btnConOn));
        		}
        	});
    		
    		label = UITool.createLabel(comp, "");
    		label.setBounds(12, 5, 32, 32);
    		label.setImage(icons.getImageByName(dk.getIconName()));
    		label.addMouseListener(new MouseAdapter() {
        		public void mouseUp(MouseEvent e) {  
        			boolean ok = roomShell.sendOrStudyProccess(dk);
                	if(ok){
                		comp.setBackgroundImage(icons.getImage(IconHolder.btnCon));
                	}
        		}
        		public void mouseDown(MouseEvent e) {
        			comp.setBackgroundImage(icons.getImage(IconHolder.btnConOn));
        		}
        	});
    		
    		String name = MessageUtil.getMessage(dk.getKeyType());
    		
    		int nameLength = name.getBytes().length;
    		label = UITool.createLabel(comp, name);
    		label.setBounds((comp.getClientArea().width - nameLength * 4) / 2, 38, 32, 20);
    		label.addMouseListener(new MouseAdapter() {
        		public void mouseUp(MouseEvent e) {
        			boolean ok = roomShell.sendOrStudyProccess(dk);
                	if(ok){
                		comp.setBackgroundImage(icons.getImage(IconHolder.btnCon));
                	}
        		}
        		public void mouseDown(MouseEvent e) {
        			comp.setBackgroundImage(icons.getImage(IconHolder.btnConOn));
        		}
        	});
		}
	}
	
	/**
	 * 添加幕布
	 * @param compRmRight
	 * @param device
	 */
	public void createScreenUI(Device device, Composite compRmRight){
		int screenCols = 2;
		List<Device> deviceList = boDevice.getListByScreen(device.getRoomId());
		compRmRight.setBounds(0, 0, 365, deviceList.size() * 160 + 80);
		for(int d=0; d<deviceList.size(); d++){
			Device ltDevice = deviceList.get(d);
			
			Composite comp = new Composite(compRmRight, SWT.NONE);
			comp.setBounds(40, (d * 164) + 40, 316, 160);
			
			Label lbName = UITool.createLabel(comp, ltDevice.getDeviceName());
			lbName.setBounds((comp.getClientArea().width - ltDevice.getDeviceName().getBytes().length * 10) / 2, 120, 132, 20);
			lbName.setFont(Colors.GLOBAL_FONT);
			
			List<DeviceKey> deviceKeyList = boDeviceKey.getListByDeviceId(ltDevice.getDeviceId());
			
			for(int k=0; k<deviceKeyList.size(); k++){
				final DeviceKey dk = deviceKeyList.get(k);
	    		
	    		final Composite compDK = new Composite(comp, SWT.NONE);
	    		compDK.setBounds((k%screenCols) * 166, (k/screenCols * 124), 116, 114);
	    		compDK.setBackgroundImage(StringUtils.isEmpty(dk.getSensorId())?icons.getGrayImage(IconHolder.btnBig):icons.getImage(IconHolder.btnBig));
	    		compDK.setCursor(compRmRight.getDisplay().getSystemCursor(SWT.CURSOR_HAND));
	    		compDK.addMouseListener(new MouseAdapter() {
	        		public void mouseUp(MouseEvent e) {
	        			compDK.setBackgroundImage(icons.getImage(IconHolder.btnBig));
	        			roomShell.sendDeviceKeySendPacket(dk);
	        		}
	        		public void mouseDown(MouseEvent e) {
	        			compDK.setBackgroundImage(icons.getImage(IconHolder.btnBigOn));
	        		}
	        	});
	    		
	    		final Label btn = UITool.createLabel(compDK, ltDevice.getDeviceName());
	    		btn.setImage(icons.getImageByName(dk.getIconName()));
	    		btn.setBounds(25, 15, 64, 64);
	    		btn.addMouseListener(new MouseAdapter() {
	        		public void mouseUp(MouseEvent e) {
	        			compDK.setBackgroundImage(icons.getImage(IconHolder.btnBig));
	        			roomShell.sendDeviceKeySendPacket(dk);
	        		}
	        		public void mouseDown(MouseEvent e) {
	        			compDK.setBackgroundImage(icons.getImage(IconHolder.btnBigOn));
	        		}
	        	});
	    		
	    		String name = MessageUtil.getMessage(dk.getKeyType());
	    		
	    		int nameLength = name.getBytes().length;
	    		Label label = UITool.createLabel(compDK, name);
	    		label.setBounds((compDK.getClientArea().width - nameLength * 6) / 2, 89, 32, 20);
	    		label.addMouseListener(new MouseAdapter() {
	        		public void mouseUp(MouseEvent e) {
	        			compDK.setBackgroundImage(icons.getImage(IconHolder.btnBig));
	        			roomShell.sendDeviceKeySendPacket(dk);
	        		}
	        		public void mouseDown(MouseEvent e) {
	        			compDK.setBackgroundImage(icons.getImage(IconHolder.btnBigOn));
	        		}
	        	});
	    	}
		}
	}
	
	/**
	 * 添加多媒体
	 * @param device
	 * @param compRmRight
	 */
	public void createMediaUI(Device device, Composite compRmRight){
		int mediaCols = 5; //5列
		List<DeviceKey> deviceKeyList = boDeviceKey.getListByDeviceId(device.getDeviceId());
		
		int col=0;
		// 26个按键
		for(int k=0; k<deviceKeyList.size() && k<26; k++){
    		final DeviceKey dk = deviceKeyList.get(k);
    		
    		String name = StringUtils.isEmpty(dk.getKeyName()) ? MessageUtil.getMediaMessage(dk.getKeyType()) : dk.getKeyName();
    		Image image = StringUtils.isEmpty(dk.getSensorId())?icons.getGrayImageByName(dk.getIconName()):icons.getImageByName(dk.getIconName());
    		
    		Image imageOn = StringUtils.isEmpty(dk.getSensorId())?icons.getGrayImageByName(IconUtil.getImageOn(dk.getIconName())):icons.getImageByName(IconUtil.getImageOn(dk.getIconName()));
    		final ImageButton ib = UITool.createImageButton(compRmRight, name, image, image, imageOn);
    		
    		ib.setBounds((col%mediaCols) * 72, (col/mediaCols * 52) + 40, 58, 44);
    		ib.setCursor(compRmRight.getDisplay().getSystemCursor(SWT.CURSOR_HAND));
    		ib.setFont(Colors.GLOBAL_FONT);
    		ib.addMouseListener(new MouseAdapter() {
                public void mouseUp(MouseEvent e) {
                	boolean ok = roomShell.sendOrStudyProccess(dk);
                	if(ok){
                		ib.setImage(icons.getImageByName(dk.getIconName()));
                	}
                }
    		});
            
    		col++;
    		
    		if(k + 1 == 10){
    			mediaCols = 2; // 2列
    			col = 4;
    		}else if(k + 1 == 17){
    			mediaCols = 5; // 2列
    			col = 26;
    		}
		}
		
		// 方向键面板
		Composite comp = new Composite(compRmRight, SWT.NONE);
		comp.setBounds(155, 150, 178, 137);
		comp.setBackgroundImage(icons.getImage(IconHolder.btnMedia3));
		
		// 方向键
		final DeviceKey dkUp = deviceKeyList.get(26);
		final Label lbUp = UITool.createLabel(comp, "");
		lbUp.setImage(StringUtils.isEmpty(dkUp.getSensorId())?icons.getGrayImageByName(dkUp.getIconName()):icons.getImageByName(dkUp.getIconName()));
		lbUp.setBounds(73, 12, 32, 32);
		lbUp.setFont(Colors.GLOBAL_FONT);
		lbUp.setCursor(compRmRight.getDisplay().getSystemCursor(SWT.CURSOR_HAND));
		lbUp.addMouseListener(new MouseAdapter() {
            public void mouseUp(MouseEvent e) {
            	boolean ok = roomShell.sendOrStudyProccess(dkUp);
            	if(ok){
            		lbUp.setImage(icons.getImageByName(dkUp.getIconName()));
            	}
            }
		});
		
		final DeviceKey dkDown = deviceKeyList.get(27);
		final Label lbDown = UITool.createLabel(comp, "");
		lbDown.setImage(StringUtils.isEmpty(dkDown.getSensorId())?icons.getGrayImageByName(dkDown.getIconName()):icons.getImageByName(dkDown.getIconName()));
		lbDown.setBounds(73, 100, 32, 32);
		lbDown.setFont(Colors.GLOBAL_FONT);
		lbDown.setCursor(compRmRight.getDisplay().getSystemCursor(SWT.CURSOR_HAND));
		lbDown.addMouseListener(new MouseAdapter() {
            public void mouseUp(MouseEvent e) {
            	boolean ok = roomShell.sendOrStudyProccess(dkDown);
            	if(ok){
            		lbDown.setImage(icons.getImageByName(dkDown.getIconName()));
            	}
            }
		});
		
		final DeviceKey dkLeft = deviceKeyList.get(28);
		final Label lbLeft = UITool.createLabel(comp, "");
		lbLeft.setImage(StringUtils.isEmpty(dkLeft.getSensorId())?icons.getGrayImageByName(dkLeft.getIconName()):icons.getImageByName(dkLeft.getIconName()));
		lbLeft.setBounds(20, 55, 32, 32);
		lbLeft.setFont(Colors.GLOBAL_FONT);
		lbLeft.setCursor(compRmRight.getDisplay().getSystemCursor(SWT.CURSOR_HAND));
		lbLeft.addMouseListener(new MouseAdapter() {
            public void mouseUp(MouseEvent e) {
            	boolean ok = roomShell.sendOrStudyProccess(dkLeft);
            	if(ok){
            		lbLeft.setImage(icons.getImageByName(dkLeft.getIconName()));
            	}
            }
		});
		
		final DeviceKey dkRight = deviceKeyList.get(29);
		final Label lbRight = UITool.createLabel(comp, "");
		lbRight.setImage(StringUtils.isEmpty(dkRight.getSensorId())?icons.getGrayImageByName(dkRight.getIconName()):icons.getImageByName(dkRight.getIconName()));
		lbRight.setBounds(126, 55, 32, 32);
		lbRight.setFont(Colors.GLOBAL_FONT);
		lbRight.setCursor(compRmRight.getDisplay().getSystemCursor(SWT.CURSOR_HAND));
		lbRight.addMouseListener(new MouseAdapter() {
            public void mouseUp(MouseEvent e) {
            	boolean ok = roomShell.sendOrStudyProccess(dkRight);
            	if(ok){
            		lbRight.setImage(icons.getImageByName(dkRight.getIconName()));
            	}
            }
		});
		
		
		final DeviceKey dkOk = deviceKeyList.get(30);
		String name = StringUtils.isEmpty(dkOk.getKeyName()) ? MessageUtil.getMessage(dkOk.getKeyType()) : dkOk.getKeyName();
		final Label lbOk = UITool.createLabel(comp, name);
		lbOk.setBounds(73, 65, 32, 32);
		lbOk.setFont(Colors.GLOBAL_FONT);
		lbOk.setForeground(StringUtils.isEmpty(dkOk.getSensorId())? Colors.GRAY:Colors.WHITE);
		lbOk.setCursor(compRmRight.getDisplay().getSystemCursor(SWT.CURSOR_HAND));
		lbOk.addMouseListener(new MouseAdapter() {
            public void mouseUp(MouseEvent e) {
            	boolean ok = roomShell.sendOrStudyProccess(dkOk);
            	if(ok){
            		lbOk.setForeground(Colors.WHITE);
            	}
            }
		});
	}
	
	/**
	 * 添加窗帘
	 * @param compRmRight
	 * @param device
	 */
	public void createWindowUI(Device device, Composite compRmRight){
		int windowCols = 3;
		List<Device> deviceList = boDevice.getListByWindow(device.getRoomId());
		compRmRight.setBounds(0, 0, 365, deviceList.size() * 160 + 80);
		for(int d=0; d<deviceList.size(); d++){
			Device ltDevice = deviceList.get(d);
			
			Composite comp = new Composite(compRmRight, SWT.NONE);
			comp.setBounds(0, (d * 164) + 40, 356, 160);
			
			Label lbName = UITool.createLabel(comp, ltDevice.getDeviceName());
			lbName.setBounds((comp.getClientArea().width - ltDevice.getDeviceName().getBytes().length * 10) / 2, 120, 132, 20);
			lbName.setFont(Colors.GLOBAL_FONT);
			
			List<DeviceKey> deviceKeyList = boDeviceKey.getListByDeviceId(ltDevice.getDeviceId());
			
			for(int k=0; k<deviceKeyList.size(); k++){
				final DeviceKey dk = deviceKeyList.get(k);
	    		
	    		final Composite compDK = new Composite(comp, SWT.NONE);
	    		compDK.setBounds((k%windowCols) * 120, (k/windowCols * 124), 116, 114);
	    		compDK.setBackgroundImage(StringUtils.isEmpty(dk.getSensorId())?icons.getGrayImage(IconHolder.btnBig):icons.getImage(IconHolder.btnBig));
	    		compDK.setCursor(compRmRight.getDisplay().getSystemCursor(SWT.CURSOR_HAND));
	    		compDK.addMouseListener(new MouseAdapter() {
	        		public void mouseUp(MouseEvent e) {
	        			compDK.setBackgroundImage(icons.getImage(IconHolder.btnBig));
	        			roomShell.sendDeviceKeySendPacket(dk);
	        		}
	        		public void mouseDown(MouseEvent e) {
	        			compDK.setBackgroundImage(icons.getImage(IconHolder.btnBigOn));
	        		}
	        	});
	    		
	    		final Label btn = UITool.createLabel(compDK, ltDevice.getDeviceName());
	    		btn.setImage(icons.getImageByName(dk.getIconName()));
	    		btn.setBounds(25, 15, 64, 64);
	    		btn.addMouseListener(new MouseAdapter() {
	        		public void mouseUp(MouseEvent e) {
	        			compDK.setBackgroundImage(icons.getImage(IconHolder.btnBig));
	        			roomShell.sendDeviceKeySendPacket(dk);
	        		}
	        		public void mouseDown(MouseEvent e) {
	        			compDK.setBackgroundImage(icons.getImage(IconHolder.btnBigOn));
	        		}
	        	});
	    		
	    		String name = MessageUtil.getMessage(dk.getKeyType());
	    		
	    		int nameLength = name.getBytes().length;
	    		Label label = UITool.createLabel(compDK, name);
	    		label.setBounds((compDK.getClientArea().width - nameLength * 6) / 2, 89, 32, 20);
	    		label.addMouseListener(new MouseAdapter() {
	        		public void mouseUp(MouseEvent e) {
	        			compDK.setBackgroundImage(icons.getImage(IconHolder.btnBig));
	        			roomShell.sendDeviceKeySendPacket(dk);
	        		}
	        		public void mouseDown(MouseEvent e) {
	        			compDK.setBackgroundImage(icons.getImage(IconHolder.btnBigOn));
	        		}
	        	});
	    	}
		}
	}
	
	/**
	 * 添加继电器
	 * @param compRmRight
	 * @param device
	 */
	public void createRelayUI(Device device, Composite compRmRight){
		int relayCols = 2; // 分2列
		List<Relay> relayList = boRelay.getListByRoomId(device.getRoomId());
		
		for(int d=0; d<relayList.size(); d++){
			final Relay relay = relayList.get(d);
			
			Label label = UITool.createLabel(compRmRight, relay.getRelayName());
			label.setBounds((d%relayCols) * 160 + 45, (d /relayCols)* 74 + 60, 100, 20);
			label.setFont(Colors.GLOBAL_FONT);
			
			final Label lbRelay = UITool.createLabel(compRmRight, "");
			lbRelay.setImage(SystemConfig.getInstance().getRelayStatus().get(relay.getRelayId())?icons.getImage(IconHolder.btnOn):icons.getImage(IconHolder.btnOff));
			lbRelay.setBounds((d%relayCols) * 160 + 40, (d /relayCols)* 74 + 80, 87, 34);
			lbRelay.setFont(Colors.GLOBAL_FONT);
			lbRelay.setCursor(compRmRight.getDisplay().getSystemCursor(SWT.CURSOR_HAND));
			lbRelay.addMouseListener(new MouseAdapter() {
	            public void mouseUp(MouseEvent e) {
	            	boolean rStatus = !SystemConfig.getInstance().getRelayStatus().get(relay.getRelayId());
	            	
	            	roomShell.sendRelaySendPacket(relay, rStatus);
	            	
	            	SystemConfig.getInstance().getRelayStatus().put(relay.getRelayId(), rStatus);
	            	lbRelay.setImage(rStatus?icons.getImage(IconHolder.btnOn):icons.getImage(IconHolder.btnOff));
	            }
			});
		}
	}
	
	/**
	 * 添加摄像头
	 * @param compRmRight
	 * @param device
	 */
	public String createVidiconUI(Device device, Composite compRmRight){
		Vidicon vidicon = boVidicon.getByDeviceId(device.getDeviceId());
		StringBuffer sbUrl = new StringBuffer();
		if(!vidicon.getVidiconUrl().startsWith("http://")){
			sbUrl.append("http://");
		}
		sbUrl.append(vidicon.getVidiconUrl()).append(":").append(vidicon.getVidiconPort());
		sbUrl.append("/video.htm");
		sbUrl.append("?user=").append(vidicon.getVidiconUser());
		sbUrl.append("&usr=").append(vidicon.getVidiconUser());
		sbUrl.append("&password=").append(vidicon.getVidiconPwd());
		sbUrl.append("&pwd=").append(vidicon.getVidiconPwd());
		if(browser == null || browser.isDisposed()){
			browser = new Browser(compRmRight, SWT.NONE);
			browser.setBounds(0, 0, 365, 420);
		}
		browser.setUrl(sbUrl.toString());
		System.out.println(sbUrl.toString());
		return sbUrl.toString();
	}
	
	/**
	 * 添加其它设备（非常规设备）
	 * @param compRmRight
	 * @param device
	 */
	public void createOtherUI(Device device, Composite compRmRight){
		int otherCols = 2; // 分2列
		
		List<Transfer> transferList = boTransfer.getListByRoomId(device.getRoomId());
		int transferCount = transferList.size();
		List<SensorOut> sensorOutList = boSensorOut.getListByRoomId(device.getRoomId());
		int sensorOutCount = sensorOutList.size();
		
		compRmRight.setBounds(0, 0, 365, (transferCount + sensorOutCount)/ 2 * 52 + 80);
		
		for(int d=0; d<sensorOutCount; d++){
			final SensorOut sensorOut = sensorOutList.get(d);
			
    		ImageButton ib = UITool.createImageButton(compRmRight, sensorOut.getSensorName(), 
    				icons.getImage(IconHolder.btnOther), 
    				icons.getImage(IconHolder.btnOther), icons.getImage(IconHolder.btnOtherOn));
    		ib.setBounds((d%otherCols) * 158 + 30, (d/otherCols * 52) + 40, 138, 44);
    		ib.setCursor(compRmRight.getDisplay().getSystemCursor(SWT.CURSOR_HAND));
    		ib.setFont(Colors.GLOBAL_FONT);
    		ib.addMouseListener(new MouseAdapter() {
                public void mouseUp(MouseEvent e) {
                	roomShell.sendSensorOutSendPacket(sensorOut);
                }
    		});
		}
		
		for(int d=0; d<transferCount; d++){
			final Transfer transfer = transferList.get(d);
			
    		ImageButton ib = UITool.createImageButton(compRmRight, transfer.getTransferName() + "学习", 
    				icons.getImage(IconHolder.btnOther), 
    				icons.getImage(IconHolder.btnOther), icons.getImage(IconHolder.btnOtherOn));
    		
    		ib.setBounds(((d + sensorOutCount)%otherCols) * 158 + 30, ((d + sensorOutCount)/otherCols * 52) + 40, 138, 44);
    		ib.setCursor(compRmRight.getDisplay().getSystemCursor(SWT.CURSOR_HAND));
    		ib.setFont(Colors.GLOBAL_FONT);
    		ib.addMouseListener(new MouseAdapter() {
                public void mouseUp(MouseEvent e) {
                	roomShell.sendTransferSendPacket(transfer);
                }
    		});
		}
	}
	
	
}
