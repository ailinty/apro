package com.joeysoft.kc868.widgets.mac;

import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Point;

/**
 * 标识绘画器
 * 
 * @author joey
 */
public interface ISignPainter {
	/**
	 * 画一个标志
	 * 
	 * @param gc
	 * 		gc
	 * @param ring
	 * 		Ring控件
	 * @param angle
	 * 		角度
	 * @param absLoc
	 * 		绝对坐标
	 */
	public void draw(GC gc, Ring ring, double angle, Point absLoc);
}
