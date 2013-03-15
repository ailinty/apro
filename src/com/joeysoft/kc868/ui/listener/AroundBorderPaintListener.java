package com.joeysoft.kc868.ui.listener;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;

/**
 * 用来在控件的周围画一圈边框，边框比控件只大一个象粿
 * 
 * @author joey
 */
public class AroundBorderPaintListener implements PaintListener {
    private Class[] types;
    private Color color;
    
    public AroundBorderPaintListener(Class[] types) {
        this(types, Display.getCurrent().getSystemColor(SWT.COLOR_BLACK));
    }
    
    public AroundBorderPaintListener(Class[] types, Color color) {
        this.types = types;
        this.color = color;
    }
    
    /* (non-Javadoc)
     * @see org.eclipse.swt.events.PaintListener#paintControl(org.eclipse.swt.events.PaintEvent)
     */
    public void paintControl(PaintEvent e) {
        Composite parent = (Composite)e.getSource();
        Control[] children = parent.getChildren();
        for(int i = 0; i < children.length; i++) {
            if(children[i].isVisible() && accept(children[i].getClass()))
                BorderPainter.drawAroundBorder(children[i], e.gc, color);
        }
    }
    
    /**
     * 桿查是否是隿要画边框的控件类圿
     * 
     * @param type
     * 		控件的Class
     * @return
     * 		true表示隿褿
     */
    private boolean accept(Class type) {
        for(int i = 0; i < types.length; i++) {
            if(type == types[i])
                return true;            
        }
        return false;
    }
}
