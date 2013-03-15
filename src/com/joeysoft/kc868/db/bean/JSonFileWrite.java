package com.joeysoft.kc868.db.bean;

import java.io.IOException;
import java.util.List;

import net.sf.json.JSONObject;

import com.joeysoft.kc868.KC868;
import com.joeysoft.kc868.SystemConfig;
import com.joeysoft.kc868.common.JSonUtil;
import com.joeysoft.kc868.common.UTF8FileUtil;
import com.joeysoft.kc868.common.ZipUtil;
import com.joeysoft.kc868.db.bo.BODevice;
import com.joeysoft.kc868.db.bo.BODeviceKey;
import com.joeysoft.kc868.db.bo.BODict;
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
import com.joeysoft.kc868.db.bo.BOSmsIn;
import com.joeysoft.kc868.db.bo.BOSmsOut;
import com.joeysoft.kc868.db.bo.BOTelIn;
import com.joeysoft.kc868.db.bo.BOTelOut;
import com.joeysoft.kc868.db.bo.BOTempSensor;
import com.joeysoft.kc868.db.bo.BOTransfer;
import com.joeysoft.kc868.db.bo.BOTransferSensor;
import com.joeysoft.kc868.db.bo.BOVidicon;

public class JSonFileWrite {
	
	public static boolean createJSonFile(){
		BORelay boRelay = new BORelay();
		BOFloor boFloor = new BOFloor();
		BORoom boRoom = new BORoom();
		BODevice boDevice = new BODevice();
		BODeviceKey boDeviceKey = new BODeviceKey();
		BODict boDict = new BODict();
		BOTransfer boTransfer = new BOTransfer();
		BOTransferSensor boTransferSensor = new BOTransferSensor();
		BOSensorOut boSensorOut = new BOSensorOut();
		BOSensorNor boSensorNor = new BOSensorNor();
		BOLineate boLineate = new BOLineate();
		BOSensorIn boSensorIn = new BOSensorIn();
		BOTempSensor boTempSensor = new BOTempSensor();
		//BOBugle boBugle = new BOBugle();
		BOScene boScene = new BOScene();
		BOSceneAction boSceneAction = new BOSceneAction();
		BOSceneBind boSceneBind = new BOSceneBind();
		BOSmsIn boSmsIn = new BOSmsIn();
		BOSmsOut boSmsOut = new BOSmsOut();
		BOTelIn boTelIn = new BOTelIn();
		BOTelOut boTelOut = new BOTelOut();
		BOVidicon boVidicon = new BOVidicon();
		
		JSONObject resultjSONObject = new JSONObject();
		
		resultjSONObject.put("SYSTEM_TITLE", SystemConfig.getInstance().getSystemTitle());
		
		resultjSONObject.put("CENTER_TITLE", SystemConfig.getInstance().getCenterTitle());
		
		resultjSONObject.put("FLOOR", boFloor.getList());
		
		resultjSONObject.put("ROOM", boRoom.getList());
		
		resultjSONObject.put("DEVICE", boDevice.getList());

		resultjSONObject.put("DEVICE_KEY", boDeviceKey.getList());
		
		List<Dict> dictList = boDict.getList();
		resultjSONObject.put("DICT", dictList);
		
		resultjSONObject.put("RELAY", boRelay.getList());
		
		resultjSONObject.put("TRANSFER", boTransfer.getList());
		
		resultjSONObject.put("TSF_SENSOR", boTransferSensor.getList());
		
		resultjSONObject.put("SENSOR", boSensorOut.getList());
		
		resultjSONObject.put("SENSOR_NOR", boSensorNor.getList());
		
		List<Lineate> lineateList = boLineate.getList();
		resultjSONObject.put("LINEATE", lineateList);
		
		List<SensorIn> sensorInList = boSensorIn.getList();
		resultjSONObject.put("SENSOR_IN", sensorInList);
		
		resultjSONObject.put("TEMP_SENSOR", boTempSensor.getList());
		
		resultjSONObject.put("SCENE", boScene.getList());
		
		resultjSONObject.put("SCENE_ACTION", boSceneAction.getList());
		
		resultjSONObject.put("SCENE_BIND", boSceneBind.getList());
		
		resultjSONObject.put("SMS_IN", boSmsIn.getList());
		resultjSONObject.put("SMS_OUT", boSmsOut.getList());
		resultjSONObject.put("TEL_IN", boTelIn.getList());
		resultjSONObject.put("TEL_OUT", boTelOut.getList());
		
		resultjSONObject.put("VIDICON", boVidicon.getList());
		
		Bugle[] bugles = new Bugle[1];
		bugles[0] = new Bugle("喇叭", "打开喇叭", "关闭喇叭", "Y");
		bugles[0].setBugleId(1);
		resultjSONObject.put("BUGLE", bugles);
		
		System.out.println(resultjSONObject.toString());
		
		try {
			UTF8FileUtil.saveFile(KC868.CONFIG_DIR + SystemConfig.getInstance().getJsonFileName(), resultjSONObject.toString(), false);
			ZipUtil.zip(KC868.CONFIG_DIR + SystemConfig.getInstance().getJsonFileName(), KC868.CONFIG_DIR + SystemConfig.getInstance().getJsonZipFileName());
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public static void main(String[] avgs){
		JSonFileWrite.createJSonFile();
	}
}
