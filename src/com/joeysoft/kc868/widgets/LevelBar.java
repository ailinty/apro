package com.joeysoft.kc868.widgets;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ControlEvent;
import org.eclipse.swt.events.ControlListener;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.events.MouseMoveListener;
import org.eclipse.swt.events.MouseTrackListener;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Composite;

import com.joeysoft.kc868.ui.Colors;

public class LevelBar extends AlphaComposite implements ControlListener, MouseListener, MouseMoveListener, MouseTrackListener {

	// 滑块
	private Composite thumb;
	private Image thumbImage;
	
	private Point controlPoint;
	
	private int orientation = SWT.HORIZONTAL;
	
	private int value;

	private int tempLocation;

	private final int DEFAULT_MAX_VALUE = 100;

	private int maxValue = DEFAULT_MAX_VALUE;
	
	private List<SliderListener> listeners = new ArrayList<SliderListener>();

	public void addSliderListener(SliderListener sliderListener) {
		listeners.add(sliderListener);
	}

	public void removeSliderListener(SliderListener sliderListener) {
		listeners.remove(sliderListener);
	}
	
	public LevelBar(Composite parent, int orientation) {
		super(parent, SWT.NONE);
		this.orientation = orientation;
		thumb = new AlphaComposite(this, SWT.NONE);
		addControlListener(this);
		thumb.addMouseListener(this);
		thumb.addMouseMoveListener(this);
		thumb.addMouseTrackListener(this);
		super.addMouseListener(new LevelBarMouseListener());
	}

	@Override
	public void mouseEnter(MouseEvent e) {
		
	}

	@Override
	public void mouseExit(MouseEvent e) {
		
	}

	@Override
	public void mouseHover(MouseEvent e) {
		
	}

	@Override
	public void mouseMove(MouseEvent e) {
		if (controlPoint == null) {
			return;
		}
		int maxLength;
		int maxLocator;
		switch (orientation) {
		case SWT.HORIZONTAL:
			maxLength = valueToPels(getMaxValue());
			maxLocator = maxLength - thumbImage.getBounds().width;
			int movedX = e.x - controlPoint.x;
			redraw(tempLocation, 0, thumbImage.getBounds().width,
					thumbImage.getBounds().height, false);
			
			tempLocation = valueToPels(getValue()) + movedX
					- thumbImage.getBounds().width / 2;
			if (tempLocation < 0) {
				tempLocation = 0;
			} else if (tempLocation > maxLocator) {
				tempLocation = maxLocator;
			}
			break;
		case SWT.VERTICAL:
			maxLength = valueToPels(getMaxValue());
			maxLocator = maxLength - thumbImage.getBounds().height;
			int movedY = e.y - controlPoint.y;
			tempLocation = valueToPels(getValue()) + movedY
					- thumbImage.getBounds().height / 2;
			if (tempLocation < 0) {
				tempLocation = 0;
			} else if (tempLocation > valueToPels(getMaxValue())
					- getBounds().height) {
				tempLocation = valueToPels(getMaxValue())
						- getBounds().height;
			}
			break;
		}
	}

	@Override
	public void mouseDoubleClick(MouseEvent e) {
		
	}

	@Override
	public void mouseDown(MouseEvent e) {
		controlPoint = new Point(e.x, e.y);
	}

	@Override
	public void mouseUp(MouseEvent e) {
		countValue(e);
		controlPoint = null;
	}

	@Override
	public void paintControl(PaintEvent e) {
		super.paintControl(e);
		GC gc = e.gc;
		gc.setForeground(Colors.GRAY);
		gc.setLineWidth(1);
		for(int i=1; i<maxValue; i++){
			int x = valueToPels(i);
			gc.drawLine(x, 0, x, 5);
		}
		
		switch (orientation) {
		case SWT.HORIZONTAL:
			if (controlPoint != null) {
				gc.drawImage(thumbImage, tempLocation, 0);
			}
			break;
		case SWT.VERTICAL:
			if (controlPoint != null) {
				gc.drawImage(thumbImage, 0, tempLocation);
			}
			break;
		}
	}

	@Override
	public void controlMoved(ControlEvent e) {
		
	}

	@Override
	public void controlResized(ControlEvent e) {
		moveThumb();
	}

	@Override
	public void dispose() {
		super.dispose();
	}
	@Override
	public void setEnabled(boolean enabled) {
		super.setEnabled(enabled);
		redraw();
	}
	
	private int valueToPels(int value) {
		float widgetLength = (orientation == SWT.HORIZONTAL) ? getBounds().width
				: getBounds().height;
		return (int) (widgetLength * (float) value / (float) maxValue);
	}
	
	private int pelsToValue(int pels) {
		float widgetLength = (orientation == SWT.HORIZONTAL) ? getBounds().width
				: getBounds().height;
		float value = ((float) pels * (float) maxValue / (float) widgetLength);
		if(value > 0.0)
			return (int)Math.ceil(value);
		else
			return (int)Math.floor(value);
	}
	
	private void countValue(MouseEvent e) {
		switch (orientation) {
		case SWT.HORIZONTAL:
			int movedX = e.x - controlPoint.x;
			if(movedX > 0){
				movedX -= 15;
			}
			setValue(getValue() + pelsToValue(movedX));
			break;
		case SWT.VERTICAL:
			int movedY = e.y - controlPoint.y;
			setValue(getValue() + pelsToValue(movedY));
			break;
		}
	}
	
	private void countValue2(MouseEvent e) {
		switch (orientation) {
		case SWT.HORIZONTAL:
			int movedX = e.x - thumb.getBounds().x;
			if(movedX > 0){
				movedX -= 15;
			}
			setValue(getValue() + pelsToValue(movedX));
			break;
		case SWT.VERTICAL:
			int movedY = e.y - thumb.getBounds().y;
			setValue(getValue() + pelsToValue(movedY));
			break;
		}
	}
	
	public void setThumbImage(Image thumbImage) {
		this.thumbImage = thumbImage;
		thumb.setBackgroundImage(thumbImage);
		moveThumb();
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		if (value < 0) {
			this.value = 0;
		} else if (value > getMaxValue()) {
			this.value = getMaxValue();
		} else {
			this.value = value;
		}
		try {
			moveThumb();
			//redraw();
		} finally {
			for (SliderListener listener : listeners) {
				try {
					SliderEvent event = new SliderEvent(this, getValue());
					listener.valueChanged(event);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}

	private void moveThumb() {
		Image icon = thumb.getBackgroundImage();
		int iconw = (icon != null) ? icon.getBounds().width : 0;
		int iconh = (icon != null) ? icon.getBounds().height : 0;
		switch (orientation) {
		case SWT.HORIZONTAL:
			int x = valueToPels(getValue()) - iconw / 2;
			if (x < 0) {
				x = 0;
			} else if (x > getBounds().width - iconw) {
				x = getBounds().width - iconw;
			}
			thumb.setBounds(x, 0, iconw, iconh);
			break;
		case SWT.VERTICAL:
			int y = valueToPels(getValue()) - iconh / 2;
			if (y < 0) {
				y = 0;
			} else if (y > getBounds().height - iconh) {
				y = getBounds().height - iconh;
			}
			thumb.setBounds(0, y, iconw, iconh);
			break;
		}
	}

	/**
     * 缺省的鼠标动作
     * 
     * @author joey
     */
    private class LevelBarMouseListener extends MouseAdapter {
        public void mouseUp(MouseEvent e) {
        	countValue2(e);
        }
    }
	public int getMaxValue() {
		return maxValue;
	}

	public void setMaxValue(int maxValue) {
		this.maxValue = maxValue;
	}
}
