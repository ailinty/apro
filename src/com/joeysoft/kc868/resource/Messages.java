package com.joeysoft.kc868.resource;

import org.eclipse.osgi.util.NLS;

import com.joeysoft.kc868.KC868;

public class Messages extends NLS{
	private static String BUNDLE_NAME;
	
	public static String default_font;
	
	public static String system_title;
	
	public static String center_title;
	
	// 对话框
	public static String message_box_common_info_title;
	public static String message_box_common_warning_title;
	public static String message_box_common_error_title;
	public static String message_box_connection_error;
	public static String message_box_user_pwd_error;
	public static String message_box_disconnection;
	public static String message_box_receive_timeout;
	public static String message_box_sync_error;
	public static String message_box_unknown;
	
	public static String message_opreate_success;
	public static String message_opreate_error;
	
	public static String message_delete_title;
	
	public static String message_delete_error;
	
	// 按钮
	public static String button_login;
	public static String button_ok;
	public static String button_cancel;
	public static String button_add;
	public static String button_del;
	public static String button_edit;
	public static String button_back;
	public static String button_save;
	
	// 登录对话框
	
	public static String login_user;
	public static String login_ip;
	public static String login_pwd;
	public static String login_port;
	public static String login_remember;
	public static String login_auto_login;
	public static String login_sync;
	public static String login_user_super;
	public static String login_user_normal;
	public static String login_clear;
	
	public static String splash_loading;
	public static String splash_waiting;
	
	// 设置对话框
	public static String device_name;
	public static String device_type;
	public static String vidicon_url;
	public static String vidicon_port;
	public static String vidicon_user;
	public static String vidicon_pwd;
	
	public static String lineate_name;
	
	public static String trigger_mode;
	public static String lineate_u;
	public static String lineate_d;
	public static String lineate_g;
	public static String lineate_l;
	public static String lineate_v;
	
	public static String radio_input;
	public static String radio_auto;
	
	public static String room;
	public static String room_name;
	public static String equip;
	public static String info;
	public static String help;
	public static String scene;
	public static String safety;
	public static String smart;
	public static String system;
	
	public static String welcome;
	public static String smart_home;
	public static String control_host;
	
	public static String system_set;
	public static String change_user;
	public static String update;
	public static String homepage;
	public static String about;
	public static String exit;
	public static String change_language;
	
	public static String language_zh;
	public static String language_en;
	public static String language_tw;
	
	public static String floor;
	public static String floor_name;
	
	public static String relay_out;
	public static String sensor_out;
	public static String sensor_in;
	public static String lineate;
	public static String temp_sensor;
	public static String transfer;
	
	public static String freq_type;
	public static String code_type;
	public static String res_type;
	public static String addr_code;
	public static String data_code;
	
	public static String temp;
	
	public static String double_zoom_in;
	public static String light;
	public static String window;
	public static String screen;
	public static String vidicon;
	public static String study;
	public static String other_device;
	
	// 常规设备
	public static String normal_device;
	
	// 继电器名称
	public static String relay_name;
	// 继电器打开名称
	public static String relay_on_name;
	// 继电器关闭名称
	public static String relay_off_name;
	// 选择
	public static String select;
	// 图
	public static String icon;
	// 编号
	public static String sequence_number;
	// 当前状态
	public static String current_status;
	// 名称
	public static String name;
	
	public static String kt_con1;
	public static String kt_con2;
	public static String kt_con3;
	public static String kt_con4;
	public static String kt_con5;
	public static String kt_con6;
	public static String kt_con7;
	public static String kt_con8;
	public static String kt_con9;
	public static String kt_con10;
	
	public static String mb_on;
	public static String mb_off;
	
	public static String cl_on;
	public static String cl_off;
	public static String cl_stop;
	
	public static String mt_menu;
	public static String mt_av;
	public static String mt_back;
	public static String mt_ok;
	
	public static String sequence;
	
	public static String sms_content;
	public static String country_code;
	public static String telphone;
	public static String sensor_upper;
	public static String sensor_lower;
	public static String humidity_lower;
	public static String humidity_upper;
	public static String signal_mode;
	public static String alarm;
	public static String week;
	public static String week_7;
	public static String week_1;
	public static String week_2;
	public static String week_3;
	public static String week_4;
	public static String week_5;
	public static String week_6;
	
	public static String time;
	public static String time_h;
	public static String time_m;
	public static String time_s;
	public static String alarm_write;
	
	public static String humidity;
	public static String gt_temp_upper;
	public static String lt_temp_lower;
	public static String gt_humidity_upper;
	public static String lt_humidity_lower;
	
	public static String tel_out;
	public static String tel_in;
	public static String sms_out;
	public static String sms_in;
	
	public static String scene_mode;
	
	public static String trigger_source;
	public static String not_normal_device;
	public static String safety_bugle;
	public static String safety_on;
	public static String safety_off;
	public static String bugle_on;
	public static String bugle_off;
	public static String scene_name;
	public static String scene_icon;
	public static String out;
	public static String in;
	
	public static String host_hard_ver;
	public static String host_soft_ver;
	public static String host_temp;
	public static String pc_soft_ver;
	public static String sim_status;
	public static String sim_singel;
	public static String study_in;
	public static String bind_set;
	public static String scene_set;
	public static String param_set;
	public static String scene_mode_action_time;
	public static String action_time;
	public static String set;
	public static String change_pwd;
	public static String resume_default;
	public static String year;
	public static String month;
	public static String set_time;
	public static String title;
	public static String ceter_title;
	
	public static String system_set_clear;
	public static String system_set_clear_true;
	public static String system_set_clear_success;
	public static String system_set_clear_error;
	
	public static String system_set_startup_time;
	public static String system_set_clear_arp;
	public static String system_set_clear_arp_info;
	public static String system_set_clear_arp_true;
	public static String system_set_validation_title_isEmpty;
	public static String system_set_validation_center_isEmpty;
	public static String system_set_validation_center_length;
	public static String system_set_write_config;
	public static String system_set_write_config_info;
	public static String system_set_write_config_info2;
	public static String system_set_backup_config;
	public static String system_set_backup_config_info;
	public static String system_set_revert_config;
	public static String system_set_revert_config_info;
	public static String system_set_revert_config_info2;
	public static String system_set_host_startup;
	public static String system_set_host_startup_info;
	public static String system_set_host_resting;
	public static String system_set_host_reconnection;
	public static String system_set_host_reconnection_info;
	public static String system_set_normal_user_pwd;
	public static String system_set_old_pwd;
	public static String system_set_new_pwd;
	public static String system_set_admin_pwd;
	public static String system_set_validation_admin_pwd_error;
	public static String system_set_validation_admin_pwd_isEmpty;
	public static String system_set_validation_admin_pwd_length;
	public static String system_set_validation_admin_pwd_check;
	public static String system_set_validation_host_pwd_isEmpty;
	public static String system_set_validation_host_pwd_length;
	public static String system_set_validation_host_pwd_check;
			
	public static String error_system_set_revert_success;
	public static String error_system_set_read_error;
	public static String error_system_set_backup_config_error;
	public static String system_set_validation_pwd_length;
	public static String system_set_pwd_check;
	public static String error_system_set_dbzip_not_exist;
	public static String error_system_set_revert_config_error;
	public static String error_system_set_revert_config_success;
	public static String error_system_set_backup_config_success;
	public static String error_system_set_sync_error;
	public static String error_system_set_host_startup_success;
	public static String error_system_set_host_time_success;
	public static String error_system_set_pwd_success;
	public static String error_system_set_net_success;

	public static String validation_device_name_isEmpty;
	public static String validation_device_name_length;
	public static String validation_floor_isEmpty;
	public static String validation_room_isEmpty;
	public static String validation_room_name_isEmpty;
	public static String validation_device_isEmpty;
	public static String validation_lineate_name_isEmpty;
	public static String validation_lineate_type_isEmpty;
	
	public static String validation_relay_name_isEmpty;
	public static String validation_relay_on_name_isEmpty;
	public static String validation_relay_off_name_isEmpty;
	
	public static String validation_transfer_isEmpty;
	
	public static String validation_sensor_name_isEmpty;
	
	public static String validation_name_isEmpty;
	public static String validation_name_length6;
	
	public static String validation_sensor_upper_isEmpty;
	public static String validation_sensor_lower_isEmpty;
	public static String validation_humidity_upper_isEmpty;
	public static String validation_humidity_lower_isEmpty;
	
	public static String error_config_file;
	public static String error_device_exist;
	public static String error_temp_gt_upper;
	public static String error_temp_lt_lower;
	public static String error_scene_sensor_out;
	public static String error_trigger_source_only;
	public static String error_action_exist;
	public static String error_tel_out_only;
	public static String error_scene_has_ten_action;
	
	public static String error_relay_cant_add;
	public static String error_lineate_cant_add;
	public static String error_relay_cant_delete;
	public static String error_lineate_cant_delete;
	
	public static String error_room_used_cant_delete;
	public static String error_scene_mode_off;
	public static String error_not_authorize;
	public static String error_data_changed;
	
	public static String error_create_floor;
	public static String error_room_havt_transfer;
	public static String error_scene_is_runing;
	public static String success_set_alarm;
	public static String success_set_action_time;
	
	static {
		BUNDLE_NAME = "locale.KC868_" + KC868.language;
		NLS.initializeMessages(BUNDLE_NAME, Messages.class);
	}
	
	public static void initialize(){
		BUNDLE_NAME = "locale.KC868_" + KC868.language;
		NLS.initializeMessages(BUNDLE_NAME, Messages.class);
	}
}
