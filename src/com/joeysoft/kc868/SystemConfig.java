package com.joeysoft.kc868;

import static com.joeysoft.kc868.resource.Messages.system_title;
import static com.joeysoft.kc868.resource.Messages.center_title;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.eclipse.swt.graphics.Point;

import com.joeysoft.kc868.common.GlobalMethod;
import com.joeysoft.kc868.db.util.DictConst;

/**
 * 系统设置
 * @author JOEY
 *
 */
public class SystemConfig {
	// 单一实例
    private static SystemConfig config = new SystemConfig();
    
    private Point mainPoint = new Point(0, 0);
    
    private boolean loginSync = true;
    // 防预
    private String sw = "N";
    
    // 动作间隔时间
 	private int actionInterval = 0;
 	
    private String systemTitle = system_title;
    
    private String centerTitle = center_title;
    
    // 系统是否已经被修改过， 修改过要同步数据
    private boolean isSysChanged = false;
    
    // 用户类型 0 普通用户 1 管理员
    private int userType = 0;
    
    private boolean isAdmin = false;
    
	private String password = "";
	private String adminPassword = "";
	// 与主机是否连接
	private boolean isConnected = false;
	
	private boolean isClosed = false;
	
	// 调光灯等级
	private Map<Integer, Integer> lightLevelBarValueMap = new HashMap<Integer, Integer>();
	// 空调等级
	private int conLevelBarValue;
	
	// 国家代码
	//private String countryCode = "+86";
	
	private String JsonFileName = "mobile2.txt";
	private String JsonZipFileName = "mobile2.zip";
	private String dbFileName = "db.mdb";
	private String dbLockFileName = "db.ldb";
	private String zipFileName = "db.zip";
	
	// 无线安防
	private String P315MInFileName = "P315M_IN.DAT";
	private String P433MInFileName = "P433M_IN.DAT";
	
	private String pcSoft = "2.1.0";
	
	private String hostType = "";
	private String hardVer = "";
	private String softVer = "";
	private String hostTemp = "";
	
	// 是否2.0.0|3.0.0 版本
	private boolean isHardSoftVer2030 = false;
	// 是否2.1.0|3.1.0 版本
	private boolean isHardSoftVer2131 = false;
	
	// 是否工程版本
	private boolean isEngine=true;
	// 是否有sim卡
	private boolean hasSim =true;
	// 继电器状态
	private Map<Integer, Boolean> relayStatus = new HashMap<Integer, Boolean>();
	
	private SystemConfig(){
		
	}
	
	public static SystemConfig getInstance() {
		return config;
	}
	
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getAdminPassword() {
		return adminPassword;
	}
	public void setAdminPassword(String adminPassword) {
		this.adminPassword = adminPassword;
	}

	public int getLightLevelBarValue(int deviceId) {
		Integer value = lightLevelBarValueMap.get(deviceId);
		return value == null?0:value;
	}

	public void setLightLevelBarValue(int deviceId, int levelBarvalue) {
		this.lightLevelBarValueMap.put(deviceId, levelBarvalue);
	}

	public int getConLevelBarValue() {
		return conLevelBarValue;
	}

	public void setConLevelBarValue(int conLevelBarValue) {
		this.conLevelBarValue = conLevelBarValue;
	}

	/*public String getCountryCode() {
		return countryCode;
	}

	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}*/

	public String getJsonFileName() {
		return JsonFileName;
	}

	public void setJsonFileName(String jsonFileName) {
		JsonFileName = jsonFileName;
	}

	public String getPcSoft() {
		return pcSoft;
	}

	public void setPcSoft(String pcSoft) {
		this.pcSoft = pcSoft;
	}

	public int getActionInterval() {
		return actionInterval;
	}

	public void setActionInterval(int actionInterval) {
		this.actionInterval = actionInterval;
	}

	public String getHostType() {
		return hostType;
	}

	public void setHostType(String hostType) {
		this.hostType = hostType;
		if(DictConst.KC868C.equals(SystemConfig.getInstance().getHostType())
    			|| DictConst.KC868D.equals(SystemConfig.getInstance().getHostType())){
			isEngine = true;
		}else{
			isEngine = false;
		}
		
		if(DictConst.KC868B.equals(SystemConfig.getInstance().getHostType())
    			|| DictConst.KC868D.equals(SystemConfig.getInstance().getHostType())){
			hasSim = true;
		}else{
			hasSim = false;
		}
	}

	public String getHardVer() {
		return hardVer;
	}

	public void setHardVer(String hardVer) {
		this.hardVer = hardVer;
	}

	public String getSoftVer() {
		return softVer;
	}

	public void setSoftVer(String softVer) {
		this.softVer = softVer;
		if("2.0.0".equals(softVer) || "3.0.0".equals(softVer)){
			isHardSoftVer2030 = true;
		}else if("2.1.0".equals(softVer) || "3.1.0".equals(softVer)){
			isHardSoftVer2131 = true;
		}
	}

	public String getHostTemp() {
		return hostTemp;
	}

	public void setHostTemp(String hostTemp) {
		this.hostTemp = hostTemp;
	}

	public Map<Integer, Boolean> getRelayStatus() {
		return relayStatus;
	}

	public void setRelayStatus(int relay) {
		String relays = GlobalMethod.addLeadingZero(Integer.toBinaryString(relay), 8);
		
		for(int i=0; i<8; i++){
			String b = relays.substring(i, i+1);
			if("1".equals(b)){
				relayStatus.put(i + 1, new Boolean(true));
			}else if("0".equals(b)){
				relayStatus.put(i + 1, new Boolean(false));
			}
		}
	}

	public boolean isEngine() {
		return isEngine;
	}

	public void setEngine(boolean isEngine) {
		this.isEngine = isEngine;
	}

	public boolean hasSim() {
		return hasSim;
	}

	public void setHasSim(boolean hasSim) {
		this.hasSim = hasSim;
	}

	public String getDbFileName() {
		return dbFileName;
	}

	public String getZipFileName() {
		return zipFileName;
	}

	public boolean isSysChanged() {
		return isSysChanged;
	}

	public void setSysChanged(boolean isSysChanged) {
		this.isSysChanged = isSysChanged;
	}

	public int getUserType() {
		return userType;
	}

	public void setUserType(int userType) {
		this.userType = userType;
		if(DictConst.ADMIN_USER == userType){
			this.isAdmin = true;
		}
	}

	public String getSystemTitle() {
		return systemTitle;
	}

	public void setSystemTitle(String systemTitle) {
		if(StringUtils.isNotEmpty(systemTitle)){
			this.systemTitle = systemTitle;
		}
	}

	public String getCenterTitle() {
		return centerTitle;
	}

	public void setCenterTitle(String centerTitle) {
		if(StringUtils.isNotEmpty(centerTitle)){
			this.centerTitle = centerTitle;
		}
	}

	public boolean isAdmin() {
		return isAdmin;
	}

	public void setAdmin(boolean isAdmin) {
		this.isAdmin = isAdmin;
	}

	public String getSw() {
		return sw;
	}

	public void setSw(String sw) {
		this.sw = sw;
	}

	public boolean isConnected() {
		return isConnected;
	}

	public void setConnected(boolean isConnected) {
		this.isConnected = isConnected;
	}

	public void setMainPoint(Point p){
		mainPoint.x = p.x;
		mainPoint.y = p.y;
	}
	
	public void setMainX(int x){
		mainPoint.x = x;
	}
	
	public void setMainY(int y){
		mainPoint.y = y;
	}

	public Point getMainPoint() {
		return mainPoint;
	}

	public boolean isClosed() {
		return isClosed;
	}

	public void setClosed(boolean isClosed) {
		this.isClosed = isClosed;
	}

	public String getDbLockFileName() {
		return dbLockFileName;
	}

	public void setDbLockFileName(String dbLockFileName) {
		this.dbLockFileName = dbLockFileName;
	}

	public boolean isLoginSync() {
		return loginSync;
	}

	public void setLoginSync(boolean loginSync) {
		this.loginSync = loginSync;
	}

	public String getJsonZipFileName() {
		return JsonZipFileName;
	}

	public void setJsonZipFileName(String jsonZipFileName) {
		JsonZipFileName = jsonZipFileName;
	}

	public String getP315MInFileName() {
		return P315MInFileName;
	}

	public void setP315MInFileName(String p315mInFileName) {
		P315MInFileName = p315mInFileName;
	}

	public String getP433MInFileName() {
		return P433MInFileName;
	}

	public void setP433MInFileName(String p433mInFileName) {
		P433MInFileName = p433mInFileName;
	}

	public boolean isHardSoftVer2030() {
		return isHardSoftVer2030;
	}

	public void setHardSoftVer2030(boolean isHardSoftVer2030) {
		this.isHardSoftVer2030 = isHardSoftVer2030;
	}

	public boolean isHardSoftVer2131() {
		return isHardSoftVer2131;
	}

	public void setHardSoftVer2131(boolean isHardSoftVer2131) {
		this.isHardSoftVer2131 = isHardSoftVer2131;
	}
}
