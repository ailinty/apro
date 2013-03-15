package com.joeysoft.kc868.widgets.qstyle;
import java.util.EventObject;

/**
 * 包装了ShutterLabel事件需要用到的参数
 * 
 * @author joey
 */
public class SlatEvent extends EventObject {
	private static final long serialVersionUID = 3258688810513545270L;
	
	// old text
	public String oldText;
	// new text
	public String newText;
	
	/**
	 * @param source
	 */
	public SlatEvent(Object source) {
		super(source);
	}
}
