package com.joeysoft.kc868.widgets.qstyle;

import org.eclipse.swt.widgets.Control;

/**
 * 为百叶窗的板条提供文本
 * 
 * @author joey
 */
public interface ISlatLabelProvider {
	/**
	 * 得到文本
	 * 
	 * @param slatControl
	 * 		横条的控件
	 * @return
	 * 		tab上的文本
	 */
	public String getText(Control slatControl);
}
