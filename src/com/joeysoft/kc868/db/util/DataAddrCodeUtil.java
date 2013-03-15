package com.joeysoft.kc868.db.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.joeysoft.kc868.common.GlobalMethod;
import com.joeysoft.kc868.db.bean.Dict;
import com.joeysoft.kc868.ui.dialogs.helper.ComboHelper;

/**
 * 数据码 地址码生成器
 * 
 * @author JOEY
 * 
 */
public class DataAddrCodeUtil {
	private static Random random;;

	/**
	 * @return Random对象
	 */
	public static Random random() {
		if (random == null)
			random = new Random();
		return random;
	}

	/**
	 * 获取codeType编码下resType电阻的可用地址码
	 * @param codeType 编码
	 * @param resType  电阻
	 * @return
	 */
	public static int getUnusedAddrCode(String codeType, String resType) {
		int addrCode = getRandAddrCode(codeType);
		while(DataAddrCodeManager.getInstance().isContains(resType, addrCode)){
			addrCode = getRandAddrCode(codeType);
		}
		return addrCode;
	}
	
	/**
	 * 获取codeType编码下resType电阻下addrCode地址码下的可用数据码,如果没有可用数据码，返回-1
	 * @param codeType
	 * @param resType
	 * @param addrCode
	 * @return int
	 */
	public static int getUnusedDataCode(String codeType, String resType, int addrCode){
		if(DataAddrCodeManager.getInstance().isFull(resType, addrCode)){ // 已经都使用返回-1
			return -1;
		}
		ArrayList<Integer> dataList = DataAddrCodeManager.getInstance().getDataList(resType, addrCode);
		for(int i=1;i<16;i++){
			if(dataList == null || !dataList.contains(i)){
				return i;
			}
		}
		return -1;
	}
	
	/**
	 * 获取codeType编码下resType电阻下addrCode地址码下的可用数据码,如果没有可用数据码，返回-1, 不能包括scheduledList预定数据码
	 * @param codeType
	 * @param resType
	 * @param addrCode
	 * @return int
	 */
	public static int getUnusedDataCode(String codeType, String resType, int addrCode, List<Integer> scheduledList){
		if(DataAddrCodeManager.getInstance().isFull(resType, addrCode)){ // 已经都使用返回-1
			return -1;
		}
		ArrayList<Integer> dataList = DataAddrCodeManager.getInstance().getDataList(resType, addrCode);
		for(int i=1;i<16;i++){
			if((scheduledList == null || !scheduledList.contains(i)) && (dataList == null || !dataList.contains(i))){
				return i;
			}
		}
		return -1;
	}
	
	/**
	 * 获取codeType编码下resType电阻下addrCode地址码下的可用数据码,如果没有可用数据码，返回-1, 只在1-10中选择
	 * @param codeType
	 * @param resType
	 * @param addrCode
	 * @return int
	 */
	public static int getUnusedDataCode10(String codeType, String resType, int addrCode){
		if(DataAddrCodeManager.getInstance().isFull(resType, addrCode)){ // 已经都使用返回-1
			return -1;
		}
		ArrayList<Integer> dataList = DataAddrCodeManager.getInstance().getDataList(resType, addrCode);
		for(int i=1;i<=10;i++){
			if(dataList == null || !dataList.contains(i)){
				return i;
			}
		}
		return -1;
	}
	
	/**
	 * 获取codeType编码下resType电阻下addrCode地址码下的可用数据码,如果没有可用数据码，返回-1, 不能包括scheduledList预定数据码, 只在1-10中选择
	 * @param codeType
	 * @param resType
	 * @param addrCode
	 * @return int
	 */
	public static int getUnusedDataCode10(String codeType, String resType, int addrCode, List<Integer> scheduledList){
		if(DataAddrCodeManager.getInstance().isFull(resType, addrCode)){ // 已经都使用返回-1
			return -1;
		}
		ArrayList<Integer> dataList = DataAddrCodeManager.getInstance().getDataList(resType, addrCode);
		for(int i=1;i<=10;i++){
			if((scheduledList == null || !scheduledList.contains(i)) && (dataList == null || !dataList.contains(i))){
				return i;
			}
		}
		return -1;
	}
	
	/**
	 * 获取codeType编码下resType电阻下addrCode地址码下的可用数据码，
	 * 如resType电阻下addrCode地址码的数据码都使用完了，就使用下一次电阻addrCode地址码下的数据码，
	 * 如果都完用，则返回null
	 * @param codeType
	 * @param resType
	 * @param addrCode
	 * @return ResDataVo
	 */
	public static ResDataVo getUnusedDataCodeNextRes(String codeType, String aResType, int addrCode){
		String resType = aResType;
		int dataCode = getUnusedDataCode(codeType, resType, addrCode);
		if(dataCode == -1){ //已经没有可用数据码
			int index = 0;
			int resTypeSize = 0;
			List<Dict> list = null;
			if("2262".equals(codeType)){
				list = DictManager.getInstance().getDictList(DictConst.RES_TYPE);
				resTypeSize = list.size();
			}else if("1527".equals(codeType)){
				list =  DictManager.getInstance().getDictList(DictConst.RES_TYPE2);
				resTypeSize = list.size();
			}
			
			while(index < resTypeSize && dataCode == -1){
				Dict dict = list.get(index);
				resType = dict.getDictValue();
				dataCode = getUnusedDataCode(codeType, resType, addrCode);
				index++;
			}
		}
		
		if(dataCode == -1){ // 此地址码的所有数据码都用完了
			return null;
		}
		return new ResDataVo(resType, dataCode);
	}
	
	/**
	 * 返回数据码+地址码的十进制
	 * @param dataCode
	 * @param addrCode
	 * @param addrNum 地址码位数
	 * @return
	 */
	public static int getDataAddrCodeBit(int addrCode, int dataCode, int addrNum){
		String data = getDataCode2D(dataCode) + GlobalMethod.addLeadingZero(Integer.toBinaryString(addrCode), addrNum);
		return Integer.valueOf(data, 2);
	}
	
	/**
	 * 按不同的编码返回随机地址码
	 * @param codeType
	 * @return
	 */
	private static int getRandAddrCode(String codeType){
		if("2262".equals(codeType)){
			return getRand2262AddrCode();
		}else{
			return getRand1527AddrCode();
		}
	}
	
	/**
	 * 返回1527编码的随机地址码
	 * @return
	 */
	private static int getRand1527AddrCode(){
		return random().nextInt(1084574) + 1;
	}
	
	/**
	 * 返回有效2262编码的随机地址码
	 * @return
	 */
	private static int getRand2262AddrCode(){
		int rand = random().nextInt(65532) + 3;
		while(!isValidAddrCode(rand)){
			 rand = random().nextInt(65532) + 3;
		}
		return rand;
	}
	
	/**
	 * 判断2262编码的地址码是否有效的地址码
	 * @return
	 */
	private static boolean isValidAddrCode(int addrCode){
		for (int k = 0; k < 16; k += 2) {
			if ((((byte)addrCode >> k) & 0x03) == 0x01) {
				return false;
			}
		}
		return true;
	}
	
	/**
	 * 4位数据码转为8位数据码
	 * @param data
	 * @return
	 */
	public static String getDataCode2D(int data){
		String data2B = Integer.toBinaryString(data);
		String data2d = "";
		
		for(int i=0; i<data2B.length() ; i++){
			data2d += data2B.substring(i, i+ 1) + data2B.substring(i, i+ 1);
		}
		return data2d;
	}
	
	/**
	 * 把地址码、数据码的组合拆分
	 * @param addrDataCode
	 * @return
	 */
	public static int[] splitAddrCodeDataCode2262(int addrDataCode){
		String addrData2B = GlobalMethod.addLeadingZero(Integer.toBinaryString(addrDataCode), 12);
		String data2B = addrData2B.substring(0, 4);
		String addr2B = addrData2B.substring(4);
		int[] ret = new int[2];
		ret[0] = Integer.valueOf(addr2B, 2);
		ret[1] = Integer.valueOf(data2B, 2);
		return ret;
	}
	
	/**
	 * 把地址码、数据码的组合拆分
	 * @param addrDataCode
	 * @return
	 */
	public static int[] splitAddrCodeDataCode1527(int addrDataCode){
		String addrData2B = GlobalMethod.addLeadingZero(Integer.toBinaryString(addrDataCode), 24);
		String data2B = addrData2B.substring(0, 4);
		String addr2B = addrData2B.substring(4);
		int[] ret = new int[2];
		ret[0] = Integer.valueOf(addr2B, 2);
		ret[1] = Integer.valueOf(data2B, 2);
		return ret;
	}
	
	/**
	 * 地址码
	 * @param switchs
	 * @return
	 */
	public static int getAddrCode8(boolean[] switchs){
		StringBuffer sb = new StringBuffer(16);
		for(int i=0; i<switchs.length; i++){
			sb.append(switchs[i]?"11":"00");
		}
		return Integer.valueOf(sb.toString(), 2);
	}
	
	public static boolean[] getSwitchs8(int addrCode){
		boolean[] switchs = new boolean[8];
		String addrCodes = GlobalMethod.addLeadingZero(Integer.toBinaryString(addrCode), 16);
		for(int i=0; i<8; i++){
			String b = addrCodes.substring(i*2, i*2+2);
			if("00".equals(b)){
				switchs[i] = false;
			}else if("11".equals(b)){
				switchs[i] = true;
			}
		}
		return switchs;
	}
	
	public static int getAddrCode8Int(int[] switchs){
		StringBuffer sb = new StringBuffer(16);
		for(int i=0; i<switchs.length; i++){
			sb.append(switchs[i] == 0?"00":switchs[i] == 1?"11":"10");
		}
		return Integer.valueOf(sb.toString(), 2);
	}
	
	public static int[] getSwitchs8Int(int addrCode){
		int[] switchs = new int[8];
		String addrCodes = GlobalMethod.addLeadingZero(Integer.toBinaryString(addrCode), 16);
		for(int i=0; i<8; i++){
			String b = addrCodes.substring(i*2, i*2+2);
			if("00".equals(b)){
				switchs[i] = 0;
			}else if("11".equals(b)){
				switchs[i] = 1;
			}else if("10".equals(b)){
				switchs[i] = 2;
			}
		}
		return switchs;
	}
	
	/**
	 * 地址码20
	 * @param switchs
	 * @return
	 */
	public static int getAddrCode20(boolean[] switchs){
		StringBuffer sb = new StringBuffer(20);
		for(int i=0; i<switchs.length; i++){
			sb.append(switchs[i]?"1":"0");
		}
		return Integer.valueOf(sb.toString(), 2);
	}
	
	public static boolean[] getSwitchs20(int addrCode){
		boolean[] switchs = new boolean[20];
		String addrCodes = GlobalMethod.addLeadingZero(Integer.toBinaryString(addrCode), 20);
		for(int i=0; i<20; i++){
			String b = addrCodes.substring(i, i+1);
			if("0".equals(b)){
				switchs[i] = false;
			}else if("1".equals(b)){
				switchs[i] = true;
			}
		}
		return switchs;
	}
	
	/**
	 * 数据码
	 * @param switchs
	 * @return
	 */
	public static int getDataCode4(boolean[] switchs){
		StringBuffer sb = new StringBuffer(4);
		for(int i=0; i<switchs.length; i++){
			sb.append(switchs[i]?"1":"0");
		}
		return Integer.valueOf(sb.toString(), 2);
	}
	
	public static boolean[] getSwitchs4(int dataCode){
		boolean[] switchs = new boolean[4];
		String dataCodes = GlobalMethod.addLeadingZero(Integer.toBinaryString(dataCode), 4);
		for(int i=0; i<4; i++){
			String b = dataCodes.substring(i, i+1);
			if("1".equals(b)){
				switchs[i] = true;
			}else if("0".equals(b)){
				switchs[i] = false;
			}
		}
		return switchs;
	}
}
