package com.joeysoft.kc868.client.packets.util;

import com.joeysoft.kc868.client.packets.OutPacket;
import com.joeysoft.kc868.client.packets.out.ev1527315.EV1527315DeletePacket;
import com.joeysoft.kc868.client.packets.out.ev1527315.EV1527315InputPacket;
import com.joeysoft.kc868.client.packets.out.ev1527315.EV1527315SendPacket;
import com.joeysoft.kc868.client.packets.out.ev1527315.EV1527315StudyClosePacket;
import com.joeysoft.kc868.client.packets.out.ev1527315.EV1527315StudyPacket;
import com.joeysoft.kc868.client.packets.out.ev1527315.EV1527315WritePacket;
import com.joeysoft.kc868.client.packets.out.ev1527433.EV1527433DeletePacket;
import com.joeysoft.kc868.client.packets.out.ev1527433.EV1527433InputPacket;
import com.joeysoft.kc868.client.packets.out.ev1527433.EV1527433SendPacket;
import com.joeysoft.kc868.client.packets.out.ev1527433.EV1527433StudyClosePacket;
import com.joeysoft.kc868.client.packets.out.ev1527433.EV1527433StudyPacket;
import com.joeysoft.kc868.client.packets.out.ev1527433.EV1527433WritePacket;
import com.joeysoft.kc868.client.packets.out.pt2262315.PT2262315DeletePacket;
import com.joeysoft.kc868.client.packets.out.pt2262315.PT2262315InputPacket;
import com.joeysoft.kc868.client.packets.out.pt2262315.PT2262315SendPacket;
import com.joeysoft.kc868.client.packets.out.pt2262315.PT2262315StudyClosePacket;
import com.joeysoft.kc868.client.packets.out.pt2262315.PT2262315StudyPacket;
import com.joeysoft.kc868.client.packets.out.pt2262315.PT2262315WritePacket;
import com.joeysoft.kc868.client.packets.out.pt2262433.PT2262433DeletePacket;
import com.joeysoft.kc868.client.packets.out.pt2262433.PT2262433InputPacket;
import com.joeysoft.kc868.client.packets.out.pt2262433.PT2262433SendPacket;
import com.joeysoft.kc868.client.packets.out.pt2262433.PT2262433StudyClosePacket;
import com.joeysoft.kc868.client.packets.out.pt2262433.PT2262433StudyPacket;
import com.joeysoft.kc868.client.packets.out.pt2262433.PT2262433WritePacket;
import com.joeysoft.kc868.client.packets.out.rf2262315.RF2262315DeletePacket;
import com.joeysoft.kc868.client.packets.out.rf2262315.RF2262315StudyClosePacket;
import com.joeysoft.kc868.client.packets.out.rf2262315.RF2262315StudyPacket;
import com.joeysoft.kc868.client.packets.out.rf2262433.RF2262433DeletePacket;
import com.joeysoft.kc868.client.packets.out.rf2262433.RF2262433StudyPacket;

public class SensorPacketUtil {
	
	public static OutPacket getSensorInStudyOutPacket(String sensorId, String freqType, String codeType){
		int sensorLoad = Integer.valueOf(sensorId.substring(3));
		if("315M".equals(freqType) && "2262".equals(codeType)){
			return new PT2262315StudyPacket(sensorLoad);
		}
		if("433M".equals(freqType) && "2262".equals(codeType)){
			return new PT2262433StudyPacket(sensorLoad);
		}
		if("315M".equals(freqType) && "1527".equals(codeType)){
			return new EV1527315StudyPacket(sensorLoad);
		}
		if("433M".equals(freqType) && "1527".equals(codeType)){
			return new EV1527433StudyPacket(sensorLoad);
		}
		return new PT2262315StudyPacket(sensorLoad);
	}
	
	public static OutPacket getSensorInRFStudyOutPacket(String sensorId, String freqType, String codeType){
		int sensorLoad = Integer.valueOf(sensorId.substring(3));
		if("315M".equals(freqType) && "2262".equals(codeType)){
			return new RF2262315StudyPacket(sensorLoad);
		}
		if("433M".equals(freqType) && "2262".equals(codeType)){
			return new RF2262433StudyPacket(sensorLoad);
		}
		/*if("315M".equals(freqType) && "1527".equals(codeType)){
			return new RF1527315StudyPacket(sensorLoad);
		}
		if("433M".equals(freqType) && "1527".equals(codeType)){
			return new RF1527433StudyPacket(sensorLoad);
		}*/
		return new RF2262315StudyPacket(sensorLoad);
	}
	
	public static OutPacket getSensorInInputOutPacket(String sensorId, String freqType, String codeType, String resType, int addrCode, int dataCode){
		int sensorLoad = Integer.valueOf(sensorId.substring(3));
		if("315M".equals(freqType) && "2262".equals(codeType)){
			PT2262315InputPacket packet = new PT2262315InputPacket();
			packet.setSensorId(sensorLoad);
			packet.setResType(resType);
			packet.setAddrCode(addrCode);
			packet.setDataCode(dataCode);
			return packet;
		}else if("433M".equals(freqType) && "2262".equals(codeType)){
			PT2262433InputPacket packet = new PT2262433InputPacket();
			packet.setSensorId(sensorLoad);
			packet.setResType(resType);
			packet.setAddrCode(addrCode);
			packet.setDataCode(dataCode);
			return packet;
		}else if("315M".equals(freqType) && "1527".equals(codeType)){
			EV1527315InputPacket packet = new EV1527315InputPacket();
			packet.setSensorId(sensorLoad);
			packet.setResType(resType);
			packet.setAddrCode(addrCode);
			packet.setDataCode(dataCode);
			return packet;
		}else if("433M".equals(freqType) && "1527".equals(codeType)){
			EV1527433InputPacket packet = new EV1527433InputPacket();
			packet.setSensorId(sensorLoad);
			packet.setResType(resType);
			packet.setAddrCode(addrCode);
			packet.setDataCode(dataCode);
			return packet;
		}else{
			PT2262315InputPacket packet = new PT2262315InputPacket();
			packet.setSensorId(sensorLoad);
			packet.setResType(resType);
			packet.setAddrCode(addrCode);
			packet.setDataCode(dataCode);
			return packet;
		}
	}
	
	public static OutPacket getSensorInDeleteOutPacket(String sensorId, String freqType, String codeType, String resType, int addrCode, int dataCode){
		int sensorLoad = Integer.valueOf(sensorId.substring(3));
		if("315M".equals(freqType) && "2262".equals(codeType)){
			PT2262315DeletePacket packet = new PT2262315DeletePacket();
			packet.setSensorId(sensorLoad);
			packet.setResType(resType);
			packet.setAddrCode(addrCode);
			packet.setDataCode(dataCode);
			return packet;
		}else if("433M".equals(freqType) && "2262".equals(codeType)){
			PT2262433DeletePacket packet = new PT2262433DeletePacket();
			packet.setSensorId(sensorLoad);
			packet.setResType(resType);
			packet.setAddrCode(addrCode);
			packet.setDataCode(dataCode);
			return packet;
		}else if("315M".equals(freqType) && "1527".equals(codeType)){
			EV1527315DeletePacket packet = new EV1527315DeletePacket();
			packet.setSensorId(sensorLoad);
			packet.setResType(resType);
			packet.setAddrCode(addrCode);
			packet.setDataCode(dataCode);
			return packet;
		}else if("433M".equals(freqType) && "1527".equals(codeType)){
			EV1527433DeletePacket packet = new EV1527433DeletePacket();
			packet.setSensorId(sensorLoad);
			packet.setResType(resType);
			packet.setAddrCode(addrCode);
			packet.setDataCode(dataCode);
			return packet;
		}else{
			PT2262315DeletePacket packet = new PT2262315DeletePacket();
			packet.setSensorId(sensorLoad);
			packet.setResType(resType);
			packet.setAddrCode(addrCode);
			packet.setDataCode(dataCode);
			return packet;
		}
	}
	
	public static OutPacket getSensorInRFDeleteOutPacket(String sensorId, String freqType, String codeType, String resType, int addrCode, int dataCode){
		int sensorLoad = Integer.valueOf(sensorId.substring(3));
		if("315M".equals(freqType) && "2262".equals(codeType)){
			RF2262315DeletePacket packet = new RF2262315DeletePacket();
			packet.setSensorId(sensorLoad);
			return packet;
		}else if("433M".equals(freqType) && "2262".equals(codeType)){
			RF2262433DeletePacket packet = new RF2262433DeletePacket();
			packet.setSensorId(sensorLoad);
			return packet;
			/*}else if("315M".equals(freqType) && "1527".equals(codeType)){
			RF1527315DeletePacket packet = new RF1527315DeletePacket();
			packet.setSensorId(sensorLoad);
			return packet;
		}else if("433M".equals(freqType) && "1527".equals(codeType)){
			RF1527433DeletePacket packet = new RF1527433DeletePacket();
			packet.setSensorId(sensorLoad);
			return packet;*/
		}else{
			RF2262315DeletePacket packet = new RF2262315DeletePacket();
			packet.setSensorId(sensorLoad);
			return packet;
		}
	}
	
	public static OutPacket getSensorInStudyCloseOutPacket(String freqType, String codeType){
		if("315M".equals(freqType) && "2262".equals(codeType)){
			return new PT2262315StudyClosePacket();
		}
		if("433M".equals(freqType) && "2262".equals(codeType)){
			return new PT2262433StudyClosePacket();
		}
		if("315M".equals(freqType) && "1527".equals(codeType)){
			return new EV1527315StudyClosePacket();
		}
		if("433M".equals(freqType) && "1527".equals(codeType)){
			return new EV1527433StudyClosePacket();
		}
		return new PT2262315StudyClosePacket();
	}
	
	public static OutPacket getSensorInRFStudyCloseOutPacket(String freqType, String codeType){
		if("315M".equals(freqType) && "2262".equals(codeType)){
			return new RF2262315StudyClosePacket();
		}
		/*if("433M".equals(freqType) && "2262".equals(codeType)){
			return new RF2262433StudyClosePacket();
		}
		if("315M".equals(freqType) && "1527".equals(codeType)){
			return new RF1527315StudyClosePacket();
		}
		if("433M".equals(freqType) && "1527".equals(codeType)){
			return new RF1527433StudyClosePacket();
		}*/
		return new RF2262315StudyClosePacket();
	}
	
	public static OutPacket getSensorWriteOutPacket(String sensorId, String freqType, String codeType, String resType, int addrCode, int dataCode){
		return getSensorWriteOutPacket(Integer.valueOf(sensorId.substring(2)), freqType, codeType, resType, addrCode, dataCode);
	}
	/**
	 * 
	 * @param sensorId
	 * @param freqType
	 * @param codeType
	 * @param resType
	 * @param addrCode
	 * @param dataCode
	 * @return
	 */
	public static OutPacket getSensorWriteOutPacket(int sensorId, String freqType, String codeType, String resType, int addrCode, int dataCode){
		if("315M".equals(freqType) && "2262".equals(codeType)){
			PT2262315WritePacket packet = new PT2262315WritePacket();
			packet.setSensorId(sensorId);
			packet.setResType(resType);
			packet.setAddrCode(addrCode);
			packet.setDataCode(dataCode);
			return packet;
		}else if("433M".equals(freqType) && "2262".equals(codeType)){
			PT2262433WritePacket packet = new PT2262433WritePacket();
			packet.setSensorId(sensorId);
			packet.setResType(resType);
			packet.setAddrCode(addrCode);
			packet.setDataCode(dataCode);
			return packet;
		}else if("315M".equals(freqType) && "1527".equals(codeType)){
			EV1527315WritePacket packet = new EV1527315WritePacket();
			packet.setSensorId(sensorId);
			packet.setResType(resType);
			packet.setAddrCode(addrCode);
			packet.setDataCode(dataCode);
			return packet;
		}else if("433M".equals(freqType) && "1527".equals(codeType)){
			EV1527433WritePacket packet = new EV1527433WritePacket();
			packet.setSensorId(sensorId);
			packet.setResType(resType);
			packet.setAddrCode(addrCode);
			packet.setDataCode(dataCode);
			return packet;
		}else{
			PT2262315WritePacket packet = new PT2262315WritePacket();
			packet.setSensorId(sensorId);
			packet.setResType(resType);
			packet.setAddrCode(addrCode);
			packet.setDataCode(dataCode);
			return packet;
		}
	}
	
	public static OutPacket getSensorSendOutPacket(String sensorId, String freqType, String codeType){
		return getSensorSendOutPacket(Integer.valueOf(sensorId.substring(2)), freqType, codeType);
	}
	
	public static OutPacket getSensorSendOutPacket(String sensorId){
		String Type = sensorId.substring(0, 2);
		int load = Integer.valueOf(sensorId.substring(2));
		if("RA".equals(Type)){
			PT2262315SendPacket packet = new PT2262315SendPacket();
			packet.setSensorId(load);
			return packet;
		}else if("RB".equals(Type)){
			PT2262433SendPacket packet = new PT2262433SendPacket();
			packet.setSensorId(load);
			return packet;
		}else if("RC".equals(Type)){
			EV1527315SendPacket packet = new EV1527315SendPacket();
			packet.setSensorId(load);
			return packet;
		}else if("RD".equals(Type)){
			EV1527433SendPacket packet = new EV1527433SendPacket();
			packet.setSensorId(load);
			return packet;
		}else{
			PT2262315SendPacket packet = new PT2262315SendPacket();
			packet.setSensorId(load);
			return packet;
		}
	}
	
	public static OutPacket getSensorSendOutPacket(int sensorId, String freqType, String codeType){
		if("315M".equals(freqType) && "2262".equals(codeType)){
			PT2262315SendPacket packet = new PT2262315SendPacket();
			packet.setSensorId(sensorId);
			return packet;
		}else if("433M".equals(freqType) && "2262".equals(codeType)){
			PT2262433SendPacket packet = new PT2262433SendPacket();
			packet.setSensorId(sensorId);
			return packet;
		}else if("315M".equals(freqType) && "1527".equals(codeType)){
			EV1527315SendPacket packet = new EV1527315SendPacket();
			packet.setSensorId(sensorId);
			return packet;
		}else if("433M".equals(freqType) && "1527".equals(codeType)){
			EV1527433SendPacket packet = new EV1527433SendPacket();
			packet.setSensorId(sensorId);
			return packet;
		}else{
			PT2262315SendPacket packet = new PT2262315SendPacket();
			packet.setSensorId(sensorId);
			return packet;
		}
	}
}
