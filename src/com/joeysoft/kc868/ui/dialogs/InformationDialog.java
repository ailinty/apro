package com.joeysoft.kc868.ui.dialogs;

import static com.joeysoft.kc868.resource.Messages.message_box_disconnection;
import static com.joeysoft.kc868.resource.Messages.message_box_receive_timeout;
import static com.joeysoft.kc868.resource.Messages.message_box_sync_error;
import static com.joeysoft.kc868.resource.Messages.message_box_unknown;
import static com.joeysoft.kc868.resource.Messages.splash_loading;
import static com.joeysoft.kc868.resource.Messages.splash_waiting;

import java.io.File;
import java.io.IOException;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.events.ShellAdapter;
import org.eclipse.swt.events.ShellEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.graphics.Pattern;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;

import com.joeysoft.kc868.KC868;
import com.joeysoft.kc868.SystemConfig;
import com.joeysoft.kc868.client.KC868Client;
import com.joeysoft.kc868.client.event.IPacketListener;
import com.joeysoft.kc868.client.event.PacketEvent;
import com.joeysoft.kc868.client.packets.ErrorPacket;
import com.joeysoft.kc868.client.packets.InPacket;
import com.joeysoft.kc868.client.packets.in.AlarmReadReplyPacket;
import com.joeysoft.kc868.client.packets.in.GetVersionReplyPacket;
import com.joeysoft.kc868.client.packets.in.ReadHostReplyPacket;
import com.joeysoft.kc868.client.packets.in.file.FileReadEndReplyPacket;
import com.joeysoft.kc868.client.packets.in.file.FileReadErrorReplyPacket;
import com.joeysoft.kc868.client.packets.in.file.FileReadHeadReplyPacket;
import com.joeysoft.kc868.client.packets.in.file.FileReadReplyPacket;
import com.joeysoft.kc868.client.packets.in.lineate.RelayReadNowReplyPacket;
import com.joeysoft.kc868.client.packets.out.AlarmReadPacket;
import com.joeysoft.kc868.client.packets.out.GetVersionPacket;
import com.joeysoft.kc868.client.packets.out.ReadHostPacket;
import com.joeysoft.kc868.client.packets.out.file.FileReadHeadPacket;
import com.joeysoft.kc868.client.packets.out.file.FileReadStartPacket;
import com.joeysoft.kc868.client.packets.out.lineate.RelayReadNowPacket;
import com.joeysoft.kc868.client.support.PacketProcessor;
import com.joeysoft.kc868.client.support.Protocol;
import com.joeysoft.kc868.client.util.FileTool;
import com.joeysoft.kc868.common.ZipUtil;
import com.joeysoft.kc868.db.bean.Alarm;
import com.joeysoft.kc868.db.bo.BOAlarm;
import com.joeysoft.kc868.db.util.DictConst;
import com.joeysoft.kc868.resource.IconHolder;
import com.joeysoft.kc868.ui.BorderStyler;
import com.joeysoft.kc868.ui.Colors;
import com.joeysoft.kc868.ui.MainShell;
import com.joeysoft.kc868.ui.helper.MessageBoxHelper;
import com.joeysoft.kc868.ui.helper.UITool;
import com.joeysoft.kc868.xsd.GlobalUtil;
import com.joeysoft.kc868.xsd.global.GlobalSetting;

import org.eclipse.swt.layout.FillLayout;

public class InformationDialog extends Dialog{

	private Display display;
	
	private Shell dialog;
	private Label lbTitle;
	private String title;
	
	private boolean ok;
	
	// IconHolder实例
    private IconHolder icons = IconHolder.getInstance();
    
	/**
	 * Create the dialog.
	 * @param parent
	 * @param style
	 */
	public InformationDialog(MainShell main) {
		super(main.getShell(), SWT.NO_TRIM | SWT.NO_BACKGROUND);
        display = main.getShell().getDisplay();
	}

	/**
	 * Open the dialog.
	 * @return the result
	 */
	public Object open() {
		// create dialog
	    Shell parent = getParent();
	    Display display = parent.getDisplay();
		dialog = new Shell(parent, getStyle());
		
		initLayout();
		
		dialog.setBackgroundImage(icons.getImage(IconHolder.bmpBg));
		dialog.setSize(300, 100);
		dialog.setBackgroundMode(SWT.INHERIT_FORCE);
		dialog.setLayout(new FillLayout());
		
		Rectangle bounds = display.getPrimaryMonitor().getBounds();
        Rectangle rect = dialog.getBounds();
        int x = bounds.x + (bounds.width - rect.width) / 2;
        int y = bounds.y + (bounds.height - rect.height) / 2;
        dialog.setLocation(x, y);
        
		dialog.open();
		while(!dialog.isDisposed()) 
			if(!display.readAndDispatch()) 
			    display.sleep();
		return ok;
	}
	
	public void close(){
		dialog.close();
	}

	/**
	 * Create contents of the dialog.
	 */
	private void initLayout() {
		BorderStyler styler = new BorderStyler();
    	styler.setHideWhenMinimize(false);
    	styler.setShowButton(false);
    	styler.setResizable(false);
    	Composite center = styler.decorateShell(dialog);
    	lbTitle = UITool.createLabel(center, title);
    	lbTitle.setBounds(10, 10, 200, 20);
	}


	public void setTitleText(String title) {
		this.title = title;
	}
}
