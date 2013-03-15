package com.joeysoft.kc868.resource;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.resource.ImageRegistry;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.widgets.Display;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.joeysoft.kc868.KC868;

/**
 * 管理图像资源
 */
public class IconHolder {
    // Log
    private static Logger logger = LoggerFactory.getLogger(IconHolder.class);
    		
    // 常用图片路径
    private static final String ICON_PATH_PREFIX = "/com/joeysoft/kc868/resource/icon";
    
    private static final String IMAGE_PATH_PREFIX = "/com/joeysoft/kc868/resource/images";
    
    // resource id
    public static final int 
    	icoHome = 0,
    	bmpCloseDown = 1,
		bmpCloseHover = 2,
		bmpCloseNormal = 3,
		bmpMinDown = 4,
		bmpMinHover = 5,
		bmpMinNormal = 6,
		bmpMaxDown = 7,
		bmpMaxHover = 8,
		bmpMaxNormal = 9,
		bmpRestoreDown = 10,
		bmpRestoreHover = 11,
		bmpRestoreNormal = 12,
		bmpBg = 13,
		bmpBgLogin = 14,
		bmpBgLoading = 15,
    	bmpBtnLogin = 16,
		bmpBtnLoginUp = 17,
		bmpBtnLoginOn = 18,
		bmpCheckbox = 19,
		bmpCheckboxOn = 20,
		bmpBgLoadingRing = 21,
		bmpBgMain = 22,
		icoHouse = 23,
		icoHelp = 24,
		icoInfo = 25,
		icoSys = 26,
		icoSmart = 27,
		icoSafety = 28,
		icoEquip = 29,
		icoScene = 30,
		icoHouseOn = 31,
		icoHelpOn = 32,
		icoInfoOn = 33,
		icoSysOn = 34,
		icoSmartOn = 35,
		icoSafetyOn = 36,
		icoEquipOn = 37,
		icoSceneOn = 38,
		
		icoExit = 39,
		icoAbout = 40,
		icoSysOpt = 41,
		icoChangeUser = 42,
		icoUpdate = 43,
		icoFirefox = 44,
    	icoLanguage = 45,
    
   		bmpBgSub = 46,
    	btnSubMenu = 47,
    	btnSubMenuOff = 48,
    	btnBack = 49,
    	btnBackOn = 50,
    	btnNormal = 51,
    	btnNormalOn = 52,
    	btnFloor = 53,
	    btnFloorLeft = 54,
	    btnFloorRight = 55,
	    icoSx = 56,
		icoLt = 57,
		icoMt = 58,
		icoKt = 59,
		icoCl = 60,
		icoMb = 61,
		icoXd = 62,
		icoOt = 63,
    	icoSxOn = 64,
		icoLtOn = 65,
		icoMtOn = 66,
		icoKtOn = 67,
		icoClOn = 68,
		icoMbOn = 69,
		icoXdOn = 70,
		icoOtOn = 71,
		btnLeft = 72,
    	btnRight = 73,
    	bmpBgRoom= 74,
    	bmpBgLight= 75,
    	btnLightAdjust= 76,
	    btnLightOn= 77,
	    btnLightOff= 78,
		icoWed = 79,
		icoTemp = 80,
		btnAirAdjust = 81,
		btnAdjust = 82,
		icoCon1 = 83,
		icoCon2 = 84,
		icoCon3 = 85,
		icoCon4 = 86,
		icoCon5 = 87,
		icoCon6 = 88,
		icoCon7 = 89,
		icoCon8 = 90,
	    icoCon9 = 91,
	    icoCon10 = 92,
    	btnCon = 93,
    	btnBig = 94,
    	icoScreen = 95,
    	icoScreenClose = 96,
    	btnMedia = 97,
    	btnMedia1 = 98,    
    	btnMedia2 = 99,
    	btnMedia3 = 100,
    	icoMedia1 = 101,
		icoMedia2 = 102,
		icoMedia3 = 103,
		icoMedia4 = 104,
		icoMedia5 = 105,
		icoMedia6 = 106,
		icoMedia7 = 107,
		icoMedia8 = 108,
		icoMedia9 = 109,
		icoUp = 110,
		icoDown = 111,
		icoLeft = 112,
		icoRight = 113,
		icoWindow = 114,
    	icoWindowClose = 115,
    	btnSafeSet = 116,
		btnSafeUnSet = 117,
		btnBugleClose= 118,
		btnBugleOpen = 119,
		btnSafeSetOn = 120,
		btnSafeUnSetOn = 121,
		btnBugleCloseOn= 122,
		btnBugleOpenOn = 123,
		btnOther = 124,
		btnOtherOn = 125,
		btnOn = 126,
		btnOff = 127,
		icoC = 128,
		btnConOn = 129,
		btnBigOn = 130,
		btnMediaOn = 131,
    	btnMedia1On = 132,
    	btnMedia2On = 133,
		icoMedia1On = 134,
		icoMedia2On = 135,
		icoMedia3On = 136,
		icoMedia4On = 137,
		icoMedia5On = 138,
		icoMedia6On = 139,
		icoMedia7On = 140,
		icoMedia8On = 141,
		icoMedia9On = 142,
		btnBig3 = 143,
		btnBig3On = 144,
		icoScene1 = 145,
		icoScene2 = 146,
		icoScene3 = 147,
		icoScene4 = 148,
		icoScene5 = 149,
		icoScene6 = 150,
		icoScene7 = 151,
		icoScene8 = 152,
		icoScene1On = 153,
		icoScene2On = 154,
		icoScene3On = 155,
		icoScene4On = 156,
		icoScene5On = 157,
		icoScene6On = 158,
		icoScene7On = 159,
		icoScene8On = 160,
		icoSim2 = 161,
		icoSim1 = 162,
		switchOn = 163,
		switchOff = 164,
		icoSigna1 = 165,
		icoSigna2 = 166,
		icoSigna3 = 167,
		icoSigna4 = 168,
		icoSigna5 = 169,
		icoLoading = 170,
		icoScene1_32 = 171,
		icoScene2_32 = 172,
		icoScene3_32 = 173,
		icoScene4_32 = 174,
		icoScene5_32 = 175,
		icoScene6_32 = 176,
		icoScene7_32 = 177,
		icoScene8_32 = 178,
		switch1 = 179,
		switch2 = 180,
		switch3 = 181,
		bmpBtnClear = 182,
		bmpBtnClearUp = 183,
		bmpBtnClearOn = 184,
		bmpBtnPause = 185,
		bmpUser = 186,
		bmpUsers = 187,
	    bmpFolder = 188,
		bmpFolderDownloads = 189,
	    bmpFolderUpload = 190,
	    bmpInfo = 191,
	    bmpApply = 192,
	    bmpHistory = 193,
	    bmpFileTemp = 194,
	    bmpAppPreferences = 195,
	    bmpAppLargeIcons = 196,
	    bmpNew = 197,
	    bmpDelete = 198,
    	icoScene9 = 199,
		icoScene10 = 200,
		icoScene11 = 201,
		icoScene12 = 202,
		icoScene13 = 203,
		icoScene14 = 204,
		icoScene15 = 205,
		icoScene16 = 206,
		icoScene17 = 207,
		icoScene18 = 208,
		icoScene19 = 209,
		icoScene20 = 210,
		icoScene9On = 211,
		icoScene10On = 212,
		icoScene11On = 213,
		icoScene12On = 214,
		icoScene13On = 215,
		icoScene14On = 216,
		icoScene15On = 217,
		icoScene16On = 218,
		icoScene17On = 219,
		icoScene18On = 220,
		icoScene19On = 221,
		icoScene20On = 222,
		icoScene9_32 = 223,
		icoScene10_32 = 224,
		icoScene11_32 = 225,
		icoScene12_32 = 226,
		icoScene13_32 = 227,
		icoScene14_32 = 228,
		icoScene15_32 = 229,
		icoScene16_32 = 230,
		icoScene17_32 = 231,
		icoScene18_32 = 232,
		icoScene19_32 = 233,
		icoScene20_32 = 234,
		icoWindowStop = 235,
    	btnStudy = 236;
																		
    // resource location
    public static final String[] resourceLocations = {
    	ICON_PATH_PREFIX + "/home.ico",
        IMAGE_PATH_PREFIX + "/closedown.gif",
		IMAGE_PATH_PREFIX + "/closehover.gif",
		IMAGE_PATH_PREFIX + "/closenormal.gif",
		IMAGE_PATH_PREFIX + "/mindown.gif",
		IMAGE_PATH_PREFIX + "/minhover.gif",
		IMAGE_PATH_PREFIX + "/minnormal.gif",
		IMAGE_PATH_PREFIX + "/maxdown.gif",
		IMAGE_PATH_PREFIX + "/maxhover.gif",
		IMAGE_PATH_PREFIX + "/maxnormal.gif",
		IMAGE_PATH_PREFIX + "/restoredown.gif",
		IMAGE_PATH_PREFIX + "/restorehover.gif",
		IMAGE_PATH_PREFIX + "/restorenormal.gif",
		IMAGE_PATH_PREFIX + "/bg.png",
		IMAGE_PATH_PREFIX + "/bg_login.png",
		IMAGE_PATH_PREFIX + "/bg_loading.png",
		IMAGE_PATH_PREFIX + "/btn_login.png",
		IMAGE_PATH_PREFIX + "/btn_login_up.png",
		IMAGE_PATH_PREFIX + "/btn_login_on.png",
		IMAGE_PATH_PREFIX + "/checkbox.png",
		IMAGE_PATH_PREFIX + "/checkbox_on.png",
		IMAGE_PATH_PREFIX + "/bg_loading_ring.png",
		IMAGE_PATH_PREFIX + "/bg_main.png",
		IMAGE_PATH_PREFIX + "/icon_m1.png",
		IMAGE_PATH_PREFIX + "/icon_m2.png",
		IMAGE_PATH_PREFIX + "/icon_m3.png",
		IMAGE_PATH_PREFIX + "/icon_m4.png",
		IMAGE_PATH_PREFIX + "/icon_m5.png",
		IMAGE_PATH_PREFIX + "/icon_m6.png",
		IMAGE_PATH_PREFIX + "/icon_m7.png",
		IMAGE_PATH_PREFIX + "/icon_m8.png",
		IMAGE_PATH_PREFIX + "/icon_m1_on.png",
		IMAGE_PATH_PREFIX + "/icon_m2_on.png",
		IMAGE_PATH_PREFIX + "/icon_m3_on.png",
		IMAGE_PATH_PREFIX + "/icon_m4_on.png",
		IMAGE_PATH_PREFIX + "/icon_m5_on.png",
		IMAGE_PATH_PREFIX + "/icon_m6_on.png",
		IMAGE_PATH_PREFIX + "/icon_m7_on.png",
		IMAGE_PATH_PREFIX + "/icon_m8_on.png",
		IMAGE_PATH_PREFIX + "/exit.gif",
		IMAGE_PATH_PREFIX + "/about.gif",
		IMAGE_PATH_PREFIX + "/sysoption.gif",
		IMAGE_PATH_PREFIX + "/changeuser.gif",
		IMAGE_PATH_PREFIX + "/update.gif",
		IMAGE_PATH_PREFIX + "/firefox.gif",
		IMAGE_PATH_PREFIX + "/language.gif",
		IMAGE_PATH_PREFIX + "/bg_sub.png",
		IMAGE_PATH_PREFIX + "/btn_submenu_on.png",
		IMAGE_PATH_PREFIX + "/btn_submenu_off.png",
		IMAGE_PATH_PREFIX + "/btn_back.png",
		IMAGE_PATH_PREFIX + "/btn_back_on.png",
		IMAGE_PATH_PREFIX + "/btn_normal.png",
		IMAGE_PATH_PREFIX + "/btn_normal_on.png",
		IMAGE_PATH_PREFIX + "/btn_floor.png",
		IMAGE_PATH_PREFIX + "/btn_floor_left.png",
		IMAGE_PATH_PREFIX + "/btn_floor_right.png",
		IMAGE_PATH_PREFIX + "/ico_sx.png",
		IMAGE_PATH_PREFIX + "/ico_lt.png",
		IMAGE_PATH_PREFIX + "/ico_mt.png",
		IMAGE_PATH_PREFIX + "/ico_kt.png",
		IMAGE_PATH_PREFIX + "/ico_cl.png",
		IMAGE_PATH_PREFIX + "/ico_mb.png",
		IMAGE_PATH_PREFIX + "/ico_xd.png",
		IMAGE_PATH_PREFIX + "/ico_ot.png",
		IMAGE_PATH_PREFIX + "/ico_sx_on.png",
		IMAGE_PATH_PREFIX + "/ico_lt_on.png",
		IMAGE_PATH_PREFIX + "/ico_mt_on.png",
		IMAGE_PATH_PREFIX + "/ico_kt_on.png",
		IMAGE_PATH_PREFIX + "/ico_cl_on.png",
		IMAGE_PATH_PREFIX + "/ico_mb_on.png",
		IMAGE_PATH_PREFIX + "/ico_xd_on.png",
		IMAGE_PATH_PREFIX + "/ico_ot_on.png",
		IMAGE_PATH_PREFIX + "/btn_left.png",
		IMAGE_PATH_PREFIX + "/btn_right.png",
		IMAGE_PATH_PREFIX + "/bg_room.png",
		IMAGE_PATH_PREFIX + "/btn_light.png",
		IMAGE_PATH_PREFIX + "/bg_light_adjust.png",
		IMAGE_PATH_PREFIX + "/ico_light_on.png",
		IMAGE_PATH_PREFIX + "/ico_light_off.png",
		IMAGE_PATH_PREFIX + "/ico_wed.png",
		IMAGE_PATH_PREFIX + "/ico_temp.png",
		IMAGE_PATH_PREFIX + "/bg_air_adjust.png",
		IMAGE_PATH_PREFIX + "/btn_adjust.png",
		IMAGE_PATH_PREFIX + "/ico_con1.png",
		IMAGE_PATH_PREFIX + "/ico_con2.png",
		IMAGE_PATH_PREFIX + "/ico_con3.png",
		IMAGE_PATH_PREFIX + "/ico_con4.png",
		IMAGE_PATH_PREFIX + "/ico_con5.png",
		IMAGE_PATH_PREFIX + "/ico_con6.png",
		IMAGE_PATH_PREFIX + "/ico_con7.png",
		IMAGE_PATH_PREFIX + "/ico_con8.png",
		IMAGE_PATH_PREFIX + "/ico_con9.png",
		IMAGE_PATH_PREFIX + "/ico_con10.png",
		IMAGE_PATH_PREFIX + "/btn_con.png",
		IMAGE_PATH_PREFIX + "/btn_big.png",
		IMAGE_PATH_PREFIX + "/ico_screen.png",
		IMAGE_PATH_PREFIX + "/ico_screen_close.png",
		IMAGE_PATH_PREFIX + "/btn_media.png",
		IMAGE_PATH_PREFIX + "/btn_media1.png",
		IMAGE_PATH_PREFIX + "/btn_media2.png",		
		IMAGE_PATH_PREFIX + "/btn_media3.png",	
		IMAGE_PATH_PREFIX + "/ico_media1.png",
		IMAGE_PATH_PREFIX + "/ico_media2.png",
		IMAGE_PATH_PREFIX + "/ico_media3.png",
		IMAGE_PATH_PREFIX + "/ico_media4.png",		
		IMAGE_PATH_PREFIX + "/ico_media5.png",	
		IMAGE_PATH_PREFIX + "/ico_media6.png",		
		IMAGE_PATH_PREFIX + "/ico_media7.png",		
		IMAGE_PATH_PREFIX + "/ico_media8.png",	
		IMAGE_PATH_PREFIX + "/ico_media9.png",
		IMAGE_PATH_PREFIX + "/ico_up.png",	
		IMAGE_PATH_PREFIX + "/ico_down.png",	
		IMAGE_PATH_PREFIX + "/ico_left.png",	
		IMAGE_PATH_PREFIX + "/ico_right.png",
		IMAGE_PATH_PREFIX + "/ico_window.png",
		IMAGE_PATH_PREFIX + "/ico_window_close.png",
		IMAGE_PATH_PREFIX + "/btn_safe1.png",
		IMAGE_PATH_PREFIX + "/btn_safe2.png",
		IMAGE_PATH_PREFIX + "/btn_safe3.png",
		IMAGE_PATH_PREFIX + "/btn_safe4.png",
		IMAGE_PATH_PREFIX + "/btn_safe1_on.png",
		IMAGE_PATH_PREFIX + "/btn_safe2_on.png",
		IMAGE_PATH_PREFIX + "/btn_safe3_on.png",
		IMAGE_PATH_PREFIX + "/btn_safe4_on.png",
		IMAGE_PATH_PREFIX + "/btn_other.png",
		IMAGE_PATH_PREFIX + "/btn_other_on.png",
		IMAGE_PATH_PREFIX + "/btn_on.png",
		IMAGE_PATH_PREFIX + "/btn_off.png",
		IMAGE_PATH_PREFIX + "/c.png",
		IMAGE_PATH_PREFIX + "/btn_con_on.png",
		IMAGE_PATH_PREFIX + "/btn_big_on.png",
		IMAGE_PATH_PREFIX + "/btn_media_on.png",
		IMAGE_PATH_PREFIX + "/btn_media1_on.png",
		IMAGE_PATH_PREFIX + "/btn_media2_on.png",
		IMAGE_PATH_PREFIX + "/ico_media1_on.png",
		IMAGE_PATH_PREFIX + "/ico_media2_on.png",
		IMAGE_PATH_PREFIX + "/ico_media3_on.png",
		IMAGE_PATH_PREFIX + "/ico_media4_on.png",		
		IMAGE_PATH_PREFIX + "/ico_media5_on.png",	
		IMAGE_PATH_PREFIX + "/ico_media6_on.png",		
		IMAGE_PATH_PREFIX + "/ico_media7_on.png",		
		IMAGE_PATH_PREFIX + "/ico_media8_on.png",
		IMAGE_PATH_PREFIX + "/ico_media9_on.png",
		IMAGE_PATH_PREFIX + "/btn_big3.png",
		IMAGE_PATH_PREFIX + "/btn_big3_on.png",
		IMAGE_PATH_PREFIX + "/ico_scene1.png",
		IMAGE_PATH_PREFIX + "/ico_scene2.png",
		IMAGE_PATH_PREFIX + "/ico_scene3.png",
		IMAGE_PATH_PREFIX + "/ico_scene4.png",
		IMAGE_PATH_PREFIX + "/ico_scene5.png",
		IMAGE_PATH_PREFIX + "/ico_scene6.png",
		IMAGE_PATH_PREFIX + "/ico_scene7.png",
		IMAGE_PATH_PREFIX + "/ico_scene8.png",
		IMAGE_PATH_PREFIX + "/ico_scene1_on.png",
		IMAGE_PATH_PREFIX + "/ico_scene2_on.png",
		IMAGE_PATH_PREFIX + "/ico_scene3_on.png",
		IMAGE_PATH_PREFIX + "/ico_scene4_on.png",
		IMAGE_PATH_PREFIX + "/ico_scene5_on.png",
		IMAGE_PATH_PREFIX + "/ico_scene6_on.png",
		IMAGE_PATH_PREFIX + "/ico_scene7_on.png",
		IMAGE_PATH_PREFIX + "/ico_scene8_on.png",
		IMAGE_PATH_PREFIX + "/ico_sim2.png",
		IMAGE_PATH_PREFIX + "/ico_sim1.png",
		IMAGE_PATH_PREFIX + "/8switch_on.png",
		IMAGE_PATH_PREFIX + "/8switch_off.png",
		IMAGE_PATH_PREFIX + "/ico_signa1.png",
		IMAGE_PATH_PREFIX + "/ico_signa2.png",
		IMAGE_PATH_PREFIX + "/ico_signa3.png",
		IMAGE_PATH_PREFIX + "/ico_signa4.png",
		IMAGE_PATH_PREFIX + "/ico_signa5.png",
		IMAGE_PATH_PREFIX + "/ico_loading.png",
		IMAGE_PATH_PREFIX + "/ico_scene1_32.png",
		IMAGE_PATH_PREFIX + "/ico_scene2_32.png",
		IMAGE_PATH_PREFIX + "/ico_scene3_32.png",
		IMAGE_PATH_PREFIX + "/ico_scene4_32.png",
		IMAGE_PATH_PREFIX + "/ico_scene5_32.png",
		IMAGE_PATH_PREFIX + "/ico_scene6_32.png",
		IMAGE_PATH_PREFIX + "/ico_scene7_32.png",
		IMAGE_PATH_PREFIX + "/ico_scene8_32.png",
		IMAGE_PATH_PREFIX + "/8switch_1.png",
		IMAGE_PATH_PREFIX + "/8switch_2.png",
		IMAGE_PATH_PREFIX + "/8switch_3.png",
		IMAGE_PATH_PREFIX + "/btn_clear.png",
		IMAGE_PATH_PREFIX + "/btn_clear_up.png",
		IMAGE_PATH_PREFIX + "/btn_clear_on.png",
		IMAGE_PATH_PREFIX + "/bt_pause.png",
		IMAGE_PATH_PREFIX + "/user.png",
		IMAGE_PATH_PREFIX + "/users.png",
		IMAGE_PATH_PREFIX + "/folder.png",
		IMAGE_PATH_PREFIX + "/folder_downloads.png",
		IMAGE_PATH_PREFIX + "/folder_upload.png",
		IMAGE_PATH_PREFIX + "/info.png",
		IMAGE_PATH_PREFIX + "/apply.png",
		IMAGE_PATH_PREFIX + "/history.png",
		IMAGE_PATH_PREFIX + "/file_temp.png",
		IMAGE_PATH_PREFIX + "/app_preferences.png",
		IMAGE_PATH_PREFIX + "/app_large_icons.png",
		IMAGE_PATH_PREFIX + "/new.png",
		IMAGE_PATH_PREFIX + "/delete.png",
		IMAGE_PATH_PREFIX + "/ico_scene9.png",
		IMAGE_PATH_PREFIX + "/ico_scene10.png",
		IMAGE_PATH_PREFIX + "/ico_scene11.png",
		IMAGE_PATH_PREFIX + "/ico_scene12.png",
		IMAGE_PATH_PREFIX + "/ico_scene13.png",
		IMAGE_PATH_PREFIX + "/ico_scene14.png",
		IMAGE_PATH_PREFIX + "/ico_scene15.png",
		IMAGE_PATH_PREFIX + "/ico_scene16.png",
		IMAGE_PATH_PREFIX + "/ico_scene17.png",
		IMAGE_PATH_PREFIX + "/ico_scene18.png",
		IMAGE_PATH_PREFIX + "/ico_scene19.png",
		IMAGE_PATH_PREFIX + "/ico_scene20.png",
		IMAGE_PATH_PREFIX + "/ico_scene9_on.png",
		IMAGE_PATH_PREFIX + "/ico_scene10_on.png",
		IMAGE_PATH_PREFIX + "/ico_scene11_on.png",
		IMAGE_PATH_PREFIX + "/ico_scene12_on.png",
		IMAGE_PATH_PREFIX + "/ico_scene13_on.png",
		IMAGE_PATH_PREFIX + "/ico_scene14_on.png",
		IMAGE_PATH_PREFIX + "/ico_scene15_on.png",
		IMAGE_PATH_PREFIX + "/ico_scene16_on.png",
		IMAGE_PATH_PREFIX + "/ico_scene17_on.png",
		IMAGE_PATH_PREFIX + "/ico_scene18_on.png",
		IMAGE_PATH_PREFIX + "/ico_scene19_on.png",
		IMAGE_PATH_PREFIX + "/ico_scene20_on.png",
		IMAGE_PATH_PREFIX + "/ico_scene9_32.png",
		IMAGE_PATH_PREFIX + "/ico_scene10_32.png",
		IMAGE_PATH_PREFIX + "/ico_scene11_32.png",
		IMAGE_PATH_PREFIX + "/ico_scene12_32.png",
		IMAGE_PATH_PREFIX + "/ico_scene13_32.png",
		IMAGE_PATH_PREFIX + "/ico_scene14_32.png",
		IMAGE_PATH_PREFIX + "/ico_scene15_32.png",
		IMAGE_PATH_PREFIX + "/ico_scene16_32.png",
		IMAGE_PATH_PREFIX + "/ico_scene17_32.png",
		IMAGE_PATH_PREFIX + "/ico_scene18_32.png",
		IMAGE_PATH_PREFIX + "/ico_scene19_32.png",
		IMAGE_PATH_PREFIX + "/ico_scene20_32.png",
		IMAGE_PATH_PREFIX + "/ico_window_stop.png",
		IMAGE_PATH_PREFIX + "/btn_study.png"
    };
    
    // 单一实例
    private static IconHolder holder = new IconHolder();
    
    // 注册
    private ImageRegistry registry;
    
    /**
     * 私有构造，singleton模式
     */
    private IconHolder() {
        // 创建图像注册�?
        registry = new ImageRegistry(Display.getCurrent());
    }

    /**
     * @return 实例
     */
    public static IconHolder getInstance() {
    	return holder;
    }
    
    /**
     * 得到某个图标的灰度图标
     * 
     * @param resId
     * @return
     */
    public Image getGrayImage(int resId) {
    	// 尝试在registry中查询
    	String path = resourceLocations[resId] + ".offline";
    	Image img = registry.get(path);
    	// 尝试得到非灰度图�?
    	if(img == null) {
    		img = getImage(resourceLocations[resId]);
    		if(img == null)
    			return null;
    		
    		// 生成灰度图像
        	img = new Image(Display.getCurrent(), img, SWT.IMAGE_GRAY);
        	registry.put(path, img);
    	}
    	
    	return img;
    }
    
    /**
     * 得到某个图标的灰度图标
     * @param s
     * @return
     */
    public Image getGrayImageByName(String s) {
    	String path = IMAGE_PATH_PREFIX + "/" + s + ".offline";
    	
    	Image img = registry.get(path);
    	// 尝试得到非灰度图�?
    	if(img == null) {
    		img = getImageByName(s);
    		if(img == null)
    			return null;
    		
    		// 生成灰度图像
        	img = new Image(Display.getCurrent(), img, SWT.IMAGE_GRAY);
        	registry.put(path, img);
    	}
    	
    	return img;
    	
    }
    
    public Image getImage24(int resId){
    	// 尝试在registry中查询
    	String path = resourceLocations[resId] + ".24";
    	Image img = registry.get(path);
    	// 尝试得到非灰度图�?
    	if(img == null) {
    		img = getImage(resourceLocations[resId]);
    		if(img == null)
    			return null;
    		// 生成24 * 24图像
        	ImageData imageData = img.getImageData();
        	ImageData imageData2 = imageData.scaledTo(24, 24);
        	Image image = new Image(Display.getCurrent(), imageData2);
        	registry.put(path, image);
        	return image;
    	}
    	return img;
    }
    
    /**
     * @param imageId 图标id
     * @return 图标
     */
    public Image getImage(int resId) {
    	if(resId < 0 || resId >= resourceLocations.length) return null;
    	return getImage(resourceLocations[resId]);
    } 
    
    public Image getImageByName(String s){
    	String path = IMAGE_PATH_PREFIX + "/" + s;
    	return getImage(path);
    }
    
    /**
     * 得到程序图标
     * @param s 资源路径
     * @return Image
     */
    private Image getImage(String s) {
    	Image ret = registry.get(s);
    	if(ret == null) {
    		ret = createImageFromJar(s);
    		if(ret != null)
    			registry.put(s, ret);
    		return ret;
    	} else
    		return ret;
    }
    
    /**
     * @param imageId 背景图片Id
     * @return 图标
     */
    public Image getBackgroundImage(int resId) {
    	if(resId < 0 || resId >= resourceLocations.length) return null;
    	return getBackgroundImage(resourceLocations[resId]);
    } 
    
    /**
     * 得到背景图片
     * @param s 资源路径
     * @return Image
     */
    private Image getBackgroundImage(String s) {
    	Image ret = registry.get(s);
    	if(ret == null) {
    		ret = createBackgroudImageFromJar(s);
    		if(ret != null)
    			registry.put(s, ret);
    		return ret;
    	} else
    		return ret;
    }
    
    /**
     * 得到图像描述
     * 
     * @param resId
     * 		资源ID
     * @return
     * 		ImageDescriptor
     */
    public ImageDescriptor getImageDescriptor(int resId) {
        ImageDescriptor ret = registry.getDescriptor(resourceLocations[resId]);
        if(ret == null) {
            ret = ImageDescriptor.createFromFile(KC868.class, resourceLocations[resId]);
            if(ret != null)
                registry.put(resourceLocations[resId], ret);
            return ret;
        } else
            return ret;
    }
    
    /**
     * 从文件创建Image对象
     * 
     * @param path
     *            the relative path to the icon 
     */
    private Image createImageFromJar(String path) {
        return createImageFromStream(IconHolder.class.getResourceAsStream(path));
    }
    
    /**
     * 从文件创建Image对象
     * 
     * @param path
     *            the relative path to the icon 
     */
    private Image createBackgroudImageFromJar(String path) {
        return createBackgroudImageFromStream(IconHolder.class.getResourceAsStream(path));
    }
    
    
    /**
     * 从一个外部文件中创建�?个图�?
     * 
     * @param path
     * @return
     */
    private Image createImageFromFile(String path) {
        try {
            return createImageFromStream(new FileInputStream(path));
        } catch (FileNotFoundException e) {
            return null;
        }
    }
    
    /**
     * 从一个输入流中创建一个图片
     * 
     * @param is
     * @return
     */
    private Image createImageFromStream(InputStream is) {
        try {
        	if (is != null) {
                ImageData imageData = new ImageData(is);
                if (imageData != null) {
                	if(imageData.transparentPixel > 0){
                		return new Image(Display.getCurrent(), imageData, imageData.getTransparencyMask());
                	}
                	return new Image(Display.getCurrent(), imageData);
                }
            }
        } catch (Exception e) {
        	logger.error(e.getMessage());
        } finally {
            try {
                if(is != null)
                    is.close();
            } catch (IOException e1) {
            }
        }
        return null;
    }
    
    /**
     * 从一个输入流中创建一个图片
     * 
     * @param is
     * @return
     */
    private Image createBackgroudImageFromStream(InputStream is) {
        try {
        	if (is != null) {
                ImageData imageData = new ImageData(is);
                if (imageData != null) {
                	if(imageData.transparentPixel > 0){
                		return new Image(Display.getCurrent(), imageData, imageData.getTransparencyMask());
                	}
                	return new Image(Display.getCurrent(), imageData);
                }
            }
        } catch (Exception e) {
        	logger.error(e.getMessage());
        } finally {
            try {
                if(is != null)
                    is.close();
            } catch (IOException e1) {
            }
        }
        return null;
    }
    
    public void dispose(){
    	registry.dispose();
    }
}
