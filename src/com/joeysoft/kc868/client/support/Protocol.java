package com.joeysoft.kc868.client.support;

public interface Protocol {
	public static final String MK = "cityclud";
	
	/** 包常量 - 头部长度 , J8583包的原因，本系统不添加ISO8583中的Header*/
	public static final int ISO8583_HEADER_LENGTH = 0;
	
	/** 包常量 - 包起始序列号, 服务端*/
	public static final int SERVER_INIT_SEQUENCE = 1000;
	
	/** 包常量 - 包起始序列号, 客户端*/
	public static final int CLIENT_INIT_SEQUENCE = 10000;
	
	/** 单位: ms */
	public static final long TIMEOUT_SEND = 10000;
	/** 最大重发次数 */
	public static final int MAX_RESEND = 5;
	
	public static final String CHARSET_DEFAULT = "GB2312";
	
	/** 包最大大小 */
	public static final int MAX_PACKET_SIZE = 4096;
	
	public static final int MAX_READ_FILE_PACKET_SIZE = 1024;
	
	/** 密钥长度 */
	public static final int LENGTH_KEY = 8;
	/** 最小数据库版本号 */
	public static final String DB_VERSION_MIN = "000";
	/** 最大数据库版本号 */
	public static final String DB_VERSION_MAX = "999";
	
	/** 类型常量 - 获取密码 */
	public static final char CMD_GET_PASSWORD = 0x0001;
	public static final String MSG_GET_PASSWORD_HOST = "PASSWORD-READ-HOST";
	public static final String MSG_GET_PASSWORD_ADMIN = "PASSWORD-READ-ADMIN";
	public static final String MSG_GET_PASSWORD_REPLY = "PASSWORD-READ-";
	
	/** 类型常量 - 设置密码 */
	public static final char CMD_SET_PASSWORD = 0x0002;
	public static final String MSG_SET_PASSWORD_HOST = "PASSWORD-WRITE_HOST-";
	public static final String MSG_SET_PASSWORD_ADMIN = "PASSWORD-WRITE_ADMIN-";
	public static final String MSG_SET_PASSWORD_REPLY = "PASSWORD-WRITE-OK";
	
	/** 类型常量 - 复位主机 */
	public static final char CMD_RESET_HOST = 0x0003;
	public static final String MSG_RESET_HOST = "OPERATION-RESET-HOST";
	public static final String MSG_RESET_HOST_REPLY = "OPERATION-RESET-OK";
	
	public static final char CMD_RESET_HOST_OVER = 0x1003;
	public static final String MSG_RESET_HOST_OVER_REPLY = "INFORMATION-RESET-OVER";
	
	/** 类型常量 - 读取主机的相关信息:硬件版本，软件版本,主机类型 */
	public static final char CMD_READ_HOST = 0x0004;
	public static final String MSG_READ_HOST = "INFORMATION-READ-HOST";
	public static final String MSG_READ_HOST_REPLY = "INFORMATION-READ-";
	
	/** 类型常量 - 读取主机内部温度（标准DS18b20温度传感器，支持负温,编程注意符号！一位小数点） */
	public static final char CMD_READ_TEMPERATURE = 0x0005;
	public static final String MSG_READ_TEMPERATURE = "INFORMATION-READ-TEMPERATURE";
	public static final String MSG_READ_TEMPERATURE_REPLY = "INFORMATION-TEMPERATURE-";
	
	/** 类型常量 - 读取网络参数 */
	public static final char CMD_READ_IP = 0x0006;
	public static final String MSG_READ_IP = "NET-READ_IP-ALL";
	public static final String MSG_READ_IP_REPLY = "NET-READ_IP-";
	
	/** 类型常量 - 获取数据库版本号 */
	public static final char CMD_GET_VERSION = 0x0007;
	public static final String MSG_GET_VERSION = "GET_VERSION--";
	public static final String MSG_GET_VERSION_REPLY = "GET_VERSION--";
	
	/** 类型常量 - 设置数据库版本号 */
	public static final char CMD_SET_VERSION = 0x0008;
	public static final String MSG_SET_VERSION = "SET_VERSION-";
	public static final String MSG_SET_VERSION_REPLY = "SET_VERSION-OK";
	
	/** 类型常量 - 设置网络参数 */
	public static final char CMD_SET_IP = 0x0009;
	public static final String MSG_SET_IP = "NET-SET_IP-";
	public static final String MSG_SET_IP_REPLY = "NET-SET_IP-OK";
	
	
	/** 类型常量 - 配置信息文件写入主机 */
	public static final String MSG_FILE_WRITE_HEAD = "FILE-WRITE-/";
	
	public static final char CMD_FILE_WRITE_START = 0x0010;
	public static final String MSG_FILE_WRITE_START_REPLY = "FILE-WRITE-START";
	public static final char CMD_FILE_WRITE_OK = 0x0011;
	public static final String MSG_FILE_WRITE_OK_REPLY = "FILE-WRITE-OK";
	public static final char CMD_FILE_WRITE_END = 0x0012;
	public static final String MSG_FILE_WRITE_END_REPLY = "FILE-WRITE-END";
	
	public static final char CMD_FILE_WRITE_ERROR = 0x0013;
	public static final String MSG_FILE_WRITE_ERROR_REPLY = "FILE-WRITE-ERROR";
	
	/** 类型常量 - 配置信息文件从主机读出 */
	public static final String MSG_FILE_READ_HEAD = "FILE-READ-/";
	
	public static final char CMD_FILE_READ_HEAD = 0x0020;
	public static final String MSG_FILE_READ_HEAD_REPLY = "FILE-READ-";
	
	public static final char CMD_FILE_READ = 0x0021;
	public static final String MSG_FILE_READ_START = "FILE-READ-START";
	public static final String MSG_FILE_READ_NEW = "FILE-READ-";
	public static final String MSG_FILE_READ_NEW_REPLY = "SUCCEED-READ-";
	
	public static final char CMD_FILE_READ_END = 0x0023;
	public static final String MSG_FILE_READ_END_REPLY = "FILE-READ-END";
	
	public static final char CMD_FILE_READ_ERROR = 0x0024;
	public static final String MSG_FILE_READ_ERROR_REPLY = "FILE-READ-ERROR";
	   
	/** 类型常量 - 准备还原操作  */
	public static final char CMD_RESTOR_HAND_START= 0x0025;
	public static final String MSG_RESTOR_HAND_START = "RESTOR-HAND-START";
	public static final String MSG_RESTOR_HAND_START_REPLY = "RESTOR-HAND-OK";
	
	/** 类型常量 - 还原/备份操作全部完成  */
	public static final char CMD_RESTOR_HAND_END= 0x0026;
	public static final String MSG_RESTOR_HAND_END = "RESTOR-HAND-END";
	public static final String MSG_RESTOR_HAND_END_REPLY = "RESTOR-HAND-END-OK";
	
	/** 类型常量 - 读文件，文件为空  */
	public static final char CMD_FILE_READ_EMPTY = 0x0027;
	public static final String MSG_FILE_READ_EMPTY_REPLY = "FILE-READ-EMPTY";
	
	/** 类型常量 - 读取主机时间  */
	public static final char CMD_READ_NOW = 0x0030;
	public static final String MSG_READ_NOW = "TIME-READ-NOW";
	public static final String MSG_READ_NOW_REPLY = "TIME-READ-";
	
	/** 类型常量 - 写入主机时间  */
	public static final char CMD_WRITE_TIME = 0x0031;
	public static final String MSG_WRITE_TIME = "TIME-WRITE-";
	public static final String MSG_WRITE_TIME_REPLY = "TIME-WRITE-OK";
	
	/** 类型常量 - 写入定时器  */
	public static final char CMD_ALARM_WRITE = 0x0032;
	public static final String MSG_ALARM_WRITE = "ALARM-WRITE-";
	public static final String MSG_ALARM_WRITE_REPLY = "ALARM-WRITE-OK";
	
	/** 类型常量 - 写入定时器  */
	public static final char CMD_ALARM_READ = 0x0033;
	public static final String MSG_ALARM_READ = "ALARM-READ-";
	public static final String MSG_ALARM_READ_REPLY = "ALARM-READ-";
	
	
	/** 类型常量 - 写入自动重启主机  */
	public static final char CMD_HOST_SET_RESET = 0x0035;
	public static final String MSG_HOST_SET_RESET = "HOST-SET_RESET-";
	public static final String MSG_HOST_SET_RESET_REPLY = "HOST-SET_RESET-OK";
			
	
	/** 类型常量 - 设置无线温湿度传感器参数(目前主机支持最多连接8个温湿度传感器)  */
	public static final char CMD_SET_SLAVER = 0x0040;
	public static final String MSG_SET_SLAVER = "RF1101-SET_SLAVER-";
	public static final String MSG_SET_SLAVER_REPLY = "RF1101-SET_SLAVER-OK";
	
	/** 类型常量 - 删除无线温湿度传感器  */
	public static final char CMD_DEL_SLAVER = 0x0041;
	public static final String MSG_DEL_SLAVER = "RF1101-DEL_SLAVER-";
	public static final String MSG_DEL_SLAVER_REPLY = "RF1101-DEL_SLAVER-OK";
	
	/** 类型常量 - 读取传感器数值  */
	public static final char CMD_RF1101_READ = 0x0042;
	public static final String MSG_RF1101_READ = "RF1101-READ-";
	public static final String MSG_RF1101_READ_REPLY = "RF1101-READ-";
	
	/** 类型常量 - 无线温湿度传感器温度上下限值设定 */
	public static final String MSG_SET_T_LIMIT = "RF1101-SET_T_LIMIT-";
	public static final char CMD_SET_T_LIMIT = 0x0043;
	public static final String MSG_SET_T_LIMIT_OK_REPLY = "RF1101-SET_T_LIMIT-OK";
	public static final String MSG_SET_T_LIMIT_OVERTOP_REPLY = "RF1101-WARNING-OVERTOP";
	public static final String MSG_SET_T_LIMIT_UNDER_REPLY = "RF1101-WARNING-UNDER";
	
	/** 类型常量 - 解除第N个传感器的上下限值  */
	public static final char CMD_RMV_T_LIMIT = 0x0044;
	public static final String MSG_RMV_T_LIMIT = "RF1101-RMV_T_LIMIT-";
	public static final String MSG_RMV_T_LIMIT_REPLY = "RF1101-RMV_T_LIMIT-OK";
	
	/** 类型常量 - 情景模式设定  */
	public static final char CMD_EVENT_WRITE = 0x0045;
	public static final String MSG_EVENT_WRITE = "EVENT-WRITE-";
	public static final String MSG_EVENT_WRITE_REPLY = "EVENT-WRITE-OK";
	
	/** 类型常量 - 无线温湿度传感器温度上下限值设定 */
	public static final String MSG_SET_H_LIMIT = "RF1101-SET_H_LIMIT-";
	public static final char CMD_SET_H_LIMIT = 0x0046;
	public static final String MSG_SET_H_LIMIT_OK_REPLY = "RF1101-SET_H_LIMIT-OK";
	
	/** 类型常量 - 绑定触发源与输出项目  */
	public static final char CMD_EVENT_CONNECT = 0x0050;
	public static final String MSG_EVENT_CONNECT = "EVENT-CONNECT-";
	public static final String MSG_EVENT_CONNECT_REPLY = "EVENT-CONNECT-OK";
	
	/** 类型常量 - 绑定触发源与输出项目  */
	public static final char CMD_EVENT_DISCONNECT = 0x0051;
	public static final String MSG_EVENT_DISCONNECT = "EVENT-DISCONNECT-";
	public static final String MSG_EVENT_DISCONNECT_REPLY = "EVENT-DISCONNECT-OK";
	
	/** 类型常量 - 手动运行  */
	public static final char CMD_EVENT_RUN = 0x0052;
	public static final String MSG_EVENT_RUN = "EVENT-RUN-";
	public static final String MSG_EVENT_RUN_REPLY = "EVENT-RUN-OK";
	
	/** 类型常量 - 设置情景模式中每个动作执行时间延时值  */
	public static final char CMD_EVENT_DELAY = 0x0053;
	public static final String MSG_EVENT_DELAY = "EVENT-DELAY-";
	public static final String MSG_EVENT_DELAY_REPLY = "EVENT-DELAY-OK";
	
	/** 类型常量 - 设置情无线解防，设防  */
	public static final char CMD_EVENT_SET_WARNING = 0x0060;
	public static final String MSG_EVENT_SET_WARNING_ON = "EVENT-SET_WARNING-ON";
	public static final String MSG_EVENT_SET_WARNING_OFF = "EVENT-SET_WARNING-OFF";
	public static final String MSG_EVENT_SET_WARNING_REPLY = "EVENT-SET_WARNING-OK";
	
	/** 类型常量 - 无线电  2262, 315*/
	public static final char CMD_PT2262_315M_WRITE = 0x0070;
	public static final String MSG_PT2262_315M_WRITE = "PT2262_315M-WRITE-";
	public static final String MSG_PT2262_315M_WRITE_REPLY = "PT2262_315M-WRITE-OK";
	
	public static final char CMD_PT2262_315M_SEND = 0x0071;
	public static final String MSG_PT2262_315M_SEND = "PT2262_315M-SEND-";
	public static final String MSG_PT2262_315M_SEND_REPLY = "PT2262_315M-SEND-OK";
	
	public static final char CMD_PT2262_315M_STUDY_OK = 0x0072;
	public static final String MSG_PT2262_315M_STUDY_OK_REPLY = "PT2262_315M-STUDY-";
	
	public static final char CMD_PT2262_315M_STUDY = 0x0073;
	public static final String MSG_PT2262_315M_STUDY = "PT2262_315M-STUDY-";
	public static final String MSG_PT2262_315M_STUDY_REPLY = "PT2262_315M-STUDY-START";
	
	public static final char CMD_PT2262_315M_STUDY_CLOSE = 0x0074;
	public static final String MSG_PT2262_315M_STUDY_CLOSE = "PT2262_315M-STUDY-CLOSE";
	public static final String MSG_PT2262_315M_STUDY_CLOSE_REPLY = "PT2262_315M-STUDY-CLOSE_OK";
	
	public static final char CMD_PT2262_315M_DELETE = 0x0075;
	public static final String MSG_PT2262_315M_DELETE = "PT2262_315M-DELETE-";
	public static final String MSG_PT2262_315M_DELETE_REPLY = "PT2262_315M-DELETE-OK";
	
	public static final char CMD_PT2262_315M_INPUT = 0x0076;
	public static final String MSG_PT2262_315M_INPUT = "PT2262_315M-INPUT-";
	public static final String MSG_PT2262_315M_INPUT_REPLY = "PT2262_315M-INPUT-OK";
	
	/** 类型常量 - 无线电  2262, 433*/
	public static final char CMD_PT2262_433M_WRITE = 0x0080;
	public static final String MSG_PT2262_433M_WRITE = "PT2262_433M-WRITE-";
	public static final String MSG_PT2262_433M_WRITE_REPLY = "PT2262_433M-WRITE-OK";
	
	public static final char CMD_PT2262_433M_SEND = 0x0081;
	public static final String MSG_PT2262_433M_SEND = "PT2262_433M-SEND-";
	public static final String MSG_PT2262_433M_SEND_REPLY = "PT2262_433M-SEND-OK";
	
	public static final char CMD_PT2262_433M_STUDY_OK = 0x0082;
	public static final String MSG_PT2262_433M_STUDY_OK_REPLY = "PT2262_433M-STUDY-";
	
	public static final char CMD_PT2262_433M_STUDY = 0x0083;
	public static final String MSG_PT2262_433M_STUDY = "PT2262_433M-STUDY-";
	public static final String MSG_PT2262_433M_STUDY_REPLY = "PT2262_433M-STUDY-START";
	
	public static final char CMD_PT2262_433M_STUDY_CLOSE = 0x0084;
	public static final String MSG_PT2262_433M_STUDY_CLOSE = "PT2262_433M-STUDY-CLOSE";
	public static final String MSG_PT2262_433M_STUDY_CLOSE_REPLY = "PT2262_433M-STUDY-CLOSE_OK";
	
	public static final char CMD_PT2262_433M_DELETE = 0x0085;
	public static final String MSG_PT2262_433M_DELETE = "PT2262_433M-DELETE-";
	public static final String MSG_PT2262_433M_DELETE_REPLY = "PT2262433M-DELETE-OK";
	
	public static final char CMD_PT2262_433M_INPUT = 0x0086;
	public static final String MSG_PT2262_433M_INPUT = "PT2262_433M-INPUT-";
	public static final String MSG_PT2262_433M_INPUT_REPLY = "PT2262_433M-INPUT-OK";
	
	/** 类型常量 - 无线电  1527, 315*/
	public static final char CMD_EV1527_315M_WRITE = 0x0090;
	public static final String MSG_EV1527_315M_WRITE = "EV1527_315M-WRITE-";
	public static final String MSG_EV1527_315M_WRITE_REPLY = "EV1527_315M-WRITE-OK";
	
	public static final char CMD_EV1527_315M_SEND = 0x0091;
	public static final String MSG_EV1527_315M_SEND = "EV1527_315M-SEND-";
	public static final String MSG_EV1527_315M_SEND_REPLY = "EV1527_315M-SEND-OK";
	
	public static final char CMD_EV1527_315M_STUDY_OK = 0x0092;
	public static final String MSG_EV1527_315M_STUDY_OK_REPLY = "EV1527_315M-STUDY-";
	
	public static final char CMD_EV1527_315M_STUDY = 0x0093;
	public static final String MSG_EV1527_315M_STUDY = "EV1527_315M-STUDY-";
	public static final String MSG_EV1527_315M_STUDY_REPLY = "EV1527_315M-STUDY-START";
	
	public static final char CMD_EV1527_315M_STUDY_CLOSE = 0x0094;
	public static final String MSG_EV1527_315M_STUDY_CLOSE = "EV1527_315M-STUDY-CLOSE";
	public static final String MSG_EV1527_315M_STUDY_CLOSE_REPLY = "EV1527_315M-STUDY-CLOSE_OK";
	
	public static final char CMD_EV1527_315M_DELETE = 0x0095;
	public static final String MSG_EV1527_315M_DELETE = "EV1527_315M-DELETE-";
	public static final String MSG_EV1527_315M_DELETE_REPLY = "EV1527315M-DELETE-OK";
	
	public static final char CMD_EV1527_315M_INPUT = 0x0096;
	public static final String MSG_EV1527_315M_INPUT = "EV1527_315M-INPUT-";
	public static final String MSG_EV1527_315M_INPUT_REPLY = "EV1527_315M-INPUT-OK";
	
	/** 类型常量 - 无线电  1527, 433*/
	public static final char CMD_EV1527_433M_WRITE = 0x0100;
	public static final String MSG_EV1527_433M_WRITE = "EV1527_433M-WRITE-";
	public static final String MSG_EV1527_433M_WRITE_REPLY = "EV1527_433M-WRITE-OK";
	
	public static final char CMD_EV1527_433M_SEND = 0x0101;
	public static final String MSG_EV1527_433M_SEND = "EV1527_433M-SEND-";
	public static final String MSG_EV1527_433M_SEND_REPLY = "EV1527_433M-SEND-OK";
	
	public static final char CMD_EV1527_433M_STUDY_OK = 0x0102;
	public static final String MSG_EV1527_433M_STUDY_OK_REPLY = "EV1527_433M-STUDY-";
	
	public static final char CMD_EV1527_433M_STUDY = 0x0103;
	public static final String MSG_EV1527_433M_STUDY = "EV1527_433M-STUDY-";
	public static final String MSG_EV1527_433M_STUDY_REPLY = "EV1527_433M-STUDY-START";
	
	public static final char CMD_EV1527_433M_STUDY_CLOSE = 0x0104;
	public static final String MSG_EV1527_433M_STUDY_CLOSE = "EV1527_433M-STUDY-CLOSE";
	public static final String MSG_EV1527_433M_STUDY_CLOSE_REPLY = "EV1527_433M-STUDY-CLOSE_OK";
	
	public static final char CMD_EV1527_433M_DELETE = 0x0105;
	public static final String MSG_EV1527_433M_DELETE = "EV1527_433M-DELETE-";
	public static final String MSG_EV1527_433M_DELETE_REPLY = "EV1527433M-DELETE-OK";
	
	public static final char CMD_EV1527_433M_INPUT = 0x0106;
	public static final String MSG_EV1527_433M_INPUT = "EV1527_433M-INPUT-";
	public static final String MSG_EV1527_433M_INPUT_REPLY = "EV1527_433M-INPUT-OK";
	
	/** 类型常量 - 红外控制*/
	public static final char CMD_INFRARED_STUDY = 0x0110;
	public static final String MSG_INFRARED_STUDY = "INFRARED-STUDY-";
	public static final String MSG_INFRARED_STUDY_REPLY = "INFRARED-STUDY-START";
	
	public static final char CMD_INFRARED_STUDY_OVER = 0x0112;
	public static final String MSG_INFRARED_STUDY_OVER_REPLY = "INFRARED-STUDY-OVER";
	
	public static final char CMD_INFRARED_STUDY_TEST = 0x0113;
	public static final String MSG_INFRARED_STUDY_TEST = "INFRARED-STUDY-TEST";
	public static final String MSG_INFRARED_STUDY_TEST_REPLY = "INFRARED-STUDY-TEST_OK";
	
	public static final char CMD_INFRARED_STUDY_CLOSE = 0x0114;
	public static final String MSG_INFRARED_STUDY_CLOSE = "INFRARED-STUDY-CLOSE";
	public static final String MSG_INFRARED_STUDY_CLOSE_REPLY = "INFRARED-STUDY-CLOSE_OK";
	
	public static final char CMD_INFRARED_STUDY_WRITE = 0x0115;
	public static final String MSG_INFRARED_STUDY_WRITE = "INFRARED-STUDY-WRITE";
	public static final String MSG_INFRARED_STUDY_WRITE_REPLY = "INFRARED-STUDY-OK";
	
	public static final char CMD_INFRARED_STUDY_OVERFLOW = 0x0116;
	public static final String MSG_INFRARED_STUDY_OVERFLOW_REPLY = "INFRARED-STUDY-OVERFLOW";
	
	public static final char CMD_INFRARED_SEND = 0x0117;
	public static final String MSG_INFRARED_SEND = "INFRARED-SEND-";
	public static final String MSG_INFRARED_SEND_REPLY = "INFRARED-SEND-OK";
	
	// 有线电
	public static final char CMD_IO_SET_MODE_NUM = 0x0120;
	public static final String MSG_IO_SET_MODE_NUM = "IO-SET_MODE-";
	public static final String MSG_IO_SET_MODE_NUM_REPLY = "IO-SET_MODE-OK";
	
	public static final char CMD_IO_SET_R_F_NUM = 0x0121;
	public static final String MSG_IO_SET_R_F_NUM = "IO-SET_R_F-";
	public static final String MSG_IO_SET_R_F_NUM_REPLY = "IO-SET_R_F-OK";
	
	public static final char CMD_IO_SET_AD = 0x0122;
	public static final String MSG_IO_SET_AD = "IO-SET_AD-";
	public static final String MSG_IO_SET_AD_REPLY = "IO-SET_AD-OK";
	
	
	public static final char CMD_IO_IRQ_HIGH = 0x0123;
	public static final String MSG_IO_IRQ_HIGH_REPLY = "IO-IRQ-HIGH";
	
	public static final char CMD_SPEAK_SET_SPEAK = 0x0129;
	public static final String MSG_SPEAK_SET_SPEAK_ON = "SPEAK-SET_SPEAK-ON";
	public static final String MSG_SPEAK_SET_SPEAK_OFF = "SPEAK-SET_SPEAK-OFF";
	public static final String MSG_SPEAK_SET_SPEAK_REPLY = "SPEAK-SET_SPEAK-OK";
	
	// 继电器
	public static final char CMD_RELAY_SET_RELAY = 0x0130;
	public static final String MSG_RELAY_SET_RELAY = "RELAY-SET_RELAY-";
	public static final String MSG_RELAY_SET_RELAY_REPLY = "RELAY-SET_RELAY-OK";
	
	public static final char CMD_RELAY_READ_NOW = 0x0131;
	public static final String MSG_RELAY_READ_NOW = "RELAY-READ-NOW";
	public static final String MSG_RELAY_READ_NOW_REPLY = "RELAY-READ-";
	
	// GSM
	public static final char CMD_TELEPHONE_WRITE_CMP = 0x0140;
	public static final String MSG_TELEPHONE_WRITE_CMP = "TELEPHONE-WRITE_CMP-";
	public static final String MSG_TELEPHONE_WRITE_CMP_REPLY = "TELEPHONE-WRITE_CMP-OK";
	
	public static final char CMD_TELEPHONE_WRITE_USER = 0x0141;
	public static final String MSG_TELEPHONE_WRITE_USER = "TELEPHONE-WRITE_USER-";
	public static final String MSG_TELEPHONE_WRITE_USER_REPLY = "TELEPHONE-WRITE_USER-OK";
	
	public static final char CMD_GSM_WRITE_CMP = 0x0145;
	public static final String MSG_GSM_WRITE_CMP = "GSM-WRITE_CMP-";
	public static final String MSG_GSM_WRITE_CMP_REPLY = "GSM-WRITE_CMP-OK";
	
	public static final char CMD_GSM_WRITE_SMS = 0x0146;
	public static final String MSG_GSM_WRITE_SMS = "GSM-WRITE_SMS-";
	public static final String MSG_GSM_WRITE_SMS_REPLY = "GSM-WRITE_SMS-OK";
	
	
	public static final char CMD_FIND_SIM_NOW = 0x0150;
	public static final String MSG_FIND_SIM_NOW = "FIND-SIM-NOW";
	public static final String MSG_FIND_SIM_NOW_OK_REPLY = "SIM-O";
	public static final String MSG_FIND_SIM_NOW_ERROR_REPLY = "SIM-ERRO";
	
	public static final char CMD_FIND_SIGNAL_NOW = 0x0151;
	public static final String MSG_FIND_SIGNAL_NOW = "FIND-SIGNAL-NOW";
	public static final String MSG_FIND_SIGNAL_NOW_REPLY = "SIGNAL-";
	
	// 无线 2.10/3.10.版本
	public static final char CMD_RFSTUY_315M_STUDY = 0x0201;
	public static final String MSG_RFSTUY_315M_STUDY = "RFSTUY_315M-STUDY-";
	public static final String MSG_RFSTUY_315M_STUDY_REPLY = "RFSTUY_315M-STUDY-START";
	
	public static final char CMD_RFSTUY_315M_STUDY_OK = 0x0202;
	public static final String MSG_RFSTUY_315M_STUDY_OK_REPLY = "RFSTUY_315M-STUDY-OK";
	
	public static final char CMD_RFSTUY_315M_CLOSE = 0x0203;
	public static final String MSG_RFSTUY_315M_STUDY_CLOSE_REPLY = "RFSTUY_315M-STUDY-CLOSE";
	
	public static final char CMD_RFSTUY_315M_DELETE = 0x0204;
	public static final String MSG_RFSTUY_315M_DELETE = "RFSTUY_315M-DELETE-";
	public static final String MSG_RFSTUY_315M_DELETE_REPLY = "RFSTUY_315M-DELETE-OK";
	
	public static final char CMD_RFSTUY_315M_SEND = 0x0205;
	public static final String MSG_RFSTUY_315M_SEND = "RFSTUY_315M-SEND-";
	public static final String MSG_RFSTUY_315M_SEND_REPLY = "RFSTUY_315M-SEND-OK";
	
	// 无线 2.10/3.10.版本
	public static final char CMD_RFSTUY_433M_STUDY = 0x0301;
	public static final String MSG_RFSTUY_433M_STUDY = "RFSTUY_433M-STUDY-";
	public static final String MSG_RFSTUY_433M_STUDY_REPLY = "RFSTUY_433M-STUDY-START";
	
	public static final char CMD_RFSTUY_433M_STUDY_OK = 0x0302;
	public static final String MSG_RFSTUY_433M_STUDY_OK_REPLY = "RFSTUY_433M-STUDY-OK";
	
	public static final char CMD_RFSTUY_433M_CLOSE = 0x0303;
	public static final String MSG_RFSTUY_433M_STUDY_CLOSE_REPLY = "RFSTUY_433M-STUDY-CLOSE";
	
	public static final char CMD_RFSTUY_433M_DELETE = 0x0304;
	public static final String MSG_RFSTUY_433M_DELETE = "RFSTUY_433M-DELETE-";
	public static final String MSG_RFSTUY_433M_DELETE_REPLY = "RFSTUY_433M-DELETE-OK";
	
	public static final char CMD_RFSTUY_433M_SEND = 0x0305;
	public static final String MSG_RFSTUY_433M_SEND = "RFSTUY_433M-SEND-";
	public static final String MSG_RFSTUY_433M_SEND_REPLY = "RFSTUY_433M-SEND-OK";
	
			
	public static final char CMD_OPERATION_STATUS_ERROR = 0x9999;
	public static final String MSG_OPERATION_STATUS_ERROR = "OPERATION-STATUS-ERROR";
	
	
	
	/** 类型常量 - 未知命令 */
	public static final char CMD_UNKNOWN = 0xFFFF;
}
