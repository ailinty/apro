package com.joeysoft.kc868.widgets;

import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.TabItem;
import org.eclipse.swt.widgets.Scale;

public class Test2 {

	/**
	 * Launch the application.
	 * @param args
	 */
	public static void main(String[] args) {
		Display display = Display.getDefault();
		Shell shell = new Shell();
		shell.setSize(450, 300);
		shell.setText("SWT Application");
		
		Scale scale = new Scale(shell, SWT.NONE);
		scale.setBounds(66, 58, 170, 42);
		scale.setMaximum(60);
		scale.setIncrement(10);
		scale.setMinimum(0);
		scale.addSelectionListener(new SelectionListener(){
			@Override
			public void widgetSelected(SelectionEvent e) {
				Scale s = (Scale)e.getSource();
				System.out.println(s.getSelection());
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				
			}
			
		});

		shell.open();
		shell.layout();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}
}
