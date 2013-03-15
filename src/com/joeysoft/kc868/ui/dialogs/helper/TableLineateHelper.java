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

import com.joeysoft.kc868.db.bean.Lineate;
import com.joeysoft.kc868.db.util.DictConst;
import com.joeysoft.kc868.db.util.DictManager;
import com.joeysoft.kc868.resource.IconHolder;

public final class TableLineateHelper {
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
        tc.setText(floor);
        tc.setWidth(80);
        
        tc = new TableColumn(table, SWT.CENTER);
        tc.setText(room);
        tc.setWidth(80);
        
        tc = new TableColumn(table, SWT.CENTER);
        tc.setText(lineate_name);
        tc.setWidth(160);
        
        tc = new TableColumn(table, SWT.CENTER);
        tc.setText(signal_mode);
        tc.setWidth(100);
        
        tc = new TableColumn(table, SWT.CENTER);
        tc.setText(trigger_mode);
        tc.setWidth(100);
        
        
        return table;
	}
	
	/**
	 * 刷新Table
	 */
	public void showTable(Table table, List<Lineate> list){
		table.removeAll();
		for(Lineate lineate : list){
			insertTable(table, lineate);
		}
	}
	
	/**
	 * 添加一行
	 * @param floor
	 */
	public void insertTable(Table table, Lineate lineate){
		TableItem item = new TableItem(table, SWT.NONE); // 创建Item
		item.setData(lineate);
		String lineateStr = "";
		
		if(DictConst.LINEATE_TYPE_UD.equals(lineate.getLineateType())){
			lineateStr = DictManager.getInstance().getDictName(DictConst.LINEATE_UD, lineate.getLineateUD());
		}else{
			lineateStr = DictManager.getInstance().getDictName(DictConst.LINEATE_GL, lineate.getLineateGL()) + " " + lineate.getLineateV();
		}
		
	    item.setText(new String[] {""+table.getItemCount(), lineate.getFloorName(), lineate.getRoomName(), lineate.getLineateName(),
	    		DictManager.getInstance().getDictName(DictConst.LINEATE_TYPE, lineate.getLineateType()), lineateStr});// 给Item设值
	    
	    
	}
	
	/**
	 * 修改一行
	 * @param tbFloor
	 * @param floor
	 */
	public void updateTable(Table table, Lineate lineate){
		if(lineate != null){
			TableItem[] tia = table.getSelection();
			tia[0].setData(lineate);
			String lineateStr = "";
			
			if(DictConst.LINEATE_TYPE_UD.equals(lineate.getLineateType())){
				lineateStr = DictManager.getInstance().getDictName(DictConst.LINEATE_UD, lineate.getLineateUD());
			}else{
				lineateStr = DictManager.getInstance().getDictName(DictConst.LINEATE_GL, lineate.getLineateGL()) + " " + lineate.getLineateV();
			}
			
			tia[0].setText(new String[] {tia[0].getText(0), lineate.getFloorName(), lineate.getRoomName(), lineate.getLineateName(),
		    		DictManager.getInstance().getDictName(DictConst.LINEATE_TYPE, lineate.getLineateType()), lineateStr});// 给Item设值
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
