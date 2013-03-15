package com.joeysoft.kc868.db.bean.vo;

import com.joeysoft.kc868.db.bean.Device;
import com.joeysoft.kc868.db.bean.SensorNor;
import com.joeysoft.kc868.db.bean.Unused;

/**
 * 灯光、窗帘、幕布特殊类，在BODevice中使用
 * @author JOEY
 *
 */
public class DeviceLtWnCl {
	private Unused next;
	
	private SensorNor sensorNor;

	public DeviceLtWnCl(SensorNor sensorNor, Unused next){
		this.sensorNor = sensorNor;
		this.next = next;
	}
	
	public Unused getNext() {
		return next;
	}

	public void setNext(Unused next) {
		this.next = next;
	}

	public SensorNor getSensorNor() {
		return sensorNor;
	}

	public void setSensorNor(SensorNor sensorNor) {
		this.sensorNor = sensorNor;
	}
}
