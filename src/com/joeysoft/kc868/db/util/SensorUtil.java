package com.joeysoft.kc868.db.util;

import com.joeysoft.kc868.db.bean.Lineate;

/**
 * 无线控制输出帮助类
 * 无线控制输出按频率、编码分RA、RB、RC、RD
 * @author JOEY
 *
 */
public class SensorUtil {
	/**
	 * 按频率、编码得到无线控制输出的编码
	 * @param freqType
	 * @param codeType
	 * @return
	 */
	public static String getSensorCodeOut(String freqType, String codeType){
		if("315M".equals(freqType) && "2262".equals(codeType)){
			return DictConst.TABLE_PREFIX_2262_315_OUT;
		}
		if("433M".equals(freqType) && "2262".equals(codeType)){
			return DictConst.TABLE_PREFIX_2262_433_OUT;
		}
		if("315M".equals(freqType) && "1527".equals(codeType)){
			return DictConst.TABLE_PREFIX_1527_315_OUT;
		}
		if("433M".equals(freqType) && "1527".equals(codeType)){
			return DictConst.TABLE_PREFIX_1527_433_OUT;
		}
		return "";
	}
	
	/**
	 * 按频率、编码得到无线控制输入的编码
	 * @param freqType
	 * @param codeType
	 * @return
	 */
	public static String getSensorCodeIn(String freqType, String codeType){
		if("315M".equals(freqType) && "2262".equals(codeType)){
			return DictConst.TABLE_PREFIX_2262_315_IN;
		}
		if("433M".equals(freqType) && "2262".equals(codeType)){
			return DictConst.TABLE_PREFIX_2262_433_IN;
		}
		if("315M".equals(freqType) && "1527".equals(codeType)){
			return DictConst.TABLE_PREFIX_1527_315_IN;
		}
		if("433M".equals(freqType) && "1527".equals(codeType)){
			return DictConst.TABLE_PREFIX_1527_433_IN;
		}
		return "";
	}
	
	/**
	 * 返回有线输入的编码
	 * @param lineate
	 * @return
	 */
	public static String getLineateCode(Lineate lineate){
		if(DictConst.LINEATE_TYPE_UD.equals(lineate.getLineateType())){
			if(lineate.getLineateUD().equals(DictConst.LINEATE_UD_U)){
				return DictConst.TABLE_PREFIX_SPR;
			}else{
				return DictConst.TABLE_PREFIX_SPF;
			}
		}else{
			if(lineate.getLineateGL().equals(DictConst.LINEATE_GL_G)){
				return DictConst.TABLE_PREFIX_SAO;
			}else{
				return DictConst.TABLE_PREFIX_SAU;
			}
		}
		
		
	}
}
