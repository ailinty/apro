package com.joeysoft.kc868.common;

import java.util.List;

import org.apache.commons.lang.StringUtils;

import net.sf.json.JSONArray;
import net.sf.json.JSONException;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import net.sf.json.util.CycleDetectionStrategy;

public abstract class JSonUtil {

	private static JsonConfig jsonConfig = null;

	public synchronized static JsonConfig getJsonConfig() {
		if (jsonConfig == null) {
			jsonConfig = new JsonConfig();
			// jsonConfig.registerJsonBeanProcessor(java.sql.Date.class, new
			// JsDateJsonBeanProcessor());
			// jsonConfig.registerJsonBeanProcessor(java.sql.Timestamp.class,
			// new JsDateJsonBeanProcessor());
			/*
			 * jsonConfig.registerDefaultValueProcessor(Integer.class, new
			 * DefaultValueProcessor(){ public Object getDefaultValue(Class
			 * type){ return JSONNull.getInstance(); } });
			 * jsonConfig.registerDefaultValueProcessor(Float.class, new
			 * DefaultValueProcessor(){ public Object getDefaultValue(Class
			 * type){ return JSONNull.getInstance(); } });
			 * jsonConfig.registerDefaultValueProcessor(Long.class, new
			 * DefaultValueProcessor(){ public Object getDefaultValue(Class
			 * type){ return JSONNull.getInstance(); } });
			 */
		}
		return jsonConfig;
	}

	public static Object getObject(JSONObject object, String key) {
		return getObject(object, key, "");
	}

	public static Object getObject(JSONObject object, String key,
			Object defaultValue) {
		if (object == null || !object.has(key)) {
			return defaultValue;
		}
		return object.get(key);
	}

	public static String getString(JSONObject object, String key) {
		return getString(object, key, "");
	}

	public static String getString(JSONObject object, String key,
			String defaultValue) {
		if (object == null || !object.has(key)) {
			return defaultValue;
		}
		return object.getString(key);
	}

	public static int getInt(JSONObject object, String key) {
		return getInt(object, key, 0);
	}

	public static int getInt(JSONObject object, String key, int defaultValue) {
		if (object == null || !object.has(key)) {
			return defaultValue;
		}
		try {
			return object.getInt(key);
		} catch (Exception e) {
			return defaultValue;
		}
	}

	public static Long getLong(JSONObject object, String key) {
		return getLong(object, key, new Long(0));
	}

	public static Long getLong(JSONObject object, String key, Long defaultValue) {
		if (object == null || !object.has(key)) {
			if (defaultValue == null) {
				return null;
			}
			return new Long(defaultValue);
		}
		try {
			return object.getLong(key);
		} catch (Exception e) {
			return defaultValue;
		}
	}

	public static Double getDouble(JSONObject object, String key) {
		return getDouble(object, key, new Double(0));
	}

	public static Double getDouble(JSONObject object, String key,
			Double defaultValue) {
		if (object == null || !object.has(key)) {
			if (defaultValue == null) {
				return null;
			}
			return new Double(defaultValue);
		}
		try {
			return object.getDouble(key);
		} catch (Exception e) {
			return defaultValue;
		}
	}

	public static boolean getBoolean(JSONObject object, String key) {
		return getBoolean(object, key, false);
	}

	public static boolean getBoolean(JSONObject object, String key,
			boolean defaultValue) {
		if (object == null || !object.has(key)) {
			return defaultValue;
		}
		return object.getBoolean(key);
	}

	public static JSONObject getJSONObject(JSONObject object, String key) {
		if (object == null || !object.has(key)) {
			return new JSONObject();
		}
		return object.getJSONObject(key);
	}

	public static JSONArray getJSONArray(JSONObject object, String key) {
		if (object == null || !object.has(key)) {
			return new JSONArray();
		}
		return object.getJSONArray(key);
	}

	/**
	 * 
	 * 将返回结果转换为json格式
	 * 
	 * @param list
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static String genJson(List list) {
		return genJson(list, null, true);
	}

	@SuppressWarnings("unchecked")
	public static String genJson(List list, String[] excludes) {
		return genJson(list, excludes, true);
	}

	/**
	 * 
	 * 将返回结果转换为json格式
	 * 
	 * @param list
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static String genJson(List list, String[] excludes, boolean nullValue) {
		String json = "";
		if (list == null || list.size() < 1) {
			json = "{}";
			return json;
		}
		JSONArray jsonList = null;
		try {
			if (excludes == null) {
				jsonList = JSONArray.fromObject(list, getJsonConfig());
			} else {
				JsonConfig cfg = JSonUtil.getJsonConfig();
				cfg.setCycleDetectionStrategy(CycleDetectionStrategy.LENIENT);
				cfg.setExcludes(excludes);

				jsonList = JSONArray.fromObject(list, cfg);
			}
		} catch (JSONException e) {
			e.printStackTrace();
			json = "{}";
			return json;
		}
		json = jsonList.toString();
		return json;
	}

	/**
	 * 将返回结果转换为json格式
	 * 
	 * @param list
	 * @param count
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static String genJson(List list, long count) {
		return genJson(list, count, null, true);
	}

	/**
	 * 将返回结果转换为json格式
	 * 
	 * @param list
	 * @param count
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static String genJson(List list, long count, boolean nullValue) {
		return genJson(list, count, null, nullValue);
	}

	/**
	 * 
	 * 将返回结果转换为json格式
	 * 
	 * @param obj
	 * @return
	 */
	public static String genJson(Object obj) {
		return genJson(obj, null, true);
	}

	/**
	 * 
	 * 将返回结果转换为json格式
	 * 
	 * @param obj
	 * @param nullValue
	 * @return
	 */
	public static String genJson(Object obj, boolean nullValue) {
		return genJson(obj, null, nullValue);
	}

	/**
	 * 
	 * 将返回结果转换为json格式
	 * 
	 * @param list
	 * @param count
	 * @param excludes
	 *            循环属�?(Hibenate级联关系)
	 * @param nullValue
	 *            false 当Integer为null转为0, 默认为true
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static String genJson(List list, long count, String[] excludes,
			boolean nullValue) {
		String json = "";
		if (list == null || list.size() < 1) {
			json = "{'success':true,'results':'0','nodes':[]}";
			return json;
		}
		JSONArray jsonList = null;
		try {
			if (excludes == null) {
				jsonList = JSONArray.fromObject(list, getJsonConfig());
			} else {
				JsonConfig cfg = JSonUtil.getJsonConfig();
				cfg.setCycleDetectionStrategy(CycleDetectionStrategy.LENIENT);
				cfg.setExcludes(excludes);
				jsonList = JSONArray.fromObject(list, cfg);
			}
		} catch (JSONException e) {
			json = "{'success':false,'results':'0','nodes':[]}";
			return json;
		}
		if (count <= 0) {
			json = "{'success':true,'results':'" + list.size() + "','nodes':"
					+ jsonList.toString() + "}";
		} else {
			json = "{'success':true,'results':'" + count + "','nodes':"
					+ jsonList.toString() + "}";
		}
		return json;
	}

	/**
	 * 将返回结果转换为json格式
	 * 
	 * @param obj
	 * @param excludes
	 *            循环属�?(Hibenate级联关系)
	 * @param nullValue
	 *            false 当Integer为null转为0, 默认为true
	 * @return
	 */
	public static String genJson(Object obj, String[] excludes,
			boolean nullValue) {
		String json = "";
		if (obj == null) {
			json = "{'success':true,'results':'0','nodes':[]}";
			return json;
		}
		JSONObject jsonObject = null;
		try {
			if (excludes == null) {
				jsonObject = JSONObject.fromObject(obj, getJsonConfig());
			} else {
				JsonConfig cfg = JSonUtil.getJsonConfig();
				cfg.setCycleDetectionStrategy(CycleDetectionStrategy.LENIENT);
				cfg.setExcludes(excludes);
				jsonObject = JSONObject.fromObject(obj, cfg);
			}
		} catch (JSONException e) {
			json = "{'success':false,'results':'0','nodes':[]}";
			return json;
		}
		json = "{'success':true,'results':'1','nodes':" + jsonObject.toString()
				+ "}";
		return json;
	}

	public static String getErrorMessage(String message) {
		return "{success:false,results:0,msg:'" + message + "',nodes:[]}";
	}

	public static String getSuccessMessage(String message) {
		return "{success:true,results:0,msg:'" + message + "',nodes:[]}";
	}

	public static String getDWZMessage(String type, String message,
			String nvaTabId, String callbackType, String forwardUrl,
			String flexId) {
		if (callbackType == null) {
			callbackType = "";
			if ("success".equals(type)) {
				callbackType = "closeCurrent";
			}
		}
		if (StringUtils.isEmpty(forwardUrl)) {
			forwardUrl = "";
		} else {
			callbackType = "forward";
		}
		String statusCode = "200";
		if ("error".equals(type)) {
			statusCode = "300";
		} else if ("timeout".equals(type)) {
			statusCode = "301";
		} else {
			statusCode = "200";
		}
		StringBuffer sb = new StringBuffer();
		sb.append("{");
		sb.append("\"statusCode\":");
		sb.append("\"");
		sb.append(statusCode);
		sb.append("\"");
		sb.append(",\"message\":");
		sb.append("\"");
		sb.append(message);
		sb.append("\"");
		if (StringUtils.isNotEmpty(nvaTabId)) {
			sb.append(",\"navTabId\":");
			sb.append("\"");
			sb.append(nvaTabId);
			sb.append("\"");
		}
		sb.append(", \"forwardUrl\":");
		sb.append("\"");
		sb.append(forwardUrl);
		sb.append("\"");
		sb.append(", \"callbackType\":");
		sb.append("\"");
		sb.append(callbackType);
		sb.append("\"");
		if (StringUtils.isNotEmpty(flexId)) {
			sb.append(", \"flexId\":");
			sb.append("\"");
			sb.append(flexId);
			sb.append("\"");
		}
		sb.append("}");
		return sb.toString();
	}

	public static String getDWZMessage(String type, String message,
			String params) {
		String nvaTabId = "";
		if (StringUtils.isEmpty(nvaTabId)) {
			nvaTabId = "";
		}
		String callbackType = "";
		if (StringUtils.isEmpty(callbackType)) {
			callbackType = "";
			if ("success".equals(type)) {
				callbackType = "closeCurrent";
			}
		}
		String forwardUrl = "";
		if (StringUtils.isEmpty(forwardUrl)) {
			forwardUrl = "";
		} else {
			callbackType = "forward";
		}
		String statusCode = "200";
		if ("error".equals(type)) {
			statusCode = "300";
		} else if ("timeout".equals(type)) {
			statusCode = "301";
		} else {
			statusCode = "200";
		}
		if (StringUtils.isNotEmpty(params)) {
			params = "," + params;
		}
		return "{\"statusCode\":\"" + statusCode + "\",\"message\":\""
				+ message + "\",\"navTabId\":\"" + nvaTabId
				+ "\", \"forwardUrl\":\"" + forwardUrl
				+ "\", \"callbackType\":\"" + callbackType + "\"" + params
				+ "}";
	}

	@SuppressWarnings("unchecked")
	public static String genJsonDWZ(List list, long count) {
		String json = "";
		if (list == null || list.size() < 1) {
			json = "{'statusCode':200,message:'','results':0,'nodes':[],navTabId:'',forwardUrl:'',callbackType:''}";
			return json;
		}
		JSONArray jsonList = null;
		try {
			jsonList = JSONArray.fromObject(list, getJsonConfig());
		} catch (JSONException e) {
			json = "{'statusCode':200,message:'','results':0,'nodes':[],navTabId:'',forwardUrl:'',callbackType:''}";
			return json;
		}
		if (count <= 0) {
			json = "{'statusCode':200,message:'','results':" + list.size()
					+ ",'nodes':" + jsonList.toString()
					+ ",navTabId:'',forwardUrl:'',callbackType:''}";
		} else {
			json = "{'statusCode':200,message:'','results':" + count
					+ ",'nodes':" + jsonList.toString()
					+ ",navTabId:'',forwardUrl:'',callbackType:''}";
		}
		return json;
	}

}