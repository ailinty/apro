package com.joeysoft.kc868.widgets;


import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

import com.joeysoft.kc868.ui.helper.UITool;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Label;

public class TEst {
	private Display display;
	private Composite composite;

	/**
	 * @wbp.parser.entryPoint
	 */
	public TEst() {
		display = Display.getDefault();
		final Shell shell = new Shell(display);
		
		shell.setSize(800, 600);
		shell.setText("GC实例!");
		shell.setLayout(new FillLayout());
		
		
		final ScrolledComposite scrolledComposite = new ScrolledComposite(
				shell, SWT.BORDER | SWT.H_SCROLL);
		scrolledComposite.setSize(600, 200);
		
		
		GridLayout layout = new GridLayout(3, false);
        layout.marginHeight = 1;
        layout.marginWidth = 3;
        
		composite = new Composite(scrolledComposite, SWT.BORDER);
		// 定义一个画布对象
		// composite.setBounds(0, 0, 1200, 200);
		// composite.setCapture(true);
		composite.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		composite.setLayout(layout);
		composite.setSize(600, 200);
		
		Button btnNewButton = new Button(composite, SWT.NONE);
		btnNewButton.setText("New Button");
		
		Button btnNewButton_1 = new Button(composite, SWT.NONE);
		btnNewButton_1.setText("New Button");
		
		shell.open();
		// =============================================================
		/*composite.addPaintListener(new PaintListener() {
			public void paintControl(final PaintEvent event) {
				Display display = event.display;
				GC gc = new GC(composite);
				gc.drawRectangle(30, 15, 2190, 1160);
				gc.setBackground(display.getSystemColor(SWT.COLOR_CYAN));
				gc.fillRectangle(30, 15, 10, 60);
				gc.dispose();
			}
		});*/
		scrolledComposite.setContent(composite);
		// =============================================================
		shell.layout();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
		display.dispose();
	}

	/**
	 * @wbp.parser.entryPoint
	 */
	public static void main(String[] args) {
		new HelloWorld17();
	}
}
