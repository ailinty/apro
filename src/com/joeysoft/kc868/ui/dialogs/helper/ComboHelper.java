package com.joeysoft.kc868.ui.dialogs.helper;

import java.util.List;

import org.eclipse.swt.custom.CCombo;

import com.joeysoft.kc868.db.bean.Dict;
import com.joeysoft.kc868.db.bean.Floor;
import com.joeysoft.kc868.db.bean.Icon;
import com.joeysoft.kc868.db.bean.Room;
import com.joeysoft.kc868.db.bean.Scene;
import com.joeysoft.kc868.db.bean.Transfer;

public class ComboHelper {
	/**
	 * 初始化数据字典combo
	 * @param combo
	 * @param list
	 */
	public static void initComboList(CCombo combo, List<Dict> list){
		combo.removeAll();
		for(int i=0; i<list.size(); i++){
			Dict dict = list.get(i); 
			combo.add(dict.getDictName(), i);
			combo.setData(dict.getDictName(), dict.getDictValue());
		}
	}
	
	/**
	 * 初始化楼层combo
	 * @param combo
	 * @param list
	 */
	public static void initFloorComboList(CCombo combo, List<Floor> list){
		combo.removeAll();
        for(int i=0; i<list.size(); i++){
        	Floor floor = list.get(i);
        	combo.add(floor.getFloorName(), i);
        	combo.setData(floor.getFloorName(), floor);
        }
	}
	
	/**
	 * 初始化楼层combo
	 * @param combo
	 * @param list
	 */
	public static void initRoomComboList(CCombo combo, List<Room> list){
		combo.removeAll();
        for(int i=0; i<list.size(); i++){
        	Room room = list.get(i);
        	combo.add(room.getRoomName(), i);
        	combo.setData(room.getRoomName(), room);
        }
	}
	
	/**
	 * 初始化图标combo
	 * @param combo
	 * @param list
	 */
	public static void initIconComboList(CCombo combo, List<Icon> list){
		combo.removeAll();
        for(int i=0; i<list.size(); i++){
        	Icon icon = list.get(i);
        	combo.add(icon.getIconName(), i);
        	combo.setData(icon.getIconName(), icon);
        }
	}
	
	/**
	 * 初始化图标combo
	 * @param combo
	 * @param list
	 */
	public static void initTransferComboList(CCombo combo, List<Transfer> list){
		combo.removeAll();
        for(int i=0; i<list.size(); i++){
        	Transfer transfer = list.get(i);
        	combo.add(transfer.getTransferName(), i);
        	combo.setData(transfer.getTransferName(), transfer);
        }
	}
	
	/**
	 * 初始化Scene combo
	 * @param combo
	 * @param list
	 */
	public static void initSceneComboList(CCombo combo, List<Scene> list){
		combo.removeAll();
        for(int i=0; i<list.size(); i++){
        	Scene scene = list.get(i);
        	combo.add(scene.getSceneName(), i);
        	combo.setData(scene.getSceneName(), scene);
        }
	}
	
	/**
	 * 初始化Scene combo
	 * @param combo
	 * @param list
	 */
	public static void initSceneIconComboList(CCombo combo, List<Icon> list){
		combo.removeAll();
        for(int i=0; i<list.size(); i++){
        	Icon icon = list.get(i);
        	combo.add(icon.getIconName(), i);
        	combo.setData(icon.getIconName(), icon);
        }
	}
}
