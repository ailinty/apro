package com.joeysoft.kc868.db.bean.vo;

import com.joeysoft.kc868.db.bean.Transfer;
import com.joeysoft.kc868.db.bean.TsfSensor;

public class TransferSensorVo {
	private Transfer transfer;
	
	private TsfSensor tsfSensor;
	
	public TransferSensorVo(){
	}

	public Transfer getTransfer() {
		return transfer;
	}

	public void setTransfer(Transfer transfer) {
		this.transfer = transfer;
	}

	public TsfSensor getTsfSensor() {
		return tsfSensor;
	}

	public void setTsfSensor(TsfSensor tsfSensor) {
		this.tsfSensor = tsfSensor;
	}
	
}
