package com.joeysoft.kc868.ui.dialogs.helper;

import static com.joeysoft.kc868.resource.Messages.floor;
import static com.joeysoft.kc868.resource.Messages.room;
import static com.joeysoft.kc868.resource.Messages.sequence;

import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;

import com.joeysoft.kc868.db.bean.Room;
import com.joeysoft.kc868.resource.IconHolder;

public final class TableRoomHelper {
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
        tc.setText(floor);
        tc.setWidth(280);
        
        tc = new TableColumn(table, SWT.CENTER);
        tc.setText(room);
        tc.setWidth(321);
        
        return table;
	}
	
	/**
	 * 刷新Table
	 */
	public void showTable(Table table, List<Room> list){
		table.removeAll();
		for(Room room : list){
			insertTable(table, room);
		}
	}
	
	/**
	 * 添加一行
	 * @param floor
	 */
	public void insertTable(Table table, Room room){
		TableItem item = new TableItem(table, SWT.NONE); // 创建Item
		item.setData(room);
	    item.setText(new String[] {""+table.getItemCount(), room.getFloorName(), room.getRoomName()});// 给Item设值
	}
	
	/**
	 * 修改一行
	 * @param tbFloor
	 * @param floor
	 */
	public void updateTable(Table table, Room room){
		if(room != null){
			TableItem[] tia = table.getSelection();
			tia[0].setData(room);
			tia[0].setText(new String[] {tia[0].getText(0), room.getFloorName(), room.getRoomName()});
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
