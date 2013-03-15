package com.joeysoft.kc868.db.bean;

import java.util.HashMap;
import java.util.Map;

/**
 * 图标类
 * @author JOEY
 *
 */
public class Icon {
	private String iconName;
	
	private String iconType;
	
	private int iconHeight;
	
	private int iconWidth;
	
	public Icon(){
		
	}
	
	public Icon(String iconName, String iconType, int iconHeight, int iconWidth){
		this.iconName = iconName;
		this.iconType = iconType;
		this.iconHeight = iconHeight;
		this.iconWidth = iconWidth;
	}
	
	public Map<String, Object> fillMap(){
		Map<String, Object> htParam = new HashMap<String, Object>();
		htParam.put("ICON_NAME", this.iconName);
		htParam.put("ICON_TYPE", this.iconType);
		htParam.put("ICON_HEIGHT", this.iconHeight);
		htParam.put("ICON_WIDTH", this.iconWidth);
		
		return htParam;
	}

	public String getIconName() {
		return iconName;
	}

	public void setIconName(String iconName) {
		this.iconName = iconName;
	}

	public int getIconHeight() {
		return iconHeight;
	}

	public void setIconHeight(int iconHeight) {
		this.iconHeight = iconHeight;
	}

	public int getIconWidth() {
		return iconWidth;
	}

	public void setIconWidth(int iconWidth) {
		this.iconWidth = iconWidth;
	}

	public String getIconType() {
		return iconType;
	}

	public void setIconType(String iconType) {
		this.iconType = iconType;
	}
	
	
}
