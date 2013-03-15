package com.joeysoft.kc868.ui.dialogs.helper;

import static com.joeysoft.kc868.resource.Messages.*;

import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;

import com.joeysoft.kc868.db.bean.TelOut;
import com.joeysoft.kc868.db.util.DictConst;
import com.joeysoft.kc868.db.util.DictManager;
import com.joeysoft.kc868.resource.IconHolder;

public final class TableTelOutHelper {
	private IconHolder icons = IconHolder.getInstance();
	
	public Table createTable(Composite comp){
		Table table = new Table(comp, SWT.SINGLE | SWT.BORDER|SWT.FULL_SELECTION|SWT.V_SCROLL);
        //tbList.setBackgroundImage(image);
        //tbList.setBackgroundMode(SWT.INHERIT_FORCE);
        //tbList.setBackground(Colors.LOGIN_BACKGROUND);
		table.setHeaderVisible(true);
        table.setLinesVisible(true);
        table.addListener(SWT.MeasureItem, new Listener() {
		    public void handleEvent(Event event) {
		        event.height = 30;
		    }
		});
        TableColumn tc = new TableColumn(table, SWT.CENTER);
        tc.setText(sequence);
        tc.setWidth(45);
        
        tc = new TableColumn(table, SWT.CENTER);
        tc.setText(name);
        tc.setWidth(311);
        
        tc = new TableColumn(table, SWT.CENTER);
        tc.setText(country_code);
        tc.setWidth(100);
        
        tc = new TableColumn(table, SWT.CENTER);
        tc.setText(telphone);
        tc.setWidth(200);
        
        return table;
	}
	
	/**
	 * 刷新Table
	 */
	public void showTable(Table table, List<TelOut> list){
		table.removeAll();
		for(TelOut telOut : list){
			insertTable(table, telOut);
		}
	}
	
	/**
	 * 添加一行
	 * @param telOut
	 */
	public void insertTable(Table table, TelOut telOut){
		TableItem item = new TableItem(table, SWT.NONE); // 创建Item
		item.setData(telOut);
	    item.setText(new String[] {""+table.getItemCount(), telOut.getTelName(), telOut.getCountryCode(), telOut.getTelPhone()});// 给Item设值
	}
	
	/**
	 * 修改一行
	 * @param tbFloor
	 * @param floor
	 */
	public void updateTable(Table table, TelOut telOut){
		if(telOut != null){
			TableItem[] tia = table.getSelection();
			tia[0].setData(telOut);
			tia[0].setText(new String[] {tia[0].getText(0), telOut.getTelName(), telOut.getCountryCode(), telOut.getTelPhone()});// 给Item设值
		}
	}
	
	/**
	 * 删除一行
	 * @param table
	 */
	public void deleteTable(Table table){
		int index = table.getSelectionIndex();
		for(int i= index+ 1;i<table.getItemCount();i++){
			TableItem item = table.getItem(i);
			item.setText(0, ""+i);
		}
		
		table.remove(index);
	}
}
