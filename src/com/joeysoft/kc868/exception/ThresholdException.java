package com.joeysoft.kc868.exception;

public class ThresholdException extends Exception{
	
	public ThresholdException(){
		super("已经超过阀值，不能继续添加！");
	}
	
	
}
