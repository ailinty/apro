package com.joeysoft.kc868.db.util;

import static com.joeysoft.kc868.resource.Messages.cl_off;
import static com.joeysoft.kc868.resource.Messages.cl_stop;
import static com.joeysoft.kc868.resource.Messages.cl_on;
import static com.joeysoft.kc868.resource.Messages.kt_con1;
import static com.joeysoft.kc868.resource.Messages.kt_con10;
import static com.joeysoft.kc868.resource.Messages.kt_con2;
import static com.joeysoft.kc868.resource.Messages.kt_con3;
import static com.joeysoft.kc868.resource.Messages.kt_con4;
import static com.joeysoft.kc868.resource.Messages.kt_con5;
import static com.joeysoft.kc868.resource.Messages.kt_con6;
import static com.joeysoft.kc868.resource.Messages.kt_con7;
import static com.joeysoft.kc868.resource.Messages.kt_con8;
import static com.joeysoft.kc868.resource.Messages.kt_con9;
import static com.joeysoft.kc868.resource.Messages.mb_off;
import static com.joeysoft.kc868.resource.Messages.mb_on;
import static com.joeysoft.kc868.resource.Messages.mt_av;
import static com.joeysoft.kc868.resource.Messages.mt_back;
import static com.joeysoft.kc868.resource.Messages.mt_menu;
import static com.joeysoft.kc868.resource.Messages.mt_ok;

public class MessageUtil {
	
	/**
	 * 按类型按键名称
	 * @param type
	 * @return
	 */
	public static String getMessage(String type){
		if(DictConst.DEVICE_TYPE_KT_CON1.equals(type)){
			return kt_con1;
		}else if (DictConst.DEVICE_TYPE_KT_CON2.equals(type)){
			return kt_con2;
		}else if(DictConst.DEVICE_TYPE_KT_CON3.equals(type)){
			return kt_con3;
		}else if(DictConst.DEVICE_TYPE_KT_CON4.equals(type)){
			return kt_con4;
		}else if(DictConst.DEVICE_TYPE_KT_CON5.equals(type)){
			return kt_con5;
		}else if(DictConst.DEVICE_TYPE_KT_CON6.equals(type)){
			return kt_con6;
		}else if(DictConst.DEVICE_TYPE_KT_CON7.equals(type)){
			return kt_con7;
		}else if(DictConst.DEVICE_TYPE_KT_CON8.equals(type)){
			return kt_con8;
		}else if(DictConst.DEVICE_TYPE_KT_CON9.equals(type)){
			return kt_con9;
		}else if(DictConst.DEVICE_TYPE_KT_CON10.equals(type)){
			return kt_con10;
		}else if(DictConst.DEVICE_TYPE_KT_THUMB1.equals(type)){
			return "1级";
		}else if(DictConst.DEVICE_TYPE_KT_THUMB2.equals(type)){
			return "2级";
		}else if(DictConst.DEVICE_TYPE_KT_THUMB3.equals(type)){
			return "3级";
		}else if(DictConst.DEVICE_TYPE_KT_THUMB4.equals(type)){
			return "4级";
		}else if(DictConst.DEVICE_TYPE_KT_THUMB5.equals(type)){
			return "5级";
		}else if(DictConst.DEVICE_TYPE_KT_THUMB6.equals(type)){
			return "6级";
		}else if(DictConst.DEVICE_TYPE_KT_THUMB7.equals(type)){
			return "7级";
		}else if(DictConst.DEVICE_TYPE_KT_THUMB8.equals(type)){
			return "8级";
		}else if(DictConst.DEVICE_TYPE_KT_THUMB9.equals(type)){
			return "9级";
		}else if(DictConst.DEVICE_TYPE_KT_THUMB10.equals(type)){
			return "10级";
		}else if(DictConst.DEVICE_TYPE_KT_THUMB11.equals(type)){
			return "11级";
		}else if(DictConst.DEVICE_TYPE_KT_THUMB12.equals(type)){
			return "12级";
		}else if(DictConst.DEVICE_TYPE_KT_THUMB13.equals(type)){
			return "13级";
		}else if(DictConst.DEVICE_TYPE_KT_THUMB14.equals(type)){
			return "14级";
		}else if(DictConst.DEVICE_TYPE_MB_ON.equals(type)){
			return mb_on;
		}else if (DictConst.DEVICE_TYPE_MB_OFF.equals(type)){
			return mb_off;
		}else if(DictConst.DEVICE_TYPE_CL_ON.equals(type)){
			return cl_on;
		}else if (DictConst.DEVICE_TYPE_CL_OFF.equals(type)){
			return cl_off;
		}else if (DictConst.DEVICE_TYPE_CL_STOP.equals(type)){
			return cl_stop;
		}else if(DictConst.DEVICE_TYPE_PD_ON.equals(type)){
			return "打开";
		}else if (DictConst.DEVICE_TYPE_PD_OFF.equals(type)){
			return "关闭";
		}else if (DictConst.DEVICE_TYPE_TD_THUMB1.equals(type)){
			return "1级";
		}else if (DictConst.DEVICE_TYPE_TD_THUMB2.equals(type)){
			return "2级";
		}else if (DictConst.DEVICE_TYPE_TD_THUMB3.equals(type)){
			return "3级";
		}else if (DictConst.DEVICE_TYPE_TD_THUMB4.equals(type)){
			return "4级";
		}else if (DictConst.DEVICE_TYPE_TD_THUMB5.equals(type)){
			return "5级";
		}else if(DictConst.DEVICE_TYPE_MT_MENU.equals(type)){
			return mt_menu;
		}else if (DictConst.DEVICE_TYPE_MT_AV.equals(type)){
			return mt_av;
		}else if (DictConst.DEVICE_TYPE_MT_BACK.equals(type)){
			return mt_back;
		}else if (DictConst.DEVICE_TYPE_MT_ON.equals(type)){
			return "打开";
		}else if (DictConst.DEVICE_TYPE_MT_OFF.equals(type)){
			return "关闭";
		}else if (DictConst.DEVICE_TYPE_MT_VOL_X.equals(type)){
			return "静音";
		}else if (DictConst.DEVICE_TYPE_MT_VOL_U.equals(type)){
			return "调大";
		}else if (DictConst.DEVICE_TYPE_MT_VOL_D.equals(type)){
			return "调小";
		}else if (DictConst.DEVICE_TYPE_MT_STOP.equals(type)){
			return "暂停";
		}else if (DictConst.DEVICE_TYPE_MT_PLAY.equals(type)){
			return "播放";
		}else if (DictConst.DEVICE_TYPE_MT_BACK_UP.equals(type)){
			return "后退";
		}else if (DictConst.DEVICE_TYPE_MT_FORWARD.equals(type)){
			return "前进";
		}else if (DictConst.DEVICE_TYPE_MT_PREVIOUS.equals(type)){
			return "上一个";
		}else if (DictConst.DEVICE_TYPE_MT_NEXT.equals(type)){
			return "下一个";
		}else if (DictConst.DEVICE_TYPE_MT_UP.equals(type)){
			return "向上";
		}else if (DictConst.DEVICE_TYPE_MT_DOWN.equals(type)){
			return "向下";
		}else if (DictConst.DEVICE_TYPE_MT_LEFT.equals(type)){
			return "向左";
		}else if (DictConst.DEVICE_TYPE_MT_RIGHT.equals(type)){
			return "向右";
		}else if (DictConst.DEVICE_TYPE_MT_OK.equals(type)){
			return "确定";
		}
		return "";
	}
	
	public static String getMediaMessage(String type){
		if(DictConst.DEVICE_TYPE_MT_MENU.equals(type)){
			return mt_menu;
		}else if (DictConst.DEVICE_TYPE_MT_AV.equals(type)){
			return mt_av;
		}else if (DictConst.DEVICE_TYPE_MT_BACK.equals(type)){
			return mt_back;
		}
		return "";
	}
}
