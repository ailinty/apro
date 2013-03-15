package com.joeysoft.kc868.ui.listener;

import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Control;

/**
 * 边框工具类
 * 
 * @author joey
 */
public class BorderPainter {
    /**
     * 在一个控件周围画指定颜色的边框，控件位于边框的中部，但是如果
     * 控件尺寸大于maxHeight，则在外围画
     * 
     * @param control
     * @param gc
     * @param color
     */
    public static void drawCenterBorder(Control control, GC gc, Color color, int maxHeight) {
		Rectangle rect = control.getBounds();
		int gap = (maxHeight - rect.height) / 2;
		if(gap <= 0) {
		    rect.y--;
		    rect.height++;
		} else {
		    rect.y -= gap + 1;
		    rect.height += gap + gap + 1;
		}
	    rect.x -= 3;
	    rect.width += 5;
		gc.setForeground(color);
		gc.drawRectangle(rect);
    }
    
    /**
     * 在控件的周围画一个边框，边框只比控件大一个象素
     * 
     * @param control
     * @param gc
     * @param color
     */
    public static void drawAroundBorder(Control control, GC gc, Color color) {
        Rectangle rect = control.getBounds();
        rect.x--;
        rect.y--;
        rect.width++;
        rect.height++;
        gc.setForeground(color);
        gc.drawRectangle(rect);
    }
}
