package com.joeysoft.kc868.ui.dialogs.helper;

import static com.joeysoft.kc868.resource.Messages.icon;
import static com.joeysoft.kc868.resource.Messages.select;
import static com.joeysoft.kc868.resource.Messages.sequence_number;
import static com.joeysoft.kc868.resource.Messages.floor;
import static com.joeysoft.kc868.resource.Messages.sequence;

import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;

import com.joeysoft.kc868.db.bean.Floor;

public final class TableFloorHelper {
	
	public Table createTable(Composite comp){
		Table table = new Table(comp, SWT.SINGLE | SWT.BORDER|SWT.FULL_SELECTION|SWT.HIDE_SELECTION|SWT.V_SCROLL);
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
        tc.setText(floor);
        tc.setWidth(601);
        
        return table;
	}
	
	/**
	 * 刷新Table
	 */
	public void showTable(Table table, List<Floor> list){
		table.removeAll();
		for(Floor floor : list){
			insertTable(table,floor);
		}
	}
	
	/**
	 * 添加一行
	 * @param floor
	 */
	public void insertTable(Table table, Floor floor){
		TableItem item = new TableItem(table, SWT.NONE); // 创建Item
		item.setData(floor);
	    item.setText(new String[] {""+table.getItemCount(), floor.getFloorName()});// 给Item设值
	}
	
	/**
	 * 修改一行
	 * @param tbFloor
	 * @param floor
	 */
	public void updateTable(Table table, Floor floor){
		if(floor != null){
			TableItem[] tia = table.getSelection();
			tia[0].setData(floor);
			tia[0].setText(new String[] {tia[0].getText(0), floor.getFloorName()});
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
