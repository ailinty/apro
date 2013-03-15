package com.joeysoft.kc868.widgets;

import org.eclipse.swt.*;
import org.eclipse.swt.events.*;
import org.eclipse.swt.graphics.*;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.*;

public class Snippet9 {

	public static void main(String[] args) {
		Display display = new Display();
		final Shell shell = new Shell(display, SWT.SHELL_TRIM | SWT.H_SCROLL
				| SWT.V_SCROLL);
		final Composite composite = new Composite(shell, SWT.BORDER);
		composite.setEnabled(false);
		composite.setLayout(new FillLayout());
		composite.setSize(700, 600);
		final Color red = display.getSystemColor(SWT.COLOR_RED);
		composite.addPaintListener(new PaintListener() {
			public void paintControl(PaintEvent e) {
				e.gc.setBackground(red);
				e.gc.fillOval(5, 5, 690, 590);
			}
		});
		final ScrollBar hBar = shell.getHorizontalBar();
		hBar.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event e) {
				Point location = composite.getLocation();
				location.x = -hBar.getSelection();
				composite.setLocation(location);
			}
		});
		final ScrollBar vBar = shell.getVerticalBar();
		vBar.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event e) {
				Point location = composite.getLocation();
				location.y = -vBar.getSelection();
				composite.setLocation(location);
			}
		});
		shell.addListener(SWT.Resize, new Listener() {
			public void handleEvent(Event e) {
				Point size = composite.getSize();
				Rectangle rect = shell.getClientArea();
				hBar.setMaximum(size.x);
				vBar.setMaximum(size.y);
				hBar.setThumb(Math.min(size.x, rect.width));
				vBar.setThumb(Math.min(size.y, rect.height));
				int hPage = size.x - rect.width;
				int vPage = size.y - rect.height;
				int hSelection = hBar.getSelection();
				int vSelection = vBar.getSelection();
				Point location = composite.getLocation();
				if (hSelection >= hPage) {
					if (hPage <= 0)
						hSelection = 0;
					location.x = -hSelection;
				}
				if (vSelection >= vPage) {
					if (vPage <= 0)
						vSelection = 0;
					location.y = -vSelection;
				}
				composite.setLocation(location);
			}
		});
		final Point[] offset = new Point[1];
		Listener listener = new Listener() {
			public void handleEvent(Event event) {
				switch (event.type) {
				case SWT.MouseDown:
					Rectangle rect = composite.getBounds();
					if (rect.contains(event.x, event.y)) {
						Point pt1 = composite.toDisplay(0, 0);
						Point pt2 = shell.toDisplay(event.x, event.y);
						offset[0] = new Point(pt2.x - pt1.x, pt2.y - pt1.y);
					}
					break;
				case SWT.MouseMove:
					if (offset[0] != null) {
						Point pt = offset[0];
						composite.setLocation(event.x - pt.x, event.y - pt.y);
						System.out
								.println("x : " + event.x + " y : " + event.y);
						hBar.setSelection(pt.x - event.x);
						vBar.setSelection(pt.y - event.y);
					}
					break;
				case SWT.MouseUp:
					offset[0] = null;
					break;
				}
			}
		};
		shell.addListener(SWT.MouseDown, listener);
		shell.addListener(SWT.MouseUp, listener);
		shell.addListener(SWT.MouseMove, listener);
		shell.setSize(600, 500);
		shell.open();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch())
				display.sleep();
		}
		display.dispose();
	}

}
