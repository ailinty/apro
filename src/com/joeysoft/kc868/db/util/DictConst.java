package com.joeysoft.kc868.db.util;

public class DictConst {
	public final static String KC868A = "KC868-A";
	public final static String KC868B = "KC868-B";
	public final static String KC868C = "KC868-C";
	public final static String KC868D = "KC868-D";
	
	public final static int ADMIN_USER = 0;
	public final static int NORMAL_USER = 1;
	
	public final static int ALARM_COUT = 8;
	
	// 红外转发器学习命令数据码
	public final static int TRANSFER_STUDY_DATACODE = 0001;
	
	public final static int PAGE_OUT = 1;
	public final static int PAGE_IN = 2;
	
	// 楼层
	public final static int MENU_FLOOR = 1;
	// 房间
	public final static int MENU_ROOM = 2;
    // 红外转发器
	public final static int MENU_TRANSFER = 3;
	// 常规设备
	public final static int MENU_DEVICE = 4;
    // 无线输出
	public final static int MENU_SENSOROUT = 5;
	// GSM
	public final static int MENU_TEL_OUT = 6;
	// GSM
	public final static int MENU_SMS_OUT = 7;
	// 继电器按钮
	public final static int MENU_RELAY = 8;
	
	// 无线输入
	public final static int MENU_SENSORIN = 1;
	// 有线输入
	public final static int MENU_LINEATE = 2;
	// 温湿度传感器
	public final static int MENU_TEMP_SENSOR = 3;
	public final static int MENU_TEL_IN = 4;
	public final static int MENU_SMS_IN = 5;
	
	// 继电器状态
	public final static String RELAY_STATUS = "RELAY_STATUS";
	// 是否
	public final static String YES_NO = "YES_NO";
	// 频率类型
	public final static String FREQ_TYPE = "FREQ_TYPE";
	// 编码类型
	public final static String CODE_TYPE = "CODE_TYPE";
	// 电阻类型
	public final static String RES_TYPE = "RES_TYPE";
	// 电阻类型2
	public final static String RES_TYPE2 = "RES_TYPE2";
	//设备类型
	public final static String DEVICE_TYPE = "DEVICE_TYPE";
	// 有线电信号模式
	public final static String LINEATE_TYPE = "LINEATE_TYPE";
	// 有线电信号模式
	public final static String LINEATE_UD = "LINEATE_UD";
	public final static String LINEATE_GL = "LINEATE_GL";
	
	// 开关量
	public final static String LINEATE_TYPE_UD = "1";
	// 模拟量
	public final static String LINEATE_TYPE_GL = "0";
	
	// 上升沿
	public final static String LINEATE_UD_U = "1";
	// 下升沿
	public final static String LINEATE_UD_D = "0";
	
	// 大于
	public final static String LINEATE_GL_G = "1";
	// 小于
	public final static String LINEATE_GL_L = "0";
	// 无线输出（非常规设备）
	public final static String DEVICE_TYPE_QT = "QT";
	// 继电器
	public final static String DEVICE_TYPE_XD = "XD";
	//电器设备类型
	
	// 普通灯
	public final static String DEVICE_TYPE_PD = "PD";
	// 调光灯
	public final static String DEVICE_TYPE_TD = "TD";
	// 空调
	public final static String DEVICE_TYPE_KT = "KT";
	// 摄像头
	public final static String DEVICE_TYPE_SX = "SX";	
	// 窗帘
	public final static String DEVICE_TYPE_CL = "CL";
	// 幕布
	public final static String DEVICE_TYPE_MB = "MB";
	// 多媒体
	public final static String DEVICE_TYPE_MT = "MT";
	// 普通灯，两按键
	public final static String DEVICE_TYPE_PD_ON = "PD_ON";
	public final static String DEVICE_TYPE_PD_OFF = "PD_OFF";
	// 可调灯，5个滑块
	public final static String DEVICE_TYPE_TD_THUMB1 = "TD_THUMB1";
	public final static String DEVICE_TYPE_TD_THUMB2 = "TD_THUMB2";
	public final static String DEVICE_TYPE_TD_THUMB3 = "TD_THUMB3";
	public final static String DEVICE_TYPE_TD_THUMB4 = "TD_THUMB4";
	public final static String DEVICE_TYPE_TD_THUMB5 = "TD_THUMB5";
	// 空调，10个按键
	public final static String DEVICE_TYPE_KT_CON1 = "KT_CON1";
	public final static String DEVICE_TYPE_KT_CON2 = "KT_CON2";
	public final static String DEVICE_TYPE_KT_CON3 = "KT_CON3";
	public final static String DEVICE_TYPE_KT_CON4 = "KT_CON4";
	public final static String DEVICE_TYPE_KT_CON5 = "KT_CON5";
	public final static String DEVICE_TYPE_KT_CON6 = "KT_CON6";
	public final static String DEVICE_TYPE_KT_CON7 = "KT_CON7";
	public final static String DEVICE_TYPE_KT_CON8 = "KT_CON8";
	public final static String DEVICE_TYPE_KT_CON9 = "KT_CON9";
	public final static String DEVICE_TYPE_KT_CON10 = "KT_CON10";
	
	// 空调总按钮数
	public final static int DEVICE_TYPE_KT_COUNT = 24;
	
	//空调，14个滑块
	public final static int DEVICE_TYPE_KT_THUMB_COUNT = 14;
	
	public final static String DEVICE_TYPE_KT_THUMB1 = "KT_THUMB1";
	public final static String DEVICE_TYPE_KT_THUMB2 = "KT_THUMB2";
	public final static String DEVICE_TYPE_KT_THUMB3 = "KT_THUMB3";
	public final static String DEVICE_TYPE_KT_THUMB4 = "KT_THUMB4";
	public final static String DEVICE_TYPE_KT_THUMB5 = "KT_THUMB5";
	public final static String DEVICE_TYPE_KT_THUMB6 = "KT_THUMB6";
	public final static String DEVICE_TYPE_KT_THUMB7 = "KT_THUMB7";
	public final static String DEVICE_TYPE_KT_THUMB8 = "KT_THUMB8";
	public final static String DEVICE_TYPE_KT_THUMB9 = "KT_THUMB9";
	public final static String DEVICE_TYPE_KT_THUMB10 = "KT_THUMB10";
	public final static String DEVICE_TYPE_KT_THUMB11 = "KT_THUMB11";
	public final static String DEVICE_TYPE_KT_THUMB12 = "KT_THUMB12";
	public final static String DEVICE_TYPE_KT_THUMB13 = "KT_THUMB13";
	public final static String DEVICE_TYPE_KT_THUMB14 = "KT_THUMB14";
	
	// 窗帘，两按键
	public final static String DEVICE_TYPE_CL_ON = "CL_ON";
	public final static String DEVICE_TYPE_CL_OFF = "CL_OFF";
	public final static String DEVICE_TYPE_CL_STOP = "CL_STOP";
	// 幕布，两按键
	public final static String DEVICE_TYPE_MB_ON = "MB_ON";
	public final static String DEVICE_TYPE_MB_OFF = "MB_OFF";
	
	// 多媒体，30按键
	public final static String DEVICE_TYPE_MT_KEY  = "MT_KEY";
	
	public final static String DEVICE_TYPE_MT_ON = "MT_ON";
	public final static String DEVICE_TYPE_MT_OFF = "MT_OFF";
	public final static String DEVICE_TYPE_MT_VOL_X = "MT_VOL_X";
	public final static String DEVICE_TYPE_MT_VOL_U  = "MT_VOL_U";
	public final static String DEVICE_TYPE_MT_VOL_D  = "MT_VOL_D";
	public final static String DEVICE_TYPE_MT_1 = "MT_1";
	public final static String DEVICE_TYPE_MT_2 = "MT_2";
	public final static String DEVICE_TYPE_MT_3 = "MT_3";
	public final static String DEVICE_TYPE_MT_4 = "MT_4";
	public final static String DEVICE_TYPE_MT_5 = "MT_5";
	public final static String DEVICE_TYPE_MT_6 = "MT_6";
	public final static String DEVICE_TYPE_MT_7 = "MT_7";
	public final static String DEVICE_TYPE_MT_8 = "MT_8";
	public final static String DEVICE_TYPE_MT_9 = "MT_9";
	public final static String DEVICE_TYPE_MT_MENU  = "MT_MENU";
	public final static String DEVICE_TYPE_MT_AV  = "MT_AV";
	public final static String DEVICE_TYPE_MT_BACK = "MT_BACK";
	public final static String DEVICE_TYPE_MT_CHA = "MT_CHA";
	public final static String DEVICE_TYPE_MT_CHP = "MT_CHP";
	public final static String DEVICE_TYPE_MT_STOP = "MT_STOP";
	public final static String DEVICE_TYPE_MT_PLAY = "MT_PLAY";
	public final static String DEVICE_TYPE_MT_BACK_UP = "MT_BACK_UP";
	public final static String DEVICE_TYPE_MT_FORWARD = "MT_FORWARD";
	public final static String DEVICE_TYPE_MT_PREVIOUS = "MT_PREVIOUS";
	public final static String DEVICE_TYPE_MT_NEXT = "MT_NEXT";
	public final static String DEVICE_TYPE_MT_UP = "MT_UP";
	public final static String DEVICE_TYPE_MT_DOWN = "MT_DOWN";
	public final static String DEVICE_TYPE_MT_LEFT = "MT_LEFT";
	public final static String DEVICE_TYPE_MT_RIGHT = "MT_RIGHT";
	public final static String DEVICE_TYPE_MT_OK = "MT_OK";

	// 各表前缀
	public final static String TABLE_PREFIX_2262_315_OUT = "RA";
	public final static String TABLE_PREFIX_2262_433_OUT = "RB";
	public final static String TABLE_PREFIX_1527_315_OUT = "RC";
	public final static String TABLE_PREFIX_1527_433_OUT = "RD";
	public final static String TABLE_PREFIX_SMS_OUT = "SM";
	public final static String TABLE_PREFIX_TEL_OUT = "TL";
	
	public final static String TABLE_PREFIX_2262_315_IN = "SRA";
	public final static String TABLE_PREFIX_2262_433_IN = "SRB";
	public final static String TABLE_PREFIX_1527_315_IN = "SRC";
	public final static String TABLE_PREFIX_1527_433_IN = "SRD";
	
	public final static String TABLE_PREFIX_TEMP_SRO = "SRO";
	public final static String TABLE_PREFIX_TEMP_SRU = "SRU";
	
	public final static String TABLE_PREFIX_TEMP_SHO = "SHO";
	public final static String TABLE_PREFIX_TEMP_SHU = "SHU";
	
	public final static String TABLE_PREFIX_SMS_IN = "SSM";
	public final static String TABLE_PREFIX_TEL_IN = "STL";
	
	public final static String TABLE_PREFIX_ALARM = "SAL";
	
	public final static String TABLE_PREFIX_SPR = "SPR";
	public final static String TABLE_PREFIX_SPF = "SPF";
	public final static String TABLE_PREFIX_SAO = "SAO";
	public final static String TABLE_PREFIX_SAU = "SAU";
}
