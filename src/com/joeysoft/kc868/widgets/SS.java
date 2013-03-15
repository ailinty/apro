package com.joeysoft.kc868.widgets;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.ScrollBar;
import org.eclipse.swt.widgets.Shell;
public class SS {
	public static void main(String[] args) {
		Display display = new Display();
		Color red = display.getSystemColor(SWT.COLOR_RED);
		Color blue = display.getSystemColor(SWT.COLOR_BLUE);
		Shell shell = new Shell(display);
		//shell.setLayout(new FillLayout());
		// set the size of the scrolled content - method 1
		final ScrolledComposite sc1 = new ScrolledComposite(shell, SWT.H_SCROLL | SWT.BORDER);
		final Composite c1 = new Composite(sc1, SWT.NONE);
		sc1.setContent(c1);
		
		// set the minimum width and height of the scrolled content - method 2
		final ScrolledComposite sc2 = new ScrolledComposite(shell, SWT.H_SCROLL| SWT.V_SCROLL | SWT.BORDER);
		sc2.setExpandHorizontal(true);
		sc2.setExpandVertical(true);
		final Composite c2 = new Composite(sc2, SWT.NONE);
		sc2.setContent(c2);
		c2.setBackground(blue);
		GridLayout layout = new GridLayout();
		layout.numColumns = 10;
		c2.setLayout(layout);
		
		
		
		c1.setBackground(red);
		layout = new GridLayout();
		layout.numColumns = 4;
		c1.setLayout(layout);
		Button b1 = new Button(c1, SWT.PUSH);
		b1.setText("first button");
		
		b1.addMouseListener(new MouseAdapter(){
			public void mouseUp(MouseEvent e) {
				//c2.setLocation(pt.x +  10, pt.y);
				
				Point location = c2.getLocation ();
				ScrollBar hBar = sc2.getHorizontalBar();
				hBar.setSelection(hBar.getSelection() - 20);
				System.out.println(hBar.getSelection());
				System.out.println("min:"+hBar.getIncrement());
				System.out.println("w:"+c2.getBounds().width);
				c2.setLocation (-hBar.getSelection(), location.y);
			}
		});
		
		Button b2 = new Button(c1, SWT.PUSH);
		b2.setText("button2222");
		b2.addMouseListener(new MouseAdapter(){
			public void mouseUp(MouseEvent e) {
				Point location = c2.getLocation ();
				
				ScrollBar hBar = sc2.getHorizontalBar();
				hBar.setSelection(hBar.getSelection() + 20);
				System.out.println(hBar.getSelection());
				System.out.println("w:"+c2.getBounds().width);
				c2.setLocation (-hBar.getSelection(), location.y);
			}
		});
		/*
		 * 这是两种用法中的一种，效果是：根据已经存在的组件，计算需要的composite的区域的大小
		 * 即运行结果中红色显示的部分。
		 */
		c1.setSize(c1.computeSize(SWT.DEFAULT, SWT.DEFAULT));
		
		
		Button b3 = new Button(c2, SWT.PUSH);
		b3.addMouseListener(new MouseAdapter(){
			public void mouseUp(MouseEvent e) {
				Point pt = c2.getLocation();
				c2.setLocation(pt.x +  10, pt.y);
				//hBar.setSelection(pt.x + 10);
	        }
		});
		
		
		
		/*
		 * 这是第二种用法，整个composite区域是用到的Composite区域
		 */
		sc2.setMinSize(c2.computeSize(SWT.DEFAULT, SWT.DEFAULT));
		Button add = new Button(shell, SWT.PUSH);
		add.setText("add children");
		final int[] index = new int[] { 0 };
		add.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event e) {
				index[0]++;
				Button button = new Button(c1, SWT.PUSH);
				button.setText("button " + index[0]);
				// reset size of content so children can be seen - method 1
				c1.setSize(c1.computeSize(SWT.DEFAULT, SWT.DEFAULT));
				c1.layout();
				button = new Button(c2, SWT.PUSH);
				button.setText("button " + index[0]);
				// reset the minimum width and height so children can be seen -
				// method 2
				sc2.setMinSize(c2.computeSize(SWT.DEFAULT, SWT.DEFAULT));
				c2.layout();
			}
		});
		shell.open();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch())
				display.sleep();
		}
		display.dispose();
	}
}
