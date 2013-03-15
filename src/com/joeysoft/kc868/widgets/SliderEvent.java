package com.joeysoft.kc868.widgets;

import java.util.EventObject;

public class SliderEvent extends EventObject {

	private int value;

	public SliderEvent(Object source, int value) {
		super(source);
		this.value = value;
	}

	public int getValue() {
		return value;
	}
}
