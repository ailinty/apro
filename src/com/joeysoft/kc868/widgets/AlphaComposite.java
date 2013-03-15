package com.joeysoft.kc868.widgets;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;

public class AlphaComposite extends Composite implements PaintListener{
	
	private Image backgroundImage;

	public AlphaComposite(Composite parent, int style) {
		super(parent, SWT.TRANSPARENCY_ALPHA | style);
		addPaintListener(this);
	}

	@Override
	public void paintControl(PaintEvent e) {
		GC gc = e.gc;
		if(backgroundImage != null){
			gc.drawImage(backgroundImage, 0, 0);
		}
	}
	
	public void setBackgroundImage(Image backgroundImage) {
		this.backgroundImage = backgroundImage;
	}

	public Image getBackgroundImage() {
		return backgroundImage;
	}
}
