package com.joeysoft.kc868.ui.dialogs.helper;

import static com.joeysoft.kc868.resource.Messages.*;
import org.eclipse.swt.SWT;

import org.eclipse.swt.custom.CCombo;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;

import com.joeysoft.kc868.common.GlobalConst;
import com.joeysoft.kc868.db.bean.Alarm;
import com.joeysoft.kc868.db.bo.BOAlarm;
import com.joeysoft.kc868.db.util.DictConst;
import com.joeysoft.kc868.resource.IconHolder;
import com.joeysoft.kc868.resource.Messages;
import com.joeysoft.kc868.ui.Colors;
import com.joeysoft.kc868.ui.SmartShell;
import com.joeysoft.kc868.ui.dialogs.InformationDialog;
import com.joeysoft.kc868.ui.helper.UITool;
import com.joeysoft.kc868.widgets.ImageButton;

/**
 * 定时器UI帮助类
 * @author JOEY
 *
 */
public class UIAlarmHelper {
	private Button checkAlarm[], checkSun[], checkMon[], checkTues[], checkWed[], checkThurs[], checkFrid[], checkSat[];
	private CCombo comboHour[], comboMinute[], comboSecond[];
	private ImageButton btnWrite, btnRead;
	
	private IconHolder icons = IconHolder.getInstance();
	
	private SmartShell smartShell;
	
	private BOAlarm boAlarm = new BOAlarm();
	private int alarmCount=0, alarmIndex=0;
	
	private InformationDialog informationDialog;
	 
	public UIAlarmHelper(SmartShell smartShell){
		this.smartShell = smartShell;
	}
	
	public void createAlarmUI(Composite comp){
		comp.setVisible(false);
		clearComposite(comp);
		checkAlarm = new Button[DictConst.ALARM_COUT];
		checkSun = new Button[DictConst.ALARM_COUT];
		checkMon = new Button[DictConst.ALARM_COUT];
		checkTues = new Button[DictConst.ALARM_COUT];
		checkWed = new Button[DictConst.ALARM_COUT];
		checkThurs = new Button[DictConst.ALARM_COUT];
		checkFrid = new Button[DictConst.ALARM_COUT];
		checkSat = new Button[DictConst.ALARM_COUT];
				
		comboHour = new CCombo[DictConst.ALARM_COUT];
		comboSecond = new CCombo[DictConst.ALARM_COUT];
		comboMinute = new CCombo[DictConst.ALARM_COUT];
		
		for(int i=0; i<DictConst.ALARM_COUT; i++){
			//Alarm alarm = SystemConfig.getInstance().getAlarm(i+1);
			Alarm alarm = boAlarm.get(i+1);
			final int index = i;
			checkAlarm[i] = UITool.createCheckbox(comp, Messages.alarm+(i+1));
			checkAlarm[i].setBounds(0, i*40, 80, 20);
			checkAlarm[i].addSelectionListener(new SelectionAdapter(){
				@Override
				public void widgetSelected(SelectionEvent e) {
						comboHour[index].setEnabled(checkAlarm[index].getSelection());
						comboMinute[index].setEnabled(checkAlarm[index].getSelection());
						comboSecond[index].setEnabled(checkAlarm[index].getSelection());
						checkSat[index].setEnabled(checkAlarm[index].getSelection());
						checkFrid[index].setEnabled(checkAlarm[index].getSelection());
						checkThurs[index].setEnabled(checkAlarm[index].getSelection());
						checkWed[index].setEnabled(checkAlarm[index].getSelection());
						checkTues[index].setEnabled(checkAlarm[index].getSelection());
						checkMon[index].setEnabled(checkAlarm[index].getSelection());
						checkSun[index].setEnabled(checkAlarm[index].getSelection());
				}
	        });

			Label label = UITool.createLabel(comp, week+":");
			label.setBounds(90, i*40 + 4, 50, 20);
			label.setForeground(Colors.BLACK);
			
			checkSun[i] = UITool.createCheckbox(comp, week_7);
			checkSun[i].setBounds(140, i*40, 30, 20);
			checkSun[i].setEnabled(false);

			checkMon[i] = UITool.createCheckbox(comp, week_1);
			checkMon[i].setBounds(180, i*40, 30, 20);
			checkMon[i].setEnabled(false);
			
			checkTues[i] = UITool.createCheckbox(comp, week_2);
			checkTues[i].setBounds(220, i*40, 30, 20);
			checkTues[i].setEnabled(false);
			
			checkWed[i] = UITool.createCheckbox(comp, week_3);
			checkWed[i].setBounds(260, i*40, 30, 20);
			checkWed[i].setEnabled(false);
			
			checkThurs[i] = UITool.createCheckbox(comp, week_4);
			checkThurs[i].setBounds(300, i*40, 30, 20);
			checkThurs[i].setEnabled(false);
			
			checkFrid[i] = UITool.createCheckbox(comp, week_5);
			checkFrid[i].setBounds(340, i*40, 30, 20);
			checkFrid[i].setEnabled(false);
			
			checkSat[i] = UITool.createCheckbox(comp, week_6);
			checkSat[i].setBounds(380, i*40, 30, 20);
			checkSat[i].setEnabled(false);
			
			label = UITool.createLabel(comp, time+":");
			label.setBounds(450, i*40 + 4, 40, 20);
			label.setForeground(Colors.BLACK);
			String[] items = new String[24];
			for(int h=0; h<24; h++){
				items[h] = String.valueOf(h);
			}
			
			comboHour[i] = UITool.createCCombo(comp, SWT.SIMPLE | SWT.READ_ONLY);
			comboHour[i].setBounds(490, i*40, 40, 20);
			comboHour[i].setBackground(Colors.WHITE);
			comboHour[i].setItems(items);
			comboHour[i].select(0);
			comboHour[i].setEditable(false);
			comboHour[i].setEnabled(false);
			
			label = UITool.createLabel(comp, time_h);
			label.setBounds(535, i*40 + 4, 15, 20);
			label.setForeground(Colors.BLACK);
			
			items = new String[60];
			for(int h=0; h<60; h++){
				items[h] = String.valueOf(h);
			}
			
			comboMinute[i] = UITool.createCCombo(comp, SWT.SIMPLE | SWT.READ_ONLY);
			comboMinute[i].setBounds(560, i*40, 40, 20);
			comboMinute[i].setBackground(Colors.WHITE);
			comboMinute[i].setItems(items);
			comboMinute[i].select(0);
			comboMinute[i].setEditable(false);
			comboMinute[i].setEnabled(false);
			
			label = UITool.createLabel(comp, time_m);
			label.setBounds(605, i*40 + 4, 15, 20);
			label.setForeground(Colors.BLACK);
			
			comboSecond[i] = UITool.createCCombo(comp, SWT.SIMPLE | SWT.READ_ONLY);
			comboSecond[i].setBounds(630, i*40, 40, 20);
			comboSecond[i].setBackground(Colors.WHITE);
			comboSecond[i].setItems(items);
			comboSecond[i].select(0);
			comboSecond[i].setEditable(false);
			comboSecond[i].setEnabled(false);
			
			label = UITool.createLabel(comp, time_s);
			label.setBounds(675, i*40 + 4, 15, 20);
			label.setForeground(Colors.BLACK);
			if(alarm != null){
				int week = alarm.getAlarmWeek();
				
				if((week&0x01) == 0x01){
					checkSun[i].setSelection(true);
				}
				if((week&0x02) == 0x02){
					checkMon[i].setSelection(true);
				}
				if((week&0x04) == 0x04){
					checkTues[i].setSelection(true);
				}
				if((week&0x08) == 0x08){
					checkWed[i].setSelection(true);
				}
				if((week&0x10) == 0x10){
					checkThurs[i].setSelection(true);
				}
				if((week&0x20) == 0x20){
					checkFrid[i].setSelection(true);
				}
				if((week&0x40) == 0x40){
					checkSat[i].setSelection(true);
				}
				
				comboHour[i].setText(alarm.getAlarmHour());
				comboMinute[i].setText(alarm.getAlarmMinute());
				comboSecond[i].setText(alarm.getAlarmSecond());
			}
		}
		
		// 控钮区
		btnWrite =  UITool.createImageButton(comp, alarm_write, icons.getImage(IconHolder.bmpBtnLogin), 
        		icons.getImage(IconHolder.bmpBtnLoginUp), icons.getImage(IconHolder.bmpBtnLoginOn));
		btnWrite.setFont(Colors.GLOBAL_FONT);
		btnWrite.setBounds(277, 370, 114, 41);
		btnWrite.addMouseListener(new MouseAdapter() {
            public void mouseUp(MouseEvent e) {
            	writeAlarm();
            }
		});
		
		comp.setVisible(true);
	}
	
	private void writeAlarm(){
		alarmCount = 0;
		alarmIndex = 0;
		for(int i=0; i<DictConst.ALARM_COUT; i++){
			String alarmParams = "";
			int week = 0;
			if(checkAlarm[i].getSelection()){
				alarmCount++;
				if(checkSun[i].getSelection()){
					week += 1;
				}
				if(checkMon[i].getSelection()){
					week += 2;
				}
				if(checkTues[i].getSelection()){
					week += 4;
				}
				if(checkWed[i].getSelection()){
					week += 8;
				}
				if(checkThurs[i].getSelection()){
					week += 16;
				}
				if(checkFrid[i].getSelection()){
					week += 32;
				}
				if(checkSat[i].getSelection()){
					week += 64;
				}
				
				alarmParams += week + GlobalConst.CONST_STRING_COMMA +
						comboHour[i].getText() + GlobalConst.CONST_STRING_COMMA + comboMinute[i].getText() + 
						GlobalConst.CONST_STRING_COMMA + comboSecond[i].getText();
				
				Alarm alarm = new Alarm();
				alarm.setAlarmId(i + 1);
	    		alarm.setAlarmWeek(week);
	    		alarm.setAlarmHour(comboHour[i].getText());
	    		alarm.setAlarmMinute(comboMinute[i].getText());
	    		alarm.setAlarmSecond(comboSecond[i].getText());
	    		
				//SystemConfig.getInstance().putAlarm(alarm);
	    		boAlarm.update(alarm);
				
				/*try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}*/
				smartShell.sendAlarmWritePacket(i + 1, alarmParams);
			}
		}
		//informationDialog =  new InformationDialog(smartShell.getMainShell());
		//informationDialog.setText("主机复位中...");
		//informationDialog.setTitleText("主机复位中...");
		//informationDialog.open();
	}
	
	public int getAlarmCount() {
		return alarmCount;
	}

	public int getAlarmIndex() {
		return alarmIndex;
	}

	public void plusAlarmIndex() {
		this.alarmIndex++;
	}

	/**
     * 清空面板
     */
    private void clearComposite(Composite comp){
    	for(Control c : comp.getChildren()){
    		if(c!= null && !c.isDisposed()){
    			c.dispose();
    		}
    	}
    	comp.redraw();
    }

	public InformationDialog getInformationDialog() {
		return informationDialog;
	}
    
    
}
