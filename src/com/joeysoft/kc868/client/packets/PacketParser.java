package com.joeysoft.kc868.client.packets;

import org.jboss.netty.buffer.ChannelBuffer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.joeysoft.kc868.client.packets.in.AlarmReadReplyPacket;
import com.joeysoft.kc868.client.packets.in.AlarmWriteReplyPacket;
import com.joeysoft.kc868.client.packets.in.DelSlaverReplyPacket;
import com.joeysoft.kc868.client.packets.in.FindSignalNowReplyPacket;
import com.joeysoft.kc868.client.packets.in.FindSimNowErrorReplyPacket;
import com.joeysoft.kc868.client.packets.in.FindSimNowOkReplyPacket;
import com.joeysoft.kc868.client.packets.in.GSMWriteCmpReplyPacket;
import com.joeysoft.kc868.client.packets.in.GSMWriteSmsReplyPacket;
import com.joeysoft.kc868.client.packets.in.GetPasswordReplyPacket;
import com.joeysoft.kc868.client.packets.in.GetVersionReplyPacket;
import com.joeysoft.kc868.client.packets.in.HostReSetReplyPacket;
import com.joeysoft.kc868.client.packets.in.OperationStatusErrorPacket;
import com.joeysoft.kc868.client.packets.in.RF1101ReadReplyPacket;
import com.joeysoft.kc868.client.packets.in.ReadHostReplyPacket;
import com.joeysoft.kc868.client.packets.in.ReadIPReplyPacket;
import com.joeysoft.kc868.client.packets.in.ReadNowReplyPacket;
import com.joeysoft.kc868.client.packets.in.ReadTemperatureReplyPacket;
import com.joeysoft.kc868.client.packets.in.ResetHostOverReplyPacket;
import com.joeysoft.kc868.client.packets.in.ResetHostReplyPacket;
import com.joeysoft.kc868.client.packets.in.RestorHandEndReplyPacket;
import com.joeysoft.kc868.client.packets.in.RestorHandStartReplyPacket;
import com.joeysoft.kc868.client.packets.in.RmvTLimitReplyPacket;
import com.joeysoft.kc868.client.packets.in.SetHLimitOkReplyPacket;
import com.joeysoft.kc868.client.packets.in.SetIPReplyPacket;
import com.joeysoft.kc868.client.packets.in.SetPasswordReplyPacket;
import com.joeysoft.kc868.client.packets.in.SetSlaverReplyPacket;
import com.joeysoft.kc868.client.packets.in.SetTLimitOkReplyPacket;
import com.joeysoft.kc868.client.packets.in.SetTLimitOvertopReplyPacket;
import com.joeysoft.kc868.client.packets.in.SetTLimitUnderReplyPacket;
import com.joeysoft.kc868.client.packets.in.SetVersionReplyPacket;
import com.joeysoft.kc868.client.packets.in.SetWarningReplyPacket;
import com.joeysoft.kc868.client.packets.in.TelephoneWriteCmpReplyPacket;
import com.joeysoft.kc868.client.packets.in.UnknownInPacket;
import com.joeysoft.kc868.client.packets.in.WriteTimeReplyPacket;
import com.joeysoft.kc868.client.packets.in.ev1527315.EV1527315InputReplyPacket;
import com.joeysoft.kc868.client.packets.in.ev1527315.EV1527315StudyCloseReplyPacket;
import com.joeysoft.kc868.client.packets.in.ev1527315.EV1527315DeleteReplyPacket;
import com.joeysoft.kc868.client.packets.in.ev1527315.EV1527315SendReplyPacket;
import com.joeysoft.kc868.client.packets.in.ev1527315.EV1527315StudyOKReplyPacket;
import com.joeysoft.kc868.client.packets.in.ev1527315.EV1527315StudyReplyPacket;
import com.joeysoft.kc868.client.packets.in.ev1527315.EV1527315WriteReplyPacket;
import com.joeysoft.kc868.client.packets.in.ev1527433.EV1527433InputReplyPacket;
import com.joeysoft.kc868.client.packets.in.ev1527433.EV1527433StudyCloseReplyPacket;
import com.joeysoft.kc868.client.packets.in.ev1527433.EV1527433DeleteReplyPacket;
import com.joeysoft.kc868.client.packets.in.ev1527433.EV1527433SendReplyPacket;
import com.joeysoft.kc868.client.packets.in.ev1527433.EV1527433StudyOKReplyPacket;
import com.joeysoft.kc868.client.packets.in.ev1527433.EV1527433StudyReplyPacket;
import com.joeysoft.kc868.client.packets.in.ev1527433.EV1527433WriteReplyPacket;
import com.joeysoft.kc868.client.packets.in.event.EventConnectReplyPacket;
import com.joeysoft.kc868.client.packets.in.event.EventDelayReplyPacket;
import com.joeysoft.kc868.client.packets.in.event.EventDisconnectReplyPacket;
import com.joeysoft.kc868.client.packets.in.event.EventRunReplyPacket;
import com.joeysoft.kc868.client.packets.in.event.EventWriteReplyPacket;
import com.joeysoft.kc868.client.packets.in.file.FileReadEmptyReplyPacket;
import com.joeysoft.kc868.client.packets.in.file.FileReadEndReplyPacket;
import com.joeysoft.kc868.client.packets.in.file.FileReadErrorReplyPacket;
import com.joeysoft.kc868.client.packets.in.file.FileReadHeadReplyPacket;
import com.joeysoft.kc868.client.packets.in.file.FileReadReplyPacket;
import com.joeysoft.kc868.client.packets.in.file.FileWriteEndReplyPacket;
import com.joeysoft.kc868.client.packets.in.file.FileWriteErrorReplyPacket;
import com.joeysoft.kc868.client.packets.in.file.FileWriteOKReplyPacket;
import com.joeysoft.kc868.client.packets.in.file.FileWriteStartReplyPacket;
import com.joeysoft.kc868.client.packets.in.infrared.InfraredSendReplyPacket;
import com.joeysoft.kc868.client.packets.in.infrared.InfraredStudyCloseReplyPacket;
import com.joeysoft.kc868.client.packets.in.infrared.InfraredStudyOverFlowReplyPacket;
import com.joeysoft.kc868.client.packets.in.infrared.InfraredStudyOverReplyPacket;
import com.joeysoft.kc868.client.packets.in.infrared.InfraredStudyReplyPacket;
import com.joeysoft.kc868.client.packets.in.infrared.InfraredStudyTestReplyPacket;
import com.joeysoft.kc868.client.packets.in.infrared.InfraredStudyWriteReplyPacket;
import com.joeysoft.kc868.client.packets.in.lineate.IOIRQHighReplyPacket;
import com.joeysoft.kc868.client.packets.in.lineate.IOSetADReplyPacket;
import com.joeysoft.kc868.client.packets.in.lineate.IOSetModeNumReplyPacket;
import com.joeysoft.kc868.client.packets.in.lineate.IOSetRFReplyPacket;
import com.joeysoft.kc868.client.packets.in.lineate.RelayReadNowReplyPacket;
import com.joeysoft.kc868.client.packets.in.lineate.RelaySetRelayReplyPacket;
import com.joeysoft.kc868.client.packets.in.lineate.SpeakSetSpeakReplyPacket;
import com.joeysoft.kc868.client.packets.in.pt2262315.PT2262315InputReplyPacket;
import com.joeysoft.kc868.client.packets.in.pt2262315.PT2262315StudyCloseReplyPacket;
import com.joeysoft.kc868.client.packets.in.pt2262315.PT2262315DeleteReplyPacket;
import com.joeysoft.kc868.client.packets.in.pt2262315.PT2262315SendReplyPacket;
import com.joeysoft.kc868.client.packets.in.pt2262315.PT2262315StudyOKReplyPacket;
import com.joeysoft.kc868.client.packets.in.pt2262315.PT2262315StudyReplyPacket;
import com.joeysoft.kc868.client.packets.in.pt2262315.PT2262315WriteReplyPacket;
import com.joeysoft.kc868.client.packets.in.pt2262433.PT2262433InputReplyPacket;
import com.joeysoft.kc868.client.packets.in.pt2262433.PT2262433StudyCloseReplyPacket;
import com.joeysoft.kc868.client.packets.in.pt2262433.PT2262433DeleteReplyPacket;
import com.joeysoft.kc868.client.packets.in.pt2262433.PT2262433SendReplyPacket;
import com.joeysoft.kc868.client.packets.in.pt2262433.PT2262433StudyOKReplyPacket;
import com.joeysoft.kc868.client.packets.in.pt2262433.PT2262433StudyReplyPacket;
import com.joeysoft.kc868.client.packets.in.pt2262433.PT2262433WriteReplyPacket;
import com.joeysoft.kc868.client.packets.in.rf2262315.RF2262315DeleteReplyPacket;
import com.joeysoft.kc868.client.packets.in.rf2262315.RF2262315SendReplyPacket;
import com.joeysoft.kc868.client.packets.in.rf2262315.RF2262315StudyCloseReplyPacket;
import com.joeysoft.kc868.client.packets.in.rf2262315.RF2262315StudyOKReplyPacket;
import com.joeysoft.kc868.client.packets.in.rf2262315.RF2262315StudyReplyPacket;
import com.joeysoft.kc868.client.packets.out.rf2262315.RF2262315StudyPacket;
import com.joeysoft.kc868.client.support.Protocol;

public class PacketParser implements IParser{
	private static Logger logger = LoggerFactory.getLogger(PacketParser.class);
	
	private int fileSegmentCount; // 文件总包数
	private int fileSegment = 0; // 得到文件包数
	private int length;
	
	/** 特殊标志当前开始文件传送 */
	private boolean readFile;

	public PacketParser(PacketHelper packetHelper) {
	}
	
	@Override
	public boolean accept(ChannelBuffer buf) {
		length = buf.readableBytes();
		if(length <= 0)
		    return false;
		return true;
	}

	@Override
	public boolean isDuplicated(InPacket packet) {
		/*boolean duplicated = history.check(packet, true);
			
	    if(duplicated)
	        logger.debug("POS号" + packet.getPosCode() + " 序号 " + packet.getSequence() + "重复到达，忽略");
	    return duplicated;*/
		return false;
	}

	@Override
	public boolean isDuplicatedNeedReply() {
		return false;
	}

	@Override
	public boolean isIncoming(ChannelBuffer buf) {
		return false;
	}

	@Override
	public InPacket parseIncoming(ChannelBuffer buf)
			throws PacketParseException {
		return null;
	}

	@Override
	public OutPacket parseOutcoming(ChannelBuffer buf)
			throws PacketParseException {
		return null;
	}
	
	@Override
	public InPacket parseIncoming(ChannelBuffer buf, int offset, int length)
			throws PacketParseException {
		String message = null;
		try{
			if(readFile){
				byte[] tempbuf = new byte[length];
				buf.readBytes(tempbuf);
				
				message = new String(tempbuf);
				if(message.startsWith(Protocol.MSG_FILE_READ_END_REPLY)){
					logger.debug("收到" + message);
					readFile = false;
					return new FileReadEndReplyPacket(Protocol.CMD_FILE_READ_END, message);
				}else if(message.startsWith(Protocol.MSG_FILE_READ_NEW_REPLY)){ // 新协议
					int index = message.indexOf(",");
					String mes = message.substring(0, index);
					int fileSegment =  Integer.valueOf(mes.substring(Protocol.MSG_FILE_READ_NEW_REPLY.length(), index));
					int size = tempbuf.length - (index + 1);//  有个逗号
					//fileSegment++;
					if(fileSegment >= fileSegmentCount){
						readFile = false;
					}
					
					byte[] bufs = new byte[size];
					System.arraycopy(tempbuf, (index + 1), bufs, 0, size);
					return new FileReadReplyPacket(Protocol.CMD_FILE_READ, mes, bufs);
				}else{
					return new FileReadReplyPacket(Protocol.CMD_FILE_READ, tempbuf);
				}
			}else{
				byte[] tempbuf = new byte[length - 1];
				buf.readBytes(tempbuf);
				buf.readByte();// 最后有个特殊字符
				
				message = new String(tempbuf);
				
				logger.debug("收到" + message);
				
				if(message.startsWith(Protocol.MSG_GET_PASSWORD_REPLY)){
					return new GetPasswordReplyPacket(Protocol.CMD_GET_PASSWORD, message);
				}else if(message.equals(Protocol.MSG_SET_PASSWORD_REPLY)){
					return new SetPasswordReplyPacket(Protocol.CMD_SET_PASSWORD, message);
				}else if(message.equals(Protocol.MSG_RESET_HOST_REPLY)){
					return new ResetHostReplyPacket(Protocol.CMD_RESET_HOST, message);
				}else if(message.startsWith(Protocol.MSG_RESET_HOST_OVER_REPLY)){
					return new ResetHostOverReplyPacket(Protocol.CMD_RESET_HOST_OVER, message);
				}else if(message.startsWith(Protocol.MSG_READ_HOST_REPLY)){
					return new ReadHostReplyPacket(Protocol.CMD_READ_HOST, message);
				}else if(message.startsWith(Protocol.MSG_READ_TEMPERATURE_REPLY)){
					return new ReadTemperatureReplyPacket(Protocol.CMD_READ_TEMPERATURE, message);
				}else if(message.startsWith(Protocol.MSG_READ_IP_REPLY)){
					return new ReadIPReplyPacket(Protocol.CMD_READ_IP, message);
				}else if(message.startsWith(Protocol.MSG_GET_VERSION_REPLY)){
					return new GetVersionReplyPacket(Protocol.CMD_GET_VERSION, message);
				}else if(message.equals(Protocol.MSG_SET_VERSION_REPLY)){
					return new SetVersionReplyPacket(Protocol.CMD_SET_VERSION, message);
				}else if(message.equals(Protocol.MSG_SET_IP_REPLY)){
					return new SetIPReplyPacket(Protocol.CMD_SET_IP, message);
					
				}else if(message.equals(Protocol.MSG_FILE_WRITE_START_REPLY)){
					return new FileWriteStartReplyPacket(Protocol.CMD_FILE_WRITE_START, message);
				}else if(message.equals(Protocol.MSG_FILE_WRITE_OK_REPLY)){
					return new FileWriteOKReplyPacket(Protocol.CMD_FILE_WRITE_OK, message);
				}else if(message.startsWith(Protocol.MSG_FILE_WRITE_END_REPLY)){
					return new FileWriteEndReplyPacket(Protocol.CMD_FILE_WRITE_END, message);
				}else if(message.equals(Protocol.MSG_FILE_WRITE_ERROR_REPLY)){
					return new FileWriteErrorReplyPacket(Protocol.CMD_FILE_WRITE_ERROR, message);
				}else if(message.equals(Protocol.MSG_FILE_READ_ERROR_REPLY)){
					readFile = false;
					return new FileReadErrorReplyPacket(Protocol.CMD_FILE_READ_ERROR, message);
				}else if(message.equals(Protocol.MSG_FILE_READ_EMPTY_REPLY)){
					readFile = false;
					return new FileReadEmptyReplyPacket(Protocol.CMD_FILE_READ_EMPTY, message);
				}else if(message.startsWith(Protocol.MSG_FILE_READ_HEAD_REPLY)){
					readFile = true;
					FileReadHeadReplyPacket reply = new FileReadHeadReplyPacket(Protocol.CMD_FILE_READ_HEAD, message);
					fileSegment = 0;
					fileSegmentCount = reply.getFileSegmentCount();
					return reply;
				}else if(message.equals(Protocol.MSG_FILE_READ_END_REPLY)){
					readFile = false;
					return new FileReadEndReplyPacket(Protocol.CMD_FILE_READ_END, message);
					
				}else if(message.startsWith(Protocol.MSG_READ_NOW_REPLY)){
					return new ReadNowReplyPacket(Protocol.CMD_READ_NOW, message);
				}else if(message.equals(Protocol.MSG_WRITE_TIME_REPLY)){
					return new WriteTimeReplyPacket(Protocol.CMD_WRITE_TIME, message);
				}else if(message.equals(Protocol.MSG_ALARM_WRITE_REPLY)){
					return new AlarmWriteReplyPacket(Protocol.CMD_ALARM_WRITE, message);
				}else if(message.startsWith(Protocol.MSG_ALARM_READ_REPLY)){
					return new AlarmReadReplyPacket(Protocol.CMD_ALARM_READ, message);
					
				}else if(message.equals(Protocol.MSG_HOST_SET_RESET_REPLY)){
					return new HostReSetReplyPacket(Protocol.CMD_HOST_SET_RESET, message);
					
					
				}else if(message.equals(Protocol.MSG_SET_SLAVER_REPLY)){
					return new SetSlaverReplyPacket(Protocol.CMD_SET_SLAVER, message);
				}else if(message.equals(Protocol.MSG_DEL_SLAVER_REPLY)){
					return new DelSlaverReplyPacket(Protocol.CMD_DEL_SLAVER, message);
				}else if(message.startsWith(Protocol.MSG_RF1101_READ_REPLY)){
					return new RF1101ReadReplyPacket(Protocol.CMD_RF1101_READ, message);
					
				}else if(message.equals(Protocol.MSG_SET_T_LIMIT_OK_REPLY)){
					return new SetTLimitOkReplyPacket(Protocol.CMD_SET_T_LIMIT, message);
				}else if(message.equals(Protocol.MSG_SET_H_LIMIT_OK_REPLY)){
					return new SetHLimitOkReplyPacket(Protocol.CMD_SET_H_LIMIT, message);
					
				}else if(message.equals(Protocol.MSG_SET_T_LIMIT_OVERTOP_REPLY)){
					return new SetTLimitOvertopReplyPacket(Protocol.CMD_SET_T_LIMIT, message);
				}else if(message.equals(Protocol.MSG_SET_T_LIMIT_UNDER_REPLY)){
					return new SetTLimitUnderReplyPacket(Protocol.CMD_SET_T_LIMIT, message);
				}else if(message.equals(Protocol.MSG_RMV_T_LIMIT_REPLY)){
					return new RmvTLimitReplyPacket(Protocol.CMD_RMV_T_LIMIT, message);
				}else if(message.equals(Protocol.MSG_EVENT_WRITE_REPLY)){
					return new EventWriteReplyPacket(Protocol.CMD_EVENT_WRITE, message);
				}else if(message.equals(Protocol.MSG_EVENT_CONNECT_REPLY)){
					return new EventConnectReplyPacket(Protocol.CMD_EVENT_CONNECT, message);
				}else if(message.equals(Protocol.MSG_EVENT_DISCONNECT_REPLY)){
					return new EventDisconnectReplyPacket(Protocol.CMD_EVENT_DISCONNECT, message);
				}else if(message.equals(Protocol.MSG_EVENT_RUN_REPLY)){
					return new EventRunReplyPacket(Protocol.CMD_EVENT_RUN, message);
				}else if(message.equals(Protocol.MSG_EVENT_DELAY_REPLY)){
					return new EventDelayReplyPacket(Protocol.CMD_EVENT_DELAY, message);
					
				}else if(message.equals(Protocol.MSG_EVENT_SET_WARNING_REPLY)){
					return new SetWarningReplyPacket(Protocol.CMD_EVENT_SET_WARNING, message);
				}else if(message.equals(Protocol.MSG_PT2262_315M_WRITE_REPLY)){
					return new PT2262315WriteReplyPacket(Protocol.CMD_PT2262_315M_WRITE, message);
				}else if(message.equals(Protocol.MSG_PT2262_315M_SEND_REPLY)){
					return new PT2262315SendReplyPacket(Protocol.CMD_PT2262_315M_SEND, message);
				}else if(message.equals(Protocol.MSG_PT2262_315M_STUDY_REPLY)){
					return new PT2262315StudyReplyPacket(Protocol.CMD_PT2262_315M_STUDY, message);
				}else if(message.equals(Protocol.MSG_PT2262_315M_STUDY_CLOSE_REPLY)){
					return new PT2262315StudyCloseReplyPacket(Protocol.CMD_PT2262_315M_STUDY_CLOSE, message);
				}else if(message.startsWith(Protocol.MSG_PT2262_315M_STUDY_OK_REPLY)){
					return new PT2262315StudyOKReplyPacket(Protocol.CMD_PT2262_315M_STUDY_OK, message);
				}else if(message.equals(Protocol.MSG_PT2262_315M_DELETE_REPLY)){
					return new PT2262315DeleteReplyPacket(Protocol.CMD_PT2262_315M_DELETE, message);
				}else if(message.startsWith(Protocol.MSG_PT2262_315M_INPUT_REPLY)){
					return new PT2262315InputReplyPacket(Protocol.CMD_PT2262_315M_INPUT, message);
					
				}else if(message.equals(Protocol.MSG_PT2262_433M_WRITE_REPLY)){
					return new PT2262433WriteReplyPacket(Protocol.CMD_PT2262_433M_WRITE, message);
				}else if(message.equals(Protocol.MSG_PT2262_433M_SEND_REPLY)){
					return new PT2262433SendReplyPacket(Protocol.CMD_PT2262_433M_SEND, message);
				}else if(message.equals(Protocol.MSG_PT2262_433M_STUDY_REPLY)){
					return new PT2262433StudyReplyPacket(Protocol.CMD_PT2262_433M_STUDY, message);
				}else if(message.equals(Protocol.MSG_PT2262_433M_STUDY_CLOSE_REPLY)){
					return new PT2262433StudyCloseReplyPacket(Protocol.CMD_PT2262_433M_STUDY_CLOSE, message);
				}else if(message.startsWith(Protocol.MSG_PT2262_433M_STUDY_OK_REPLY)){
					return new PT2262433StudyOKReplyPacket(Protocol.CMD_PT2262_433M_STUDY_OK, message);
				}else if(message.equals(Protocol.MSG_PT2262_433M_DELETE_REPLY)){
					return new PT2262433DeleteReplyPacket(Protocol.CMD_PT2262_433M_DELETE, message);
				}else if(message.startsWith(Protocol.MSG_PT2262_433M_INPUT_REPLY)){
					return new PT2262433InputReplyPacket(Protocol.CMD_PT2262_433M_INPUT, message);
					
				}else if(message.equals(Protocol.MSG_EV1527_315M_WRITE_REPLY)){
					return new EV1527315WriteReplyPacket(Protocol.CMD_EV1527_315M_WRITE, message);
				}else if(message.equals(Protocol.MSG_EV1527_315M_SEND_REPLY)){
					return new EV1527315SendReplyPacket(Protocol.CMD_EV1527_315M_SEND, message);
				}else if(message.equals(Protocol.MSG_EV1527_315M_STUDY_REPLY)){
					return new EV1527315StudyReplyPacket(Protocol.CMD_EV1527_315M_STUDY, message);
				}else if(message.equals(Protocol.MSG_EV1527_315M_STUDY_CLOSE_REPLY)){
					return new EV1527315StudyCloseReplyPacket(Protocol.CMD_EV1527_315M_STUDY_CLOSE, message);
				}else if(message.startsWith(Protocol.MSG_EV1527_315M_STUDY_OK_REPLY)){
					return new EV1527315StudyOKReplyPacket(Protocol.CMD_EV1527_315M_STUDY_OK, message);
				}else if(message.equals(Protocol.MSG_EV1527_315M_DELETE_REPLY)){
					return new EV1527315DeleteReplyPacket(Protocol.CMD_EV1527_315M_DELETE, message);
				}else if(message.startsWith(Protocol.MSG_EV1527_315M_INPUT_REPLY)){
					return new EV1527315InputReplyPacket(Protocol.CMD_EV1527_315M_INPUT, message);
					
				}else if(message.equals(Protocol.MSG_EV1527_433M_WRITE_REPLY)){
					return new EV1527433WriteReplyPacket(Protocol.CMD_EV1527_433M_WRITE, message);
				}else if(message.equals(Protocol.MSG_EV1527_433M_SEND_REPLY)){
					return new EV1527433SendReplyPacket(Protocol.CMD_EV1527_433M_SEND, message);
				}else if(message.equals(Protocol.MSG_EV1527_433M_STUDY_REPLY)){
					return new EV1527433StudyReplyPacket(Protocol.CMD_EV1527_433M_STUDY, message);
				}else if(message.equals(Protocol.MSG_EV1527_433M_STUDY_CLOSE_REPLY)){
					return new EV1527433StudyCloseReplyPacket(Protocol.CMD_EV1527_433M_STUDY_CLOSE, message);	
				}else if(message.startsWith(Protocol.MSG_EV1527_433M_STUDY_OK_REPLY)){
					return new EV1527433StudyOKReplyPacket(Protocol.CMD_EV1527_433M_STUDY_OK, message);
				}else if(message.equals(Protocol.MSG_EV1527_433M_DELETE_REPLY)){
					return new EV1527433DeleteReplyPacket(Protocol.CMD_EV1527_433M_DELETE, message);
				}else if(message.startsWith(Protocol.MSG_EV1527_433M_INPUT_REPLY)){
					return new EV1527433InputReplyPacket(Protocol.CMD_EV1527_433M_INPUT, message);
					
				}else if(message.equals(Protocol.MSG_INFRARED_STUDY_REPLY)){
					return new InfraredStudyReplyPacket(Protocol.CMD_INFRARED_STUDY, message);
				}else if(message.equals(Protocol.MSG_INFRARED_STUDY_OVER_REPLY)){
					return new InfraredStudyOverReplyPacket(Protocol.CMD_INFRARED_STUDY_OVER, message);
				}else if(message.equals(Protocol.MSG_INFRARED_STUDY_TEST_REPLY)){
					return new InfraredStudyTestReplyPacket(Protocol.CMD_INFRARED_STUDY_TEST, message);
				}else if(message.equals(Protocol.MSG_INFRARED_STUDY_CLOSE_REPLY)){
					return new InfraredStudyCloseReplyPacket(Protocol.CMD_INFRARED_STUDY_CLOSE, message);
				}else if(message.equals(Protocol.MSG_INFRARED_STUDY_WRITE_REPLY)){
					return new InfraredStudyWriteReplyPacket(Protocol.CMD_INFRARED_STUDY_WRITE, message);
				}else if(message.equals(Protocol.MSG_INFRARED_STUDY_OVERFLOW_REPLY)){
					return new InfraredStudyOverFlowReplyPacket(Protocol.CMD_INFRARED_STUDY_OVERFLOW, message);
				}else if(message.equals(Protocol.MSG_INFRARED_SEND_REPLY)){
					return new InfraredSendReplyPacket(Protocol.CMD_INFRARED_SEND, message);
					
				}else if(message.equals(Protocol.MSG_IO_SET_MODE_NUM_REPLY)){
					return new IOSetModeNumReplyPacket(Protocol.CMD_IO_SET_MODE_NUM, message);
				}else if(message.equals(Protocol.MSG_IO_SET_R_F_NUM_REPLY)){
					return new IOSetRFReplyPacket(Protocol.CMD_IO_SET_R_F_NUM, message);
				}else if(message.equals(Protocol.MSG_IO_SET_AD_REPLY)){
					return new IOSetADReplyPacket(Protocol.CMD_IO_SET_AD, message);
				}else if(message.equals(Protocol.MSG_IO_IRQ_HIGH_REPLY)){
					return new IOIRQHighReplyPacket(Protocol.CMD_IO_IRQ_HIGH, message);
					
				}else if(message.equals(Protocol.MSG_SPEAK_SET_SPEAK_REPLY)){
					return new SpeakSetSpeakReplyPacket(Protocol.CMD_SPEAK_SET_SPEAK, message);	
				}else if(message.equals(Protocol.MSG_RELAY_SET_RELAY_REPLY)){
					return new RelaySetRelayReplyPacket(Protocol.CMD_RELAY_SET_RELAY, message);	
				}else if(message.startsWith(Protocol.MSG_RELAY_READ_NOW_REPLY)){
					return new RelayReadNowReplyPacket(Protocol.CMD_RELAY_READ_NOW, message);	
					
				}else if(message.equals(Protocol.MSG_TELEPHONE_WRITE_CMP_REPLY)){
					return new TelephoneWriteCmpReplyPacket(Protocol.CMD_TELEPHONE_WRITE_CMP, message);	
				}else if(message.equals(Protocol.MSG_TELEPHONE_WRITE_USER_REPLY)){
					return new TelephoneWriteCmpReplyPacket(Protocol.CMD_TELEPHONE_WRITE_USER, message);	
				}else if(message.equals(Protocol.MSG_GSM_WRITE_CMP_REPLY)){
					return new GSMWriteCmpReplyPacket(Protocol.CMD_GSM_WRITE_CMP, message);	
				}else if(message.equals(Protocol.MSG_GSM_WRITE_SMS_REPLY)){
					return new GSMWriteSmsReplyPacket(Protocol.CMD_GSM_WRITE_SMS, message);	
					
				}else if(message.equals(Protocol.MSG_FIND_SIM_NOW_OK_REPLY)){
					return new FindSimNowOkReplyPacket(Protocol.CMD_FIND_SIM_NOW, message);	
				}else if(message.equals(Protocol.MSG_FIND_SIM_NOW_ERROR_REPLY)){
						return new FindSimNowErrorReplyPacket(Protocol.CMD_FIND_SIM_NOW, message);	
				}else if(message.startsWith(Protocol.MSG_FIND_SIGNAL_NOW_REPLY)){
					return new FindSignalNowReplyPacket(Protocol.CMD_FIND_SIGNAL_NOW, message);	
					
				}else if(message.equals(Protocol.MSG_RESTOR_HAND_START_REPLY)){
					return new RestorHandStartReplyPacket(Protocol.CMD_RESTOR_HAND_START, message);
					
				}else if(message.equals(Protocol.MSG_RESTOR_HAND_END_REPLY)){
					return new RestorHandEndReplyPacket(Protocol.CMD_RESTOR_HAND_END, message);
					
				}else if(message.equals(Protocol.MSG_RFSTUY_315M_STUDY_REPLY)){
					return new RF2262315StudyReplyPacket(Protocol.CMD_RFSTUY_315M_STUDY, message);
				}else if(message.equals(Protocol.MSG_RFSTUY_315M_STUDY_OK_REPLY)){
					return new RF2262315StudyOKReplyPacket(Protocol.CMD_RFSTUY_315M_STUDY_OK, message);
				}else if(message.equals(Protocol.MSG_RFSTUY_315M_STUDY_CLOSE_REPLY)){
					return new RF2262315StudyCloseReplyPacket(Protocol.CMD_RFSTUY_315M_CLOSE, message);
				}else if(message.equals(Protocol.MSG_RFSTUY_315M_DELETE_REPLY)){
					return new RF2262315DeleteReplyPacket(Protocol.CMD_RFSTUY_315M_DELETE, message);
				}else if(message.equals(Protocol.MSG_RFSTUY_315M_SEND_REPLY)){
					return new RF2262315SendReplyPacket(Protocol.CMD_RFSTUY_315M_SEND, message);
					
				}else if(message.equals(Protocol.MSG_OPERATION_STATUS_ERROR)){
					return new OperationStatusErrorPacket(Protocol.CMD_OPERATION_STATUS_ERROR, message);
				}else{
					return new UnknownInPacket(Protocol.CMD_UNKNOWN, message);
				}
			}
			
		} catch (Exception e) {
            // 如果解析失败，返回null
			logger.error(e.getMessage(), e);
			return new UnknownInPacket(Protocol.CMD_UNKNOWN, message);
        }
	}

	@Override
	public OutPacket parseOutcoming(ChannelBuffer buf, int offset, int length)
			throws PacketParseException {
		return null;
	}
	
	public int getLength() {
		return length;
	}

	public void setLength(int length) {
		this.length = length;
	}
}
