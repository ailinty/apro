package com.joeysoft.kc868.widgets;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.events.ControlEvent;
import org.eclipse.swt.events.ControlListener;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Composite;

public class ImageButtonList extends Composite implements DisposeListener, PaintListener, ControlListener{
	private int style;
	// 图片列表
	List<ImageButton> imageList;
	// 客户区大小
	private Rectangle clientArea;
	
	public ImageButtonList(Composite parent, int style) {
		super(parent, style);
		this.style = style;
	}
	
	private void init(){
		imageList = new ArrayList<ImageButton>();
		
		clientArea = getClientArea();
		
	}
	

	@Override
	public void controlMoved(ControlEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void controlResized(ControlEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void paintControl(PaintEvent event) {
		
	}

	@Override
	public void widgetDisposed(DisposeEvent e) {
		imageList.clear();
		
	}

}
