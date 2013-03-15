package com.joeysoft.kc868.ui;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;

public class ControlMove {
    public static void main(String[] args) {
        Display display = new Display();
        final Shell shell = new Shell(display);
        shell.setLayout(new FillLayout()); // <-- use FillLayout to force
        // composite take entire shell
        // client area
        final Composite composite = new Composite(shell, SWT.NULL);
        composite.setEnabled(false);
        FormLayout formLayout = new FormLayout();
        composite.setLayout(formLayout);

        final Button button = new Button(composite, SWT.PUSH);
        button.setText("Button");
        composite.pack();
        composite.setBackground(new Color(display, 255, 0, 0)); // <-- Set to
        // RED allow you
        // to see
        // composite
        // size

        final Point[] offset = new Point[1];
        Listener listener = new Listener() {
            public void handleEvent(Event event) {
                switch (event.type) {
                case SWT.MouseDown:
                    Rectangle rect = button.getBounds(); // <-- change to move
                    // button within
                    // composite
                    if (rect.contains(event.x, event.y)) {
                        /*
                        * Returns a point which is the result of converting the
                        * argument, which is specified in coordinates relative
                        * to the receiver, to display relative coordinates.
                        */
                        Point pt1 = button.toDisplay(0, 0);
                        Point pt2 = composite.toDisplay(event.x, event.y);
                        offset[0] = new Point(pt2.x - pt1.x, pt2.y - pt1.y);
                    }
                    break;
                case SWT.MouseMove:
                    if (offset[0] != null) {
                        Point pt = offset[0];
                        button.setLocation(event.x - pt.x, event.y - pt.y); //<--
                        // move
                        // button
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
        shell.setSize(300, 300);
        shell.open();
        while (!shell.isDisposed()) {
            if (!display.readAndDispatch())
                display.sleep();
        }
        display.dispose();
    }
}
 


